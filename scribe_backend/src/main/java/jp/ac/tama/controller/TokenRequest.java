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
public class TokenRequest {
    private String state;
    private String code;

}
