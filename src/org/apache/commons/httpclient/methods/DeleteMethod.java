package org.apache.commons.httpclient.methods;

import org.apache.commons.httpclient.HttpMethodBase;

public class DeleteMethod extends HttpMethodBase
{
  public DeleteMethod()
  {
  }

  public DeleteMethod(String paramString)
  {
    super(paramString);
  }

  public String getName()
  {
    return "DELETE";
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.methods.DeleteMethod
 * JD-Core Version:    0.6.2
 */