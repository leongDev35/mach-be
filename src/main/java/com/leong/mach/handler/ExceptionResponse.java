package com.leong.mach.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY) //! chỉ định rằng các trường trong JSON kết quả sẽ chỉ được bao gồm nếu chúng không trống (null hoặc rỗng). Điều này giúp tránh việc gửi dữ liệu không cần thiết trong phản hồi.
public class ExceptionResponse { //! dùng để chuẩn hóa cấu trúc phản hồi khii xảy ra ngoại lệ

    private Integer businessErrorCode;
    private String businessErrorDescription;
    private String error;
    private Set<String> validationErrors;
    private Map<String, String> errors;
}
