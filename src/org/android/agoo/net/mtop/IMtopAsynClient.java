package org.android.agoo.net.mtop;

import android.content.Context;

public abstract interface IMtopAsynClient
{
  public abstract void getV3(Context paramContext, MtopRequest paramMtopRequest, MtopResponseHandler paramMtopResponseHandler);

  public abstract void setBaseUrl(String paramString);

  public abstract void setDefaultAppSecret(String paramString);

  public abstract void setDefaultAppkey(String paramString);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.android.agoo.net.mtop.IMtopAsynClient
 * JD-Core Version:    0.6.2
 */