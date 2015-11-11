package fm.qingting.qtradio.view.personalcenter.mydownload;

import android.content.Context;
import android.os.Environment;
import android.text.Layout.Alignment;
import android.text.TextUtils;
import android.view.View.MeasureSpec;
import fm.qingting.download.QTRadioDownloadAgent;
import fm.qingting.download.QTRadioDownloadAgent.OnDownloadPathChangeListener;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.TextViewElement;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.DownLoadInfoNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.view.scheduleview.SizeInfo;
import fm.qingting.qtradio.view.settingviews.SettingConfig;
import java.io.File;
import java.util.Locale;

public class StorageInfoView extends QtView
  implements IEventHandler, QTRadioDownloadAgent.OnDownloadPathChangeListener
{
  private final String LOCATIONMODEL = "存储位置:%s,可用空间%s";
  private final ViewLayout buttonLayout = this.standardLayout.createChildLT(120, 50, 300, 60, ViewLayout.SCALE_FLAG_SLTCW);
  private ButtonElement mButton;
  private TextViewElement mLocationView;
  private TextViewElement mUsageView;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 110, 720, 110, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout storageUsageLayout = this.standardLayout.createChildLT(720, 32, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);

  public StorageInfoView(Context paramContext)
  {
    super(paramContext);
    this.mUsageView = new TextViewElement(paramContext);
    this.mUsageView.setAlignment(Layout.Alignment.ALIGN_CENTER);
    this.mUsageView.setColor(SkinManager.getTextColorSubInfo());
    this.mUsageView.setMaxLineLimit(1);
    addElement(this.mUsageView);
    this.mLocationView = new TextViewElement(paramContext);
    this.mLocationView.setAlignment(Layout.Alignment.ALIGN_CENTER);
    this.mLocationView.setColor(SkinManager.getTextColorSubInfo());
    this.mLocationView.setText(getLocationText());
    this.mLocationView.setMaxLineLimit(1);
    addElement(this.mLocationView);
    this.mButton = new ButtonElement(paramContext);
    this.mButton.setText("设置");
    this.mButton.setFrameColor(SkinManager.getTextColorSubInfo(), SkinManager.getTextColorSubInfo());
    this.mButton.setTextColor(SkinManager.getBackgroundColor(), SkinManager.getTextColorSubInfo());
    addElement(this.mButton);
    if (!SettingConfig.needShowDownloadDirSetting())
      this.mButton.setVisible(4);
    this.mButton.setOnElementClickListener(new ViewElement.OnElementClickListener()
    {
      public void onElementClick(ViewElement paramAnonymousViewElement)
      {
        ControllerManager.getInstance().openDownloadDirSettingController();
      }
    });
    QTRadioDownloadAgent.getInstance().addPathChangeListener(this);
  }

  private String getAvailableMS()
  {
    String str = QTRadioDownloadAgent.getInstance().getDownLoadPath();
    return SizeInfo.getFileSize(QTRadioDownloadAgent.getInstance().getAvailableExternalMemorySize(str));
  }

  private String getLocationText()
  {
    if (TextUtils.equals(QTRadioDownloadAgent.getInstance().getDownLoadPath(), Environment.getExternalStorageDirectory().getPath() + File.separator + "QTDownloadRadio"));
    for (String str = "存储卡1"; ; str = "存储卡2")
    {
      Locale localLocale = Locale.CHINA;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = str;
      arrayOfObject[1] = getAvailableMS();
      return String.format(localLocale, "存储位置:%s,可用空间%s", arrayOfObject);
    }
  }

  public void close(boolean paramBoolean)
  {
    QTRadioDownloadAgent.getInstance().removePathChangeListener(this);
    super.close(paramBoolean);
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("onclick"))
      ControllerManager.getInstance().openDownloadDirSettingController();
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.storageUsageLayout.scaleToBounds(this.standardLayout);
    this.buttonLayout.scaleToBounds(this.standardLayout);
    this.mUsageView.measure(this.storageUsageLayout);
    this.mLocationView.measure(this.storageUsageLayout);
    this.mUsageView.setTextSize(SkinManager.getInstance().getTinyTextSize());
    this.mLocationView.setTextSize(SkinManager.getInstance().getTinyTextSize());
    this.mButton.setTextSize(SkinManager.getInstance().getTinyTextSize());
    int i;
    if (this.mUsageView.getVisiblity() == 0)
    {
      i = this.storageUsageLayout.height;
      this.mLocationView.setTranslationY(i);
    }
    while (true)
    {
      this.mButton.measure(this.buttonLayout.leftMargin, i + this.buttonLayout.topMargin, this.buttonLayout.getRight(), i + this.buttonLayout.getBottom());
      int j = i + this.storageUsageLayout.height;
      if (this.mButton.getVisiblity() == 0)
        j += this.buttonLayout.getBottom();
      setMeasuredDimension(this.standardLayout.width, j);
      return;
      this.mLocationView.setTranslationY(0);
      i = 0;
    }
  }

  public void onPathChanged(String paramString)
  {
    this.mLocationView.setText(getLocationText());
    dispatchActionEvent("pathchanged", null);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setUsageInfo"))
      if (InfoManager.getInstance().root().mDownLoadInfoNode.getTotalProgramCnt() == 0)
      {
        this.mUsageView.setVisible(4);
        this.mUsageView.setText((String)paramObject);
        this.mLocationView.setText(getLocationText());
        requestLayout();
      }
    while (!paramString.equalsIgnoreCase("setLocationInfo"))
      while (true)
      {
        return;
        this.mUsageView.setVisible(0);
      }
    this.mLocationView.setText(getLocationText());
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.personalcenter.mydownload.StorageInfoView
 * JD-Core Version:    0.6.2
 */