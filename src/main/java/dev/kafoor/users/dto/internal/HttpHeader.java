package dev.kafoor.users.dto.internal;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class HttpHeader {
    public final String ip;
    public final String userAgent;
}
