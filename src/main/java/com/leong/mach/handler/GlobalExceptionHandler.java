package com.leong.mach.handler;

import jakarta.mail.MessagingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.leong.mach.exception.ActivationTokenException;
import com.leong.mach.exception.EmailAlreadyExistsException;
import com.leong.mach.exception.OperationNotPermittedException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import static com.leong.mach.handler.BusinessErrorCodes.ACCOUNT_DISABLED;
import static com.leong.mach.handler.BusinessErrorCodes.ACCOUNT_LOCKED;
import static com.leong.mach.handler.BusinessErrorCodes.BAD_CREDENTIALS;

@RestControllerAdvice // ! lớp này sẽ bắt và xử lý các ngoại lệ được ném ra trong toàn bộ ứng dụng,
                      // sau đó trả về phản hồi thích hợp dưới dạng JSON (hoặc XML nếu được cấu hình).
public class GlobalExceptionHandler { // ! xử lý Exception

        @ExceptionHandler(LockedException.class) // ! chỉ định phương thức này sẽ xử lý ngoại lệ LockedException
        // ! ResponseEntity<ExceptionResponse> có nghĩa là trả về 1 đối tượng
        // ResponseEntity của class ResponseEntity có tham số kiểu dữ liệu là
        // ExceptionResponse. Tham số kiểu dữ liệu này được sử dụng để chỉ định kiểu dữ
        // liệu cho các thuộc tính và phương thức của lớp.
        public ResponseEntity<ExceptionResponse> handleException(LockedException exp) {
                return ResponseEntity
                                .status(UNAUTHORIZED) // ! Trả về mã trạng thái HTTP 401 Unauthorized
                                .body( // ! Phần nội dung của phản hồi được tạo từ đối tượng ExceptionResponse
                                                ExceptionResponse.builder()
                                                                .businessErrorCode(ACCOUNT_LOCKED.getCode())
                                                                .businessErrorDescription(
                                                                                ACCOUNT_LOCKED.getDescription())
                                                                .error(exp.getMessage())
                                                                .build());
        }

        @ExceptionHandler(DisabledException.class)
        public ResponseEntity<ExceptionResponse> handleException(DisabledException exp) {
                return ResponseEntity
                                .status(UNAUTHORIZED)
                                .body(
                                                ExceptionResponse.builder()
                                                                .businessErrorCode(ACCOUNT_DISABLED.getCode())
                                                                .businessErrorDescription(
                                                                                ACCOUNT_DISABLED.getDescription())
                                                                .error(exp.getMessage())
                                                                .build());
        }

        @ExceptionHandler(BadCredentialsException.class) // ! xử lý BadCredentialsException
        public ResponseEntity<ExceptionResponse> handleException() {
                return ResponseEntity
                                .status(UNAUTHORIZED) // ! Để trả về STATUS CODE
                                .body(
                                                ExceptionResponse.builder()
                                                                .businessErrorCode(BAD_CREDENTIALS.getCode())
                                                                .businessErrorDescription(
                                                                                BAD_CREDENTIALS.getDescription())
                                                                .error("Email and / or Password is incorrect")
                                                                .build());
        }

        @ExceptionHandler(MessagingException.class)
        public ResponseEntity<ExceptionResponse> handleException(MessagingException exp) {
                return ResponseEntity
                                .status(INTERNAL_SERVER_ERROR)
                                .body(
                                                ExceptionResponse.builder()
                                                                .error(exp.getMessage())
                                                                .build());
        }

        @ExceptionHandler(ActivationTokenException.class)
        public ResponseEntity<ExceptionResponse> handleException(ActivationTokenException exp) {
                return ResponseEntity
                                .status(BAD_REQUEST)
                                .body(
                                                ExceptionResponse.builder()
                                                                .error(exp.getMessage())
                                                                .build());
        }

        @ExceptionHandler(OperationNotPermittedException.class)
        public ResponseEntity<ExceptionResponse> handleException(OperationNotPermittedException exp) {
                return ResponseEntity
                                .status(BAD_REQUEST)
                                .body(
                                                ExceptionResponse.builder()
                                                                .error(exp.getMessage())
                                                                .build());
        }

        @ExceptionHandler(MethodArgumentNotValidException.class) // ! là một ngoại lệ xảy ra khi một hoặc nhiều đối số
                                                                 // của phương thức không thỏa mãn các ràng buộc xác
                                                                 // thực đã được định nghĩa, chẳng hạn như @NotNull,
                                                                 // @Size, @Min, @Max, hoặc @Valid.
        public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(
                        MethodArgumentNotValidException exp) {
                Set<String> errors = new HashSet<>(); // ! chứa các thông báo lỗi từ quá trình xác thực. Set được sử
                                                      // dụng để đảm bảo rằng mỗi thông báo lỗi chỉ xuất hiện một lần
                                                      // (không trùng lặp).
                exp.getBindingResult().getAllErrors() // ! exp.getBindingResult().getAllErrors() trả về danh sách tất cả
                                                      // các lỗi xác thực xảy ra.
                                .forEach(error -> { // ! Sử dụng phương thức forEach để duyệt qua tất cả các lỗi và
                                                    // trích xuất thông báo lỗi (errorMessage) từ mỗi lỗi.
                                        // var fieldName = ((FieldError) error).getField();
                                        var errorMessage = error.getDefaultMessage();
                                        errors.add(errorMessage); // ! mỗi thông báo lỗi sau đó được thêm vào
                                                                  // Set<String> errors
                                });

                return ResponseEntity
                                .status(BAD_REQUEST)
                                .body(
                                                ExceptionResponse.builder()
                                                                .validationErrors(errors)
                                                                .build());
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ExceptionResponse> handleException(Exception exp) {
                exp.printStackTrace();
                return ResponseEntity
                                .status(INTERNAL_SERVER_ERROR)
                                .body(
                                                ExceptionResponse.builder()
                                                                .businessErrorDescription(
                                                                                "Internal error, please contact the admin")
                                                                .error(exp.getMessage())
                                                                .build());
        }

        @ExceptionHandler(EmailAlreadyExistsException.class)
        public ResponseEntity<ExceptionResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException exp) {
                return ResponseEntity
                .status(CONFLICT)
                .body(
                                ExceptionResponse.builder()
                                                .businessErrorDescription(
                                                                "Email already in use")
                                                .error(exp.getMessage())
                                                .build());
        }
}
