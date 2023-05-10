package com.tms.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LessonStatusResponseDto {
    String Status;
    Date startTime;
    Date endTime;
}
