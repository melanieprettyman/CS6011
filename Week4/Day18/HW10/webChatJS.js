
//Initialize our WebSockets:
let ws = "ws://localhost:8080/";
let websocket = new WebSocket(ws);
console.log("web socket created");
// on websocket open:
websocket.onopen = function(event) {
  console.log("ws is now open");
  MessageAdd('<div class="message green">You have entered the chat room.</div>');
};

// on websocket close:
websocket.onclose = function(event) {
  MessageAdd('<div class="message blue">You have been disconnected.</div>');
};

// on websocket error:
websocket.onerror = function(event) {
  MessageAdd('<div class="message red">Connection to chat failed.</div>');
};


//On-message event handler
//receive messages from the server and display them for all users in the chat room
websocket.onmessage = function(event) {
  var data = JSON.parse(event.data);

  if (data.type == "message") {
    MessageAdd('<div class="message">' + data.username + ': ' + data.message + '</div>');
  }
};

/*creates an event handler for the chat-form form element we created earlier.
The message is then pulled from the input field. If the message is at least one character long,
a JavaScript object is created holding some data about the message, the user, and the input text.
The websocket.send() method then submits a stringified version of our object to the server, and the input
field value is cleared.*/
document.getElementById("chat-form").addEventListener("submit", function(event) {
  event.preventDefault();

  var message_element = document.getElementsByTagName("input")[0];
  var message = message_element.value;

  if (message.toString().length) {
    var username = localStorage.getItem("username");

    var data = {
      type: "message",
      username: username,
      message: message
    };

    websocket.send(JSON.stringify(data));
    message_element.value = "";
  }
}, false);

roomNameForm = document.getElementById("roomName");
roomName = roomNameForm.value;
  roomName.addEventListener("keypress", function (e) {
  if (e.key === 'Enter') {
    // code for enter
    if(roomName >= 'a' && roomName <= 'z'){
      localStorage.setItem("username", userName);
    }
    else {
      alert("Your room-name must contain only lowercase letters (and no spaces).");
    }
  }
});
