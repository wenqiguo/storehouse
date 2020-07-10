package com.nylg.gwq.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataGrideView {

    private Integer code=0;
    private String msg="";
    private Long count = 1L;
    private Object data;



    public DataGrideView(Object data) {
        this.data = data;
    }

    public DataGrideView(Long count, Object data) {
        this.count = count;
        this.data = data;
    }
}
