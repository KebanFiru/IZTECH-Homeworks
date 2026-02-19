# Box Top Side Matching Puzzle App

## Project Description
This is a comprehensive 8x8 grid puzzle game developed for CENG211 course. The game challenges players to maximize a target letter on the top surfaces of boxes through strategic rolling, tool usage, and box manipulation over 5 turns. Players interact with three types of boxes (Regular, Unchanging, and Fixed), utilize five different special tools, and navigate complex game mechanics including domino-effect rolling, stamping, flipping, and fixing operations.

## Features
- **Three Box Types**:
  - **RegularBox (85%)**: Can be rolled, opened, and stamped; 75% chance of containing a tool
  - **UnchangingBox (10%)**: Can be rolled and opened; always contains a tool; surfaces cannot be changed by stamps
  - **FixedBox (5%)**: Cannot be rolled or stamped; always empty; blocks domino-effect
- **Five Special Tools**:
  - **PlusShapeStamp**: Stamps target letter on a box and its 4 adjacent neighbors (plus shape)
  - **MassRowStamp**: Stamps target letter on all stampable boxes in a row
  - **MassColumnStamp**: Stamps target letter on all stampable boxes in a column
  - **BoxFlipper**: Flips a box upside down (swaps top and bottom surfaces)
  - **BoxFixer**: Converts any box to a FixedBox (immovable for the rest of the game)
- **Advanced Game Mechanics**:
  - **Domino-Effect Rolling**: Edge boxes can be rolled inward, affecting all boxes in the path until a FixedBox is encountered
  - **Turn-Based Validation**: Only rolled boxes can be opened in the same turn
  - **Strategic Tool Usage**: Players must decide when and where to use tools for maximum impact
  - **Letter Maximization**: Goal is to maximize occurrences of a randomly selected target letter (A-H)
- **Robust Input Handling**:
  - Multiple input formats supported (R1-C4 or 1-4)
  - Comprehensive error messages for invalid inputs
  - Edge detection and corner direction selection
  - Out-of-bounds validation
- **Three Custom Exceptions**:
  - **EmptyBoxException** (checked): Normal game flow when box has no tool
  - **BoxAlreadyFixedException** (unchecked): Logic error when trying to fix a FixedBox
  - **UnmovableFixedBoxException** (unchecked): Logic error when trying to roll/flip a FixedBox

## Project Structure
```
Box Top Side Matching Puzzle App/
├── src/
│   ├── app/
│   │   └── BoxPuzzleApp.java              # Main application entry point
│   ├── core/
│   │   ├── BoxPuzzle.java                 # Main game controller with MenuHandler inner class
│   │   ├── BoxGrid.java                   # 8x8 grid manager with rolling logic
│   │   └── exceptions/
│   │       ├── EmptyBoxException.java     # Checked exception for empty boxes
│   │       ├── BoxAlreadyFixedException.java        # Unchecked exception for fixing attempts
│   │       └── UnmovableFixedBoxException.java      # Unchecked exception for rolling attempts
│   ├── interfaces/
│   │   ├── IRollable.java                 # Interface for boxes that can be rolled
│   │   ├── IOpenable.java                 # Interface for boxes that can be opened
│   │   └── IStampable.java                # Interface for boxes that can be stamped
│   ├── model/
│   │   ├── box/
│   │   │   ├── Box.java                   # Abstract base class for all box types
│   │   │   ├── RegularBox.java            # Most common box (85% probability)
│   │   │   ├── UnchangingBox.java         # Special box with unchangeable surfaces (10%)
│   │   │   └── FixedBox.java              # Immovable box (5% probability)
│   │   ├── tools/
│   │   │   ├── SpecialTool.java           # Abstract base class for all tools
│   │   │   ├── PlusShapeStamp.java        # Stamps in plus shape pattern
│   │   │   ├── MassRowStamp.java          # Stamps entire row
│   │   │   ├── MassColumnStamp.java       # Stamps entire column
│   │   │   ├── BoxFlipper.java            # Flips box upside down
│   │   │   └── BoxFixer.java              # Converts box to FixedBox
│   │   └── enums/
│   │       ├── Letter.java                # Enum for box surface letters (A-H)
│   │       └── Direction.java             # Enum for rolling directions (UP, DOWN, LEFT, RIGHT)
├── .gitignore                              # Git ignore rules
└── README.md                               # This file
```

