package ServMeBread;

import java.util.ArrayList;
import java.util.Base64;

public class HTTPBasicAuth implements HTTPController
{
    private ArrayList<String> users;
    private HTTPController cont;
    public HTTPBasicAuth(HTTPController cont, ArrayList<String> users)
    {
        this.users = users;
        this.cont = cont;
    }
    private boolean handle(HTTPRequest req, HTTPResponse res)
    {
        String auth = req.getHeader("Authorization");
        if( auth == null)
        {
            res.addHeader("WWW-Authenticate", "Basic realm=Welcome");
            res.Unauthorized();
            return false;
        }
        else
        {
            if ( !auth.startsWith("Basic ") )
            {
                res.ServerError("Invalide Authorization, not Basic method");
                return false;
            }
            auth = auth.substring(6);
            try
            {
                auth = new String(Base64.getDecoder().decode(auth));
            }
            catch (IllegalArgumentException e)
            {
                res.ServerError("Invalide Authorization, not base64 data");
                return false;
            }
            for (int i = 0; i < this.users.size(); i++)
            {
                String usr = this.users.get(i);
                if (usr.equals(auth))
                {
                    return true;
                }
            }
        }
        
        res.ServerError("Invalide Authorization, Wrong user:pass");
        // res.addHeader("WWW-Authenticate", "Basic realm=Welcome");
        // res.Unauthorized();
        return false;
    }
    public void get(HTTPRequest req, HTTPResponse res)
    {
        if ( this.handle(req, res) ) this.cont.get(req, res);
    }
    public void post(HTTPRequest req, HTTPResponse res)
    {
        if ( this.handle(req, res) ) this.cont.post(req, res);
    }
}
