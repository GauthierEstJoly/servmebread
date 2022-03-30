import java.io.IOException;
import java.util.ArrayList;

import ServMeBread.HTTPBasicAuth;
import ServMeBread.HTTPServer;
import ServMeBread.HTTPStaticFileHandler;
import handlers.MyTestHandler;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        HTTPServer serv;

        System.out.println("Starting web server. . .");
        serv = new HTTPServer(8080);
        
        serv.get("/", new HTTPStaticFileHandler("static/index.html", true));
        serv.post("/", new MyTestHandler());

        ArrayList<String> users = new ArrayList<String>();

        users.add("admin:pass");

        serv.get("/auth", new HTTPBasicAuth(new MyTestHandler(), users));
        

        serv.Loop();

    }
}