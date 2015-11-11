package fm.qingting.qtradio.jd.data;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

class MainHandler extends Handler
{
  public MainHandler()
  {
    super(Looper.getMainLooper());
  }

  public void handleMessage(Message paramMessage)
  {
    Response localResponse = (Response)paramMessage.obj;
    if (localResponse.getListener() != null)
      localResponse.getListener().onResponse(localResponse);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.jd.data.MainHandler
 * JD-Core Version:    0.6.2
 */