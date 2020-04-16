package ejercicioversion2

class MovimientoController {
    static responseFormats = ['json', 'xml']
    MovimientoService movimientoService

    def index() {
        List<Movimiento> list = movimientoService.list()
        long count = list.size()
        respond([data: list, count: count])
    }

    def show(long id) {
        Movimiento movimiento = movimientoService.get(id)
        if (movimiento == null) {
            respond(status: 400)
        } else {
            respond(movimiento.obtieneDatos())
        }
    }

    def save() {
        Movimiento movimiento = movimientoService.create(request.JSON as Map)
        respond(movimiento)

    }

    def update(long id) {
        Movimiento movimiento = movimientoService.update(id, request.JSON as Map)
        respond(movimiento)
    }

    def delete() {

    }
}