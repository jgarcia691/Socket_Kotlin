import java.io.*
import java.net.ServerSocket
import java.net.Socket

class SocketServer(private val port: Int) {
    fun start() {
        val serverSocket = ServerSocket(port)
        println("Servidor iniciado en el puerto $port")

        while (true) {
            val clientSocket = serverSocket.accept()
            println("Cliente conectado: ${clientSocket.inetAddress.hostAddress}")

            // Crear un hilo para manejar la conexión del cliente
            Thread(ClientHandler(clientSocket)).start()
        }
    }
}

class ClientHandler(private val clientSocket: Socket) : Runnable {
    override fun run() {
        try {
            val input = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
            val output = PrintWriter(clientSocket.getOutputStream(), true)

            // Leer mensajes del cliente
            var message: String?
            while (input.readLine().also { message = it } != null) {
                println("Mensaje recibido: $message")
                output.println("Mensaje recibido: $message")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                clientSocket.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}

fun main() {
    val port = 12345
    val server = SocketServer(port)
    server.start()
}
