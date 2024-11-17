package com.leong.mach.mangaApp.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leong.mach.user.UserRepository;



//!  Đánh dấu đây là một Spring Service Bean, nói cho Spring biết rằng nó cần quản lý và inject bean này vào các component khác trong ứng dụng.
@Service 
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository; //! cần Bean này để tương tác với DB
    @Override
    @Transactional //! Đánh dấu phương thức này để Spring quản lý transaction. Trong trường hợp này, nó đảm bảo rằng các hoạt động truy vấn dữ liệu sẽ được thực hiện trong một transaction đơn lẻ.
    //! phương thức chính của UserDetailService
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return repository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
