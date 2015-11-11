package fm.qingting.async.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class FilePart extends StreamPart
{
  File file;

  public FilePart(String paramString, File paramFile)
  {
    super(paramString, (int)paramFile.length(), new ArrayList()
    {
    });
    this.file = paramFile;
  }

  protected InputStream getInputStream()
    throws IOException
  {
    return new FileInputStream(this.file);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.http.FilePart
 * JD-Core Version:    0.6.2
 */