package kayapp.clients

import kayak.json.Json
import kayak.web.LongPathParameter
import kayak.web.Request
import kayak.web.Response

class Controller(private val clients: Clients = Clients()) : kayak.web.HttpControllerServlet() {

  private val byId = path("/{id}")

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
    val allClients = clients.all()
    val clientsAsJson = Json.of(allClients.map { it.asJson() })
    response.writeJson(clientsAsJson)
  }

  fun getClientById(request: Request, response: Response) {
    val pathParameter = request[id]
    val clientId = Client.Id.of(pathParameter)

    if (clientId.failed)
      response.notFound()
    else {
      val client = clients.get(clientId.value)
      if (client.ok) {
        response.writeJson(client.value.asJson())
      } else {
        response.notFound()
      }
    }
  }

  override fun doPost(request: Request, response: Response) {
    when {
      request.isIndex() ->
        create(request, response)
      else ->
        unhandledPost(request, response)
    }
  }

  fun create(request: Request, response: Response) {
    val jsonSpec = request.jsonBody
    val client = clients.createClientUsing(jsonSpec)
    if (client.ok)
      response.writeJson(client.value.asJson())
    else
      response.unprocessableEntity(client.failure)
  }

  companion object {
    private val id = LongPathParameter("id")
  }
}

