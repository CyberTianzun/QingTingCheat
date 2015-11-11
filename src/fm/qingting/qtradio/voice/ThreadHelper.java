package fm.qingting.qtradio.voice;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadHelper extends ThreadPoolExecutor
{
  private static ThreadHelper mInstance;

  private ThreadHelper()
  {
    super(2, 4, 6L, TimeUnit.SECONDS, new LinkedBlockingQueue());
  }

  public static ThreadHelper getInstance()
  {
    if (mInstance == null)
      mInstance = new ThreadHelper();
    return mInstance;
  }

  public void setCorePoolSize(int paramInt)
  {
    if (paramInt < 2)
      paramInt = 2;
    super.setCorePoolSize(paramInt);
  }

  public void setKeepAliveTime(int paramInt)
  {
    if (paramInt < 6)
      paramInt = 6;
    super.setKeepAliveTime(paramInt, TimeUnit.SECONDS);
  }

  public void setMaximumPoolSize(int paramInt)
  {
    if (paramInt < 4)
      paramInt = 4;
    super.setMaximumPoolSize(paramInt);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.voice.ThreadHelper
 * JD-Core Version:    0.6.2
 */