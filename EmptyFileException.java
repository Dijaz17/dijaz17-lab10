import java.io.IOException;

public class EmptyFileException extends IOException {

    public EmptyFileException(String filepath) {
        super("EmptyFileException: " + filepath + " was empty");
    }
    
}
