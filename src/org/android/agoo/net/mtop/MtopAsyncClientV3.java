package org.android.agoo.net.mtop;

import android.content.Context;
import com.umeng.message.proguard.al;
import com.umeng.message.proguard.aq;

public class MtopAsyncClientV3 extends al
  implements IMtopAsynClient
{
  private String b;
  private String c;
  private String d;

  public void getV3(Context paramContext, MtopRequest paramMtopRequest, MtopResponseHandler paramMtopResponseHandler)
  {
    try
    {
      MtopRequestHelper.checkAppKeyAndAppSecret(paramMtopRequest, this.b, this.c);
      aq localaq = MtopRequestHelper.getUrlWithRequestParams(paramContext, paramMtopRequest);
      get(paramContext, this.d, localaq, paramMtopResponseHandler);
      return;
    }
    catch (Throwable localThrowable)
    {
      paramMtopResponseHandler.onFailure(localThrowable);
    }
  }

  public void setBaseUrl(String paramString)
  {
    this.d = paramString;
  }

  public void setDefaultAppSecret(String paramString)
  {
    this.c = paramString;
  }

  public void setDefaultAppkey(String paramString)
  {
    this.b = paramString;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.android.agoo.net.mtop.MtopAsyncClientV3
 * JD-Core Version:    0.6.2
 */