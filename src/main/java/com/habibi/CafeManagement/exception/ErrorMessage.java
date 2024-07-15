package com.habibi.CafeManagement.exception;

import java.util.Date;

public class ErrorMessage {
    private String message;
    private Date date;

    private String errorCause;

    public ErrorMessage() {
    }

    public ErrorMessage(String message, Date date, String errorCause) {
        this.message = message;
        this.date = date;
        this.errorCause = errorCause;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getErrorCause() {
        return errorCause;
    }

    public void setErrorCause(String errorCause) {
        this.errorCause = errorCause;
    }
}
