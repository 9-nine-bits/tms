package com.tms.dto;

import lombok.Data;

@Data
public class ModifyPwdRequest {
    String account;
    String password;
}
