package com.ixintui.push;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

final class MediateService$ServiceHandler extends Handler
{
  public MediateService$ServiceHandler(MediateService paramMediateService, Looper paramLooper)
  {
    super(paramLooper);
  }

  public final void handleMessage(Message paramMessage)
  {
    Intent localIntent = (Intent)paramMessage.obj;
    MediateService.a(this.a, localIntent);
    if (!MediateService.b(this.a, localIntent))
      this.a.stopSelf(paramMessage.arg1);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.ixintui.push.MediateService.ServiceHandler
 * JD-Core Version:    0.6.2
 */