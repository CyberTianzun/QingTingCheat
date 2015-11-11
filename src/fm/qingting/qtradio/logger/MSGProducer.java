package fm.qingting.qtradio.logger;

public class MSGProducer extends Thread
  implements Runnable
{
  public static long count = 0L;

  public static long getCount()
  {
    return count;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.logger.MSGProducer
 * JD-Core Version:    0.6.2
 */