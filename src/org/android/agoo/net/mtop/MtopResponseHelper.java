package org.android.agoo.net.mtop;

import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class MtopResponseHelper
{
  public static final String FAIL = "FAIL";
  public static final String SUCCESS = "SUCCESS";

  public static void handlerMessageError(String paramString)
  {
    if (TextUtils.equals(paramString, "ER_PARAM_DEVICE_TOKEN"));
    while ((TextUtils.equals(paramString, "ER_PARAM_INVALID")) || (TextUtils.equals(paramString, "ER_PARAM_APP_PACKAGE")) || (TextUtils.equals(paramString, "ER_PARAM_AGOO_SDK_VERSION")) || (TextUtils.equals(paramString, "ER_BIZ_NO_MULTIPLEX")) || (TextUtils.equals(paramString, "FAIL")) || (TextUtils.equals(paramString, "FAIL_SYS_SERVLET_ASYNC_TIMEOUT")) || (TextUtils.equals(paramString, "FAIL_SYS_SERVLET_ASYNC_ERROR")) || (TextUtils.equals(paramString, "FAIL_SYS_PARAM_FORMAT_ERROR")) || (TextUtils.equals(paramString, "FAIL_SYS_PARAM_MISSING")) || (TextUtils.equals(paramString, "API_STOP_SERVICE")) || (TextUtils.equals(paramString, "ERRCODE_AUTH_REJECT")) || (TextUtils.equals(paramString, "ERRCODE_APP_ACCESS_API_FAIL")) || (TextUtils.equals(paramString, "ERR_SID_INVALID")) || (TextUtils.equals(paramString, "FAIL_SYS_HSF_ASYNC_POOL_FOOL")) || (TextUtils.equals(paramString, "FAIL_SYS_HSF_ASYNC_TIMEOUT")) || (TextUtils.equals(paramString, "FAIL_SYS_HSF_THROWN_EXCEPTION_CODE")) || (TextUtils.equals(paramString, "FAIL_SYS_HSF_INVOKE_ERROR")) || (TextUtils.equals(paramString, "FAIL_SYS_HSF_NOTFOUND")) || (!TextUtils.equals(paramString, "FAIL_SYS_HSF_TIMEOUT")))
      return;
  }

  public static Result parse(String paramString)
    throws Throwable
  {
    int i = 0;
    Result localResult = new Result();
    while (true)
    {
      try
      {
        JSONObject localJSONObject = new JSONObject(paramString);
        JSONArray localJSONArray = localJSONObject.getJSONArray("ret");
        int j = localJSONArray.length();
        if (i >= j)
          break;
        String str = localJSONArray.getString(i);
        if (!TextUtils.isEmpty(str))
        {
          String[] arrayOfString = str.split("::");
          if ((arrayOfString != null) && (2 == arrayOfString.length))
          {
            localResult.setRetCode(arrayOfString[0]);
            localResult.setRetDesc(arrayOfString[1]);
            if ("SUCCESS".equals(arrayOfString[0]))
            {
              localResult.setData(localJSONObject.getString("data"));
              localResult.setSuccess(true);
              return localResult;
            }
            localResult.setSuccess(false);
            localResult.setData(localJSONObject.getString("data"));
          }
        }
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
      i++;
    }
    return localResult;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.android.agoo.net.mtop.MtopResponseHelper
 * JD-Core Version:    0.6.2
 */