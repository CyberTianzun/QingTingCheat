package fm.qingting.qtradio.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import fm.qingting.framework.manager.EventDispacthManager;

public class EducationReceiver extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (paramIntent != null)
    {
      int i = paramIntent.getIntExtra("collection_remind_channel_id", 0);
      if (i != 0)
        EventDispacthManager.getInstance().dispatchAction("showEducationFav", Integer.valueOf(i));
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.manager.EducationReceiver
 * JD-Core Version:    0.6.2
 */