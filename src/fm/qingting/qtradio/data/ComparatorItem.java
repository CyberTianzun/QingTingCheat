package fm.qingting.qtradio.data;

import fm.qingting.qtradio.model.ReserveChannel;
import java.util.Comparator;

class ComparatorItem
  implements Comparator<ReserveChannel>
{
  private static final int DOWM = -1;
  private static final int UP = 1;
  public long basetime = 0L;

  public ComparatorItem(long paramLong)
  {
    this.basetime = paramLong;
  }

  public int compare(ReserveChannel paramReserveChannel1, ReserveChannel paramReserveChannel2)
  {
    if ((paramReserveChannel1.reservetime <= this.basetime) && (paramReserveChannel2.reservetime > this.basetime));
    do
    {
      return 1;
      if ((paramReserveChannel1.reservetime > this.basetime) && (paramReserveChannel2.reservetime < this.basetime))
        return -1;
      if (paramReserveChannel1.reservetime < paramReserveChannel2.reservetime)
        return -1;
    }
    while (paramReserveChannel1.reservetime > paramReserveChannel2.reservetime);
    return 0;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.ComparatorItem
 * JD-Core Version:    0.6.2
 */