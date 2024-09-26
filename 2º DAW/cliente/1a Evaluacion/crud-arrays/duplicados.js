let numeros = [1, 2, 3, 4, 5, 1, 2, 3];

function verNumeros() {
    for (let i = 0; i < numeros.length; i++) {
        document.getElementById("duplicados").innerHTML += numeros[i] + ", ";
        console.log(numeros[i]);
    }
}

function eliminarNumerosDuplicados() {
    let numerosSinDuplicados = numeros.filter((item, index) => numeros.indexOf(item) === index);
    numeros = numerosSinDuplicados;
    document.getElementById("duplicados").innerHTML = "";
    verNumeros();
}
