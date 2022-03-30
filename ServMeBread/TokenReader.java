package ServMeBread;
import java.io.BufferedReader;
import java.io.IOException;

public class TokenReader
{
    private BufferedReader reader;
    public TokenReader(BufferedReader reader)
    {
        this.reader = reader;
    }
    public String readToken() throws IOException
    {
        String ret = "";

        char c = (char)this.reader.read();
        while(c != ' ' && c != '\r')
        {
            ret += c;
            c = (char)this.reader.read();

        }
        if(c == '\r')
        {
            this.reader.read(); // \n
        }
        return ret;
    }
    public BufferedReader getReader()
    {
        return this.reader;
    }
}
