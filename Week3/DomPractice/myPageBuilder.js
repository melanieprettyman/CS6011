


function main() {
  document.writeln("<h1> Why are you here?</h1>");
  document.writeln("<p id='main_par'> Really why are you here? This is a completely useless website. </p>");
  document.writeln("<p>Here is the link to a better website:<a href=https://isitchristmas.today/> <3 </a></p>");
  document.writeln("<div id = \"img\"> <caption> Dwayne the Rock Johnson as an egg</caption> <br> </div>");


  let img = document.getElementById('img');
  let devImage = new Image();
  devImage.src = "data:image/jpeg;base64,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";
  devImage.setAttribute('width', '120px');
  devImage.setAttribute('height', '150px');
  img.appendChild(devImage);

  let myElement = document.body;
  myElement.style.background = "chartreuse";

  myElement.style.fontWeight = "bold";

  myElement.style.textAlign = "center";

  //Event based programming (HTML)
  document.writeln("<button id= \"IncButton\"> Increase font size</button>\n")
  document.writeln("<button id= \"ResetButton\"> Reset </button>\n")

//Event based programming
  function update_paragraph() {
    const mainParagraph = document.getElementById('main_par');
    mainParagraph.style.fontSize = "24px";
    mainParagraph.style.color = "darkblue";
    mainParagraph.style.background = "pink";

  }

  function reset_paragraph() {
    const mainParagraph = document.getElementById('main_par');
    mainParagraph.style.fontSize = "16px";
    mainParagraph.style.color = "black";
    mainParagraph.style.background = "chartreuse";

  }

//option 1
  const btn1 = document.getElementById("IncButton");
  btn1.onclick = update_paragraph;


//Option 2
  const btn2 = document.getElementById("ResetButton");
  btn2.addEventListener("click", reset_paragraph);


// //option 3
//   btn2.onclick = function (){
//     const mainParagraph = document.getElementById('main_par');
//     mainParagraph.style.fontSize = "12px";
//     mainParagraph.style.color = "black";
//     mainParagraph.style.background = "chartreuse";
//   }

//option 4 (from html file)

//
  document.writeln("<div id = \"display_fromserver\"></div>\n");
 document.writeln("<button onclick = \"load_server();\"> Load from Server </button>");

  function load_server(){
    let req = new XMLHttpRequest();
    req.open("GET", "files/LICENSE.txt");
    req.send();

    req.onload = function (){
      document.getElementById("display_fromserver").innerHTML = req.responseText;


      }
    }



}
window.onload = main
