package kayapp

import kayak.web.Request
import kayak.web.Response

class Hi : kayak.web.HttpControllerServlet() {
  override fun doGet(request: Request, response: Response) {
    response.writeText("hi :P")
  }
}
