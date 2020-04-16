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
        movinientoInstance.descuento = json.descuento as double
        movinientoInstance.total = 0
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy") // 01-01-2020
        movinientoInstance.fechaVendido = sdf.parse(json.fechaVendido as String)
        List<Detalle> detalles = Arrays.asList(json.detalles) as List<Detalle>


        if (movinientoInstance.id != null){
            for (int i = 0; i < detalles.size(); i++) {
                Detalle detalle = detalles.get(i)
                detalle.cantidad = detalles[i].cantidad
                if (detalles[i]?.articulo instanceof Integer) {
                    detalle.articulo = articuloService.get(detalles[i]?.articulo as Long)
                } else {
                    detalle.articulo = articuloService.get(detalles[i].articulo.id as long)
                }
                if (detalle.articulo != null) {
                    detalle.precio = detalle.articulo.precio
                    double descuento = ((detalle.cantidad * detalle.precio)*movinientoInstance.descuento)
                    movinientoInstance.total += (detalle.cantidad * detalle.precio) - descuento
                    detalle.movimiento = movinientoInstance
                }
            }
        }else{
            for (int i = 0; i < detalles.size(); i++) {
                Detalle detalle = new Detalle()
                detalle.cantidad = detalles[i].cantidad
                if (detalles[i]?.articulo instanceof Integer) {
                    detalle.articulo = articuloService.get(detalles[i]?.articulo as Long)
                } else {
                    detalle.articulo = articuloService.get(detalles[i].articulo.id as long)
                }
                if (detalle.articulo != null) {
                    detalle.precio = detalle.articulo.precio
                    double descuento = ((detalle.cantidad * detalle.precio)*movinientoInstance.descuento)
                    movinientoInstance.total += (detalle.cantidad * detalle.precio) - descuento
                    detalle.movimiento = movinientoInstance
                }
            }
        }



        return movinientoInstance
    }

}
