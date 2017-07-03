package jp.ac.tama.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by kajiwarayutaka on 2017/07/04.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessToken {
    private String accessToken;
    private Integer expiresIn;
    private String refreshToken;
    private String scope;
    private String tokenType;
    private String rawResponse;

}
