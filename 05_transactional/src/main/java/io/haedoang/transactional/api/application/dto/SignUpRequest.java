package io.haedoang.transactional.api.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * author : haedoang
 * date : 2022-08-23
 * description :
 */
@Data
@AllArgsConstructor
public class SignUpRequest {
    private String username;
    private String password;
}
