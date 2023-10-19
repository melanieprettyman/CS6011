"use strict";

const xValue = document.getElementById("xValue");
const yValue = document.getElementById("yValue");
const resultInput = document.getElementById("result");


//No button - we might create it later on

//I will listen to key press event on xValue and yValue
xValue.addEventListener("keypress", handleKeyPress);
yValue.addEventListener("keypress", handleKeyPress);


let ws = new WebSocket("ws://localhost:8080/calculate");
//handle open
let wsOpen = false;
ws.onopen = function () {
  wsOpen = true;
}

function handleKeyPress(event){
  //What the user presses is the 'key code'
  if (event.code === "Enter"){
    let x = parseFloat(xValue.value);

    //If they don't enter a number (maybe a string) x will equal NaN
    if(isNaN(x)){
      alert("x should be a number.")
      xValue.value = "Enter a number";
      xValue.select();
      return;
    }
    let y = parseFloat(yValue.value);

    //If they don't enter a number (maybe a string) x will equal NaN
    if(isNaN(y)){
      alert("y should be a number.")
      yValue.value = "Enter a number";
      yValue.select();
      return;
    }

    //handle on message
    ws.onmessage = function(event) {
      resultInput.value = event.data;
    }
      if(wsOpen){
      ws.send(x + " " + y);
    }
    else {
      resultInput.value = "Couldn't open the websocket"
    }
  }
}

function handleError(){
  resultInput.value= "Problem connecting to server";
}

function handleAjax(){
  resultInput.value=this.responseTest;
}
//Option 1 using AJAX
// let xhr = new XMLHttpRequest();
// xhr.open("GET", "http://localhost:8080/calculate?x=" + x + "&y="+ y);
// xhr.onerror= handleError;
// xhr.addEventListener("load", "call a method");
// xhr.onload = handleAjax;
// xhr.send();
