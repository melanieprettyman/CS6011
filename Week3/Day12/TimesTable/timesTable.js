function buildTable () {
  // let table = document.createElement("table");
  var htm = '';

  //the row counter loop
  for (var i=1; i<=10; i++) {
    htm += '<tr class="bold">';
    // let tr = document.createElement("tr")
    // table.appendChild(tr);

    //the columns in each row
    for(var j=1; j<=10; j++) {
      htm += '<td class="bold" >' +(i*j)+ '</td>';
     //  let td = document.createElement("td");
     //  tr.appendChild(td);
    }
    htm += '</tr>';
  }

  //add the rows inside the table
  document.getElementById('myTable').innerHTML = htm;


}

document.onready = buildTable();
// Variable to store the currently selected <td>
let selectedTd = null;


var tds= document.getElementsByTagName("td");
for(let td of tds ){
  td.addEventListener("mouseover", function() {td.classList.add("lit")});
  td.addEventListener("mouseout", function() {td.classList.remove("lit")});
  td.addEventListener("click", function () {
    if (selectedTd) {
      selectedTd.classList.remove("lit2");
    }
    td.classList.add("lit2");
    selectedTd = td;
  });


}


let i = 0;
function changeBackground(){
  let doc = document.getElementById("myTable");
  let color = ["rgb(200,200,0)", "rgb(100, 0, 100)", "rgb(0,150,0)"];
  doc.style.backgroundColor = color[i];
  i = (i +1) % color.length;
}
window.setInterval(changeBackground, 100);
