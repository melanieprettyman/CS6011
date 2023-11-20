
# Not the Bees

"Not the Bees" is an interactive web-based game built with JavaScript, HTML, and CSS. In this game, players control a honey pot and navigate through a swarm of bees while attempting to avoid collisions. The game features an animated hive, interactive movement controls, and a timer to track gameplay duration. Players can challenge themselves to beat their best time and restart the game for another attempt.

Website: https://notthebees.tiiny.site/



## Game Play

- **Objective:** Navigate the honey pot through the swarm of bees without colliding.
- **Controls:** Move the honey pot by moving your mouse cursor.
- **Timer:** A timer at the bottom of the screen tracks your gameplay duration.
- **Game Over:** Colliding with the bees ends the game, displaying a "Game Over" screen.
- **Restart:** Reload the page to start a new game and beat your best time.
## Deployment

To install and run the Not The Bees, follow these steps:

### Prerequisites
- Ensure you have Java Development Kit (JDK) installed on your system.
- The project is designed to be run in an Integrated Development Environment (IDE) like IntelliJ IDEA or Eclipse, but you can also use command-line tools if you prefer.

### Steps
1. **Clone the Repository**
   - Clone the project repository from GitHub.
2. **Open the index.html file in your web browser**
   - Move your mouse cursor to control the honey pot.
   - Avoid collisions with the bees to stay in the game.




## Game Logic Overview
- **Canvas Animation:** Utilizes canvas drawing for game graphics.
- **Bee Movement:** Bees chase the honey pot based on its position.
- **Collision Detection:** Checks for collisions between the honey pot and bees.
- **Timer Functionality:** Tracks and displays game duration.
## API Reference

### Variables

#### `canvas`

| Parameter | Type                  | Description                              |
| --------- | --------------------- | ---------------------------------------- |
| `canvas`  | `HTMLCanvasElement`   | The canvas element used for drawing game graphics. |

#### `context`

| Parameter | Type                     | Description                              |
| --------- | ------------------------ | ---------------------------------------- |
| `context` | `CanvasRenderingContext2D` | The 2D drawing context for the canvas.   |

#### `canvasW`

| Parameter | Type     | Description     |
| --------- | -------- | --------------- |
| `canvasW` | `number` | Width of the canvas. |

#### `canvasH`

| Parameter | Type     | Description     |
| --------- | -------- | --------------- |
| `canvasH` | `number` | Height of the canvas. |

#### `honeyPot`

| Parameter | Type   | Description        |
| --------- | ------ | ------------------ |
| `honeyPot` | `Image` | Image object for the honey pot. |

#### `bee`

| Parameter | Type   | Description        |
| --------- | ------ | ------------------ |
| `bee`     | `Image` | Image object for the bee. |

#### `beeArray`

| Parameter | Type   | Description                    |
| --------- | ------ | ------------------------------ |
| `beeArray`| `Array` | Array storing bee objects.     |

#### `timerInterval`

| Parameter      | Type   | Description                     |
| -------------- | ------ | ------------------------------- |
| `timerInterval`| `number`| Interval for updating the timer. |

#### `totalSeconds`

| Parameter      | Type   | Description                |
| -------------- | ------ | -------------------------- |
| `totalSeconds` | `number`| Total seconds elapsed.     |

#### `secondsLabel`

| Parameter     | Type         | Description          |
| ------------- | ------------ | -------------------- |
| `secondsLabel`| `HTMLElement` | Label for seconds.    |

#### `minutesLabel`

| Parameter     | Type         | Description          |
| ------------- | ------------ | -------------------- |
| `minutesLabel`| `HTMLElement` | Label for minutes.    |

### Functions

#### `animateImage()`

| Parameter     | Type   | Description                               |
| ------------- | ------ | ----------------------------------------- |
| `animateImage`| None   | Animates game images and handles collision detection. |

#### `mainDrawing()`

| Parameter     | Type   | Description                      |
| ------------- | ------ | -------------------------------- |
| `mainDrawing` | None   | Initializes the main drawing function. |

#### `handleHoneyPot(e)`

| Parameter        | Type       | Description                        |
| ---------------- | ---------- | ---------------------------------- |
| `handleHoneyPot` | `MouseEvent`| Handles mouse movement to update honey pot position. |

#### `eraseOld()`

| Parameter | Type | Description                       |
| --------- | ---- | --------------------------------- |
| `eraseOld`| None | Erases old drawings and adds a background color. |

#### `endScreen()`

| Parameter | Type | Description                       |
| --------- | ---- | --------------------------------- |
| `endScreen`| None | Displays the end screen upon game over. |

#### `startTimer()`

| Parameter   | Type | Description                      |
| ------------ | ---- | -------------------------------- |
| `startTimer` | None | Starts the timer for tracking gameplay duration. |

#### `updateTimer()`

| Parameter    | Type | Description                       |
| ------------ | ---- | --------------------------------- |
| `updateTimer`| None | Updates the timer display.         |

#### `stopTimer()`

| Parameter  | Type | Description                       |
| ---------- | ---- | --------------------------------- |
| `stopTimer`| None | Stops the timer.                   |

#### `pad(val)`

| Parameter | Type   | Description                      |
| --------- | ------ | -------------------------------- |
| `pad`     | `number`| Pads the value for proper timer format. |

### Event Handlers

#### `onload`

| Parameter | Type | Description                       |
| --------- | ---- | --------------------------------- |
| `onload`  | None | Initiates the main drawing function upon page load. |

#### `onmousemove`

| Parameter        | Type       | Description                        |
| ---------------- | ---------- | ---------------------------------- |
| `onmousemove`    | `MouseEvent`| Handles mouse movement to update honey pot position. |

## Acknowledgements

The Synthesizer Application was created by Melanie Prettyman and was developed as part of CS 6011. We would like to thank the following resources and libraries that contributed to the project's development:
- **JavaFX** for the graphical user interface.
- **Java Sound API** for audio generation and playback.

We also appreciate the guidance and support provided by Professor Nabil Makarem throughout the project.

