package com.tms.dto;

import lombok.Data;

import java.util.Queue;

@Data
public class LoginRequestDto {
    private String account;
    private String password;
}
