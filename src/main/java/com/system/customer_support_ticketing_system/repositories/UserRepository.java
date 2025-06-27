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
    @Query("UPDATE User u SET u.passwordResetCode= :code WHERE u.email = :email")
    void updatePasswordResetCode(@Param("email") String email, @Param("code") String code);

    @Query("SELECT u.passwordResetCode FROM User u WHERE u.email = :email")
    Optional<String> findResetCodeByEmail(@Param("email")String email);

    @Modifying
    @Query("UPDATE User u SET u.password = :password , u.passwordResetCode= NULL WHERE u.email = :email")
    int resetPassword(@Param("email")String email, @Param("password")String password);

    @Query("SELECT u.email AS email, u.passwordResetCode AS passwordResetCode FROM User u WHERE u.email = :email")
    Optional<EmailCodeView> findEmailAndCodeByEmail(@Param("email") String email);
}
