package vlad.kuchuk.taskmanagementsystem.security.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vlad.kuchuk.taskmanagementsystem.security.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    @Modifying
    @Query("UPDATE UserEntity u SET u.refreshToken = :refreshToken WHERE u.id = :userId")
    void updateRefreshToken(@Param("userId") Long userId, @Param("refreshToken") String refreshToken);

    @Query("SELECT CASE WHEN u.refreshToken = :providedRefreshToken " +
            "THEN true ELSE false END " +
            "FROM UserEntity u WHERE u.id = :userId")
    boolean isRefreshTokenValid(@Param("userId") Long userId, @Param("providedRefreshToken") String providedRefreshToken);

    boolean existsByIdAndRefreshToken(Long userId, String providedRefreshToken);
}
