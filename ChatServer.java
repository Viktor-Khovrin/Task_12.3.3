import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
    ArrayList<Client> clients = new ArrayList<>();
    ServerSocket server;

    ChatServer() throws IOException{
        // создаем серверный сокет на порту 1234
        server = new ServerSocket(1234);
    }

    void sendAll(String message){
        for (Client client : clients){
            client.receive(message);
        }
    }

    public void run(){
        int numOfClients;
        while (true) {
                System.out.println("Waiting...");
            try {
                // запускаем поток
                Socket socket = server.accept();
                // ждем клиента из сети
                // создаем клиента на своей стороне
                clients.add(new Client(socket, this));
                numOfClients = clients.size();
                System.out.println("Client "+numOfClients+" connected!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws IOException {
        new ChatServer().run();
    }
}