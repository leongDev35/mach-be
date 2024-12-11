package com.leong.mach.mangaApp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component //! Đánh dấu lớp JwtFilter là một Spring Component, cho phép Spring quản lý và inject nó vào các bean khác trong ứng dụng.
@RequiredArgsConstructor //! Được sử dụng để tự động sinh constructor với các tham số được đánh dấu là final hoặc @NonNull
//!  Trong trường hợp này, Spring sẽ tự động inject JwtService và UserDetailsService vào.
public class JwtFilter extends OncePerRequestFilter {
    //! Kế thừa từ OncePerRequestFilter: Đây là một lớp trừu tượng trong Spring, đảm bảo rằng doFilter chỉ được gọi một lần cho mỗi yêu cầu.
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override //! nơi chính để thực hiện logic xử lý cho mỗi yêu cầu.
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, //! Đối tượng chứa thông tin của yêu cầu HTTP.
            @NonNull HttpServletResponse response, //! Đối tượng chứa thông tin của phản hồi HTTP.
            @NonNull FilterChain filterChain //! Đối tượng giúp gọi tiếp các filter khác trong chuỗi.
    ) throws ServletException, IOException {
        if (request.getServletPath().contains("/api/v1/auth")) { //! kiểm tra đường dẫn nếu có /api/v1/auth thì cho tiếp tục
            filterChain.doFilter(request, response);
            return;
        }

        //! nếu k phải /api/v1/auth 

        final String authHeader = request.getHeader("Authorization"); //! lấy giá trị của Header từ yêu cầu
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) { //! kiểm tra xem Header này có bắt đầu bằng Bearer không
            filterChain.doFilter(request, response); //! nếu không filter cho phép yêu cầu đi tiếp
            return;
        }
        jwt = authHeader.substring(7); //! nếu có trích xuất jwt từ header
        userEmail = jwtService.extractUsername(jwt); //! rút trích email của người dùng từ jwtService

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) { //! nếu email người dùng khác null và trong SecurityContextHolder không có authentication 
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail); //! tải chi tiết người dùng bằng UserDetailsService
            if (jwtService.isTokenValid(jwt, userDetails)) { //! kiểm tra tính hợp lệ của JWT, nếu hợp lệ thì 
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken( //! tạo authToken
                        userDetails, //! đối tượng đại diện người dùng
                        null, //! k lưu password vào đây
                        userDetails.getAuthorities() //! các quyền
                );

                //!Dòng này được sử dụng để thiết lập chi tiết xác thực của người dùng vào đối tượng authToken.
                //! WebAuthenticationDetailsSource().buildDetails(request) được gọi để tạo một đối tượng WebAuthenticationDetails, chứa các thông tin cụ thể về phiên làm việc của người dùng, ví dụ như địa chỉ IP, user-agent, và các thông tin HTTP request khác.
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)  //!  Phương thức buildDetails(request) sẽ trích xuất các thông tin này từ đối tượng HttpServletRequest (request).
                );

                //! => authToken bây giờ chứa thông tin chi tiết cụ thể về request và người dùng trong quá trình xác thực.
                SecurityContextHolder.getContext().setAuthentication(authToken); //!  Dòng này thực hiện việc đặt đối tượng authToken, đã chứa thông tin xác thực của người dùng, vào SecurityContextHolder
            }
        }
        filterChain.doFilter(request, response); //! chuyển yêu cầu đến các filter tiếp theo trong chuỗi xử lý
    }
}