## Technologies and Concepts

### Programming Language
- Java SE 17+

### Design Principles & Patterns
- **Object-Oriented Programming**: 
  - Encapsulation: All fields private with controlled access
  - Inheritance: Three-level hierarchy (Box → RegularBox/UnchangingBox/FixedBox)
  - Polymorphism: Tool usage via generic method `<T extends SpecialTool>`
  - Abstraction: Abstract classes (Box, SpecialTool) and interfaces (IRollable, IOpenable, IStampable)
- **Design Patterns**:
  - **Strategy Pattern**: Each SpecialTool implements useTool() with its own algorithm
  - **Template Method Pattern**: Box class defines common structure, subclasses override specifics
  - **Facade Pattern**: BoxPuzzle simplifies complex interactions between grid, boxes, and tools
- **Java Advanced Features**:
  - **Generics**: `<T extends SpecialTool>` for type-safe tool usage
  - **Inner Classes**: MenuHandler inside BoxPuzzle for game flow management
  - **Collections Framework**: `ArrayList<List<Box>>` for 2D grid representation (35+ line explanation included)
  - **Enums**: Type-safe constants for Letter and Direction
  - **Exception Handling**: Mix of checked (EmptyBoxException) and unchecked (BoxAlreadyFixedException, UnmovableFixedBoxException) exceptions
- **Defensive Programming**: 
  - Comprehensive input validation (location format, bounds checking)
  - Null checks before operations
  - Deep copying for mutable objects
  - Immutable random generation for surfaces
  - Boundary validations for grid operations
  - Edge and corner detection algorithms
- **Code Quality**:
  - Comprehensive JavaDoc documentation (100% coverage)
  - Consistent naming conventions
  - Helper methods for clarity (isEdgeBox, isCornerBox, determineAutoDirection)
  - DRY principle applied throughout
  - Magic numbers eliminated using constants

### Game Mechanics
- **Box Generation**:
  - Probability-based: 85% Regular, 10% Unchanging, 5% Fixed
  - Random letter assignment on all 6 surfaces (A-H)
  - Tool initialization based on box type
- **Tool Distribution**:
  - RegularBox: 75% chance (15% each of 5 tools), 25% empty
  - UnchangingBox: 100% chance (20% each of 5 tools)
  - FixedBox: Always empty
- **Rolling Mechanics**:
  - Edge boxes only (28 positions out of 64)
  - Corner boxes: User selects from 2 directions
  - Non-corner edge boxes: Auto-determined direction (inward)
  - Domino-effect: All boxes in path roll until FixedBox or boundary
  - Surface rotation based on direction (UP/DOWN rotate front-back-top-bottom, LEFT/RIGHT rotate left-right-top-bottom)
- **Stamping Behavior**:
  - RegularBox: Surfaces can be changed
  - UnchangingBox: Implements IStampable but surfaces remain unchanged
  - FixedBox: Does NOT implement IStampable (cannot be stamped)
- **Turn Structure**:
  1. Optional: View all surfaces of any box
  2. Stage 1: Roll an edge box (affects path)
  3. Stage 2: Open a rolled box and use tool
  4. Repeat for 5 turns
- **Winning Condition**:
  - Maximize target letter on top surfaces after 5 turns
  - Final score displayed (count of target letter)

### Key Implementation Details
- **Grid Display Format**: `R-A-M` (Type-TopLetter-HasTool)
  - Type: R (Regular), U (Unchanging), X (Fixed)
  - TopLetter: A-H
  - HasTool: M (has tool), O (empty/opened)
- **Rolling Validation**: `wasRolledThisTurn()` flag prevents opening non-rolled boxes
- **Exception Hierarchy**: 
  - Checked (EmptyBoxException): Normal flow, caught and handled gracefully
  - Unchecked (BoxAlreadyFixedException, UnmovableFixedBoxException): Logic errors, propagate to main handler
- **Direction Determination**:
  - Top edge (R1) → DOWN
  - Bottom edge (R8) → UP
  - Left edge (C1) → RIGHT
  - Right edge (C8) → LEFT
  - Corners → User choice
- **Stamping Priority**: Center box first, then adjacent boxes (prevents overwriting)
- **Collections Choice**: ArrayList for O(1) random access and iteration efficiency

## Game Rules

