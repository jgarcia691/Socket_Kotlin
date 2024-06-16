import java.io.*
import java.net.Socket

fun main() {
    val host = "localhost"
    val port = 12345

    try {
        val socket = Socket(host, port)
        val input = BufferedReader(InputStreamReader(socket.getInputStream()))
        val output = PrintWriter(socket.getOutputStream(), true)

        println("Conectado al servidor en $host:$port")

        // Crear un hilo para leer mensajes del servidor
        Thread {
            try {
                var serverMessage: String?
                while (input.readLine().also { serverMessage = it } != null) {
                    println("Respuesta del servidor: $serverMessage")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()

        // Leer mensajes del usuario y enviarlos al servidor
        while (true) {
            val userMessage: String? = "HOLA BB"

            if (userMessage.equals("exit", ignoreCase = true)) {
                break
            }
            output.println(userMessage)
            Thread.sleep(1000)
        }

        socket.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}
