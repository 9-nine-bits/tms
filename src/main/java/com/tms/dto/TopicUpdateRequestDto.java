package com.tms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
