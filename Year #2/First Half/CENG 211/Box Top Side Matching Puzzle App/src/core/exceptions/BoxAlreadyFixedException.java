package core.exceptions;

/**
 * Exception thrown when attempting to use a BoxFixer tool on a box that is already fixed.
 * 
 * This exception occurs during the second stage of a turn when:
 * - A player acquires a BoxFixer tool from an opened box
 * - The player tries to use the BoxFixer on a box that is already a FixedBox
 * - Attempting to fix a FixedBox is redundant and not allowed
 * 
 * When this exception is thrown, the turn is wasted and the game continues to the next turn.
 * The application should catch and handle this exception gracefully without terminating.
 * 
 * Note: Using a BoxFixer on a RegularBox or UnchangingBox is valid - it replaces them
 * with a FixedBox copy and removes any tool inside.
 */
public class BoxAlreadyFixedException extends RuntimeException{
    
    /**
     * Constructs a new BoxAlreadyFixedException with no detail message.
     */
    public BoxAlreadyFixedException() {
        super();
    }
    
    /**
     * Constructs a new BoxAlreadyFixedException with the specified detail message.
     * 
     * @param message the detail message explaining why the operation failed
     */
    public BoxAlreadyFixedException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new BoxAlreadyFixedException with the specified detail message and cause.
     * 
     * @param message the detail message
     * @param cause the cause of this exception
     */
    public BoxAlreadyFixedException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Constructs a new BoxAlreadyFixedException with the specified cause.
     * 
     * @param cause the cause of this exception
     */
    public BoxAlreadyFixedException(Throwable cause) {
        super(cause);
    }
}
