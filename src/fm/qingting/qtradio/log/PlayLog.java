package fm.qingting.qtradio.log;

import android.content.Context;
import fm.qingting.qtradio.logger.QTLogger;
import fm.qingting.qtradio.model.ProgramABTestBean;
import java.util.ArrayList;
import java.util.List;

public class PlayLog
{
  public static final int ALARM = 12;
  public static final int ALIAS_PUSH = 32;
  public static final int CATEGORY_ALL = 26;
  public static final int CATEGORY_BIG = 25;
  public static final int CATEGORY_FORM = 40;
  public static final int CATEGORY_MORE = 34;
  public static final int CATEGORY_SMALL = 36;
  public static final int CATEGORY_TOPIC = 27;
  public static final int COLLECTION = 4;
  public static final int CONENT_UPDATE_PUSH = 28;
  public static final int CONTINUE_PLAY_PUSH = 30;
  public static final int DOWNLOAD = 6;
  public static final int FRONTPAGE_BIG = 21;
  public static final int FRONTPAGE_BILLBOARD = 24;
  public static final int FRONTPAGE_MORE = 33;
  public static final int FRONTPAGE_SMALL = 22;
  public static final int FRONTPAGE_TOPIC = 23;
  public static final int GLOBAL_PUSH = 29;
  public static final int H5_BANNER = 46;
  public static final int H5_CATEGORY_BIG = 47;
  public static final int H5_CATEGORY_SMALL = 48;
  public static final int H5_GEZI_MORE = 53;
  public static final int H5_LIST = 49;
  public static final int H5_LIST_MORE = 54;
  public static final int H5_PODCASTER = 52;
  public static final int H5_RANK = 50;
  public static final int H5_SPECIAL_TOPIIC = 51;
  public static final int LAST_PLAYED = 13;
  public static final int LIVE_CHANNEL_CATEGORY = 38;
  public static final int LIVE_CHANNEL_PUSH = 39;
  public static final int NOVEL_PUSH = 31;
  public static final int OTHER = 0;
  public static final int PLAYHISTORY = 7;
  public static final int PODCASTER = 44;
  public static final String PlayLog = "playlogv6";
  public static final int RANK = 45;
  public static final int RECOMMEND_LIVE_CHANNEL = 37;
  public static final int RESERVE = 8;
  public static final int SEARCH = 5;
  public static final int SPECIAL_TOPIC = 41;
  public static final int SYSTEM_RADIO = 9;
  public static final int USER_FILTER = 35;
  private static PlayLog instance;
  private int mCategoryId = 0;
  private int mChannelId = 0;
  private int mChannelType = 0;
  private String mCity;
  private List<Integer> mLstABtestCatid = new ArrayList();
  private List<Integer> mLstABtestNum = new ArrayList();
  private List<Integer> mLstABtestOption = new ArrayList();
  private List<ProgramABTestBean> mLstProgramABTest = new ArrayList();
  private int mProgramId = 0;
  private String mRegion;
  private int mSpeed = 0;
  private int mUniqueId = 0;
  private int playSource = 0;

  public static PlayLog getInstance()
  {
    if (instance == null)
      instance = new PlayLog();
    return instance;
  }

  private int hasCategoryABtest(int paramInt)
  {
    for (int i = 0; i < this.mLstABtestCatid.size(); i++)
      if (((Integer)this.mLstABtestCatid.get(i)).intValue() == paramInt)
        return i;
    return -1;
  }

  private int hasProgramABTest(int paramInt1, int paramInt2)
  {
    if (this.mChannelType == 1)
    {
      i = -1;
      return i;
    }
    for (int i = 0; ; i++)
    {
      if (i >= this.mLstProgramABTest.size())
        break label73;
      if ((((ProgramABTestBean)this.mLstProgramABTest.get(i)).channelId == paramInt1) && (((ProgramABTestBean)this.mLstProgramABTest.get(i)).uniqueId == paramInt2))
        break;
    }
    label73: return -1;
  }

  private static void log(String paramString)
  {
  }

  public void addABTestCategory(int paramInt1, int paramInt2, int paramInt3)
  {
    this.mLstABtestCatid.add(Integer.valueOf(paramInt1));
    this.mLstABtestNum.add(Integer.valueOf(paramInt2));
    this.mLstABtestOption.add(Integer.valueOf(paramInt3));
  }

  public void addABTestProgram(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    ProgramABTestBean localProgramABTestBean = new ProgramABTestBean();
    localProgramABTestBean.channelId = paramInt1;
    localProgramABTestBean.uniqueId = paramInt2;
    localProgramABTestBean.abtestNum = paramInt3;
    localProgramABTestBean.abtestOption = paramInt4;
    this.mLstProgramABTest.add(localProgramABTestBean);
  }

  public void addCommnPlayLog(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    this.mCategoryId = paramInt1;
    this.mChannelId = paramInt2;
    this.mSpeed = paramInt3;
    this.mProgramId = paramInt4;
    this.mUniqueId = paramInt5;
    this.mChannelType = paramInt6;
  }

  public void addSource(int paramInt)
  {
    this.playSource = paramInt;
    log("addSource: " + this.playSource);
  }

  public int getSource()
  {
    return this.playSource;
  }

  public boolean hasValidPlayInfo()
  {
    return (this.mCategoryId != 0) && (this.mChannelId != 0);
  }

  public void sendPlayLog(Context paramContext, int paramInt)
  {
    if ((paramContext == null) || (this.mCategoryId == 0) || (this.mChannelId == 0))
      return;
    QTLogger.getInstance().setContext(paramContext);
    int i = hasProgramABTest(this.mChannelId, this.mUniqueId);
    String str1;
    if ((i != -1) && (i < this.mLstProgramABTest.size()))
    {
      String str10 = ((ProgramABTestBean)this.mLstProgramABTest.get(i)).abtestNum + "-" + ((ProgramABTestBean)this.mLstProgramABTest.get(i)).abtestOption;
      str1 = QTLogger.getInstance().buildCommonLog(str10, null, null);
    }
    while (true)
    {
      if (str1 == null)
        str1 = QTLogger.getInstance().buildCommonLog();
      if (str1 == null)
        break;
      String str2 = str1 + "\"" + this.mCategoryId + "\"";
      String str3 = str2 + ",\"" + this.mChannelId + "\"";
      String str4 = str3 + ",\"" + this.mProgramId + "\"";
      if (this.mChannelType == 0);
      for (String str5 = str4 + ",\"" + this.mUniqueId + "\""; ; str5 = str4 + ",\"\"")
      {
        String str6 = str5 + ",\"" + paramInt + "\"";
        String str7 = str6 + ",\"" + this.playSource + "\"";
        String str8 = str7 + ",\"" + this.mSpeed + "\"";
        LogModule.getInstance().send("playlogv6", str8);
        return;
        int j = hasCategoryABtest(this.mCategoryId);
        if ((j == -1) || (j >= this.mLstABtestNum.size()))
          break label511;
        String str9 = this.mLstABtestNum.get(j) + "-" + this.mLstABtestOption.get(j);
        str1 = QTLogger.getInstance().buildCommonLog(str9, null, null);
        break;
      }
      label511: str1 = null;
    }
  }

  public void setLocation(String paramString1, String paramString2)
  {
    this.mCity = paramString1;
    this.mRegion = paramString2;
    QTLogger.getInstance().setCity(paramString1);
    QTLogger.getInstance().setRegion(paramString2);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.log.PlayLog
 * JD-Core Version:    0.6.2
 */