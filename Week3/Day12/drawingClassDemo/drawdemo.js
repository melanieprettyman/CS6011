"use strict";

//Get canvas
let canvas = document.getElementById("canvasDrawing");
//Tell JS to draw in 2D
let context = canvas.getContext("2d");
//get width and height of canvas from html
let canvasW = canvas.width;
let canvasH = canvas.height;

//get image to draw
let myIMG= new Image();
myIMG.src = "avatar.jpeg";

//Animated image
let MSDIMG = new Image();
MSDIMG.src = "msd.png";
//1 - let xPos and let yPos
MSDIMG.xPos = 10;
MSDIMG.yPos = 10;

let moveRight = true;

function animateImage(){
  //erase and draw
  eraseOld();
  context.drawImage(MSDIMG, MSDIMG.xPos, MSDIMG.yPos);

  //will change these static value to a more dynamic ones
  //x-axis, y-axis, size
  context.drawImage(myIMG, myIMG.xPos - 50, myIMG.yPos - 50, 100, 100);
  context.strokeRect(myIMG.xPos - 50, myIMG.yPos - 50, 100, 100);

  if(moveRight) {
    MSDIMG.xPos += 5; //making the number large will make the steps faster so it moves faster
  }
  else{
    MSDIMG.xPos-=5;
  }
  //hits the line above
  if(MSDIMG.xPos > canvasW - MSDIMG.width){
    moveRight = false;
  }
  //hits the left side
  else if (MSDIMG.xPos< 0){
    moveRight = true;
  }

  window.requestAnimationFrame(animateImage);

}

//Draw the image in main function
function mainDrawing(){
  //call animate (once)
  window.requestAnimationFrame(animateImage);
  //making it happen over and over add the above to the animate method
}
function handleClick(e){
  //update x and y pos of avatar
  myIMG.xPos = e.x;
  myIMG.yPos = e.y;
}
function eraseOld(){
  //add layer on top of previous image
  context.fillStyle="rgb(114,5,128)";
  context.fillRect(0,0, canvasW, canvasH);
}

//Animate
//Load drawing onto screen
window.onload=mainDrawing;

//Pass in event
//calling handleclick function when mose is moved, erases odl and draws/
document.onmousemove = handleClick;





