package pl.edu.pw.zpoif.exchangeit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyErrorController extends Controller{
    private static final String prefix =
            "java.util.concurrent.ExecutionException: pl.edu.pw.zpoif.exchangeit.exceptions.AlphaVintageAPI" +
                    ".RequestsNumExceededException: ";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        String message = ex.getMessage().replaceFirst(prefix, "").replaceAll("\"", "");
        return new ResponseEntity<>("{\"error\":\"" + message + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
