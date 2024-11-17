package com.leong.mach.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {
    
  
    private final UserDetailsService userDetailsService;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider; //! sau khi set UserDetailSevice và PasswordEncoder thì coi như Provider đã hoàn thành
    }

    @Bean //! tùy chỉnh AuthenticationManager bằng AuthenticationConfiguration
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); //! config.getAuthenticationManager() là phương thức lấy ra AuthenticationManager đã được cấu hình từ AuthenticationConfiguration.
    }

    @Bean //! password sẽ được mã hóa theo kiểu BCrypt
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //! trả về ID của user hiện tại thực hiện thao tác (create,modify) trên hệ thống
    @Bean
    public AuditorAware<Integer> auditorAware() {
        return new ApplicationAuditAware();
    }

    @Bean
    public CorsFilter corsFilter() {
        //! là một lớp cơ sở để định nghĩa các cấu hình CORS cho các URL cụ thể
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); 
        //! đối tượng này lưu trữ các cấu hình liên quan đến CORS
        final CorsConfiguration config = new CorsConfiguration(); 
        config.setAllowCredentials(true); //! Cho phép gửi thông tin xác thực (như cookies hoặc HTTP authentication) trong yêu cầu CORS.
        //! Chỉ định các nguồn (origin) được phép gửi yêu cầu đến server. Ở đây, chỉ có các yêu cầu từ http://localhost:4200 (thường là ứng dụng Angular chạy cục bộ) được phép.
        //! Nếu muốn cho phép nhiều nguồn, có thể sử dụng config.setAllowedOrigins(Arrays.asList("http://origin1.com", "http://origin2.com"));
        config.setAllowedOrigins(Collections.singletonList("http://localhost:3000")); 
        //! Xác định các header nào từ phía client được phép gửi trong yêu cầu.
        //! Ở đây, chỉ các header như Origin, Content-Type, Accept, Authorization được phép
        config.setAllowedHeaders(Arrays.asList(
                HttpHeaders.ORIGIN,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT,
                HttpHeaders.AUTHORIZATION
        ));
        //! chỉ có 5 phương thức được cho phép
        config.setAllowedMethods(Arrays.asList(
                "GET",
                "POST",
                "DELETE",
                "PUT",
                "PATCH"
        ));
        source.registerCorsConfiguration("/**", config); //! đăng ký cấu hình CORS cho toàn bộ các URL (/**).
        return new CorsFilter(source);

    }
}
