# Scholarship Evaluation System

## Project Description
This is a comprehensive Scholarship Evaluation System developed for CENG211 course. The system evaluates three types of scholarship applications (Merit, Need, and Research) based on specific criteria including GPA requirements, family income thresholds, and research publications. The system processes CSV data containing applicant information, supporting documents, and additional data to determine scholarship eligibility, type (Full/Half), and duration.

## Features
- **Three Scholarship Types**:
  - **Merit Scholarship**: Based on GPA and academic performance
  - **Need-Based Scholarship**: Based on family income and dependents
  - **Research Scholarship**: Based on publications and impact factors
- **Comprehensive Evaluation Criteria**:
  - Common Requirements: Enrollment certificate (ENR), valid transcript, minimum GPA 2.5
  - Type-Specific Requirements: GPA thresholds, income limits, publication requirements
- **Intelligent Decision System**:
  - Priority-based rejection reason detection
  - Scholarship type determination (Full/Half)
  - Duration calculation based on documents and type
  - Adjustment factors for dependents (Need scholarship)
- **CSV Data Processing**: 
  - Reads applicant data, transcripts, family info, documents, and publications
  - Factory pattern for creating appropriate application types based on ID prefix
- **Evaluation Statistics**:
  - Total applications processed
  - Acceptance/rejection rates with percentages
  - Full vs. Half scholarship distribution

## Project Structure
```
Scholarship Evaulation System/
├── src/
│   ├── main/
│   │   └── ScholarshipEvaluationSystem.java  # Main application entry point
│   ├── model/
│   │   ├── Applicant.java                    # Applicant data with validation
│   │   ├── Document.java                     # Supporting document entity
│   │   └── Publication.java                  # Research publication entity
│   ├── types/
│   │   ├── Application.java                  # Abstract base class for applications
│   │   ├── MeritApplication.java             # Merit scholarship application
│   │   ├── NeedApplication.java              # Need-based scholarship application
│   │   ├── ResearchApplication.java          # Research scholarship application
│   │   ├── DocumentType.java                 # Enum for document types (ENR, REC, RSV, GRP)
│   │   ├── RejectionReason.java              # Enum for rejection reasons
│   │   └── ScholarshipResultType.java        # Enum for scholarship types (Full/Half/None)
│   ├── evaluator/
│   │   ├── ScholarshipEvaluator.java         # Abstract evaluator with common logic
│   │   ├── MeritScholarshipEvaluator.java    # Merit-specific evaluation rules
│   │   ├── NeedScholarshipEvaluator.java     # Need-specific evaluation rules
│   │   └── ResearchScholarshipEvaluator.java # Research-specific evaluation rules
│   └── util/
│       └── ApplicationParser.java            # CSV file parser with Factory pattern
├── files/
│   └── ScholarshipApplications.csv           # Input data file
├── bin/                                       # Compiled class files
├── .gitignore                                 # Git ignore rules
└── README.md                                  # This file
```

## Technologies and Concepts

### Programming Language
- Java 17+

### Design Principles & Patterns
- **Object-Oriented Programming**: 
  - Encapsulation, inheritance, polymorphism, abstraction
  - Single Responsibility Principle (SRP) - Applications manage data, Evaluators manage logic
  - Open/Closed Principle - Easy to add new scholarship types
- **Design Patterns**:
  - **Strategy Pattern**: Each Application type uses its own ScholarshipEvaluator strategy
  - **Factory Pattern**: ApplicationParser creates appropriate Application subclass based on ID
  - **Template Method Pattern**: ScholarshipEvaluator defines evaluation flow, subclasses implement specifics
- **Defensive Programming**: 
  - Comprehensive input validation and null checks
  - Deep copying for mutable objects (Applicant, Document, Publication)
  - Immutability with final fields where appropriate
  - Boundary validations (GPA ranges, income thresholds)
  - Meaningful exception messages
- **Encapsulation**: 
  - All fields are private with controlled access via getters/setters
  - Defensive copies returned from getters
- **Code Quality**:
  - JavaDoc documentation for all classes and methods
  - Consistent naming conventions
  - DRY (Don't Repeat Yourself) principle
  - Magic numbers eliminated using constants

### Evaluation Logic
- **Merit Scholarship**:
  - Full: GPA ≥ 3.20
  - Half: 3.00 ≤ GPA < 3.20
  - Duration: 2 years with REC, 1 year without
- **Need-Based Scholarship**:
  - Base thresholds: Full ≤ $10k, Half ≤ $15k
  - Adjustment #1: If SAV (Savings) document exists → +20% to both thresholds
  - Adjustment #2: If 3+ dependents → additional +10% to both thresholds
  - Adjustments are cumulative (both can apply)
  - Duration: Fixed 1 year
- **Research Scholarship**:
  - Requires: At least one publication or GRP (Grant Research Proposal) document
  - Full: Average impact factor ≥ 1.50
  - Half: 1.00 ≤ Average impact factor < 1.50
  - Duration: 12 months (Full) or 6 months (Half), +12 months with RSV

### Key Implementation Details
- **StringTokenizer** for robust CSV parsing with delimiter handling
- **Enum types** for type safety (DocumentType, RejectionReason, ScholarshipResultType)
- **Priority-based rejection**: Checks in order (ENR → Transcript → GPA → Specific)
- **Comparator** for sorting applications by ID
- **Defensive copying** in constructors and copy constructors
- **Inner helper classes** in parser for temporary data storage

## Contributors

This project was developed collaboratively by Group 13 for CENG211 course. While all team members contributed to the entire codebase through code reviews, testing, and refinements, primary responsibilities were distributed as follows:

| Team Member    | Primary Contributions |
|----------------|----------------------|
| Talha Karakaya | **CSV I/O, Main & Packaging**<br>- ApplicationParser.java (CSV parsing with Factory pattern)<br>- ScholarshipEvaluationSystem.java (main application & statistics)<br>- Project packaging and organization<br>- Data aggregation and summary reporting |
| Mert Türkilli   | **Model & Inheritance**<br>- Application.java (abstract base class with Strategy pattern)<br>- MeritApplication.java (merit application type)<br>- NeedApplication.java (need application type)<br>- ResearchApplication.java (research application type)<br>- Applicant.java (applicant entity)<br>- Document.java (document entity)<br>- Publication.java (publication entity)<br>- DocumentType.java (document types enum) |
| Hakan Bağıç | **Evaluation & Business Logic**<br>- ScholarshipEvaluator.java (abstract evaluation framework)<br>- MeritScholarshipEvaluator.java (merit-specific rules)<br>- NeedScholarshipEvaluator.java (need-specific rules with adjustments)<br>- ResearchScholarshipEvaluator.java (research-specific rules)<br>- RejectionReason.java (rejection logic)<br>- ScholarshipResultType.java (scholarship types) |

**Note**: All team members participated in design decisions, code reviews, testing, validation implementation, defensive programming enhancements, and comprehensive JavaDoc documentation.

## Course Information
- **Course**: CENG211 - Programming Fundamentals
- **Assignment**: Homework #2
- **Group**: Group 13
- **Semester**: Fall 2025-2026
