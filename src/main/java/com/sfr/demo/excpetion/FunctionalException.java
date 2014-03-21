package com.sfr.demo.excpetion;

public class FunctionalException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    private ErrorDetail errorDetail;
    
    public ErrorDetail getErrorDetail() {
        return this.errorDetail;
    }
    
    public void setErrorDetail(final ErrorDetail errorDetail) {
        this.errorDetail = errorDetail;
    }
    
    public FunctionalException(final ErrorDetail errorDetail) {
        super();
        this.errorDetail = errorDetail;
    }
    
    
    
}
