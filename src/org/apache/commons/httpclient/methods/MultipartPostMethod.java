package org.apache.commons.httpclient.methods;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MultipartPostMethod extends ExpectContinueMethod
{
  private static final Log LOG;
  public static final String MULTIPART_FORM_CONTENT_TYPE = "multipart/form-data";
  static Class class$org$apache$commons$httpclient$methods$MultipartPostMethod;
  private final List parameters = new ArrayList();

  static
  {
    Class localClass;
    if (class$org$apache$commons$httpclient$methods$MultipartPostMethod == null)
    {
      localClass = class$("org.apache.commons.httpclient.methods.MultipartPostMethod");
      class$org$apache$commons$httpclient$methods$MultipartPostMethod = localClass;
    }
    while (true)
    {
      LOG = LogFactory.getLog(localClass);
      return;
      localClass = class$org$apache$commons$httpclient$methods$MultipartPostMethod;
    }
  }

  public MultipartPostMethod()
  {
  }

  public MultipartPostMethod(String paramString)
  {
    super(paramString);
  }

  static Class class$(String paramString)
  {
    try
    {
      Class localClass = Class.forName(paramString);
      return localClass;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }
  }

  protected void addContentLengthRequestHeader(HttpState paramHttpState, HttpConnection paramHttpConnection)
    throws IOException, HttpException
  {
    LOG.trace("enter EntityEnclosingMethod.addContentLengthRequestHeader(HttpState, HttpConnection)");
    if (getRequestHeader("Content-Length") == null)
      addRequestHeader("Content-Length", String.valueOf(getRequestContentLength()));
    removeRequestHeader("Transfer-Encoding");
  }

  protected void addContentTypeRequestHeader(HttpState paramHttpState, HttpConnection paramHttpConnection)
    throws IOException, HttpException
  {
    LOG.trace("enter EntityEnclosingMethod.addContentTypeRequestHeader(HttpState, HttpConnection)");
    if (!this.parameters.isEmpty())
    {
      StringBuffer localStringBuffer = new StringBuffer("multipart/form-data");
      if (Part.getBoundary() != null)
      {
        localStringBuffer.append("; boundary=");
        localStringBuffer.append(Part.getBoundary());
      }
      setRequestHeader("Content-Type", localStringBuffer.toString());
    }
  }

  public void addParameter(String paramString, File paramFile)
    throws FileNotFoundException
  {
    LOG.trace("enter MultipartPostMethod.addParameter(String parameterName, File parameterFile)");
    FilePart localFilePart = new FilePart(paramString, paramFile);
    this.parameters.add(localFilePart);
  }

  public void addParameter(String paramString1, String paramString2)
  {
    LOG.trace("enter addParameter(String parameterName, String parameterValue)");
    StringPart localStringPart = new StringPart(paramString1, paramString2);
    this.parameters.add(localStringPart);
  }

  public void addParameter(String paramString1, String paramString2, File paramFile)
    throws FileNotFoundException
  {
    LOG.trace("enter MultipartPostMethod.addParameter(String parameterName, String fileName, File parameterFile)");
    FilePart localFilePart = new FilePart(paramString1, paramString2, paramFile);
    this.parameters.add(localFilePart);
  }

  public void addPart(Part paramPart)
  {
    LOG.trace("enter addPart(Part part)");
    this.parameters.add(paramPart);
  }

  protected void addRequestHeaders(HttpState paramHttpState, HttpConnection paramHttpConnection)
    throws IOException, HttpException
  {
    LOG.trace("enter MultipartPostMethod.addRequestHeaders(HttpState state, HttpConnection conn)");
    super.addRequestHeaders(paramHttpState, paramHttpConnection);
    addContentLengthRequestHeader(paramHttpState, paramHttpConnection);
    addContentTypeRequestHeader(paramHttpState, paramHttpConnection);
  }

  public String getName()
  {
    return "POST";
  }

  public Part[] getParts()
  {
    return (Part[])this.parameters.toArray(new Part[this.parameters.size()]);
  }

  protected long getRequestContentLength()
    throws IOException
  {
    LOG.trace("enter MultipartPostMethod.getRequestContentLength()");
    return Part.getLengthOfParts(getParts());
  }

  protected boolean hasRequestContent()
  {
    return true;
  }

  public void recycle()
  {
    LOG.trace("enter MultipartPostMethod.recycle()");
    super.recycle();
    this.parameters.clear();
  }

  protected boolean writeRequestBody(HttpState paramHttpState, HttpConnection paramHttpConnection)
    throws IOException, HttpException
  {
    LOG.trace("enter MultipartPostMethod.writeRequestBody(HttpState state, HttpConnection conn)");
    Part.sendParts(paramHttpConnection.getRequestOutputStream(), getParts());
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.methods.MultipartPostMethod
 * JD-Core Version:    0.6.2
 */