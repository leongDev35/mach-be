package com.leong.mach.config;

//! ApplicationAuditAware là một lớp tùy chỉnh triển khai giao diện AuditorAware của Spring Data.
//! Nó cung cấp thông tin về người dùng hiện tại đang thực hiện thao tác nào đó trên hệ thống, dựa trên thông tin bảo mật từ SecurityContextHolder.
//! Điều này rất hữu ích cho việc theo dõi ai là người đã tạo hoặc cập nhật một bản ghi trong cơ sở dữ liệu, giúp tăng cường tính bảo mật và khả năng theo dõi trong các ứng dụng Spring.

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.leong.mach.user.User;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<Integer> {
    @Override
    public Optional<Integer> getCurrentAuditor() { //! trả về thông tin về auditor hiện tại, kiểu dữ liệu Integer chỉ ra rằng thông tin về auditor sẽ được trả về dưới dạng một Integer, thông thường là ID của người dùng.
        //! sử dụng SecurityContextHolder để truy xuất đối tượng Authentication hiện tại từ ngữ cảnh bảo mật. Authentication chứa thông tin về người dùng hiện tại.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //! kiểm tra tính hợp lệ của thông tin xác thực
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty(); //! không có người dùng nào được xác định là auditor.
        }

        User userPrincipal = (User) authentication.getPrincipal(); //! Dòng này ép kiểu đối tượng principal về kiểu User.

        return Optional.ofNullable(userPrincipal.getId()); //! Trả về đối tượng Optional chứa giá trị của Id, nếu là null thì trả về rỗng
    }
}
