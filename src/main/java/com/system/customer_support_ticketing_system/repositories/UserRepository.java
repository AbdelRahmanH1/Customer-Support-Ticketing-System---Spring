package com.system.customer_support_ticketing_system.repositories;

import com.system.customer_support_ticketing_system.dtos.EmailCodeView;
import com.system.customer_support_ticketing_system.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsUserByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.id = :userId ")
    void updatePassword(@Param("userId") Long userId, @Param("password") String password);

    @Modifying
    @Query("UPDATE User u SET u.deleted = true WHERE u.id = :userId")
    void softDeleteUser(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE User u SET u.password = :password  WHERE u.email = :email")
    int resetPassword(@Param("email")String email, @Param("password")String password);

}
