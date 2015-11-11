package fm.qingting.qtradio.jd.data;

import fm.qingting.qtradio.view.popviews.CategoryResortPopView.CategoryResortInfo;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class RequestParam
{
  private int mAdHeight = 200;
  private int mAdWidth = 200;
  private String mDeviceId = "111";
  private String mGender = "M";
  private String mId = "123";
  private String mParamString = "";

  private String getUserTag()
  {
    ArrayList localArrayList = CategoryResortPopView.CategoryResortInfo.getSortedIdArrayList();
    if (localArrayList != null)
    {
      str = "";
      for (int i = 0; (i < localArrayList.size()) && (i < 3); i++)
      {
        str = str + localArrayList.get(i);
        if (i < 2)
          str = str + "_";
      }
    }
    String str = "";
    return str;
  }

  public void setDeviceId(String paramString)
  {
    this.mDeviceId = paramString;
    this.mId = (paramString + System.currentTimeMillis());
    this.mParamString = "";
  }

  public void setGender(String paramString)
  {
    this.mGender = paramString;
    this.mParamString = "";
  }

  public String toString()
  {
    JSONObject localJSONObject1;
    if (this.mParamString.equalsIgnoreCase(""))
      localJSONObject1 = new JSONObject();
    try
    {
      localJSONObject1.put("id", this.mId);
      JSONObject localJSONObject2 = new JSONObject();
      localJSONObject2.put("did", this.mDeviceId);
      localJSONObject1.put("device", localJSONObject2);
      JSONObject localJSONObject3 = new JSONObject();
      localJSONObject3.put("gender", this.mGender);
      localJSONObject3.put("usertag", getUserTag());
      localJSONObject1.put("user", localJSONObject3);
      JSONObject localJSONObject4 = new JSONObject();
      localJSONObject4.put("w", this.mAdWidth);
      localJSONObject4.put("h", this.mAdHeight);
      localJSONObject1.put("ad", localJSONObject4);
      this.mParamString = localJSONObject1.toString();
      return this.mParamString;
    }
    catch (JSONException localJSONException)
    {
      while (true)
      {
        localJSONException.printStackTrace();
        this.mParamString = "";
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.jd.data.RequestParam
 * JD-Core Version:    0.6.2
 */