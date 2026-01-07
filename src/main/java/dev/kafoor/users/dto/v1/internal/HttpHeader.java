package dev.kafoor.users.dto.v1.internal;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class HttpHeader {
    public final String ip;
    public final String userAgent;

    @Override
    public String toString(){
        return "HttpHeader:" +
                "\n\tip:        " + ip +
                "\n\tuserAgent: " + userAgent;
    }
}
