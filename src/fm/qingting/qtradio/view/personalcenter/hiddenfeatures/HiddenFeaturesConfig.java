package fm.qingting.qtradio.view.personalcenter.hiddenfeatures;

import android.content.Context;
import fm.qingting.qtradio.logger.QTLogger;
import fm.qingting.qtradio.model.InfoManager;
import java.util.ArrayList;
import java.util.List;

public class HiddenFeaturesConfig
{
  public static final String PREFIX_CHANNELNAME = "渠道:";
  public static final String PREFIX_DEVICE_ID = "ID:";
  public static final String PREFIX_GETUI_CID = "个推:";

  public static List<Object> getConfigList()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new HiddenFeaturesItem("渠道:" + InfoManager.getInstance().getContext().getString(2131492868), HiddenFeaturesItem.HiddenFeaturesType.BUTTON, "copy", "复制", "internalCode:" + InfoManager.getInstance().getContext().getString(2131492875)));
    localArrayList.add(new HiddenFeaturesItem("ID:" + InfoManager.getInstance().getDeviceId(), HiddenFeaturesItem.HiddenFeaturesType.BUTTON, "copy", "复制"));
    String str = QTLogger.getInstance().getGeTuiCID();
    if (str != null)
      localArrayList.add(new HiddenFeaturesItem("个推:" + str, HiddenFeaturesItem.HiddenFeaturesType.BUTTON, "copy", "复制"));
    localArrayList.add(new HiddenFeaturesItem("主页面\"再按一次退出\"", HiddenFeaturesItem.HiddenFeaturesType.SWITCHER, "doublebackquit", null));
    localArrayList.add(new HiddenFeaturesItem("开启调试模式", HiddenFeaturesItem.HiddenFeaturesType.SWITCHER, "debug", null, "开启帮助我们发现问题！"));
    localArrayList.add(new HiddenFeaturesItem("启动页", HiddenFeaturesItem.HiddenFeaturesType.BUTTON, "launch", "打开", "此处有惊喜!"));
    return localArrayList;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.personalcenter.hiddenfeatures.HiddenFeaturesConfig
 * JD-Core Version:    0.6.2
 */