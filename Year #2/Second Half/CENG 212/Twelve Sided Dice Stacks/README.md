# Twelve-Sided Dice Stacks Solver


## Objective & Task

The goal of this project is to model and solve a 3D spatial puzzle using **pure functional programming**. The system simulates a vertical stack of 12-sided (dodecahedron) dice where each placement is governed by strict mathematical rules rather than physical simulation.

### The Task
The solver must identify all possible numbers that can exist on the **top face of the uppermost die** in a stack of 3, 4, or 5 dice, given:
1.  A specific **touching-face sum** constraint.
2.  A known **visible side face** on the bottom-most die.

## Algorithmic Logic & Constraints

The project implements a **State Space Search** using recursive backtracking. The solution space is governed by three primary constraints:

-   **The Opposite Rule:** In a 12-sided die, the sum of any two opposing faces is exactly **13**. ($Face_{top} + Face_{bottom} = 13$).
-   **The Visibility Rule:** A visible side face ($V$) on the bottom die acts as a forbidden state; it cannot be the top or the bottom face of that die ($Face \neq V$ and $13-Face \neq V$).
-   **The Contact Rule:** The sum of two touching faces between Die $n$ and Die $n+1$ must equal a constant input $S$.
    -   *Mathematical Transition:* $Bottom_{next} = S - (13 - Top_{current})$

### Recursive Engine
The core function `traverse-stack` "climbs" the tower level by level. If a calculated face value falls outside the valid range (1-12), the algorithm prunes that branch and backtracks, ensuring only mathematically valid physical configurations are considered.

## 🛠 Technical Stack

-   **Language:** Scheme (R5RS Compliant)
-   **Interpreter:** GNU Guile 3.0