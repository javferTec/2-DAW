const baseURL = "http://localhost:3000/alumnos";

window.onload = function() {
    cargarAlumnos();
    document.getElementById("nuevoAlumno").addEventListener("click", nuevoAlumno);
}

function cargarAlumnos() {
    get(baseURL)
        .then(alumnos => {
            pintarTablaAlumnos(alumnos);
        })
        .catch((error) => {
            alert(error);
        });
}

function pintarTablaAlumnos(alumnos) {
    let tablaBody = document.getElementsByTagName("tbody")[0];
    tablaBody.innerHTML = "";
    alumnos.forEach(alumno => {
        let tr = document.createElement("tr");
        tr.innerHTML = `
            <td>${alumno.id}</td>
            <td>${alumno.nombre}</td>
            <td>${alumno.curso}</td>
            <td>
                <button class="verAlumno btn btn-info btn-sm" id="${alumno.id}">Ver</button>
                <button class="editarAlumno btn btn-warning btn-sm" id="${alumno.id}">Editar</button>
                <button class="eliminarAlumno btn btn-danger btn-sm" id="${alumno.id}">Eliminar</button>
            </td>
        `;
        tablaBody.appendChild(tr);
    });

    // Asignar eventos a los botones después de crear las filas
    ["verAlumno", "editarAlumno", "eliminarAlumno"].forEach(className => {
        Array.from(document.getElementsByClassName(className)).forEach(button => {
            button.addEventListener("click", window[className]);
        });
    });
}

function nuevoAlumno() {
    let alumnoNuevo = {
        "id": 11,
        "nombre": "Manuel Santana",
        "curso": "Desarrollo",
        "email": "manuel@daw.es",
        "promedio": 8.5
    };

    post(baseURL, alumnoNuevo)
        .then(() => {
            cargarAlumnos();
        })
        .catch((error) => {
            alert("Error al añadir el alumno.");
        });
}

function verAlumno(event) {
    let id = event.target.id;

    get(`${baseURL}/${id}`)
        .then(alumno => {
            alert(
                `Información del alumno con ID ${id}:\n\n` +
                `Nombre: ${alumno.nombre}\n` +
                `Curso: ${alumno.curso}\n` +
                `Email: ${alumno.email}\n` +
                `Promedio: ${alumno.promedio}`
            );
        })
        .catch((error) => {
            alert(error);
        });
}

function editarAlumno(event) {
    let alumnoEditado = {
        "id": 11,
        "nombre": "Manuel Santamaría",
        "curso": "Cliente",
        "email": "manuel@daw.com",
        "promedio": 10
    };

    let id = event.target.id;

    put(`${baseURL}/${id}`, alumnoEditado)
        .then(() => {
            cargarAlumnos();
        })
        .catch((error) => {
            alert(error);
        });
}

function eliminarAlumno(event) {
    let id = event.target.id;

    del(`${baseURL}/${id}`, "Error al eliminar el alumno.")
        .then(() => {
            cargarAlumnos();
        })
        .catch((error) => {
            alert(error);
        });
}