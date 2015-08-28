package Exercise3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author tobias
 */
public class EchoServer implements Runnable {

    Socket s;
    BufferedReader in;
    String echo;
    PrintWriter out;
    String upper;
    Map<String, String> words;

    public EchoServer(Socket soc) {
        s = soc;
        words = new HashMap();
        words.put("hund", "dog");
        words.put("kat", "cat");
        words.put("mand", "man");
        words.put("kvinde", "woman");
    }

    public static void main(String[] args) throws IOException {
        String ip = "localhost";
        int port = 4321;
        if (args.length == 2) {
            ip = args[0];
            port = Integer.parseInt(args[1]);
        }
        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress(ip, port));
        while (true) {
            EchoServer e = new EchoServer(ss.accept());
            Thread t1 = new Thread(e);
            t1.start();
        }
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), true);
            while (true) {
                echo = in.readLine();
                if (echo.length() >= 6) {
                    try {
                        if (echo.substring(0, 6).equals("UPPER#")) {
                            out.println(echo.substring(6).toUpperCase());
                            break;
                        } else if (echo.substring(0, 6).equals("LOWER#")) {
                            out.println(echo.substring(6).toLowerCase());
                            break;
                        } else if (echo.substring(0, 8).equals("REVERSE#")) {
                            out.println(new StringBuilder(echo.substring(8)).reverse());
                            break;
                        } else if (echo.substring(0, 10).equals("TRANSLATE#")) {
                            if (words.containsKey(echo.substring(10).toLowerCase())) {
                                out.println(words.get(echo.substring(10).toLowerCase()));
                                break;
                            } else {
                                out.println("#NOT_FOUND");
                            }
                        } else {
                            s.close();
                        }
                    } catch (StringIndexOutOfBoundsException e) {
                        //do nothing
                    }
                } else {
                    s.close();
                }

            }
        } catch (IOException e) {
        }
    }
}
