package com.leong.mach.email;


import lombok.Getter;

@Getter //! Phương thức getName() trả về giá trị chuỗi liên kết với hằng số enum. Điều này cho phép bạn truy cập giá trị của name từ bên ngoài.
//! EmailTemplateName là tên của enum này
public enum EmailTemplateName { //! enum là một loại dữ liệu định nghĩa một tập hợp các hằng số có tên


    //! Đây là một hằng số (constant) của enum EmailTemplateName.
//! ACTIVATE_ACCOUNT là tên của hằng số này.
//!"activate_account" là giá trị chuỗi liên kết với hằng số 
    ACTIVATE_ACCOUNT("activate_account");  

    private final String name; //! Biến thành viên name được sử dụng để lưu trữ giá trị chuỗi được liên kết với mỗi hằng số enum. Nó được đánh dấu là final, nghĩa là giá trị của nó không thể thay đổi sau khi đã được gán.

    EmailTemplateName(String name) { //! Đây là một hàm khởi tạo cho enum. Hàm này nhận một đối số là một chuỗi (name) và gán nó cho biến thành viên name.
        this.name = name;
    }
}
