package org.apache.commons.httpclient.methods;

public class PutMethod extends EntityEnclosingMethod
{
  public PutMethod()
  {
  }

  public PutMethod(String paramString)
  {
    super(paramString);
  }

  public String getName()
  {
    return "PUT";
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.methods.PutMethod
 * JD-Core Version:    0.6.2
 */