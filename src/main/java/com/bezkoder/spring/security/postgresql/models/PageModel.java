package com.bezkoder.spring.security.postgresql.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageModel {
    private Integer pageNumber;
    private Integer totalElements;
    private Integer offset;
    private Integer size;
    private Object data;
}
