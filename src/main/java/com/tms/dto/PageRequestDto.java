package com.tms.dto;

import lombok.Data;

@Data
public class PageRequestDto {
    /*分页参数*/
    private Integer pageNum = 1;//当前页
    private Integer pageSize = 10;//每页显示的记录数量
}
