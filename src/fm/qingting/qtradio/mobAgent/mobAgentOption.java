package fm.qingting.qtradio.mobAgent;

import android.content.Context;
import com.umeng.analytics.MobclickAgent;
import fm.qingting.framework.data.IMonitorMobAgent;
import java.util.HashMap;
import java.util.Map;

public class mobAgentOption
  implements IMonitorMobAgent
{
  public static final String WEIBO_CAI = "weibo_cai_option_new";
  public static final String WEIBO_CREATE = "weibo_message_create_new";
  public static final String WEIBO_DING = "weibo_ding_option_new";
  public static final String WEIBO_FLOWER = "weibo_flower_click_new";
  public static final String WEIBO_FRIEND = "weibo_friend_click_new";
  public static final String WEIBO_LEAVE = "weibo_message_leave_new";
  public static final String WEIBO_LOGOUT = "weibo_logout_click_new";
  public static final String WEIBO_REPO = "weibo_repo_option_new";
  public static final String WEIBO_SIGN = "weibo_sign_click_new";
  public static final String WEIBO_TOMYWEIBO = "weibo_tomyweibo_click_new";
  private static mobAgentOption instance;
  private Context _context;
  private String _event;
  private String _option;
  private String _param;
  private String _pos;
  private String _source;
  private String _start;

  public static mobAgentOption getInstance()
  {
    try
    {
      if (instance == null)
        instance = new mobAgentOption();
      mobAgentOption localmobAgentOption = instance;
      return localmobAgentOption;
    }
    finally
    {
    }
  }

  public String getEvent()
  {
    return this._event;
  }

  public String getPos()
  {
    return this._pos;
  }

  public String getSource()
  {
    return this._source;
  }

  public void init(Context paramContext)
  {
    this._context = paramContext;
    this._event = "";
  }

  public void sendMobAgentAPI(String paramString1, String paramString2, Object paramObject)
  {
    if ((paramString1 == null) || (paramString1.equalsIgnoreCase("")) || (this._context == null))
      return;
    String str;
    if ((paramString1.equalsIgnoreCase("channel_regions")) || (paramString1.equalsIgnoreCase("channel_countries")))
      str = "api_channel_regions";
    while (true)
    {
      Map localMap = (Map)paramObject;
      if (localMap == null)
        break;
      ((Long)localMap.get("duration")).longValue();
      if (str.equalsIgnoreCase(""))
        break;
      return;
      if (paramString1.equalsIgnoreCase("channel_types"))
        str = "api_channel_types";
      else if (paramString1.equalsIgnoreCase("region_channels"))
        str = "api_channel_regionchannel_r";
      else if (paramString1.equalsIgnoreCase("type_channels"))
        str = "api_channel_regionchannel_t";
      else if (paramString1.equalsIgnoreCase("week_program_schedule"))
        str = "api_channel_playbillv2";
      else if (paramString1.equalsIgnoreCase("recommended_channels"))
        str = "api_channel_recommend";
      else if (paramString1.equalsIgnoreCase("search_channels"))
        str = "api_search_channel";
      else if (paramString1.equalsIgnoreCase("app_key"))
        str = "api_vbo_appkey";
      else if (paramString1.equalsIgnoreCase("bootstrap"))
        str = "api_bootstrap";
      else if (paramString1.equalsIgnoreCase("uniad"))
        str = "api_vbo_uniad";
      else if (paramString1.equalsIgnoreCase("program_topics_list"))
        str = "api_vbo_programwids";
      else if (paramString1.equalsIgnoreCase("radio_topic_list"))
        str = "api_vbo_channelwids";
      else if (paramString1.equalsIgnoreCase("global_top_posts"))
        str = "api_vbo_topwidv2";
      else if (paramString1.equalsIgnoreCase("get_ip_location"))
        str = "api_utils_iplocation";
      else
        str = "api_request_other";
    }
  }

  public void sendMobAgentAPIERROR(String paramString1, String paramString2, Object paramObject)
  {
    if ((paramString1 == null) || (paramString1.equalsIgnoreCase("")) || (this._context == null))
      return;
    String str1;
    if ((paramString1.equalsIgnoreCase("channel_regions")) || (paramString1.equalsIgnoreCase("channel_countries")))
      str1 = "api_channel_regions_error";
    while (true)
    {
      Map localMap = (Map)paramObject;
      if (localMap == null)
        break;
      long l = ((Long)localMap.get("duration")).longValue();
      String str2 = (String)localMap.get("request");
      if ((str2 == null) || (str1.equalsIgnoreCase("")))
        break;
      MobclickAgent.onEventDuration(this._context, str1, paramString2, l);
      String str3 = str1 + "_detail";
      if (str2.indexOf("googleapis") != -1)
        break;
      int i = str2.indexOf("/api/");
      if (i != -1)
        str2 = str2.substring(i + 5);
      MobclickAgent.onEvent(this._context, str3, str2);
      return;
      if (paramString1.equalsIgnoreCase("channel_types"))
        str1 = "api_channel_types_error";
      else if (paramString1.equalsIgnoreCase("region_channels"))
        str1 = "api_channel_regionchannel_r_error";
      else if (paramString1.equalsIgnoreCase("type_channels"))
        str1 = "api_channel_regionchannel_t_error";
      else if (paramString1.equalsIgnoreCase("week_program_schedule"))
        str1 = "api_channel_playbillv2_error";
      else if (paramString1.equalsIgnoreCase("recommended_channels"))
        str1 = "api_channel_recommend_error";
      else if (paramString1.equalsIgnoreCase("search_channels"))
        str1 = "api_search_channel_error";
      else if (paramString1.equalsIgnoreCase("app_key"))
        str1 = "api_vbo_appkey_error";
      else if (paramString1.equalsIgnoreCase("bootstrap"))
        str1 = "api_bootstrap_error";
      else if (paramString1.equalsIgnoreCase("uniad"))
        str1 = "api_vbo_uniad_error";
      else if (paramString1.equalsIgnoreCase("program_topics_list"))
        str1 = "api_vbo_programwids_error";
      else if (paramString1.equalsIgnoreCase("radio_topic_list"))
        str1 = "api_vbo_channelwids_error";
      else if (paramString1.equalsIgnoreCase("global_top_posts"))
        str1 = "api_vbo_topwidv2_error";
      else if (paramString1.equalsIgnoreCase("get_ip_location"))
        str1 = "api_utils_iplocation_error";
      else
        str1 = "api_request_other_error";
    }
  }

  public void sendMobAgentAPITIMEOUT(String paramString, Object paramObject)
  {
    if (this._context == null);
    Map localMap;
    do
    {
      return;
      localMap = (Map)paramObject;
    }
    while (localMap == null);
    long l = ((Long)localMap.get("duration")).longValue();
    MobclickAgent.onEventDuration(this._context, "api_request_timeout", paramString, l);
  }

  public void sendMobAgentAPIUNKNOWHOST(String paramString, Object paramObject)
  {
    if (this._context == null);
    Map localMap;
    do
    {
      return;
      localMap = (Map)paramObject;
    }
    while (localMap == null);
    long l = ((Long)localMap.get("duration")).longValue();
    MobclickAgent.onEventDuration(this._context, "api_request_unknowhost", paramString, l);
  }

  public void sendMobAgentEvent(String paramString)
  {
    if (this._context == null)
      return;
    MobclickAgent.onEvent(this._context, paramString);
  }

  public void sendMobAgentEvent(String paramString1, String paramString2)
  {
    if (this._context == null)
      return;
    MobclickAgent.onEvent(this._context, paramString1, paramString2);
  }

  public void sendMobAgentEventDuration(String paramString, Long paramLong)
  {
    if (this._context == null)
      return;
    MobclickAgent.onEventDuration(this._context, paramString, paramLong.longValue());
  }

  public void sendMobAgentEventDuration(String paramString1, String paramString2, Long paramLong)
  {
    if (this._context == null)
      return;
    MobclickAgent.onEventDuration(this._context, paramString1, paramString2, paramLong.longValue());
  }

  public void sendMobClickAgent(int paramInt)
  {
    if (paramInt == 0);
    HashMap localHashMap;
    do
    {
      do
        return;
      while ((this._event == null) || (this._event.equalsIgnoreCase("")) || (this._event == null));
      localHashMap = new HashMap();
      if ((paramInt & 0x1) == 1)
        localHashMap.put("source", this._source);
      if ((paramInt & 0x10) == 16)
        localHashMap.put("haslogin", this._start);
      if (((paramInt & 0x100) != 256) || ((paramInt & 0x1000) == 4096))
        localHashMap.put("resultOption", this._option);
      if ((paramInt & 0x10000) == 65536)
        localHashMap.put("selectParam", this._param);
    }
    while ((localHashMap == null) || (localHashMap.size() <= 0));
    MobclickAgent.onEvent(this._context, this._event, localHashMap);
  }

  public void sendMobClickAgentCustom(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    if ((paramString1 == null) || (paramString1.equalsIgnoreCase("")) || (this._context == null))
      return;
    HashMap localHashMap = new HashMap();
    if ((paramString2 != null) && (!paramString2.equalsIgnoreCase("")))
      localHashMap.put("source", paramString2);
    if ((paramString4 != null) && (!paramString4.equalsIgnoreCase("")))
      localHashMap.put("resultOption", paramString4);
    if ((paramString5 != null) && (!paramString5.equalsIgnoreCase("")))
      localHashMap.put("selectParam", "");
    MobclickAgent.onEvent(this._context, paramString1, localHashMap);
  }

  public void sendMobClickAgentLog(int paramInt)
  {
    if (paramInt == 0);
    HashMap localHashMap;
    do
    {
      do
        return;
      while ((this._event == null) || (this._event.equalsIgnoreCase("")) || (this._event == null));
      localHashMap = new HashMap();
      if ((paramInt & 0x1) == 1)
        localHashMap.put("source", this._source);
      if ((paramInt & 0x10) == 16)
        localHashMap.put("haslogin", this._start);
      if (((paramInt & 0x100) != 256) || ((paramInt & 0x1000) == 4096))
        localHashMap.put("loginViewOption", this._option);
      if ((paramInt & 0x10000) == 65536)
        localHashMap.put("selectParam", this._param);
    }
    while ((localHashMap == null) || (localHashMap.size() <= 0));
    MobclickAgent.onEvent(this._context, this._event, localHashMap);
  }

  public void setEvent(String paramString)
  {
    this._event = paramString;
  }

  public void setOption(String paramString)
  {
    this._option = paramString;
  }

  public void setParam(String paramString)
  {
    this._param = paramString;
  }

  public void setPos(String paramString)
  {
    this._pos = paramString;
  }

  public void setSource(String paramString)
  {
    this._source = paramString;
  }

  public void setStart(String paramString)
  {
    this._start = paramString;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.mobAgent.mobAgentOption
 * JD-Core Version:    0.6.2
 */