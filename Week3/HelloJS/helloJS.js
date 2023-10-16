"use strict";
function main() {
  document.writeln('Hello, World!');
  console.log("Hello World!");
  //What's the difference? Why would you want to use one or the other?
    //writeln prints the string to the webpage, console.log prints the string to the consol and is mainly
    //used for debugging



  //Create an array literal variable (write stuff inside of []) containing a string, a boolean, an int,
  // a floating-point number, and an object.

  let stdObj;
  stdObj = {name: "John", gpa: 3.7, year: 3};

  let myArray;
  myArray=["poop", false, 69, stdObj];

  console.log( myArray );

  myArray[2] = 69.69;

  console.log( myArray );

  //What do you notice?
  //the element was replaced/changed to what I modified it to


  //Declare/Define a function:
  function myFunctionCStyle( param1, param2 ) {
    console.log(param1 + param2);
  }
  //Create a function and store it in a variable:
    let myFunctionJSStyle = function( param1, param2 ) {
      console.log(param1 + param2);

    }
  myFunctionJSStyle(10,10);
  myFunctionJSStyle(1.56, 2.54);
  myFunctionJSStyle("to the windows ","to the walls...");
  myFunctionJSStyle("to the windows ",69);


  myFunctionCStyle(10,10);
  myFunctionCStyle(1.56, 2.54);
  myFunctionCStyle("to the windows ","to the walls...");
  //floating point numbers, strings

  //Which syntax do you prefer? Can you detect any difference between the two ways of declaring functions?
  /* I prefer the JS style of functions. A function literal is compiled into a class that when instantiated at run time
  is a function value. The distinction between function literals and values is that function literals exist in the
  source code, whereas function values exist as objects at runtime.
  */



}
window.onload = main;


