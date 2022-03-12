import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

class Client implements Runnable {
    Socket socket;
    ChatServer server;
    Scanner in;
    PrintStream out;

    public Client(Socket socket, ChatServer server){
        this.socket = socket;
        this.server = server;
        try{
            // получаем потоки ввода и вывода
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            // создаем удобные средства ввода и вывода
            this.in = new Scanner(is);
            this.out = new PrintStream(os);
        }catch (IOException e){
            e.printStackTrace();
        }
        new Thread(this).start();
    }
    void receive(String message){
        out.println(message);
    }
    public void run() {
        String number;
        try {
            number = Integer.toString(server.clients.indexOf(this) + 1);
            // читаем из сети и пишем в сеть
            out.println("Welcome to chat!");
            server.sendAll(number + " is joined to us!");
            number +=": ";
            String input =  number + in.nextLine();
            while (!input.equals(number +"bye")) {
                server.sendAll(input);
                input = number + in.nextLine();
            }
            server.sendAll("Client "+ number+" is leave us...");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}