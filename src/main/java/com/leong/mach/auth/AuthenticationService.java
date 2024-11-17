package com.leong.mach.auth;

import com.leong.mach.email.EmailTemplateName;
import com.leong.mach.exception.EmailAlreadyExistsException;
import com.leong.mach.mangaApp.security.JwtService;
import com.leong.mach.email.EmailService;
import com.leong.mach.role.RoleRepository;
import com.leong.mach.user.Token;
import com.leong.mach.user.TokenRepository;
import com.leong.mach.user.User;
import com.leong.mach.user.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

//!  một annotation được sử dụng để đánh dấu một lớp như là một thành phần dịch vụ (service).
//!  Điều này có nghĩa là lớp được đánh dấu @Service đảm nhận các logic xử lý nghiệp vụ của ứng dụng.
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    // ! ĐĂNG KÝ USER TỪ REQUEST
    public void register(RegistrationRequest request) throws MessagingException {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email is already registered");
        }
        var userRole = roleRepository.findByName("USER")
                // todo - better exception handling
                .orElseThrow(() -> new IllegalStateException("ROLE USER was not initiated"));

        var user = User.builder() // ! tạo 1 User object từ request
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // ! mã hóa password
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user); // ! lưu user vào trong DB
        sendValidationEmail(user); // ! gửi email xác thực
    }

    // ! phương thức xác thực. Nhận vào 1 req và trả về AuthenticationResponse
    public AuthenticationResponse authenticate( AuthenticationRequest request) { // ! request chỉ có email và password

        try {
            var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(), // ! set email là principal
                            request.password() // ! set password là credentials
                    ));

            var claims = new HashMap<String, Object>();
            var user = ((User) auth.getPrincipal()); // ! ép kiểu (casting) từ Object mà getPrincipal() trả về, thành
            System.out.println(user.fullName());
            System.out.println(user.getAvatarUrl());
            
            // một đối tượng kiểu User.
            claims.put("fullName", user.getFullName());

            var jwtToken = jwtService.generateToken(claims, (User) auth.getPrincipal());
            return AuthenticationResponse.builder()
                    .token(jwtToken) // ! trả về 1 token cho Client
                    .avatarUrl(user.getAvatarUrl())
                    .fullname(user.fullName())
                    .build();
        // } catch (BadCredentialsException e) { // ! bắt ngoại lệ Bad credentials
        //     throw new BadCredentialsException("Invalid credentials", e);
        } catch (Exception e) {
            // Bắt các ngoại lệ khác
            System.out.println(e);
            throw e; // ! phải ném ra ngoại lệ
        }

    }

    // @Transactional //! phải tắt đi vì nếu có một Exception như token hết hạn =>
    // sẽ roll back và không tạo token mới
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                // todo exception has to be defined
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException(
                    "Activation token has expired. A new token has been send to the same email address");
        }

        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    // ! gửi email để enable tài khoản
    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user); // ! tạo và lưu mã token với user
        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                (activationUrl + newToken),
                newToken,
                "Account activation" // ! chủ đề email
        );
    }

    // ! tạo và lưu Token Active
    private String generateAndSaveActivationToken(User user) { // ! nhận vào 1 user
        // Generate a token
        String generatedToken = generateActivationCode(6); // ! tạo 1 chuỗi mã dài 6 ký tự số
        var token = Token.builder() // ! xây dựng đối tượng Token với các thông tin của người dùng
                .token(generatedToken) // ! lưu mã token vừa tạo vào
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);

        return generatedToken; // ! trả về mã token vừa tạo
    }

    // ! sinh ra 1 chuỗi mã kích hoạt ngẫu nhiên có độ dài được chỉ định
    private String generateActivationCode(int length) {
        String characters = "0123456789"; // ! 1 chuỗi các ký tự
        StringBuilder codeBuilder = new StringBuilder(); // ! Là một đối tượng trong Java được sử dụng để xây dựng và
                                                         // quản lý chuỗi một cách hiệu quả, đặc biệt là khi bạn cần
                                                         // thay đổi nội dung của chuỗi nhiều lần

        SecureRandom secureRandom = new SecureRandom(); // ! là một lớp trong Java cung cấp các phương thức để sinh số
                                                        // ngẫu nhiên một cách an toàn. Trong trường hợp này,
                                                        // secureRandom được sử dụng để sinh ra các chỉ số ngẫu nhiên để
                                                        // lựa chọn ký tự từ characters.

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length()); // ! sinh ra một chỉ số ngẫu nhiên từ 0 đến độ
                                                                         // dài của chuỗi characters
            codeBuilder.append(characters.charAt(randomIndex)); // ! ký tự tại chỉ số ngẫu nhiên này được lấy ra từ
                                                                // chuỗi characters và được thêm vào codeBuilder bằng
                                                                // phương thức append().
        }

        return codeBuilder.toString(); // ! chuyển đổi codeBuilder thành một chuỗi và trả về chuỗi này là mã kích hoạt
                                       // đã được sinh ra ngẫu nhiên.
    }
}
