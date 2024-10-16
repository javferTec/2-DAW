class Carrito {
    constructor(id) {
        this.id = id;
        this.articulos = [];
    }

    anyadeArticulo(articulo) {
        console.log("Artículo añadido al carrito");
    }

    borraArticulo(codigo) {
        console.log("Artículo borrado del carrito");
    }

    modificaUnidades(codigo, operacion) {
        console.log("Unidades modificadas");
    }

    verCarrito() {
        console.log("Carrito visualizado");
    }
}
