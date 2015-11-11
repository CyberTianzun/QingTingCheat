package com.umeng.fb.model;

import android.text.TextUtils;
import com.umeng.fb.util.Log;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class UserInfo
{
  private static final String TAG = UserInfo.class.getName();
  int ageGroup = -1;
  Map<String, String> contact;
  String gender = "";
  Map<String, String> remark;

  public UserInfo()
  {
    this.contact = new HashMap();
    this.remark = new HashMap();
  }

  UserInfo(JSONObject paramJSONObject)
    throws JSONException
  {
    this.ageGroup = paramJSONObject.optInt("age_group", -1);
    this.gender = paramJSONObject.optString("gender", "");
    this.contact = new HashMap();
    this.remark = new HashMap();
    JSONObject localJSONObject1 = paramJSONObject.optJSONObject("contact");
    if (localJSONObject1 != null)
    {
      Iterator localIterator2 = localJSONObject1.keys();
      while (localIterator2.hasNext())
      {
        String str2 = (String)localIterator2.next();
        this.contact.put(str2, localJSONObject1.getString(str2));
      }
    }
    JSONObject localJSONObject2 = paramJSONObject.optJSONObject("remark");
    Log.d(TAG, "" + localJSONObject2);
    if (localJSONObject2 != null)
    {
      Iterator localIterator1 = localJSONObject2.keys();
      while (localIterator1.hasNext())
      {
        String str1 = (String)localIterator1.next();
        this.remark.put(str1, localJSONObject2.getString(str1));
      }
    }
  }

  public int getAgeGroup()
  {
    return this.ageGroup;
  }

  public Map<String, String> getContact()
  {
    return this.contact;
  }

  public String getGender()
  {
    return this.gender;
  }

  public Map<String, String> getRemark()
  {
    return this.remark;
  }

  public void setAgeGroup(int paramInt)
  {
    this.ageGroup = paramInt;
  }

  public void setContact(Map<String, String> paramMap)
  {
    this.contact = paramMap;
  }

  public void setGender(String paramString)
  {
    this.gender = paramString;
  }

  public void setRemark(Map<String, String> paramMap)
  {
    this.remark = paramMap;
  }

  public JSONObject toJson()
  {
    JSONObject localJSONObject1 = new JSONObject();
    do
    {
      JSONObject localJSONObject3;
      try
      {
        if (this.ageGroup > -1)
          localJSONObject1.put("age_group", this.ageGroup);
        if (!TextUtils.isEmpty(this.gender))
          localJSONObject1.put("gender", this.gender);
        if ((this.contact == null) || (this.contact.size() <= 0))
          continue;
        localJSONObject3 = new JSONObject();
        Iterator localIterator2 = this.contact.entrySet().iterator();
        while (localIterator2.hasNext())
        {
          Map.Entry localEntry2 = (Map.Entry)localIterator2.next();
          localJSONObject3.put((String)localEntry2.getKey(), localEntry2.getValue());
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        return localJSONObject1;
      }
      localJSONObject1.put("contact", localJSONObject3);
    }
    while ((this.remark == null) || (this.remark.size() <= 0));
    JSONObject localJSONObject2 = new JSONObject();
    Iterator localIterator1 = this.remark.entrySet().iterator();
    while (localIterator1.hasNext())
    {
      Map.Entry localEntry1 = (Map.Entry)localIterator1.next();
      localJSONObject2.put((String)localEntry1.getKey(), localEntry1.getValue());
    }
    localJSONObject1.put("remark", localJSONObject2);
    return localJSONObject1;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.fb.model.UserInfo
 * JD-Core Version:    0.6.2
 */