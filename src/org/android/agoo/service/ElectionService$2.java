package org.android.agoo.service;

import android.text.TextUtils;
import com.umeng.message.proguard.Q;
import java.util.Map;
import org.android.agoo.net.mtop.MtopResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;

class ElectionService$2 extends MtopResponseHandler
{
  ElectionService$2(ElectionService paramElectionService)
  {
  }

  public void onFailure(String paramString1, String paramString2)
  {
    Q.d("ElectionService", "errCode[" + paramString1 + "]errDesc[" + paramString2 + "]");
    ElectionService.b(this.a);
  }

  public void onSuccess(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
    {
      Q.c("ElectionService", "remote content==null");
      ElectionService.b(this.a);
      return;
    }
    Q.c("ElectionService", "remote election result[" + paramString + "] ");
    JSONArray localJSONArray1;
    try
    {
      JSONObject localJSONObject1 = new JSONObject(paramString);
      ElectionService.a(this.a, Long.parseLong(localJSONObject1.getString("time_out")));
      localJSONArray1 = localJSONObject1.getJSONArray("vote_list");
      if (localJSONArray1 == null)
      {
        Q.c("ElectionService", "remote vote_list==null");
        ElectionService.b(this.a);
        return;
      }
    }
    catch (Throwable localThrowable)
    {
      Q.d("ElectionService", "remote--JSONException", localThrowable);
      ElectionService.b(this.a);
      return;
    }
    int i = localJSONArray1.length();
    if (i <= 0)
    {
      Q.c("ElectionService", "remote vote_list.length==0");
      ElectionService.b(this.a);
      return;
    }
    while (true)
    {
      int j;
      String str1;
      JSONArray localJSONArray2;
      int k;
      if (j < i)
      {
        JSONObject localJSONObject2 = localJSONArray1.getJSONObject(j);
        if (localJSONObject2 == null)
        {
          ElectionService.b(this.a);
          return;
        }
        str1 = localJSONObject2.getString("package_name");
        if (TextUtils.isEmpty(str1))
        {
          Q.c("ElectionService", "sudoPack==null");
          ElectionService.b(this.a);
          return;
        }
        if (ElectionService.c(this.a).get(str1) == null)
        {
          Q.c("ElectionService", "elctionResults not found[" + str1 + "]");
          ElectionService.b(this.a);
          return;
        }
        localJSONArray2 = localJSONObject2.getJSONArray("package_name_list");
        if (localJSONArray2 == null)
        {
          Q.c("ElectionService", "remote package_name_list==null");
          ElectionService.b(this.a);
          return;
        }
        k = localJSONArray2.length();
        if (k > 0)
          break label376;
        Q.c("ElectionService", "remote package_name_list.length==0");
        ElectionService.b(this.a);
      }
      while (true)
      {
        if (m >= k)
          break label382;
        String str2 = localJSONArray2.getString(m);
        ElectionService.d(this.a).put(str2, str1);
        m++;
        continue;
        ElectionService.a(this.a, "remote");
        return;
        j = 0;
        break;
        label376: int m = 0;
      }
      label382: j++;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.android.agoo.service.ElectionService.2
 * JD-Core Version:    0.6.2
 */