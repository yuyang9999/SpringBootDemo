package hello.apis;

import lombok.Data;

/**
 * Created by yangyu on 7/10/17.
 */
@Data
public class ApiResponse {
    private boolean hasError;

    private String errorMsg;

    private Object response;

    public ApiResponse(boolean hasError, String errorMsg, Object response) {
        this.hasError = hasError;
        this.errorMsg = errorMsg;
        this.response = response;
    }
}
