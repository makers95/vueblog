package com.markerhub.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Description
 * @Param $
 * @return $
 **/
@Data
public class LoginDto implements Serializable {

    @NotBlank(message = "帳號不為空")
    private String username;

    @NotBlank(message = "密碼不為空")
    private String password;
}
