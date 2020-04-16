package ejercicioversion2

class ArticuloController {
	static responseFormats = ['json', 'xml']
    ArticuloService articuloService

    def index() {
        List<Articulo> list = articuloService.list()
        long count = list.size()
        respond([data: list, count: count])
    }

    def show(long id) {
        Articulo articulo = articuloService.get(id)
        if (articulo == null) {
            respond(status: 400)
        } else {
            respond(articulo.obtieneDatos())
        }
    }

    def save() {
        Articulo articulo = articuloService.create(request.JSON as Map)
        respond(articulo)

    }

    def update(long id) {
        Articulo articulo = articuloService.update(id, request.JSON as Map)
        respond(articulo)
    }

    def delete() {

    }
}
