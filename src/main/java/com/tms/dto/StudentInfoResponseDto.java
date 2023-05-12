package com.tms.dto;

import com.tms.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentInfoResponseDto {
    private String account;

    private String username;

    private String phone;

    private String gender;

    private String password;

    private String question2;

    private String question1;

    private String answer1;

    private String answer2;
    public StudentInfoResponseDto(User u){
        this.account=u.getAccount();
        this.username=u.getUsername();
        this.phone=u.getPhone();
        this.password=u.getPassword();
        this.question1=u.getQuestion1();
        this.question2=u.getQuestion2();
        this.answer1=u.getAnswer1();
        this.answer2=u.getAnswer2();

    }

}
