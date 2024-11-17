package com.leong.mach.mangaApp.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts; //! lớp để tạo JWT instance
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//! service để giải mã token, xác thực token...
@Service
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey; 
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;


    //! trích UserName từ token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    //! method reference cho phép bạn truy cập và sử dụng một phương thức như một đối tượng hàm
    //! ở đây là tham chiếu đến phương thức getSubject của 1 đối tượng Claims

    }

    //! phương thức trích xuất từ Claim
    //! ở đây kiểu T chính là Claims::getSubject
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { //! hàm claimsResolver nhận đối số Claims và trả về kiểu T
        final Claims claims = extractAllClaims(token);
        //! Function là 1 interface có phương thức apply
        //! áp dụng đối số claims cho hàm này để trả về kết quả
        return claimsResolver.apply(claims); //! ở đây sẽ trả về Claims::getSubject
        //! tương đương với claims.getSubject
    }
    
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder() //! phương thức này trả về một builder để cấu hình cách phân tích cú pháp (parse) JWT
                .setSigningKey(getSignInKey()) //! thiết lập khóa để kiểm tra chữ ký
                .build() //! hoàn thành cấu hình trả về 1 object JwtParser
                .parseClaimsJws(token) //! Phương thức này trả về một đối tượng Jws<Claims> (JSON Web Signature) chứa các claims và thông tin chữ ký của JWT.
                .getBody(); //! trả về phần payload (claims) của JWT dưới dạng đối tượng Claims.
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration) {
        var authorities = userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .toList();
        return Jwts
                .builder() // ! tạo JwtBuilder instance.
                .setClaims(extraClaims) // ! The JWT payload may be either byte[] content (via content) or JSON Claims
                                        // (such as subject, claims, etc), but not both.
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())) // ! set ngày token được phát hành
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // ! set thời gian hết hạn cho JWT
                .claim("authorities", authorities) // ! để thêm một claim (yêu cầu) tùy chỉnh vào phần payload của JWT.
                                                   // Ở đây là các quyền của User
                .signWith(getSignInKey()) // ! ký JWT với khóa bí mật => xác thực nguồn gốc và tính toàn vẹn, đảm bảo là
                                          // nó không bị giả mạo
                .compact(); // ! Gọi phương thức compact() để tạo ra chuỗi JWT thu gọn.
    }


    //! check token có hợp lệ không 
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        //! so sánh tên trong token và tên userDetail cùng với xem token hết hạn hay không
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    //! kiểm tra token có hết hạn hay không
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); //! lấy hạn của token
    }

  

    private Key getSignInKey() {
        // ! Decoders.BASE64.decode(secretKey): Đây là sử dụng lớp Decoders từ thư viện
        // io.jsonwebtoken.io, và phương thức BASE64.decode để giải mã chuỗi secretKey
        // từ định dạng Base64 về mảng byte.
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        // ! Keys: Là một lớp trong thư viện io.jsonwebtoken.security được sử dụng để
        // tạo các đối tượng Key cho việc ký và xác thực JWT.
        // ! hmacShaKeyFor(keyBytes): Là phương thức tạo một đối tượng Key dùng cho
        // thuật toán HMAC-SHA từ mảng byte keyBytes. HMAC-SHA là một thuật toán mã hóa
        // băm kết hợp (hash-based message authentication code) sử dụng trong việc tạo
        // chữ ký số cho dữ liệu và xác thực dữ liệu trong các giao thức bảo mật.
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
