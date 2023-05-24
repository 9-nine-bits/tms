package com.tms.dto;

import com.alibaba.excel.annotation.ExcelProperty;

import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends BaseRowModel {
    @ExcelProperty(value = "学号", index = 0)
    private String account;
    @ExcelProperty(value = "姓名", index = 1)
    private String username;
}
