// Función general para hacer las peticiones
function sendRequest(method, url, data = null, callback, errorMessage = "Ocurrió un error, por favor inténtelo más tarde.") {
    const xhr = new XMLHttpRequest();
    xhr.open(method, url, true);
    xhr.setRequestHeader("Content-Type", "application/json; charset=utf-8");
    xhr.responseType = "json";

    // Manejo de la respuesta
    xhr.onload = function() {
        if (xhr.status >= 200 && xhr.status < 300) {
            callback(null, xhr.response); // Llamar al callback con la respuesta si fue bien
        } else {
            callback(errorMessage, null); // Error genérico si no es un duplicado
        }
    };

    // Manejo de errores de conexión
    xhr.onerror = function() {
        callback(errorMessage, null); // Llamar al callback con el mensaje de error genérico
    };

    // Enviar la petición
    const requestData = data ? JSON.stringify(data) : null;
    xhr.send(requestData);
}

// Función que maneja la respuesta de las peticiones
function handleResponse(callback) {
    return (error, result) => {
        if (error) {
            console.error(error);
            alert(error);
            return;
        }
        callback(result); // Si la respuesta fue exitosa, pasarla al callback
    };
}

// Funciones específicas para cada tipo de petición
function get(url, callback) {
    sendRequest("GET", url, null, callback);
}

function post(url, data, callback) {
    sendRequest("POST", url, data, callback);
}

function put(url, data, callback) {
    sendRequest("PUT", url, data, callback);
}

function del(url, data, callback) {
    sendRequest("DELETE", url, data, callback);
}