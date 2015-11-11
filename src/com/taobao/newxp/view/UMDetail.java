package com.taobao.newxp.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.taobao.newxp.Promoter;
import com.taobao.newxp.a.e;
import com.taobao.newxp.common.AlimmContext;
import com.taobao.newxp.common.ExchangeConstants;
import com.taobao.newxp.common.Log;
import com.taobao.newxp.common.b.d.b;
import com.taobao.newxp.common.b.d.c;
import com.taobao.newxp.controller.ExchangeDataService;
import com.taobao.newxp.controller.XpListenersCenter.ExchangeDataRequestListener;
import com.taobao.newxp.controller.b;
import com.taobao.newxp.net.MMEntity;
import com.taobao.newxp.net.h;
import com.taobao.newxp.net.s;
import com.taobao.newxp.net.s.a;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UMDetail extends Activity
{
  public static final String ENTITY_KEY = "entity";
  public static final String PROMOTER_KEY = "promoter";
  private static final String k = UMDetail.class.getName();
  HorizontalStrip a;
  View b;
  MMEntity c;
  Context d;
  Promoter e;
  int f;
  TextView g;
  volatile Map<String, Drawable> h = new HashMap();
  List<f.a> i;
  ExchangeDataService j;

  private void a(Promoter paramPromoter, int paramInt, ExchangeDataService paramExchangeDataService)
  {
    Uri localUri = Uri.parse(paramPromoter.url);
    if (!AlimmContext.getAliContext().getAppUtils().e("android.permission.CALL_PHONE"))
    {
      Toast.makeText(this.d, "This App has no call_phone permission!", 0).show();
      return;
    }
    String str = localUri.getAuthority();
    Intent localIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + str));
    this.d.startActivity(localIntent);
  }

  private void b()
  {
    int m = ExchangeConstants.definePageLevel(this.c.layoutType);
    s.a locala1 = new s.a(this.c).a(3).b(this.f).c(m);
    Promoter[] arrayOfPromoter = new Promoter[1];
    arrayOfPromoter[0] = this.e;
    s.a locala2 = locala1.a(arrayOfPromoter);
    new h(this.d, this.e.url).a(locala2).d(this.e.title).a();
  }

  private void c()
  {
    if (this.j != null)
    {
      this.j.setFilterInstalledApp(false);
      this.j.getEntity().filterPromoter = this.e.promoter;
      final View localView = findViewById(com.taobao.newxp.a.c.ag(this.d));
      localView.setVisibility(8);
      this.j.requestDataAsyn(this.d, new XpListenersCenter.ExchangeDataRequestListener()
      {
        public void dataReceived(int paramAnonymousInt, List<Promoter> paramAnonymousList)
        {
          if ((paramAnonymousList != null) && (paramAnonymousList.size() >= 4))
          {
            int i = 0;
            if (i < 4)
            {
              final Promoter localPromoter = (Promoter)paramAnonymousList.get(i);
              Object localObject2;
              Object localObject1;
              switch (i)
              {
              default:
                localObject2 = null;
                localObject1 = null;
              case 0:
              case 1:
              case 2:
              case 3:
              }
              while (true)
              {
                if (localObject1 != null)
                  ((ImageView)localObject1).setOnClickListener(new View.OnClickListener()
                  {
                    public void onClick(View paramAnonymous2View)
                    {
                      b.a(localPromoter, UMDetail.this.d, UMDetail.this.j, false);
                    }
                  });
                if (localObject2 != null)
                  localObject2.setText(localPromoter.title);
                Animation localAnimation = AnimationUtils.loadAnimation(UMDetail.this.d, com.taobao.newxp.a.a.n(UMDetail.this.d));
                com.taobao.newxp.common.b.d.a(UMDetail.this.d, (ImageView)localObject1, localPromoter.icon, false, null, localAnimation, true);
                localView.setVisibility(0);
                i++;
                break;
                ImageView localImageView4 = (ImageView)localView.findViewById(com.taobao.newxp.a.c.ah(UMDetail.this.d));
                TextView localTextView4 = (TextView)localView.findViewById(com.taobao.newxp.a.c.ai(UMDetail.this.d));
                localObject1 = localImageView4;
                localObject2 = localTextView4;
                continue;
                ImageView localImageView3 = (ImageView)localView.findViewById(com.taobao.newxp.a.c.aj(UMDetail.this.d));
                TextView localTextView3 = (TextView)localView.findViewById(com.taobao.newxp.a.c.ak(UMDetail.this.d));
                localObject1 = localImageView3;
                localObject2 = localTextView3;
                continue;
                ImageView localImageView2 = (ImageView)localView.findViewById(com.taobao.newxp.a.c.al(UMDetail.this.d));
                TextView localTextView2 = (TextView)localView.findViewById(com.taobao.newxp.a.c.am(UMDetail.this.d));
                localObject1 = localImageView2;
                localObject2 = localTextView2;
                continue;
                ImageView localImageView1 = (ImageView)localView.findViewById(com.taobao.newxp.a.c.an(UMDetail.this.d));
                TextView localTextView1 = (TextView)localView.findViewById(com.taobao.newxp.a.c.ao(UMDetail.this.d));
                localObject1 = localImageView1;
                localObject2 = localTextView1;
              }
            }
            new s.a(UMDetail.this.j.getEntity()).a(0).b(0).a((Promoter[])paramAnonymousList.toArray(new Promoter[paramAnonymousList.size()])).a().a();
          }
        }
      }
      , true);
    }
  }

  private void d()
  {
    int m = 0;
    String str1 = k;
    StringBuilder localStringBuilder = new StringBuilder().append("Start load imgs. [imgs.length");
    if (this.e.imgs == null);
    for (int n = 0; ; n = this.e.imgs.length)
    {
      Log.a(str1, n + "]");
      if ((this.e.imgs == null) || (this.e.imgs.length <= 0))
        break;
      String[] arrayOfString = this.e.imgs;
      int i1 = arrayOfString.length;
      while (m < i1)
      {
        final String str2 = arrayOfString[m];
        com.taobao.newxp.common.b.d.a(this.d, str2, new d.c()
        {
          public void a(Drawable paramAnonymousDrawable)
          {
            UMDetail.this.h.put(str2, paramAnonymousDrawable);
            Log.a(UMDetail.a(), "Loaded drawable[" + str2 + "]");
            if (UMDetail.this.h.size() == UMDetail.this.e.imgs.length)
            {
              UMDetail.this.i = UMDetail.this.filterBadIMG();
              UMDetail.this.showStrip(UMDetail.this.i);
            }
          }

          public void a(d.b paramAnonymousb)
          {
          }
        });
        m++;
      }
    }
    findViewById(com.taobao.newxp.a.c.ar(this.d)).setVisibility(8);
  }

  protected List<f.a> filterBadIMG()
  {
    ArrayList localArrayList = new ArrayList();
    String[] arrayOfString = this.e.imgs;
    int m = arrayOfString.length;
    int n = 0;
    while (n < m)
    {
      String str1 = arrayOfString[n];
      Drawable localDrawable = (Drawable)this.h.get(str1);
      if ((localDrawable != null) && (localDrawable.getIntrinsicHeight() > 0) && (localDrawable.getIntrinsicWidth() > 0))
      {
        localArrayList.add(new f.a(str1, localDrawable));
        n++;
      }
      else
      {
        String str2 = k;
        StringBuilder localStringBuilder = new StringBuilder().append("filter bad image [").append(str1).append("]").append("   ");
        if (localDrawable == null);
        for (String str3 = "null"; ; str3 = "Exist")
        {
          Log.a(str2, str3);
          break;
        }
      }
    }
    return localArrayList;
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.d = this;
    setContentView(com.taobao.newxp.a.d.h(this.d));
    Bundle localBundle = getIntent().getExtras();
    if (localBundle != null)
    {
      this.e = ((Promoter)localBundle.getParcelable("promoter"));
      this.c = ((MMEntity)localBundle.getParcelable("entity"));
      this.f = localBundle.getInt("action_index");
    }
    MMEntity localMMEntity = new MMEntity();
    localMMEntity.psid = this.c.psid;
    localMMEntity.slotId = this.c.slotId;
    localMMEntity.appkey = this.c.appkey;
    localMMEntity.layoutType = 16;
    this.j = new ExchangeDataService()
    {
      protected void a()
      {
        super.a();
        UMDetail.this.j.getEntity().psid = UMDetail.this.c.psid;
      }
    };
    this.j.setEntity(localMMEntity);
    ((TextView)findViewById(com.taobao.newxp.a.c.aa(this.d))).setText(this.e.title);
    if ((TextView)findViewById(com.taobao.newxp.a.c.V(this.d)) != null);
    ((TextView)findViewById(com.taobao.newxp.a.c.ab(this.d))).setText(this.e.provider);
    this.g = ((TextView)findViewById(com.taobao.newxp.a.c.ac(this.d)));
    this.g.setText(this.e.description);
    if (this.e.description.length() < 75)
      findViewById(com.taobao.newxp.a.c.as(this.d)).setVisibility(8);
    this.g.setMaxLines(3);
    final TextView localTextView = (TextView)findViewById(com.taobao.newxp.a.c.as(this.d));
    localTextView.setOnClickListener(new View.OnClickListener()
    {
      int a;
      int b = 3;
      boolean c = false;

      public void onClick(View paramAnonymousView)
      {
        ViewGroup.LayoutParams localLayoutParams = UMDetail.this.g.getLayoutParams();
        if (this.a == 0)
          this.a = localLayoutParams.height;
        if (3 <= UMDetail.this.g.getLineCount())
        {
          if (!this.c)
            break label137;
          UMDetail.this.g.setMaxLines(this.b);
          this.c = false;
          localTextView.setText(UMDetail.this.d.getText(e.o(UMDetail.this.d)));
        }
        while (true)
        {
          Log.a(UMDetail.a(), "descript text view has changed height.[" + localLayoutParams.height + "]");
          UMDetail.this.g.requestLayout();
          return;
          label137: UMDetail.this.g.setMaxLines(2147483647);
          this.c = true;
          localTextView.setText(UMDetail.this.d.getText(e.p(UMDetail.this.d)));
        }
      }
    });
    ImageView localImageView = (ImageView)findViewById(com.taobao.newxp.a.c.ad(this.d));
    Animation localAnimation = AnimationUtils.loadAnimation(this.d, com.taobao.newxp.a.a.n(this.d));
    com.taobao.newxp.common.b.d.a(this.d, localImageView, this.e.icon, false, null, localAnimation, true);
    this.a = ((HorizontalStrip)findViewById(com.taobao.newxp.a.c.ae(this.d)));
    this.b = findViewById(com.taobao.newxp.a.c.af(this.d));
    findViewById(com.taobao.newxp.a.c.at(this.d)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        UMDetail.a(UMDetail.this);
      }
    });
    findViewById(com.taobao.newxp.a.c.au(this.d)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        UMDetail.this.finish();
      }
    });
    c();
  }

  protected void onDestroy()
  {
    super.onDestroy();
    this.a.setAdapter(null);
    Iterator localIterator = this.h.values().iterator();
    while (localIterator.hasNext())
    {
      Drawable localDrawable = (Drawable)localIterator.next();
      if (localDrawable != null)
        localDrawable.setCallback(null);
    }
    this.h.clear();
  }

  protected void onResume()
  {
    super.onResume();
    d();
  }

  protected void showStrip(List<f.a> paramList)
  {
    if (paramList.size() > 0)
    {
      f localf = f.a(paramList);
      this.a.setAdapter(localf);
      this.b.setVisibility(8);
      this.a.setVisibility(0);
      return;
    }
    findViewById(com.taobao.newxp.a.c.ar(this.d)).setVisibility(8);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.view.UMDetail
 * JD-Core Version:    0.6.2
 */