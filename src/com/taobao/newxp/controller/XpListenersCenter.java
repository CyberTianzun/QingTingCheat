package com.taobao.newxp.controller;

import android.view.View;
import android.view.ViewGroup;
import com.taobao.newxp.Promoter;
import java.util.List;

public class XpListenersCenter
{
  public static abstract interface AdapterListener
  {
    public abstract void onFitType(View paramView, Promoter paramPromoter, XpListenersCenter.FitType paramFitType);
  }

  public static enum BindMode
  {
    static
    {
      BindMode[] arrayOfBindMode = new BindMode[2];
      arrayOfBindMode[0] = BIND_FORM_CACHE;
      arrayOfBindMode[1] = BIND_FROM_NET;
    }
  }

  public static abstract interface EntryOnClickListener
  {
    public abstract void onClick(View paramView);
  }

  public static abstract interface ExchangeDataRequestListener
  {
    public static final int STATUS_FAIL = 0;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_SUCCESS_WITH_GIF_SHOW = 2;

    public abstract void dataReceived(int paramInt, List<Promoter> paramList);
  }

  public static enum FitType
  {
    static
    {
      DOWNLOAD = new FitType("DOWNLOAD", 1);
      BROWSE = new FitType("BROWSE", 2);
      PHONE = new FitType("PHONE", 3);
      NEW = new FitType("NEW", 4);
      FitType[] arrayOfFitType = new FitType[5];
      arrayOfFitType[0] = OPEN;
      arrayOfFitType[1] = DOWNLOAD;
      arrayOfFitType[2] = BROWSE;
      arrayOfFitType[3] = PHONE;
      arrayOfFitType[4] = NEW;
    }
  }

  public static abstract class FloatDialogListener
  {
    public void onClick()
    {
    }

    public void onClose()
    {
    }

    public boolean onConfirmClickWithCallBackUrl(String paramString)
    {
      return false;
    }

    public void onPrepared(int paramInt)
    {
    }

    public void onShow(boolean paramBoolean)
    {
    }

    public void onStart()
    {
    }
  }

  public static abstract interface InitializeListener
  {
    public abstract void onReceived(int paramInt);

    public abstract void onStartRequestData(int paramInt);
  }

  public static abstract interface LargeGalleryBindListener
  {
    public abstract void onEnd(XpListenersCenter.STATUS paramSTATUS, ViewGroup paramViewGroup);

    public abstract void onStart(XpListenersCenter.BindMode paramBindMode, ViewGroup paramViewGroup);
  }

  public static abstract interface ListClickListener
  {
    public abstract void click(Promoter paramPromoter);
  }

  public static abstract interface NTipsChangedListener
  {
    public abstract void onChanged(int paramInt);
  }

  public static enum STATUS
  {
    static
    {
      FAIL = new STATUS("FAIL", 1);
      STATUS[] arrayOfSTATUS = new STATUS[2];
      arrayOfSTATUS[0] = SUCCESS;
      arrayOfSTATUS[1] = FAIL;
    }
  }

  public static abstract interface WelcomeAdsListener
  {
    public abstract void onCountdown(int paramInt);

    public abstract void onDataReviced(Promoter paramPromoter);

    public abstract void onError(String paramString);

    public abstract void onFinish();

    public abstract void onShow(View paramView);
  }

  public static abstract interface onHandleVisListener
  {
    public abstract void onVisable();
  }

  public static abstract interface onPageChangedListener
  {
    public abstract void onPageChanged(int paramInt, Promoter paramPromoter, View paramView);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.controller.XpListenersCenter
 * JD-Core Version:    0.6.2
 */