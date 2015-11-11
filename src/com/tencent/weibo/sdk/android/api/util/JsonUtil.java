package com.tencent.weibo.sdk.android.api.util;

import com.tencent.weibo.sdk.android.model.BaseVO;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonUtil
{
  public static List<BaseVO> jsonToList(Class<? extends BaseVO> paramClass, JSONArray paramJSONArray)
    throws Exception
  {
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; ; i++)
    {
      if (i >= paramJSONArray.length())
        return localArrayList;
      localArrayList.add(jsonToObject(paramClass, paramJSONArray.getJSONObject(i)));
    }
  }

  public static BaseVO jsonToObject(Class<? extends BaseVO> paramClass, JSONObject paramJSONObject)
    throws Exception
  {
    HashMap localHashMap = new HashMap();
    Method[] arrayOfMethod = paramClass.getMethods();
    int i = arrayOfMethod.length;
    int j = 0;
    BaseVO localBaseVO;
    Field[] arrayOfField;
    int k;
    int m;
    if (j >= i)
    {
      localBaseVO = (BaseVO)paramClass.newInstance();
      arrayOfField = paramClass.getDeclaredFields();
      k = arrayOfField.length;
      m = 0;
    }
    while (true)
    {
      if (m >= k)
      {
        return localBaseVO;
        Method localMethod1 = arrayOfMethod[j];
        if (localMethod1.getName().startsWith("set"))
          localHashMap.put(localMethod1.getName().toLowerCase(), localMethod1);
        j++;
        break;
      }
      Field localField = arrayOfField[m];
      String str1 = localField.getType().getName();
      String str2 = localField.getName();
      try
      {
        if (str1.equals("boolean"))
        {
          Method localMethod10 = (Method)localHashMap.get("set" + str2.toLowerCase());
          Object[] arrayOfObject9 = new Object[1];
          arrayOfObject9[0] = Boolean.valueOf(paramJSONObject.getBoolean(str2));
          localMethod10.invoke(localBaseVO, arrayOfObject9);
        }
        else if (str1.equals(Boolean.class.getName()))
        {
          Method localMethod9 = (Method)localHashMap.get("set" + str2.toLowerCase());
          Object[] arrayOfObject8 = new Object[1];
          arrayOfObject8[0] = Boolean.valueOf(paramJSONObject.getBoolean(str2));
          localMethod9.invoke(localBaseVO, arrayOfObject8);
        }
        else if ((str1.equals("int")) || (str1.equals("byte")))
        {
          Method localMethod2 = (Method)localHashMap.get("set" + str2.toLowerCase());
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = Integer.valueOf(paramJSONObject.getInt(str2));
          localMethod2.invoke(localBaseVO, arrayOfObject1);
        }
        else if ((str1.equals(Integer.class.getName())) || (str1.equals(Byte.class.getName())))
        {
          Method localMethod3 = (Method)localHashMap.get("set" + str2.toLowerCase());
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = Integer.valueOf(paramJSONObject.getInt(str2));
          localMethod3.invoke(localBaseVO, arrayOfObject2);
        }
        else if (str1.equals(String.class.getName()))
        {
          Method localMethod8 = (Method)localHashMap.get("set" + str2.toLowerCase());
          Object[] arrayOfObject7 = new Object[1];
          arrayOfObject7[0] = paramJSONObject.getString(str2);
          localMethod8.invoke(localBaseVO, arrayOfObject7);
        }
        else if ((str1.equals("double")) || (str1.equals("float")))
        {
          Method localMethod4 = (Method)localHashMap.get("set" + str2.toLowerCase());
          Object[] arrayOfObject3 = new Object[1];
          arrayOfObject3[0] = Double.valueOf(paramJSONObject.getDouble(str2));
          localMethod4.invoke(localBaseVO, arrayOfObject3);
        }
        else if ((str1.equals(Double.class.getName())) || (str1.equals(Float.class.getName())))
        {
          Method localMethod5 = (Method)localHashMap.get("set" + str2.toLowerCase());
          Object[] arrayOfObject4 = new Object[1];
          arrayOfObject4[0] = Double.valueOf(paramJSONObject.getDouble(str2));
          localMethod5.invoke(localBaseVO, arrayOfObject4);
        }
        else if (str1.equals("long"))
        {
          Method localMethod7 = (Method)localHashMap.get("set" + str2.toLowerCase());
          Object[] arrayOfObject6 = new Object[1];
          arrayOfObject6[0] = Long.valueOf(paramJSONObject.getLong(str2));
          localMethod7.invoke(localBaseVO, arrayOfObject6);
        }
        else if (str1.equals(Long.class.getName()))
        {
          Method localMethod6 = (Method)localHashMap.get("set" + str2.toLowerCase());
          Object[] arrayOfObject5 = new Object[1];
          arrayOfObject5[0] = Long.valueOf(paramJSONObject.getLong(str2));
          localMethod6.invoke(localBaseVO, arrayOfObject5);
        }
        label838: m++;
      }
      catch (Exception localException)
      {
        break label838;
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.weibo.sdk.android.api.util.JsonUtil
 * JD-Core Version:    0.6.2
 */