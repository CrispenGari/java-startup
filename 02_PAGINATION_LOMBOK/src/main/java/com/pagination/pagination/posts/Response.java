package com.pagination.pagination.posts;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    protected LocalDateTime timeStamp;
    protected int statusCode;
    protected HttpStatus status;
    protected int limit;
    protected int page;
    protected Map<?, ?> posts;
    protected ResponseError error;
}

@Data
class ResponseError{
    private String field;
    private String message;
    public ResponseError(String message, String field){
        this.field = field;
        this.message = message;
    }
}