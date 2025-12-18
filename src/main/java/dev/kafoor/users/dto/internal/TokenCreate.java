package dev.kafoor.users.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenCreate {
    private String refresh;
    private String ip;
    private String userAgent;

    @Override
    public String toString(){
        return "TokenCreate:" +
                "\n\trefresh:   " + refresh +
                "\n\tip:        " + ip +
                "\n\tuserAgent: " + userAgent;
    }
}
