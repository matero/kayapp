package kayapp

import jakarta.servlet.Servlet
import kayak.AppPort
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import java.util.logging.Level
import java.util.logging.Logger

fun ServletContextHandler.addServlet(servlet: Servlet, pathSpec: String) = addServlet(ServletHolder(servlet), pathSpec)

fun ServletContextHandler.servlets(vararg servletsSpecification: Pair<String, Servlet>) {
  servletsSpecification.forEach {
    this.addServlet(ServletHolder(it.second), it.first)
  }
}

object Application {
  @JvmStatic
  fun main(args: Array<String>) {
    val server = makeHttpServer()

    try {
      server.start()
      server.join()
    } catch (failure: Exception) {
      Logger.getLogger("kayapp").log(Level.SEVERE, "failure detected", failure)
    } finally {
      server.destroy()
    }
  }

  private fun makeHttpServer(): Server {
    val server = Server(AppPort.value)
    server.handler = makeContextHandler()
    return server
  }

  private fun makeContextHandler(): ServletContextHandler {
    val ctx = ServletContextHandler(ServletContextHandler.NO_SESSIONS)
    ctx.contextPath = "/"
    ctx.servlets(
      "/hi" to Hi,
      "/api/v1/clients/*" to kayapp.clients.Controller()
    )
    return ctx
  }
}
