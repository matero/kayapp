package kayapp

import kayak.AppPort
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import java.util.logging.Level
import java.util.logging.Logger

object Application {
  @JvmStatic
  fun main(args: Array<String>) {
    val server = Server(AppPort.value)

    val ctx = ServletContextHandler(ServletContextHandler.NO_SESSIONS)
    ctx.contextPath = "/"
    ctx.addServlet(Hi::class.java, "/hi")
    ctx.addServlet(kayapp.clients.Controller::class.java, "/api/v1/clients/*")

    server.handler = ctx

    try {
      server.start()
      server.join()
    } catch (failure: Exception) {
      Logger.getLogger("kayapp").log(Level.SEVERE, "failure detected", failure)
    } finally {
      server.destroy()
    }
  }
}
