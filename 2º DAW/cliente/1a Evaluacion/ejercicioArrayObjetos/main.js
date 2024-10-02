let alumnos = [
    {
        codigo: "DAW-1-2020",
        nombre: "Pepe",
        ciudad: "Valencia",
        calificaciones: [
            { asignatura: "PRG", nota: 9 },
            { asignatura: "GBD", nota: 6 },
            { asignatura: "ING", nota: 4 },
            { asignatura: "FOL", nota: 3 },
        ],
        edad: 24,
    },
    {
        codigo: "DAW-2-2020",
        nombre: "Juan",
        ciudad: "Castellon",
        calificaciones: [
            { asignatura: "DWC", nota: 4 },
            { asignatura: "DWS", nota: 6 },
            { asignatura: "DAW", nota: 7 },
            { asignatura: "DIW", nota: 8 },
            { asignatura: "ING", nota: 9 },
            { asignatura: "EIE", nota: 2 },
        ],
        edad: 28,
    },
    {
        codigo: "DAW-1-2019",
        nombre: "Ana",
        ciudad: "Valencia",
        calificaciones: [
            { asignatura: "PRG", nota: 6 },
            { asignatura: "GBD", nota: 2 },
            { asignatura: "ING", nota: 6 },
            { asignatura: "FOL", nota: 2 },
        ],
        edad: 22,
    },
    {
        codigo: "DAW-2-2020",
        nombre: "Maria",
        ciudad: "Castellon",
        calificaciones: [
            { asignatura: "DWC", nota: 4 },
            { asignatura: "DWS", nota: 6 },
            { asignatura: "DAW", nota: 7 },
            { asignatura: "DIW", nota: 8 },
            { asignatura: "ING", nota: 4 },
            { asignatura: "EIE", nota: 1 },
        ],
        edad: 30,
    },
];

function notasAlumno() {
    var nombre = prompt("Introduce el nombre del alumno");

    let alumno = alumnos.find((alumno) => alumno.nombre === nombre);
    if (!alumno) {
        console.log("No se ha encontrado el alumno");
        return;
    }

    let notaMin = 10;
    let notaMax = 0;
    let notaMedia = 0;
    let nomAsigMin = "";
    let nomAsignMax = "";

    alumno.calificaciones.forEach((calificacion) => {
        if (calificacion.nota < notaMin) {
            notaMin = calificacion.nota;
            nomAsigMin = calificacion.asignatura;
        }

        if (calificacion.nota > notaMax) {
            notaMax = calificacion.nota;
            nomAsignMax = calificacion.asignatura;
        }

        notaMedia += calificacion.nota;
    });

    notaMedia = notaMedia / alumno.calificaciones.length;

    console.log("Nota media: " + notaMedia);
    console.log("Nota menor: " + notaMin + " en la asignatura " + nomAsigMin);
    console.log("Nota mayor: " + notaMax + " en la asignatura " + nomAsignMax);
}

function subeUnPunto() {
    alumnos.forEach((alumno) => {
        alumno.calificaciones.forEach((calificacion) => {
            if (calificacion.nota < 10) {
                calificacion.nota++;
            }
        });
    });

    console.log("Se ha subido un punto a todas las notas");
    console.log(alumnos);
}
