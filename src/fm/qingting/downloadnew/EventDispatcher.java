package fm.qingting.downloadnew;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class EventDispatcher extends Handler
{
  private static final int EVENT_LOOP = 2;
  private static final int EVENT_ONE_TIME = 1;
  private static final String KEY_EXTRA = "extra";
  private static final String KEY_ID = "taskid";
  private DownloadTask mCurTask;
  private final List<WeakReference<DownloadListener>> mListeners = new ArrayList(5);

  EventDispatcher()
  {
    super(Looper.getMainLooper());
  }

  public void addListener(DownloadListener paramDownloadListener)
  {
    int i = 0;
    if (i < this.mListeners.size())
    {
      DownloadListener localDownloadListener = (DownloadListener)((WeakReference)this.mListeners.get(i)).get();
      if ((localDownloadListener == null) || (localDownloadListener != paramDownloadListener));
    }
    while (true)
    {
      if (i == -1)
        this.mListeners.add(new WeakReference(paramDownloadListener));
      return;
      i++;
      break;
      i = -1;
    }
  }

  public void handleMessage(Message paramMessage)
  {
    DownloadState localDownloadState = DownloadState.UNSPECIFIED;
    Map localMap = (Map)paramMessage.obj;
    Object localObject2;
    int i;
    Object localObject3;
    switch (paramMessage.what)
    {
    default:
      localObject2 = null;
      i = 0;
      localObject3 = null;
    case 1:
    case 2:
    }
    while (localObject2 != null)
    {
      Iterator localIterator = this.mListeners.iterator();
      while (true)
        if (localIterator.hasNext())
        {
          DownloadListener localDownloadListener = (DownloadListener)((WeakReference)localIterator.next()).get();
          if (localDownloadListener != null)
          {
            localDownloadListener.onDownloadEvent((String)localObject2, localDownloadState, localObject3);
            continue;
            String str2 = (String)localMap.get("taskid");
            localDownloadState = DownloadState.valueOf(paramMessage.arg1);
            Object localObject4 = localMap.get("extra");
            localObject2 = str2;
            localObject3 = localObject4;
            i = 0;
            break;
            String str1 = (String)localMap.get("taskid");
            localDownloadState = DownloadState.valueOf(paramMessage.arg1);
            Object localObject1 = localMap.get("extra");
            localObject2 = str1;
            i = 1;
            localObject3 = localObject1;
            break;
          }
        }
      if (i != 0)
      {
        Message localMessage = obtainMessage(2);
        localMessage.arg1 = localDownloadState.ordinal();
        HashMap localHashMap = new HashMap();
        localHashMap.put("taskid", localObject2);
        localHashMap.put("extra", Long.valueOf(this.mCurTask.getCurSize()));
        localMessage.obj = localHashMap;
        sendMessageDelayed(localMessage, 500L);
      }
    }
  }

  public boolean removeListener(DownloadListener paramDownloadListener)
  {
    Iterator localIterator = this.mListeners.iterator();
    while (localIterator.hasNext())
    {
      WeakReference localWeakReference = (WeakReference)localIterator.next();
      if (localWeakReference.get() == null)
        localIterator.remove();
      if (localWeakReference.get() == paramDownloadListener)
        localIterator.remove();
    }
    return false;
  }

  public void sendEvent(String paramString, DownloadState paramDownloadState, Object paramObject)
  {
    Message localMessage = obtainMessage(1);
    localMessage.arg1 = paramDownloadState.ordinal();
    HashMap localHashMap = new HashMap();
    localHashMap.put("taskid", paramString);
    localHashMap.put("extra", paramObject);
    localMessage.obj = localHashMap;
    sendMessage(localMessage);
  }

  public void sendLoopEvent(String paramString, DownloadState paramDownloadState)
  {
    removeMessages(2);
    Message localMessage = obtainMessage(2);
    localMessage.arg1 = paramDownloadState.ordinal();
    HashMap localHashMap = new HashMap();
    localHashMap.put("taskid", paramString);
    localHashMap.put("extra", Long.valueOf(this.mCurTask.getCurSize()));
    localMessage.obj = localHashMap;
    sendMessage(localMessage);
  }

  public void setCurrentTask(DownloadTask paramDownloadTask)
  {
    this.mCurTask = paramDownloadTask;
  }

  public void stopLoopEvent()
  {
    removeMessages(2);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.downloadnew.EventDispatcher
 * JD-Core Version:    0.6.2
 */