function compararFechas() {
    /*var fecha1 = document.getElementById("fecha1").value;
    var fecha2 = document.getElementById("fecha2").value;

    fecha1 = fecha1.split("/");
    fecha2 = fecha2.split("/");

    fecha1 = fecha1[2] + fecha1[1] + fecha1[0];
    fecha2 = fecha2[2] + fecha2[1] + fecha2[0];*/

    // CODIGO REDUCIDO
    var fecha1 = document.getElementById("fecha1").value.split("/").reverse().join("");
    var fecha2 = document.getElementById("fecha2").value.split("/").reverse().join("");

    console.log("fecha1: " + fecha1);
    console.log("fecha2: " + fecha2);


    /*if (fecha1 > fecha2) {
        document.getElementById("resultado").innerHTML = "La fecha 1 es mayor que la fecha 2";
    } else if (fecha1 < fecha2) {
        document.getElementById("resultado").innerHTML = "La fecha 2 es mayor que la fecha 1";
    } else {
        document.getElementById("resultado").innerHTML = "Las fechas son iguales";
    }*/

    // CODIGO REDUCIDO
    var resultado = fecha1 > fecha2 ? "La fecha 1 es mayor que la fecha 2" :
        fecha1 < fecha2 ? "La fecha 2 es mayor que la fecha 1" :
            "Las fechas son iguales";

    document.getElementById("resultado").innerHTML = resultado;

}
