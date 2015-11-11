package fm.qingting.qtradio.abtest;

import android.content.Context;
import com.umeng.analytics.MobclickAgent;
import fm.qingting.qtradio.fm.PlayerAgent;
import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.logger.QTLogger;
import fm.qingting.qtradio.model.GlobalCfg;
import fm.qingting.qtradio.model.H5Bean;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.ProgramABTestBean;
import fm.qingting.qtradio.model.SharedCfg;
import fm.qingting.utils.LifeTime;
import fm.qingting.utils.QTMSGManage;
import fm.qingting.utils.RangeRandom;

public class ABTest
{
  private static final String ExternalSeperator = ";";
  private static final String InternalSeperator = "-";
  private static final String KEY_ABTEST_H5 = "KEY_ABTEST_H5";
  private static final String KEY_ABTEST_PROGRAM = "KEY_ABTEST_PROGRAM";
  private static final String LogType = "ABTest";
  private static ABTest _ins;
  private Context _context;
  private boolean hassendH5ABTest = false;
  private String keys_abtest_h5 = null;
  private String keys_abtest_program = null;
  private boolean shouldSelect = false;

  public static String buildCommonLogWithABTest(Context paramContext, boolean paramBoolean)
  {
    String str1 = buildUserTypeString(paramContext, ABTestConfig.items);
    QTLogger.getInstance().setContext(paramContext);
    QTLogger localQTLogger = QTLogger.getInstance();
    if (LifeTime.isFirstLaunchAfterInstall);
    for (String str2 = "1"; ; str2 = "0")
    {
      String str3 = localQTLogger.buildCommonLog(str1, null, str2).trim();
      if ((!paramBoolean) && (str3.charAt(-1 + str3.length()) == ','))
        str3 = str3.substring(0, -1 + str3.length());
      return str3;
    }
  }

