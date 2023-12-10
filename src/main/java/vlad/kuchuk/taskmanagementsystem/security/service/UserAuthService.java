package vlad.kuchuk.taskmanagementsystem.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vlad.kuchuk.taskmanagementsystem.security.entity.UserEntity;
import vlad.kuchuk.taskmanagementsystem.security.repository.AuthenticationRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAuthService {

    private final AuthenticationRepository authenticationRepository;

    public Optional<UserEntity> getUserById(Long id) {
        return authenticationRepository.findById(id);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return authenticationRepository.findByEmail(email);
    }

    @Transactional
    public UserEntity saveUser(UserEntity user) {
        return authenticationRepository.save(user);
    }

    @Transactional
    public void updateRefreshToken(Long userId, String refreshToken) {
        authenticationRepository.updateRefreshToken(userId, refreshToken);
    }

    boolean isRefreshTokenValid(Long userId, String providedRefreshToken) {
        return authenticationRepository.existsByIdAndRefreshToken(userId, providedRefreshToken);
    }
}
