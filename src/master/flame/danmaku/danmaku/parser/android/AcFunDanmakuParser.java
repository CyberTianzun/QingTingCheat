package master.flame.danmaku.danmaku.parser.android;

import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.DanmakuFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AcFunDanmakuParser extends BaseDanmakuParser
{
  private Danmakus _parse(JSONArray paramJSONArray, Danmakus paramDanmakus)
  {
    Danmakus localDanmakus;
    int j;
    if (paramDanmakus == null)
    {
      localDanmakus = new Danmakus();
      if (paramJSONArray != null)
      {
        int i = paramJSONArray.length();
        j = 0;
        if (i != 0);
      }
      else
      {
        return localDanmakus;
      }
    }
    while (true)
    {
      if (j < paramJSONArray.length());
      try
      {
        JSONObject localJSONObject = paramJSONArray.getJSONObject(j);
        String[] arrayOfString = localJSONObject.getString("c").split(",");
        if (arrayOfString.length <= 0)
          break label248;
        int k = Integer.parseInt(arrayOfString[2]);
        if (k == 7)
          break label248;
        long l = ()(1000.0F * Float.parseFloat(arrayOfString[0]));
        int m = 0xFF000000 | Integer.parseInt(arrayOfString[1]);
        float f = Float.parseFloat(arrayOfString[3]);
        BaseDanmaku localBaseDanmaku = DanmakuFactory.createDanmaku(k, this.mDisp);
        if (localBaseDanmaku == null)
          break label248;
        localBaseDanmaku.time = l;
        localBaseDanmaku.textSize = (f * (this.mDispDensity - 0.6F));
        localBaseDanmaku.textColor = m;
        if (m <= -16777216)
        {
          n = -1;
          localBaseDanmaku.textShadowColor = n;
          DanmakuFactory.fillText(localBaseDanmaku, localJSONObject.optString("m", "...."));
          localBaseDanmaku.index = j;
          localBaseDanmaku.setTimer(this.mTimer);
          localDanmakus.addItem(localBaseDanmaku);
        }
      }
      catch (JSONException localJSONException)
      {
        while (true)
        {
          break;
          int n = -16777216;
        }
        return localDanmakus;
      }
      catch (NumberFormatException localNumberFormatException)
      {
      }
      localDanmakus = paramDanmakus;
      break;
      label248: j++;
    }
  }

  private Danmakus doParse(JSONArray paramJSONArray)
  {
    Object localObject = new Danmakus();
    if ((paramJSONArray == null) || (paramJSONArray.length() == 0))
      return localObject;
    int i = 0;
    while (i < paramJSONArray.length())
      try
      {
        JSONArray localJSONArray = paramJSONArray.getJSONArray(i);
        if (localJSONArray != null)
        {
          Danmakus localDanmakus = _parse(localJSONArray, (Danmakus)localObject);
          localObject = localDanmakus;
        }
        label55: i++;
      }
      catch (JSONException localJSONException)
      {
        break label55;
      }
  }

  public Danmakus parse()
  {
    if ((this.mDataSource != null) && ((this.mDataSource instanceof JSONSource)))
      return doParse(((JSONSource)this.mDataSource).data());
    return new Danmakus();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     master.flame.danmaku.danmaku.parser.android.AcFunDanmakuParser
 * JD-Core Version:    0.6.2
 */