//let formulario = document.forms["form"];
let formulario = document.getElementById("form");
let nombre = document.getElementById("name");
let email = document.getElementById("email");
let nameError = document.getElementById("nameError");
let emailError = document.getElementById("emailError");
let envioExitoso = document.getElementById("envioExitoso");

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
        capturarUrl();
        capturarUrlV2();
        //event.target.submit(); // COMENTAR PARA PODER VER LOS DATOS DE LA URL EN LA CONSOLA
        envioExitoso.innerHTML = "Formulario enviado con éxito";
        nameError.innerHTML = "";
        emailError.innerHTML = "";
        nombre.value = "";
        email.value = "";
        return true;
    }

    return false;
}

function capturarUrl() {
    let url = new URLSearchParams(window.location.search);
    let nombre = url.get("name");
    let email = url.get("email");

    console.log("Nombre: " + nombre);
    console.log("Email: " + email);
}

function capturarUrlV2() {
    let url = window.location.href;
    let paramsString = url.split("?")[1];
    let paramsArray = paramsString.split("&");
    let paramsObject = {};
    paramsArray.forEach(param => {
        let [key, value] = param.split("=");
        paramsObject[key] = value;
    });
    let nombre = paramsObject.name.split("+").join(" ");
    let email = paramsObject.email.split("%40").join("@");
    console.log(nombre + " - " + email);
}