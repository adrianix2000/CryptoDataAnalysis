package pl.adrianix2000.backend.Exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.net.http.HttpResponse;

@Getter
public class ApplicationException extends RuntimeException{

    private HttpStatus status;

    public ApplicationException(String message, HttpStatus httpStatus) {
        super(message);
        status = httpStatus;
    }
}
