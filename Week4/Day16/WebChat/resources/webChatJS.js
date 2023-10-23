
// Initialize WebSockets
let ws = "ws://localhost:8080/";
// Define the WebSocket server address
let websocket = new WebSocket(ws);

// Get DOM elements by their IDs
roomname = document.getElementById("roomName");
username = document.getElementById("userName");
peopleBox = document.getElementById("peopleBox");
chatBox = document.getElementById("chatBox");
// Scroll chat box to the bottom
chatBox.scrollTop = chatBox.scrollHeight;

// WebSocket status flag
wsOpen = false;

// Event handler for WebSocket open:
websocket.onopen = function(event) {
  // WebSocket is open and ready to use
  wsOpen = true;
};


//--------------------
//ROOM INPUT LISTENER
//--------------------
// Event listener for the room name input field
roomname.addEventListener("keypress", function (e) {
  if (e.key === 'Enter') {
    const name = roomname.value;

    // Define a regular expression pattern to match only lowercase letters
      //^ = start matching from the beginning of the string. $ = continue matching until the end of the string.
    const lowercaseLetters = /^[a-z]+$/;

    //test method returns true if the string matches the pattern, so if not true, do the following
    if (!lowercaseLetters.test(name) || name.includes(' ')) {
      alert("Your room-name must contain only lowercase letters (and no spaces).");
    } else {
      // If the name is valid, create and send a "join" message to the server
      const send = "join " + username.value + " " + name;
      websocket.send(send);
      // Reset the input field
      roomname.value = "";
    }
  }
});

//-------------------------
//CHAT SUBMISSION LISTENER
//-------------------------
// Event listener for the chat form submission
// This handles sending chat messages to the server
document.getElementById("chat-form").addEventListener("submit", function(event) {
  // Prevent form submission (page refresh)
  event.preventDefault();

  //Pull message from the input field
  let message_element = document.getElementById("input");
  let message = "message " + message_element.value;

  // Send the message to the server via WebSocket
  websocket.send(message);
  // Clear the input field
  message_element.value = "";
});

//--------------------
//LEAVE BTN LISTENER
//--------------------
// Event listener for the "Leave" button
// This handles sending a "leave" message to the server
document.getElementById("leavebtn").addEventListener("click", function(event) {
  let message = "leave ";
  // Send the "leave" message to the server
  websocket.send(message);
});

//-----------
//ON-MESSAGE
//-----------
// On-message event handler
// Receive messages from the server and display them in the chat room
websocket.onmessage = function(event) {
  // Parse the incoming JSON data
  let data = JSON.parse(event.data);

  // Create elements for displaying messages
  let joinMsg = document.createElement("blockquote");
  let message = document.createElement("blockquote");

  //Create message based on data type
  if (data.type === "join") {
    joinMsg.textContent = data.user + ' has entered ' + data.room;
  }
  if (data.type === "message") {
    message.textContent = data.user + ': ' + data.message;
    console.log(message);
  }
  if (data.type === "leave") {
    let removedPerson = document.getElementById(data.user);
    message.textContent = data.user + ' left the room';
    peopleBox.removeChild(removedPerson);
  }

  // Add join and chat messages to the respective boxes
  peopleBox.appendChild(joinMsg);
  chatBox.appendChild(message);
};
