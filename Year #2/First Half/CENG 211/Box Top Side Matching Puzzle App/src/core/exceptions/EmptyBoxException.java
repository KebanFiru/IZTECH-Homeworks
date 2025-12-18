package core.exceptions;

public class EmptyBoxException extends RuntimeException {

    public EmptyBoxException(){
        super();
    }
    public EmptyBoxException(String message) {

        super(message);
    }
}
