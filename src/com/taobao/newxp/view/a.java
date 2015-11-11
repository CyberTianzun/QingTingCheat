package com.taobao.newxp.view;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.taobao.newxp.Promoter;
import com.taobao.newxp.Promoter.a;
import com.taobao.newxp.a.c;
import com.taobao.newxp.a.e;
import com.taobao.newxp.common.AlimmContext;
import com.taobao.newxp.common.ExchangeConstants;
import com.taobao.newxp.common.Log;
import com.taobao.newxp.common.b.d;
import com.taobao.newxp.controller.ExchangeDataService;
import com.taobao.newxp.controller.XpListenersCenter.AdapterListener;
import com.taobao.newxp.controller.XpListenersCenter.FitType;
import com.taobao.newxp.controller.XpListenersCenter.ListClickListener;
import java.util.List;

public class a extends ArrayAdapter<Promoter>
{
  public XpListenersCenter.ListClickListener a = null;
  boolean b = false;
  private Context c;
  private int d;
  private int e;
  private XpListenersCenter.AdapterListener f = null;
  private ExchangeDataService g;

  public a(Context paramContext, int paramInt1, List<Promoter> paramList, int paramInt2, int paramInt3, ExchangeDataService paramExchangeDataService)
  {
    super(paramContext, paramInt1, paramList);
    this.c = paramContext;
    this.d = paramInt2;
    this.e = paramInt3;
    this.g = paramExchangeDataService;
  }

  public void a(int paramInt)
  {
  }

  public void a(XpListenersCenter.AdapterListener paramAdapterListener)
  {
    this.f = paramAdapterListener;
  }

  public long getItemId(int paramInt)
  {
    return 0L;
  }

