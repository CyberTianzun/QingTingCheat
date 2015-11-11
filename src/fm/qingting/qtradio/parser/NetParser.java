package fm.qingting.qtradio.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import fm.qingting.framework.data.DataParserImpl;
import fm.qingting.framework.data.ListData;
import fm.qingting.framework.data.Navigation;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.abtest.ABTest;
import fm.qingting.qtradio.ad.AdConfig;
import fm.qingting.qtradio.ad.AdPos;
import fm.qingting.qtradio.im.info.GroupInfo;
import fm.qingting.qtradio.im.message.IMMessage;
import fm.qingting.qtradio.jd.data.CommodityInfo;
import fm.qingting.qtradio.model.ActivityNode;
import fm.qingting.qtradio.model.AdvertisementItemNode;
import fm.qingting.qtradio.model.AdvertisementItemNode.AdTrackers;
import fm.qingting.qtradio.model.Attribute;
import fm.qingting.qtradio.model.Attributes;
import fm.qingting.qtradio.model.BillboardItemNode;
import fm.qingting.qtradio.model.BroadcasterNode;
import fm.qingting.qtradio.model.CategoryNode;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.CheckInNode;
import fm.qingting.qtradio.model.DataCenterInfo;
import fm.qingting.qtradio.model.GameBean;
import fm.qingting.qtradio.model.H5Bean;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.MediaCenter;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.PingInfo;
import fm.qingting.qtradio.model.PingInfoV6;
import fm.qingting.qtradio.model.ProgramABTestBean;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.ProgramSchedule;
import fm.qingting.qtradio.model.ProgramScheduleList;
import fm.qingting.qtradio.model.ProgramTopicNode;
import fm.qingting.qtradio.model.QTADLocation;
import fm.qingting.qtradio.model.QTLocation;
import fm.qingting.qtradio.model.RadioChannelNode;
import fm.qingting.qtradio.model.RecommendCategoryNode;
import fm.qingting.qtradio.model.RecommendConfigureNode;
import fm.qingting.qtradio.model.RecommendItemNode;
import fm.qingting.qtradio.model.RecommendPlayingItemNode;
import fm.qingting.qtradio.model.SearchChannel;
import fm.qingting.qtradio.model.SearchOndemand;
import fm.qingting.qtradio.model.SpecialTopicNode;
import fm.qingting.qtradio.model.UpgradeInfo;
import fm.qingting.qtradio.model.advertisement.QTAdvertisementInfo;
import fm.qingting.qtradio.model.advertisement.QTCoupon;
import fm.qingting.qtradio.room.AdminInfo;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.search.SearchHotKeyword;
import fm.qingting.qtradio.search.SearchItemNode;
import fm.qingting.qtradio.search.SearchNode;
import fm.qingting.qtradio.social.MiniFavNode;
import fm.qingting.qtradio.social.MiniPlayNode;
import fm.qingting.qtradio.social.UserProfile;
import fm.qingting.utils.TimeUtil;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetParser extends DataParserImpl
{
  private ActivityNode _parseActivity(JSONObject paramJSONObject)
  {
    if (paramJSONObject != null)
      try
      {
        ActivityNode localActivityNode = new ActivityNode();
        localActivityNode.id = paramJSONObject.getIntValue("id");
        localActivityNode.name = paramJSONObject.getString("title");
        localActivityNode.type = paramJSONObject.getString("type");
        localActivityNode.contentUrl = paramJSONObject.getString("url");
        localActivityNode.infoUrl = null;
        localActivityNode.infoTitle = paramJSONObject.getString("description");
        return localActivityNode;
      }
      catch (Exception localException)
      {
        return null;
      }
    return null;
  }

  private AdminInfo _parseAdminInfo(JSONObject paramJSONObject)
  {
    AdminInfo localAdminInfo;
    if (paramJSONObject != null)
      try
      {
        localAdminInfo = new AdminInfo();
        localAdminInfo.userKey = paramJSONObject.getString("userId");
        localAdminInfo.snsInfo.sns_avatar = paramJSONObject.getString("avatar");
        localAdminInfo.snsInfo.sns_name = paramJSONObject.getString("userName");
        localAdminInfo.snsInfo.signature = paramJSONObject.getString("signature");
        localAdminInfo.snsInfo.age = paramJSONObject.getIntValue("age");
        localAdminInfo.snsInfo.sns_gender = paramJSONObject.getString("gender");
        String str1 = paramJSONObject.getString("is_blocked");
        localAdminInfo.weiboName = paramJSONObject.getString("weiboName");
        if ((str1 != null) && (!str1.equalsIgnoreCase("")))
          if (Integer.valueOf(str1).intValue() != 0)
            break label173;
        label173: for (localAdminInfo.isBlocked = false; ; localAdminInfo.isBlocked = true)
        {
          String str2 = paramJSONObject.getString("level");
          if ((str2 == null) || (str2.equalsIgnoreCase("")))
            break;
          localAdminInfo.level = Integer.valueOf(str2).intValue();
          return localAdminInfo;
        }
      }
      catch (Exception localException)
      {
      }
    else
      localAdminInfo = null;
    return localAdminInfo;
  }

  private BillboardItemNode _parseBillboardNode(JSONObject paramJSONObject)
  {
    return null;
  }

  private DataCenterInfo _parseCenter(JSONObject paramJSONObject, String paramString)
  {
    if ((paramJSONObject == null) || (paramString == null))
    {
      localDataCenterInfo = null;
      return localDataCenterInfo;
    }
    DataCenterInfo localDataCenterInfo = new DataCenterInfo();
    localDataCenterInfo.mapServiceInfo = new HashMap();
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    while (true)
    {
      int i;
      try
      {
        JSONArray localJSONArray = paramJSONObject.getJSONArray(paramString);
        i = 0;
        if (i < localJSONArray.size())
        {
          JSONObject localJSONObject = localJSONArray.getJSONObject(i);
          PingInfo localPingInfo1 = _parseDataCenterService(localJSONObject, "hls");
          if (localPingInfo1 != null)
            localArrayList1.add(localPingInfo1);
          PingInfo localPingInfo2 = _parseDataCenterService(localJSONObject, "download");
          if (localPingInfo2 != null)
            localArrayList2.add(localPingInfo2);
        }
        else
        {
          if (localArrayList1.size() > 0)
            localDataCenterInfo.mapServiceInfo.put("hls", localArrayList1);
          if (localArrayList2.size() <= 0)
            break;
          localDataCenterInfo.mapServiceInfo.put("download", localArrayList2);
          return localDataCenterInfo;
        }
      }
      catch (Exception localException)
      {
        return localDataCenterInfo;
      }
      i++;
    }
  }

  private ChannelNode _parseChannelNode(JSONObject paramJSONObject)
  {
    ChannelNode localChannelNode;
    if (paramJSONObject != null)
      try
      {
        localChannelNode = new ChannelNode();
        if (paramJSONObject.containsKey("channel_star"))
        {
          localChannelNode.ratingStar = paramJSONObject.getIntValue("channel_star");
          localChannelNode.channelId = paramJSONObject.getIntValue("id");
          localChannelNode.title = paramJSONObject.getString("title");
          localChannelNode.desc = paramJSONObject.getString("description");
          localChannelNode.groupId = paramJSONObject.getIntValue("chatgroup_id");
          localChannelNode.categoryId = paramJSONObject.getIntValue("category_id");
          localChannelNode.update_time = paramJSONObject.getString("update_time");
          JSONObject localJSONObject1 = paramJSONObject.getJSONObject("thumbs");
          if (localJSONObject1 != null)
          {
            localChannelNode.setSmallThumb(localJSONObject1.getString("200_thumb"));
            localChannelNode.setMediumThumb(localJSONObject1.getString("400_thumb"));
            localChannelNode.setLargeThumb(localJSONObject1.getString("800_thumb"));
            if (localChannelNode.noThumb())
            {
              localChannelNode.setSmallThumb(localJSONObject1.getString("small_thumb"));
              localChannelNode.setMediumThumb(localJSONObject1.getString("medium_thumb"));
              localChannelNode.setLargeThumb(localJSONObject1.getString("large_thumb"));
            }
          }
          String str = paramJSONObject.getString("type");
          if ((str == null) || (!str.equalsIgnoreCase("channel_ondemand")))
            break label403;
          localChannelNode.channelType = 1;
          label205: if (paramJSONObject.getIntValue("auto_play") != 0)
            break label411;
          localChannelNode.autoPlay = false;
          label219: if (paramJSONObject.getIntValue("record_enabled") != 0)
            break label419;
          localChannelNode.recordEnable = false;
          label233: if (!localChannelNode.isLiveChannel())
            break label427;
          localChannelNode.audienceCnt = paramJSONObject.getIntValue("audience_count");
          JSONObject localJSONObject3 = paramJSONObject.getJSONObject("mediainfo");
          if (localJSONObject3 != null)
            localChannelNode.resId = localJSONObject3.getIntValue("id");
        }
        JSONObject localJSONObject2;
        while (true)
        {
          localJSONObject2 = paramJSONObject.getJSONObject("detail");
          if (localJSONObject2 == null)
            break label603;
          localChannelNode.programCnt = localJSONObject2.getIntValue("program_count");
          JSONArray localJSONArray1 = localJSONObject2.getJSONArray("authors");
          if (localJSONArray1 == null)
            break label441;
          for (int k = 0; k < localJSONArray1.size(); k++)
          {
            BroadcasterNode localBroadcasterNode2 = parseBroadcasterNode(localJSONArray1.getJSONObject(k));
            if (localChannelNode.lstAuthors == null)
              localChannelNode.lstAuthors = new ArrayList();
            localChannelNode.lstAuthors.add(localBroadcasterNode2);
          }
          if (!paramJSONObject.containsKey("star"))
            break;
          localChannelNode.ratingStar = paramJSONObject.getIntValue("star");
          break;
          label403: localChannelNode.channelType = 0;
          break label205;
          label411: localChannelNode.autoPlay = true;
          break label219;
          label419: localChannelNode.recordEnable = true;
          break label233;
          label427: localChannelNode.latest_program = paramJSONObject.getString("latest_program");
        }
        label441: JSONArray localJSONArray2 = localJSONObject2.getJSONArray("broadcasters");
        if (localJSONArray2 != null)
          for (int j = 0; j < localJSONArray2.size(); j++)
          {
            BroadcasterNode localBroadcasterNode1 = parseBroadcasterNode(localJSONArray2.getJSONObject(j));
            if (localChannelNode.lstBroadcaster == null)
              localChannelNode.lstBroadcaster = new ArrayList();
            localChannelNode.lstBroadcaster.add(localBroadcasterNode1);
          }
        JSONArray localJSONArray3 = localJSONObject2.getJSONArray("podcasters");
        int i = 0;
        if (localJSONArray3 == null)
          break label603;
        while (i < localJSONArray3.size())
        {
          UserInfo localUserInfo = _parsePodcaster(localJSONArray3.getJSONObject(i));
          if (localChannelNode.lstPodcasters == null)
            localChannelNode.lstPodcasters = new ArrayList();
          if (localUserInfo != null)
            localChannelNode.lstPodcasters.add(localUserInfo);
          i++;
        }
      }
      catch (Exception localException)
      {
      }
    else
      localChannelNode = null;
    label603: return localChannelNode;
  }

  private PingInfo _parseDataCenterService(JSONObject paramJSONObject, String paramString)
  {
    int i = 0;
    PingInfo localPingInfo1;
    if ((paramJSONObject == null) || (paramString == null))
      localPingInfo1 = null;
    while (true)
    {
      return localPingInfo1;
      try
      {
        if (paramString.equalsIgnoreCase("hls"))
        {
          JSONObject localJSONObject2 = paramJSONObject.getJSONObject(paramString);
          String str6 = localJSONObject2.getString("sdomain");
          String str7 = localJSONObject2.getString("mdomain");
          String str8 = localJSONObject2.getString("codename");
          String str9 = localJSONObject2.getString("res");
          String str10 = localJSONObject2.getString("testpath");
          localPingInfo1 = new PingInfo();
          localPingInfo1.sdomain = str6.trim();
          localPingInfo1.mdomain = str7.trim();
          localPingInfo1.codename = str8.trim();
          localPingInfo1.res = str9.trim();
          localPingInfo1.testpath = str10.trim();
          localPingInfo1.sLstBackupIPs = new ArrayList();
          localPingInfo1.mLstBackupIPs = new ArrayList();
          JSONArray localJSONArray2 = localJSONObject2.getJSONArray("sbackupips");
          for (int j = 0; j < localJSONArray2.size(); j++)
          {
            String str12 = localJSONArray2.getString(j).trim();
            localPingInfo1.sLstBackupIPs.add(str12);
          }
          JSONArray localJSONArray3 = localJSONObject2.getJSONArray("mbackupips");
          while (i < localJSONArray3.size())
          {
            String str11 = localJSONArray3.getString(i).trim();
            localPingInfo1.mLstBackupIPs.add(str11);
            i++;
          }
        }
        if (paramString.equalsIgnoreCase("download"))
        {
          JSONObject localJSONObject1 = paramJSONObject.getJSONObject(paramString);
          String str1 = localJSONObject1.getString("domain");
          String str2 = localJSONObject1.getString("testpath");
          String str3 = localJSONObject1.getString("res");
          String str4 = localJSONObject1.getString("codename");
          PingInfo localPingInfo2 = new PingInfo();
          localPingInfo2.sdomain = str1.trim();
          localPingInfo2.mdomain = localPingInfo2.sdomain;
          localPingInfo2.codename = str4.trim();
          localPingInfo2.res = str3.trim();
          localPingInfo2.testpath = str2.trim();
          localPingInfo2.sLstBackupIPs = new ArrayList();
          JSONArray localJSONArray1 = localJSONObject1.getJSONArray("backupips");
          while (i < localJSONArray1.size())
          {
            String str5 = localJSONArray1.getString(i).trim();
            localPingInfo2.sLstBackupIPs.add(str5);
            i++;
          }
          localPingInfo2.mLstBackupIPs = localPingInfo2.sLstBackupIPs;
          return localPingInfo2;
        }
      }
      catch (Exception localException)
      {
        return null;
      }
    }
    return null;
  }

  private ProgramNode _parseLiveProgramNode(JSONObject paramJSONObject, int paramInt)
  {
    int i = 0;
    if (paramJSONObject != null)
    {
      ProgramNode localProgramNode = new ProgramNode();
      localProgramNode.id = paramJSONObject.getIntValue("id");
      localProgramNode.startTime = paramJSONObject.getString("start_time");
      localProgramNode.endTime = paramJSONObject.getString("end_time");
      if ((paramInt != 0) || ((localProgramNode.endTime != null) && (localProgramNode.endTime.equalsIgnoreCase("00:00:00"))))
        localProgramNode.endTime = "23:59:00";
      localProgramNode.title = paramJSONObject.getString("title");
      localProgramNode.channelId = paramJSONObject.getIntValue("channel_id");
      localProgramNode.uniqueId = paramJSONObject.getIntValue("program_id");
      localProgramNode.groupId = paramJSONObject.getIntValue("chatgroup_id");
      localProgramNode.dayOfWeek = paramInt;
      localProgramNode.channelType = 0;
      JSONObject localJSONObject1 = paramJSONObject.getJSONObject("mediainfo");
      if (localJSONObject1 != null)
        localProgramNode.resId = localJSONObject1.getIntValue("id");
      ProgramABTestBean localProgramABTestBean = InfoManager.getInstance().getProgramABTest(localProgramNode.channelId, localProgramNode.uniqueId);
      if (localProgramABTestBean != null)
      {
        localProgramNode.resId = localProgramABTestBean.resId;
        localProgramNode.title = localProgramABTestBean.title;
      }
      JSONObject localJSONObject2 = paramJSONObject.getJSONObject("detail");
      if (localJSONObject2 != null)
      {
        JSONArray localJSONArray = localJSONObject2.getJSONArray("broadcasters");
        if (localJSONArray != null)
        {
          localProgramNode.lstBroadcaster = new ArrayList();
          while (i < localJSONArray.size())
          {
            BroadcasterNode localBroadcasterNode = new BroadcasterNode();
            JSONObject localJSONObject3 = localJSONArray.getJSONObject(i);
            localBroadcasterNode.id = localJSONObject3.getIntValue("id");
            localBroadcasterNode.nick = localJSONObject3.getString("username");
            localBroadcasterNode.avatar = localJSONObject3.getString("thumb");
            localBroadcasterNode.weiboId = localJSONObject3.getString("weibo_id");
            localBroadcasterNode.weiboName = localJSONObject3.getString("weibo_name");
            localBroadcasterNode.qqId = localJSONObject3.getString("qq_id");
            localBroadcasterNode.qqName = localJSONObject3.getString("qq_name");
            localProgramNode.lstBroadcaster.add(localBroadcasterNode);
            i++;
          }
        }
      }
      return localProgramNode;
    }
    return null;
  }

  private List<PingInfoV6> _parseMediaCenter(JSONObject paramJSONObject, int paramInt)
  {
    int i = 0;
    ArrayList localArrayList;
    if (paramJSONObject != null)
      try
      {
        JSONArray localJSONArray = paramJSONObject.getJSONArray("mediacenters");
        double d = paramJSONObject.getDoubleValue("preference_change_cost");
        if (localJSONArray != null)
        {
          localArrayList = new ArrayList();
          if (i >= localJSONArray.size())
            break label242;
          JSONObject localJSONObject = localJSONArray.getJSONObject(i);
          PingInfoV6 localPingInfoV6 = new PingInfoV6();
          localPingInfoV6.domain = localJSONObject.getString("domain");
          localPingInfoV6.backupIP = localJSONObject.getString("backup_ips");
          localPingInfoV6.weight = localJSONObject.getIntValue("weight");
          localPingInfoV6.testpath = localJSONObject.getString("test_path");
          localPingInfoV6.accessExp = localJSONObject.getString("access");
          localPingInfoV6.replayExp = localJSONObject.getString("replay");
          localPingInfoV6.res = localJSONObject.getString("result");
          localPingInfoV6.codename = localJSONObject.getString("codename");
          localPingInfoV6.channelType = paramInt;
          localPingInfoV6.pcc = d;
          String str = localJSONObject.getString("type");
          if ((str != null) && (str.equalsIgnoreCase("cdn")));
          for (localPingInfoV6.isCDN = true; ; localPingInfoV6.isCDN = false)
          {
            localArrayList.add(localPingInfoV6);
            i++;
            break;
          }
        }
      }
      catch (Exception localException)
      {
      }
    else
      localArrayList = null;
    label242: return localArrayList;
  }

  private List<RecommendItemNode> _parseNewRecommendV2Banner(JSONObject paramJSONObject, List<RecommendItemNode> paramList)
  {
    JSONArray localJSONArray;
    int i;
    if ((paramJSONObject != null) && (paramList != null))
    {
      localJSONArray = paramJSONObject.getJSONArray("data");
      i = 0;
    }
    while (i < localJSONArray.size())
    {
      RecommendItemNode localRecommendItemNode = _parseRecommendNode(localJSONArray.getJSONObject(i));
      if (localRecommendItemNode != null)
        paramList.add(localRecommendItemNode);
      i++;
      continue;
      paramList = null;
    }
    return paramList;
  }

  private List<RecommendItemNode> _parseNewRecommendV2Main(JSONObject paramJSONObject, String paramString1, String paramString2)
  {
    if (paramJSONObject != null);
    return null;
  }

  private UserInfo _parsePodcaster(JSONObject paramJSONObject)
  {
    if (paramJSONObject != null)
    {
      UserInfo localUserInfo = new UserInfo();
      localUserInfo.userId = paramJSONObject.getString("user_system_id");
      localUserInfo.userKey = localUserInfo.userId;
      localUserInfo.isBlocked = false;
      localUserInfo.isPodcaster = true;
      localUserInfo.podcasterId = paramJSONObject.getIntValue("id");
      localUserInfo.podcasterName = paramJSONObject.getString("nickname");
      localUserInfo.fansNumber = paramJSONObject.getLong("fan_num").longValue();
      localUserInfo.snsInfo.signature = paramJSONObject.getString("signature");
      localUserInfo.snsInfo.sns_id = paramJSONObject.getString("weibo_id");
      localUserInfo.snsInfo.sns_name = paramJSONObject.getString("weibo_name");
      localUserInfo.snsInfo.sns_avatar = paramJSONObject.getString("avatar");
      localUserInfo.snsInfo.desc = paramJSONObject.getString("description");
      int i = paramJSONObject.getIntValue("sex");
      if (i == 0)
        localUserInfo.snsInfo.sns_gender = "n";
      do
      {
        return localUserInfo;
        if (i == 1)
        {
          localUserInfo.snsInfo.sns_gender = "m";
          return localUserInfo;
        }
      }
      while (i != 2);
      localUserInfo.snsInfo.sns_gender = "f";
      return localUserInfo;
    }
    return null;
  }

  private ProgramNode _parseProgramTempNode(JSONObject paramJSONObject)
  {
    if (paramJSONObject != null)
      try
      {
        ProgramNode localProgramNode = new ProgramNode();
        localProgramNode.mLiveInVirtual = true;
        localProgramNode.dayOfWeek = 0;
        localProgramNode.id = paramJSONObject.getIntValue("id");
        localProgramNode.uniqueId = localProgramNode.id;
        localProgramNode.channelType = 0;
        localProgramNode.title = paramJSONObject.getString("title");
        localProgramNode.duration = paramJSONObject.getDoubleValue("duration");
        localProgramNode.sequence = paramJSONObject.getIntValue("sequence");
        localProgramNode.startTime = paramJSONObject.getString("start_time");
        long l1 = TimeUtil.dateToMS(localProgramNode.startTime);
        localProgramNode.startTime = TimeUtil.msToDate3(l1);
        localProgramNode.setAbsoluteStartTime(l1 / 1000L);
        localProgramNode.endTime = paramJSONObject.getString("end_time");
        long l2 = TimeUtil.dateToMS(localProgramNode.endTime);
        localProgramNode.endTime = TimeUtil.msToDate3(l2);
        localProgramNode.setAbsoluteEndTime(l2 / 1000L);
        localProgramNode.sequence = paramJSONObject.getIntValue("sequence");
        localProgramNode.groupId = paramJSONObject.getIntValue("chatgroup_id");
        JSONObject localJSONObject = paramJSONObject.getJSONObject("mediainfo");
        if (localJSONObject != null)
          localProgramNode.resId = localJSONObject.getIntValue("id");
        return localProgramNode;
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  private RecommendItemNode _parseRecommendItemNode(JSONObject paramJSONObject, boolean paramBoolean)
  {
    Object localObject = null;
    if (paramJSONObject != null);
    try
    {
      RecommendItemNode localRecommendItemNode = new RecommendItemNode();
      localRecommendItemNode.name = paramJSONObject.getString("title");
      JSONObject localJSONObject2;
      int i;
      if (!paramBoolean)
      {
        JSONObject localJSONObject1 = paramJSONObject.getJSONObject("thumbs");
        if (localJSONObject1 != null)
        {
          localRecommendItemNode.setSmallThumb(localJSONObject1.getString("200_thumb"));
          localRecommendItemNode.setMediumThumb(localJSONObject1.getString("400_thumb"));
          localRecommendItemNode.setLargeThumb(localJSONObject1.getString("800_thumb"));
        }
        if (localRecommendItemNode.noThumb())
          localRecommendItemNode.setSmallThumb(paramJSONObject.getString("thumb"));
        localRecommendItemNode.update_time = paramJSONObject.getString("update_time");
        localJSONObject2 = paramJSONObject.getJSONObject("detail");
        if (localJSONObject2 == null)
          break label715;
        if (!localJSONObject2.containsKey("channel_star"))
          break label345;
        localRecommendItemNode.ratingStar = localJSONObject2.getIntValue("channel_star");
        i = 1;
      }
      while (true)
      {
        label149: JSONObject localJSONObject3 = paramJSONObject.getJSONObject("parent_info");
        String str1;
        int j;
        int k;
        String str3;
        if (localJSONObject3 != null)
        {
          str1 = localJSONObject3.getString("parent_type");
          if (str1 != null)
            if ((str1.equalsIgnoreCase("channel")) || (str1.equalsIgnoreCase("channel_ondemand")))
            {
              j = localJSONObject3.getIntValue("parent_id");
              String str2 = localJSONObject3.getString("parent_name");
              k = 0;
              str3 = str2;
              label226: JSONObject localJSONObject4 = localJSONObject3.getJSONObject("parent_extra");
              if (localJSONObject4 != null)
              {
                k = localJSONObject4.getIntValue("category_id");
                localRecommendItemNode.mCategoryId = k;
              }
            }
        }
        while (true)
        {
          String str4 = localJSONObject2.getString("type");
          if (str4.equalsIgnoreCase("program_ondemand"))
          {
            ProgramNode localProgramNode2 = _parseVirtualProgramNode(localJSONObject2, 0);
            localProgramNode2.channelId = j;
            if (str3 != null)
              localProgramNode2.setChannelName(str3);
            if (i != 0)
              localProgramNode2.channelRatingStar = localRecommendItemNode.ratingStar;
            localRecommendItemNode.setDetail(localProgramNode2);
            break label715;
            localRecommendItemNode.setSmallThumb(paramJSONObject.getString("thumb"));
            break;
            label345: if (!localJSONObject2.containsKey("star"))
              break label709;
            localRecommendItemNode.ratingStar = localJSONObject2.getIntValue("star");
            i = 1;
            break label149;
            if (!str1.equalsIgnoreCase("category"))
              break label685;
            k = localJSONObject3.getIntValue("parent_id");
            str3 = null;
            j = 0;
            break label226;
          }
          if (str4.equalsIgnoreCase("channel_ondemand"))
          {
            ChannelNode localChannelNode2 = _parseChannelNode(localJSONObject2);
            localChannelNode2.categoryId = k;
            if (i != 0)
              localChannelNode2.ratingStar = localRecommendItemNode.ratingStar;
            localRecommendItemNode.setDetail(localChannelNode2);
            break label715;
          }
          if (str4.equalsIgnoreCase("channel_live"))
          {
            ChannelNode localChannelNode1 = _parseChannelNode(localJSONObject2);
            localChannelNode1.categoryId = k;
            if (i != 0)
              localChannelNode1.ratingStar = localRecommendItemNode.ratingStar;
            localRecommendItemNode.setDetail(localChannelNode1);
            break label715;
          }
          if (str4.equalsIgnoreCase("topic"))
          {
            SpecialTopicNode localSpecialTopicNode = _parseSpecialTopicNode(localJSONObject2);
            if (localSpecialTopicNode != null)
            {
              localRecommendItemNode.mCategoryId = localSpecialTopicNode.categoryId;
              if (i != 0)
                localSpecialTopicNode.channelStar = localRecommendItemNode.ratingStar;
              localRecommendItemNode.setDetail(localSpecialTopicNode);
            }
            if (i == 0)
              break label715;
            localSpecialTopicNode.channelStar = localRecommendItemNode.ratingStar;
            break label715;
          }
          if (str4.equalsIgnoreCase("activity"))
          {
            localRecommendItemNode.setDetail(_parseActivity(localJSONObject2));
            break label715;
          }
          boolean bool = str4.equalsIgnoreCase("program_temp");
          localObject = null;
          if (!bool)
            break label718;
          ProgramNode localProgramNode1 = _parseProgramTempNode(localJSONObject2);
          if (localProgramNode1 != null)
          {
            localProgramNode1.mLiveInVirtual = true;
            localProgramNode1.channelId = j;
            if (str3 != null)
              localProgramNode1.setChannelName(str3);
            localRecommendItemNode.setDetail(localProgramNode1);
          }
          if (i == 0)
            break label715;
          localProgramNode1.channelRatingStar = localRecommendItemNode.ratingStar;
          break label715;
          label685: str3 = null;
          k = 0;
          j = 0;
          break label226;
          str3 = null;
          k = 0;
          j = 0;
        }
        label709: i = 0;
      }
      label715: localObject = localRecommendItemNode;
      label718: return localObject;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  private RecommendItemNode _parseRecommendNode(JSONObject paramJSONObject)
  {
    return null;
  }

  private RecommendPlayingItemNode _parseRecommendPlayingProgram(JSONObject paramJSONObject)
  {
    if (paramJSONObject != null)
      try
      {
        JSONObject localJSONObject1 = paramJSONObject.getJSONObject("detail");
        if (localJSONObject1 == null)
          return null;
        RecommendPlayingItemNode localRecommendPlayingItemNode = new RecommendPlayingItemNode();
        localRecommendPlayingItemNode.channelName = paramJSONObject.getString("sub_title");
        localRecommendPlayingItemNode.programName = paramJSONObject.getString("title");
        localRecommendPlayingItemNode.thumb = paramJSONObject.getString("thumb");
        if (paramJSONObject.containsKey("channel_star"))
          localRecommendPlayingItemNode.ratingStar = paramJSONObject.getIntValue("channel_star");
        if (localJSONObject1.containsKey("channel_star"))
          localRecommendPlayingItemNode.ratingStar = localJSONObject1.getIntValue("channel_star");
        JSONObject localJSONObject2 = localJSONObject1.getJSONObject("mediainfo");
        if (localJSONObject2 != null)
          localRecommendPlayingItemNode.resId = localJSONObject2.getIntValue("id");
        localRecommendPlayingItemNode.channelId = localJSONObject1.getIntValue("channel_id");
        localRecommendPlayingItemNode.programid = localJSONObject1.getIntValue("id");
        localRecommendPlayingItemNode.startTime = localJSONObject1.getString("start_time");
        if ((localRecommendPlayingItemNode.startTime != null) && (localRecommendPlayingItemNode.startTime.equalsIgnoreCase("00:00:00")))
          localRecommendPlayingItemNode.startTime = "00:00:01";
        localRecommendPlayingItemNode.endTime = localJSONObject1.getString("end_time");
        if ((localRecommendPlayingItemNode.endTime != null) && (localRecommendPlayingItemNode.endTime.equalsIgnoreCase("00:00:00")))
          localRecommendPlayingItemNode.endTime = "23:59:59";
        return localRecommendPlayingItemNode;
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  private SpecialTopicNode _parseSpecialTopicNode(JSONObject paramJSONObject)
  {
    if (paramJSONObject != null)
      try
      {
        SpecialTopicNode localSpecialTopicNode = new SpecialTopicNode();
        localSpecialTopicNode.id = (1000001 + paramJSONObject.getIntValue("id"));
        localSpecialTopicNode.title = paramJSONObject.getString("title");
        localSpecialTopicNode.thumb = paramJSONObject.getString("thumb");
        localSpecialTopicNode.desc = paramJSONObject.getString("description");
        localSpecialTopicNode.categoryId = paramJSONObject.getIntValue("category_id");
        localSpecialTopicNode.create_time = paramJSONObject.getString("create_time");
        localSpecialTopicNode.update_time = paramJSONObject.getString("update_time");
        return localSpecialTopicNode;
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  private UserInfo _parseUserInfo(JSONObject paramJSONObject)
  {
    UserInfo localUserInfo;
    if (paramJSONObject != null)
      try
      {
        localUserInfo = new UserInfo();
        localUserInfo.userKey = paramJSONObject.getString("userId");
        localUserInfo.snsInfo.sns_avatar = paramJSONObject.getString("avatar");
        localUserInfo.snsInfo.sns_name = paramJSONObject.getString("userName");
        localUserInfo.snsInfo.signature = paramJSONObject.getString("signature");
        localUserInfo.snsInfo.age = paramJSONObject.getIntValue("age");
        localUserInfo.snsInfo.sns_gender = paramJSONObject.getString("gender");
        String str1 = paramJSONObject.getString("is_blocked");
        if ((str1 != null) && (!str1.equalsIgnoreCase("")))
          if (Integer.valueOf(str1).intValue() != 0)
            break label163;
        label163: for (localUserInfo.isBlocked = false; ; localUserInfo.isBlocked = true)
        {
          String str2 = paramJSONObject.getString("level");
          if ((str2 == null) || (str2.equalsIgnoreCase("")))
            break;
          localUserInfo.level = Integer.valueOf(str2).intValue();
          return localUserInfo;
        }
      }
      catch (Exception localException)
      {
      }
    else
      localUserInfo = null;
    return localUserInfo;
  }

  private ProgramNode _parseVirtualProgramNode(JSONObject paramJSONObject, int paramInt)
  {
    ProgramNode localProgramNode1 = null;
    if (paramJSONObject != null)
    {
      String str1 = paramJSONObject.getString("type");
      if ((str1 == null) || (str1.equalsIgnoreCase("program_ondemand")))
        break label55;
      boolean bool = str1.equalsIgnoreCase("program_temp");
      localProgramNode1 = null;
      if (bool)
        localProgramNode1 = _parseProgramTempNode(paramJSONObject);
    }
    return localProgramNode1;
    label55: ProgramNode localProgramNode2 = new ProgramNode();
    localProgramNode2.id = paramJSONObject.getIntValue("id");
    localProgramNode2.uniqueId = localProgramNode2.id;
    localProgramNode2.channelType = 1;
    localProgramNode2.title = paramJSONObject.getString("title");
    localProgramNode2.duration = paramJSONObject.getDoubleValue("duration");
    localProgramNode2.sequence = paramJSONObject.getIntValue("sequence");
    localProgramNode2.createTime = paramJSONObject.getString("create_time");
    localProgramNode2.updateTime = paramJSONObject.getString("update_time");
    localProgramNode2.sequence = paramJSONObject.getIntValue("sequence");
    localProgramNode2.groupId = paramJSONObject.getIntValue("chatgroup_id");
    JSONObject localJSONObject1 = paramJSONObject.getJSONObject("mediainfo");
    if (localJSONObject1 != null)
    {
      localProgramNode2.resId = localJSONObject1.getIntValue("id");
      JSONArray localJSONArray = localJSONObject1.getJSONArray("bitrates_url");
      if (localJSONArray != null)
      {
        localProgramNode2.lstAudioPath = new ArrayList();
        localProgramNode2.lstBitrate = new ArrayList();
        for (int i = 0; i < localJSONArray.size(); i++)
        {
          JSONObject localJSONObject2 = localJSONArray.getJSONObject(i);
          if (localJSONObject2 != null)
          {
            String str2 = localJSONObject2.getString("file_path");
            if (str2 != null)
              localProgramNode2.lstAudioPath.add(str2);
            Integer localInteger = Integer.valueOf(localJSONObject2.getIntValue("bitrate"));
            if (localInteger != null)
              localProgramNode2.lstBitrate.add(localInteger);
          }
        }
      }
    }
    return localProgramNode2;
  }

  private void addParentForRecommendNodes(Node paramNode, List<RecommendItemNode> paramList)
  {
    if ((paramNode == null) || (paramList == null));
    while (true)
    {
      return;
      for (int i = 0; i < paramList.size(); i++)
        if (((RecommendItemNode)paramList.get(i)).mNode != null)
          ((RecommendItemNode)paramList.get(i)).mNode.parent = paramNode;
    }
  }

  private boolean findSameValue(List<String> paramList, String paramString)
  {
    if ((paramList == null) || (paramString == null));
    while (true)
    {
      return false;
      for (int i = 0; i < paramList.size(); i++)
        if ((paramList.get(i) != null) && (((String)paramList.get(i)).equalsIgnoreCase(paramString)))
          return true;
    }
  }

  private Result parseADConfig(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    while (true)
    {
      int i;
      try
      {
        JSONArray localJSONArray = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        if (localJSONArray != null)
        {
          ArrayList localArrayList = new ArrayList();
          i = 0;
          if (i < localJSONArray.size())
          {
            JSONObject localJSONObject = localJSONArray.getJSONObject(i);
            if (localJSONObject == null)
              break label256;
            String str1 = localJSONObject.getString("name");
            String str2 = localJSONObject.getString("url");
            int j = localJSONObject.getIntValue("percent");
            String str3 = localJSONObject.getString("region");
            AdConfig localAdConfig1 = new AdConfig();
            localAdConfig1.name = str1;
            localAdConfig1.url = str2;
            localAdConfig1.percent = j;
            localAdConfig1.mRegions = str3;
            localArrayList.add(localAdConfig1);
            if (localAdConfig1.percent <= 1000)
              break label256;
            int k = -1 + localAdConfig1.percent / 1000;
            int m = 0;
            if (m >= k)
              break label256;
            AdConfig localAdConfig2 = new AdConfig();
            localAdConfig2.name = str1;
            localAdConfig2.url = str2;
            localAdConfig2.percent = j;
            localAdConfig2.mRegions = str3;
            localArrayList.add(localAdConfig2);
            m++;
            continue;
          }
          Result localResult = new Result(true, localArrayList);
          return localResult;
        }
      }
      catch (Exception localException)
      {
      }
      return null;
      label256: i++;
    }
  }

  private Result parseADCouponInfo(String paramString)
  {
    if (paramString != null)
    {
      QTCoupon localQTCoupon = new QTCoupon();
      try
      {
        JSONObject localJSONObject = (JSONObject)JSON.parse(paramString);
        localQTCoupon.result = localJSONObject.getString("result");
        if (localJSONObject.containsKey("coupon"))
          localQTCoupon.coupon = ((JSONObject)JSON.parse(localJSONObject.getString("coupon"))).getString("coupon");
        return new Result(true, localQTCoupon);
      }
      catch (Exception localException)
      {
        return null;
      }
    }
    return null;
  }

  private Result parseADLocation(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
      try
      {
        JSONObject localJSONObject = ((JSONObject)JSON.parse(paramString)).getJSONObject("data");
        if ((localJSONObject != null) && (localJSONObject.getIntValue("errorno") == 0))
        {
          QTADLocation localQTADLocation = new QTADLocation();
          localQTADLocation.city = localJSONObject.getString("city");
          localQTADLocation.regionCode = localJSONObject.getString("region");
          localQTADLocation.school = localJSONObject.getString("school");
          Result localResult = new Result(true, localQTADLocation);
          return localResult;
        }
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  private Result parseADVFromAirWave(String paramString)
  {
    int i = 0;
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
      try
      {
        JSONObject localJSONObject = ((JSONObject)JSON.parse(paramString)).getJSONObject("0A89BCB4E85400C4");
        if (localJSONObject != null)
        {
          if (localJSONObject.getIntValue("returncode") != 200)
            return null;
          RecommendItemNode localRecommendItemNode = new RecommendItemNode();
          localRecommendItemNode.name = "推广";
          localRecommendItemNode.setSmallThumb(localJSONObject.getString("imgurl"));
          localRecommendItemNode.isweb = true;
          ActivityNode localActivityNode = new ActivityNode();
          localActivityNode.name = "推广";
          localActivityNode.infoUrl = localRecommendItemNode.getApproximativeThumb();
          localActivityNode.contentUrl = localJSONObject.getString("clickurl");
          localActivityNode.desc = localActivityNode.name;
          localActivityNode.infoTitle = localActivityNode.name;
          localActivityNode.putUserInfo = false;
          JSONArray localJSONArray1 = localJSONObject.getJSONArray("imgtracking");
          JSONArray localJSONArray2 = localJSONObject.getJSONArray("thclkurl");
          if ((localJSONArray1 != null) && (localJSONArray1.size() > 0))
          {
            localActivityNode.imageTracking = new ArrayList();
            for (int j = 0; j < localJSONArray1.size(); j++)
            {
              String str2 = (String)localJSONArray1.get(j);
              localActivityNode.imageTracking.add(str2);
            }
          }
          if ((localJSONArray2 != null) && (localJSONArray2.size() > 0))
          {
            localActivityNode.clickTracking = new ArrayList();
            while (i < localJSONArray2.size())
            {
              String str1 = (String)localJSONArray2.get(i);
              localActivityNode.clickTracking.add(str1);
              i++;
            }
          }
          localRecommendItemNode.mNode = localActivityNode;
          Result localResult = new Result(true, localRecommendItemNode);
          return localResult;
        }
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  private Result parseADWhiteList(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    while (true)
    {
      int i;
      try
      {
        JSONArray localJSONArray = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        if (localJSONArray != null)
        {
          ArrayList localArrayList = new ArrayList();
          i = 0;
          if (i < localJSONArray.size())
          {
            String str = localJSONArray.getString(i);
            if (str == null)
              break label99;
            localArrayList.add(Integer.valueOf(str));
            break label99;
          }
          Result localResult = new Result(true, localArrayList);
          return localResult;
        }
      }
      catch (Exception localException)
      {
      }
      return null;
      label99: i++;
    }
  }

  private Result parseADinfoList(String paramString)
  {
    ArrayList localArrayList;
    if (paramString != null)
      localArrayList = new ArrayList();
    while (true)
    {
      try
      {
        JSONArray localJSONArray = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        if (localJSONArray.size() != 0)
          break label161;
        return new Result(true, localArrayList);
        if (i < localJSONArray.size())
        {
          JSONObject localJSONObject = localJSONArray.getJSONObject(i);
          String str1 = localJSONObject.getString("id");
          String str2 = localJSONObject.getString("download_url");
          String str3 = localJSONObject.getString("thumb");
          String str4 = localJSONObject.getString("logo");
          localArrayList.add(new QTAdvertisementInfo(str1, localJSONObject.getString("title"), str3, str2, str4, localJSONObject.getString("desc")));
          i++;
          continue;
        }
      }
      catch (Exception localException)
      {
        return null;
      }
      return new Result(true, localArrayList);
      return null;
      label161: int i = 0;
    }
  }

  private Result parseAMAdConfig(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    while (true)
    {
      int i;
      try
      {
        JSONArray localJSONArray = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        if (localJSONArray != null)
        {
          ArrayList localArrayList = new ArrayList();
          i = 0;
          if (i < localJSONArray.size())
          {
            JSONObject localJSONObject = localJSONArray.getJSONObject(i);
            if (localJSONObject == null)
              break label209;
            AdConfig localAdConfig = new AdConfig();
            localAdConfig.mAdvId = localJSONObject.getString("advid");
            localAdConfig.mAdMasterUrl = localJSONObject.getString("admasterurl");
            localAdConfig.mMiaozhenUrl = localJSONObject.getString("miaozhenurl");
            localAdConfig.mCustomerUrl = localJSONObject.getString("customerurl");
            localAdConfig.mMMAUrl = localJSONObject.getString("mmaurl");
            localAdConfig.mRegions = localJSONObject.getString("regions");
            localAdConfig.percent = localJSONObject.getIntValue("percent");
            localAdConfig.mEventType = localJSONObject.getString("eventtype");
            localArrayList.add(localAdConfig);
            break label209;
          }
          Result localResult = new Result(true, localArrayList);
          return localResult;
        }
      }
      catch (Exception localException)
      {
      }
      return null;
      label209: i++;
    }
  }

  private Result parseActivityList(String paramString)
  {
    if (paramString != null)
    {
      ArrayList localArrayList = new ArrayList();
      try
      {
        JSONArray localJSONArray = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        for (int i = 0; i < localJSONArray.size(); i++)
        {
          JSONObject localJSONObject = (JSONObject)localJSONArray.get(i);
          ActivityNode localActivityNode = new ActivityNode();
          localActivityNode.id = localJSONObject.getIntValue("id");
          localActivityNode.name = localJSONObject.getString("title");
          localActivityNode.type = localJSONObject.getString("type");
          localActivityNode.contentUrl = localJSONObject.getString("url");
          localActivityNode.infoUrl = localActivityNode.contentUrl;
          localActivityNode.infoTitle = localJSONObject.getString("description");
          localArrayList.add(localActivityNode);
        }
      }
      catch (Exception localException)
      {
        return null;
      }
      return new Result(true, localArrayList);
    }
    return null;
  }

  private Result parseAddFriend(String paramString)
  {
    if (paramString != null);
    return new Result(true, paramString);
  }

  private Result parseAdsPos(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
      try
      {
        JSONArray localJSONArray = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        if (localJSONArray != null)
        {
          ArrayList localArrayList = new ArrayList();
          for (int i = 0; i < localJSONArray.size(); i++)
          {
            JSONObject localJSONObject = localJSONArray.getJSONObject(i);
            AdPos localAdPos = new AdPos();
            localAdPos.posdesc = localJSONObject.getString("posdesc");
            localAdPos.posid = localJSONObject.getString("posid");
            localAdPos.posquery = localJSONObject.getString("posquery");
            localAdPos.parseDesc();
            localArrayList.add(localAdPos);
          }
          Result localResult = new Result(true, localArrayList);
          return localResult;
        }
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  private Result parseAdvertisementInfo(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    while (true)
    {
      int k;
      try
      {
        JSONObject localJSONObject1 = (JSONObject)JSON.parse(paramString);
        int i = localJSONObject1.getIntValue("errorno");
        AdvertisementItemNode localAdvertisementItemNode = new AdvertisementItemNode();
        if (i == 0)
        {
          JSONObject localJSONObject2 = localJSONObject1.getJSONObject("data");
          localAdvertisementItemNode.id = localJSONObject2.getString("id");
          localAdvertisementItemNode.image = localJSONObject2.getString("image");
          localAdvertisementItemNode.resType = localJSONObject2.getIntValue("restype");
          int j = localJSONObject2.getIntValue("size");
          localAdvertisementItemNode.width = (j / 10000);
          localAdvertisementItemNode.height = (j - 10000 * localAdvertisementItemNode.width);
          localAdvertisementItemNode.audioPath = localJSONObject2.getString("audio");
          localAdvertisementItemNode.duration = localJSONObject2.getIntValue("duration");
          localAdvertisementItemNode.landing = localJSONObject2.getString("landing");
          localAdvertisementItemNode.desc = localJSONObject2.getString("subtitle");
          localAdvertisementItemNode.interval = localJSONObject2.getIntValue("interval");
          localAdvertisementItemNode.splash_landing = localJSONObject2.getString("splash_landing");
          localAdvertisementItemNode.skin = localJSONObject2.getString("skin");
          localAdvertisementItemNode.useLocalWebview = localJSONObject2.getBooleanValue("use_default_browser");
          if (localAdvertisementItemNode.useLocalWebview)
            break label571;
          bool = true;
          localAdvertisementItemNode.useLocalWebview = bool;
          localAdvertisementItemNode.internal_landing = localJSONObject2.getString("internal_landing");
          if ((localAdvertisementItemNode.internal_landing != null) && (!localAdvertisementItemNode.internal_landing.equalsIgnoreCase("")))
          {
            String[] arrayOfString = localAdvertisementItemNode.internal_landing.split("/");
            if ((arrayOfString != null) && (arrayOfString.length >= 5))
            {
              localAdvertisementItemNode.internal_catid = Integer.valueOf(arrayOfString[1]).intValue();
              localAdvertisementItemNode.internal_channelid = Integer.valueOf(arrayOfString[2]).intValue();
              localAdvertisementItemNode.interval_programid = Integer.valueOf(arrayOfString[3]).intValue();
              localAdvertisementItemNode.interval_channeltype = Integer.valueOf(arrayOfString[4]).intValue();
            }
          }
          JSONArray localJSONArray = localJSONObject2.getJSONArray("trackers");
          if (localJSONArray != null)
          {
            localAdvertisementItemNode.mTracker.lstEventType = new ArrayList();
            localAdvertisementItemNode.mTracker.lstProvider = new ArrayList();
            localAdvertisementItemNode.mTracker.lstTrackerUrl = new ArrayList();
            k = 0;
            if (k < localJSONArray.size())
            {
              JSONObject localJSONObject3 = localJSONArray.getJSONObject(k);
              String str1 = localJSONObject3.getString("provider");
              String str2 = localJSONObject3.getString("url");
              String str3 = localJSONObject3.getString("event_type");
              if ((str1 == null) || (str2 == null) || (str3 == null))
                break label565;
              localAdvertisementItemNode.mTracker.lstEventType.add(str3);
              localAdvertisementItemNode.mTracker.lstProvider.add(str1);
              localAdvertisementItemNode.mTracker.lstTrackerUrl.add(str2);
              break label565;
            }
          }
          Result localResult = new Result(true, localAdvertisementItemNode);
          return localResult;
        }
      }
      catch (Exception localException)
      {
      }
      return null;
      label565: k++;
      continue;
      label571: boolean bool = false;
    }
  }

  private Result parseAllChannels(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    while (true)
    {
      int i;
      try
      {
        JSONObject localJSONObject = (JSONObject)JSON.parse(paramString);
        JSONArray localJSONArray = localJSONObject.getJSONArray("data");
        if (localJSONArray != null)
        {
          ArrayList localArrayList = new ArrayList();
          i = 0;
          localObject1 = null;
          if (i < localJSONArray.size())
          {
            localObject2 = _parseChannelNode(localJSONArray.getJSONObject(i));
            if (localObject2 == null)
              break label145;
            localArrayList.add(localObject2);
            if (localObject1 == null)
              break label149;
            localObject1.nextSibling = ((Node)localObject2);
            ((ChannelNode)localObject2).prevSibling = localObject1;
            break label149;
          }
          Result localResult = new Result(true, localArrayList, "total", String.valueOf(localJSONObject.getIntValue("total")));
          return localResult;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return null;
      label145: Object localObject2 = localObject1;
      label149: i++;
      Object localObject1 = localObject2;
    }
  }

  private Result parseBillboardChannel(String paramString)
  {
    ArrayList localArrayList;
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
      localArrayList = new ArrayList();
    while (true)
    {
      int i;
      try
      {
        JSONArray localJSONArray = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        i = 0;
        if (i < localJSONArray.size())
        {
          BillboardItemNode localBillboardItemNode = _parseBillboardNode(localJSONArray.getJSONObject(i));
          if (localBillboardItemNode == null)
            break label96;
          localArrayList.add(localBillboardItemNode);
          break label96;
        }
        Result localResult = new Result(true, localArrayList);
        return localResult;
      }
      catch (Exception localException)
      {
      }
      return null;
      label96: i++;
    }
  }

  private Result parseBillboardChannels(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    while (true)
    {
      int i;
      try
      {
        JSONArray localJSONArray = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        if (localJSONArray != null)
        {
          ArrayList localArrayList = new ArrayList();
          i = 0;
          if (i < localJSONArray.size())
          {
            JSONObject localJSONObject1 = localJSONArray.getJSONObject(i);
            if (localJSONObject1 == null)
              break label197;
            BillboardItemNode localBillboardItemNode = new BillboardItemNode();
            JSONObject localJSONObject2 = localJSONObject1.getJSONObject("detail");
            if (localJSONObject2 != null)
            {
              ChannelNode localChannelNode = new ChannelNode();
              String str = localJSONObject2.getString("type");
              if ((str == null) || (!str.equalsIgnoreCase("channel_live")))
                break label197;
              localChannelNode.channelType = 0;
              localBillboardItemNode.setDetail(_parseChannelNode(localJSONObject2));
            }
            localBillboardItemNode.name = localJSONObject1.getString("titile");
            localBillboardItemNode.desc = localJSONObject1.getString("sub_title");
            localArrayList.add(localBillboardItemNode);
            break label197;
          }
          Result localResult = new Result(true, localArrayList);
          return localResult;
        }
      }
      catch (Exception localException)
      {
      }
      return null;
      label197: i++;
    }
  }

  private Result parseBillboardProgram(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
      try
      {
        JSONArray localJSONArray = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        if (localJSONArray != null)
        {
          ArrayList localArrayList = new ArrayList();
          for (int i = 0; ; i++)
            if (i < localJSONArray.size())
            {
              JSONObject localJSONObject1 = localJSONArray.getJSONObject(i);
              if (localJSONObject1 != null)
              {
                JSONObject localJSONObject2 = localJSONObject1.getJSONObject("parent_info");
                if (localJSONObject2 == null)
                  return null;
                BillboardItemNode localBillboardItemNode = new BillboardItemNode();
                JSONObject localJSONObject3 = localJSONObject1.getJSONObject("detail");
                ProgramNode localProgramNode1;
                if (localJSONObject3 != null)
                {
                  localProgramNode1 = new ProgramNode();
                  String str = localJSONObject3.getString("type");
                  if ((str == null) || (!str.equalsIgnoreCase("program_ondemand")))
                    break label237;
                }
                label237: for (localProgramNode1.channelType = 1; ; localProgramNode1.channelType = 0)
                {
                  ProgramNode localProgramNode2 = _parseVirtualProgramNode(localJSONObject3, 0);
                  localProgramNode2.channelId = localJSONObject2.getIntValue("parent_id");
                  localBillboardItemNode.parentId = localProgramNode2.channelId;
                  localBillboardItemNode.parentType = localJSONObject2.getString("parent_type");
                  localBillboardItemNode.setDetail(localProgramNode2);
                  localBillboardItemNode.name = localJSONObject1.getString("titile");
                  localBillboardItemNode.desc = localJSONObject1.getString("sub_title");
                  localArrayList.add(localBillboardItemNode);
                  break;
                }
              }
            }
            else
            {
              Result localResult = new Result(true, localArrayList);
              return localResult;
            }
        }
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  private BroadcasterNode parseBroadcasterNode(JSONObject paramJSONObject)
  {
    BroadcasterNode localBroadcasterNode = new BroadcasterNode();
    localBroadcasterNode.avatar = paramJSONObject.getString("thumb");
    localBroadcasterNode.nick = paramJSONObject.getString("username");
    localBroadcasterNode.id = paramJSONObject.getIntValue("id");
    localBroadcasterNode.qqName = paramJSONObject.getString("qq_name");
    localBroadcasterNode.qqId = paramJSONObject.getString("qq_id");
    localBroadcasterNode.weiboName = paramJSONObject.getString("weibo_name");
    localBroadcasterNode.weiboId = paramJSONObject.getString("weibo_id");
    return localBroadcasterNode;
  }

  private Result parseCategoryAttr(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    while (true)
    {
      int i;
      try
      {
        JSONArray localJSONArray1 = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        if (localJSONArray1 != null)
        {
          ArrayList localArrayList = new ArrayList();
          i = 0;
          if (i < localJSONArray1.size())
          {
            JSONObject localJSONObject1 = localJSONArray1.getJSONObject(i);
            if (localJSONObject1 == null)
              break label232;
            Attributes localAttributes = new Attributes();
            localAttributes.mLstAttribute = new ArrayList();
            localAttributes.id = localJSONObject1.getIntValue("id");
            localAttributes.name = localJSONObject1.getString("name");
            JSONArray localJSONArray2 = localJSONObject1.getJSONArray("values");
            if (localJSONArray2 != null)
            {
              int j = 0;
              if (j < localJSONArray2.size())
              {
                JSONObject localJSONObject2 = localJSONArray2.getJSONObject(j);
                Attribute localAttribute = new Attribute();
                localAttribute.id = localJSONObject2.getIntValue("id");
                localAttribute.name = localJSONObject2.getString("name");
                localAttributes.mLstAttribute.add(localAttribute);
                j++;
                continue;
              }
            }
            localArrayList.add(localAttributes);
            break label232;
          }
          Result localResult = new Result(true, localArrayList);
          return localResult;
        }
      }
      catch (Exception localException)
      {
      }
      return null;
      label232: i++;
    }
  }

  private Result parseChannelNode(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
      try
      {
        JSONObject localJSONObject = (JSONObject)JSON.parse(paramString);
        if (localJSONObject != null)
        {
          ChannelNode localChannelNode = _parseChannelNode(localJSONObject.getJSONObject("data"));
          if (localChannelNode != null)
          {
            Result localResult = new Result(true, localChannelNode);
            return localResult;
          }
        }
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  private Result parseCheckInStatus(String paramString)
  {
    if (paramString != null)
    {
      CheckInNode localCheckInNode = new CheckInNode();
      try
      {
        JSONObject localJSONObject = ((JSONObject)JSON.parse(paramString)).getJSONObject("data");
        localCheckInNode.date = localJSONObject.getString("date");
        localCheckInNode.pid = String.valueOf(localJSONObject.getIntValue("pid"));
        localCheckInNode.name = localJSONObject.getString("name");
        localCheckInNode.type = localJSONObject.getString("type");
        localCheckInNode.todaynum = localJSONObject.getIntValue("todaynum");
        localCheckInNode.totalnum = localJSONObject.getIntValue("totalnum");
        return new Result(true, localCheckInNode);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
    }
    return null;
  }

  private Result parseContentCategory(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    while (true)
    {
      try
      {
        JSONArray localJSONArray = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        int i = 0;
        if (i >= localJSONArray.size())
          break label113;
        JSONObject localJSONObject = (JSONObject)localJSONArray.get(i);
        String str2 = String.valueOf(localJSONObject.getIntValue("id"));
        boolean bool = localJSONObject.getString("container").equalsIgnoreCase("channel");
        if (bool)
        {
          str1 = str2;
          return new Result(true, str1);
        }
        i++;
        continue;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      return null;
      label113: String str1 = "";
    }
  }

  private Result parseContentConfigure(String paramString)
  {
    if (paramString != null)
    {
      ArrayList localArrayList = new ArrayList();
      try
      {
        JSONArray localJSONArray = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        for (int i = 0; i < localJSONArray.size(); i++)
        {
          JSONObject localJSONObject = localJSONArray.getJSONObject(i);
          RecommendConfigureNode localRecommendConfigureNode = new RecommendConfigureNode();
          localRecommendConfigureNode.id = localJSONObject.getIntValue("id");
          localRecommendConfigureNode.name = localJSONObject.getString("name");
          localRecommendConfigureNode.iconId = localJSONObject.getString("iconId");
          localRecommendConfigureNode.iconUrl = localJSONObject.getString("iconUrl");
          localRecommendConfigureNode.type = localJSONObject.getString("type");
          localRecommendConfigureNode.itemtype = localJSONObject.getString("itemtype");
          localRecommendConfigureNode.updatetime = localJSONObject.getIntValue("updatetime");
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      return new Result(true, localArrayList);
    }
    return null;
  }

  private Result parseCreateUser(String paramString)
  {
    if (paramString != null)
      try
      {
        String str = ((JSONObject)JSON.parse(paramString)).getJSONObject("data").getString("userid");
        return new Result(true, str);
      }
      catch (Exception localException)
      {
        return null;
      }
    return null;
  }

  // ERROR //
  private Result parseCurrentPlayingPrograms(String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +216 -> 217
    //   4: new 140	java/util/ArrayList
    //   7: dup
    //   8: invokespecial 141	java/util/ArrayList:<init>	()V
    //   11: astore_2
    //   12: aload_1
    //   13: invokestatic 727	com/alibaba/fastjson/JSON:parse	(Ljava/lang/String;)Ljava/lang/Object;
    //   16: checkcast 19	com/alibaba/fastjson/JSONObject
    //   19: ldc_w 486
    //   22: invokevirtual 145	com/alibaba/fastjson/JSONObject:getJSONArray	(Ljava/lang/String;)Lcom/alibaba/fastjson/JSONArray;
    //   25: astore 4
    //   27: iconst_0
    //   28: istore 5
    //   30: iload 5
    //   32: aload 4
    //   34: invokevirtual 150	com/alibaba/fastjson/JSONArray:size	()I
    //   37: if_icmpge +170 -> 207
    //   40: aload 4
    //   42: iload 5
    //   44: invokevirtual 804	com/alibaba/fastjson/JSONArray:get	(I)Ljava/lang/Object;
    //   47: checkcast 19	com/alibaba/fastjson/JSONObject
    //   50: astore 6
    //   52: aload 6
    //   54: ldc 17
    //   56: invokevirtual 23	com/alibaba/fastjson/JSONObject:getIntValue	(Ljava/lang/String;)I
    //   59: istore 7
    //   61: aload 6
    //   63: ldc 28
    //   65: invokevirtual 32	com/alibaba/fastjson/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   68: astore 8
    //   70: new 1079	fm/qingting/qtradio/model/PlayingProgramNode
    //   73: dup
    //   74: invokespecial 1080	fm/qingting/qtradio/model/PlayingProgramNode:<init>	()V
    //   77: astore 9
    //   79: aload 9
    //   81: iload 7
    //   83: putfield 1081	fm/qingting/qtradio/model/PlayingProgramNode:channelId	I
    //   86: aload 9
    //   88: aload 8
    //   90: putfield 1082	fm/qingting/qtradio/model/PlayingProgramNode:channelName	Ljava/lang/String;
    //   93: aload 6
    //   95: ldc_w 1084
    //   98: invokevirtual 145	com/alibaba/fastjson/JSONObject:getJSONArray	(Ljava/lang/String;)Lcom/alibaba/fastjson/JSONArray;
    //   101: astore 11
    //   103: aload 9
    //   105: new 140	java/util/ArrayList
    //   108: dup
    //   109: invokespecial 141	java/util/ArrayList:<init>	()V
    //   112: putfield 1087	fm/qingting/qtradio/model/PlayingProgramNode:lstbroadcastersName	Ljava/util/List;
    //   115: iconst_0
    //   116: istore 12
    //   118: iload 12
    //   120: aload 11
    //   122: invokevirtual 150	com/alibaba/fastjson/JSONArray:size	()I
    //   125: if_icmpge +60 -> 185
    //   128: aload 11
    //   130: iload 12
    //   132: invokevirtual 804	com/alibaba/fastjson/JSONArray:get	(I)Ljava/lang/Object;
    //   135: checkcast 19	com/alibaba/fastjson/JSONObject
    //   138: astore 14
    //   140: aload 9
    //   142: aload 14
    //   144: ldc_w 728
    //   147: invokevirtual 32	com/alibaba/fastjson/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   150: putfield 1088	fm/qingting/qtradio/model/PlayingProgramNode:programName	Ljava/lang/String;
    //   153: aload 9
    //   155: aload 14
    //   157: ldc_w 1090
    //   160: invokevirtual 32	com/alibaba/fastjson/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   163: putfield 1093	fm/qingting/qtradio/model/PlayingProgramNode:broadcastTime	Ljava/lang/String;
    //   166: aload 9
    //   168: aload 14
    //   170: ldc_w 548
    //   173: invokevirtual 23	com/alibaba/fastjson/JSONObject:getIntValue	(Ljava/lang/String;)I
    //   176: putfield 1094	fm/qingting/qtradio/model/PlayingProgramNode:duration	I
    //   179: iinc 12 1
    //   182: goto -64 -> 118
    //   185: aload_2
    //   186: aload 9
    //   188: invokeinterface 166 2 0
    //   193: pop
    //   194: iinc 5 1
    //   197: goto -167 -> 30
    //   200: astore_3
    //   201: aload_3
    //   202: invokevirtual 989	java/lang/Exception:printStackTrace	()V
    //   205: aconst_null
    //   206: areturn
    //   207: new 745	fm/qingting/framework/data/Result
    //   210: dup
    //   211: iconst_1
    //   212: aload_2
    //   213: invokespecial 748	fm/qingting/framework/data/Result:<init>	(ZLjava/lang/Object;)V
    //   216: areturn
    //   217: aconst_null
    //   218: areturn
    //   219: astore 10
    //   221: goto -27 -> 194
    //
    // Exception table:
    //   from	to	target	type
    //   12	27	200	java/lang/Exception
    //   30	93	200	java/lang/Exception
    //   185	194	200	java/lang/Exception
    //   93	115	219	java/lang/Exception
    //   118	179	219	java/lang/Exception
  }

  private Result parseDataCenterList(String paramString)
  {
    if (paramString != null);
    return null;
  }

  private Result parseGetUserData(String paramString)
  {
    if (paramString != null);
    try
    {
      JSONObject localJSONObject = ((JSONObject)JSON.parse(paramString)).getJSONObject("data");
      UserProfile localUserProfile = new UserProfile();
      String str1 = localJSONObject.getString("_id");
      localUserProfile.setUserKey(str1, Integer.valueOf(localJSONObject.getString("sns_type")).intValue());
      UserInfo localUserInfo = new UserInfo();
      localUserInfo.userKey = str1;
      localUserInfo.snsInfo.sns_id = localJSONObject.getString("sns_id");
      localUserInfo.snsInfo.sns_name = localJSONObject.getString("username");
      localUserInfo.snsInfo.sns_avatar = localJSONObject.getString("avatar");
      String str2 = localJSONObject.getString("is_blocked");
      if ((str2 != null) && (!str2.equalsIgnoreCase("")))
        if (Integer.valueOf(str2).intValue() != 0)
          break label218;
      label218: for (localUserInfo.isBlocked = false; ; localUserInfo.isBlocked = true)
      {
        String str3 = localJSONObject.getString("level");
        if ((str3 != null) && (!str3.equalsIgnoreCase("")))
          localUserInfo.level = Integer.valueOf(str3).intValue();
        localUserProfile.setUserInfo(localUserInfo);
        return new Result(true, localUserProfile);
      }
    }
    catch (Exception localException)
    {
      return null;
    }
    catch (Error localError)
    {
      label228: break label228;
    }
  }

  private Result parseGroupInfo(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    while (true)
    {
      int j;
      int i;
      try
      {
        JSONObject localJSONObject = (JSONObject)((JSONObject)JSON.parse(paramString)).get("data");
        GroupInfo localGroupInfo = new GroupInfo();
        localGroupInfo.groupId = localJSONObject.getString("groupId");
        localGroupInfo.groupName = localJSONObject.getString("groupName");
        localGroupInfo.userCnt = localJSONObject.getIntValue("num_of_users");
        localGroupInfo.groupDesc = localJSONObject.getString("description");
        localGroupInfo.groupThumb = localJSONObject.getString("thumb");
        JSONArray localJSONArray1 = localJSONObject.getJSONArray("admins");
        if ((localJSONArray1 != null) && (localJSONArray1.size() > 0))
        {
          localGroupInfo.lstAdmins = new ArrayList();
          j = 0;
          if (j < localJSONArray1.size())
          {
            AdminInfo localAdminInfo = _parseAdminInfo(localJSONArray1.getJSONObject(j));
            if (localAdminInfo == null)
              break label278;
            localGroupInfo.lstAdmins.add(localAdminInfo);
            break label278;
          }
        }
        JSONArray localJSONArray2 = localJSONObject.getJSONArray("managers");
        if ((localJSONArray2 != null) && (localJSONArray2.size() > 0))
        {
          localGroupInfo.lstManagers = new ArrayList();
          i = 0;
          if (i < localJSONArray2.size())
          {
            UserInfo localUserInfo = _parseUserInfo(localJSONArray2.getJSONObject(i));
            if (localUserInfo == null)
              break label284;
            localGroupInfo.lstManagers.add(localUserInfo);
            break label284;
          }
        }
        Result localResult = new Result(true, localGroupInfo);
        return localResult;
      }
      catch (Exception localException)
      {
      }
      return null;
      label278: j++;
      continue;
      label284: i++;
    }
  }

  private Result parseGroupUsers(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
      try
      {
        JSONArray localJSONArray = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        ArrayList localArrayList = new ArrayList();
        if ((localJSONArray != null) && (localJSONArray.size() > 0))
        {
          int i = localJSONArray.size();
          int j = 0;
          if (j < i)
          {
            JSONObject localJSONObject = localJSONArray.getJSONObject(j);
            UserInfo localUserInfo = new UserInfo();
            localUserInfo.userKey = localJSONObject.getString("userId");
            localUserInfo.snsInfo.sns_name = localJSONObject.getString("userName");
            localUserInfo.snsInfo.age = localJSONObject.getIntValue("age");
            localUserInfo.snsInfo.signature = localJSONObject.getString("signature");
            localUserInfo.snsInfo.sns_gender = localJSONObject.getString("gender");
            localUserInfo.snsInfo.sns_avatar = localJSONObject.getString("avatar");
            String str1 = localJSONObject.getString("is_blocked");
            if ((str1 != null) && (!str1.equalsIgnoreCase("")))
              if (Integer.valueOf(str1).intValue() != 0)
                break label261;
            label261: for (localUserInfo.isBlocked = false; ; localUserInfo.isBlocked = true)
            {
              String str2 = localJSONObject.getString("level");
              if ((str2 != null) && (!str2.equalsIgnoreCase("")))
                localUserInfo.level = Integer.valueOf(str2).intValue();
              localArrayList.add(localUserInfo);
              j++;
              break;
            }
          }
          if ((localArrayList == null) || (localArrayList.size() <= 0));
        }
        Result localResult = new Result(true, localArrayList);
        return localResult;
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  private Result parseHotSearchKeywords(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
      try
      {
        ArrayList localArrayList = new ArrayList();
        JSONArray localJSONArray = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        if (localJSONArray != null)
        {
          for (int i = 0; i < localJSONArray.size(); i++)
          {
            JSONObject localJSONObject = (JSONObject)localJSONArray.get(i);
            SearchHotKeyword localSearchHotKeyword = new SearchHotKeyword();
            localSearchHotKeyword.keyword = localJSONObject.getString("name");
            localSearchHotKeyword.searchCnt = localJSONObject.getIntValue("count");
            localArrayList.add(localSearchHotKeyword);
          }
          Result localResult = new Result(true, localArrayList);
          return localResult;
        }
      }
      catch (Exception localException)
      {
        return null;
      }
    return null;
  }

  private Result parseIMBaseUserInfo(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
      try
      {
        JSONObject localJSONObject = ((JSONObject)JSON.parse(paramString)).getJSONObject("data");
        UserInfo localUserInfo = new UserInfo();
        localUserInfo.userId = localJSONObject.getString("userId");
        localUserInfo.userKey = localUserInfo.userId;
        localUserInfo.snsInfo.sns_avatar = localJSONObject.getString("avatar");
        localUserInfo.snsInfo.signature = localJSONObject.getString("signature");
        localUserInfo.snsInfo.sns_gender = localJSONObject.getString("gender");
        String str1 = localJSONObject.getString("is_blocked");
        if ((str1 != null) && (!str1.equalsIgnoreCase("")))
          if (Integer.valueOf(str1).intValue() != 0)
            break label191;
        label191: for (localUserInfo.isBlocked = false; ; localUserInfo.isBlocked = true)
        {
          String str2 = localJSONObject.getString("level");
          if ((str2 != null) && (!str2.equalsIgnoreCase("")))
            localUserInfo.level = Integer.valueOf(str2).intValue();
          if (localUserInfo == null)
            break;
          return new Result(true, localUserInfo);
        }
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  private Result parseIMHistory(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    while (true)
    {
      int i;
      try
      {
        JSONObject localJSONObject = (JSONObject)JSON.parse(paramString);
        ArrayList localArrayList = new ArrayList();
        JSONArray localJSONArray = localJSONObject.getJSONArray("data");
        if (localJSONArray != null)
        {
          i = 0;
          if (i < localJSONArray.size())
          {
            String str = localJSONArray.getJSONObject(i).getString("message");
            if (str == null)
              break label130;
            IMMessage localIMMessage = new IMMessage();
            IMMessage.parseEvent(str, localIMMessage);
            if (localIMMessage == null)
              break label130;
            localArrayList.add(localIMMessage);
            break label130;
          }
          Result localResult = new Result(true, localArrayList);
          return localResult;
        }
      }
      catch (Exception localException)
      {
      }
      return null;
      label130: i++;
    }
  }

  private Result parseIMServer(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
      try
      {
        Result localResult = new Result(true, (String)((JSONObject)JSON.parse(paramString)).get("server"));
        return localResult;
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  private Result parseIPLocation(String paramString)
  {
    if (paramString != null)
      try
      {
        QTLocation localQTLocation = new QTLocation();
        JSONObject localJSONObject = (JSONObject)JSON.parse(paramString);
        localQTLocation.city = localJSONObject.getString("city");
        localQTLocation.region = localJSONObject.getString("region");
        localQTLocation.ip = localJSONObject.getString("ip");
        Result localResult = new Result(true, localQTLocation);
        return localResult;
      }
      catch (Exception localException)
      {
        return null;
      }
    return null;
  }

  private Result parseJdAd(String paramString)
  {
    if (paramString == null)
      return null;
    try
    {
      JSONArray localJSONArray = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
      ArrayList localArrayList = new ArrayList();
      for (int i = 0; i < localJSONArray.size(); i++)
        localArrayList.add(new CommodityInfo());
      Result localResult = new Result(true, localArrayList);
      return localResult;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  private Result parseLinkInfo(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
      try
      {
        RecommendItemNode localRecommendItemNode = _parseRecommendNode(((JSONObject)JSON.parse(paramString)).getJSONObject("data"));
        if (localRecommendItemNode != null)
        {
          Result localResult = new Result(true, localRecommendItemNode);
          return localResult;
        }
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  private Result parseListActivities(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    while (true)
    {
      int i;
      try
      {
        JSONArray localJSONArray = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        ArrayList localArrayList = new ArrayList();
        if (localJSONArray != null)
        {
          i = 0;
          if (i < localJSONArray.size())
          {
            ActivityNode localActivityNode = _parseActivity(localJSONArray.getJSONObject(i));
            if (localActivityNode == null)
              break label100;
            localArrayList.add(localActivityNode);
            break label100;
          }
          Result localResult = new Result(true, localArrayList);
          return localResult;
        }
      }
      catch (Exception localException)
      {
      }
      return null;
      label100: i++;
    }
  }

  private Result parseListCategories(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    while (true)
    {
      int i;
      try
      {
        JSONArray localJSONArray = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        if (localJSONArray != null)
        {
          ArrayList localArrayList = new ArrayList();
          i = 0;
          if (i < localJSONArray.size())
          {
            JSONObject localJSONObject = localJSONArray.getJSONObject(i);
            if (localJSONObject == null)
              break label149;
            CategoryNode localCategoryNode = new CategoryNode();
            localCategoryNode.categoryId = localJSONObject.getIntValue("id");
            localCategoryNode.name = localJSONObject.getString("name");
            localCategoryNode.sectionId = localJSONObject.getIntValue("section_id");
            localCategoryNode.type = 1;
            localArrayList.add(localCategoryNode);
            break label149;
          }
          Result localResult = new Result(true, localArrayList);
          return localResult;
        }
      }
      catch (Exception localException)
      {
      }
      return null;
      label149: i++;
    }
  }

  private Result parseLiveCategory(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    while (true)
    {
      int i;
      try
      {
        ArrayList localArrayList = new ArrayList();
        JSONArray localJSONArray1 = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        if (localJSONArray1 != null)
        {
          i = 0;
          int k;
          if (i < localJSONArray1.size())
          {
            JSONObject localJSONObject1 = localJSONArray1.getJSONObject(i);
            JSONArray localJSONArray2 = localJSONObject1.getJSONArray("sections");
            String str1 = localJSONObject1.getString("group_name");
            int j = 0;
            if (j >= localJSONArray2.size())
              break label372;
            JSONObject localJSONObject2 = localJSONArray2.getJSONObject(j);
            CategoryNode localCategoryNode = new CategoryNode();
            localCategoryNode.type = 0;
            String str2;
            if (str1 != null)
            {
              if (str1.equalsIgnoreCase("地区"))
                localCategoryNode.type = 4;
            }
            else
            {
              localCategoryNode.name = localJSONObject2.getString("name");
              localCategoryNode.sectionId = localJSONObject2.getIntValue("section_id");
              JSONObject localJSONObject3 = localJSONObject2.getJSONObject("redirect");
              if (localJSONObject3 == null)
                continue;
              Integer localInteger = Integer.valueOf(localJSONObject3.getIntValue("category_id"));
              if (localInteger != null)
                localCategoryNode.categoryId = localInteger.intValue();
              JSONArray localJSONArray3 = localJSONObject3.getJSONArray("attributes");
              if (localJSONArray3 == null)
                continue;
              str2 = "";
              k = 0;
              if (k >= localJSONArray3.size())
                continue;
              str2 = str2 + localJSONArray3.getIntValue(k);
              if (k >= -1 + localJSONArray3.size())
                continue;
              str2 = str2 + "/";
              continue;
            }
            if (!str1.equalsIgnoreCase("内容"))
              continue;
            localCategoryNode.type = 3;
            continue;
            localCategoryNode.mAttributesPath = str2;
            localArrayList.add(localCategoryNode);
            j++;
            continue;
          }
          else
          {
            Result localResult = new Result(true, localArrayList);
            return localResult;
          }
          k++;
          continue;
        }
      }
      catch (Exception localException)
      {
      }
      return null;
      label372: i++;
    }
  }

  private Result parseLiveChannelInfo(String paramString)
  {
    if (paramString != null);
    return null;
  }

  private Result parseLiveChannels(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    return null;
  }

  private Result parseLiveProgramSchedule(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    while (true)
    {
      int i;
      int j;
      try
      {
        JSONObject localJSONObject = ((JSONObject)JSON.parse(paramString)).getJSONObject("data");
        ProgramScheduleList localProgramScheduleList = new ProgramScheduleList(0);
        i = 1;
        localObject1 = null;
        if (i <= 7)
        {
          JSONArray localJSONArray = localJSONObject.getJSONArray(String.valueOf(i));
          if ((localJSONArray == null) || (localJSONArray.size() <= 0))
            break label231;
          ProgramSchedule localProgramSchedule = new ProgramSchedule();
          localProgramSchedule.dayOfWeek = i;
          localProgramSchedule.mLstProgramNodes = new ArrayList();
          j = 0;
          if (j < localJSONArray.size())
          {
            localObject2 = _parseLiveProgramNode(localJSONArray.getJSONObject(j), i);
            if (localObject2 == null)
              break label217;
            if (localObject1 != null)
            {
              localObject1.nextSibling = ((Node)localObject2);
              ((ProgramNode)localObject2).prevSibling = localObject1;
            }
            localProgramSchedule.mLstProgramNodes.add(localObject2);
            break label221;
          }
          localProgramScheduleList.mLstProgramsScheduleNodes.add(localProgramSchedule);
          break label231;
        }
        if (localProgramScheduleList.mLstProgramsScheduleNodes.size() > 0)
        {
          Result localResult = new Result(true, localProgramScheduleList);
          return localResult;
        }
      }
      catch (Exception localException)
      {
      }
      return null;
      label217: Object localObject2 = localObject1;
      label221: j++;
      Object localObject1 = localObject2;
      continue;
      label231: i++;
    }
  }

  private Result parseLocalRecommendInfo(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    while (true)
    {
      int i;
      int j;
      String str3;
      int i2;
      int i1;
      try
      {
        JSONArray localJSONArray1 = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        if (localJSONArray1 != null)
        {
          RecommendCategoryNode localRecommendCategoryNode = new RecommendCategoryNode();
          i = 0;
          if (i < localJSONArray1.size())
          {
            JSONObject localJSONObject1 = localJSONArray1.getJSONObject(i);
            if (localJSONObject1 == null)
              break label437;
            String str1 = localJSONObject1.getString("name");
            String str2 = localJSONObject1.getString("brief_name");
            if (str1 == null)
              break label437;
            j = localJSONObject1.getIntValue("section_id");
            str3 = "";
            JSONObject localJSONObject2 = localJSONObject1.getJSONObject("redirect");
            if (localJSONObject2 == null)
              break label400;
            String str4 = localJSONObject2.getString("redirect_type");
            if ((str4 == null) || (!str4.equalsIgnoreCase("section")))
              break label393;
            k = localJSONObject2.getIntValue("section_id");
            m = localJSONObject2.getIntValue("category_id");
            JSONArray localJSONArray2 = localJSONObject2.getJSONArray("attributes");
            if (localJSONArray2 == null)
              break label420;
            i2 = 0;
            if (i2 >= localJSONArray2.size())
              break label420;
            str3 = str3 + localJSONArray2.getIntValue(i2);
            if (i2 >= -1 + localJSONArray2.size())
              break label414;
            str3 = str3 + "/";
            break label414;
            JSONArray localJSONArray3 = localJSONObject1.getJSONArray("recommends");
            if (localJSONArray3 == null)
              break label437;
            i1 = 0;
            if (i1 >= localJSONArray3.size())
              break label437;
            RecommendItemNode localRecommendItemNode = _parseRecommendItemNode(localJSONArray3.getJSONObject(i1), false);
            if (localRecommendItemNode == null)
              break label431;
            if ((str5 != null) && (!str5.equalsIgnoreCase("")))
              localRecommendItemNode.mAttributesPath = str5;
            if (m != 0)
              localRecommendItemNode.mCategoryId = m;
            localRecommendItemNode.sectionId = n;
            if (str1 != null)
              localRecommendItemNode.belongName = str1;
            localRecommendItemNode.briefName = str2;
            localRecommendCategoryNode.insertRecCategory(localRecommendItemNode, 1);
            break label431;
          }
          Result localResult = new Result(true, localRecommendCategoryNode);
          return localResult;
        }
      }
      catch (Exception localException)
      {
      }
      return null;
      label393: int k = j;
      continue;
      label400: String str5 = str3;
      int n = j;
      int m = 0;
      continue;
      label414: i2++;
      continue;
      label420: n = k;
      str5 = str3;
      continue;
      label431: i1++;
      continue;
      label437: i++;
    }
  }

  private Result parseMediaCenter(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
      try
      {
        JSONObject localJSONObject1 = ((JSONObject)JSON.parse(paramString)).getJSONObject("data");
        JSONObject localJSONObject2 = localJSONObject1.getJSONObject("radiostations_hls");
        JSONObject localJSONObject3 = localJSONObject1.getJSONObject("radiostations_download");
        JSONObject localJSONObject4 = localJSONObject1.getJSONObject("storedaudio_m4a");
        MediaCenter localMediaCenter = new MediaCenter();
        localMediaCenter.mapMediaCenters = new HashMap();
        List localList1 = _parseMediaCenter(localJSONObject2, 0);
        if ((localList1 != null) && (localList1.size() > 0))
          localMediaCenter.mapMediaCenters.put("radiohls", localList1);
        List localList2 = _parseMediaCenter(localJSONObject3, 0);
        if ((localList2 != null) && (localList2.size() > 0))
          localMediaCenter.mapMediaCenters.put("radiodownload", localList2);
        List localList3 = _parseMediaCenter(localJSONObject4, 1);
        if ((localList3 != null) && (localList3.size() > 0))
          localMediaCenter.mapMediaCenters.put("virutalchannel", localList3);
        Result localResult = new Result(true, localMediaCenter);
        return localResult;
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  private Result parseMessageList(String paramString)
  {
    if (paramString != null);
    while (true)
    {
      int i;
      try
      {
        JSONArray localJSONArray = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        i = 0;
        if (i < localJSONArray.size())
        {
          JSONObject localJSONObject = localJSONArray.getJSONObject(i);
          String str1 = localJSONObject.getString("type");
          if ((str1 == null) || (str1.equalsIgnoreCase("add_user_friend_req")) || (!str1.equalsIgnoreCase("message")))
            break label129;
          String str2 = localJSONObject.getString("info");
          if ((str2 == null) || (str2.equalsIgnoreCase("")))
            break label129;
          URLDecoder.decode(str2, "UTF-8");
        }
      }
      catch (Exception localException)
      {
        return null;
      }
      return new Result(true, null);
      return null;
      label129: i++;
    }
  }

  private List<Node> parseMiniFav(JSONArray paramJSONArray)
  {
    ArrayList localArrayList;
    int i;
    if ((paramJSONArray != null) && (paramJSONArray.size() > 0))
    {
      localArrayList = new ArrayList();
      i = 0;
    }
    while (true)
    {
      if (i < paramJSONArray.size());
      try
      {
        JSONObject localJSONObject = paramJSONArray.getJSONObject(i);
        MiniFavNode localMiniFavNode = new MiniFavNode();
        localMiniFavNode.parentId = Integer.valueOf(localJSONObject.getString("parentid")).intValue();
        localMiniFavNode.id = Integer.valueOf(localJSONObject.getString("id")).intValue();
        localMiniFavNode.channelType = localJSONObject.getIntValue("type");
        localMiniFavNode.name = localJSONObject.getString("name");
        localMiniFavNode.categoryId = Integer.valueOf(localJSONObject.getString("catid")).intValue();
        localArrayList.add(localMiniFavNode);
        label135: i++;
        continue;
        return localArrayList;
        return null;
      }
      catch (Exception localException)
      {
        break label135;
      }
    }
  }

  private List<Node> parseMiniPlay(JSONObject paramJSONObject, String paramString)
  {
    if ((paramJSONObject != null) && (paramString != null));
    try
    {
      JSONArray localJSONArray = paramJSONObject.getJSONArray(paramString);
      ArrayList localArrayList1 = new ArrayList();
      int i = -16 + localJSONArray.size();
      int j;
      ArrayList localArrayList2;
      int k;
      long l2;
      if (i < 0)
      {
        j = 0;
        localArrayList2 = new ArrayList();
        long l1 = System.currentTimeMillis() / 1000L;
        k = -1 + localJSONArray.size();
        l2 = l1;
      }
      for (int m = k; ; m--)
        if (m > j)
        {
          JSONObject localJSONObject = localJSONArray.getJSONObject(m);
          MiniPlayNode localMiniPlayNode = new MiniPlayNode();
          localMiniPlayNode.name = localJSONObject.getString("name");
          localMiniPlayNode.time = localJSONObject.getLong("time").longValue();
          if (!findSameValue(localArrayList2, localMiniPlayNode.name))
          {
            localArrayList2.add(localMiniPlayNode.name);
            if (l2 - localMiniPlayNode.time >= 60L)
            {
              l2 = localMiniPlayNode.time;
              localMiniPlayNode.categoryId = localJSONObject.getString("categoryid");
              localMiniPlayNode.id = localJSONObject.getString("id");
              String str = localJSONObject.getString("type");
              if (str.equalsIgnoreCase("novel"))
                localMiniPlayNode.contentType = 2;
              while (true)
              {
                localArrayList1.add(localMiniPlayNode);
                break;
                if (str.equalsIgnoreCase("channel"))
                  localMiniPlayNode.contentType = 0;
                else if (str.equalsIgnoreCase("podcast"))
                  localMiniPlayNode.contentType = 1;
              }
            }
          }
        }
        else
        {
          return localArrayList1;
          return null;
          j = i;
          break;
        }
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  private Result parseNewRecommendV2Banner(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
      try
      {
        RecommendCategoryNode localRecommendCategoryNode = new RecommendCategoryNode();
        _parseNewRecommendV2Banner((JSONObject)JSON.parse(paramString), localRecommendCategoryNode.getLstBanner());
        Result localResult = new Result(true, localRecommendCategoryNode);
        return localResult;
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  private Result parseNewSearch(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    while (true)
    {
      int i;
      try
      {
        JSONArray localJSONArray1 = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        SearchNode localSearchNode = new SearchNode();
        ArrayList localArrayList1 = new ArrayList();
        i = 0;
        int j;
        if (i < localJSONArray1.size())
        {
          JSONObject localJSONObject1 = localJSONArray1.getJSONObject(i);
          String str1 = localJSONObject1.getString("groupValue");
          JSONArray localJSONArray2 = localJSONObject1.getJSONObject("doclist").getJSONArray("docs");
          ArrayList localArrayList2 = new ArrayList();
          j = 0;
          if (j < localJSONArray2.size())
          {
            JSONObject localJSONObject2 = localJSONArray2.getJSONObject(j);
            String str2 = localJSONObject2.getString("type");
            if (str2 != null)
            {
              SearchItemNode localSearchItemNode = new SearchItemNode();
              if (str2.equalsIgnoreCase("channel_ondemand"))
              {
                localSearchItemNode.groupType = 2;
                localSearchItemNode.channelType = 1;
                localSearchItemNode.channelId = localJSONObject2.getIntValue("id");
                localSearchItemNode.categoryId = localJSONObject2.getIntValue("category_id");
                localSearchItemNode.cover = localJSONObject2.getString("cover");
                localSearchItemNode.name = localJSONObject2.getString("title");
                localSearchItemNode.catName = localJSONObject2.getString("category_name");
                localSearchItemNode.desc = localJSONObject2.getString("description");
                localSearchItemNode.totalScore = localJSONObject2.getDoubleValue("totalscore");
                localSearchItemNode.cName = localSearchItemNode.name;
                localSearchItemNode.star = localJSONObject2.getIntValue("star");
                if (localSearchItemNode.channelId != 0)
                {
                  localArrayList2.add(localSearchItemNode);
                  localArrayList1.add(localSearchItemNode);
                }
              }
              else
              {
                if (str2.equalsIgnoreCase("program_ondemand"))
                {
                  localSearchItemNode.groupType = 1;
                  localSearchItemNode.channelType = 1;
                  localSearchItemNode.programId = localJSONObject2.getIntValue("id");
                  localSearchItemNode.channelId = localJSONObject2.getIntValue("parent_id");
                  localSearchItemNode.categoryId = 0;
                  localSearchItemNode.cover = localJSONObject2.getString("cover");
                  localSearchItemNode.name = localJSONObject2.getString("title");
                  localSearchItemNode.cName = localJSONObject2.getString("parent_name");
                  localSearchItemNode.totalScore = localJSONObject2.getDoubleValue("totalscore");
                  continue;
                }
                if (str2.equalsIgnoreCase("channel_live"))
                {
                  localSearchItemNode.groupType = 0;
                  localSearchItemNode.channelType = 0;
                  localSearchItemNode.channelId = localJSONObject2.getIntValue("id");
                  localSearchItemNode.categoryId = localJSONObject2.getIntValue("category_id");
                  localSearchItemNode.cover = localJSONObject2.getString("cover");
                  localSearchItemNode.name = localJSONObject2.getString("title");
                  localSearchItemNode.catName = localJSONObject2.getString("category_name");
                  localSearchItemNode.totalScore = localJSONObject2.getDoubleValue("totalscore");
                  localSearchItemNode.freqs = localJSONObject2.getString("freqs");
                  localSearchItemNode.cName = localSearchItemNode.name;
                  localSearchItemNode.audience_count = localJSONObject2.getIntValue("audience_count");
                  continue;
                }
                if (!str2.equalsIgnoreCase("people_podcaster"))
                  continue;
                localSearchItemNode.groupType = 3;
                localSearchItemNode.podcasterId = localJSONObject2.getIntValue("id");
                localSearchItemNode.podcasterTitle = localJSONObject2.getString("title");
                localSearchItemNode.podcasterDescription = localJSONObject2.getString("description");
                localSearchItemNode.podcasterFan_num = localJSONObject2.getIntValue("fan_num");
                localSearchItemNode.podcasterNickName = localJSONObject2.getString("nickname");
                localSearchItemNode.podcasterAvatar = localJSONObject2.getString("avatar");
                localSearchItemNode.totalScore = localJSONObject2.getDoubleValue("totalscore");
                localSearchItemNode.name = localSearchItemNode.podcasterNickName;
                localSearchItemNode.cover = localSearchItemNode.podcasterAvatar;
                localSearchItemNode.catName = localSearchItemNode.podcasterDescription;
                localSearchItemNode.channelId = localSearchItemNode.podcasterId;
                continue;
              }
            }
          }
          else
          {
            Collections.sort(localArrayList2, new NewSearchComparator());
            if (str1.equalsIgnoreCase("channel_ondemand"))
            {
              localSearchNode.setResult(1, localArrayList2);
              break label843;
            }
            if (str1.equalsIgnoreCase("program_ondemand"))
            {
              localSearchNode.setResult(4, localArrayList2);
              break label843;
            }
            if (str1.equalsIgnoreCase("channel_live"))
            {
              localSearchNode.setResult(3, localArrayList2);
              break label843;
            }
            if (!str1.equalsIgnoreCase("people_podcaster"))
              break label843;
            localSearchNode.setResult(2, localArrayList2);
            break label843;
          }
        }
        else
        {
          Collections.sort(localArrayList1, new NewSearchComparator());
          localSearchNode.setResult(0, localArrayList1);
          Result localResult = new Result(true, localSearchNode);
          return localResult;
        }
        j++;
        continue;
      }
      catch (Exception localException)
      {
      }
      return null;
      label843: i++;
    }
  }

  private Result parseNewSearchHotresults(String paramString)
  {
    ArrayList localArrayList;
    if (paramString != null)
      localArrayList = new ArrayList();
    while (true)
    {
      Navigation localNavigation;
      int i;
      String str1;
      String str2;
      String str3;
      String str4;
      String str5;
      try
      {
        JSONArray localJSONArray = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        if (localJSONArray.size() == 0)
          return new Result(true, new ListData(localArrayList, new Navigation(0, 20, 0)));
        localNavigation = new Navigation(1, 20, 0);
        i = 0;
        if (i >= localJSONArray.size())
          break label316;
        JSONObject localJSONObject = localJSONArray.getJSONObject(i);
        str1 = localJSONObject.getString("cid");
        str2 = localJSONObject.getString("name");
        str3 = localJSONObject.getString("catid");
        str4 = localJSONObject.getString("type");
        str5 = localJSONObject.getString("typekey");
        String str6 = "";
        if (localJSONObject.containsKey("catname"))
          str6 = localJSONObject.getString("catname");
        if (str5.equalsIgnoreCase("channel"))
          localArrayList.add(new SearchChannel("", str1, str2, str4, "电台", "", "", str3, str6));
        else if (str5.equalsIgnoreCase("novel"))
          localArrayList.add(new SearchOndemand("", str1, str2, str4, "小说", "", "", str3));
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      if (str5.equalsIgnoreCase("podcast"))
      {
        localArrayList.add(new SearchOndemand("", str1, str2, str4, "播客", "", "", str3));
        break label337;
        label316: return new Result(true, new ListData(localArrayList, localNavigation));
        return null;
      }
      label337: i++;
    }
  }

  private Result parseNewSearchRecommend(String paramString)
  {
    ArrayList localArrayList;
    if (paramString != null)
      localArrayList = new ArrayList();
    while (true)
    {
      int j;
      try
      {
        JSONObject localJSONObject1 = (JSONObject)JSON.parse(paramString);
        JSONArray localJSONArray1 = localJSONObject1.getJSONArray("data");
        if (localJSONArray1.size() == 0)
          return new Result(true, new ListData(localArrayList, new Navigation(0, 20, 0)));
        new Navigation(localJSONObject1.getIntValue("cur"), 20, 0);
        int i = 0;
        if (i < localJSONArray1.size())
        {
          JSONObject localJSONObject2 = localJSONArray1.getJSONObject(i);
          JSONArray localJSONArray2 = localJSONObject2.getJSONArray("items");
          j = 0;
          if (j < localJSONArray2.size())
          {
            JSONObject localJSONObject3 = localJSONArray2.getJSONObject(j);
            String str1 = localJSONObject3.getString("cid");
            String str2 = localJSONObject3.getString("name");
            String str3 = localJSONObject3.getString("catid");
            String str4 = localJSONObject3.getString("type");
            String str5 = "";
            if (localJSONObject2.containsKey("catname"))
              str5 = localJSONObject3.getString("catname");
            if (str4.equalsIgnoreCase("channel"))
            {
              localArrayList.add(new SearchChannel("", str1, str2, str4, "电台", "", "", str3, str5));
              break label305;
            }
            if (str4.equalsIgnoreCase("novel"))
              break label305;
            boolean bool = str4.equalsIgnoreCase("podcast");
            if (!bool)
              break label305;
            break label305;
          }
          i++;
          continue;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      return new Result(true, localArrayList);
      return null;
      label305: j++;
    }
  }

  private Result parseNewSearchSuggestion(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    while (true)
    {
      int i;
      try
      {
        JSONArray localJSONArray1 = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        ArrayList localArrayList = new ArrayList();
        i = 0;
        int j;
        if (i < localJSONArray1.size())
        {
          JSONArray localJSONArray2 = localJSONArray1.getJSONObject(i).getJSONObject("doclist").getJSONArray("docs");
          j = 0;
          if (j >= localJSONArray2.size())
            break label694;
          JSONObject localJSONObject = localJSONArray2.getJSONObject(j);
          String str = localJSONObject.getString("type");
          if (str != null)
          {
            SearchItemNode localSearchItemNode = new SearchItemNode();
            if (str.equalsIgnoreCase("channel_ondemand"))
            {
              localSearchItemNode.groupType = 2;
              localSearchItemNode.channelType = 1;
              localSearchItemNode.channelId = localJSONObject.getIntValue("id");
              localSearchItemNode.categoryId = localJSONObject.getIntValue("category_id");
              localSearchItemNode.cover = localJSONObject.getString("cover");
              localSearchItemNode.name = localJSONObject.getString("title");
              localSearchItemNode.catName = localJSONObject.getString("category_name");
              localSearchItemNode.desc = localJSONObject.getString("description");
              localSearchItemNode.totalScore = localJSONObject.getDoubleValue("totalscore");
              localSearchItemNode.cName = localSearchItemNode.name;
              localSearchItemNode.star = localJSONObject.getIntValue("star");
              if (localSearchItemNode.channelId != 0)
                localArrayList.add(localSearchItemNode);
            }
            else
            {
              if (str.equalsIgnoreCase("program_ondemand"))
              {
                localSearchItemNode.groupType = 1;
                localSearchItemNode.channelType = 1;
                localSearchItemNode.programId = localJSONObject.getIntValue("id");
                localSearchItemNode.channelId = localJSONObject.getIntValue("parent_id");
                localSearchItemNode.categoryId = 0;
                localSearchItemNode.cover = localJSONObject.getString("cover");
                localSearchItemNode.name = localJSONObject.getString("title");
                localSearchItemNode.cName = localJSONObject.getString("parent_name");
                localSearchItemNode.totalScore = localJSONObject.getDoubleValue("totalscore");
                continue;
              }
              if (str.equalsIgnoreCase("channel_live"))
              {
                localSearchItemNode.groupType = 0;
                localSearchItemNode.channelType = 0;
                localSearchItemNode.channelId = localJSONObject.getIntValue("id");
                localSearchItemNode.categoryId = localJSONObject.getIntValue("category_id");
                localSearchItemNode.cover = localJSONObject.getString("cover");
                localSearchItemNode.name = localJSONObject.getString("title");
                localSearchItemNode.catName = localJSONObject.getString("category_name");
                localSearchItemNode.totalScore = localJSONObject.getDoubleValue("totalscore");
                localSearchItemNode.freqs = localJSONObject.getString("freqs");
                localSearchItemNode.cName = localSearchItemNode.name;
                localSearchItemNode.audience_count = localJSONObject.getIntValue("audience_count");
                continue;
              }
              if (!str.equalsIgnoreCase("people_podcaster"))
                continue;
              localSearchItemNode.groupType = 3;
              localSearchItemNode.podcasterId = localJSONObject.getIntValue("id");
              localSearchItemNode.podcasterTitle = localJSONObject.getString("title");
              localSearchItemNode.podcasterDescription = localJSONObject.getString("description");
              localSearchItemNode.podcasterFan_num = localJSONObject.getIntValue("fan_num");
              localSearchItemNode.podcasterNickName = localJSONObject.getString("nickname");
              localSearchItemNode.podcasterAvatar = localJSONObject.getString("avatar");
              localSearchItemNode.totalScore = localJSONObject.getDoubleValue("totalscore");
              localSearchItemNode.name = localSearchItemNode.podcasterNickName;
              localSearchItemNode.cover = localSearchItemNode.podcasterAvatar;
              localSearchItemNode.catName = localSearchItemNode.podcasterDescription;
              localSearchItemNode.channelId = localSearchItemNode.podcasterId;
              continue;
            }
          }
        }
        else
        {
          Collections.sort(localArrayList, new NewSearchComparator());
          Result localResult = new Result(true, localArrayList);
          return localResult;
        }
        j++;
        continue;
      }
      catch (Exception localException)
      {
      }
      return null;
      label694: i++;
    }
  }

  private Result parsePodcasterBaseInfo(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
      try
      {
        JSONObject localJSONObject = ((JSONObject)JSON.parse(paramString)).getJSONObject("data");
        if (localJSONObject == null)
          return null;
        UserInfo localUserInfo = _parsePodcaster(localJSONObject);
        if (localUserInfo != null)
        {
          Result localResult = new Result(true, localUserInfo);
          return localResult;
        }
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  private Result parsePodcasterChannels(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    while (true)
    {
      int i;
      try
      {
        JSONArray localJSONArray = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        ArrayList localArrayList = new ArrayList();
        i = 0;
        if (i < localJSONArray.size())
        {
          ChannelNode localChannelNode = _parseChannelNode(localJSONArray.getJSONObject(i));
          if (localChannelNode == null)
            break label96;
          localArrayList.add(localChannelNode);
          break label96;
        }
        Result localResult = new Result(true, localArrayList);
        return localResult;
      }
      catch (Exception localException)
      {
      }
      return null;
      label96: i++;
    }
  }

  private Result parsePodcasterDetailInfo(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    return null;
  }

  private Result parsePodcasterLatestInfo(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    while (true)
    {
      int i;
      try
      {
        JSONArray localJSONArray = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        ArrayList localArrayList = new ArrayList();
        i = 0;
        if (i < localJSONArray.size())
        {
          JSONObject localJSONObject1 = localJSONArray.getJSONObject(i);
          if (localJSONObject1 == null)
            break label147;
          JSONObject localJSONObject2 = localJSONObject1.getJSONObject("program");
          if (localJSONObject2 == null)
            break label147;
          ProgramNode localProgramNode = _parseVirtualProgramNode(localJSONObject2, 0);
          if (localProgramNode == null)
            break label147;
          localProgramNode.channelId = localJSONObject1.getIntValue("radio_channel_id");
          localProgramNode.setChannelName(localJSONObject1.getString("channel_name"));
          localArrayList.add(localProgramNode);
          break label147;
        }
        Result localResult = new Result(true, localArrayList);
        return localResult;
      }
      catch (Exception localException)
      {
      }
      return null;
      label147: i++;
    }
  }

  private Result parsePostBarrage(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
      try
      {
        JSONObject localJSONObject1 = (JSONObject)JSON.parse(paramString);
        int i = localJSONObject1.getIntValue("errorno");
        JSONObject localJSONObject2 = localJSONObject1.getJSONObject("data");
        IMMessage localIMMessage = new IMMessage();
        localIMMessage.mMessage = localJSONObject2.getString("content");
        localIMMessage.mFromAvatar = localJSONObject2.getString("sender_avatar");
        localIMMessage.mGender = localJSONObject2.getString("sender_gender");
        localIMMessage.publish = localJSONObject2.getIntValue("time_point");
        localIMMessage.mFromName = localJSONObject2.getString("sender_name");
        localIMMessage.mFromID = localJSONObject2.getString("sender_id");
        localIMMessage.mImage = localJSONObject2.getString("image");
        localIMMessage.mLike = localJSONObject2.getIntValue("like");
        if (localJSONObject2.getIntValue("type") == 1);
        for (localIMMessage.chatType = 3; ; localIMMessage.chatType = 2)
        {
          localIMMessage.mMsgId = localJSONObject2.getString("id");
          if (i != 0)
            break;
          return new Result(true, localIMMessage);
        }
      }
      catch (Exception localException)
      {
      }
    else
      return null;
    return null;
  }

  private Result parseProgramBarrage(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    while (true)
    {
      JSONArray localJSONArray2;
      int i;
      try
      {
        JSONObject localJSONObject1 = (JSONObject)JSON.parse(paramString);
        JSONArray localJSONArray1 = localJSONObject1.getJSONArray("text");
        localJSONArray2 = localJSONObject1.getJSONArray("image");
        ArrayList localArrayList1 = new ArrayList();
        ArrayList localArrayList2 = new ArrayList();
        ArrayList localArrayList3 = new ArrayList();
        localArrayList1.add(localArrayList2);
        localArrayList1.add(localArrayList3);
        if (localJSONArray1 == null)
          break label460;
        int j = 0;
        if (j >= localJSONArray1.size())
          break label460;
        IMMessage localIMMessage2 = new IMMessage();
        JSONObject localJSONObject3 = localJSONArray1.getJSONObject(j);
        localIMMessage2.mMessage = localJSONObject3.getString("content");
        localIMMessage2.mFromAvatar = localJSONObject3.getString("sender_avatar");
        localIMMessage2.mGender = localJSONObject3.getString("sender_gender");
        localIMMessage2.publish = localJSONObject3.getIntValue("time_point");
        localIMMessage2.mFromName = localJSONObject3.getString("sender_name");
        localIMMessage2.mFromID = localJSONObject3.getString("sender_id");
        if (localJSONObject3.getIntValue("type") == 1)
        {
          localIMMessage2.chatType = 3;
          localIMMessage2.mMsgId = localJSONObject3.getString("id");
          localArrayList2.add(localIMMessage2);
          j++;
          continue;
        }
        localIMMessage2.chatType = 2;
        continue;
        if (i < localJSONArray2.size())
        {
          IMMessage localIMMessage1 = new IMMessage();
          JSONObject localJSONObject2 = localJSONArray2.getJSONObject(i);
          localIMMessage1.mMessage = localJSONObject2.getString("content");
          localIMMessage1.mFromAvatar = localJSONObject2.getString("sender_avatar");
          localIMMessage1.mGender = localJSONObject2.getString("sender_gender");
          localIMMessage1.publish = localJSONObject2.getIntValue("time_point");
          localIMMessage1.mFromName = localJSONObject2.getString("sender_name");
          localIMMessage1.mFromID = localJSONObject2.getString("sender_id");
          localIMMessage1.mImage = localJSONObject2.getString("image");
          localIMMessage1.mLike = localJSONObject2.getIntValue("like");
          if (localJSONObject2.getIntValue("type") == 1)
          {
            localIMMessage1.chatType = 3;
            localIMMessage1.mMsgId = localJSONObject2.getString("id");
            localArrayList3.add(localIMMessage1);
            i++;
            continue;
          }
          localIMMessage1.chatType = 2;
          continue;
        }
        Result localResult = new Result(true, localArrayList1);
        return localResult;
      }
      catch (Exception localException)
      {
      }
      return null;
      label460: if (localJSONArray2 != null)
        i = 0;
    }
  }

  private Result parseProgramTopics(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
    {
      ArrayList localArrayList = new ArrayList();
      while (true)
      {
        int j;
        try
        {
          JSONArray localJSONArray1 = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
          int i = 0;
          if (i >= localJSONArray1.size())
            break;
          JSONObject localJSONObject1 = localJSONArray1.getJSONObject(i);
          ProgramTopicNode localProgramTopicNode = new ProgramTopicNode();
          localProgramTopicNode.type = localJSONObject1.getString("type");
          localProgramTopicNode.startTime = localJSONObject1.getLong("starttime").longValue();
          localProgramTopicNode.endTime = localJSONObject1.getLong("endtime").longValue();
          localProgramTopicNode.topic = localJSONObject1.getString("content");
          if (localProgramTopicNode.type.equalsIgnoreCase("channel"))
          {
            localProgramTopicNode.channelId = String.valueOf(localJSONObject1.getIntValue("channelid"));
            JSONArray localJSONArray2 = localJSONObject1.getJSONArray("sns");
            if (localJSONArray2 != null)
            {
              j = 0;
              if (j < localJSONArray2.size())
              {
                JSONObject localJSONObject2 = localJSONArray2.getJSONObject(j);
                localProgramTopicNode.platform = localJSONObject2.getString("platform").trim();
                localProgramTopicNode.mid = localJSONObject2.getString("mid").trim();
                if (!localProgramTopicNode.platform.equalsIgnoreCase("weibo"))
                  break label308;
              }
            }
            localArrayList.add(localProgramTopicNode);
            i++;
            continue;
          }
          if (!localProgramTopicNode.type.equalsIgnoreCase("program"))
            continue;
          localProgramTopicNode.channelId = String.valueOf(localJSONObject1.getIntValue("channelid"));
          localProgramTopicNode.programId = String.valueOf(localJSONObject1.getIntValue("programid"));
          continue;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          return null;
        }
        label308: j++;
      }
      return new Result(true, localArrayList);
    }
    return null;
  }

  private Result parseProgramsScheduleByDay(String paramString)
  {
    if (paramString != null);
    return null;
  }

  private Result parsePullCollectionData(String paramString)
  {
    if (paramString != null);
    while (true)
    {
      try
      {
        JSONObject localJSONObject = ((JSONObject)JSON.parse(paramString)).getJSONObject("data");
        if (localJSONObject != null)
        {
          JSONArray localJSONArray = localJSONObject.getJSONArray("favchannels");
          if (localJSONArray == null)
            break label72;
          localList = parseMiniFav(localJSONArray);
          Result localResult = new Result(true, localList);
          return localResult;
        }
      }
      catch (Exception localException)
      {
        return null;
      }
      return new Result(true, null);
      label72: List localList = null;
    }
  }

  private Result parseQtimeConfig(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
      try
      {
        JSONArray localJSONArray = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        HashMap localHashMap1 = new HashMap();
        HashMap localHashMap2 = new HashMap();
        HashMap localHashMap3 = new HashMap();
        HashMap localHashMap4 = new HashMap();
        HashMap localHashMap5 = new HashMap();
        HashMap localHashMap6 = new HashMap();
        HashMap localHashMap7 = new HashMap();
        HashMap localHashMap8 = new HashMap();
        ArrayList localArrayList1 = new ArrayList();
        ArrayList localArrayList2 = new ArrayList();
        for (int i = 0; ; i++)
          if (i < localJSONArray.size())
          {
            JSONObject localJSONObject = localJSONArray.getJSONObject(i);
            if (localJSONObject.getString("type").equals("wsq"))
            {
              localHashMap1.put(Integer.valueOf(Integer.parseInt(localJSONObject.getString("cid"))), localJSONObject.getString("wid"));
              localHashMap2.put(Integer.valueOf(Integer.parseInt(localJSONObject.getString("cid"))), localJSONObject.getString("entry"));
              localHashMap3.put(Integer.valueOf(Integer.parseInt(localJSONObject.getString("cid"))), localJSONObject.getString("entryType"));
            }
            else if (localJSONObject.getString("type").equals("live"))
            {
              localArrayList2.add(Integer.valueOf(Integer.parseInt(localJSONObject.getString("cid"))));
            }
            else if (localJSONObject.getString("type").equals("mall"))
            {
              localHashMap4.put(Integer.valueOf(Integer.parseInt(localJSONObject.getString("program_id"))), localJSONObject.getString("mall"));
            }
            else if (localJSONObject.getString("type").equalsIgnoreCase("channel"))
            {
              H5Bean localH5Bean1 = new H5Bean();
              localH5Bean1.type = 1;
              localH5Bean1.id = Integer.parseInt(localJSONObject.getString("id"));
              localH5Bean1.url = localJSONObject.getString("url");
              String str1 = localJSONObject.getString("abtestNum");
              if ((str1 != null) && (!str1.equalsIgnoreCase("")))
                localH5Bean1.abtestNum = Integer.valueOf(str1).intValue();
              localHashMap5.put(Integer.valueOf(localH5Bean1.id), localH5Bean1);
              ABTest.getInstance().addH5ABTest(localH5Bean1);
            }
            else if (localJSONObject.getString("type").equalsIgnoreCase("category"))
            {
              H5Bean localH5Bean2 = new H5Bean();
              localH5Bean2.type = 2;
              localH5Bean2.id = Integer.parseInt(localJSONObject.getString("id"));
              localH5Bean2.url = localJSONObject.getString("url");
              String str2 = localJSONObject.getString("abtestNum");
              if ((str2 != null) && (!str2.equalsIgnoreCase("")))
                localH5Bean2.abtestNum = Integer.valueOf(str2).intValue();
              localHashMap7.put(Integer.valueOf(localH5Bean2.id), localH5Bean2);
              ABTest.getInstance().addH5ABTest(localH5Bean2);
            }
            else if (localJSONObject.getString("type").equalsIgnoreCase("podcaster"))
            {
              H5Bean localH5Bean3 = new H5Bean();
              localH5Bean3.type = 4;
              localH5Bean3.id = Integer.parseInt(localJSONObject.getString("id"));
              localH5Bean3.url = localJSONObject.getString("url");
              String str3 = localJSONObject.getString("abtestNum");
              if ((str3 != null) && (!str3.equalsIgnoreCase("")))
                localH5Bean3.abtestNum = Integer.valueOf(str3).intValue();
              localHashMap8.put(Integer.valueOf(localH5Bean3.id), localH5Bean3);
              ABTest.getInstance().addH5ABTest(localH5Bean3);
            }
            else if (localJSONObject.getString("type").equalsIgnoreCase("specialtopic"))
            {
              H5Bean localH5Bean4 = new H5Bean();
              localH5Bean4.type = 3;
              localH5Bean4.id = Integer.parseInt(localJSONObject.getString("id"));
              localH5Bean4.url = localJSONObject.getString("url");
              String str4 = localJSONObject.getString("abtestNum");
              if ((str4 != null) && (!str4.equalsIgnoreCase("")))
                localH5Bean4.abtestNum = Integer.valueOf(str4).intValue();
              localHashMap6.put(Integer.valueOf(localH5Bean4.id), localH5Bean4);
              ABTest.getInstance().addH5ABTest(localH5Bean4);
            }
            else if ((!localJSONObject.getString("type").equalsIgnoreCase("programabtest")) && (localJSONObject.getString("type").equalsIgnoreCase("game")))
            {
              GameBean localGameBean = new GameBean();
              localGameBean.title = localJSONObject.getString("title");
              localGameBean.desc = localJSONObject.getString("desc");
              localGameBean.people = Integer.parseInt(localJSONObject.getString("people"));
              localGameBean.thumb = localJSONObject.getString("thumb");
              localGameBean.url = localJSONObject.getString("url");
              String str5 = localJSONObject.getString("share");
              if ((str5 != null) && (str5.equalsIgnoreCase("1")));
              for (localGameBean.hasShared = true; ; localGameBean.hasShared = false)
              {
                localArrayList1.add(localGameBean);
                break;
              }
            }
          }
          else
          {
            HashMap localHashMap9 = new HashMap();
            localHashMap9.put("config", localHashMap1);
            localHashMap9.put("entry", localHashMap2);
            localHashMap9.put("entryType", localHashMap3);
            localHashMap9.put("live", localArrayList2);
            localHashMap9.put("mall", localHashMap4);
            localHashMap9.put("channel", localHashMap5);
            localHashMap9.put("category", localHashMap7);
            localHashMap9.put("podcaster", localHashMap8);
            localHashMap9.put("specialtopic", localHashMap6);
            localHashMap9.put("game", localArrayList1);
            Result localResult = new Result(true, localHashMap9);
            return localResult;
          }
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  private Result parseRadioInfo(String paramString)
  {
    if (paramString != null)
    {
      ArrayList localArrayList = new ArrayList();
      try
      {
        JSONArray localJSONArray = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        for (int i = 0; i < localJSONArray.size(); i++)
        {
          JSONObject localJSONObject = localJSONArray.getJSONObject(i);
          RadioChannelNode localRadioChannelNode = new RadioChannelNode();
          localRadioChannelNode.freq = localJSONObject.getString("freq");
          localRadioChannelNode.freq = String.valueOf(Integer.valueOf((int)(1000.0F * Float.valueOf(localRadioChannelNode.freq).floatValue())));
          localRadioChannelNode.channelId = localJSONObject.getIntValue("id");
          localRadioChannelNode.channelName = localJSONObject.getString("name");
          localArrayList.add(localRadioChannelNode);
        }
      }
      catch (Exception localException)
      {
        return null;
      }
      return new Result(true, localArrayList);
    }
    return null;
  }

  private Result parseRecommendChannel(String paramString)
  {
    if (paramString != null);
    return null;
  }

  private void parseRecommendContent(JSONArray paramJSONArray, List<RecommendItemNode> paramList)
  {
  }

  private Result parseRecommendForCategory(String paramString)
  {
    if (paramString == null)
      return null;
    while (true)
    {
      int i;
      try
      {
        JSONArray localJSONArray = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        if (localJSONArray == null)
          return null;
        ArrayList localArrayList = new ArrayList();
        i = 0;
        if (i < localJSONArray.size())
        {
          RecommendItemNode localRecommendItemNode = _parseRecommendNode(localJSONArray.getJSONObject(i));
          if (localRecommendItemNode != null)
            localArrayList.add(localRecommendItemNode);
        }
        else
        {
          Result localResult = new Result(true, localArrayList);
          return localResult;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      i++;
    }
  }

  private Result parseRecommendInfo(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    while (true)
    {
      int i;
      int i3;
      int j;
      String str3;
      int i2;
      int i1;
      try
      {
        JSONArray localJSONArray1 = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        if (localJSONArray1 != null)
        {
          RecommendCategoryNode localRecommendCategoryNode = new RecommendCategoryNode();
          i = 0;
          if (i < localJSONArray1.size())
          {
            JSONObject localJSONObject1 = localJSONArray1.getJSONObject(i);
            if (localJSONObject1 == null)
              break label507;
            String str1 = localJSONObject1.getString("name");
            String str2 = localJSONObject1.getString("brief_name");
            if (str1 == null)
              break label507;
            if (str1.equalsIgnoreCase("banner"))
            {
              JSONArray localJSONArray4 = localJSONObject1.getJSONArray("recommends");
              if (localJSONArray4 == null)
                break label507;
              i3 = 0;
              if (i3 >= localJSONArray4.size())
                break label507;
              RecommendItemNode localRecommendItemNode2 = _parseRecommendItemNode(localJSONArray4.getJSONObject(i3), true);
              if (localRecommendItemNode2 == null)
                break label478;
              localRecommendCategoryNode.insertRecCategory(localRecommendItemNode2, 0);
              break label478;
            }
            j = localJSONObject1.getIntValue("section_id");
            str3 = "";
            JSONObject localJSONObject2 = localJSONObject1.getJSONObject("redirect");
            if (localJSONObject2 == null)
              break label464;
            String str4 = localJSONObject2.getString("redirect_type");
            if ((str4 == null) || (!str4.equalsIgnoreCase("section")))
              break label457;
            k = localJSONObject2.getIntValue("section_id");
            m = localJSONObject2.getIntValue("category_id");
            JSONArray localJSONArray2 = localJSONObject2.getJSONArray("attributes");
            if (localJSONArray2 == null)
              break label490;
            i2 = 0;
            if (i2 >= localJSONArray2.size())
              break label490;
            str3 = str3 + localJSONArray2.getIntValue(i2);
            if (i2 >= -1 + localJSONArray2.size())
              break label484;
            str3 = str3 + "/";
            break label484;
            JSONArray localJSONArray3 = localJSONObject1.getJSONArray("recommends");
            if (localJSONArray3 == null)
              break label507;
            i1 = 0;
            if (i1 >= localJSONArray3.size())
              break label507;
            RecommendItemNode localRecommendItemNode1 = _parseRecommendItemNode(localJSONArray3.getJSONObject(i1), false);
            if (localRecommendItemNode1 == null)
              break label501;
            if ((str5 != null) && (!str5.equalsIgnoreCase("")))
              localRecommendItemNode1.mAttributesPath = str5;
            if (m != 0)
              localRecommendItemNode1.mCategoryId = m;
            localRecommendItemNode1.sectionId = n;
            localRecommendItemNode1.belongName = str1;
            localRecommendItemNode1.briefName = str2;
            localRecommendCategoryNode.insertRecCategory(localRecommendItemNode1, 1);
            break label501;
          }
          Result localResult = new Result(true, localRecommendCategoryNode);
          return localResult;
        }
      }
      catch (Exception localException)
      {
      }
      return null;
      label457: int k = j;
      continue;
      label464: String str5 = str3;
      int n = j;
      int m = 0;
      continue;
      label478: i3++;
      continue;
      label484: i2++;
      continue;
      label490: n = k;
      str5 = str3;
      continue;
      label501: i1++;
      continue;
      label507: i++;
    }
  }

  private Result parseRecommendNovel(String paramString)
  {
    if (paramString != null)
    {
      ArrayList localArrayList = new ArrayList();
      try
      {
        parseRecommendContent(((JSONObject)JSON.parse(paramString)).getJSONArray("data"), localArrayList);
        return new Result(true, localArrayList);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
    }
    return null;
  }

  private Result parseRecommendPlayingPrograms(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    while (true)
    {
      int i;
      try
      {
        JSONArray localJSONArray = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        ArrayList localArrayList = new ArrayList();
        i = 0;
        if (i < localJSONArray.size())
        {
          RecommendPlayingItemNode localRecommendPlayingItemNode = _parseRecommendPlayingProgram(localJSONArray.getJSONObject(i));
          if (localRecommendPlayingItemNode == null)
            break label96;
          localArrayList.add(localRecommendPlayingItemNode);
          break label96;
        }
        Result localResult = new Result(true, localArrayList);
        return localResult;
      }
      catch (Exception localException)
      {
      }
      return null;
      label96: i++;
    }
  }

  private Result parseRecommendPodcast(String paramString)
  {
    if (paramString != null)
    {
      ArrayList localArrayList = new ArrayList();
      try
      {
        parseRecommendContent(((JSONObject)JSON.parse(paramString)).getJSONArray("data"), localArrayList);
        return new Result(true, localArrayList);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
    }
    return null;
  }

  // ERROR //
  private Result parseRingToneList(String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +388 -> 389
    //   4: new 140	java/util/ArrayList
    //   7: dup
    //   8: invokespecial 141	java/util/ArrayList:<init>	()V
    //   11: astore_2
    //   12: aload_1
    //   13: invokestatic 727	com/alibaba/fastjson/JSON:parse	(Ljava/lang/String;)Ljava/lang/Object;
    //   16: checkcast 19	com/alibaba/fastjson/JSONObject
    //   19: ldc_w 486
    //   22: invokevirtual 145	com/alibaba/fastjson/JSONObject:getJSONArray	(Ljava/lang/String;)Lcom/alibaba/fastjson/JSONArray;
    //   25: astore 4
    //   27: iconst_0
    //   28: istore 5
    //   30: iload 5
    //   32: aload 4
    //   34: invokevirtual 150	com/alibaba/fastjson/JSONArray:size	()I
    //   37: if_icmpge +342 -> 379
    //   40: aload 4
    //   42: iload 5
    //   44: invokevirtual 154	com/alibaba/fastjson/JSONArray:getJSONObject	(I)Lcom/alibaba/fastjson/JSONObject;
    //   47: astore 6
    //   49: new 1699	fm/qingting/qtradio/model/RingToneNode
    //   52: dup
    //   53: invokespecial 1700	fm/qingting/qtradio/model/RingToneNode:<init>	()V
    //   56: astore 7
    //   58: aload 7
    //   60: aload 6
    //   62: ldc_w 1702
    //   65: invokevirtual 32	com/alibaba/fastjson/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   68: putfield 1705	fm/qingting/qtradio/model/RingToneNode:ringToneId	Ljava/lang/String;
    //   71: aload 7
    //   73: aload 6
    //   75: ldc_w 816
    //   78: invokevirtual 32	com/alibaba/fastjson/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   81: putfield 1708	fm/qingting/qtradio/model/RingToneNode:ringDesc	Ljava/lang/String;
    //   84: aload 7
    //   86: aload 6
    //   88: ldc_w 548
    //   91: invokevirtual 23	com/alibaba/fastjson/JSONObject:getIntValue	(Ljava/lang/String;)I
    //   94: putfield 1709	fm/qingting/qtradio/model/RingToneNode:duration	I
    //   97: aload 7
    //   99: getfield 1713	fm/qingting/qtradio/model/RingToneNode:downloadInfo	Lfm/qingting/qtradio/model/Download;
    //   102: ifnonnull +15 -> 117
    //   105: aload 7
    //   107: new 1715	fm/qingting/qtradio/model/Download
    //   110: dup
    //   111: invokespecial 1716	fm/qingting/qtradio/model/Download:<init>	()V
    //   114: putfield 1713	fm/qingting/qtradio/model/RingToneNode:downloadInfo	Lfm/qingting/qtradio/model/Download;
    //   117: aload 7
    //   119: getfield 1713	fm/qingting/qtradio/model/RingToneNode:downloadInfo	Lfm/qingting/qtradio/model/Download;
    //   122: bipush 125
    //   124: bipush 24
    //   126: aload 7
    //   128: getfield 1709	fm/qingting/qtradio/model/RingToneNode:duration	I
    //   131: imul
    //   132: imul
    //   133: putfield 1719	fm/qingting/qtradio/model/Download:fileSize	I
    //   136: aload 7
    //   138: aload 6
    //   140: ldc_w 1071
    //   143: invokevirtual 23	com/alibaba/fastjson/JSONObject:getIntValue	(Ljava/lang/String;)I
    //   146: invokestatic 983	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   149: putfield 1721	fm/qingting/qtradio/model/RingToneNode:updatetime	Ljava/lang/String;
    //   152: aload 7
    //   154: ldc_w 1723
    //   157: putfield 1726	fm/qingting/qtradio/model/RingToneNode:ringType	Ljava/lang/String;
    //   160: aload 6
    //   162: ldc_w 266
    //   165: invokevirtual 215	com/alibaba/fastjson/JSONObject:getJSONObject	(Ljava/lang/String;)Lcom/alibaba/fastjson/JSONObject;
    //   168: astore 8
    //   170: aload 7
    //   172: aload 8
    //   174: ldc 168
    //   176: invokevirtual 32	com/alibaba/fastjson/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   179: putfield 1729	fm/qingting/qtradio/model/RingToneNode:mDownLoadPath	Ljava/lang/String;
    //   182: aload 7
    //   184: aload 8
    //   186: ldc_w 1731
    //   189: invokevirtual 215	com/alibaba/fastjson/JSONObject:getJSONObject	(Ljava/lang/String;)Lcom/alibaba/fastjson/JSONObject;
    //   192: ldc 156
    //   194: invokevirtual 32	com/alibaba/fastjson/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   197: putfield 1734	fm/qingting/qtradio/model/RingToneNode:mTranscode	Ljava/lang/String;
    //   200: aload 7
    //   202: aload 8
    //   204: ldc_w 1736
    //   207: invokevirtual 23	com/alibaba/fastjson/JSONObject:getIntValue	(Ljava/lang/String;)I
    //   210: putfield 1738	fm/qingting/qtradio/model/RingToneNode:downloadnum	I
    //   213: aload 6
    //   215: ldc_w 1740
    //   218: invokevirtual 215	com/alibaba/fastjson/JSONObject:getJSONObject	(Ljava/lang/String;)Lcom/alibaba/fastjson/JSONObject;
    //   221: astore 10
    //   223: new 402	fm/qingting/qtradio/model/BroadcasterNode
    //   226: dup
    //   227: invokespecial 403	fm/qingting/qtradio/model/BroadcasterNode:<init>	()V
    //   230: astore 11
    //   232: new 402	fm/qingting/qtradio/model/BroadcasterNode
    //   235: dup
    //   236: invokespecial 403	fm/qingting/qtradio/model/BroadcasterNode:<init>	()V
    //   239: astore 12
    //   241: aload 12
    //   243: aload 10
    //   245: ldc 17
    //   247: invokevirtual 23	com/alibaba/fastjson/JSONObject:getIntValue	(Ljava/lang/String;)I
    //   250: putfield 404	fm/qingting/qtradio/model/BroadcasterNode:id	I
    //   253: aload 12
    //   255: aload 10
    //   257: ldc_w 406
    //   260: invokevirtual 32	com/alibaba/fastjson/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   263: putfield 409	fm/qingting/qtradio/model/BroadcasterNode:nick	Ljava/lang/String;
    //   266: aload 12
    //   268: aload 10
    //   270: ldc_w 411
    //   273: invokevirtual 32	com/alibaba/fastjson/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   276: putfield 413	fm/qingting/qtradio/model/BroadcasterNode:avatar	Ljava/lang/String;
    //   279: aload 12
    //   281: aload 10
    //   283: ldc_w 415
    //   286: invokevirtual 32	com/alibaba/fastjson/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   289: putfield 418	fm/qingting/qtradio/model/BroadcasterNode:weiboId	Ljava/lang/String;
    //   292: aload 12
    //   294: aload 10
    //   296: ldc_w 420
    //   299: invokevirtual 32	com/alibaba/fastjson/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   302: putfield 421	fm/qingting/qtradio/model/BroadcasterNode:weiboName	Ljava/lang/String;
    //   305: aload 12
    //   307: aload 10
    //   309: ldc_w 423
    //   312: invokevirtual 32	com/alibaba/fastjson/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   315: putfield 426	fm/qingting/qtradio/model/BroadcasterNode:qqId	Ljava/lang/String;
    //   318: aload 12
    //   320: aload 10
    //   322: ldc_w 428
    //   325: invokevirtual 32	com/alibaba/fastjson/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   328: putfield 431	fm/qingting/qtradio/model/BroadcasterNode:qqName	Ljava/lang/String;
    //   331: aload 7
    //   333: aload 11
    //   335: getfield 409	fm/qingting/qtradio/model/BroadcasterNode:nick	Ljava/lang/String;
    //   338: putfield 1741	fm/qingting/qtradio/model/RingToneNode:title	Ljava/lang/String;
    //   341: aload 7
    //   343: aload 11
    //   345: invokevirtual 1745	fm/qingting/qtradio/model/RingToneNode:setBroadcaster	(Lfm/qingting/qtradio/model/BroadcasterNode;)V
    //   348: aload 7
    //   350: aload 10
    //   352: ldc_w 1747
    //   355: invokevirtual 32	com/alibaba/fastjson/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   358: invokevirtual 1750	fm/qingting/qtradio/model/RingToneNode:setBelongRadio	(Ljava/lang/String;)V
    //   361: aload_2
    //   362: aload 7
    //   364: invokeinterface 166 2 0
    //   369: pop
    //   370: iinc 5 1
    //   373: goto -343 -> 30
    //   376: astore_3
    //   377: aconst_null
    //   378: areturn
    //   379: new 745	fm/qingting/framework/data/Result
    //   382: dup
    //   383: iconst_1
    //   384: aload_2
    //   385: invokespecial 748	fm/qingting/framework/data/Result:<init>	(ZLjava/lang/Object;)V
    //   388: areturn
    //   389: aconst_null
    //   390: areturn
    //   391: astore 9
    //   393: goto -23 -> 370
    //
    // Exception table:
    //   from	to	target	type
    //   12	27	376	java/lang/Exception
    //   30	117	376	java/lang/Exception
    //   117	213	376	java/lang/Exception
    //   361	370	376	java/lang/Exception
    //   213	361	391	java/lang/Exception
  }

  private Result parseSendMsgToFriend(String paramString)
  {
    if (paramString != null);
    return new Result(true, paramString);
  }

  private Result parseSetCheckInStatus(String paramString)
  {
    if (paramString != null)
      return new Result(true, null);
    return null;
  }

  private Result parseSetUserData(String paramString)
  {
    if (paramString != null);
    return new Result(true, null);
  }

  private Result parseShareInfo(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
      try
      {
        JSONArray localJSONArray = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        for (int i = 0; i < localJSONArray.size(); i++)
        {
          RecommendItemNode localRecommendItemNode = _parseRecommendNode(localJSONArray.getJSONObject(i));
          if (localRecommendItemNode != null)
          {
            Result localResult = new Result(true, localRecommendItemNode);
            return localResult;
          }
        }
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  private Result parseUpgradeInfo(String paramString)
  {
    if (paramString != null)
    {
      UpgradeInfo localUpgradeInfo = new UpgradeInfo();
      try
      {
        JSONObject localJSONObject = ((JSONObject)JSON.parse(paramString)).getJSONObject("data");
        localUpgradeInfo.url = localJSONObject.getString("url");
        localUpgradeInfo.msg = localJSONObject.getString("msg");
        localUpgradeInfo.upgradeFromUM = localJSONObject.getBoolean("umeng_enabled").booleanValue();
        return new Result(true, localUpgradeInfo);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
    }
    return null;
  }

  private Result parseUserFollowers(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
      try
      {
        Result localResult = new Result(true, (JSONObject)JSON.parse(paramString));
        return localResult;
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  private Result parseUserFollowings(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
      try
      {
        Result localResult = new Result(true, (JSONObject)JSON.parse(paramString));
        return localResult;
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  private Result parseUserInfo(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
      try
      {
        Result localResult = new Result(true, (JSONObject)JSON.parse(paramString));
        return localResult;
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  private Result parseUserTids(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
      try
      {
        JSONArray localJSONArray = (JSONArray)JSON.parse(paramString);
        if (localJSONArray != null)
        {
          ArrayList localArrayList = new ArrayList();
          for (int i = 0; i < localJSONArray.size(); i++)
            localArrayList.add(localJSONArray.getString(i));
          Result localResult = new Result(true, localArrayList);
          return localResult;
        }
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  private Result parseVirtualProgramInfo(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
      try
      {
        ProgramNode localProgramNode = _parseVirtualProgramNode(((JSONObject)JSON.parse(paramString)).getJSONObject("data"), 0);
        if (localProgramNode != null)
        {
          Result localResult = new Result(true, localProgramNode);
          return localResult;
        }
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  private Result parseVirtualProgramSchedule(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    while (true)
    {
      int i;
      try
      {
        JSONArray localJSONArray = ((JSONObject)JSON.parse(paramString)).getJSONArray("data");
        if (localJSONArray != null)
        {
          ProgramScheduleList localProgramScheduleList = new ProgramScheduleList(1);
          ProgramSchedule localProgramSchedule = new ProgramSchedule();
          localProgramSchedule.dayOfWeek = 0;
          localProgramSchedule.mLstProgramNodes = new ArrayList();
          i = 0;
          localObject1 = null;
          if (i < localJSONArray.size())
          {
            localObject2 = _parseVirtualProgramNode(localJSONArray.getJSONObject(i), 0);
            if (localObject2 == null)
              break label182;
            if (((ProgramNode)localObject2).sequence == 0)
              ((ProgramNode)localObject2).sequence = i;
            if (localObject1 != null)
            {
              localObject1.nextSibling = ((Node)localObject2);
              ((ProgramNode)localObject2).prevSibling = localObject1;
            }
            localProgramSchedule.mLstProgramNodes.add(localObject2);
            break label186;
          }
          localProgramScheduleList.mLstProgramsScheduleNodes.add(localProgramSchedule);
          Result localResult = new Result(true, localProgramScheduleList);
          return localResult;
        }
      }
      catch (Exception localException)
      {
      }
      return null;
      label182: Object localObject2 = localObject1;
      label186: i++;
      Object localObject1 = localObject2;
    }
  }

  private Result parseVirtualProgramsSchedule(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    return null;
  }

  private Result parseWsqNew(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")))
      try
      {
        JSONObject localJSONObject = ((JSONObject)JSON.parse(paramString)).getJSONObject("data");
        HashMap localHashMap = new HashMap();
        localHashMap.put(localJSONObject.getString("wid"), localJSONObject.getInteger("new"));
        Result localResult = new Result(true, localHashMap);
        return localResult;
      }
      catch (Exception localException)
      {
      }
    return null;
  }

  public Result parse(String paramString, Object paramObject1, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("new_search_suggestion"))
      return parseNewSearchSuggestion((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_LIST_ACTIVITIES"))
      return parseListActivities((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_AD_WHITE_LIST"))
      return parseADWhiteList((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_PODCASTER_BASEINFO"))
      return parsePodcasterBaseInfo((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_PODCASTER_DETAILINFO"))
      return parsePodcasterDetailInfo((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_AD_LOCATION"))
      return parseADLocation((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_PODCASTER_LATESTINFO"))
      return parsePodcasterLatestInfo((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_PODCASTER_CHANNELS"))
      return parsePodcasterChannels((String)paramObject2);
    if (paramString.equalsIgnoreCase("POST_PROGRAM_BARRAGE"))
      return parsePostBarrage((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_PROGRAM_BARRAGE"))
      return parseProgramBarrage((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_QTIME_CONFIG"))
      return parseQtimeConfig((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_WSQ_NEW"))
      return parseWsqNew((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_LOCAL_CATEGORY"))
      return parseListCategories((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_LOCAL_RECOMMEND_INFO"))
      return parseLocalRecommendInfo((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_AD_CONFIG"))
      return parseADConfig((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_AM_AD_CONFIG"))
      return parseAMAdConfig((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_ADV_FROM_AIRWAVE"))
      return parseADVFromAirWave((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_LIST_CATEGORIES"))
      return parseListCategories((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_CATEGORY_ATTRS"))
      return parseCategoryAttr((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_ALL_CHANNELS"))
      return parseAllChannels((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_LIST_CHANNELS"))
      return parseAllChannels((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_LIST_LIVE_CHANNELS"))
      return parseAllChannels((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_VIRTUAL_CHANNEL_INFO"))
      return parseChannelNode((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_LIVE_CHANNEL_INFO"))
      return parseChannelNode((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_LIVE_PROGRAM_SCHEDULE"))
      return parseLiveProgramSchedule((String)paramObject2);
    if ((paramString.equalsIgnoreCase("GET_VIRTUAL_PROGRAM_SCHEDULE")) || (paramString.equalsIgnoreCase("RELOAD_VIRTUAL_PROGRAMS_SCHEDULE")))
      return parseVirtualProgramSchedule((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_VIRTUAL_PROGRAM_INFO"))
      return parseVirtualProgramInfo((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_LIST_MEDIACENTER"))
      return parseMediaCenter((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_AD_POS"))
      return parseAdsPos((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_AD_INFO"))
      return parseAdvertisementInfo((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_AD_INFO_BYCHANNEL"))
      return parseAdvertisementInfo((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_RECOMMEND_PLAYING"))
      return parseRecommendPlayingPrograms((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_SPECIAL_TOPIC_CHANNELS"))
      return parseSpecialTopicChannels((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_RECOMMEND_INFO"))
      return parseRecommendInfo((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_BILLBOARD_CHANNELS"))
      return parseBillboardChannels((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_BILLBOARD_PROGRAMS"))
      return parseBillboardProgram((String)paramObject2);
    if (paramString.equalsIgnoreCase("get_current_playing_programs"))
      return parseCurrentPlayingPrograms((String)paramObject2);
    if (paramString.equalsIgnoreCase("new_search"))
      return parseNewSearch((String)paramObject2);
    if (paramString.equalsIgnoreCase("new_recommendv2_banner"))
      return parseNewRecommendV2Banner((String)paramObject2);
    if (paramString.equalsIgnoreCase("get_share_info"))
      return parseShareInfo((String)paramObject2);
    if (paramString.equalsIgnoreCase("new_recommendv2_fp_banner"))
      return parseNewRecommendV2Banner((String)paramObject2);
    if (paramString.equalsIgnoreCase("get_user_info"))
      return parseUserInfo((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_IM_HISTORY_FROM_SERVER"))
      return parseIMHistory((String)paramObject2);
    if (paramString.equalsIgnoreCase("get_user_followers"))
      return parseUserFollowers((String)paramObject2);
    if (paramString.equalsIgnoreCase("get_user_followings"))
      return parseUserFollowings((String)paramObject2);
    if (paramString.equalsIgnoreCase("get_group_info"))
      return parseGroupInfo((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_IM_SERVER"))
      return parseIMServer((String)paramObject2);
    if (paramString.equalsIgnoreCase("GET_LIST_LIVE_CATEGORIES"))
      return parseLiveCategory((String)paramObject2);
    if (paramString.equalsIgnoreCase("get_group_users"))
      return parseGroupUsers((String)paramObject2);
    if (paramString.equalsIgnoreCase("get_link_info"))
      return parseLinkInfo((String)paramObject2);
    if (paramString.equalsIgnoreCase("get_im_base_user_info"))
      return parseIMBaseUserInfo((String)paramObject2);
    if (paramString.equalsIgnoreCase("create_user"))
      return parseCreateUser((String)paramObject2);
    if (paramString.equalsIgnoreCase("set_user_data"))
      return parseSetUserData((String)paramObject2);
    if (paramString.equalsIgnoreCase("pull_collection_data"))
      return parsePullCollectionData((String)paramObject2);
    if (paramString.equalsIgnoreCase("get_social_user_data"))
      return parseGetUserData((String)paramObject2);
    if (paramString.equalsIgnoreCase("get_ip_location"))
      return parseIPLocation((String)paramObject2);
    if (paramString.equalsIgnoreCase("current_program_topics"))
      return parseProgramTopics((String)paramObject2);
    if (paramString.equalsIgnoreCase("get_ringtone_list"))
      return parseRingToneList((String)paramObject2);
    if (paramString.equalsIgnoreCase("report_user"))
      return new Result(true, "");
    if ((paramString.equalsIgnoreCase("add_following")) || (paramString.equalsIgnoreCase("cancel_following")))
      return new Result(true, null);
    if (paramString.equalsIgnoreCase("get_user_tids"))
      return parseUserTids((String)paramObject2);
    if (paramString.equalsIgnoreCase("hot_search_keywords"))
      return parseHotSearchKeywords((String)paramObject2);
    if (paramString.equalsIgnoreCase("get_jd_ad"))
      return parseJdAd((String)paramObject2);
    return super.parse(paramString, paramObject1, paramObject2);
  }

  public Result parseSpecialTopicChannels(String paramString)
  {
    if ((paramString != null) && (!paramString.equalsIgnoreCase("")));
    while (true)
    {
      int i;
      try
      {
        JSONObject localJSONObject = ((JSONObject)JSON.parse(paramString)).getJSONObject("data");
        if (localJSONObject != null)
        {
          SpecialTopicNode localSpecialTopicNode = _parseSpecialTopicNode(localJSONObject);
          JSONArray localJSONArray = localJSONObject.getJSONArray("channels");
          if (localJSONArray != null)
          {
            ArrayList localArrayList = new ArrayList();
            i = 0;
            if (i < localJSONArray.size())
            {
              ChannelNode localChannelNode = _parseChannelNode(localJSONArray.getJSONObject(i));
              if (localChannelNode == null)
                break label151;
              localArrayList.add(localChannelNode);
              break label151;
            }
            if (localSpecialTopicNode != null)
            {
              localSpecialTopicNode.setChannels(localArrayList);
              return new Result(true, localSpecialTopicNode);
            }
          }
          if (localSpecialTopicNode != null)
          {
            Result localResult = new Result(true, localSpecialTopicNode);
            return localResult;
          }
        }
      }
      catch (Exception localException)
      {
      }
      return null;
      label151: i++;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.parser.NetParser
 * JD-Core Version:    0.6.2
 */