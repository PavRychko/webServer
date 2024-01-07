import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


@Slf4j
@AllArgsConstructor
@Setter
@Getter
public class Server {
    private int port;
    private String webAppPath;


    public void start() {
        log.info("starting server");
        try (ServerSocket serverSocket = new ServerSocket(port);) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                    RequestHandler requestHandler = new RequestHandler(reader, writer, webAppPath);
                    requestHandler.handle();
                } catch (Exception e) {
                    log.error("Exception occurred while handling request, message: \n" + e.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("Exception occurred while starting server, message: \n" + e.getMessage());
        }
    }
}
