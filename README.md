# 2048 – Java Implementation

A fully interactive implementation of the classic 2048 puzzle game, developed in Java using the Processing graphics library.

This project was completed as part of INFO1113 (Object-Oriented Programming) at the University of Sydney.

---

## Overview

The game recreates the original 2048 mechanics, including tile movement, merging logic, score updates, and win/lose state detection. The application uses object-oriented design principles to separate game logic, board management, and rendering.

---

## Features

- 4x4 grid-based gameplay
- Tile movement in all four directions
- Correct merge logic (single merge per move)
- Random tile spawning (2 or 4)
- Win detection (2048 tile)
- Game over detection
- Graphical rendering using Processing
- Modular class structure (Board, Tile, App)

---

## Technologies Used

- Java
- Processing
- Gradle

---

## Project Structure
src/main/java/TwentyFortyEight/
├── App.java # Entry point and rendering
├── Board.java # Core game logic and movement rules
└── Tile.java # Tile representation and state


---

## How to Run

1. Clone the repository
2. Open the project in an IDE (e.g., IntelliJ)
3. Run the `App.java` file

Alternatively:
./gradlew run

---

## Learning Outcomes

This project demonstrates:

- Object-oriented design and encapsulation
- State management and game logic implementation
- Event-driven input handling
- Basic 2D rendering using Processing
- Build configuration using Gradle
