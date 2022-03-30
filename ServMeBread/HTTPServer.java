package ServMeBread;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer
{
    private ServerSocket ss;
    private HTTPMethodHandler GET;
    private HTTPMethodHandler POST;
    

    public HTTPServer(int port) throws IOException
    {
        this.ss = new ServerSocket(port);
        this.GET = new HTTPMethodHandler("GET");
        this.POST = new HTTPMethodHandler("POST");
        
    }


    public void get(String path, HTTPController controller)
    {
        this.GET.addHandler(path, controller);
    }
    public void post(String path, HTTPController controller)
    {
        this.POST.addHandler(path, controller);
    }

    public HTTPResponse handleGet(HTTPRequest req)
    {
        return this.GET.handle(req);
    }
    public HTTPResponse handlePost(HTTPRequest req)
    {
        return this.POST.handle(req);
    }

    public void Loop() throws IOException
    {
        while(true)
        {
            Socket s = this.ss.accept();
            HTTPConnection conn = new HTTPConnection(s);
            HTTPHandler hConn = new HTTPHandler(conn, this);
            hConn.start();
        }
    }
}
