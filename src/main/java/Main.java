public class Main {

    public static void main(String[] args) {
        Server server = new Server(8000, "src/main/resources");
        server.start();
    }
}
