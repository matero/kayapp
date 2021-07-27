package kayapp.clients

import kayak.json.Json
import kayak.web.*

class Controller(private val clients: Clients = Clients()) : HttpControllerServlet(), UseMimeTypes {

  private val byId = path("/{id}")

  override fun doGet(request: Request, response: Response) {
    if (request.accepts(mediaType=application/json)) {
      when {
        request.isIndex() ->
          index(request, response)
        request.matches(byId) ->
          getClientById(request, response)
        else ->
          unhandledGet(request, response)
      }
    } else {
      unhandledGet(request, response)
    }
  }

  fun index(request: Request, response: Response) {
    val allClients = clients.all()
    val clientsAsJson = Json.of(allClients.map { it.asJson() })
    response.writeJson(clientsAsJson)
  }

  fun getClientById(request: Request, response: Response) {
    val clientId = Client.Id.of(request[id])

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
    if (request.accepts(mediaType=application/json)) {
      when {
        request.isIndex() ->
          create(request, response)
        else ->
          unhandledPost(request, response)
      }
    } else {
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

