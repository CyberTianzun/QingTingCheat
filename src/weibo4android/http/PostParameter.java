package weibo4android.http;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

public class PostParameter
  implements Serializable, Comparable
{
  private static final String GIF = "image/gif";
  private static final String JPEG = "image/jpeg";
  private static final String OCTET = "application/octet-stream";
  private static final String PNG = "image/png";
  private static final long serialVersionUID = -8708108746980739212L;
  private File file = null;
  String name;
  String value;

  public PostParameter(String paramString, double paramDouble)
  {
    this.name = paramString;
    this.value = String.valueOf(paramDouble);
  }

  public PostParameter(String paramString, int paramInt)
  {
    this.name = paramString;
    this.value = String.valueOf(paramInt);
  }

  public PostParameter(String paramString, File paramFile)
  {
    this.name = paramString;
    this.file = paramFile;
  }

  public PostParameter(String paramString1, String paramString2)
  {
    this.name = paramString1;
    this.value = paramString2;
  }

  static boolean containsFile(List<PostParameter> paramList)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
      if (((PostParameter)localIterator.next()).isFile())
        return true;
    return false;
  }

  public static boolean containsFile(PostParameter[] paramArrayOfPostParameter)
  {
    if (paramArrayOfPostParameter == null);
    while (true)
    {
      return false;
      int i = paramArrayOfPostParameter.length;
      for (int j = 0; j < i; j++)
        if (paramArrayOfPostParameter[j].isFile())
          return true;
    }
  }

  public static String encodeParameters(PostParameter[] paramArrayOfPostParameter)
  {
    if (paramArrayOfPostParameter == null)
      return "";
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    while (true)
    {
      if (i < paramArrayOfPostParameter.length)
      {
        if (paramArrayOfPostParameter[i].isFile())
          throw new IllegalArgumentException("parameter [" + paramArrayOfPostParameter[i].name + "]should be text");
        if (i != 0)
          localStringBuffer.append("&");
      }
      try
      {
        localStringBuffer.append(URLEncoder.encode(paramArrayOfPostParameter[i].name, "UTF-8")).append("=").append(URLEncoder.encode(paramArrayOfPostParameter[i].value, "UTF-8"));
        label115: i++;
        continue;
        return localStringBuffer.toString();
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        break label115;
      }
    }
  }

  public static PostParameter[] getParameterArray(String paramString, int paramInt)
  {
    return getParameterArray(paramString, String.valueOf(paramInt));
  }

  public static PostParameter[] getParameterArray(String paramString1, int paramInt1, String paramString2, int paramInt2)
  {
    return getParameterArray(paramString1, String.valueOf(paramInt1), paramString2, String.valueOf(paramInt2));
  }

  public static PostParameter[] getParameterArray(String paramString1, String paramString2)
  {
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter(paramString1, paramString2);
    return arrayOfPostParameter;
  }

  public static PostParameter[] getParameterArray(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    PostParameter[] arrayOfPostParameter = new PostParameter[2];
    arrayOfPostParameter[0] = new PostParameter(paramString1, paramString2);
    arrayOfPostParameter[1] = new PostParameter(paramString3, paramString4);
    return arrayOfPostParameter;
  }

  public int compareTo(Object paramObject)
  {
    PostParameter localPostParameter = (PostParameter)paramObject;
    int i = this.name.compareTo(localPostParameter.name);
    if (i == 0)
      i = this.value.compareTo(localPostParameter.value);
    return i;
  }

  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (paramObject == null);
    PostParameter localPostParameter;
    do
    {
      do
      {
        return false;
        if (this == paramObject)
          return bool;
      }
      while (!(paramObject instanceof PostParameter));
      localPostParameter = (PostParameter)paramObject;
      if (this.file == null)
        break;
    }
    while (!this.file.equals(localPostParameter.file));
    if ((this.name.equals(localPostParameter.name)) && (this.value.equals(localPostParameter.value)));
    while (true)
    {
      return bool;
      if (localPostParameter.file == null)
        break;
      return false;
      bool = false;
    }
  }

  public String getContentType()
  {
    if (!isFile())
      throw new IllegalStateException("not a file");
    String str1 = this.file.getName();
    if (-1 == str1.lastIndexOf("."))
      return "application/octet-stream";
    String str2 = str1.substring(1 + str1.lastIndexOf(".")).toLowerCase();
    if (str2.length() == 3)
    {
      if ("gif".equals(str2))
        return "image/gif";
      if ("png".equals(str2))
        return "image/png";
      if ("jpg".equals(str2))
        return "image/jpeg";
      return "application/octet-stream";
    }
    if (str2.length() == 4)
    {
      if ("jpeg".equals(str2))
        return "image/jpeg";
      return "application/octet-stream";
    }
    return "application/octet-stream";
  }

  public File getFile()
  {
    return this.file;
  }

  public String getName()
  {
    return this.name;
  }

  public String getValue()
  {
    return this.value;
  }

  public int hashCode()
  {
    int i = 31 * (31 * this.name.hashCode() + this.value.hashCode());
    if (this.file != null);
    for (int j = this.file.hashCode(); ; j = 0)
      return j + i;
  }

  public boolean isFile()
  {
    return this.file != null;
  }

  public String toString()
  {
    return "PostParameter{name='" + this.name + '\'' + ", value='" + this.value + '\'' + ", file=" + this.file + '}';
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     weibo4android.http.PostParameter
 * JD-Core Version:    0.6.2
 */