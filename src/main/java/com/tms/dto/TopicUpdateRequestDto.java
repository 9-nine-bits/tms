package com.tms.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class TopicUpdateRequestDto {
    @NonNull
    private String oldName;
    @NonNull
    private String topicName;
    @NonNull
    private String topicDesc;
    @NonNull
    private String topicCapacity;
    @NonNull
    private Integer topicMaxNum;
    @NonNull
    private String isTop;


}
