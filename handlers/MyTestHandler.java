package handlers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import ServMeBread.HTTPController;
import ServMeBread.HTTPRequest;
import ServMeBread.HTTPResponse;

public class MyTestHandler implements HTTPController
{

    public void get(HTTPRequest req, HTTPResponse res)
    {
        res.Success("Hallo from brazil !!");
    }

}
