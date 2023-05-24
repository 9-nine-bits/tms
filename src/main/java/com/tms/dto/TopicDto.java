package com.tms.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDto extends BaseRowModel {

    @ExcelProperty(value = "选题名称", index = 0)
    private String topicName;
    @ExcelProperty(value = "选题描述", index = 1)
    private String topicDesc;
    @ExcelProperty(value = "容纳人数", index = 2)
    private String topicCapacity;
}
