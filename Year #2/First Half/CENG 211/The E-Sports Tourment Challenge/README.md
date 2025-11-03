# E-Sports Tournament Management System

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

| Team Member    | Primary Contributions |
|----------------|----------------------|
| Mert Türkilli  | **Match Logic & Management**<br>- Match.java (point calculation algorithms)<br>- MatchManagement.java (match generation system) |
| Hakan Bağıç    | **Statistics & Application**<br>- Query.java (tournament queries and statistics)<br>- PointsBoard.java (points calculation and medals)<br>- ESportManagementApp.java (main application) |
| Talha Karakaya | **Data Models & I/O**<br>- FileIO.java (CSV file operations)<br>- Game.java (game entity)<br>- Gamer.java (gamer entity) |

**Note**: All team members participated in design decisions, code reviews, testing, validation implementation, and JavaDoc documentation.

## Course Information
- **Course**: CENG211 - Programming Fundamentals
- **Assignment**: Homework #1
- **Group**: Group 13
- **Semester**: Fall 2025-2026

