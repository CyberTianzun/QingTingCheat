package org.apache.commons.httpclient.util;

public final class TimeoutController
{
  public static void execute(Runnable paramRunnable, long paramLong)
    throws TimeoutController.TimeoutException
  {
    Thread localThread = new Thread(paramRunnable, "Timeout guard");
    localThread.setDaemon(true);
    execute(localThread, paramLong);
  }

  public static void execute(Thread paramThread, long paramLong)
    throws TimeoutController.TimeoutException
  {
    paramThread.start();
    try
    {
      paramThread.join(paramLong);
      label9: if (paramThread.isAlive())
      {
        paramThread.interrupt();
        throw new TimeoutException();
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      break label9;
    }
  }

  public static class TimeoutException extends Exception
  {
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.util.TimeoutController
 * JD-Core Version:    0.6.2
 */