package kayapp.clients

import kayak.json.JsonArray
import kayak.web.LongPathParameter
import kayak.web.Request
import kayak.web.Response

class Controller : kayak.web.HttpControllerServlet() {
  private val byId = path("/{id}")

  private val repository = Repository()

  override fun doGet(request: Request, response: Response) {
    when {
      request.isIndex() ->
        index(request, response)
      request.matches(byId) ->
        getClientById(request, response)
      else ->
        unhandledGet(request, response)
    }
  }

  fun index(request: Request, response: Response) {
    val allClients = repository.all()
    val clientsAsJson = JsonArray.of(allClients.map { it.asJson() })
    response.writeJson(clientsAsJson)
  }

  fun getClientById(request: Request, response: Response) {
    val pathParameter = request[id]
    val clientId = Client.Id.of(pathParameter)

    if (clientId.failed)
      response.notFound()
    else {
      val client = repository.get(clientId.value)
      if (client.ok) {
        response.writeJson(client.value.asJson())
      } else {
        response.notFound()
      }
    }
  }

  companion object {
    private val id = LongPathParameter("id")
  }
}
