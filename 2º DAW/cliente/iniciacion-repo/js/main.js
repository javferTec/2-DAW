import { articulos } from "./datos.js";
import { verMensaje } from "./utilidades.js";

window.onload = function() {
    verMensaje()
    alert("Total articulos: " + articulos.length)
}

let miBoton=document.getElementById("btn1")
miBoton.addEventListener("click", verMensaje)
// miBoton.onclick = verMensaje