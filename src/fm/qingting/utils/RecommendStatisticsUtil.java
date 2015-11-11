package fm.qingting.utils;

import android.util.Log;
import android.util.SparseIntArray;
import java.util.HashMap;
import java.util.Map;

public enum RecommendStatisticsUtil
{
  private boolean mPause = false;
  private Map<Integer, SparseIntArray> mapBannerIntArray = new HashMap();
  private Map<Integer, SparseIntArray> mapIntArray = new HashMap();

  static
  {
    RecommendStatisticsUtil[] arrayOfRecommendStatisticsUtil = new RecommendStatisticsUtil[1];
    arrayOfRecommendStatisticsUtil[0] = INSTANCE;
  }

  private void log(String paramString)
  {
    Log.e("recommendstatics", paramString);
  }

  public void addBannerData(int paramInt1, int paramInt2)
  {
    if (paramInt1 < 0)
      return;
    if (this.mapBannerIntArray.get(Integer.valueOf(paramInt2)) == null)
    {
      SparseIntArray localSparseIntArray1 = new SparseIntArray();
      localSparseIntArray1.put(paramInt1, 1 + localSparseIntArray1.get(paramInt1));
      this.mapBannerIntArray.put(Integer.valueOf(paramInt2), localSparseIntArray1);
      return;
    }
    SparseIntArray localSparseIntArray2 = (SparseIntArray)this.mapBannerIntArray.get(Integer.valueOf(paramInt2));
    localSparseIntArray2.put(paramInt1, 1 + localSparseIntArray2.get(paramInt1));
  }

  public void addValidData(int paramInt1, int paramInt2)
  {
    if (this.mPause);
    while (paramInt1 < 0)
      return;
    if (this.mapIntArray.get(Integer.valueOf(paramInt2)) == null)
    {
      SparseIntArray localSparseIntArray1 = new SparseIntArray();
      localSparseIntArray1.put(paramInt1, 1 + localSparseIntArray1.get(paramInt1));
      this.mapIntArray.put(Integer.valueOf(paramInt2), localSparseIntArray1);
      return;
    }
    SparseIntArray localSparseIntArray2 = (SparseIntArray)this.mapIntArray.get(Integer.valueOf(paramInt2));
    localSparseIntArray2.put(paramInt1, 1 + localSparseIntArray2.get(paramInt1));
  }

  public void pause()
  {
    this.mPause = true;
  }

  public void resume()
  {
    this.mPause = false;
  }

  public void sendLog()
  {
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.RecommendStatisticsUtil
 * JD-Core Version:    0.6.2
 */