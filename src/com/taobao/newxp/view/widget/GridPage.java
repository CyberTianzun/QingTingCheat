package com.taobao.newxp.view.widget;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.taobao.munion.base.a;
import com.taobao.newxp.Promoter;
import com.taobao.newxp.common.AlimmContext;
import com.taobao.newxp.common.Log;
import com.taobao.newxp.view.container.GridTemplateConfig;
import java.util.ArrayList;
import java.util.List;

public class GridPage extends RelativeLayout
{
  private static final String a = "GridPage";
  private final GridPageAdapter b;
  private final GridTemplateConfig c;
  private final Context d;
  private int e = 0;
  private final List<LinearLayout> f;

  public GridPage(Context paramContext, GridPageAdapter paramGridPageAdapter, GridTemplateConfig paramGridTemplateConfig)
  {
    super(paramContext);
    this.d = paramContext;
    this.b = paramGridPageAdapter;
    this.c = paramGridTemplateConfig;
    this.f = new ArrayList();
    a();
  }

  private void a()
  {
    int i = this.b.getCount();
    int j = this.c.numColumns;
    int k = (int)AlimmContext.getAliContext().getAppUtils().a(this.c.verticalSpacing);
    Log.c("GridPage", "GridPage init params numColums=" + j + "   verticalSpacing=" + k);
    int m;
    int n;
    if (i % j == 0)
    {
      m = i / j;
      n = 0;
    }
    int i3;
    for (int i1 = 0; ; i1 = i3)
    {
      if (n >= m)
        return;
      LinearLayout localLinearLayout = new LinearLayout(this.d);
      RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-1, -2);
      localLinearLayout.setId(n + 10);
      if (n > 0)
        localLayoutParams.addRule(3, -1 + localLinearLayout.getId());
      if ((k > 0) && (n > 0))
        localLayoutParams.topMargin = k;
      localLinearLayout.setLayoutParams(localLayoutParams);
      localLinearLayout.setOrientation(0);
      int i2 = i1;
      i3 = i1;
      while (true)
        if (i2 < i1 + j)
        {
          RelativeLayout localRelativeLayout = new RelativeLayout(this.d);
          LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(-1, -1);
          localLayoutParams1.weight = 1.0F;
          localRelativeLayout.setLayoutParams(localLayoutParams1);
          int i4 = i3 + 1;
          if (i3 < i)
            localRelativeLayout.addView(this.b.getView(i2));
          localLinearLayout.addView(localRelativeLayout);
          i2++;
          i3 = i4;
          continue;
          m = 1 + i / j;
          break;
        }
      this.f.add(localLinearLayout);
      addView(localLinearLayout);
      this.e = i;
      n++;
    }
  }

  public void notifyDataSetChanged(boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      if (this.e != this.b.getCount())
      {
        removeAllViews();
        a();
        Log.c("GridPage", "data has changed..");
        return;
      }
      Log.c("GridPage", "data has no changed..");
      return;
    }
    removeAllViews();
    Log.c("GridPage", "pre cast change page.." + getChildCount());
    a();
    Log.c("GridPage", "cast change page.." + getChildCount());
  }

  public static abstract class GridPageAdapter
  {
    List<Promoter> c;
    GridPage.PageInfo d;

    public GridPageAdapter(List<Promoter> paramList, GridPage.PageInfo paramPageInfo)
    {
      this.c = paramList;
      this.d = paramPageInfo;
    }

    public abstract View buildView(int paramInt1, int paramInt2, Promoter paramPromoter);

    public int getCount()
    {
      return this.d.count;
    }

    public View getView(int paramInt)
    {
      int i = paramInt + this.d.sPos;
      return buildView(paramInt, i, (Promoter)this.c.get(i));
    }
  }

  public static class PageInfo
  {
    public int count;
    public int lastPos;
    public int page;
    public boolean pageChange = false;
    public int sPos;
    public boolean show = false;

    public PageInfo(int paramInt1, int paramInt2)
    {
      this.sPos = paramInt1;
      this.count = paramInt2;
      this.lastPos = (-1 + (paramInt1 + paramInt2));
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.view.widget.GridPage
 * JD-Core Version:    0.6.2
 */