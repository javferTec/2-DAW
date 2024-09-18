function tabla() {
    let n = parseInt(prompt("Dime número: "));

    let miTabla = "<table>";

    for (let i = 1; i <= 10; i++) {
        let m = n * i;
        miTabla += `<tr><td>${n}</td><td> x </td><td> ${i} </td><td> = </td><td> ${m}</td></tr>`;
    }

    miTabla += "</table>";

    document.getElementById("tabla").innerHTML = miTabla;
}