package com.tms.pagedto;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    List<T> data;
    int pageNum;
    int pageSize;
    int total;

}
