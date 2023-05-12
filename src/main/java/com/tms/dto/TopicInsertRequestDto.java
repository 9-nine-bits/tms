package com.tms.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class TopicInsertRequestDto {

    @NonNull
    private String topicName;
    @NonNull
    private String topicDesc;
    @NonNull
    private String topicCapacity;


}
