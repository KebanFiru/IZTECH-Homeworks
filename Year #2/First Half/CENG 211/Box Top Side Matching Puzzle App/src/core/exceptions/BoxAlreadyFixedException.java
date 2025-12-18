package core.exceptions;

public class BoxAlreadyFixedException extends RuntimeException {

    public BoxAlreadyFixedException() {
        super();
    }

    public BoxAlreadyFixedException(String message) {

        super(message);
    }
}
