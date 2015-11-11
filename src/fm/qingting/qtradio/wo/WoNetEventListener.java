package fm.qingting.qtradio.wo;

import android.content.Context;
import android.telephony.TelephonyManager;
import fm.qingting.qtradio.manager.INETEventListener;
import fm.qingting.qtradio.manager.NetWorkManage;

public class WoNetEventListener
  implements INETEventListener
{
  private static String TAG = "WoNetEventListener";
  private static WoNetEventListener instance = null;
  private Context _context;
  private int isChinaUnicom = -1;

  private WoNetEventListener()
  {
    NetWorkManage.getInstance().addListener(this);
  }

  public static WoNetEventListener getInstance()
  {
    try
    {
      if (instance == null)
        instance = new WoNetEventListener();
      WoNetEventListener localWoNetEventListener = instance;
      return localWoNetEventListener;
    }
    finally
    {
    }
  }

  public static boolean hasOpenUniCom(String paramString)
  {
    String[] arrayOfString = { "天津", "福建", "山东", "辽宁", "湖北", "安徽", "山西", "黑龙江", "青海", "甘肃" };
    int i = arrayOfString.length;
    for (int j = 0; j < i; j++)
      if (arrayOfString[j].equalsIgnoreCase(paramString))
        return false;
    return true;
  }

  public static boolean isChinaUnicom(Context paramContext)
  {
    if (paramContext != null)
    {
      String str = ((TelephonyManager)paramContext.getSystemService("phone")).getSimOperator();
      if (str != null)
      {
        if (str.equalsIgnoreCase("46001"))
          return true;
        return !str.equals("");
      }
    }
    return false;
  }

  public void init(Context paramContext)
  {
    this._context = paramContext;
    onNetChanged(NetWorkManage.getInstance().getNetWorkType());
  }

  public boolean isChinaUnicom()
  {
    if (this._context != null)
    {
      String str = ((TelephonyManager)this._context.getSystemService("phone")).getSimOperator();
      if (str != null)
      {
        if (str.equals("46001"))
        {
          this.isChinaUnicom = 1;
          return true;
        }
        if (WoApiRequest.isChinaUnicomNetwork(this._context))
        {
          this.isChinaUnicom = 1;
          return true;
        }
        this.isChinaUnicom = 0;
        return false;
      }
      if (WoApiRequest.isChinaUnicomNetwork(this._context))
      {
        this.isChinaUnicom = 1;
        return true;
      }
      this.isChinaUnicom = 0;
      return false;
    }
    if (this.isChinaUnicom == 1)
      return true;
    return this.isChinaUnicom != 0;
  }

  public boolean isChinaUnicomNet()
  {
    String str = NetWorkManage.getInstance().getNetWorkType();
    return ((str == "3G") || (str == "2G")) && (isChinaUnicom());
  }

  public void onNetChanged(String paramString)
  {
    if (!isChinaUnicom())
      WoApiRequest.disableWoProxy();
    while ((!WoApiRequest.hasOpen()) || (paramString == null))
      return;
    if ((paramString.equalsIgnoreCase("3G")) || (paramString.equalsIgnoreCase("2G")) || (paramString.equalsIgnoreCase("WiFi")))
    {
      if (paramString.equalsIgnoreCase("WiFi"))
        WoApiRequest.disableWoProxy(this._context);
      while (true)
      {
        boolean bool1 = WoApiRequest.getInitState().equals(WoApiRequest.INIT_STAGE.DONE);
        boolean bool2 = WoApiRequest.getInitState().equals(WoApiRequest.INIT_STAGE.NOTHING);
        if ((!bool1) && (!bool2))
          break;
        WoApiRequest.reset();
        WoApiRequest.init(this._context);
        return;
        WoApiRequest.enableWoProxy(this._context);
      }
    }
    WoApiRequest.NET_TYPE localNET_TYPE = WoApiRequest.checkNetWorkType(this._context);
    if ((localNET_TYPE == WoApiRequest.NET_TYPE.NET) || (localNET_TYPE == WoApiRequest.NET_TYPE.WAP))
    {
      WoApiRequest.enableWoProxy(this._context);
      WoApiRequest.reset();
      WoApiRequest.init(this._context);
      return;
    }
    WoApiRequest.disableWoProxy(this._context);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.wo.WoNetEventListener
 * JD-Core Version:    0.6.2
 */