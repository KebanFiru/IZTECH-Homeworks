package core.exceptions;

public class EmptyBoxException extends RuntimeException {

    public EmptyBoxException(){
        
    }
    public EmptyBoxException(String message) {

        super(message);
    }
}
