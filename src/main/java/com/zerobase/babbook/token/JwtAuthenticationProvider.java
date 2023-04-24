package com.zerobase.babbook.token;

import com.zerobase.babbook.domain.common.UserRole;
import com.zerobase.babbook.domain.dto.UserDto;
import com.zerobase.babbook.util.Aes256Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Objects;

/**
 * JwtAuthenticationProvider 클래스는 JWT 토큰을 생성하고 유효성 검증,
 * 토큰에서 유저 정보를 추출하는 기능을 제공하는 클래스
 *
 *메서드
 * createToken
 * 파라미터로 전달된 이메일, 아이디, 권한 정보를 이용하여 JWT 토큰을 생성
 * 생성된 토큰에 payload(Claim) 로 이메일, 아이디, 권한 정보 포함
 *
 * validateToken
 * 전달된 JWT 토큰이 유효한지 검증
 * 유효하지 않은 경우 false , 유효한 경우 true 반환.
 *
 * getUserDto
 * 전달된 JWT 토큰에서 유저 정보를 추출하여 UserDto 객체로 반환
 * 추출된 정보는 이메일, 아이디 추출시에는 복호화해 정보 추출
 */
public class JwtAuthenticationProvider {

    private final String secretKey = "secretKey";
    private final long tokenValidTime = 1000L * 60 * 60 * 24; //인증 만료 하루

    public String createToken(String email, Long id, UserRole userRole) {
        Claims claims = Jwts.claims()
            .setSubject(Aes256Utils.encrypt(email))
            .setId(Aes256Utils.encrypt(id.toString()));
        claims.put("roles", userRole);
        Date now = new Date();
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + tokenValidTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwtToken);
            return !claimsJws.getBody()
                .getExpiration()
                .before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public UserDto getUserDto(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();
        return new UserDto(
            Long.valueOf(Objects.requireNonNull(Aes256Utils.decrypt(claims.getId()))),
            Aes256Utils.decrypt(claims.getSubject())
        );
    }
}
