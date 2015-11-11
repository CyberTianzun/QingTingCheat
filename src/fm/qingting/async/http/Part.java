package fm.qingting.async.http;

import fm.qingting.async.DataSink;
import fm.qingting.async.callback.CompletedCallback;
import fm.qingting.async.http.libcore.RawHeaders;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import org.apache.http.NameValuePair;

public class Part
{
  public static final String CONTENT_DISPOSITION = "Content-Disposition";
  private int length = -1;
  Multimap mContentDisposition;
  RawHeaders mHeaders;

  static
  {
    if (!Part.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  public Part(RawHeaders paramRawHeaders)
  {
    this.mHeaders = paramRawHeaders;
    this.mContentDisposition = Multimap.parseHeader(this.mHeaders, "Content-Disposition");
  }

  public Part(String paramString, int paramInt, List<NameValuePair> paramList)
  {
    this.length = paramInt;
    this.mHeaders = new RawHeaders();
    StringBuilder localStringBuilder = new StringBuilder(String.format("form-data; name=\"%s\"", new Object[] { paramString }));
    if (paramList != null)
    {
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        NameValuePair localNameValuePair = (NameValuePair)localIterator.next();
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = localNameValuePair.getName();
        arrayOfObject[1] = localNameValuePair.getValue();
        localStringBuilder.append(String.format("; %s=\"%s\"", arrayOfObject));
      }
    }
    this.mHeaders.set("Content-Disposition", localStringBuilder.toString());
    this.mContentDisposition = Multimap.parseHeader(this.mHeaders, "Content-Disposition");
  }

  public String getContentType()
  {
    return this.mHeaders.get("Content-Type");
  }

  public String getFilename()
  {
    String str = this.mContentDisposition.getString("filename");
    if (str == null)
      return null;
    return new File(str).getName();
  }

  public String getName()
  {
    return this.mContentDisposition.getString("name");
  }

  public RawHeaders getRawHeaders()
  {
    return this.mHeaders;
  }

  public boolean isFile()
  {
    return this.mContentDisposition.containsKey("filename");
  }

  public int length()
  {
    return this.length;
  }

  public void write(DataSink paramDataSink, CompletedCallback paramCompletedCallback)
  {
    if (!$assertionsDisabled)
      throw new AssertionError();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.http.Part
 * JD-Core Version:    0.6.2
 */