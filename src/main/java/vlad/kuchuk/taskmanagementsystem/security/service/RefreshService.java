package vlad.kuchuk.taskmanagementsystem.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vlad.kuchuk.taskmanagementsystem.security.repository.AuthenticationRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshService {

    private final AuthenticationRepository authenticationRepository;

    @Transactional
    public void updateRefreshToken(Long userId, String refreshToken) {
        authenticationRepository.updateRefreshToken(userId, refreshToken);
    }

    boolean isRefreshTokenValid(Long userId, String providedRefreshToken) {
        return authenticationRepository.existsByIdAndRefreshToken(userId, providedRefreshToken);
    }
}