  public View getView(final int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    LayoutInflater localLayoutInflater = (LayoutInflater)this.c.getSystemService("layout_inflater");
    a locala1;
    if ((paramView == null) || (paramView.getTag() == null))
    {
      paramView = localLayoutInflater.inflate(this.d, paramViewGroup, false);
      locala1 = new a();
      locala1.a = ((ImageView)paramView.findViewById(c.I(this.c)));
      locala1.b = ((TextView)paramView.findViewById(c.l(this.c)));
      locala1.c = ((TextView)paramView.findViewById(c.G(this.c)));
      locala1.d = ((TextView)paramView.findViewById(c.H(this.c)));
      locala1.e = ((TextView)paramView.findViewById(c.u(this.c)));
      locala1.f = ((Button)paramView.findViewById(c.M(this.c)));
    }
    label805: label826: 
    while (true)
    {
      final Promoter localPromoter;
      try
      {
        locala1.g = ((ImageView)paramView.findViewById(c.s(this.c)));
        if (Log.LOG)
        {
          String str2 = ExchangeConstants.LOG_TAG;
          if ("New tip Imageview is " + locala1.g == null)
          {
            str3 = "null";
            Log.c(str2, str3);
          }
        }
        else
        {
          paramView.setTag(locala1);
          locala2 = locala1;
          localPromoter = (Promoter)getItem(paramInt);
          locala2.a.setImageDrawable(this.c.getResources().getDrawable(com.taobao.newxp.a.b.b(this.c)));
          if (locala2.a != null)
          {
            if (!ExchangeConstants.ROUND_ICON)
              break label721;
            d.a(this.c, locala2.a, localPromoter.icon, false, null, null, true);
          }
          if (locala2.b != null)
          {
            if (!AlimmContext.getAliContext().getAppUtils().c(localPromoter.app_package_name))
              break label742;
            locala2.b.setText(e.b(this.c));
            if ((!AlimmContext.getAliContext().getAppUtils().c(localPromoter.app_package_name)) && (!TextUtils.isEmpty(localPromoter.price)))
              locala2.b.setText(localPromoter.price);
          }
          if (locala2.c != null)
            locala2.c.setText(localPromoter.title);
          if (locala2.d != null)
            locala2.d.setText(localPromoter.ad_words);
          if (!ExchangeConstants.show_size)
            break label805;
          if (locala2.e != null)
            locala2.e.setText(com.taobao.newxp.common.b.b.a(this.c, localPromoter.size));
          if (locala2.f != null)
            locala2.d.setText(localPromoter.ad_words);
          View.OnClickListener local1 = new View.OnClickListener()
          {
            public void onClick(View paramAnonymousView)
            {
              com.taobao.newxp.controller.b.a(new com.taobao.newxp.controller.a.a(localPromoter, paramInt), a.a(a.this), a.b(a.this), true, paramInt);
            }
          };
          if (locala2.f != null)
          {
            if (!AlimmContext.getAliContext().getAppUtils().c(localPromoter.app_package_name))
              break label826;
            locala2.f.setText(e.b(this.c));
            if (this.f != null)
              this.f.onFitType(paramView, localPromoter, XpListenersCenter.FitType.OPEN);
            if (localPromoter.new_tip != 1)
              break label1017;
            if (this.f != null)
              this.f.onFitType(paramView, localPromoter, XpListenersCenter.FitType.NEW);
            if (locala2.g != null)
              locala2.g.setVisibility(0);
            locala2.f.setOnClickListener(local1);
          }
          ExchangeConstants.definePageLevel(this.e);
          paramView.setOnClickListener(new View.OnClickListener()
          {
            public void onClick(View paramAnonymousView)
            {
              if (a.this.a != null)
              {
                a.this.a.click(localPromoter);
                return;
              }
              com.taobao.newxp.controller.b.a(new com.taobao.newxp.controller.a.a(localPromoter, paramInt), a.a(a.this), a.b(a.this), false, paramInt);
            }
          });
          if (paramInt == -1 + getCount())
          {
            Log.c(ExchangeConstants.LOG_TAG, "get last position data " + paramInt);
            a(paramInt);
          }
          return paramView;
        }
      }
      catch (Exception localException)
      {
        locala1.g = null;
        continue;
        String str3 = "not null";
        continue;
      }
      a locala2 = (a)paramView.getTag();
      continue;
      label721: d.a(this.c, locala2.a, localPromoter.icon, false);
      continue;
      label742: if ((localPromoter.landing_type == 3) || (localPromoter.landing_type == 2) || (localPromoter.landing_type == 4))
      {
        locala2.b.setText(e.c(this.c));
      }
      else
      {
        locala2.b.setText(e.e(this.c));
        continue;
        if (locala2.e != null)
        {
          locala2.e.setVisibility(8);
          continue;
          if ((localPromoter.landing_type == 3) || (localPromoter.landing_type == 2) || (localPromoter.landing_type == 4))
          {
            int i = e.c(this.c);
            String str1 = Uri.parse(localPromoter.url).getScheme();
            if ((str1 != null) && (str1.equalsIgnoreCase(Promoter.a.a.toString())))
            {
              locala2.f.setText(e.f(this.c));
              e.f(this.c);
              if (this.f != null)
                this.f.onFitType(paramView, localPromoter, XpListenersCenter.FitType.PHONE);
            }
            else
            {
              locala2.f.setText(i);
              if (this.f != null)
                this.f.onFitType(paramView, localPromoter, XpListenersCenter.FitType.BROWSE);
            }
          }
          else
          {
            locala2.f.setText(e.e(this.c));
            if (this.f != null)
            {
              this.f.onFitType(paramView, localPromoter, XpListenersCenter.FitType.DOWNLOAD);
              continue;
              label1017: if (locala2.g != null)
                locala2.g.setVisibility(8);
            }
          }
        }
      }
    }
  }

  static class a
  {
    ImageView a;
    TextView b;
    TextView c;
    TextView d;
    TextView e;
    Button f;
    ImageView g;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.view.a
 * JD-Core Version:    0.6.2
 */