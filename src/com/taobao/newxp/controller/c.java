package com.taobao.newxp.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.taobao.munion.base.f;
import com.taobao.munion.base.volley.s;
import com.taobao.newxp.common.AlimmContext;
import com.taobao.newxp.common.ExchangeConstants;
import com.taobao.newxp.common.Log;
import com.taobao.newxp.net.MMEntity;
import com.taobao.newxp.net.b;
import com.taobao.newxp.net.o;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class c extends f
{
  ExchangeDataService a;
  private final XpListenersCenter.ExchangeDataRequestListener b;
  private final int c;
  private final boolean d;

  public c(ExchangeDataService paramExchangeDataService, XpListenersCenter.ExchangeDataRequestListener paramExchangeDataRequestListener, int paramInt, boolean paramBoolean)
  {
    this.a = paramExchangeDataService;
    this.b = paramExchangeDataRequestListener;
    this.c = paramInt;
    this.d = paramBoolean;
  }

  public void a()
  {
    a(false);
  }

  public void a(s params)
  {
    if (this.b != null)
      this.b.dataReceived(0, new ArrayList());
  }

  public void a(JSONObject paramJSONObject)
  {
    ArrayList localArrayList = new ArrayList();
    MMEntity localMMEntity = this.a.getEntity();
    boolean bool;
    if (TextUtils.isEmpty(localMMEntity.sid))
      bool = true;
    while (true)
    {
      int i = this.a.a.a(localMMEntity, localArrayList, this.a.mSpecificPromoterClz, paramJSONObject);
      Context localContext = AlimmContext.getAliContext().getAppContext();
      int j = paramJSONObject.optInt("preload", 0);
      SharedPreferences localSharedPreferences = localContext.getSharedPreferences(this.a.e, 0);
      if (localSharedPreferences.getInt(this.a.f, 0) != j)
      {
        SharedPreferences.Editor localEditor = localSharedPreferences.edit();
        localEditor.putInt(this.a.f, j);
        localEditor.commit();
      }
      if (localArrayList != null);
      try
      {
        if (localArrayList.size() <= 0)
          if (bool)
            this.a.removeCache();
        while (true)
        {
          if (this.b != null)
          {
            if (localMMEntity.filterInstalledApp)
            {
              int k = this.a.a(localArrayList);
              if ((k > 0) && (localMMEntity.newTips > 0))
              {
                int m = localMMEntity.newTips;
                n = localMMEntity.newTips - k;
                if (n <= 0)
                  break label313;
                localMMEntity.newTips = n;
                Log.c(ExchangeConstants.LOG_TAG, "new tips has changed " + m + " ===> " + localMMEntity.newTips);
              }
            }
            this.b.dataReceived(i, localArrayList);
          }
          return;
          bool = false;
          break;
          if ((this.d) && (i == 1))
            this.a.a(bool, paramJSONObject);
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          continue;
          label313: int n = -1;
        }
      }
    }
  }

  public void a(boolean paramBoolean)
  {
    TextUtils.isEmpty(this.a.b.sid);
    int i = this.c;
    if (i == 1)
    {
      ExchangeDataService localExchangeDataService = this.a;
      if (TextUtils.isEmpty(this.a.b.sid));
      for (boolean bool = true; ; bool = false)
      {
        List localList = localExchangeDataService.requestCache(bool, true);
        int j = 0;
        if (localList != null)
        {
          int k = localList.size();
          j = 0;
          if (k > 0)
            j = 1;
        }
        if (this.b != null)
          this.b.dataReceived(j, localList);
        Log.a(ExchangeConstants.LOG_TAG, "get data from cache.");
        return;
      }
    }
    o localo = this.a.a.a(this.a.b, paramBoolean, this);
    AlimmContext.getAliContext().getQueryQueue().a(localo);
    Log.a(ExchangeConstants.LOG_TAG, "get data from live.");
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.controller.c
 * JD-Core Version:    0.6.2
 */