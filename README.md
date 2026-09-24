# Cross-Numbers Board Game (Jeu des chiffres croisés)

An interactive turn-based arithmetic board game implemented in **Java**, combining crosswords logic, mental arithmetic, and object-oriented architecture. 

This project was developed as part of the MIPP curriculum at **INSA Rouen Normandie** (Mathematical Engineering Department, Academic Year 2024-2025), supervised by **Mr. A. Osmani**, and created by **Mouad Sheradj Drissi** and **Amr Zaki Salih**.

---

##  Overview

Inspired by crossword puzzles and mathematical games, the goal of **Cross-Numbers** is to place numbered tiles on a grid to form continuous sequences (rows or columns) whose sums match designated global target numbers. 

### Core Game Rules
- **Target Cards:** At the beginning of the game, 3 target numbers are randomly drawn from a card deck (values ranging from 15 to 50) and remain the objectives for the entire match.
- **Tiles & Rack (Chevalet):** Players receive an initial hand of numbered tiles (drawn from a 90-tile bag with values ranging from 1 to 20) with a maximum capacity of 9 tiles per rack.
- **Tile Placement:** On their turn, players place tiles on a 17x17 grid[cite: 25]. Newly placed tiles must connect with already placed tiles.
- **Validation & Scoring:** A placed sequence is validated if the sum of connected tiles in a row or column matches one of the 3 target numbers. Score is calculated based on the target card value and the number of tiles played.
- **Tile Exchange:** If unable to play a valid move, players can trade 1 to 3 tiles with the shared tile bag.
- **Endgame & Victory:** The game terminates when no moves are possible, when the board is full, or when players agree to end the session. The player with the highest total score wins.

---

##  Features
- **Object-Oriented Design (UML-driven):** Designed using formal UML class, use-case, sequence, and object diagrams before implementation.
- **Turn-based CLI & GUI Integration:** Hybrid gameplay featuring terminal-based command flow alongside an interactive graphical game board built with **Java Swing / AWT** (`JFrame`, `JPanel`, `GridLayout`).
- **Placement Validation Engine:** Advanced backtracking and adjacent verification algorithms (`verif_ligne`, `verif_col`) checking horizontal and vertical continuity and sum correctness.
- **Rack & Bag Management:** Dynamic tile tracking, capacity checks, and strategic tile exchanging via the shared pool.

---

## Project Architecture

The codebase is organized into packages separating game logic, board models, and user interactions:


    ├── main.java                 # Entry point running the game and tests[cite: 25, 26]
    ├── src/                      # Game mechanics and orchestrator[cite: 25, 26]
    │   ├── Systeme.java          # Global turn orchestrator, score validation, and rules engine[cite: 25]
    │   └── Chevalet.java         # Player tile rack management (draw, exchange, play)[cite: 25]
    ├── materiel/                 # Physical game components[cite: 25, 26]
    │   ├── Plateau.java          # 17x17 grid state, Swing UI board rendering, and memory buffer[cite: 25]
    │   ├── SacJetons.java        # Shared bag of 90 tiles[cite: 25]
    │   ├── PaquetCartes.java     # Target numbers deck (36 cards)[cite: 25]
    │   ├── Jeton.java            # Numbered tile entity (values 1 to 20)[cite: 25]
    │   └── Carte.java            # Target number card entity[cite: 25]
    ├── utilisateur/              # Player management and interactions[cite: 25, 26]
    │   └── Joueur.java           # Player attributes, score tracking, and input handling[cite: 25]
    ├── Rapport_jeu_mot_croisés.pdf # Full design and architectural project report[cite: 25]
    └── README.md

---
## Compilation & Execution

### Prerequisites

*A Java Development Kit (JDK 8 or higher) must be installed.*

### Compile

Compile all packages and the main class:   

    javac main.java materiel/*.java utilisateur/*.java src/*.java

### Run

*Launch the application:*   

    java main

*To generate and view the complete HTML API documentation:*   

    javadoc -d docs -sourcepath . -subpackages materiel utilisateur src firefox docs/index.html

---

### Authors & Acknowledgments

  **Mouad Sheradj Drissi**
    
   **Amr Zaki Salih**
   
  Academic Advisor: **Mr. A. Osmani** (INSA Rouen Normandie)

---

## Institution

**INSA Rouen Normandie**
Department of Mathematical Engineering
