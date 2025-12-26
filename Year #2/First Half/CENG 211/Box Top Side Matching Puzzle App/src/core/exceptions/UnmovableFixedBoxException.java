package core.exceptions;

/**
 * Runtime exception thrown when attempting to move or manipulate a FixedBox in ways it cannot support.
 * 
 * This exception occurs when:
 * - A player selects an edge FixedBox during the first stage of a turn (trying to roll it)
 * - A BoxFlipper tool is used on a FixedBox (trying to flip it upside down)
 * - Any operation tries to change the orientation of a FixedBox
 * 
 * This is a RuntimeException (unchecked) because:
 * - It represents a logic error that should be prevented by validation
 * - The application should check if a box is a FixedBox before attempting to move/flip it
 * - It indicates improper operation on an immovable object rather than a recoverable game state
 * 
 * FixedBoxes are immovable and their top side stays the same at all times.
 * They also prevent the domino-effect from being transmitted past them.
 * 
 * When this exception is thrown, the turn is wasted and the game continues to the next turn.
 */
public class UnmovableFixedBoxException extends RuntimeException {
    
    /**
     * Constructs a new UnmovableFixedBoxException with no detail message.
     */
    public UnmovableFixedBoxException() {
        super();
    }
    
    /**
     * Constructs a new UnmovableFixedBoxException with the specified detail message.
     * 
     * @param message the detail message explaining why the FixedBox cannot be moved
     */
    public UnmovableFixedBoxException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new UnmovableFixedBoxException with the specified detail message and cause.
     * 
     * @param message the detail message
     * @param cause the cause of this exception
     */
    public UnmovableFixedBoxException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Constructs a new UnmovableFixedBoxException with the specified cause.
     * 
     * @param cause the cause of this exception
     */
    public UnmovableFixedBoxException(Throwable cause) {
        super(cause);
    }
}
