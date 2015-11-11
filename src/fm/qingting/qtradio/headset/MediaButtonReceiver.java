package fm.qingting.qtradio.headset;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

public class MediaButtonReceiver extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    int i = 1;
    if (!"android.intent.action.MEDIA_BUTTON".equals(paramIntent.getAction()));
    while (true)
    {
      return;
      KeyEvent localKeyEvent = (KeyEvent)paramIntent.getParcelableExtra("android.intent.extra.KEY_EVENT");
      if (localKeyEvent != null)
      {
        if (localKeyEvent.getAction() == i);
        while (i != 0)
        {
          int j = localKeyEvent.getKeyCode();
          long l = localKeyEvent.getEventTime() - localKeyEvent.getDownTime();
          Message localMessage = Message.obtain();
          localMessage.what = 0;
          Bundle localBundle = new Bundle();
          localBundle.putInt("key_code", j);
          localBundle.putLong("event_time", l);
          localMessage.setData(localBundle);
          HeadSet.getInstance(paramContext).getHandler().sendMessage(localMessage);
          return;
          i = 0;
        }
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.headset.MediaButtonReceiver
 * JD-Core Version:    0.6.2
 */