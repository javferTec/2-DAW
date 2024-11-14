const defaultErrorMessage = "Ocurrió un error, por favor inténtelo más tarde.";

function sendRequest(method, url, data = null, errorMessage = defaultErrorMessage) {
    const options = {
        method,
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        },
        body: data ? JSON.stringify(data) : null
    };

    return fetch(url, options)
        .then(response => {
            if (!response.ok) throw new Error(errorMessage);
            return response.json();
        })
        .catch();
}

const get = (url, errorMessage) => sendRequest("GET", url, null, errorMessage);
const post = (url, data, errorMessage) => sendRequest("POST", url, data, errorMessage);
const put = (url, data, errorMessage) => sendRequest("PUT", url, data, errorMessage);
const del = (url, errorMessage) => sendRequest("DELETE", url, null, errorMessage);
