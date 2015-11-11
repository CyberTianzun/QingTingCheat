package com.umeng.message.proguard;

import android.text.TextUtils;
import org.android.agoo.helper.a.b;
import org.android.agoo.net.mtop.MtopHttpChunked;

class ad
  implements a.b
{
  ad(aa paramaa)
  {
  }

  public void a(int paramInt, String paramString)
  {
    Q.c("MessagePush", "connect_host[" + paramString + "]---->[" + paramInt + "]");
    U.a(this.a.a, paramInt, paramString);
    if ((paramInt == 408) || (paramInt == 307))
    {
      aa.d(this.a, aa.j(this.a), "host_error_connect");
      return;
    }
    aa.d(this.a, aa.k(this.a), "host_error_connect");
  }

  public void a(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      String str = String.format("http://%s/rest/api3.do", new Object[] { paramString });
      aa.a(this.a).setBaseUrl(str);
      aa.i(this.a);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.proguard.ad
 * JD-Core Version:    0.6.2
 */