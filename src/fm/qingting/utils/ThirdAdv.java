package fm.qingting.utils;

import android.content.Context;
import android.provider.Settings.Secure;
import fm.qingting.framework.utils.MobileState;
import fm.qingting.qtradio.model.ActivityNode;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.RecommendItemNode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ThirdAdv
{
  private static int AirWaveSeq = 0;
  private static ThirdAdv _instance;
  private String mAndroidID;
  private Context mContext;
  private Map<Integer, RecommendItemNode> mapAdvNode = new HashMap();

  public static ThirdAdv getInstance()
  {
    if (_instance == null)
      _instance = new ThirdAdv();
    return _instance;
  }

  public String getAirWaveAid()
  {
    try
    {
      if (this.mAndroidID == null)
        this.mAndroidID = Settings.Secure.getString(this.mContext.getContentResolver(), "android_id");
      if ((this.mAndroidID != null) && (this.mAndroidID.length() > 0))
      {
        String str = String.valueOf(AirWaveSeq) + this.mAndroidID.substring(1);
        return str;
      }
    }
    catch (Exception localException)
    {
    }
    return "90wrd56d696e539c";
  }

  public String getAirWaveBID()
  {
    String str1 = "0A89BCB4E85400C4" + System.currentTimeMillis() / 1000L;
    String str2 = str1 + "000";
    String str3 = str2 + AirWaveSeq;
    AirWaveSeq = 1 + AirWaveSeq;
    return str3;
  }

  public String getAirWaveCarrier()
  {
    int i = OperatorInfo.getOperator(this.mContext);
    if (i == 1)
      return "1";
    if (i == 2)
      return "2";
    if (i == 3)
      return "3";
    return "0";
  }

  public String getAirWaveNetType()
  {
    int i = MobileState.getNetWorkType(this.mContext);
    if (i == 2)
      return "3";
    if (i == 1)
      return "1";
    if (i == 3)
      return "2";
    return "0";
  }

  public RecommendItemNode getRecommendNodes(int paramInt)
  {
    RecommendItemNode localRecommendItemNode = (RecommendItemNode)this.mapAdvNode.get(Integer.valueOf(paramInt));
    if (localRecommendItemNode == null)
      this.mapAdvNode.put(Integer.valueOf(paramInt), null);
    return localRecommendItemNode;
  }

  public void init(Context paramContext)
  {
    if (paramContext == null)
      return;
    this.mContext = paramContext;
  }

  public void setAdv(RecommendItemNode paramRecommendItemNode)
  {
    if (paramRecommendItemNode == null)
      return;
    Iterator localIterator = this.mapAdvNode.entrySet().iterator();
    int i = 0;
    label22: int j;
    if (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      if (localEntry.getValue() == null)
      {
        j = ((Integer)localEntry.getKey()).intValue();
        if (j == 0)
          break label132;
      }
    }
    while (true)
    {
      if (j != 0)
        this.mapAdvNode.put(Integer.valueOf(j), paramRecommendItemNode);
      if ((paramRecommendItemNode.mNode == null) || (!paramRecommendItemNode.mNode.nodeName.equalsIgnoreCase("activity")))
        break;
      ThirdTracker.getInstance().setThirdAdv((ActivityNode)paramRecommendItemNode.mNode);
      return;
      j = i;
      label132: i = j;
      break label22;
      j = i;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.ThirdAdv
 * JD-Core Version:    0.6.2
 */