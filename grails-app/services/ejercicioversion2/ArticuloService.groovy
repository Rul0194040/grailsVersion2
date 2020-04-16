package ejercicioversion2

import grails.gorm.transactions.Transactional

import java.text.SimpleDateFormat

@Transactional
class ArticuloService {

    List<Articulo> list() {
        return Articulo.list()
    }

    Articulo get(Long id) {
        Articulo articulo = Articulo.get(id)
        if(!articulo.activo) {
            return null
        }

        return articulo
    }

    Articulo build(Articulo articuloInstance, Map json) {
        articuloInstance.clave = json.clave
        articuloInstance.nombre = json.nombre
        articuloInstance.descripcion = json.descripcion
        articuloInstance.precio = json.precio as double

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy") // 01-01-2020
        articuloInstance.fechaAlta = sdf.parse(json.fechaAlta as String)

        articuloInstance.activo = json.activo
        return articuloInstance
    }

    Articulo create(Map json) {
        Articulo articulo = new Articulo()
        build(articulo, json)
        articulo.save(flush: true)
    }

    Articulo update(long id, Map json) {
        Articulo articulo = get(id)
        build(articulo, json)
        articulo.save(flush: true)
    }



}
