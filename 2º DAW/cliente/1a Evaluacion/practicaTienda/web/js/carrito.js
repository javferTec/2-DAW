class Carrito {
    constructor(id) {
        this.id = id;
        this.articulos = [];
    }

    anyadeArticulo(articulo) {
        let codigoArticulo = articulo.codigo;
        if (this.articulos.some(articulo => articulo.codigo === codigoArticulo)) {
            articulo.unidades++;
        } else {
            articulo.unidades = 1;
            this.articulos.push(articulo);
        }
    }

    borraArticulo(codigo) {
        let indice = this.articulos.findIndex(articulo => articulo.codigo === codigo);
        this.articulos.splice(indice, 1);
        this.pintarArticulosCarrito();
    }

    modificaUnidades(codigo, operacion) {
        let articulo = this.articulos.find(articulo => articulo.codigo === codigo);
        if (operacion === "suma") {
            articulo.unidades++;
            this.pintarArticulosCarrito();
        } else if (operacion === "resta") {
            if (articulo.unidades > 1) {
                articulo.unidades--;
                this.pintarArticulosCarrito();
            } else {
                this.borraArticulo(codigo);
            }
        }
    }

    verCarrito() {
        if (carrito.articulos.length === 0) {
            alert("El carrito está vacío");
        } else {
            let dialog = document.getElementById("miDialogo");
            this.pintarArticulosCarrito();
            dialog.showModal();

            let idPedido = document.getElementById("idPedido");
            idPedido.innerHTML = this.id;
        }
    }

    pintarArticulosCarrito() {
        let dialogoContenido = document.getElementById("dialogContent");
        dialogoContenido.innerHTML = "";

        let tablaDentroDiv = `
        <div class="card mb-2">
            <table class="table">
                <tr>
                    <th>Nombre</th>
                    <th>Precio</th>
                    <th>Unidades</th>
                    <th>Total</th>
                    <th>Acciones</th>
                </tr>
    `;

        carrito.articulos.forEach(articulo => {
            tablaDentroDiv += `
                <tr>
                    <td>${articulo.nombre}</td>
                    <td>${articulo.precio}€</td>
                    <td>${articulo.unidades}</td>
                    <td>${articulo.precio * articulo.unidades}€</td>
                    <td>
                        <button id="${articulo.codigo}" class="sumaUnidades btn btn-primary">+</button>
                        <button id="${articulo.codigo}" class="restaUnidades btn btn-warning">-</button>
                        <button id="${articulo.codigo}" class="borraArticulo btn btn-danger">Borrar</button>
                    </td>
                </tr>
        `;
        });

        tablaDentroDiv += `
            </table>
        </div>
    `;

        dialogoContenido.innerHTML = tablaDentroDiv;

        let botones = document.getElementsByClassName("sumaUnidades");
        for (let boton of botones) {
            boton.addEventListener("click", () => {
                carrito.modificaUnidades(boton.id, "suma");
            });
        }

        botones = document.getElementsByClassName("restaUnidades");
        for (let boton of botones) {
            boton.addEventListener("click", () => {
                carrito.modificaUnidades(boton.id, "resta");
            });
        }

        botones = document.getElementsByClassName("borraArticulo");
        for (let boton of botones) {
            boton.addEventListener("click", () => {
                carrito.borraArticulo(boton.id);
            });
        }

        let total = document.getElementById("total");
        let suma = 0;
        carrito.articulos.forEach(articulo => {
            suma += articulo.precio * articulo.unidades;
        });
        total.innerHTML = `${suma}€`;
    }

    cerrarDialogo() {
        let btnCerrarDialog = document.getElementById("btnCierraDialog");
        btnCerrarDialog.addEventListener("click", () => {
            let dialog = document.getElementById("miDialogo");
            dialog.close();
        });
    }
}
