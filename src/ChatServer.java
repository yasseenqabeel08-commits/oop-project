
    import java.io.*;
import java.net.*;
import java.util.*;

    public class ChatServer {
        static List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());

        public static void main(String[] args) throws Exception {
            ServerSocket server = new ServerSocket(5000);
            System.out.println("Server started...");

            while (true) {
                Socket socket = server.accept();
                ClientHandler client = new ClientHandler(socket);
                clients.add(client);
                new Thread(client).start();
            }
        }

        static class ClientHandler implements Runnable {
            Socket socket;
            BufferedReader in;
            PrintWriter out;

            ClientHandler(Socket socket) throws Exception {
                this.socket = socket;
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
            }

            public void run() {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        System.out.println("Server received: " + msg);
                        synchronized (clients) {
                            for (ClientHandler c : clients) {
                                c.out.println(msg);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // ✅ Remove client when they disconnect
                    clients.remove(this);
                    try { socket.close(); } catch (Exception ignored) {}
                    System.out.println("Client disconnected. Remaining: " + clients.size());
                }
            }
        }
    }

