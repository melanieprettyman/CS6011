
# Multi-Platform Chat Application

This project is a multi-platform chat application consisting of a web server, web client, and Android app. The web server, implemented in Java, facilitates communication between clients using WebSockets. The web client is a simple HTML and JS web page that allows users to log in and participate in chat rooms. The Android app provides a native mobile experience for users to join chat rooms, send messages, and view chat history.


## Tech Stack

**Java:** Used for the web server to handle WebSocket communication and manage chat rooms

**HTML and JS:** Implemented a web client for users to access the chat application through a browser.

**Android Studio (Java):** Developed a native Android app to enhance user experience on mobile devices.
## Lessons Learned

- WebSocket Communication: Implementing WebSocket communication between the server and clients required careful handling of messages and synchronization to ensure a seamless chat experience.

- Android UI Design: Designing a user-friendly interface for the Android app, including chat bubbles and message display, presented challenges in UI design and responsiveness.


## Deployment

To deploy this project clone the repository:


```bash
git clone <repository_url>
```
- Set Up the Web Server: Open the Java project in your preferred IDE and run the server. Make sure to handle exceptions gracefully.

- Access the Web Client: Open the index.html file in a web browser. Enter your username, choose a chat room, and start chatting.

- Run the Android App: Open the Android project in Android Studio. Connect an Android device or use an emulator. Build and run the app on the device.

## How to Use the Project

 -  Web Client: 
    - Open the index.html file in a web browser
    - Enter your username and choose a chat room.
    - Start chatting with other users in the same room.

 -  Android App:
    - Launch the app on your Android device
    - Enter your username and choose a chat room.
    - Send and receive messages in real-time.




## API Reference

### MainActivity Class

#### `onCreate(Bundle savedInstanceState)`

| Parameter               | Type     | Description                                      |
| ----------------------- | -------- | ------------------------------------------------ |
| `savedInstanceState`    | `Bundle` | Initializes the main activity of the Android app. |

#### `handleButton(View view)`

| Parameter  | Type   | Description                                |
| ---------- | ------ | ------------------------------------------ |
| `view`     | `View` | Handles the button click event in the main activity. |

---

### ChatActivity Class

#### `onCreate(Bundle savedInstanceState)`

| Parameter               | Type     | Description                                          |
| ----------------------- | -------- | ---------------------------------------------------- |
| `savedInstanceState`    | `Bundle` | Initializes the chat activity of the Android app.     |

#### `sendMessage(View view)`

| Parameter  | Type   | Description                                         |
| ---------- | ------ | --------------------------------------------------- |
| `view`     | `View` | Sends a message through the WebSocket connection.     |

---

### MyWebsocket Class

#### `onConnected(WebSocket websocket, Map<String, List<String>> headers)`

| Parameters             | Type                              | Description                                         |
| ---------------------- | --------------------------------- | --------------------------------------------------- |
| `websocket`, `headers` | `WebSocket`, `Map<String, List<String>>` | Called when the WebSocket connection is established. |

#### `onError(WebSocket websocket, WebSocketException cause)`

| Parameters       | Type                   | Description                                       |
| ---------------- | ---------------------- | ------------------------------------------------- |
| `websocket`, `cause` | `WebSocket`, `WebSocketException` | Called when an error occurs in the WebSocket connection. |

#### `onTextMessage(WebSocket websocket, String text)`

| Parameters      | Type                   | Description                                      |
| --------------- | ---------------------- | ------------------------------------------------ |
| `websocket`, `text` | `WebSocket`, `String` | Called when a text message is received through the WebSocket. |


## Related

The Android chat app is complemented by a chat-server project that handles backend communication through WebSocket protocols. The chat-server facilitates real-time messaging between users and manages WebSocket connections.


[Chat Server](https://github.com/melanieprettyman/CS6011/tree/main/Week4/Day20/ChatServer)


## Acknowledgements

 -  This project was developed by Melanie Prettyman. Special thanks to Dav de St. Germain for guidance and support during the development process.

