let criterios = ["Sin ordenar", "Ascendente por precio", "Descendente por precio"];
let listaArticulosOrdenados = listaArticulos;
let carrito = new Carrito();

function creaListaCriterios() {
    let lista = document.getElementById("criteriosOrdenacion");

    criterios.forEach((criterio, indice) => {
        let opcion = document.createElement("option");
        opcion.text = criterio;
        opcion.value = indice
        lista.add(opcion);
    });

    lista.addEventListener("change", () => {
        listaArticulosOrdenados = obtenerCriterioOrdenacion();
        pintaArticulos(listaArticulosOrdenados);
    });
}

function obtenerCriterioOrdenacion() {
    let lista = document.getElementById("criteriosOrdenacion");
    let indice = lista.selectedIndex;


    if (indice === 3) { // descendente por precio
        listaArticulosOrdenados = [...listaArticulos].sort((a, b) => a.precio - b.precio);
    } else if (indice === 2) { // ascendente por precio
        listaArticulosOrdenados = [...listaArticulos].sort((a, b) => b.precio - a.precio);
    } else { // sin ordenar
        listaArticulosOrdenados = [...listaArticulos];
    }

    return listaArticulosOrdenados;
}

function pintaArticulos(listaArticulosOrdenados) {
    let contenedor = document.getElementById("contenedor");

    contenedor.innerHTML = "";

    listaArticulosOrdenados.forEach(articulos => {
        card += `
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
                  <button id="${articulos.codigo}" class="ponArticuloEnCarrito btn btn-success">Comprar</button>
                </div>
              </div>
        `;

        contenedor.innerHTML = card;
    });

    let botones = document.getElementsByClassName("ponArticuloEnCarrito");
    /*for (let boton of botones) {
        boton.addEventListener("click", () => {
            ponArticuloEnCarrito();
        });
    }*/

    Array.from(botones.forEach(boton => {boton.addEventListener("click", () => {ponArticuloEnCarrito();})}));

}

function ponArticuloEnCarrito() {
    let boton = event.target;
    let codigo = boton.id;
    let articulo = listaArticulos.find(articulo => articulo.codigo === codigo);
    carrito.anyadeArticulo(articulo);
}

function verCarro() {
    let botonCarrito = document.getElementById("carrito");
    botonCarrito.addEventListener("click", () => {
        carrito.verCarrito();
    });
}

function efectuaPedido() {
    let botonPedido = document.getElementById("btnEfectuaPedido");
    botonPedido.addEventListener("click", () => {
        console.log(JSON.stringify(carrito.articulos) + " - " + carrito.id);
    });
}

window.onload = () => {
    creaListaCriterios();
    pintaArticulos(listaArticulosOrdenados);
    carrito.id = 1111;
    verCarro();
    carrito.cerrarDialogo();
    efectuaPedido();
};
