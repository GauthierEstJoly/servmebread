package ServMeBread;
import java.util.HashMap;

public class HTTPMethodHandler
{
    private String method;
    private HashMap<String, HTTPController> handlers;
    public HTTPMethodHandler(String method)
    {
        this.method = method;
        this.handlers = new HashMap<String, HTTPController>();
    }
    public void addHandler(String path, HTTPController controller)
    {
        this.handlers.put(path, controller);
    }
    public HTTPResponse handle(HTTPRequest req)
    {   
        HTTPController controller = this.handlers.get(req.url);
        HTTPResponse res = new HTTPResponse();

        if (controller != null)
        {
            if ( this.method.equals("GET") )
                controller.get(req, res);
            if ( this.method.equals("POST") )
                controller.post(req, res);
            
        }

        return res;
    }
}
