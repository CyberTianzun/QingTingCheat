package fm.qingting.qtradio.room;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class DoActionHandler extends Handler
{
  public DoActionHandler(Looper paramLooper)
  {
    super(paramLooper);
  }

  public void handleMessage(Message paramMessage)
  {
    try
    {
      Action localAction = (Action)paramMessage.obj;
      StateMachine.getInstance().getCurrState().execute(localAction);
      return;
    }
    catch (Exception localException)
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.room.DoActionHandler
 * JD-Core Version:    0.6.2
 */