package ServMeBread;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;

public class HTTPRequest
{
    public String method;
    public String url;
    public String version;
    public String body;
    public boolean isInvalide = false;
    public HashMap<String, String> headers;
    public HashMap<String, String> form;
    

    private HTTPRequest()
    {}

    private static HTTPRequest Invalide()
    {
        HTTPRequest req = new HTTPRequest();
        req.isInvalide = true;       
        return req;
    }

    public String getHeader(String key)
    {
        return this.headers.get(key.toLowerCase());
    }

    public String UserAgent()
    {
        return this.getHeader("User-Agent");
    }
    public String Referer()
    {
        return this.getHeader("Referer");
    }

    public static HTTPRequest fromTokenReader(TokenReader tk) throws IOException
    {
        HTTPRequest req = new HTTPRequest();
        req.headers = new HashMap<String, String>();
        req.form = new HashMap<String, String>();
        req.method = tk.readToken();
        req.url = tk.readToken();
        req.version = tk.readToken();
        req.isInvalide = false;

        // Lire les headers
        String line = "";
        while (true)
        {
            line = tk.getReader().readLine();
            if ( line == null   ) break;
            if ( line.isEmpty() ) break;
            String[] keyvalue = line.split(": ");
            if ( keyvalue.length != 2 )
                return HTTPRequest.Invalide();
            req.headers.put(keyvalue[0].toLowerCase(), keyvalue[1]);
        }
        req.body = "";
        if ( !req.method.equals("GET") )
        {
            String contentlength_txt = req.getHeader("Content-Length");
            if (contentlength_txt == null)
            {
                return HTTPRequest.Invalide();
            }
            int contentlength = Integer.parseInt(contentlength_txt);
            while(contentlength-- != 0)
                req.body += (char)tk.getReader().read();

            String contenttype = req.getHeader("Content-Type");
            if ( contenttype != null && contenttype.equals("application/x-www-form-urlencoded") )
            {
                for (String entry : req.body.split("&"))
                {
                    String[] keyvalue = entry.split("=");
                    if( keyvalue.length == 2 )
                        req.form.put(URLDecoder.decode(keyvalue[0], "UTF-8"), URLDecoder.decode(keyvalue[1], "UTF-8"));
                }
            }
            
        }

        return req;
    }
}