package com.taobao.newxp.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import com.taobao.newxp.a.d;
import com.taobao.newxp.view.widget.SwipeView;

public class ScreenshotDialog extends Dialog
{
  private static boolean a = false;
  View b;
  Context c;
  SwipeView d;
  e e;

  public ScreenshotDialog(Context paramContext, e parame)
  {
    super(paramContext, 16973840);
    WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
    localLayoutParams.height = -1;
    localLayoutParams.width = -1;
    localLayoutParams.gravity = 17;
    getWindow().setAttributes(localLayoutParams);
    this.e = parame;
    this.c = paramContext;
    this.b = LayoutInflater.from(paramContext).inflate(d.g(this.c), null);
    this.d = ((SwipeView)this.b.findViewById(com.taobao.newxp.a.c.Z(this.c)));
    Display localDisplay = ((WindowManager)this.c.getSystemService("window")).getDefaultDisplay();
    View.OnClickListener local1 = new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ScreenshotDialog.this.dismiss();
      }
    };
    for (int i = 0; i < this.e.a(); i++)
    {
      View localView = this.e.a(this.c, localDisplay.getWidth(), localDisplay.getHeight(), i);
      this.d.addView(localView);
      localView.setOnClickListener(local1);
    }
    getWindow().setContentView(this.b, new ViewGroup.LayoutParams(-1, -1));
    getWindow().getAttributes().windowAnimations = com.taobao.newxp.common.b.c.a(this.c).i("taobao_xp_dialog_animations");
  }

  public void dismiss()
  {
    super.dismiss();
    a = false;
  }

  public void show(int paramInt)
  {
    if (!a)
    {
      this.d.scrollToPage(paramInt);
      a = true;
      super.show();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.view.ScreenshotDialog
 * JD-Core Version:    0.6.2
 */