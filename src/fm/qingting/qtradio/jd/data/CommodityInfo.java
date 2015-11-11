package fm.qingting.qtradio.jd.data;

import org.json.JSONException;
import org.json.JSONObject;

public class CommodityInfo
{
  private String mAvatar = "";
  private String mDescription;
  private String mPrice = "";
  private String mShopUrl = "";
  private String mTitle = "";
  private String mTrackUrl;
  private long mUpdateTime = 0L;

  public void fromJson(JSONObject paramJSONObject)
    throws JSONException
  {
    if (paramJSONObject == null)
      return;
    this.mTitle = paramJSONObject.getString("title");
    this.mDescription = paramJSONObject.getString("desc");
    this.mShopUrl = paramJSONObject.getString("click");
    this.mAvatar = paramJSONObject.getString("image");
    this.mPrice = paramJSONObject.getString("price");
    this.mTrackUrl = paramJSONObject.getString("exposure");
  }

  public String getAvatar()
  {
    return this.mAvatar;
  }

  public String getDescription()
  {
    return this.mDescription;
  }

  public String getPrice()
  {
    return this.mPrice;
  }

  public String getShopUrl()
  {
    return this.mShopUrl;
  }

  public String getTitle()
  {
    return this.mTitle;
  }

  public String getTrackUrl()
  {
    return this.mTrackUrl;
  }

  public long getUpdateTime()
  {
    return this.mUpdateTime;
  }

  public void setAvatar(String paramString)
  {
    if (paramString == null)
      paramString = "";
    this.mAvatar = paramString;
  }

  public void setPrice(String paramString)
  {
    if (paramString == null)
      paramString = "";
    this.mPrice = paramString;
  }

  public void setShoupUrl(String paramString)
  {
    if (paramString == null)
      paramString = "";
    this.mShopUrl = paramString;
  }

  public void setTitle(String paramString)
  {
    if (paramString == "")
      paramString = "";
    this.mTitle = paramString;
  }

  public void setUpdateTime(long paramLong)
  {
    this.mUpdateTime = paramLong;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.jd.data.CommodityInfo
 * JD-Core Version:    0.6.2
 */