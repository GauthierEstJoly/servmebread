package ServMeBread;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HTTPStaticFileHandler implements HTTPController
{
    private String filename;
    private boolean cache;
    private String cached;
    public HTTPStaticFileHandler(String file, boolean cache)
    {
        this.filename = file;
        this.cache = cache;
        this.cached = "";
    }
    public HTTPStaticFileHandler(String file)
    {
        this(file, false);
    }
    public void get(HTTPRequest req, HTTPResponse res)
    {
        if ( this.cache && !this.cached.equals("") )
        {
            res.body = this.cached;
            res.code = 200;
            return;
        }
        try
        {
            res.body = "";
            res.code = 200;
            Scanner myReader = new Scanner( new File( this.filename ) );
            while ( myReader.hasNextLine() )
                res.body += myReader.nextLine();
            myReader.close();
            if (this.cache)
            {
                this.cached = res.body;
            }
        }
        catch (FileNotFoundException e)
        {
            res.code = 404;
            res.body = "File not found";
            e.printStackTrace();
        }

    }
}
