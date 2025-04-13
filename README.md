# Project Ellen

This project is a game developed using Java and Kotlin, leveraging the GameLib framework. The game features various mechanics such as character interactions, weapon usage, animations, and state management.

## Project Structure

- **`characters`**: Contains classes for characters like Ripley, Alien, and others.
- **`game`**: Core game mechanics, such as doors, lasers, and other objects.
- **`items`**: Implementation of game items like ammo and energy.
- **`weapons`**: Weapons and their functionality.

## Main Characters

### Ripley
The main character of the game with the following attributes:
- Energy (health)
- Speed
- Backpack for storing items
- Weapon (gun)

### Alien
An enemy that attacks the player. Upon being defeated, it may drop ammo or energy.

## Key Mechanics

- **Doors**: Can change the map state (e.g., turning tiles into walls or clearing them).
- **Lasers**: Deal damage to all objects crossing their beam.
- **Health System**: Characters and enemies have health that decreases when taking damage.
- **Message System**: Used to publish events like character death or door opening.

## Installation and Running

1. Ensure you have **Java 17** and **Gradle** installed.
2. Clone the repository:
   ```bash
   git clone <git@github.com:danylo-senchyshyn/project-ellen.git>
