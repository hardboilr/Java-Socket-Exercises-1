package Exercise3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author tobias
 */
public class EchoClient {
    
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 4321);
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out.println("TRANSLasdATE#mands");
        System.out.println(in.readLine());
    }
}
