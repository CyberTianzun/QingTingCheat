package com.umeng.fb.net;

import org.json.JSONObject;

public class FbResponse extends UResponse
{
  private JSONObject jsonObj;

  public FbResponse(JSONObject paramJSONObject)
  {
    super(paramJSONObject);
    this.jsonObj = paramJSONObject;
  }

  public JSONObject getJson()
  {
    return this.jsonObj;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.fb.net.FbResponse
 * JD-Core Version:    0.6.2
 */