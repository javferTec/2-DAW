let alumnos = ["Pepe", "Jose", "Maria", "Ana", "Luis"];

function verAlumnos() {
    for (let i = 0; i < alumnos.length; i++) {
        console.log(alumnos[i]);
    }
}
    
function generarTabla() {
    let tabla = "<table border='1'><tr><td>Nombre</td><td>Acción</td></tr>";
    for (let i = 0; i < alumnos.length; i++) {
        tabla += "<tr><td>" + alumnos[i] + "</td><td><button onclick='eliminarAlumno(" + i + ")'>Eliminar</button><button onclick='actualizarAlumno(" + i + ")'>Actualizar</button></td></tr>";
    }
    tabla += "</table>";
    document.getElementById("tabla").innerHTML = tabla;
}

function eliminarAlumno(posicion) {
    alumnos.splice(posicion, 1);
    generarTabla();
}

function actualizarAlumno(posicion) {
    let nuevoNombre = prompt("Introduce el nuevo nombre");
    alumnos[posicion] = nuevoNombre;
    generarTabla();
}

function insertarAlumno() {
    let nuevoNombre = prompt("Introduce el nuevo nombre");
    let pos = alumnos.indexOf(nuevoNombre);
    if (pos != -1 || nuevoNombre == "") {
        alert("El alumno ya existe o el nombre está vacío");
    } else {
        alumnos.push(nuevoNombre);
    }
    generarTabla();
}

