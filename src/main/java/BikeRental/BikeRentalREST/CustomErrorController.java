package BikeRental.BikeRentalREST;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@RestController
public class CustomErrorController {
    @ExceptionHandler(NoHandlerFoundException.class)
    public CustomMessage handleNotFound(NoHandlerFoundException ex, HttpServletRequest request) {
        return new CustomMessage(404, ex.getMessage());
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CustomMessage methodNotAllowed(HttpRequestMethodNotSupportedException ex, HttpServletRequest request){
        return new CustomMessage(405, ex.getMessage());
    }
}
