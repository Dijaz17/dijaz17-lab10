import java.io.IOException;

public class EmptyFileException extends IOException {

    public EmptyFileException(String filepath) {
        super(filepath + " was empty");
    }
    
}