  public static String buildUserTypeString(Context paramContext, ABTestItem[] paramArrayOfABTestItem)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < paramArrayOfABTestItem.length; i++)
    {
      ABTestItem localABTestItem = paramArrayOfABTestItem[i];
      String str = GlobalCfg.getInstance(paramContext).getValueFromDB(localABTestItem.OptionName);
      if ((str != null) && (str.trim().length() > 0))
      {
        if (localStringBuffer.length() > 0)
          localStringBuffer.append(";");
        localStringBuffer.append(localABTestItem.number + "-" + str);
      }
    }
    return localStringBuffer.toString();
  }

  private void fetchCoverage()
  {
    String str = MobclickAgent.getConfigParams(this._context, "ABTestCoverage");
    if (str != null);
    try
    {
      ABTestConfig.coverage = Double.parseDouble(str);
      log("coverage:" + ABTestConfig.coverage);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public static String getCommonLogWithPushType(Context paramContext, ABTestItem[] paramArrayOfABTestItem)
  {
    String str1 = buildUserTypeString(paramContext, paramArrayOfABTestItem);
    String str2 = buildUserTypeString(paramContext, ABTestConfig.items);
    QTLogger.getInstance().setContext(paramContext);
    QTLogger localQTLogger = QTLogger.getInstance();
    if (LifeTime.isFirstLaunchAfterInstall);
    for (String str3 = "1"; ; str3 = "0")
      return localQTLogger.buildCommonLog(str2, str1, str3);
  }

  public static ABTest getInstance()
  {
    if (_ins == null)
      _ins = new ABTest();
    return _ins;
  }

  private void init(Context paramContext)
  {
    this._context = paramContext;
    if (LifeTime.isFirstLaunchAfterInstall)
      fetchCoverage();
    this.keys_abtest_h5 = SharedCfg.getInstance().getValue("KEY_ABTEST_H5");
    this.keys_abtest_program = SharedCfg.getInstance().getValue("KEY_ABTEST_PROGRAM");
  }

  private boolean isOptionEnabled(String paramString)
  {
    String str1 = MobclickAgent.getConfigParams(this._context, paramString);
    if ((str1 != null) && (str1.equalsIgnoreCase("1")))
    {
      log(paramString + " is enabled");
      return true;
    }
    String str2;
    if (str1 == null)
    {
      str2 = paramString + " null";
      QTMSGManage.getInstance().sendStatistcsMessage("UmengParameterFail", str2);
    }
    while (true)
    {
      log(str2);
      return false;
      str2 = paramString + ":" + str1;
      QTMSGManage.getInstance().sendStatistcsMessage("UmengParameterFail", str2);
    }
  }

  private static void log(String paramString)
  {
  }

  private void recordOption(String paramString1, String paramString2)
  {
    log("[recordOption]" + paramString1 + ":" + paramString2);
    GlobalCfg.getInstance(this._context).setValueToDB(paramString1, "String", paramString2);
  }

  private String selectOption(String paramString1, String paramString2)
  {
    if (RangeRandom.randomSwitch())
      return paramString1;
    return paramString2;
  }

  private static void sendABTestLogIfChosen(Context paramContext, ABTestItem[] paramArrayOfABTestItem)
  {
    String str1 = buildUserTypeString(paramContext, paramArrayOfABTestItem);
    if ((str1 != null) && (str1.trim().length() > 0))
    {
      String str2 = QTLogger.getInstance().buildCommonLog(str1, null, String.valueOf(LifeTime.isFirstLaunchAfterInstall)).trim();
      if (str2.charAt(-1 + str2.length()) == ',')
        str2 = str2.substring(0, -1 + str2.length());
      String str3 = str2 + "\n";
      LogModule.getInstance().send("ABTest", str3);
      return;
    }
    log("user type string is empty. don't send log");
  }

  private boolean shouldSelect()
  {
    return RangeRandom.random(ABTestConfig.coverage);
  }

  public void addH5ABTest(H5Bean paramH5Bean)
  {
    if (!InfoManager.getInstance().enableH5());
    String str;
    label202: label216: 
    do
    {
      do
      {
        return;
        if ((!this.shouldSelect) || (paramH5Bean == null) || (paramH5Bean.abtestNum <= 0))
          break;
        if (this.keys_abtest_h5 == null)
          break label202;
      }
      while (this.keys_abtest_h5.contains(String.valueOf(paramH5Bean.abtestNum)));
      this.keys_abtest_h5 += "_";
      for (this.keys_abtest_h5 += paramH5Bean.abtestNum; ; this.keys_abtest_h5 = String.valueOf(paramH5Bean.abtestNum))
      {
        SharedCfg.getInstance().saveValue("KEY_ABTEST_H5", this.keys_abtest_h5);
        SharedCfg.getInstance().saveValue(String.valueOf(paramH5Bean.abtestNum), selectOption("0", "1"));
        if ((paramH5Bean == null) || (paramH5Bean.abtestNum <= 0) || (paramH5Bean.type != 2))
          break;
        str = SharedCfg.getInstance().getValue(String.valueOf(paramH5Bean.abtestNum));
        if (str == null)
          break;
        if (!str.equalsIgnoreCase("1"))
          break label216;
        PlayerAgent.getInstance().addABTestCategory(paramH5Bean.id, paramH5Bean.abtestNum, 1);
        return;
      }
    }
    while (!str.equalsIgnoreCase("0"));
    PlayerAgent.getInstance().addABTestCategory(paramH5Bean.id, paramH5Bean.abtestNum, 0);
  }

  public void addProgramABTest(ProgramABTestBean paramProgramABTestBean)
  {
    if (!InfoManager.getInstance().enableProgramABTest());
    String str;
    label206: label220: 
    do
    {
      do
      {
        return;
        if ((!this.shouldSelect) || (paramProgramABTestBean == null) || (paramProgramABTestBean.abtestNum <= 0))
          break;
        if (this.keys_abtest_program == null)
          break label206;
      }
      while (this.keys_abtest_program.contains(String.valueOf(paramProgramABTestBean.abtestNum)));
      this.keys_abtest_program += "_";
      for (this.keys_abtest_program += paramProgramABTestBean.abtestNum; ; this.keys_abtest_program = String.valueOf(paramProgramABTestBean.abtestNum))
      {
        SharedCfg.getInstance().saveValue("KEY_ABTEST_PROGRAM", this.keys_abtest_program);
        SharedCfg.getInstance().saveValue(String.valueOf(paramProgramABTestBean.abtestNum), selectOption("0", "1"));
        if ((paramProgramABTestBean == null) || (paramProgramABTestBean.abtestNum <= 0) || (paramProgramABTestBean.type != 5))
          break;
        str = SharedCfg.getInstance().getValue(String.valueOf(paramProgramABTestBean.abtestNum));
        if (str == null)
          break;
        if (!str.equalsIgnoreCase("1"))
          break label220;
        PlayerAgent.getInstance().addABTestProgram(paramProgramABTestBean.channelId, paramProgramABTestBean.uniqueId, paramProgramABTestBean.abtestNum, 1);
        return;
      }
    }
    while (!str.equalsIgnoreCase("0"));
    PlayerAgent.getInstance().addABTestProgram(paramProgramABTestBean.channelId, paramProgramABTestBean.uniqueId, paramProgramABTestBean.abtestNum, 0);
  }

  public String getOption(String paramString)
  {
    String str = GlobalCfg.getInstance(this._context).getValueFromDB(paramString);
    log("[getOption]" + paramString + ":" + str);
    return str;
  }

  public boolean manualSetOption(ABTestItem paramABTestItem, String paramString)
  {
    if (isOptionEnabled(paramABTestItem.OptionName))
    {
      recordOption(paramABTestItem.OptionName, paramString);
      return true;
    }
    return false;
  }

  public void sendH5ABTest()
  {
    if (!InfoManager.getInstance().enableH5());
    String str1;
    label145: 
    do
    {
      String[] arrayOfString;
      do
      {
        do
          return;
        while ((this.keys_abtest_h5 == null) || (this.keys_abtest_h5.equalsIgnoreCase("")) || (this.hassendH5ABTest));
        arrayOfString = this.keys_abtest_h5.split("_");
      }
      while (arrayOfString == null);
      int i = 0;
      str1 = null;
      if (i < arrayOfString.length)
      {
        String str4 = arrayOfString[i];
        String str5;
        if ((str4 != null) && (!str4.equalsIgnoreCase("")))
        {
          str5 = SharedCfg.getInstance().getValue(str4);
          if ((str5 != null) && (!str5.equalsIgnoreCase("")))
            if (str1 != null)
              break label145;
        }
        String str6;
        for (str1 = str4 + "-" + str5; ; str1 = str6 + str4 + "-" + str5)
        {
          i++;
          break;
          str6 = str1 + ";";
        }
      }
    }
    while (str1 == null);
    String str2 = QTLogger.getInstance().buildCommonLog(str1, null, String.valueOf(LifeTime.isFirstLaunchAfterInstall)).trim();
    if (str2.charAt(-1 + str2.length()) == ',')
      str2 = str2.substring(0, -1 + str2.length());
    String str3 = str2 + "\n";
    LogModule.getInstance().send("ABTest", str3);
    this.hassendH5ABTest = true;
  }

  public void startABTest(Context paramContext)
  {
    init(paramContext);
    if (LifeTime.isFirstLaunchAfterInstall)
    {
      log("is First launch after install");
      if (shouldSelect())
      {
        this.shouldSelect = true;
        log("select this user.");
        int i = 0;
        if (i < ABTestConfig.items.length)
        {
          ABTestItem localABTestItem = ABTestConfig.items[i];
          if (localABTestItem.generateMethod != ABTestItem.GenerateMethod.Auto);
          while (true)
          {
            i++;
            break;
            if (isOptionEnabled(localABTestItem.OptionName))
              recordOption(localABTestItem.OptionName, selectOption(localABTestItem.OptionA, localABTestItem.OptionB));
          }
        }
        GlobalCfg.getInstance(this._context).saveValueToDB();
        sendABTestLogIfChosen(this._context, ABTestConfig.items);
        return;
      }
      log("don't select this user.");
      return;
    }
    log("Not First launch after install");
    sendABTestLogIfChosen(this._context, ABTestConfig.items);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.abtest.ABTest
 * JD-Core Version:    0.6.2
 */