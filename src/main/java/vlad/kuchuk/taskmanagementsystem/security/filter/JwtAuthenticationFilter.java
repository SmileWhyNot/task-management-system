package vlad.kuchuk.taskmanagementsystem.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import vlad.kuchuk.taskmanagementsystem.commonExceptionUtil.ApiError;
import vlad.kuchuk.taskmanagementsystem.security.entity.CustomUserDetails;
import vlad.kuchuk.taskmanagementsystem.security.service.JwtService;
import vlad.kuchuk.taskmanagementsystem.user.dto.UserDto;
import vlad.kuchuk.taskmanagementsystem.user.service.UserService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final Long userId;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = authHeader.substring(7);
        try {
            userId = jwtService.extractId(jwt);
        } catch (ExpiredJwtException ex) {
            ApiError apiError = new ApiError("UNAUTHORIZED", "Your JWT is expired");
            String jsonResponse = new ObjectMapper().writeValueAsString(apiError);

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter()
                    .write(jsonResponse);
            return;
        }

        if (userId != null && SecurityContextHolder.getContext()
                                                   .getAuthentication() == null) {
            UserDto user = userService.getById(userId);
            CustomUserDetails userDetails = new CustomUserDetails(user);
            if (jwtService.isTokenValid(jwt, user)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext()
                                     .setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
