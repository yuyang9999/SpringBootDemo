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

    public ApiResponse(String errorMsg) {
        this.hasError = true;
        this.errorMsg = errorMsg;
        this.response = "";
    }

    public ApiResponse(Object response) {
        this.hasError = false;
        this.errorMsg = "";
        this.response = response;
    }

    public static final ApiResponse succeedResp = new ApiResponse(false, "", "");

    public static final ApiResponse userNotExistedError = new ApiResponse(false, "user not existed", "");
}
