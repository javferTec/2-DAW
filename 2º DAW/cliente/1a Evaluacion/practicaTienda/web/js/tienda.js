let criterios = ["Sin ordenar", "Ascendente por precio", "Descendente por precio"];

function creaListaCriterios() {
    let lista = document.getElementById("criteriosOrdenacion");

    criterios.forEach((criterio) => {
        let opcion = document.createElement("option");
        opcion.text = criterio;
        opcion.value = criterio;
        lista.add(opcion);
    });
}

function pintaArticulos(orden) {
    let contenedor = document.getElementById("contenedor");

    listaArticulos.forEach(articulos => {
        let card = `
              <div class="col">
                <div class="card">
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
    pintaArticulos();
};
