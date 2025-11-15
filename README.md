# Arkanoid-OOP-Project
🎮 ColorBreak — Java Arkanoid Game

Advanced Arkanoid-style game implemented as part of an Object-Oriented Programming project.
Includes full Listener Pattern implementation, block removal logic, scoring system, ball-death mechanics, and organized Java packages.

📌 Features
🧱 Block Collision & Removal

Blocks are removed only if hit by a ball of a different color

Ball color updates to the block’s color after every hit

Listener pattern used to decouple hit events from removal logic

👀 Listener Pattern (Observer) Implementation

HitNotifier & HitListener interfaces

BlockRemover to handle block deletion

BallRemover to handle balls falling off the screen

ScoreTrackingListener to update score

Clean decoupled architecture

❤️ Lives & Ball Removal

“Death Region” block removes balls when they fall below screen

Game ends if all balls die

⭐ Scoring System

+5 points per block hit

+100 bonus points for clearing all blocks

Live score display via a ScoreIndicator sprite at the top of the window

🧱 Clean OOP Architecture

Organized into Java packages (geometry, sprites, collisions, game logic…)

No instanceof or AWT graphics

Uses composition, delegation, and listeners

🗂️ Project Structure
src/
  geometry/
  sprites/
  collisions/
  game/
  indicators/
  listeners/
  Ass5Game.java   (main class)
build.xml

🚀 How to Run
Compile
ant compile

Run
ant run


Make sure your folder structure is:

ass5/
  src/
  build.xml

🧪 Key Classes (Overview)
🎯 Hit Listeners
Class	Role
HitNotifier	Object that notifies listeners of hit events
HitListener	Interface for receiving hit events
BlockRemover	Removes blocks + updates counter
BallRemover	Removes balls when they hit death-region
ScoreTrackingListener	Adds points on block hits
👁️ Score Indicator

Displays score on-screen using a custom Sprite.
Automatically updates as Counter changes — fully decoupled from listeners.

🕹️ Gameplay Rules

Ball hitting block of same color → block stays

Ball hitting block of different color → block removed

Ball color changes to block color after each collision

Game ends when:

all blocks are removed OR

all balls are lost

📚 Technologies & Concepts

Java OOP

Observer / Listener Pattern

Packages & modular architecture

Collision detection

Sprite animation

Counters & state management

Game loop

✨ Credits

Built as part of BIU — Object Oriented Programming course (2024–2025), improved and structured for professional GitHub presentation.
