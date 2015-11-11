package fm.qingting.qtradio.push;

import android.util.Log;
import fm.qingting.qtradio.stat.PlayRecord;
import fm.qingting.utils.DateUtil;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class PushTimeAnalyse
{
  public static int getMostFrequentListenHour(List<PlayRecord> paramList, int paramInt)
  {
    int i = -1;
    if ((paramList == null) || (paramList.size() <= 0))
      return i;
    long[] arrayOfLong = new long[24];
    for (int j = 0; j < arrayOfLong.length; j++)
      arrayOfLong[j] = 0L;
    Iterator localIterator = paramList.iterator();
    label51: boolean bool = localIterator.hasNext();
    int k = 0;
    long l2;
    int m;
    long l4;
    if (bool)
    {
      PlayRecord localPlayRecord = (PlayRecord)localIterator.next();
      l2 = localPlayRecord.duration;
      long l3 = localPlayRecord.time - l2;
      m = DateUtil.getGTM8CalendarFromUtcms(l3).get(11);
      l4 = 3600L - l3 % 3600L;
    }
    while (true)
    {
      long l5;
      if (l2 >= l4)
      {
        arrayOfLong[m] = (l4 + arrayOfLong[m]);
        l5 = l2 - l4;
        m++;
        if (m >= 24)
        {
          l2 = l5;
          l4 = 3600L;
          m = 0;
        }
      }
      else
      {
        arrayOfLong[m] = (l2 + arrayOfLong[m]);
        break label51;
        while (k < arrayOfLong.length)
        {
          log(k + ":" + arrayOfLong[k]);
          k++;
        }
        log("from hour:" + paramInt);
        long l1 = -1L;
        while (paramInt < arrayOfLong.length)
        {
          if (arrayOfLong[paramInt] > l1)
          {
            l1 = arrayOfLong[paramInt];
            i = paramInt;
          }
          paramInt++;
        }
        break;
        l2 = l5;
        l4 = 3600L;
      }
    }
  }

  private static void log(String paramString)
  {
    Log.i("PushTimeAnalyse", paramString);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.push.PushTimeAnalyse
 * JD-Core Version:    0.6.2
 */