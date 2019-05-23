package com.smaller.jiview.core.config.security;

import com.smaller.jiview.core.constant.JwtConstants;
import com.smaller.jiview.core.constant.RedisKeyConstant;
import com.smaller.jiview.core.manager.RedisManager;
import com.smaller.jiview.core.pojo.dto.JwtDTO;
import com.smaller.jiview.core.pojo.dto.LoginUserDTO;
import com.smaller.jiview.core.properties.JwtProperties;
import com.smaller.jiview.core.util.CommonUtil;
import com.smaller.jiview.core.util.HttpUtil;
import com.smaller.jiview.core.util.SecurityUtil;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;

/**
 * 构造及解析jwt的工具类
 */
@Slf4j
@Component
public class JwtHelper {
    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RedisManager redisManager;

    /**
     * 解析Header
     *
     * @return Header
     */
    public Header parseHeader() {
        Header header = null;

        final String headerKey = jwtProperties.getHeaderKey();
        final String tokenType = jwtProperties.getTokenType();

        String auth = request.getHeader(headerKey);

        if (StringUtils.isEmpty(auth)) {
            return null;
        }

        String tokenWithoutType = auth.substring(tokenType.length() + 1, auth.length());
        Jws<Claims> jws = parseJWT(tokenWithoutType);

        if (jws != null) {
            header = jws.getHeader();
        }

        return header;
    }

    /**
     * 获取加密后的SignKey
     *
     * @return Key
     */
    private Key getSignKey() {
        String signKey = jwtProperties.getSignKey();
        String signKeyBase64 = SecurityUtil.base64Encode(signKey);
        byte[] signKeyBase64Bytes = DatatypeConverter.parseBase64Binary(signKeyBase64);
        return new SecretKeySpec(signKeyBase64Bytes, signatureAlgorithm.getJcaName());
    }

    /**
     * 计算token过期日期
     *
     * @return Date
     */
    private Date calcExpirationDate() {
        return new Date(System.currentTimeMillis() + jwtProperties.getExpiresInMinute() * 1000 * 60);
    }

