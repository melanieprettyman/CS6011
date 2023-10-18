"use strict";

//Get canvas
let canvas = document.getElementById("canvasDrawing");
//Tell JS to draw in 2D
let context = canvas.getContext("2d");
//get width and height of canvas from html
let canvasW = canvas.width;
let canvasH = canvas.height;

//get image to draw
let honeyPot= new Image();
honeyPot.src = "honey.png";
honeyPot.xPos = 40;
honeyPot.yPos = 40;

//Animated image
let bee = new Image();
bee.src = "bee.png";


let beeArray = [];
for(let i = 0; i < 6; i++) {
  let beeObj = {xPos : Math.floor(Math.random() * 1400), yPos : Math.floor((Math.random() *700))}
  beeObj.img = new Image();
  beeObj.img.src = "bee.png"
  // beeImg.src = "Bee.jpeg";
  beeArray.push(beeObj);
}

function animateImage() {
  //erase and draw
  eraseOld();

  let collision = false;

  for (let i = 0; i < 6; i++) {
    context.drawImage(beeArray[i].img, beeArray[i].xPos, beeArray[i].yPos, 80, 60);

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
    if(((honeyPot.xPos - beeArray[i].xPos) <= 3) &&
      ((honeyPot.xPos - beeArray[i].xPos) >= -3) &&
      ((honeyPot.yPos - beeArray[i].yPos) <= 3) &&
      ((honeyPot.yPos - beeArray[i].yPos) >= -3)){
      collision = true;
      }
    }

    //will change these static value to a more dynamic ones
    //x-axis, y-axis, size
    context.drawImage(honeyPot, honeyPot.xPos - 50, honeyPot.yPos - 50, 60, 60);

  if(collision){
    endScreen();

  } else {
    // Requesting a new frame
    window.requestAnimationFrame(animateImage);
  }
}

//Draw the image in main function
  function mainDrawing() {
    //call animate (once)
    window.requestAnimationFrame(animateImage);
    //making it happen over and over add the above to the animate method
  }

  function handleHoneyPot(e) {
    //update x and y pos of avatar
    honeyPot.xPos = e.x;
    honeyPot.yPos = e.y;
  }

  function eraseOld() {
    //add layer on top of previous image
    context.fillStyle = "rgb(201,175,25)";
    context.fillRect(0, 0, canvasW, canvasH);
  }

//Animate
//Load drawing onto screen
  window.onload = mainDrawing;

//Pass in event
//calling handleHoneyPot function when mose is moved, erases odl and draws/
  document.onmousemove = handleHoneyPot;


function endScreen() {
  context.fillStyle = "black";
  context.font = "50px Arial";
  context.fillText("Game Over", (canvasW / 2)-150, 100);
  context.fillText("Reload page to play again!", (canvasW / 2)-300, 150);
}
