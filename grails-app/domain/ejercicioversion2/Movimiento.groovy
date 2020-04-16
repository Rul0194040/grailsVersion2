package ejercicioversion2

class Movimiento {

    double descuento
    double total
    Date fechaVendido

    static hasMany = [detalles: Detalle]

    static constraints = {
        descuento(min: 0d, max: 0.15d)
        total(min: 3d)
    }

    Map obtieneDatos() {
        [
                id          : id, version: version,
                total       : total,
                descuento   : descuento,
                fechaVendido: fechaVendido.format("dd-MM-yyyy"),
                detalles    : detalles*.obtieneDatos()
        ]
    }
}
