package com.tms.dto;

import lombok.Data;

@Data
public class TopicAllResponseDto {



    private String topicName;

    private String topicDesc;

    private String topicCapacity;

    private Integer topicMaxNum;

    private Integer topicSelectedNum;

    private String isTop;

    private String isCheck;

    private Integer createUserId;

}
