package fm.qingting.qtradio.view.settingviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import fm.qingting.download.QTRadioDownloadAgent;
import fm.qingting.framework.adapter.IAdapterIViewFactory;
import fm.qingting.framework.adapter.ItemParam;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ListViewImpl;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.GlobalCfg;
import fm.qingting.qtradio.model.SingleCheckAdapter;
import fm.qingting.qtradio.view.scheduleview.SizeInfo;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DownloadDirSettingView extends ListViewImpl
  implements IEventHandler
{
  private SingleCheckAdapter adapter;
  private IAdapterIViewFactory factory;
  private List<String> mSdList;

  public DownloadDirSettingView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(SkinManager.getBackgroundColor());
    this.factory = new IAdapterIViewFactory()
    {
      public IView createView(int paramAnonymousInt)
      {
        return new SingleCheckSettingItemView(DownloadDirSettingView.this.getContext(), this.val$hash);
      }
    };
    this.adapter = new SingleCheckAdapter(new ArrayList(), this.factory);
    this.adapter.setEventHandler(this);
    setVerticalScrollBarEnabled(false);
    setVerticalFadingEdgeEnabled(false);
    setCacheColorHint(0);
    setDivider(null);
    setHeaderDividersEnabled(false);
    setSelector(17170445);
    setAdapter(this.adapter);
  }

  @TargetApi(9)
  private boolean checkIfValid(String paramString)
  {
    File localFile = new File(paramString);
    if (!localFile.exists())
      localFile.mkdir();
    return true;
  }

  private void getAllExterSdcardPath()
  {
    this.mSdList = SettingConfig.getAllExterSdcardPath();
    String str1 = getFirstExterPath();
    ArrayList localArrayList = new ArrayList();
    String str2 = GlobalCfg.getInstance(null).getDownloadPath();
    List localList = this.mSdList;
    int i = 0;
    if (localList != null)
    {
      int j = this.mSdList.size();
      i = 0;
      if (j > 0)
      {
        int k = 0;
        int m = 0;
        int n = 0;
        while (k < this.mSdList.size())
        {
          String str3 = (String)this.mSdList.get(k);
          String str4 = str3 + File.separator + "QTDownloadRadio";
          this.mSdList.set(k, str4);
          if (!checkIfValid(str4))
          {
            k++;
          }
          else
          {
            String str5 = SizeInfo.getFileSize(QTRadioDownloadAgent.getInstance().getAvailableExternalMemorySize(str4));
            String str6 = SizeInfo.getFileSize(QTRadioDownloadAgent.getInstance().getTotalExternalMemorySize(str4));
            String str7 = "（" + str5 + "可用，" + "共" + str6 + "）";
            String str8;
            if (TextUtils.equals(str4, str1 + File.separator + "QTDownloadRadio"))
              str8 = "存储卡1" + str7;
            while (true)
            {
              String str9 = "位置：" + str4;
              localArrayList.add(new SettingItem(str8, SettingItem.SettingType.check, str4, str9));
              if (!TextUtils.equals(str2, str4))
                break;
              n = k;
              break;
              if (this.mSdList.size() == 2)
              {
                str8 = "存储卡2" + str7;
              }
              else
              {
                str8 = "存储卡2" + m + str7;
                m++;
              }
            }
          }
        }
        i = n;
      }
    }
    this.adapter.setData(localArrayList);
    this.adapter.checkIndex(i);
  }

  private String getFirstExterPath()
  {
    return Environment.getExternalStorageDirectory().getPath();
  }

  private void initData()
  {
    getAllExterSdcardPath();
  }

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("itemCallback"))
    {
      ItemParam localItemParam = (ItemParam)paramObject2;
      if (localItemParam.type.equalsIgnoreCase("check"))
      {
        int i = localItemParam.position;
        this.adapter.checkIndex(i);
        QTRadioDownloadAgent.getInstance().changeDownloadPath((String)this.mSdList.get(i));
      }
    }
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
      initData();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.settingviews.DownloadDirSettingView
 * JD-Core Version:    0.6.2
 */