package dev.kafoor.users.util;

import dev.kafoor.users.dto.internal.HttpHeader;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class HttpHeaderExtractor {
    public static HttpHeader extract(HttpServletRequest http) {
        return HttpHeader.builder()
                .ip(http.getRemoteAddr())
                .userAgent(http.getHeader("User-Agent"))
                .build();
    }
}
