package homies.com.backend.exception;

import java.util.Date;

public class ApiError {

    private Date timestamp;
    private int status;
    private String error;

    public ApiError() {
        this.timestamp = new Date();
    }

    public ApiError(int status, String error) {
        this.timestamp = new Date();
        this.status = status;
        this.error = error;
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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}