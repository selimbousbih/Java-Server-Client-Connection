import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket socket;
    PrintWriter out;
    BufferedReader in;

    private String hostName = "127.0.0.1";
    private int portNumber = 5559;

    boolean stop = false;

    public Client() throws IOException {
        try {
            socket = new Socket(hostName, portNumber);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            System.out.println("Connection refused");
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        handle();
        while(true){
            System.out.println("Enter your msg: ");
            String a = stdIn.readLine();
            out.println(a);
            if(a.equals("Bye"))
                stop=true;

        }

    }

    public void handle() {
        new Thread(new Runnable() {
            public void run() {
                String fromServer;
                while(!stop){
                    try {
                        if((fromServer = in.readLine())!=null){
                            System.out.println(fromServer);
                        }
                    } catch (IOException ignored) {
                    }
                }
            }
        }).start();
    }
}
