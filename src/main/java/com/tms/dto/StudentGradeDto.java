package com.tms.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentGradeDto extends BaseRowModel {
    @ExcelProperty(value = "学号", index = 0)
    private String account;
    @ExcelProperty(value = "姓名", index = 1)
    private String username;
    @ExcelProperty(value = "需求分析", index = 2)
    private String a;
    @ExcelProperty(value = "原型设计", index = 3)
    private String b;
    @ExcelProperty(value = "数据库设计", index = 4)
    private String c;
    @ExcelProperty(value = "概要设计", index = 5)
    private String d;
    @ExcelProperty(value = "编码", index = 6)
    private String e;
    @ExcelProperty(value = "测试", index = 7)
    private String f;
    @ExcelProperty(value = "总分", index = 8)
    private String score;
}
