# G13_CENG211_HW1 - E-Sports Tournament Management System

## Project Description
This is a comprehensive E-Sports Tournament Management System developed for CENG211 course. The system manages tournaments with 100 gamers, 10 games, and automatically generates 1500 matches (15 matches per gamer). It calculates points based on performance and experience, assigns medals, and provides detailed tournament statistics.

## Features
- **Match Generation**: Automatically creates 1500 matches with random game selections (3 games per match)
- **Points Calculation System**: 
  - Raw Points: Sum of (rounds × base points per game)
  - Skill Points: floor(rawPoints × (1 + min(experience, 10) × 0.02))
  - Bonus Points: Tiered system based on raw points
    - 600+: 100 points
    - 400-599: 50 points
    - 200-399: 25 points
    - <200: 10 points
  - Match Points: Skill Points + Bonus Points
- **Medal System**: 
  - GOLD: Total points >= 4400
  - SILVER: Total points 3800-4399
  - BRONZE: Total points 3500-3799
  - NONE: Total points < 3500
- **Tournament Statistics**:
  - Highest scoring match details
  - Lowest scoring match with most contributing game
  - Match with lowest bonus points
  - Highest scoring gamer with complete statistics
  - Total tournament points across all matches
  - Medal distribution with counts and percentages

## Project Structure
```
G13_CENG211_HW1/
├── src/
│   ├── ESportManagementApp.java    # Main application entry point
│   ├── Game.java                    # Game entity with validation
│   ├── Gamer.java                   # Gamer entity with validation
│   ├── Match.java                   # Match entity with point calculation logic
│   ├── MatchManagement.java         # Match generation and management
│   ├── PointsBoard.java             # Points calculation and medal assignment
│   ├── Query.java                   # Tournament statistics and queries
│   └── FileIO.java                  # CSV file reading operations
├── files/
│   ├── games.csv                    # Game data (10 games)
│   └── gamers.csv                   # Gamer data (100 gamers)
├── .gitignore                       # Git ignore rules
└── README.md                        # This file
```

## How to Run

### Prerequisites
- Java 11 or higher
- CSV files in the `files/` directory

### Running the Application
```bash
# Make sure you are in the G13_CENG211_HW1 directory

# Run directly (Java 11+)
java src/ESportManagementApp.java

# Or compile first, then run
javac src/*.java
java -cp src ESportManagementApp
```

## Input Files

### games.csv Format
```
ID,GameName,BasePointPerRound
1,CS2,16
2,Valorant,12
...
```

### gamers.csv Format
```
ID,Nickname,Name,Phone,ExperienceYears
1,Shadow1,John Doe,555-0101,5
2,Phoenix2,Jane Smith,555-0102,3
...
```

## Sample Output
```
Highest-Scoring Match:
Match ID: 1224
Games: [CS2, PUBG, Apex Legends]
Rounds: [9, 10, 10]
Raw Points: 462
Skill Points: 535
Bonus Points: 50
Match Points: 585

Lowest-Scoring Match:
Match ID: 750
Games: [Dota2, Valorant, Fortnite]
Rounds: [1, 1, 3]
Raw Points: 56
Skill Points: 63
Bonus Points: 10
Match Points: 73

Most Contributing Game in this Match:
Game: Fortnite
Contribution: 3 rounds × 9 points = 27

Match with Lowest Bonus Points:
Match ID: 1
Games: [Fortnite, Overwatch, Call of Duty]
Rounds: [9, 2, 4]
Skill Points: 165
Bonus Points: 10
Match Points: 175

Highest-Scoring Gamer
Nickname: Wraith3
Name: Elijah Martin
Total Points: 5191
Average Per Match: 346.07
Medal: GOLD

Total Tournament Points across 1500 matches: 409,602

Medal Distribution:
GOLD:   26 gamers (26.0%)
SILVER: 47 gamers (47.0%)
BRONZE: 16 gamers (16.0%)
NONE:   11 gamers (11.0%)
```

## Technologies and Concepts

### Programming Language
- Java 17+

### Design Principles
- **Object-Oriented Programming**: Encapsulation, inheritance, polymorphism
- **Defensive Programming**: 
  - Comprehensive input validation
  - Deep copying for mutable objects (arrays, custom objects)
  - Null checks and boundary validations
  - Meaningful exception messages
- **Encapsulation**: All fields are private with controlled access via getters/setters
- **Immutability**: Final fields where appropriate
- **Code Quality**:
  - JavaDoc documentation for all classes and methods
  - Consistent naming conventions
  - DRY (Don't Repeat Yourself) principle
  - Magic numbers eliminated using constants

### Key Implementation Details
- Random round generation (1-10 rounds per game)
- Unique match ID generation
- Random game selection ensuring no duplicates per match
- Locale.US formatting for consistent number display
- Deep copy constructors for all model classes

## Contributors

This project was developed collaboratively by Group 13 for CENG211 course. While all team members contributed to the entire codebase through code reviews, testing, and refinements, primary responsibilities were distributed as follows:

| Student Number | Team Member | Primary Contributions |
|---------------|------------|----------------------|
| 320201048 | Mert Türkilli | **Match Logic & Management**<br>- Match.java (point calculation algorithms)<br>- MatchManagement.java (match generation system) |
| 320201066 | Hakan Bağıç | **Statistics & Application**<br>- Query.java (tournament queries and statistics)<br>- PointsBoard.java (points calculation and medals)<br>- ESportManagementApp.java (main application) |
| 320201027 | Talha Karakaya | **Data Models & I/O**<br>- FileIO.java (CSV file operations)<br>- Game.java (game entity)<br>- Gamer.java (gamer entity) |

**Note**: All team members participated in design decisions, code reviews, testing, validation implementation, and JavaDoc documentation.

### Development Tools & AI Assistance

This project utilized AI tools (GitHub Copilot) to enhance code quality and development efficiency:
- **Code Review**: AI-assisted review for identifying potential bugs, code smells, and improvement opportunities
- **JavaDoc Documentation**: AI assistance in creating comprehensive, consistent JavaDoc comments across all classes and methods
- **Git Repository Management**: AI-guided best practices for commit messages, branch management, and repository organization
- **Defensive Programming**: AI suggestions for validation patterns, null checks, and error handling strategies
- **Code Refactoring**: AI recommendations for eliminating magic numbers, improving naming conventions, and maintaining consistency

All AI-generated suggestions were thoroughly reviewed, tested, and validated by the team members before implementation.

## Course Information
- **Course**: CENG211 - Programming Fundamentals
- **Assignment**: Homework #1
- **Group**: Group 13
- **Semester**: Fall 2025-2026

## License
This project is developed for educational purposes as part of CENG211 course requirements.