### Setup
1. 8x8 grid is generated with random boxes (85% Regular, 10% Unchanging, 5% Fixed)
2. Each box gets random letters (A-H) on all 6 surfaces
3. Tools are randomly placed inside boxes based on type probabilities
4. A random target letter (A-H) is selected for the game

### Turn Flow (5 Turns Total)
1. **Optional Preview**: View all 6 surfaces of any box
2. **Stage 1 - Roll Edge Box**:
   - Select an edge box (must be on R1, R8, C1, or C8)
   - Corner boxes: Choose from 2 possible directions
   - Other edge boxes: Auto-rolled inward
   - Domino-effect: All boxes in path roll until FixedBox or boundary
   - FixedBox selection: Turn skips, game continues to next turn
3. **Stage 2 - Open Box & Use Tool**:
   - Select a box that was rolled in Stage 1
   - If box is empty: Turn ends, continue to next turn
   - If box contains tool: Use tool immediately on a target location
   - BoxAlreadyFixedException: Turn wasted, continue to next turn

### Tool Usage Rules
- **PlusShapeStamp**: Target any box; stamps center + 4 adjacent boxes (if they exist and are stampable)
- **MassRowStamp**: Target any box; stamps all stampable boxes in that row
- **MassColumnStamp**: Target any box; stamps all stampable boxes in that column
- **BoxFlipper**: Target any non-FixedBox; swaps top and bottom surfaces
- **BoxFixer**: Target any non-FixedBox; replaces with FixedBox (same surfaces, tool removed)

### Restrictions
- **Rolling**: Only edge boxes can be rolled; FixedBox throws exception
- **Opening**: Only boxes rolled in current turn can be opened
- **Stamping**: UnchangingBox accepts stamp but doesn't change; FixedBox cannot be stamped
- **Flipping**: FixedBox cannot be flipped (throws exception)
- **Fixing**: Cannot fix an already FixedBox (throws exception, wastes turn)

### Winning
- No winning/losing condition
- Goal: Maximize target letter on top surfaces after 5 turns
- Final score shows total count of target letter

## Contributors

This project was developed collaboratively by Group 13 for CENG211 course. While all team members contributed to the entire codebase through code reviews, testing, and refinements, primary responsibilities were distributed as follows:

| Team Member    | Primary Contributions |
|----------------|----------------------|
| Talha Karakaya | **Model, Enums & Interfaces**<br>- Box.java (abstract base class with rolling mechanics)<br>- RegularBox.java, UnchangingBox.java, FixedBox.java (box types)<br>- Letter.java (letter enum with helper methods)<br>- Direction.java (direction enum)<br>- IRollable.java (rolling interface)<br>- IOpenable.java (opening interface)<br>- IStampable.java (stamping interface) |
| Mert Türkilli   | **Core Game Logic & Application**<br>- BoxPuzzleApp.java (main application entry point)<br>- BoxPuzzle.java (game controller with MenuHandler inner class)<br>- BoxGrid.java (8x8 grid manager with domino-effect rolling)<br>- Game flow management and user interaction<br>- Turn validation and rolled status tracking<br>- Collections framework implementation (ArrayList explanation) |
| Hakan Bağıç | **Tools & Exception Handling**<br>- SpecialTool.java (abstract tool base class)<br>- PlusShapeStamp.java, MassRowStamp.java, MassColumnStamp.java (stamping tools)<br>- BoxFlipper.java, BoxFixer.java (manipulation tools)<br>- EmptyBoxException.java (checked exception)<br>- BoxAlreadyFixedException.java (unchecked exception)<br>- UnmovableFixedBoxException.java (unchecked exception)<br>- Exception hierarchy design and handling logic |

**Note**: All team members participated in design decisions, code reviews, testing, validation implementation, defensive programming enhancements, and comprehensive JavaDoc documentation.

## Course Information
- **Course**: CENG211 - Programming Fundamentals
- **Assignment**: Homework #4 - Box Top Side Matching Puzzle
- **Semester**: Fall 2025-2026
- **Institution**: Izmir Institute of Technology (IZTECH)

## Development Notes
- All JavaDoc documentation is comprehensive (100% coverage)
- Exception handling follows Java best practices (checked vs unchecked)
- Collections framework usage includes detailed 35+ line explanation
- Helper methods improve code readability and maintainability
- Input validation prevents runtime errors and provides clear feedback
