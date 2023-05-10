package com.tms.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class StudentPageDto implements Serializable {
    private int account;
    private String username;
    private String phone;
    private String gender;

}
