package org.apache.commons.httpclient;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.httpclient.util.ParameterParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HeaderElement extends NameValuePair
{
  private static final Log LOG;
  static Class class$org$apache$commons$httpclient$HeaderElement;
  private NameValuePair[] parameters = null;

  static
  {
    Class localClass;
    if (class$org$apache$commons$httpclient$HeaderElement == null)
    {
      localClass = class$("org.apache.commons.httpclient.HeaderElement");
      class$org$apache$commons$httpclient$HeaderElement = localClass;
    }
    while (true)
    {
      LOG = LogFactory.getLog(localClass);
      return;
      localClass = class$org$apache$commons$httpclient$HeaderElement;
    }
  }

  public HeaderElement()
  {
    this(null, null, null);
  }

  public HeaderElement(String paramString1, String paramString2)
  {
    this(paramString1, paramString2, null);
  }

  public HeaderElement(String paramString1, String paramString2, NameValuePair[] paramArrayOfNameValuePair)
  {
    super(paramString1, paramString2);
    this.parameters = paramArrayOfNameValuePair;
  }

  public HeaderElement(char[] paramArrayOfChar)
  {
    this(paramArrayOfChar, 0, paramArrayOfChar.length);
  }

  public HeaderElement(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    this();
    if (paramArrayOfChar == null);
    List localList;
    do
    {
      do
      {
        return;
        localList = new ParameterParser().parse(paramArrayOfChar, paramInt1, paramInt2, ';');
      }
      while (localList.size() <= 0);
      NameValuePair localNameValuePair = (NameValuePair)localList.remove(0);
      setName(localNameValuePair.getName());
      setValue(localNameValuePair.getValue());
    }
    while (localList.size() <= 0);
    this.parameters = ((NameValuePair[])localList.toArray(new NameValuePair[localList.size()]));
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

  public static final HeaderElement[] parse(String paramString)
    throws HttpException
  {
    LOG.trace("enter HeaderElement.parse(String)");
    if (paramString == null)
      return new HeaderElement[0];
    return parseElements(paramString.toCharArray());
  }

  public static final HeaderElement[] parseElements(String paramString)
  {
    LOG.trace("enter HeaderElement.parseElements(String)");
    if (paramString == null)
      return new HeaderElement[0];
    return parseElements(paramString.toCharArray());
  }

  public static final HeaderElement[] parseElements(char[] paramArrayOfChar)
  {
    LOG.trace("enter HeaderElement.parseElements(char[])");
    if (paramArrayOfChar == null)
      return new HeaderElement[0];
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    int j = 0;
    int k = paramArrayOfChar.length;
    int m = 0;
    if (i >= k)
      return (HeaderElement[])localArrayList.toArray(new HeaderElement[localArrayList.size()]);
    int n = paramArrayOfChar[i];
    label83: HeaderElement localHeaderElement;
    if (n == 34)
    {
      if (m == 0)
        m = 1;
    }
    else
    {
      if ((m != 0) || (n != 44))
        break label145;
      localHeaderElement = new HeaderElement(paramArrayOfChar, j, i);
      j = i + 1;
    }
    while (true)
    {
      if ((localHeaderElement != null) && (localHeaderElement.getName() != null))
        localArrayList.add(localHeaderElement);
      i++;
      break;
      m = 0;
      break label83;
      label145: int i1 = k - 1;
      localHeaderElement = null;
      if (i == i1)
        localHeaderElement = new HeaderElement(paramArrayOfChar, j, k);
    }
  }

  public NameValuePair getParameterByName(String paramString)
  {
    LOG.trace("enter HeaderElement.getParameterByName(String)");
    if (paramString == null)
      throw new IllegalArgumentException("Name may not be null");
    NameValuePair[] arrayOfNameValuePair = getParameters();
    if (arrayOfNameValuePair != null);
    for (int i = 0; ; i++)
    {
      if (i >= arrayOfNameValuePair.length)
        return null;
      NameValuePair localNameValuePair = arrayOfNameValuePair[i];
      if (localNameValuePair.getName().equalsIgnoreCase(paramString))
        return localNameValuePair;
    }
  }

  public NameValuePair[] getParameters()
  {
    return this.parameters;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.HeaderElement
 * JD-Core Version:    0.6.2
 */