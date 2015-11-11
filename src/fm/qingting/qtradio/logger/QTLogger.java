package fm.qingting.qtradio.logger;

import android.content.Context;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.utils.MobileState;
import fm.qingting.qtradio.model.BillboardItemNode;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.GlobalCfg;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RecommendItemNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.ShareBean;
import fm.qingting.qtradio.model.SpecialTopicNode;
import fm.qingting.qtradio.search.SearchItemNode;
import fm.qingting.qtradio.search.SearchNode;
import fm.qingting.utils.AppInfo;
import fm.qingting.utils.DateUtil;
import fm.qingting.utils.DeviceInfo;
import fm.qingting.utils.OperatorInfo;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class QTLogger
  implements LoggerAPI
{
  private static QTLogger instance;
  private Context _context;
  public String city;
  private String downloadException;
  public String fmAvailable;
  private String mGetuiClientID = null;
  private String mLocalIp = null;
  private int mSpecialTopicId = 0;
  private String mSpecialTopicName = null;
  public String region;

  private void addSearchLog(String paramString1, String paramString2)
  {
  }

  private String buildOtherString(String paramString1, String paramString2)
  {
    try
    {
      String str1 = buildCommonLog();
      if (str1 == null)
        return null;
      String str2 = "" + str1;
      String str3 = str2 + "\"";
      String str4 = str3 + paramString1;
      String str5 = str4 + "\"";
      String str6 = str5 + ",";
      String str7 = str6 + "\"";
      String str8 = str7 + paramString2;
      String str9 = str8 + "\"";
      String str10 = str9 + "\n";
      return str10;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public static QTLogger getInstance()
  {
    if (instance == null)
      instance = new QTLogger();
    return instance;
  }

  public static String getNetworkInfo(Context paramContext)
  {
    int i = MobileState.getNetWorkType(paramContext);
    if (i == 2)
      return "0";
    if (i == 1)
      return "1";
    if (i == 3)
      return "2";
    if (i == 5)
      return "3";
    return "-1";
  }

  private void log(String paramString)
  {
  }

  public static String msToDate(long paramLong)
  {
    Date localDate = new Date(paramLong);
    return new SimpleDateFormat("yyyy-MM-dd").format(localDate);
  }

  public void addDownloadExceptionLog(String paramString)
  {
    EventDispacthManager.getInstance().dispatchAction("showError", paramString);
    this.downloadException = paramString;
  }

  public String buildBillboardShowLog(BillboardItemNode paramBillboardItemNode, int paramInt1, int paramInt2)
  {
    if ((paramBillboardItemNode != null) && (paramInt2 < 0));
    return null;
  }

  public String buildCommonLog()
  {
    return buildCommonLog(null, null, null);
  }

  public String buildCommonLog(String paramString1, String paramString2, String paramString3)
  {
    try
    {
      String str1 = "" + "\"";
      String str2 = str1 + DateUtil.getCurrentSeconds();
      String str3 = str2 + "\"";
      String str4 = str3 + ",";
      String str5 = str4 + "\"";
      String str6 = str5 + "+8";
      String str7 = str6 + "\"";
      String str8 = str7 + ",";
      String str9 = str8 + "\"";
      String str10 = str9 + "Android";
      String str11 = str10 + "\"";
      String str12 = str11 + ",";
      String str13 = str12 + "\"";
      String str14 = str13 + DeviceInfo.getUniqueId(this._context);
      String str15 = str14 + "\"";
      String str16 = str15 + ",";
      String str17 = str16 + "\"";
      String str18 = str17 + OperatorInfo.OperatorToStr(OperatorInfo.getOperator(this._context));
      String str19 = str18 + "\"";
      String str20 = str19 + ",";
      String str21;
      String str22;
      label657: String str31;
      String str32;
      label928: String str39;
      String str40;
      label1163: String str53;
      if (this._context != null)
      {
        String str72 = str20 + "\"";
        String str73 = str72 + String.valueOf(AppInfo.getCurrentInternalVersion(this._context));
        String str74 = str73 + "\"";
        str21 = str74 + ",";
        if (this._context == null)
          break label2006;
        String str69 = str21 + "\"";
        String str70 = str69 + AppInfo.getChannelName(this._context);
        String str71 = str70 + "\"";
        str22 = str71 + ",";
        String str23 = str22 + "\"";
        String str24 = str23 + DeviceInfo.getDeviceName().replace(",", " ");
        String str25 = str24 + "\"";
        String str26 = str25 + ",";
        String str27 = str26 + "\"";
        String str28 = str27 + DeviceInfo.getAndroidOsVersion();
        String str29 = str28 + "\"";
        String str30 = str29 + ",";
        str31 = str30 + "\"";
        if (this.mGetuiClientID == null)
          this.mGetuiClientID = GlobalCfg.getInstance(this._context).getGeTuiClientID();
        if ((this.mGetuiClientID == null) || (this.mGetuiClientID.equalsIgnoreCase("")))
          break label2031;
        str32 = str31 + this.mGetuiClientID;
        String str33 = str32 + "\"";
        String str34 = str33 + ",";
        String str35 = str34 + "\"";
        String str36 = str35 + getNetworkInfo(this._context);
        String str37 = str36 + "\"";
        String str38 = str37 + ",";
        str39 = str38 + "\"";
        if (this.mLocalIp == null)
          this.mLocalIp = GlobalCfg.getInstance(this._context).getLocalIp();
        if ((this.mLocalIp == null) || (this.mLocalIp.equalsIgnoreCase("")) || (this.mLocalIp.equalsIgnoreCase("\n")))
          break label2056;
        str40 = str39 + this.mLocalIp;
        String str41 = str40 + "\"";
        String str42 = str41 + ",";
        String str43 = str42 + "\"";
        String str44 = str43 + "China";
        String str45 = str44 + "\"";
        String str46 = str45 + ",";
        String str47 = str46 + "\"";
        if (this.region != null)
          str47 = str47 + this.region;
        String str48 = str47 + "\"";
        String str49 = str48 + ",";
        String str50 = str49 + "\"";
        if (this.city != null)
          str50 = str50 + this.city;
        String str51 = str50 + "\"";
        String str52 = str51 + ",";
        str53 = str52 + "\"";
        if (this.fmAvailable == null)
          break label2081;
      }
      label2006: label2031: String str54;
      for (Object localObject = str53 + this.fmAvailable; ; localObject = str54)
      {
        String str55 = (String)localObject + "\"";
        String str56 = str55 + ",";
        String str57 = str56 + "\"";
        if ((paramString1 != null) && (paramString1.trim().length() > 0))
          str57 = str57 + paramString1;
        String str58 = str57 + "\"";
        String str59 = str58 + ",";
        String str60 = str59 + "\"";
        if ((paramString2 != null) && (paramString2.trim().length() > 0))
          str60 = str60 + paramString2;
        String str61 = str60 + "\"";
        String str62 = str61 + ",";
        String str63 = str62 + "\"";
        if ((paramString3 != null) && (paramString3.trim().length() > 0))
          str63 = str63 + paramString3;
        String str64 = str63 + "\"";
        String str65 = str64 + ",";
        String str66 = str65 + "\"";
        String str67 = str66 + this._context.getPackageName();
        String str68 = str67 + "\"";
        return str68 + ",";
        str21 = str20 + "\"\",";
        break;
        str22 = str21 + "\"\",";
        break label657;
        str32 = str31 + "";
        break label928;
        label2056: str40 = str39 + "";
        break label1163;
        label2081: str54 = str53 + "";
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public String buildDeviceIdLog(String paramString)
  {
    if (paramString == null);
    String str1;
    do
    {
      return null;
      str1 = buildCommonLog();
    }
    while (str1 == null);
    try
    {
      String str2 = str1 + "\"";
      String str3 = str2 + paramString;
      String str4 = str3 + "\"";
      String str5 = str4 + "\n";
      return str5;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public String buildDownloadLog(Node paramNode, boolean paramBoolean)
  {
    if ((paramNode != null) && (!paramNode.nodeName.equalsIgnoreCase("program")));
    return null;
  }

  public String buildEnterIMLog(int paramInt)
  {
    String str1 = buildCommonLog();
    if (str1 != null)
      try
      {
        String str2 = str1 + "\"";
        String str3 = str2 + String.valueOf(paramInt);
        String str4 = str3 + "\"";
        String str5 = str4 + "\n";
        return str5;
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  public String buildIMGroupRelationLog(String paramString1, String paramString2, int paramInt)
  {
    if ((paramString1 == null) || (paramString2 == null));
    String str1;
    do
    {
      return null;
      str1 = buildCommonLog();
    }
    while (str1 == null);
    try
    {
      String str2 = str1 + "\"";
      String str3 = str2 + paramString1;
      String str4 = str3 + "\"";
      String str5 = str4 + ",";
      String str6 = str5 + "\"";
      String str7 = str6 + paramString2;
      String str8 = str7 + "\"";
      String str9 = str8 + ",";
      String str10 = str9 + "\"";
      String str11 = str10 + String.valueOf(paramInt);
      String str12 = str11 + "\"";
      String str13 = str12 + "\n";
      return str13;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public String buildIMSendGroupLog(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString2 == null));
    String str1;
    do
    {
      return null;
      str1 = buildCommonLog();
    }
    while (str1 == null);
    try
    {
      String str2 = str1 + "\"";
      String str3 = str2 + paramString1;
      String str4 = str3 + "\"";
      String str5 = str4 + ",";
      String str6 = str5 + "\"";
      String str7 = str6 + paramString2;
      String str8 = str7 + "\"";
      String str9 = str8 + "\n";
      return str9;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public String buildIMSendUserLog(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString2 == null));
    String str1;
    do
    {
      return null;
      str1 = buildCommonLog();
    }
    while (str1 == null);
    try
    {
      String str2 = str1 + "\"";
      String str3 = str2 + paramString1;
      String str4 = str3 + "\"";
      String str5 = str4 + ",";
      String str6 = str5 + "\"";
      String str7 = str6 + paramString2;
      String str8 = str7 + "\"";
      String str9 = str8 + "\n";
      return str9;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public String buildIMUserRelationLog(String paramString1, String paramString2, int paramInt)
  {
    if ((paramString1 == null) || (paramString2 == null));
    String str1;
    do
    {
      return null;
      str1 = buildCommonLog();
    }
    while (str1 == null);
    try
    {
      String str2 = str1 + "\"";
      String str3 = str2 + paramString1;
      String str4 = str3 + "\"";
      String str5 = str4 + ",";
      String str6 = str5 + "\"";
      String str7 = str6 + paramString2;
      String str8 = str7 + "\"";
      String str9 = str8 + ",";
      String str10 = str9 + "\"";
      String str11 = str10 + String.valueOf(paramInt);
      String str12 = str11 + "\"";
      String str13 = str12 + "\n";
      return str13;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public String buildListeneringQualityLog(Node paramNode, double paramDouble, int paramInt, String paramString)
  {
    if ((paramNode == null) || (!paramNode.nodeName.equalsIgnoreCase("program")) || (paramString == null));
    while (true)
    {
      return null;
      try
      {
        ProgramNode localProgramNode = (ProgramNode)paramNode;
        if (!localProgramNode.isDownloadProgram())
        {
          String str1 = buildCommonLog();
          if ((str1 != null) && (localProgramNode.resId != 0))
          {
            String str2 = str1 + "\"";
            String str3 = str2 + localProgramNode.resId;
            String str4 = str3 + "\"";
            String str5 = str4 + ",";
            String str6 = str5 + "\"";
            String str7 = str6 + localProgramNode.id;
            String str8 = str7 + "\"";
            String str9 = str8 + ",";
            String str10 = str9 + "\"";
            if ((localProgramNode.channelType == 0) && (localProgramNode.getCurrPlayStatus() == 3));
            String str11;
            for (Object localObject = str10 + "2"; ; localObject = str11)
            {
              String str12 = (String)localObject + "\"";
              String str13 = str12 + ",";
              String str14 = str13 + "\"";
              String str15 = str14 + String.valueOf(paramInt);
              String str16 = str15 + "\"";
              String str17 = str16 + ",";
              String str18 = str17 + "\"";
              String str19 = str18 + String.valueOf(paramDouble);
              String str20 = str19 + "\"";
              String str21 = str20 + ",";
              String str22 = str21 + "\"";
              String str23 = str22 + String.valueOf(paramString);
              String str24 = str23 + "\"";
              return str24 + "\n";
              str11 = str10 + String.valueOf(localProgramNode.channelType);
            }
          }
        }
      }
      catch (Exception localException)
      {
      }
    }
    return null;
  }

  public String buildProgramsShowLog(List<ProgramNode> paramList, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    if (paramList == null)
      return null;
    if (paramList.size() > 0);
    while (true)
    {
      try
      {
        String str1 = "" + "\"";
        String str2 = str1 + DeviceInfo.getUniqueId(this._context);
        String str3 = str2 + "\"";
        String str4 = str3 + ",";
        String str5 = str4 + "\"";
        if (paramBoolean)
        {
          str6 = str5 + "1";
          String str7 = str6 + "\"";
          String str8 = str7 + ",";
          String str9 = str8 + "\"";
          String str10 = str9 + String.valueOf(paramInt4);
          String str11 = str10 + "\"";
          String str12 = str11 + ",";
          String str13 = str12 + "\"";
          String str14 = str13 + paramInt1;
          String str15 = str14 + "\"";
          String str16 = str15 + ",";
          String str17 = str16 + "\"";
          String str18 = str17 + paramInt2;
          String str19 = str18 + "\"";
          String str20 = str19 + ",";
          String str21 = str20 + "\"";
          String str22 = str21 + paramInt3;
          String str23 = str22 + "\"";
          String str24 = str23 + ",";
          if (!paramBoolean)
            break label781;
          localObject = "";
          String str27 = str24 + "\"";
          String str28 = str27 + (String)localObject;
          String str29 = str28 + "\"";
          return str29 + "\n";
        }
        String str6 = str5 + "0";
        continue;
        if (i >= paramList.size())
          continue;
        if (localObject == null)
        {
          localObject = String.valueOf(((ProgramNode)paramList.get(i)).id);
        }
        else
        {
          String str25 = (String)localObject + "_";
          String str26 = str25 + ((ProgramNode)paramList.get(i)).id;
          localObject = str26;
        }
      }
      catch (Exception localException)
      {
      }
      return null;
      label781: Object localObject = null;
      int i = 0;
      continue;
      i++;
    }
  }

  public String buildPublishLog(ShareBean paramShareBean)
  {
    if (paramShareBean == null);
    while (true)
    {
      return null;
      try
      {
        String str1 = buildCommonLog();
        if (str1 != null)
        {
          String str2 = str1 + "\"";
          String str3 = str2 + paramShareBean.platform;
          String str4 = str3 + "\"";
          String str5 = str4 + ",";
          String str6 = str5 + "\"";
          String str7 = str6 + String.valueOf(paramShareBean.channelType);
          String str8 = str7 + "\"";
          String str9 = str8 + ",";
          String str10 = str9 + "\"";
          String str11 = str10 + paramShareBean.categoryId;
          String str12 = str11 + "\"";
          String str13 = str12 + ",";
          String str14 = str13 + "\"";
          String str15 = str14 + paramShareBean.channelId;
          String str16 = str15 + "\"";
          String str17 = str16 + ",";
          String str18 = str17 + "\"";
          String str19;
          String str22;
          String str23;
          label523: String str26;
          if (paramShareBean.programId == 0)
          {
            str19 = str18 + "";
            String str20 = str19 + "\"";
            String str21 = str20 + ",";
            str22 = str21 + "\"";
            if (paramShareBean.snsId != null)
              break label781;
            str23 = str22 + "";
            String str24 = str23 + "\"";
            String str25 = str24 + ",";
            str26 = str25 + "\"";
            if (paramShareBean.broadcasterName != null)
              break label808;
          }
          label781: label808: String str27;
          for (Object localObject = str26 + ""; ; localObject = str27)
          {
            String str28 = (String)localObject + "\"";
            String str29 = str28 + ",";
            String str30 = str29 + "\"";
            String str31 = str30 + String.valueOf(paramShareBean.time);
            String str32 = str31 + "\"";
            return str32 + "\n";
            str19 = str18 + paramShareBean.programId;
            break;
            str23 = str22 + paramShareBean.snsId;
            break label523;
            str27 = str26 + paramShareBean.broadcasterName;
          }
        }
      }
      catch (Exception localException)
      {
      }
    }
    return null;
  }

  public String buildRecommendShowLog(RecommendItemNode paramRecommendItemNode, int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    return null;
  }

  public String buildResourceUnavailLog(Node paramNode)
  {
    if ((paramNode == null) || (!paramNode.nodeName.equalsIgnoreCase("program")))
      return null;
    while (true)
    {
      try
      {
        ProgramNode localProgramNode = (ProgramNode)paramNode;
        String str1 = buildCommonLog();
        if ((str1 == null) || (localProgramNode.resId == 0))
          break;
        String str2 = str1 + "\"";
        String str3 = str2 + localProgramNode.resId;
        String str4 = str3 + "\"";
        String str5 = str4 + ",";
        String str6 = str5 + "\"";
        String str7 = str6 + localProgramNode.id;
        String str8 = str7 + "\"";
        String str9 = str8 + ",";
        String str10 = str9 + "\"";
        if (localProgramNode.channelType == 0)
        {
          i = localProgramNode.getCurrPlayStatus();
          String str11;
          String str14;
          if ((localProgramNode.channelType == 0) && (i == 3))
          {
            str11 = str10 + "2";
            String str12 = str11 + "\"";
            String str13 = str12 + ",";
            str14 = str13 + "\"";
            if ((localProgramNode.channelType == 0) && (i == 3))
            {
              localObject = str14 + localProgramNode.getSourceUrl();
              String str15 = (String)localObject + "\"";
              return str15 + "\n";
            }
          }
          else
          {
            str11 = str10 + localProgramNode.channelType;
            continue;
          }
          String str16 = str14 + "";
          Object localObject = str16;
          continue;
        }
      }
      catch (Exception localException)
      {
        return null;
      }
      int i = 0;
    }
  }

  public String buildSearchKeywordLogString(String paramString, List<SearchItemNode> paramList)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")) || (paramList == null) || (paramList.size() == 0))
      return null;
    while (true)
    {
      int i;
      try
      {
        String str1 = buildCommonLog();
        if (str1 == null)
          return null;
        String str2 = str1 + "\"";
        String str3 = str2 + paramString;
        String str4 = str3 + "\"";
        String str5 = str4 + ",";
        localObject1 = "";
        i = 0;
        String str9;
        String str10;
        String str11;
        String str12;
        if ((i < paramList.size()) && (i < 10))
        {
          if (((SearchItemNode)paramList.get(i)).groupType == 0)
          {
            String str8 = (String)localObject1 + "2_";
            localObject2 = str8 + ((SearchItemNode)paramList.get(i)).channelId;
            if ((i >= -1 + paramList.size()) || (i >= 9))
              break label530;
            localObject2 = (String)localObject2 + ":";
            break label530;
          }
          if (((SearchItemNode)paramList.get(i)).groupType == 2)
          {
            String str7 = (String)localObject1 + "3_";
            localObject2 = str7 + ((SearchItemNode)paramList.get(i)).channelId;
            continue;
          }
          if (((SearchItemNode)paramList.get(i)).groupType == 1)
          {
            String str6 = (String)localObject1 + "4_";
            localObject2 = str6 + ((SearchItemNode)paramList.get(i)).programId;
            continue;
          }
        }
        else
        {
          str9 = str5 + "\"";
          str10 = str9 + (String)localObject1;
          str11 = str10 + "\"";
          str12 = str11 + "\n";
          return str12;
        }
      }
      catch (Exception localException)
      {
        return null;
      }
      Object localObject2 = localObject1;
      continue;
      label530: i++;
      Object localObject1 = localObject2;
    }
  }

  public String buildSearchedClickLog(SearchItemNode paramSearchItemNode)
  {
    String str1;
    if (paramSearchItemNode == null)
      str1 = null;
    while (true)
    {
      return str1;
      str1 = "";
      try
      {
        int i = InfoManager.getInstance().root().mSearchNode.getSearchIndex(paramSearchItemNode);
        if (i >= 0)
        {
          String str2 = InfoManager.getInstance().root().mSearchNode.getLastKeyword();
          if ((str2 != null) && (!str2.equalsIgnoreCase("")))
          {
            String str3 = buildCommonLog();
            if (str3 == null)
              return null;
            String str4 = str1 + str3;
            String str5 = str4 + "\"";
            String str6 = str5 + str2;
            String str7 = str6 + "\"";
            String str8 = str7 + ",";
            String str9 = str8 + "\"";
            String str10 = str9 + InfoManager.getInstance().root().mSearchNode.mSearchPageType;
            String str11 = str10 + "\"";
            String str12 = str11 + ",";
            String str13 = str12 + "\"";
            String str14 = str13 + i;
            String str15 = str14 + "\"";
            String str16 = str15 + ",";
            String str17 = str16 + "\"";
            String str18 = str17 + paramSearchItemNode.channelId;
            String str19 = str18 + "\"";
            String str20 = str19 + ",";
            String str21 = str20 + "\"";
            String str22 = str21 + paramSearchItemNode.programId;
            String str23 = str22 + "\"";
            String str24 = str23 + "\n";
            return str24;
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    return null;
  }

  public String buildSpecialTopicClickLog(int paramInt1, int paramInt2)
  {
    if ((this.mSpecialTopicId == 0) || (this.mSpecialTopicName == null));
    while (true)
    {
      return null;
      try
      {
        String str1 = buildCommonLog();
        if (str1 != null)
        {
          String str2 = str1 + "\"";
          String str3 = str2 + this.mSpecialTopicId;
          String str4 = str3 + "\"";
          String str5 = str4 + ",";
          String str6 = str5 + "\"";
          String str7 = str6 + this.mSpecialTopicName;
          String str8 = str7 + "\"";
          String str9 = str8 + ",";
          String str10 = str9 + "\"";
          String str11 = str10 + paramInt1;
          String str12 = str11 + "\"";
          String str13 = str12 + ",";
          String str14 = str13 + "\"";
          String str15 = str14 + paramInt2;
          String str16 = str15 + "\"";
          String str17 = str16 + "\n";
          return str17;
        }
      }
      catch (Exception localException)
      {
      }
    }
    return null;
  }

  public String buildSpecialTopicLog(SpecialTopicNode paramSpecialTopicNode)
  {
    if (paramSpecialTopicNode == null);
    while (true)
    {
      return null;
      try
      {
        String str1 = buildCommonLog();
        if (str1 != null)
        {
          String str2 = str1 + "\"";
          String str3 = str2 + paramSpecialTopicNode.getApiId();
          String str4 = str3 + "\"";
          String str5 = str4 + ",";
          String str6 = str5 + "\"";
          String str7 = str6 + paramSpecialTopicNode.title;
          String str8 = str7 + "\"";
          String str9 = str8 + "\n";
          this.mSpecialTopicName = paramSpecialTopicNode.title;
          this.mSpecialTopicId = paramSpecialTopicNode.getApiId();
          return str9;
        }
      }
      catch (Exception localException)
      {
      }
    }
    return null;
  }

  public String buildSpeedTest(String paramString, List<String> paramList, List<Long> paramList1)
  {
    if ((paramString == null) || (paramList == null))
      return null;
    try
    {
      String str1 = buildCommonLog();
      if (str1 != null)
      {
        String str2 = str1 + "\"";
        String str3 = str2 + paramString;
        String str4 = str3 + "\"";
        String str5 = str4 + ",";
        Object localObject = "";
        int i = 0;
        while (i < paramList.size())
        {
          String str16 = (String)localObject + (String)paramList.get(i);
          String str17 = str16 + ";";
          i++;
          localObject = str17;
        }
        String str6 = str5 + "\"";
        String str7 = str6 + (String)localObject;
        String str8 = str7 + "\"";
        String str9 = "";
        for (int j = 0; j < paramList1.size(); j++)
        {
          String str15 = str9 + String.valueOf(paramList1.get(j));
          str9 = str15 + ";";
        }
        String str10 = str8 + ",";
        String str11 = str10 + "\"";
        String str12 = str11 + str9;
        String str13 = str12 + "\"";
        String str14 = str13 + "\n";
        return str14;
      }
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public String getDownloadException()
  {
    return this.downloadException;
  }

  public String getGeTuiCID()
  {
    if (this.mGetuiClientID == null)
      this.mGetuiClientID = GlobalCfg.getInstance(this._context).getGeTuiClientID();
    return this.mGetuiClientID;
  }

  public void log(String paramString1, String paramString2)
  {
  }

  public void sendCollectionLog(Node paramNode)
  {
    if (paramNode == null);
    while (true)
    {
      return;
      try
      {
        String str1 = buildCommonLog();
        if ((str1 != null) && (paramNode != null) && (paramNode.nodeName.equalsIgnoreCase("channel")))
        {
          String str2 = str1 + "\"";
          String str3 = str2 + ((ChannelNode)paramNode).channelId;
          String str4 = str3 + "\"";
          String str5 = str4 + ",";
          String str6 = str5 + "\"";
          String str7 = str6 + ((ChannelNode)paramNode).title;
          String str8 = str7 + "\"";
          String str9 = str8 + ",";
          String str10 = str9 + "\"";
          String str11 = str10 + ((ChannelNode)paramNode).categoryId;
          String str12 = str11 + "\"";
          String str13 = str12 + ",";
          String str14 = str13 + "\"";
          String str15 = str14 + "";
          String str16 = str15 + "\"";
          DisruptorHelper.produce("favoriteChannels", str16 + "\n");
          return;
        }
      }
      catch (Exception localException)
      {
      }
    }
  }

  public void sendOther(String paramString1, String paramString2)
  {
    if ((paramString1 != null) && (paramString2 != null) && (!paramString1.equalsIgnoreCase("")) && (!paramString2.equalsIgnoreCase("")))
    {
      String str = buildOtherString(paramString1, paramString2);
      if (str != null)
        DisruptorHelper.produce("other", str);
    }
  }

  public void sendResourceUnavailLog(String paramString)
  {
    if (paramString != null)
      DisruptorHelper.produce("ResourceUnavailable", paramString);
  }

  public void sendSearchLog()
  {
  }

  public void sendSearchResultLog()
  {
  }

  public void setCity(String paramString)
  {
    this.city = paramString;
  }

  public void setContext(Context paramContext)
  {
    this._context = paramContext;
  }

  public void setFMAvailable(String paramString)
  {
    this.fmAvailable = paramString;
  }

  public void setGeTuiCID(String paramString)
  {
    if (paramString != null)
      this.mGetuiClientID = paramString;
  }

  public void setLocalIp(String paramString)
  {
    this.mLocalIp = paramString;
  }

  public void setRegion(String paramString)
  {
    this.region = paramString;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.logger.QTLogger
 * JD-Core Version:    0.6.2
 */