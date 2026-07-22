package homies.com.backend.exception;

import java.util.Date;

public class ApiError {

    private Date timestamp;
    private int status;
    private String message;

    public ApiError() {
        this.timestamp = new Date();
    }

    public ApiError(int status, String message) {
        this.timestamp = new Date();
        this.status = status;
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
