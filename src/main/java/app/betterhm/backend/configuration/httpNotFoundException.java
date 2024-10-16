package app.betterhm.backend.configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class httpNotFoundException extends RuntimeException{
    public httpNotFoundException() {
        super();
    }
}
