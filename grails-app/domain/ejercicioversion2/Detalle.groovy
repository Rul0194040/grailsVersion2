package ejercicioversion2

class Detalle {


    double precio
    double cantidad
    Articulo articulo

    static belongsTo = [movimiento: Movimiento]

    static constraints = {
        cantidad(min: 1d)
    }

    Map obtieneDatos() {[
            did: this.id,
            dversion: this.version,
            precio: precio,
            cantidad: cantidad,
            articulo: articulo.obtieneDatos()
    ]}
}
