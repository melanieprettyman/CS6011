
# Chat Server

The chat server facilitates WebSocket communication for an Android chat application. It handles incoming client connections, manages room logic, and enables real-time messaging between multiple clients. Here are the key aspects:



## Implementation Overview

- **MyRunnable Class**: Manages client connections and WebSocket communication.
- **WebSocket Communication**: Handles WebSocket messages (join, leave, and general messages).
- **Room Logic**: Maintains rooms, adds/removes clients, sends message history to clients, and broadcasts messages within the room.
- **File Handling**: Serves HTML files and other resources for the accompanying website.
## Deployment

1. **Compile and Run**: Compile the server code and execute it on a suitable platform.
2. **WebSocket Endpoint**: Use the WebSocket URL (e.g., `ws://your-server-address:port/endpoint`) in the Android app for WebSocket connections.



## How to Use the Project

1. **Accessing the Site**: Host the website on a server or local environment.
2. **Interacting with the Site**: Use the chat interface to send and receive messages.
3. **Compatibility**: Ensures compatibility with the Android app's WebSocket endpoints for cross-platform communication.




## Features

- **HTML & JavaScript**: Incorporates HTML and JS for user interaction and message display.
- **Responsive Design**: Ensures compatibility and usability across various devices and screen sizes.
- **WebSocket Integration**: Utilizes WebSocket connection for real-time chat functionality.



## API Reference

### MyRunnable Class

#### `run()`

| Parameter | Type     | Description                                   |
| --------- | -------- | --------------------------------------------- |
| `client`  | `Socket` | Manages client connections and WebSocket communication. |

#### `handleIncomingWebSocketMessages()`

| Parameter | Type     | Description                                   |
| --------- | -------- | --------------------------------------------- |
|           |          | Handles incoming WebSocket messages, including parsing, handling room logic, and sending messages. |

#### `extractRoomName(String jsonMessage)`

| Parameter    | Type     | Description                                   |
| ------------ | -------- | --------------------------------------------- |
| `jsonMessage`| `String`| Extracts the room name from a JSON message.   |

#### `handleClients(String message_)`

| Parameter | Type     | Description                                   |
| --------- | -------- | --------------------------------------------- |
| `message_`| `String`| Manages client actions (join, leave) within a room based on received messages. |

---

### Key Methods Overview

- **`run()`**: Manages client connections and WebSocket communication.
- **`handleIncomingWebSocketMessages()`**: Handles incoming WebSocket messages, including parsing, handling room logic, and sending messages.
- **`extractRoomName(String jsonMessage)`**: Extracts the room name from a JSON message.
- **`handleClients(String message_)`**: Manages client actions (join, leave) within a room based on received messages.

### WebSocket Communication

- **Handling Messages**: Identifies message types (join, leave, general) and performs corresponding room logic.
- **Payload Decoding**: Unmasks, decodes, and processes incoming WebSocket message payloads.

### Room Management

- **Room Creation**: Creates rooms based on extracted room names.
- **User Interaction**: Manages users joining, leaving rooms, and broadcasts message history within the room.

### Error Handling

- **Client Errors**: Manages errors related to client connections and communication.
- **Message Parsing Errors**: Addresses issues related to parsing incoming messages.


## Related

The Chat-Server is complemented by a Andriod Chat App project that handles frontend communication. 

[Chat App](https://github.com/melanieprettyman/CS6011/tree/main/Week5/Day22/app/src/main)


## Acknowledgements

 -  This project was developed by Melanie Prettyman. Special thanks to Dav de St. Germain for guidance and support during the development process.

