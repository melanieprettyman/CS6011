
# Synthesizer Application

The Synthesizer Application is a JavaFX-based interactive audio synthesizer that allows users to create, manipulate, and play various sound waves and musical notes. Users can generate different types of waveforms (e.g., sine waves, square waves), adjust volume, and mix multiple audio components together. Additionally, the application features a piano interface for playing notes. Users can create a variety of soundscapes by combining these audio components and adjusting their properties.




## Tech

- **JavaFX**: Chosen for its user-friendly GUI capabilities, allowing for easy interaction with the audio components. Provides tools for creating the visual interface, buttons, and widgets.
- **Java Sound API**: Utilized for audio generation and playback, enabling the application to create and play audio clips.
- **MIDI Integration**: Used to trigger musical notes on the piano, enhancing the application's versatility and allowing users to experiment with a wide range of sounds.
## Challenges/Future Features 

### Challenges 
Challenges faced during the development of the Synthesizer Application include:
- Implementing drag-and-drop functionality for widgets.
- Creating dynamic cabling between components.
- Ensuring the application could accommodate various types of audio components, such as sine waves, square waves, and volume adjusters.
- Intuitive user interaction with both the piano and audio components.

### Future Features
For future development, the application could be expanded with additional features, including:
- Implementing additional audio components and effects.
- Enabling the saving and loading of soundscapes.
- Supporting more complex signal processing, allowing users to create more intricate soundscapes.


## Deployment

To install and run the Synthesizer Application, follow these steps:

### Prerequisites
- Ensure you have Java Development Kit (JDK) installed on your system.
- The project is designed to be run in an Integrated Development Environment (IDE) like IntelliJ IDEA or Eclipse, but you can also use command-line tools if you prefer.

### Steps
1. **Clone the Repository**
   - Clone the project repository from GitHub.
2. **Open the Project in Your IDE**
   - Open the project in your preferred IDE by importing the project directory.
3. **Configure the Project**
   - Configure the project to use JavaFX libraries.
4. **Run the Application**
   - Find the synthesizer application class and run it.
5. **Enjoy the Synthesizer**
   - The Synthesizer Application should open, and you can start experimenting with sound waves and the piano interface.



## How to Use the Project

Once the Synthesizer Application is up and running, here's how you can use it:

### Sound Wave Widgets
- Click the buttons in the right panel to create different sound wave widgets (e.g., SineWave, SquareWave, VF-SineWave).
- Adjust the properties and settings of the widgets to modify the sound output.

### Connect Widgets
- Use the cabling system to connect audio components by clicking on the output and input jacks of widgets.
- Create complex soundscapes by mixing different audio components.

### Adjust Volume
- Create a VolumeAdjuster widget to control the overall volume.
- Adjust the slider to control the volume of all connected components.

### Play Sounds
- Press the play button in the bottom panel to play the audio composition created by the connected widgets.
- Enjoy the unique sounds and melodies you've created.

### Play the Piano
- On the left side, you have a piano interface with keys mapped to your keyboard.
- Press keys on your keyboard to play musical notes.

### Customize and Experiment
- Feel free to experiment with different soundscapes, waveforms, and musical compositions.
- Save and load your soundscapes if those features have been implemented.



## API Reference

### Synthesizer Class

#### `generateSoundWave(WaveType type)`

| Parameter | Type       | Description                              |
| --------- | ---------- | ---------------------------------------- |
| `type`    | `WaveType` | Generates a sound wave of the given type. |

#### `adjustVolume(int volumeLevel)`

| Parameter      | Type     | Description                           |
| -------------- | -------- | ------------------------------------- |
| `volumeLevel`  | `int`    | Adjusts the volume to the given level.|

### Piano Class

#### `playNote(Note note)`

| Parameter | Type  | Description                       |
| --------- | ----- | --------------------------------- |
| `note`    | `Note`| Plays the given musical note.     |

#### `stopNote()`

| Parameter | Type | Description                  |
| --------- | ---- | ---------------------------- |
|           |      | Stops the currently playing note. |

### SoundWidget Class

#### `createSoundWave(WaveType type)`

| Parameter | Type       | Description                                  |
| --------- | ---------- | -------------------------------------------- |
| `type`    | `WaveType` | Creates a sound wave widget of the given type. |

#### `connectWidgets(SoundWidget widget1, SoundWidget widget2)`

| Parameter   | Type         | Description                                        |
| ----------- | ------------ | -------------------------------------------------- |
| `widget1, widget2` | `SoundWidget` | Connects two sound widgets to create soundscapes. |

#### `adjustVolume(int volumeLevel)`

| Parameter      | Type     | Description                                  |
| -------------- | -------- | -------------------------------------------- |
| `volumeLevel`  | `int`    | Adjusts the volume level of the sound widgets.|

### Key Methods Overview

- **`generateSoundWave(WaveType type)`**: Generates a sound wave of the given type.
- **`adjustVolume(int volumeLevel)`**: Adjusts the volume level of the synthesizer.
- **`playNote(Note note)`**: Plays the given musical note on the piano.
- **`stopNote()`**: Stops the currently playing note on the piano.
- **`createSoundWave(WaveType type)`**: Creates a sound wave widget of the given type.
- **`connectWidgets(SoundWidget widget1, SoundWidget widget2)`**: Connects two sound widgets to create soundscapes.
- **`adjustVolume(int volumeLevel)`**: Adjusts the volume level of the sound widgets.

### Sound Generation

- **Sound Wave Generation**: Creates various types of sound waves (sine, square, etc.) using `generateSoundWave()`.
- **Volume Control**: Adjusts the volume level of the synthesizer and sound widgets using `adjustVolume()`.

### Piano Interaction

- **Playing Notes**: Produces musical notes using the `playNote()` method on the piano.
- **Note Stopping**: Stops the currently playing note on the piano using the `stopNote()` method.
## Acknowledgements

The Synthesizer Application was created by Melanie Prettyman and was developed as part of CS 6011. We would like to thank the following resources and libraries that contributed to the project's development:
- **JavaFX** for the graphical user interface.
- **Java Sound API** for audio generation and playback.

We also appreciate the guidance and support provided by Professor Nabil Makarem throughout the project.

