import {Input} from "./Input";

let originalInput = Input.get();
console.log("Starting");

let shortestLength = -1;
let alphabet = "abcdefghijklmnopqrstuvwxyz".split("");

for (let removeLetter = 0; removeLetter < alphabet.length; removeLetter++) {
    let input = originalInput + "";
    let letterToRemove = alphabet[removeLetter];
    input = input.split(letterToRemove).join("");
    input = input.split(letterToRemove.toUpperCase()).join("");
    console.log("Removing " + letterToRemove);

    let length = 0;
    while (input.length != length) {
        length = input.length;
        for (let i = 0; i < alphabet.length; i++) {
            let lower = alphabet[i];
            let upper = lower.toUpperCase();
            input = input.replace(lower+upper, "");
            input = input.replace(upper+lower, "");
        }
    }
    if (length < shortestLength || shortestLength < 0) {
        shortestLength = length;
    }

    console.log(input);

}

console.log("Shortest length: " + shortestLength);