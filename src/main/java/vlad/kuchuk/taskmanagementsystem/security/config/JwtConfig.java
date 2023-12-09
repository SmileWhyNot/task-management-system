package vlad.kuchuk.taskmanagementsystem.security.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import vlad.kuchuk.taskmanagementsystem.appYamlReader.YamlPropertySourceFactory;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "jwt")
@PropertySource(value = "classpath:defaults/jwt.yml", factory = YamlPropertySourceFactory.class)
public class JwtConfig {
    private String secretKey;
    private long refreshTokenExpirationMs;
    private long tokenExpirationMs;
}

