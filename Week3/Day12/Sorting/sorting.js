// Function to find the index of the minimum element in the array
function findMinLocation(arr, startIndex, compareFunction) {
  //ensures that the first element is correctly
  // considered as the initial minimum during the first iteration.
  let minIndex = startIndex;

  //starting from startIndex (i in selectionSort) find the min within the "unsorted" portion of the array
  for (let i = startIndex; i < arr.length; i++) {
    // if arr[i] is less than arr[minIndex]
    if (compareFunction(arr[i], arr[minIndex])) {
      //updates the minIndex to the current index i, arr[i] is the new minimum element found
      minIndex = i;
    }
  }
  return minIndex;
}

// Selection Sort Function
function selectionSort(arr, compareFunction) {
  for (let i = 0; i < arr.length; i++) {
    //find minIndex starting from pos i
    const minIndex = findMinLocation(arr, i, compareFunction);
    //if minIndex does not equal what is at pos i, swap them
    if (minIndex !== i) {
      // Swap the elements
      [arr[i], arr[minIndex]] = [arr[minIndex], arr[i]];
    }
  }
}

// Compare function for sorting integers in ascending order
function compareInt(a, b) {
  return a < b;
}
// Compare function for sorting strings in ascending order
function compareString(a, b) {
  return a.length < b.length;
}

// Test the sorting function with different types of data
const intArray = [5, 3, 7, 1, 9, 2];
selectionSort(intArray, compareInt);
console.log( intArray);

const floatArray = [5.2, 3.1, 7.5, 1.0, 9.7, 2.3];
selectionSort(floatArray, compareInt);
console.log(floatArray);

const strArray = ["poop", "Me Myself & I", "who", "get out"];
selectionSort(strArray, compareString);
console.log( strArray);

