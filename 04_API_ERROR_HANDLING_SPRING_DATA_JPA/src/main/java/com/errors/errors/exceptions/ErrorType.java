package com.errors.errors.exceptions;
import lombok.*;
import java.util.Date;
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ErrorType {
    private Date timestamp;
    private String message;
    private String details;
}
