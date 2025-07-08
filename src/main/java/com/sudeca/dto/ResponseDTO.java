package com.sudeca.dto;
import lombok.Data;

import java.util.Map;

@Data
public class ResponseDTO {
    private Object data;
    private String status;
    private String message;

    public ResponseDTO() {
    }

    public ResponseDTO(Object data, String status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }
}
