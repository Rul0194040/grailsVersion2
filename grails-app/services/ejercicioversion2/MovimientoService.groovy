package ejercicioversion2

import grails.gorm.transactions.Transactional

import java.text.SimpleDateFormat

@Transactional
class MovimientoService {

    ArticuloService articuloService

    List<Movimiento> list() {
        return Movimiento.list()
    }

    Movimiento get(Long id) {
        Movimiento movimiento = Movimiento.get(id)
        return movimiento
    }

    Movimiento create(Map json) {
        Movimiento movimiento = new Movimiento()
        build(movimiento, json)
        movimiento.save(flush: true)
    }

    Movimiento update(long id, Map json) {
        Movimiento movimiento = get(id)
        build(movimiento, json)
        movimiento.save(flush: true)
    }

    Movimiento build(Movimiento movinientoInstance, Map json) {

        if(movinientoInstance.id == null){
            movinientoInstance = new Movimiento()
            movinientoInstance.total = 0
            movinientoInstance.fechaVendido = new Date()



            List detalles = json.detalles
            for (int i = 0; i < detalles.size(); i++) {
                Detalle detalle = new Detalle()
                detalle.cantidad = detalles[i].cantidad
                if (detalles[i]?.articulo instanceof Integer) {
                    detalle.articulo = articuloService.get(detalles[i]?.articulo)
                } else {
                    detalle.articulo = articuloService.get(detalles[i].articulo.id as long)
                }
                if (detalle.articulo != null) {
                    detalle.precio = detalle.articulo.precio
                    movinientoInstance.total += (detalle.cantidad * detalle.precio)*((detalle.cantidad * detalle.precio)*movinientoInstance.descuento)
                    detalle.movimiento = movinientoInstance
                    movinientoInstance.addToDetalles(detalle)
                }
            }


        }else{
            movinientoInstance.total = 0

            List detalles = json.detalles
            println "detalles: $detalles"
            for (int i = 0; i < detalles.size(); i++) {
                Detalle detalle
                if (detalles[i].id)
                    detalle = Detalle.get(detalles[i].id)
                else
                    detalle = new Detalle()

                detalle.cantidad = detalles[i].cantidad
                if (detalles[i]?.articulo instanceof Integer) {
                    detalle.articulo = Articulo.get(detalles[i].articulo as long)
                } else {
                    detalle.articulo = Articulo.get(detalles[i].articulo.id as long)
                }
                if (detalle.articulo != null) {
                    detalle.precio = detalle.articulo.precio
                    movinientoInstance.total += (detalle.cantidad * detalle.precio)*((detalle.cantidad * detalle.precio)*movinientoInstance.descuento)

                    if (detalles[i].id == null) {
                        detalle.movimiento = movinientoInstance
                        movinientoInstance.addToDetalles(detalle)
                    }
                }
            }
        }

        return movinientoInstance
    }

}
