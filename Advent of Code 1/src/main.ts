import {Input} from "./Input";

let originalInput = Input.get();
console.log("Starting");

let amount = 0;

let splitInput = originalInput.split("\n");
for (let index = 0; index < splitInput.length; index++) {
    let piece = splitInput[index];
    let sign = piece.charAt(0);
    let number = parseInt(piece.substring(1));
    if (sign == "-") {
        amount -= number;
    } else if (sign = "+") {
        amount += number;
    } else {
        console.log("Uh oh " + piece);
    }
}

console.log("Frequency: " + amount);