    /**
     * 解析token
     *
     * @param token
     * @return Jws<Claims>
     */
    private Jws<Claims> parseJWT(String token) {
        try {
            return Jwts.parser().setSigningKey(this.getSignKey())
                    .parseClaimsJws(token);
        } catch (SignatureException e) {
            log.error("签名不正确");
            return null;
        } catch (ExpiredJwtException e) {
            log.error("token过期");
            return null;
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }

    private JwtUser getJwtUser() {
        return CommonUtil.getJwtUser(request);
    }

    /**
     * 获取当前登录者信息
     *
     * @return LoginUserDTO
     */
    public LoginUserDTO getLoginUserDTO() {
        LoginUserDTO loginUserDTO = new LoginUserDTO();

        JwtUser jwtUser = this.getJwtUser();

        if(jwtUser == null){
            return loginUserDTO;
        }

        loginUserDTO.setLoginUserPkid(Long.parseLong(jwtUser.getId()));
        loginUserDTO.setLoginUserName(jwtUser.getUsername());
        loginUserDTO.setUserType(jwtUser.getUserType());
        loginUserDTO.setIp(HttpUtil.getClientRealIP(request));
        loginUserDTO.setRequestUri(request.getRequestURI());

        return loginUserDTO;
    }

    /**
     * @param jwtDTO
     * @return
     */
    public String createAndSaveToken(JwtDTO jwtDTO) {
        final String headerKey = jwtProperties.getHeaderKey();
        final String tokenType = jwtProperties.getTokenType();

        String token = createToken(jwtDTO);
        String tokenWithType = String.format("%s %s", tokenType, token);

        HashMap<String, String> map = new HashMap<>();
        //格式化为Bearer X.X.X
        map.put(headerKey, tokenWithType);

        // 新生成的token存入redis
        String key = this.getRedisKeyForToken();
        String hashKey = jwtDTO.getLoginPkid().toString();
        redisManager.saveByHash(key, hashKey, token);

        return tokenWithType;
    }

    /**
     * 生成token
     *
     * @param jwtDTO
     * @return String
     */
    private String createToken(JwtDTO jwtDTO) {
        long expMillis = 0;

        Date expirationDate = calcExpirationDate();
        expMillis = expirationDate.getTime();

        return createToken(jwtDTO, expMillis);
    }

    /**
     * 生成token,并指定有效期
     *
     * @param jwtDTO
     * @param expMillis
     * @return
     */
    private String createToken(JwtDTO jwtDTO, long expMillis) {
        String audience = jwtProperties.getAudience();
        String issuer = jwtProperties.getIssuer();

        Long userPkid = jwtDTO.getLoginPkid();
        String userLogin = jwtDTO.getUserLogin();

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam(JwtConstants.LOGIN_PKID_KEY, userPkid)
                // 1.iss: 该JWT的签发者
                .setIssuer(issuer)
                // 2.sub: 该JWT所面向的用户
                .setSubject(userLogin)
                // 3.aud: 接收该JWT的一方
                .setAudience(audience)
                // 5.iat(issued at): 在什么时候签发的
                .setIssuedAt(new Date())
                .signWith(signatureAlgorithm, this.getSignKey());

        Date now = new Date(System.currentTimeMillis());
        Date exp = new Date(expMillis);

        // 设置Token的过期时间 & 生效时间(生效时间提前一天，防止出现时区问题导致生成的token无法登陆)
        builder.setExpiration(exp).setNotBefore(DateUtils.addDays(now, -1));

        //生成JWT
        return builder.compact();
    }

    /**
     * 从token中获取userName
     *
     * @param token
     * @return String
     */
    public String getUsernameFromToken(String token) {
        String username = "";
        try {
            Claims claimsFromToken = getClaimsFromToken(token);

            if (claimsFromToken != null) {
                username = claimsFromToken.getSubject();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return username;
    }

    /**
     * 从token中获取loginPkid
     *
     * @param token
     * @return String
     */
    private String getLoginPkidFromToken(String token) {
        String loginPkid = "";
        try {
            Header headerFromToken = getHeaderFromToken(token);

            if (headerFromToken != null) {
                loginPkid = headerFromToken.get(JwtConstants.LOGIN_PKID_KEY).toString();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return loginPkid;
    }

    /**
     * 从token中获取loginUUID
     *
     * @param token
     * @return String
     */
    private String getLoginUUIDFromToken(String token) {
        String loginPkid = "";
        try {
            Header header = getHeaderFromToken(token);

            if (header != null) {
                loginPkid = (String) header.get(JwtConstants.LOGIN_PKID_KEY);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return loginPkid;
    }

    /**
     * 从token中获取过期日期
     *
     * @param token
     * @return Date
     */
    private Date getExpirationDateFromToken(String token) {
        Date expirationDateFromToken = null;

        try {
            Claims claimsFromToken = getClaimsFromToken(token);

            if (claimsFromToken != null) {
                expirationDateFromToken = claimsFromToken.getExpiration();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return expirationDateFromToken;
    }

    /**
     * 从token中获取Header
     *
     * @param token
     * @return Header
     */
    private Header getHeaderFromToken(String token) {
        Header headerFromToken = null;

        try {
            Jws<Claims> jws = this.parseJWT(token);

            if (jws != null) {
                headerFromToken = jws.getHeader();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return headerFromToken;
    }

    /**
     * 从token中获取Claims
     *
     * @param token
     * @return Claims
     */
    private Claims getClaimsFromToken(String token) {
        Claims claimsFromToken = null;

        try {
            claimsFromToken = Jwts.parser()
                    .setSigningKey(this.getSignKey())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claimsFromToken = null;
            log.error(e.getMessage());
        }

        return claimsFromToken;
    }

    /**
     * 检查token是否过期
     *
     * @param token
     * @return Boolean
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return new Date().after(expiration);
    }

    /**
     * 从redis取出当前登录用户的最新token
     *
     * @return String
     */
    private String getRedisKeyForToken() {
        String issuer = jwtProperties.getIssuer();
        String redisKeyForTokenPrefix = RedisKeyConstant.TOKEN_ISSUED_BY;

        if (StringUtils.isNotEmpty(issuer)) {
            issuer = issuer.toUpperCase();
        }

        return redisKeyForTokenPrefix + issuer;
    }

    /**
     * 检查token与redis中所存储的token是否一致
     *
     * @param token
     * @param jwtUser
     * @return Boolean
     */
    private Boolean isTokenEqualsRedis(String token, JwtUser jwtUser) {
        String key = this.getRedisKeyForToken();
        String hashKey = jwtUser.getId();
        String tokenFromRedis = redisManager.getByHash(key, hashKey);

        if (StringUtils.isNotEmpty(token)) {
            token = token.trim();
        }

        if (StringUtils.isNotEmpty(tokenFromRedis)) {
            tokenFromRedis = tokenFromRedis.trim();
        }

        return token.equals(tokenFromRedis);
    }

    /**
     * 检查token中的userPkid是否正确
     *
     * @param token
     * @param jwtUser
     * @return Boolean
     */
    private Boolean isUserPkidCorrect(String token, JwtUser jwtUser) {
        final String loginPkid = getLoginPkidFromToken(token);
        return StringUtils.isNotEmpty(loginPkid) && loginPkid.equals(jwtUser.getId());
    }

    /**
     * 检查token中的userUuid是否正确
     *
     * @param token
     * @param jwtUser
     * @return Boolean
     */
    private Boolean isUserUuidCorrect(String token, JwtUser jwtUser) {
        final String loginUUID = getLoginUUIDFromToken(token);
        return StringUtils.isNotEmpty(loginUUID) && loginUUID.equals(jwtUser.getId());
    }

    /**
     * 验证token有效性
     *
     * @param token
     * @param userDetails
     * @return Boolean
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser jwtUser = (JwtUser) userDetails;

        return isTokenEqualsRedis(token, jwtUser)
                && !isTokenExpired(token)
                && isUserPkidCorrect(token, jwtUser);
//                && isUserUuidCorrect(token, jwtUser);
    }
}
