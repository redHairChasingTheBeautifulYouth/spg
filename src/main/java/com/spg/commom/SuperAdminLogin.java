package com.spg.commom;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author trevor
 * @date 03/27/19 12:43
 */
@Data
@ApiModel
public class SuperAdminLogin {

    @NotBlank(message = "不能为空")
    @ApiModelProperty("账户名")
    private String account;

    @NotBlank(message = "不能为空")
    @NotBlank
    @ApiModelProperty("密码")
    private String passward;
}
