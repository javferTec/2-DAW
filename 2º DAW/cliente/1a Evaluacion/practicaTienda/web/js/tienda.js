import { listaArticulos } from "./articulos.js";

let criterios = ["Sin ordenar", "Ascendente por precio", "Descendente por precio"];
let listaArticulosOrdenados;


function creaListaCriterios() {
    let lista = document.getElementById("criteriosOrdenacion");

    let iterable = 1;

    criterios.forEach((criterio) => {
        let opcion = document.createElement("option");
        opcion.text = criterio;
        opcion.value = iterable++;
        lista.add(opcion);
    });
}

function obtenerCriterioOrdenacion() {
    let lista = document.getElementById("criteriosOrdenacion");
    let indice = lista.selectedIndex;


    if (indice === 1) {
        listaArticulosOrdenados = [...listaArticulos].sort((a, b) => a.precio - b.precio);
    } else if (indice === 2) {
        listaArticulosOrdenados = [...listaArticulos].sort((a, b) => b.precio - a.precio);
    } else {
        listaArticulosOrdenados = listaArticulos;
    }

    return listaArticulosOrdenados;
}

function pintaArticulos(listaArticulosOrdenados) {
    let contenedor = document.getElementById("contenedor");

    listaArticulosOrdenados.forEach(articulos => {
        let card = `
              <div class="col mb-4">
                <div class="card mb-2">
                  <img src="./assets/${articulos.codigo}.jpg" class="card-img-top" alt="">
                  <div class="card-body">
                    <h5 class="card-title">${articulos.nombre}</h5>
                    <p class="card-text">${articulos.descripcion}</p>
                    <b>
                      <p class="card-text text-center">${articulos.precio}€</p>
                    </b>
                  </div>
                  <button id="${articulos.codigo}" class="btn btn-success">Comprar</button>
                </div>
              </div>
        `;
        contenedor.innerHTML += card;
    });
}

function ponArticuloEnCarrito() {
    console.log("Artículo añadido al carrito");
}

function verCarro() {
    console.log("Mostrando el carrito");
}

function efectuaPedido() {
    console.log("Pedido realizado");
}

window.onload = () => {
    creaListaCriterios();

    let lista = document.getElementById("criteriosOrdenacion");
    lista.addEventListener("change", () => {
        listaArticulosOrdenados = obtenerCriterioOrdenacion();
        pintaArticulos(listaArticulosOrdenados);
    });
};
