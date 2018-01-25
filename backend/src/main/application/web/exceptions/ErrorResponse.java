package main.application.web.exceptions;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ErrorResponse implements Serializable {

    private Date date;
    
    private int code;
    
    private String message;

    public ErrorResponse() {
        this.date = new Date();
    }
    
    public ErrorResponse(int code, String message) {
        super();
        this.date = new Date();
        this.code = code;
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Error [date=" + date + ", code=" + code + ", message=" + message + "]";
    }
    
}
