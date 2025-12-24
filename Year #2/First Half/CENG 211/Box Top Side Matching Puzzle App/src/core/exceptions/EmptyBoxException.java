package core.exceptions;

/**
 * Exception thrown when attempting to open a box that is empty.
 * 
 * This exception occurs during the second stage of a turn when:
 * - A player opens a box that contains no SpecialTool
 * - The box was previously opened and its tool was already taken
 * - A FixedBox is opened (always empty)
 * - A RegularBox is opened and it was generated without a tool (25% chance)
 * 
 * When this exception is thrown, the turn is wasted and the game continues to the next turn.
 * The application should catch and handle this exception gracefully without terminating.
 */
public class EmptyBoxException extends Exception {
    
    /**
     * Constructs a new EmptyBoxException with no detail message.
     */
    public EmptyBoxException() {
        super();
    }
    
    /**
     * Constructs a new EmptyBoxException with the specified detail message.
     * 
     * @param message the detail message explaining why the box is empty
     */
    public EmptyBoxException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new EmptyBoxException with the specified detail message and cause.
     * 
     * @param message the detail message
     * @param cause the cause of this exception
     */
    public EmptyBoxException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Constructs a new EmptyBoxException with the specified cause.
     * 
     * @param cause the cause of this exception
     */
    public EmptyBoxException(Throwable cause) {
        super(cause);
    }
}
