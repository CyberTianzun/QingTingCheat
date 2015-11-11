package com.umeng.fb.net;

import org.json.JSONObject;

public class FbRequest extends URequest
{
  public String mKey;
  public String mReportContent;
  public JSONObject mValue;

  public FbRequest(String paramString)
  {
    super(paramString);
    this.mReportContent = paramString;
  }

  public FbRequest(String paramString1, JSONObject paramJSONObject, String paramString2)
  {
    super(paramString2);
    this.mKey = paramString1;
    this.mValue = paramJSONObject;
    this.mReportContent = paramString2;
  }

  public String getHttpMethod()
  {
    return POST;
  }

  public String toGetUrl()
  {
    return this.mReportContent;
  }

  public JSONObject toJson()
  {
    return this.mValue;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.fb.net.FbRequest
 * JD-Core Version:    0.6.2
 */