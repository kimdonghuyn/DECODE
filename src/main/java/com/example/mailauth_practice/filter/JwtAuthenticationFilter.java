package com.example.mailauth_practice.filter;

import com.example.mailauth_practice.entity.UserEntity;
import com.example.mailauth_practice.provider.JwtProvider;
import com.example.mailauth_practice.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String token = parseBearerToken(request); // token 가져옴

            if (token == null) {  // 만약 token 값이 null 이면, (null이 나오는 경우 -> Authorization이 없거나 Bearer Token 형식이 아님)
                filterChain.doFilter(request, response); // 다음 filter로 넘김
                return;
            }

            String userId = jwtProvider.validate(token);
            if (userId == null) {
                filterChain.doFilter(request, response);
                return;
            }

            UserEntity userEntity = userRepository.findByUserId(userId);
            String role = userEntity.getRole(); // role: ROLE_USER, ROLE_ADMIN

            // ROLE_DEVELOPER, ROLE_MASTER .......
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role));

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext(); // bean에 등록할 빈 컨텍스트를 만듬

            AbstractAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userId, null, authorities);  // 아이디, 비밀번호, 권한
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            securityContext.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(securityContext);


        } catch (Exception exception) {
            exception.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        boolean hasAuthorization = StringUtils.hasText(authorization); // 텍스트가 하나라도 포함되어있으면 true 리턴 -> 빈값인지 아닌지 확인하는 용도
        if (!hasAuthorization) return null;

        boolean isBearer = authorization.startsWith("Bearer "); // 이 BearerToken이 Bearer 로 시작하는지 확인
        if (!isBearer) return null;

        String token = authorization.substring(7); // "Bearer " 이거 다음 인덱스가 7임 그 뒤에 있는 글자를 token으로 사용

        return token;
    }
}
