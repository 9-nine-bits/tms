package com.tms.dto;

import lombok.Data;

@Data
public class StageScoreInsertRequestDto {


    private String account;

    private String username;

    private String curStatusScore;

    private String lessonStatus;
}
