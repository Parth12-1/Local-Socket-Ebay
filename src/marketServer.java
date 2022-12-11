import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.JSONObject;

public class marketServer {
    public static void main(String[] args) throws Exception {
        // Create a server socket
        ServerSocket serverSocket = new ServerSocket(1000);
        Boolean running = true;
        while (running) {
            Socket socket = serverSocket.accept();
            Server server = new Server(socket);
            server.start();
        }
        serverSocket.close();
    }
}
