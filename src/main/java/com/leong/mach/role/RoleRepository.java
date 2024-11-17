package com.leong.mach.role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String role);  //! trả về 1 đối tượng Optional có thể chứa giá trị Role
}
