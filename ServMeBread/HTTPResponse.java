package ServMeBread;
import java.io.PrintWriter;
import java.util.HashMap;



public class HTTPResponse
{
    public static final HashMap<Integer, String> RESPONSES = new HashMap<Integer, String>();
    static
    {
        // 2xx Succesful
        RESPONSES.put(200, "OK");
        RESPONSES.put(201, "Created");
        RESPONSES.put(202, "Accepted");

        // 3xx Redirection
        RESPONSES.put(300, "Multiple Choices");
        RESPONSES.put(301, "Moved Permanently");
        RESPONSES.put(302, "Found");
        RESPONSES.put(303, "See Other");
        RESPONSES.put(304, "Not Modified");
        RESPONSES.put(305, "Use Proxy");
        RESPONSES.put(306, "Switch Proxy");
        RESPONSES.put(307, "Temporary Redirect");
        RESPONSES.put(308, "Permanent Redirect");

        // 4xx Client Error
        RESPONSES.put(400, "Bad Request");
        RESPONSES.put(401, "Unauthorized");
        RESPONSES.put(402, "Payment Required");
        RESPONSES.put(403, "Forbidden");
        RESPONSES.put(404, "Not Found");
        RESPONSES.put(405, "Method Not Allowed");
        RESPONSES.put(406, "Not Acceptable");
        RESPONSES.put(407, "Proxy Authentication Required");
        RESPONSES.put(408, "Request Timeout");
        
        // 5xx Server Error
        RESPONSES.put(500, "Internal Server Error");
        RESPONSES.put(501, "Not Implemented");
        RESPONSES.put(502, "Bad Gateway");
        RESPONSES.put(503, "Service Unavailable");
        RESPONSES.put(504, "Gateway Timeout");
        RESPONSES.put(505, "HTTP Version Not Supported");
        RESPONSES.put(506, "Variant Also Negotiates");
        RESPONSES.put(507, "Insufficient Storage");
    }

    public static final String CONTENT_TYPE_HTML = "text/html";
    public static final String CONTENT_TYPE_PLAINTEXT = "text/plain";
    public static final String CONTENT_TYPE_JSON = "application/json";



    public String version;
    public int code;
    public String body;
    public HashMap<String, String> headers;
    public String contenttype = HTTPResponse.CONTENT_TYPE_HTML;
    public String charset = "UTF-8";

    public HTTPResponse(String version, int code, String body)
    {
        this.version = version;
        this.code = code;
        this.body = body;
        this.headers = new HashMap<String, String>();
        this.addHeader("Server", "ServMeBread 0.1");

    }
    public HTTPResponse()
    {
        this("HTTP/1.1", 404, "Not implemented");
    }


    public void Redirect(String url)
    {
        this.addHeader("Location", url);
        this.code = 301;
    }
    public void Success(String body)
    {
        this.body = body;
        this.code = 200;
    }
    public void ServerError(String reason)
    {
        this.body = "Internal server error: " + reason;
        this.code = 500;
    }
    public void Unauthorized()
    {
        this.body = "Unauthorized";
        this.code = 401;
    }

    public void addHeader(String key, String value)
    {
        this.headers.put(key, value);
    }
    public void setContentType(String contentType)
    {
        this.contenttype = contentType;
    }
    public void setCharset(String charset)
    {
        this.charset = charset;
    }
    public void WriteToPrinter(PrintWriter out)
    {

        this.headers.put("Content-Type", this.contenttype + "; charset=" + this.charset);
        this.headers.put("Content-Length", this.body.length()  + "");
        

        out.print(this.version + " ");
        out.print(this.code + " ");
        out.print(HTTPResponse.RESPONSES.get(this.code) + "\r\n");


        for (String header : this.headers.keySet())
        {
            out.println(header + ": " + this.headers.get(header) + "\r");
        }


        out.print("\r\n");
        out.println(this.body);

        out.flush();
        
    }
}
