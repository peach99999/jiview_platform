package com.smaller.jiview.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


/**
 * @author oneway on 2017/5/12.
 */
@Slf4j
@Component
public class HttpUtil {
    public String postRequestResult(String url, String param) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> requestEntity = new HttpEntity<>(param, headers);

        ResponseEntity<String> postEntity = restTemplate.postForEntity(url, requestEntity, String.class, param);

        return postEntity.getBody();
    }

    public static CloseableHttpClient acceptsUntrustedCertsHttpClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        HttpClientBuilder b = HttpClientBuilder.create();

        // setup a Trust Strategy that allows all certificates.
        //
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                return true;
            }
        }).build();
        b.setSSLContext(sslContext);

        // don't check Hostnames, either.
        //      -- use SSLConnectionSocketFactory.getDefaultHostnameVerifier(), if you don't want to weaken
        HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;

        // here's the special part:
        //      -- need to create an SSL Socket Factory, to use our weakened "trust strategy";
        //      -- and create a Registry, to register it.
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslSocketFactory)
                .build();

        // now, we create connection-manager using our Registry.
        //      -- allows multi-threaded use
        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connMgr.setMaxTotal(200);
        connMgr.setDefaultMaxPerRoute(100);
        b.setConnectionManager(connMgr);

        return b.build();
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * <p>
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     * <p>
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     * <p>
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return
     */
    public static String getClientRealIP(HttpServletRequest request) {
        String ips = request.getHeader("x-forwarded-for");

        if (isIpInvaild(ips)) {
            ips = request.getHeader("Proxy-Client-IP");
        }
        if (isIpInvaild(ips)) {
            ips = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isIpInvaild(ips)) {
            ips = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (isIpInvaild(ips)) {
            ips = request.getHeader("HTTP_X_FORWARDED");
        }
        if (isIpInvaild(ips)) {
            ips = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
        }
        if (isIpInvaild(ips)) {
            ips = request.getHeader("HTTP_CLIENT_IP");
        }
        if (isIpInvaild(ips)) {
            ips = request.getHeader("HTTP_FORWARDED_FOR");
        }
        if (isIpInvaild(ips)) {
            ips = request.getHeader("HTTP_FORWARDED");
        }
        if (isIpInvaild(ips)) {
            ips = request.getHeader("HTTP_VIA");
        }
        if (isIpInvaild(ips)) {
            ips = request.getHeader("REMOTE_ADDR");
        }
        if (isIpInvaild(ips)) {
            ips = request.getRemoteAddr();
        }

        String ip = ips;
        if (StringUtils.isNotEmpty(ips) && ips.indexOf(',') != -1) {
            ip = ips.split(",")[0];
        }

        log.info("HttpRequestUtil→getClientRealIP→ips：" + ips);
        log.info("HttpRequestUtil→getClientRealIP→ip：" + ip);

        return ip;
    }

    private static Boolean isIpInvaild(String ip) {
        return ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown");
    }
}
