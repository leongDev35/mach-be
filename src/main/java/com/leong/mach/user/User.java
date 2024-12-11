package com.leong.mach.user;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leong.mach.mangaApp.chapter.Chapter;
import com.leong.mach.mangaApp.manga.Manga;
import com.leong.mach.role.Role;
import com.leong.mach.walletApp.wallet.Wallet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.FetchType.EAGER;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User implements 
UserDetails, 
Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY // ! auto increment
    )
    private Integer id;
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    @Column(unique = true)
    private String email;
    private String password;
    @Builder.Default
    private String avatarUrl = "/27f28108-8074-444b-9e0b-ffb525b0a5de.jpg.512.jpg";
    private boolean accountLocked;
    private boolean enabled;

    
    @ManyToMany(fetch = EAGER, cascade = CascadeType.MERGE) //! quan trọng
    @JoinTable(name = "user_role", // ! tên bảng
            joinColumns = @JoinColumn(name = "user_id"), // ! liên kết với khóa ngoại của bảng đầu
            inverseJoinColumns = @JoinColumn(name = "role_id") // ! liên kết với khóa ngoại của bảng chính thứ 2
    )
    // @JsonIgnore
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Wallet> wallets;

    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Manga> mangas;

    @JsonIgnore
    @OneToMany(mappedBy = "uploadByUser")
    private List<Chapter> chapters;

    @Override // ! của UserDetails
    // ! phương thức này để lấy các quyền của người dùng
    // ! mục đích trả về một collection chứa các vai trò (roles) của người dùng. Mỗi
    // !vai trò có một name
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream() // ! sử dụng Stream Api của Java để xử lý danh sách các roles. Biến roles thành
                          // một Stream để có thể xử lý tuần tư
                .map(r -> new SimpleGrantedAuthority(r.getName())) // ! mỗi r được chuyển thành một
                                                                   // SimpleGrantedAuthority với tên của role đó
                .collect(Collectors.toList()); // ! thu thập kết quả thành lại một LIST
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String fullName() {
        return getFirstname() + " " + getLastname();
    }

    // public String getAvatarUrl() {
    //     return avatarUrl;
    // }

    @Override
    public String getName() {
        return email;
    }

    public String getFullName() {
        return firstname + " " + lastname;
    }
}
