package ServMeBread;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HTTPConnection
{
    private Socket socket;
    private boolean secured = false;

    private PrintWriter out;
    private BufferedReader in;
    private TokenReader tr;

    public HTTPConnection(Socket s) throws IOException
    {
        this.socket = s;
        this.out = new PrintWriter(this.socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.tr = new TokenReader(this.in);
    }
    public Socket getSocket()
    {
        return this.socket;
    }
    public boolean isSecured()
    {
        return this.secured;
    }
    public TokenReader getTokenReader()
    {
        return this.tr;
    }
    public PrintWriter getPrinter()
    {
        return this.out;
    }
}
