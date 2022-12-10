import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.text.*;
import java.util.*;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public static void main(String[] args) throws IOException {
        new Server();
    }
    public Server() throws IOException {
        this.start(6666);
        // this.stop(); // TODO: should we use this?
    }

    protected void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true) {
        clientSocket = null;

        try {
            clientSocket = serverSocket.accept();
            System.out.println("A new client is connected : " + clientSocket);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // obtaining input and out streams
            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

            String greeting = in.readLine();
            out.println(greeting);
            System.out.println("Assigning new thread for this client");

            // create a new thread object
            Thread t = new ClientHandler(clientSocket, dis, dos);

            // Invoking the start() method
            t.start();
        } catch (Exception e) {
            clientSocket.close();
            e.printStackTrace();
        }
        }
        }

    private static class ClientHandler extends Thread
    {
        DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat fortime = new SimpleDateFormat("hh:mm:ss");
        final DataInputStream dis;
        final DataOutputStream dos;
        final Socket s;


        // Constructor
        public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)
        {
            this.s = s;
            this.dis = dis;
            this.dos = dos;
        }

        @Override
        public void run()
        {
            String received;
            String toreturn;
            while (true)
            {
                try {

                    // Ask user what he wants
                    dos.writeUTF("What do you want?[Date | Time]..\n"+
                            "Type Exit to terminate connection.");

                    // receive the answer from client
                    received = dis.readUTF();

                    if(received.equals("Exit"))
                    {
                        System.out.println("Client " + this.s + " sends exit...");
                        System.out.println("Closing this connection.");
                        this.s.close();
                        System.out.println("Connection closed");
                        break;
                    }

                    // creating Date object
                    Date date = new Date();

                    // write on output stream based on the
                    // answer from the client
                    switch (received) {

                        case "Date" :
                            toreturn = fordate.format(date);
                            dos.writeUTF(toreturn);
                            break;

                        case "Time" :
                            toreturn = fortime.format(date);
                            dos.writeUTF(toreturn);
                            break;

                        default:
                            dos.writeUTF("Invalid input");
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try
            {
                // closing resources
                this.dis.close();
                this.dos.close();

            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

}
