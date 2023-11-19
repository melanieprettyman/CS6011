"use strict";
// Get the canvas element from the HTML
let canvas = document.getElementById("canvasDrawing");
// Initialize the 2D drawing context
let context = canvas.getContext("2d");
// Get the width and height of the canvas from the HTML
let canvasW = canvas.width;
let canvasH = canvas.height;

// Load the image of a honey pot to be drawn
let honeyPot = new Image();
honeyPot.src = "honey.png";
honeyPot.xPos = 40; // Initial X position of the honey pot
honeyPot.yPos = 40; // Initial Y position of the honey pot

// Load an image of a bee
let bee = new Image();
bee.src = "bee.png";

// Create an array to store bee objects
let beeArray = [];
for (let i = 0; i < 6; i++) {
  // Initialize bee objects with random positions
  let beeObj = { xPos: Math.floor(Math.random() * 1400), yPos: Math.floor(Math.random() * 700) };
  beeObj.img = new Image();
  beeObj.img.src = "bee.png";
  beeArray.push(beeObj);
}

// Function to animate the images
function animateImage() {
  // Erase old images
  eraseOld();

  //Set collision to equal false
  let collision = false;

  for (let i = 0; i < 6; i++) {
    // Draw each bee image and update its position
    context.drawImage(beeArray[i].img, beeArray[i].xPos, beeArray[i].yPos, 80, 60);

    // Update bee positions based on honey pot position
    if (honeyPot.xPos > beeArray[i].xPos) {
      beeArray[i].xPos += 3;
    } else if (honeyPot.xPos < beeArray[i].xPos) {
      beeArray[i].xPos -= 3;
    }
    if (honeyPot.yPos > beeArray[i].yPos) {
      beeArray[i].yPos += 3;
    } else if (honeyPot.yPos < beeArray[i].yPos) {
      beeArray[i].yPos -= 3;
    }

    // Check for collision between honey pot and bees, if so then collision is true
    if (
      (Math.abs(honeyPot.xPos - beeArray[i].xPos) <= 3) &&
      (Math.abs(honeyPot.yPos - beeArray[i].yPos) <= 3)
    ) {
      collision = true;
    }
  }

  // Draw the honey pot image
  context.drawImage(honeyPot, honeyPot.xPos - 50, honeyPot.yPos - 50, 60, 60);

  // If a collision occurs, show the end screen; otherwise, request the next frame for animation
  if (collision) {
    stopTimer(); // Stop the timer when collision occurs
    endScreen();
  } else {
    updateTimer(); // Update the timer within the game loop
    window.requestAnimationFrame(animateImage);
  }
}

// Main function to initialize the drawing
function mainDrawing() {
  // Request the first frame for animation
  window.requestAnimationFrame(animateImage);
}

// Function to handle mouse movement and update honey pot position
function handleHoneyPot(e) {
  honeyPot.xPos = e.x;
  honeyPot.yPos = e.y;
}

// Function to erase the old drawing and add a background color
function eraseOld() {
  context.fillStyle = "rgb(201,175,25)";
  context.fillRect(0, 0, canvasW, canvasH);
}

// When the page is loaded, start the main drawing function
window.onload = mainDrawing;

// Register the handleHoneyPot function to respond to mouse movement events
document.onmousemove = handleHoneyPot;

// Function to display the end screen
function endScreen() {
  context.fillStyle = "black";
  context.font = "50px Arial";
  context.fillText("Game Over", (canvasW / 2) - 150, 100);
  context.fillText("Reload the page to play again!", (canvasW / 2) - 300, 150);
}

let timerInterval;
let totalSeconds = 0;
let secondsLabel = document.getElementById("seconds");
let minutesLabel = document.getElementById("minutes");

function startTimer() {
  timerInterval = setInterval(updateTimer, 1000);
}

function updateTimer() {
  totalSeconds++;
  secondsLabel.innerHTML = pad(totalSeconds % 60);
  minutesLabel.innerHTML = pad(parseInt(totalSeconds / 60));
}

function stopTimer() {
  clearInterval(timerInterval);
}

function pad(val) {
  const valString = val + "";
  if (valString.length < 2) {
    return "0" + valString;
  } else {
    return valString;
  }
}
