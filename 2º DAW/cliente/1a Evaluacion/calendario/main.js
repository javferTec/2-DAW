window.onload = function() {
    calendario();
};

function calendario() {

    let fecha = new Date();
    let dia = fecha.getDate();
    let mes = fecha.getMonth();
    let anyo = fecha.getFullYear();
    let diasMes = new Date(anyo, mes + 1, 0).getDate();
    
    fecha.setDate(1);
    let primerDia = fecha.getDay();

    let diasSemana = ['L', 'M', 'X', 'J', 'V', 'S', 'D'];
    let meses = ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
        'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'
    ];

    let calendarioHtml;

    calendarioHtml = "<table border='1'>";
    calendarioHtml += "<tr>";
    calendarioHtml += "<th colspan='7'>" + meses[mes] + " " + anyo + "</th>";
    calendarioHtml += "</tr>";
    calendarioHtml += "<tr>";

    for (let i = 0; i < diasSemana.length; i++) {
        calendarioHtml += "<td>" + diasSemana[i] + "</td>";
    }

    calendarioHtml += "</tr>";
    calendarioHtml += "<tr>";

    let diaSemana = (primerDia + 6) % 7;
    for (let i = 0; i < diaSemana; i++) {
        calendarioHtml += "<td></td>";
    }

    for (let i = 1; i <= diasMes; i++) {
        if (diaSemana % 7 == 0) {
            calendarioHtml += "</tr><tr>";
        }
        if (i == dia) {
            calendarioHtml += "<td style='background-color: #FF0000;'>" + i + "</td>";
        } else {
            calendarioHtml += "<td>" + i + "</td>";
        }
        diaSemana++;
    }

    calendarioHtml += "</tr>";
    calendarioHtml += "</table>";

    document.getElementById('calendario').innerHTML = calendarioHtml;
}