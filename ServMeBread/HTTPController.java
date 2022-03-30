package ServMeBread;


public interface HTTPController
{
    public default void get(HTTPRequest req, HTTPResponse res)
    {}
    public default void post(HTTPRequest req, HTTPResponse res)
    {}
    
}