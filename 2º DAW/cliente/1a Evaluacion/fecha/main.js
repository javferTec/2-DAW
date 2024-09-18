window.onload = function() {
    fecha();
}

function fecha() {
    let fecha = new Date();
    let dia = fecha.getDate();
    let mes = fecha.getMonth();
    let año = fecha.getFullYear();
    let diaSemana = fecha.getDay();

    let dias = ["Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"];

    let meses = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
        "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];

    let fechaTexto = "Hoy es " + dias[diaSemana] + " " + dia + " de " + meses[mes] + " de " + año;

    document.getElementById("fecha").innerHTML = fechaTexto;
}