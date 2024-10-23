//let formulario = document.forms["form"];
let formulario = document.getElementById("form");
let nombre = document.getElementById("name");
let email = document.getElementById("email");
let nameError = document.getElementById("nameError");
let emailError = document.getElementById("emailError");

function validarNombre() {
    if (nombre.value === "" || nombre.value.length < 5) {
        nameError.innerHTML = "Por favor, pon un nombre válido, al menos 5 caracteres";
        return false;
    }
    nameError.innerHTML = "";
    return true;

}

function validarEmail() {
    let regexEmail = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;

    if (email.value === "" || email.value.length > 8) {
        if (!regexEmail.test(email.value)) {
            emailError.innerHTML = "Por favor, pon un formato de email válido";
        }
        emailError.innerHTML = "";
        return true;
    } else {
        emailError.innerHTML = "Por favor, pon un email válido al menos 8 caracteres";

    }
    return false;
}

formulario.addEventListener("submit", validarFormulario);

function validarFormulario(event) {
    event.preventDefault();

    if (validarNombre() && validarEmail()) {
        alert("Formulario enviado");
        event.target.submit()
        nameError.innerHTML = "";
        emailError.innerHTML = "";
        nombre.value = "";
        email.value = "";
        return true;
    }

    return false;
}

