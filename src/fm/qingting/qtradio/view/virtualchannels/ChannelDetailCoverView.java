package fm.qingting.qtradio.view.virtualchannels;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View.MeasureSpec;
import android.widget.ImageView;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.utils.ImageLoader;
import fm.qingting.framework.utils.ImageLoaderHandler;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.model.AdvertisementInfoNode;
import fm.qingting.qtradio.model.AdvertisementItemNode;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.utils.ScreenType;

public class ChannelDetailCoverView extends ViewGroupViewImpl
{
  private final ViewLayout infoLayout = this.standardLayout.createChildLT(720, 315, 0, 114, ViewLayout.SCALE_FLAG_SLTCW);
  private String mBackgroundUrl;
  private ChannelDetailInfoView mInfoView;
  private boolean mIsAdBackgroundUrl;
  private ChannelOperateView mOperateView;
  private final ViewLayout operateLayout = this.standardLayout.createChildLT(720, 101, 0, 429, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 530, 720, 530, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);

  public ChannelDetailCoverView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(-1);
    this.mInfoView = new ChannelDetailInfoView(paramContext);
    addView(this.mInfoView);
    this.mOperateView = new ChannelOperateView(paramContext);
    addView(this.mOperateView);
  }

  private String getCoverUrl(ChannelNode paramChannelNode)
  {
    if (paramChannelNode == null);
    AdvertisementItemNode localAdvertisementItemNode;
    do
    {
      return null;
      localAdvertisementItemNode = InfoManager.getInstance().root().mAdvertisementInfoNode.getAdvertisement(paramChannelNode.channelId);
    }
    while ((localAdvertisementItemNode == null) || (localAdvertisementItemNode.skin == null) || (localAdvertisementItemNode.skin.equalsIgnoreCase("")));
    this.mIsAdBackgroundUrl = true;
    return localAdvertisementItemNode.skin;
  }

  private void internalSetBackground(Bitmap paramBitmap)
  {
    setBackgroundDrawable(new TrimedBackgroundDrawable(paramBitmap));
  }

  private void setBackgroundByUrl(String paramString)
  {
    this.mBackgroundUrl = paramString;
    int i = ScreenType.getWidth();
    int j = ScreenType.getHeight();
    Bitmap localBitmap = ImageLoader.getInstance(getContext()).getImage(paramString, i, j);
    if (localBitmap == null)
    {
      ImageLoaderHandler local1 = new ImageLoaderHandler()
      {
        public void loadImageFinish(boolean paramAnonymousBoolean, String paramAnonymousString, Bitmap paramAnonymousBitmap, int paramAnonymousInt1, int paramAnonymousInt2)
        {
          if (TextUtils.equals(ChannelDetailCoverView.this.mBackgroundUrl, paramAnonymousString))
            ChannelDetailCoverView.this.setBackgroundUsingBitmap(paramAnonymousBitmap);
        }

        public void updateImageViewFinish(boolean paramAnonymousBoolean, ImageView paramAnonymousImageView, String paramAnonymousString, Bitmap paramAnonymousBitmap)
        {
        }
      };
      ImageLoader.getInstance(getContext()).loadImage(paramString, null, this, local1);
      return;
    }
    setBackgroundUsingBitmap(localBitmap);
  }

  private void setBackgroundUsingBitmap(Bitmap paramBitmap)
  {
    if ((this.mBackgroundUrl == null) || (paramBitmap == null));
    while (true)
    {
      return;
      try
      {
        if (this.mIsAdBackgroundUrl)
        {
          internalSetBackground(paramBitmap);
          return;
        }
      }
      catch (Exception localException)
      {
      }
      catch (Error localError)
      {
      }
    }
  }

  public void close(boolean paramBoolean)
  {
    this.mInfoView.close(paramBoolean);
    this.mOperateView.close(paramBoolean);
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.infoLayout.layoutView(this.mInfoView);
    this.operateLayout.layoutView(this.mOperateView);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.infoLayout.scaleToBounds(this.standardLayout);
    this.operateLayout.scaleToBounds(this.standardLayout);
    this.infoLayout.measureView(this.mInfoView);
    this.operateLayout.measureView(this.mOperateView);
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("ca"))
      this.mInfoView.update(paramString, paramObject);
    do
    {
      return;
      if (paramString.equalsIgnoreCase("setData"))
      {
        String str = getCoverUrl((ChannelNode)paramObject);
        if ((str == null) || (str.length() == 0))
          setBackgroundColor(-1);
        while (true)
        {
          this.mInfoView.update(paramString, paramObject);
          this.mOperateView.update(paramString, paramObject);
          return;
          setBackgroundByUrl(str);
        }
      }
    }
    while (!paramString.equalsIgnoreCase("setpodcasterinfo"));
    this.mInfoView.update(paramString, paramObject);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.virtualchannels.ChannelDetailCoverView
 * JD-Core Version:    0.6.2
 */