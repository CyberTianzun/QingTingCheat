package com.tencent.weibo.sdk.android.network;

import android.content.Context;
import android.util.Log;
import com.tencent.weibo.sdk.android.api.util.JsonUtil;
import com.tencent.weibo.sdk.android.model.BaseVO;
import com.tencent.weibo.sdk.android.model.ModelResult;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONArray;
import org.json.JSONObject;

public class HttpReqWeiBo extends HttpReq
{
  private Context mContext;
  private Integer mResultType = Integer.valueOf(0);
  private Class<? extends BaseVO> mTargetClass;
  private Class<? extends BaseVO> mTargetClass2;

  public HttpReqWeiBo(Context paramContext, String paramString1, HttpCallback paramHttpCallback, Class<? extends BaseVO> paramClass, String paramString2, Integer paramInteger)
  {
    this.mContext = paramContext;
    this.mHost = "192.168.1.100";
    this.mPort = 8088;
    this.mUrl = paramString1;
    this.mCallBack = paramHttpCallback;
    this.mTargetClass = paramClass;
    this.mResultType = paramInteger;
    this.mMethod = paramString2;
  }

  protected Object processResponse(InputStream paramInputStream)
    throws Exception
  {
    ModelResult localModelResult = new ModelResult();
    if (paramInputStream != null)
    {
      InputStreamReader localInputStreamReader = new InputStreamReader(paramInputStream);
      BufferedReader localBufferedReader = new BufferedReader(localInputStreamReader);
      StringBuffer localStringBuffer = new StringBuffer();
      while (true)
      {
        String str1 = localBufferedReader.readLine();
        if (str1 == null)
        {
          localBufferedReader.close();
          localInputStreamReader.close();
          Log.d("relst", localStringBuffer.toString());
          if ((localStringBuffer.toString().indexOf("errcode") != -1) || (localStringBuffer.toString().indexOf("access_token") == -1))
            break;
          localModelResult.setObj(localStringBuffer.toString());
          return localModelResult;
        }
        localStringBuffer.append(str1);
      }
      JSONObject localJSONObject = new JSONObject(localStringBuffer.toString());
      Class localClass = this.mTargetClass;
      BaseVO localBaseVO1 = null;
      if (localClass != null)
        localBaseVO1 = (BaseVO)this.mTargetClass.newInstance();
      String str2 = localJSONObject.getString("errcode");
      String str3 = localJSONObject.getString("msg");
      if ((str2 != null) && ("0".equals(str2)))
      {
        localModelResult.setSuccess(true);
        switch (this.mResultType.intValue())
        {
        default:
          return localModelResult;
        case 0:
          BaseVO localBaseVO3 = JsonUtil.jsonToObject(this.mTargetClass, localJSONObject);
          ArrayList localArrayList = new ArrayList();
          localArrayList.add(localBaseVO3);
          localModelResult.setList(localArrayList);
          return localModelResult;
        case 1:
          Map localMap = localBaseVO1.analyseHead(localJSONObject);
          JSONArray localJSONArray2 = (JSONArray)localMap.get("array");
          List localList2 = JsonUtil.jsonToList(this.mTargetClass, localJSONArray2);
          int i;
          Integer localInteger1;
          int j;
          Integer localInteger2;
          if (localMap.get("total") == null)
          {
            i = 0;
            localInteger1 = Integer.valueOf(i);
            if (localMap.get("p") != null)
              break label458;
            j = 1;
            localInteger2 = Integer.valueOf(j);
            if (localMap.get("ps") != null)
              break label478;
          }
          for (int k = 1; ; k = ((Integer)localMap.get("ps")).intValue())
          {
            Integer localInteger3 = Integer.valueOf(k);
            boolean bool = ((Boolean)localMap.get("isLastPage")).booleanValue();
            localModelResult.setList(localList2);
            localModelResult.setTotal(localInteger1.intValue());
            localModelResult.setP(localInteger2.intValue());
            localModelResult.setPs(localInteger3.intValue());
            localModelResult.setLastPage(bool);
            return localModelResult;
            i = ((Integer)localMap.get("total")).intValue();
            break;
            j = ((Integer)localMap.get("p")).intValue();
            break label351;
          }
        case 2:
          localModelResult.setObj(JsonUtil.jsonToObject(this.mTargetClass, localJSONObject));
          return localModelResult;
        case 3:
          label351: BaseVO localBaseVO2 = JsonUtil.jsonToObject(this.mTargetClass, localJSONObject);
          label458: label478: JSONArray localJSONArray1 = localJSONObject.getJSONArray("result_list");
          List localList1 = JsonUtil.jsonToList(this.mTargetClass2, localJSONArray1);
          localModelResult.setObj(localBaseVO2);
          localModelResult.setList(localList1);
          return localModelResult;
        case 4:
        }
        localModelResult.setObj(localJSONObject);
        return localModelResult;
      }
      localModelResult.setSuccess(false);
      localModelResult.setError_message(str3);
      return localModelResult;
    }
    localModelResult.setSuccess(false);
    localModelResult.setError_message("请求失败");
    return localModelResult;
  }

  public void setReq(String paramString)
    throws Exception
  {
    if ("POST".equals(this.mMethod))
    {
      HttpReq.UTF8PostMethod localUTF8PostMethod = new HttpReq.UTF8PostMethod(this.mUrl);
      this.mParam.toString();
      localUTF8PostMethod.setRequestEntity(new ByteArrayRequestEntity(this.mParam.toString().getBytes("utf-8")));
    }
  }

  protected void setReq(HttpMethod paramHttpMethod)
    throws Exception
  {
    if ("POST".equals(this.mMethod))
    {
      PostMethod localPostMethod = (PostMethod)paramHttpMethod;
      this.mParam.toString();
      localPostMethod.addParameter("Connection", "Keep-Alive");
      localPostMethod.addParameter("Charset", "UTF-8");
      localPostMethod.setRequestEntity(new ByteArrayRequestEntity(this.mParam.toString().getBytes("utf-8")));
    }
  }

  public void setmResultType(Integer paramInteger)
  {
    this.mResultType = paramInteger;
  }

  public void setmTargetClass(Class<? extends BaseVO> paramClass)
  {
    this.mTargetClass = paramClass;
  }

  public void setmTargetClass2(Class<? extends BaseVO> paramClass)
  {
    this.mTargetClass2 = paramClass;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.weibo.sdk.android.network.HttpReqWeiBo
 * JD-Core Version:    0.6.2
 */