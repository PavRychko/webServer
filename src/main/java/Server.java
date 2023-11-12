import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


@AllArgsConstructor
@Setter
@Getter
public class Server {
    private int port;
    private String webAppPath;


    public void start() {
        try(ServerSocket serverSocket = new ServerSocket(port);){
            while (true){
                try(Socket socket = serverSocket.accept();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())))
                {
                RequestHandler requestHandler = new RequestHandler(reader, writer, webAppPath);
                requestHandler.handle();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
