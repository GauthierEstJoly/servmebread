package ServMeBread;
import java.io.IOException;

public class HTTPHandler extends Thread
{
    private HTTPConnection conn;
    private HTTPServer serv;

    public HTTPHandler(HTTPConnection conn, HTTPServer serv)
    {
        this.conn = conn;
        this.serv = serv;
    }

    public void handleRequest() throws IOException
    {
        TokenReader tk = this.conn.getTokenReader();

        HTTPRequest req = HTTPRequest.fromTokenReader(tk);
        if ( req.isInvalide )
        {
            System.out.println(String.format("[       | %-30s |          ]", "INVALIDE REQUEST"));
            return;
        }
        System.out.println(String.format("[ %5s | %-30s | %8s ]", req.method, req.url, req.version));

        HTTPResponse res = switch( req.method )
        {
            case "GET" -> this.serv.handleGet(req);
            case "POST" -> this.serv.handlePost(req);
            default -> new HTTPResponse();
        };
        res.WriteToPrinter(this.conn.getPrinter());


    }

    public void run()
    {
        try {
            this.handleRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
