package vlad.kuchuk.taskmanagementsystem.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vlad.kuchuk.taskmanagementsystem.security.entity.UserEntity;
import vlad.kuchuk.taskmanagementsystem.security.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Transactional
    public void updateRefreshToken(Long userId, String refreshToken) {
        userRepository.updateRefreshToken(userId, refreshToken);
    }

    boolean isRefreshTokenValid(Long userId, String providedRefreshToken) {
        return userRepository.existsByIdAndRefreshToken(userId, providedRefreshToken);
    }
}
