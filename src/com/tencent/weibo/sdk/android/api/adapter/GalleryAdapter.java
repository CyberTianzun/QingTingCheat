package com.tencent.weibo.sdk.android.api.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.PopupWindow;
import com.tencent.weibo.sdk.android.api.util.ImageLoaderAsync;
import com.tencent.weibo.sdk.android.api.util.ImageLoaderAsync.callBackImage;
import com.tencent.weibo.sdk.android.model.ImageInfo;
import java.util.ArrayList;

public class GalleryAdapter extends BaseAdapter
{
  private ArrayList<ImageInfo> imageList;
  private ImageLoaderAsync imageLoader;
  private Context myContext;
  private PopupWindow popView;

  public GalleryAdapter(Context paramContext, PopupWindow paramPopupWindow, ArrayList<ImageInfo> paramArrayList)
  {
    this.myContext = paramContext;
    this.imageList = paramArrayList;
    this.imageLoader = new ImageLoaderAsync();
    this.popView = paramPopupWindow;
  }

  public int getCount()
  {
    return this.imageList.size();
  }

  public Object getItem(int paramInt)
  {
    return this.imageList.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    final ImageView localImageView = new ImageView(this.myContext);
    String str = ((ImageInfo)this.imageList.get(paramInt)).getImagePath();
    this.imageLoader.loadImage(str, new ImageLoaderAsync.callBackImage()
    {
      public void callback(Drawable paramAnonymousDrawable, String paramAnonymousString)
      {
        if (paramAnonymousDrawable != null)
          localImageView.setImageDrawable(paramAnonymousDrawable);
      }
    });
    localImageView.setScaleType(ImageView.ScaleType.FIT_XY);
    localImageView.setLayoutParams(new Gallery.LayoutParams(-1, -1));
    if ((this.popView != null) && (this.popView.isShowing()))
      this.popView.dismiss();
    return localImageView;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.weibo.sdk.android.api.adapter.GalleryAdapter
 * JD-Core Version:    0.6.2
 */