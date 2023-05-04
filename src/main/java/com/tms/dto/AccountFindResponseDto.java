package com.tms.dto;

import com.tms.entity.User;
import lombok.Data;

@Data
public class AccountFindResponseDto {
    String account;
    String password;
    String question1;
    String question2;
    String answer1;
    String answer2;

    public AccountFindResponseDto(User u) {
        this.question1 = u.getQuestion1();
        this.question2 = u.getQuestion2();
        this.account=u.getAccount();
    }

    public AccountFindResponseDto() {
    }
}
