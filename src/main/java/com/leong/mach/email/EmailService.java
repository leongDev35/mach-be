package com.leong.mach.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service //! đánh dấu là Service
@Slf4j //! sử dụng để logging. Nó tự động khởi tạo một instance của org.slf4j.Logger dựa trên tên lớp, trong trường hợp này là EmailService.
@RequiredArgsConstructor //! tạo constructor với tham số cho các final field đã được đánh dấu với @NonNull hoặc final.
public class EmailService {
    private final JavaMailSender mailSender;  //!  Đối tượng này được Spring inject vào EmailService để gửi email. 
    private final SpringTemplateEngine templateEngine; //!   Đối tượng này được sử dụng để xử lý các template email thymeleaf.
 
    @Async //! Annotation này cho biết rằng phương thức sẽ được gọi bất đồng bộ, điều này giúp ứng dụng không bị chặn đứng khi gửi email, và cho phép các hoạt động khác tiếp tục thực thi trong khi email đang được gửi đi.
    public void sendEmail( //! phương thức để gửi email với các trường như dưới
            String to, //! địa chỉ email người nhân
            String username,
            EmailTemplateName emailTemplate, //! mẫu email, tập hợp enum
            String confirmationUrl, //! các thông tin cần thiết để người dùng có thể xác nhận
            String activationCode, //! mã kích hoạt
            String subject //! chủ đề của email
    ) throws MessagingException {

        String templateName; 
        if (emailTemplate == null) { 
            templateName = "confirm-email"; //! nếu không có email template được chỉ định thì tên Template là confirm-email
        } else {
            templateName = emailTemplate.name(); //! nếu có lấy name của template
        }
        MimeMessage mimeMessage = mailSender.createMimeMessage(); //! đối tượng này đại diện cho email cần gửi
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED,
                UTF_8.name()
        );
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("confirmationUrl", confirmationUrl);
        properties.put("activation_code", activationCode);

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom("duynguyen.3595@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(templateName, context);

        helper.setText(template, true);

        mailSender.send(mimeMessage);
    }
}
