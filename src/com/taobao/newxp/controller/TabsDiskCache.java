package com.taobao.newxp.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;
import com.taobao.newxp.UFPResType;
import com.taobao.newxp.b;
import com.taobao.newxp.common.Category;
import com.taobao.newxp.common.ExchangeConstants;
import com.taobao.newxp.common.b.e;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TabsDiskCache
{
  public static final Category a = new Category("", "热卖", "", UFPResType.TB_ITEM, b.b);
  public static final Category b = new Category("", ExchangeConstants.DEFAULT_HANDLE_APP_WALL_TITLE, "", UFPResType.APP, b.a);
  private static final String e = TabsDiskCache.class.getName();
  private static final String f = "UMENG_TABS_CACHE";
  private static final String g = "Tabs";
  String c;
  int d = 0;
  private final Context h;

  private TabsDiskCache(Context paramContext)
  {
    this.h = paramContext;
  }

  public static TabsDiskCache a(Context paramContext, String paramString)
  {
    TabsDiskCache localTabsDiskCache = new TabsDiskCache(paramContext.getApplicationContext());
    localTabsDiskCache.c = paramString;
    Log.i(e, "Initailized TabsDiskCahce [" + localTabsDiskCache.c + "]");
    return localTabsDiskCache;
  }

  private List<Category> b(JSONArray paramJSONArray)
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    try
    {
      while (i < paramJSONArray.length())
      {
        JSONObject localJSONObject = (JSONObject)paramJSONArray.get(i);
        String str1 = localJSONObject.optString("title");
        String str2 = localJSONObject.optString("url_params");
        String str3 = localJSONObject.optString("resource_type");
        String str4 = localJSONObject.optString("template");
        String str5 = localJSONObject.optString("tabid", "");
        this.d = (1 + this.d);
        if (!TextUtils.isEmpty(str1))
        {
          UFPResType localUFPResType = UFPResType.fromString(str3);
          HashSet localHashSet = new HashSet();
          b localb = b.a(str4, localHashSet);
          Category localCategory = new Category(e.a(str2), str1, str5, localUFPResType, localb);
          localCategory.index = i;
          localCategory.addAttributes(localHashSet);
          localArrayList.add(localCategory);
        }
        i++;
      }
    }
    catch (JSONException localJSONException)
    {
      Log.w(e, "", localJSONException);
    }
    return localArrayList;
  }

  private String c()
  {
    return "Tabs_" + this.c;
  }

  public List<Category> a(Category[] paramArrayOfCategory)
  {
    JSONArray localJSONArray = a();
    if ((localJSONArray != null) && (localJSONArray.length() > 0))
    {
      List localList = b(localJSONArray);
      if ((localList == null) || (localList.size() == 0))
      {
        if (paramArrayOfCategory != null)
          localList = Arrays.asList(paramArrayOfCategory);
      }
      else
        return localList;
      return new ArrayList();
    }
    if (paramArrayOfCategory != null)
      return Arrays.asList(paramArrayOfCategory);
    return new ArrayList();
  }

  public JSONArray a()
  {
    if (this.h == null)
    {
      Log.e(e, "TabDiskCache is not initialized.");
      return null;
    }
    try
    {
      JSONArray localJSONArray = new JSONArray(this.h.getSharedPreferences("UMENG_TABS_CACHE", 1).getString(c(), ""));
      Log.i(e, "get Data from TabsDiskCahce [" + this.c + "] " + localJSONArray.toString());
      return localJSONArray;
    }
    catch (Exception localException)
    {
      Log.w(e, "", localException);
    }
    return null;
  }

  public boolean a(JSONArray paramJSONArray)
  {
    if (this.h == null)
      Log.e(e, "TabDiskCache is not initialized.");
    while (true)
    {
      return false;
      if (paramJSONArray != null)
        try
        {
          if (paramJSONArray.length() > 0)
          {
            SharedPreferences localSharedPreferences = this.h.getSharedPreferences("UMENG_TABS_CACHE", 2);
            SharedPreferences.Editor localEditor = localSharedPreferences.edit();
            try
            {
              localEditor.putString(c(), paramJSONArray.toString());
              localEditor.commit();
              Log.i(e, "update TabsDiskCahce [" + this.c + "] " + paramJSONArray.toString());
              return true;
            }
            finally
            {
            }
          }
        }
        catch (Exception localException)
        {
          Log.w(e, "", localException);
        }
    }
    return false;
  }

  public boolean b()
  {
    try
    {
      SharedPreferences.Editor localEditor = this.h.getSharedPreferences("UMENG_TABS_CACHE", 2).edit();
      localEditor.clear();
      localEditor.commit();
      return true;
    }
    catch (Exception localException)
    {
      Log.w(e, "", localException);
    }
    return false;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.controller.TabsDiskCache
 * JD-Core Version:    0.6.2
 */