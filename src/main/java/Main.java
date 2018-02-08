import nchu.turbineServer.socket.MagnetServerSocket;

public class Main {
    public static void main(String[] args) {
        MagnetServerSocket serverSocket = new MagnetServerSocket();
        serverSocket.start();
    }
}
