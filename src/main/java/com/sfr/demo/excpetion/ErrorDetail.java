package com.sfr.demo.excpetion;

public class ErrorDetail {
    
    private String field;
    
    private String detail;
    
    public String getField() {
        return this.field;
    }
    
    public void setField(final String field) {
        this.field = field;
    }
    
    public String getDetail() {
        return this.detail;
    }
    
    public void setDetail(final String detail) {
        this.detail = detail;
    }
    
    public ErrorDetail(final String field, final String detail) {
        super();
        this.field = field;
        this.detail = detail;
    }
    
    
    
    
    
}
