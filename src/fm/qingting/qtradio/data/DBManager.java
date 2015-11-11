package fm.qingting.qtradio.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.google.gson.Gson;
import fm.qingting.framework.data.DBHelper;
import fm.qingting.framework.data.IDBHelperDelegate;
import fm.qingting.framework.net.HTTPConnection;
import fm.qingting.framework.net.UrlAttr;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DBManager
  implements IDBHelperDelegate
{
  public static final String ACTIVITY = "activity";
  public static final String ALARM = "alarm";
  public static final String ALARMINFOS = "alarmInfos";
  public static final String ALLFAVCATEGORYNODES = "favCategoryNodes";
  public static final String AUTHORITEMS = "authorItems";
  public static final String BILLBOARDNODES = "billboardNodes";
  public static final String CATEGORY = "category";
  public static final String CATEGORYATTRIBUTES = "categoryAttributes";
  public static final String CATEGORYNODES = "categoryNodes";
  public static final String CHANNELNODES = "channelNodesv6";
  public static final String COMMONRECORDS = "commonRecords";
  public static final String CONTENTCATEGORY = "contentcategory";
  public static final String ColNameUrl = "url";
  public static final String ColNameUrlAttr = "urlattr";
  public static final String DOWNLOADINGPROGRAMNODES = "downloadingprogramNodes";
  public static final String FAVOURITECHANNELS = "favouritechannels";
  public static final String FMFREQ = "fmfreq";
  public static final String FREQCHANNELS = "freqChannels";
  public static final String FRONTPAGENODES = "frontpageNodes";
  public static final String GENERICOBJS = "genericObjs";
  public static final String IMBLACK = "imBlack";
  public static final String IMCONTACTS = "imContacts";
  public static final String IMDATABASE = "imDatabase";
  public static final String IMUSERINFO = "imUserInfo";
  public static final String MEDIANCENTERS = "mediaCenterDS";
  public static final String NOTIFICATION = "notification";
  public static final String PLAYEDMETA = "playedMeta";
  public static final String PLAYHISTORY = "playhistory";
  public static final String PLAYLIST = "playList";
  public static final String PODCASTERFOLLOW = "podcasterFollow";
  public static final String PODCASTERS = "podcastersInfo";
  public static final String PREDOWNLOADINGPROGRAMNODES = "predownloadingprogramNodes";
  public static final String PROFILE = "profile";
  public static final String PROGRAMNODES = "programNodes";
  public static final String PULLLIST = "pullList";
  public static final String PULLMSGCONFIG = "pullMsgConfig";
  public static final String PULLMSGSTATE = "pullMsgState";
  public static final String QTRADIO = "qtradio";
  public static final String RADIONODES = "radioNodes";
  public static final String RECOMMENDCATEGORYNODES = "recCategoryNodes";
  public static final String RESERVE = "reserve";
  public static final String RESERVEPROGRAM = "reserveprogram";
  public static final String Record = "record";
  public static final String SEARCHHISTROY = "searchhistroy";
  public static final String SHARETABLE = "shareTable";
  public static final String SOCIALUSER = "socialuser";
  public static final String SUBCATEGORYNODES = "subcategoryNodes";
  public static final String TableNameUrlAttr = "urlattrs";
  private static final String Tag = "DBManager";
  public static final String URLATTR = "url_attr";
  public static final String USERINFOS = "userinfos";
  public static final String WEIBO = "weibo";
  private static final boolean generateDataBase = false;
  private static DBManager instance;
  private static Map<String, UrlAttr> urlAttrMap = new HashMap();
  public static final boolean useConnectionPool = true;
  private final int MAXPOOLSIZE = 8;
  private DBHelper _alarmInfoHelper;
  private DBHelper _allFavCategoryHelper;
  private DBHelper _billboardHelper;
  private DBHelper _categoryAttributesHelper;
  private DBHelper _categoryNodesHelper;
  private DBHelper _channelNodesHelper;
  private DBHelper _commonRecordsHelper;
  private DBHelper _contentCategoryHelper;
  private Context _context;
  private DBHelper _datacenterHelper;
  private DBHelper _downloadingNodesHelper;
  private DBHelper _favouritechannelsHelper;
  private DBHelper _freqChannelsHelper;
  private DBHelper _frontPageHelper;
  private DBHelper _genericObjHelper;
  private DBHelper _imBlackDataHelper;
  private DBHelper _imContactsDataHelper;
  private DBHelper _imDataHelper;
  private DBHelper _imUserHelper;
  private DBHelper _playedMetaDataHelper;
  private DBHelper _playhistoryHelper;
  private DBHelper _playlistDataHelper;
  private DBHelper _podcasterFollowHelper;
  private DBHelper _podcasterHelper;
  private DBHelper _predownloadingNodesHelper;
  private DBHelper _profileHelp;
  private DBHelper _programNodesHelper;
  private DBHelper _pullMsgStateHelper;
  private DBHelper _pulllistDataHelper;
  private DBHelper _qtradioHelp;
  private DBHelper _radioHelper;
  private DBHelper _recCategoryHelper;
  private DBHelper _recordHelp;
  private DBHelper _reserveHelp;
  private DBHelper _reserveProgramHelper;
  private DBHelper _shareTableHelper;
  private DBHelper _socialUserHelper;
  private DBHelper _subcategoryNodesHelper;
  private DBHelper _urlAttrHelper;
  private DBHelper _userInfosHelper;
  private DBHelper _weiboHelp;
  private List<Map<String, Object>> accessToken;
  private List<Map<String, Object>> activity;
  private List<Map<String, Object>> alarm;
  private List<Map<String, Object>> flower;
  private HashMap<String, SQLDbCache> mSqlCaches = new HashMap();
  private List<Map<String, Object>> notification;
  private List<Map<String, Object>> profile;
  private List<Map<String, Object>> searchhistroy;
  private List<Map<String, Object>> signin;

  private void clearConnectionPool()
  {
    Iterator localIterator = this.mSqlCaches.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      SQLDbCache localSQLDbCache = (SQLDbCache)this.mSqlCaches.get(str);
      if (localSQLDbCache != null)
      {
        SQLiteDatabase localSQLiteDatabase = localSQLDbCache.getDb();
        if (localSQLiteDatabase != null)
          localSQLiteDatabase.close();
      }
    }
    this.mSqlCaches.clear();
  }

  private void createAlarmInfoTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("alarmInfo", "VARCHAR(512)");
    localHashMap1.put("alarmInfos", localHashMap2);
    this._alarmInfoHelper = new DBHelper(localHashMap1, null, this._context, "alarmInfos", null, 1, this);
  }

  private void createAllFavCategoryTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("node", "VARCHAR(1000)");
    localHashMap2.put("id", "VARCHAR(30)");
    localHashMap2.put("name", "VARCHAR(30)");
    localHashMap1.put("allFavCategoryNodes", localHashMap2);
    this._allFavCategoryHelper = new DBHelper(localHashMap1, null, this._context, "favCategoryNodes", null, 1, this);
  }

  private void createBillboardTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("BillboardItemNode", "VARCHAR(256)");
    localHashMap2.put("node", "VARCHAR(1000)");
    localHashMap2.put("nodeName", "VARCHAR(32)");
    localHashMap2.put("type", "int");
    localHashMap1.put("billboardNodes", localHashMap2);
    this._billboardHelper = new DBHelper(localHashMap1, null, this._context, "billboardNodes", null, 1, this);
  }

  private void createCategoryAttributesTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("catid", "int");
    localHashMap2.put("attrs", "VARCHAR(1000)");
    localHashMap1.put("categoryAttributes", localHashMap2);
    this._categoryAttributesHelper = new DBHelper(localHashMap1, null, this._context, "categoryAttributes", null, 1, this);
  }

  private void createCategoryNodeTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("categoryNode", "VARCHAR(1000)");
    localHashMap2.put("id", "int");
    localHashMap2.put("parentId", "int");
    localHashMap1.put("categoryNodes", localHashMap2);
    this._categoryNodesHelper = new DBHelper(localHashMap1, null, this._context, "categoryNodes", null, 1, this);
  }

  private void createChannelNodeTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("catid", "int");
    localHashMap2.put("type", "int");
    localHashMap2.put("channelid", "int");
    localHashMap2.put("channelNode", "VARCHAR(1000)");
    localHashMap2.put("key", "VARCHAR(32)");
    localHashMap1.put("channelNodes", localHashMap2);
    this._channelNodesHelper = new DBHelper(localHashMap1, null, this._context, "channelNodesv6", null, 1, this);
  }

  private void createCommonRecordsTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("key", "VARCHAR(64)");
    localHashMap2.put("type", "VARCHAR(16)");
    localHashMap2.put("value", "VARCHAR(64)");
    localHashMap1.put("commonRecords", localHashMap2);
    this._commonRecordsHelper = new DBHelper(localHashMap1, null, this._context, "commonRecords", null, 1, this);
  }

  private void createContentCategoryTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("nodename", "VARCHAR(10)");
    localHashMap2.put("name", "VARCHAR(10)");
    localHashMap2.put("type", "VARCHAR(10)");
    localHashMap2.put("id", "int");
    localHashMap1.put("contentcategory", localHashMap2);
    this._contentCategoryHelper = new DBHelper(localHashMap1, null, this._context, "contentcategory", null, 1, this);
  }

  private void createDataCenterTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("type", "VARCHAR(32)");
    localHashMap2.put("pinginfo", "VARCHAR(512)");
    localHashMap1.put("mediaCenterDS", localHashMap2);
    this._datacenterHelper = new DBHelper(localHashMap1, null, this._context, "mediaCenterDS", null, 1, this);
  }

  private void createDownloadingProgramNodeTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("id", "VARCHAR(16)");
    localHashMap2.put("programNode", "VARCHAR(1000)");
    localHashMap1.put("downloadingNodes", localHashMap2);
    this._downloadingNodesHelper = new DBHelper(localHashMap1, null, this._context, "downloadingprogramNodes", null, 1, this);
  }

  private void createFavouriteChannelTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("id", "int");
    localHashMap2.put("channelNode", "VARCHAR(1000)");
    localHashMap1.put("favouritechannels", localHashMap2);
    this._favouritechannelsHelper = new DBHelper(localHashMap1, null, this._context, "favouritechannels", null, 1, this);
  }

  private void createFreqChannelTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("city", "VARCHAR(32)");
    localHashMap2.put("channelid", "int");
    localHashMap2.put("channelname", "VARCHAR(64)");
    localHashMap2.put("freq", "VARCHAR(16)");
    localHashMap1.put("freqChannels", localHashMap2);
    this._freqChannelsHelper = new DBHelper(localHashMap1, null, this._context, "freqChannels", null, 1, this);
  }

  private void createFrontPageTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("RecommendItemNode", "VARCHAR(1000)");
    localHashMap2.put("node", "VARCHAR(1000)");
    localHashMap2.put("nodeName", "VARCHAR(32)");
    localHashMap2.put("type", "int");
    localHashMap1.put("frontpageNodes", localHashMap2);
    this._frontPageHelper = new DBHelper(localHashMap1, null, this._context, "frontpageNodes", null, 1, this);
  }

  private void createGenericObjTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("id", "int");
    localHashMap2.put("type", "VARCHAR(32)");
    localHashMap2.put("value", "VARCHAR(1000)");
    localHashMap1.put("genericObjs", localHashMap2);
    this._genericObjHelper = new DBHelper(localHashMap1, null, this._context, "genericObjs", null, 1, this);
  }

  private void createIMBlackUser()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("info", "VARCHAR(512)");
    localHashMap2.put("reserve", "VARCHAR(128)");
    localHashMap1.put("imBlack", localHashMap2);
    this._imBlackDataHelper = new DBHelper(localHashMap1, null, this._context, "imBlack", null, 1, this);
  }

  private void createIMContacts()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("type", "VARCHAR(4)");
    localHashMap2.put("info", "VARCHAR(512)");
    localHashMap2.put("key", "VARCHAR(128)");
    localHashMap1.put("imContacts", localHashMap2);
    this._imContactsDataHelper = new DBHelper(localHashMap1, null, this._context, "imContacts", null, 1, this);
  }

  private void createIMDatabase()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("msgSeq", "long");
    localHashMap2.put("msgId", "VARCHAR(100)");
    localHashMap2.put("fromContactId", "VARCHAR(100)");
    localHashMap2.put("toContactId", "VARCHAR(100)");
    localHashMap2.put("contentType", "int");
    localHashMap2.put("content", "VARCHAR(600)");
    localHashMap2.put("timestamp", "long");
    localHashMap2.put("avatar", "VARCHAR(500)");
    localHashMap2.put("name", "VARCHAR(32)");
    localHashMap1.put("userMessage", localHashMap2);
    HashMap localHashMap3 = new HashMap();
    localHashMap3.put("msgSeq", "long");
    localHashMap3.put("msgId", "VARCHAR(100)");
    localHashMap3.put("groupId", "VARCHAR(100)");
    localHashMap3.put("fromContactId", "VARCHAR(100)");
    localHashMap3.put("contentType", "int");
    localHashMap3.put("content", "VARCHAR(600)");
    localHashMap3.put("timestamp", "long");
    localHashMap3.put("avatar", "VARCHAR(500)");
    localHashMap3.put("name", "VARCHAR(32)");
    localHashMap1.put("groupMessage", localHashMap3);
    HashMap localHashMap4 = new HashMap();
    localHashMap4.put("im_group_message_index", "groupMessage(groupId)");
    this._imDataHelper = new DBHelper(localHashMap1, localHashMap4, this._context, "imDatabase", null, 1, this);
  }

  private void createIMUserInfo()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("id", "VARCHAR(64)");
    localHashMap2.put("avatar", "VARCHAR(500)");
    localHashMap2.put("name", "VARCHAR(32)");
    localHashMap2.put("gender", "VARCHAR(8)");
    localHashMap1.put("imUserInfo", localHashMap2);
    this._imUserHelper = new DBHelper(localHashMap1, null, this._context, "imUserInfo", null, 1, this);
  }

  private void createPlayHistoryTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("id", "int");
    localHashMap2.put("nodeName", "VARCHAR(32)");
    localHashMap2.put("playNode", "VARCHAR(1000)");
    localHashMap2.put("time", "long");
    localHashMap2.put("catId", "int");
    localHashMap2.put("subCatId", "int");
    localHashMap2.put("channelId", "int");
    localHashMap2.put("channelName", "VARCHAR(64)");
    localHashMap2.put("playPosition", "long");
    localHashMap2.put("channelThumb", "VARCHAR(256)");
    localHashMap1.put("playhistory", localHashMap2);
    this._playhistoryHelper = new DBHelper(localHashMap1, null, this._context, "playhistory", null, 2, this);
  }

  private void createPlayListTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("id", "int");
    localHashMap2.put("type", "int");
    localHashMap2.put("node", "VARCHAR(1000)");
    localHashMap1.put("playList", localHashMap2);
    this._playlistDataHelper = new DBHelper(localHashMap1, null, this._context, "playList", null, 1, this);
  }

  private void createPlayedMetaTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("id", "VARCHAR(32)");
    localHashMap2.put("playedMetaData", "VARCHAR(512)");
    localHashMap1.put("playedMetaData", localHashMap2);
    this._playedMetaDataHelper = new DBHelper(localHashMap1, null, this._context, "playedMeta", null, 1, this);
  }

  private void createPodcasterFollowTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("podcasterId", "integer");
    localHashMap2.put("followTime", "long");
    localHashMap2.put("userKey", "VARCHAR(32)");
    localHashMap2.put("updateTime", "long");
    localHashMap2.put("data", "VARCHAR(1000)");
    localHashMap1.put("myPodcaster", localHashMap2);
    this._podcasterFollowHelper = new DBHelper(localHashMap1, null, this._context, "podcasterFollow", null, 1, this);
  }

  private void createPodcasterInfoTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("podcasterId", "integer PRIMARY KEY");
    localHashMap2.put("data", "VARCHAR(1000)");
    localHashMap1.put("podcastersInfo", localHashMap2);
    this._podcasterHelper = new DBHelper(localHashMap1, null, this._context, "podcastersInfo", null, 1, this);
  }

  private void createPreDownloadProgramNodeTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("id", "VARCHAR(16)");
    localHashMap2.put("programNode", "VARCHAR(1000)");
    localHashMap1.put("predownloadingNodes", localHashMap2);
    this._predownloadingNodesHelper = new DBHelper(localHashMap1, null, this._context, "predownloadingprogramNodes", null, 1, this);
  }

  private void createProgramNodeTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("cid", "int");
    localHashMap2.put("pid", "int");
    localHashMap2.put("dw", "int");
    localHashMap2.put("programNode", "VARCHAR(1000)");
    localHashMap1.put("programNodes", localHashMap2);
    this._programNodesHelper = new DBHelper(localHashMap1, null, this._context, "programNodes", null, 1, this);
  }

  private void createPullListTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("id", "int");
    localHashMap2.put("type", "int");
    localHashMap2.put("node", "VARCHAR(1000)");
    localHashMap1.put("pullList", localHashMap2);
    this._pulllistDataHelper = new DBHelper(localHashMap1, null, this._context, "pullList", null, 1, this);
  }

  private void createPullMsgStateTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("id", "VARCHAR(32)");
    localHashMap2.put("state", "int");
    localHashMap1.put("pullMsgState", localHashMap2);
    this._pullMsgStateHelper = new DBHelper(localHashMap1, null, this._context, "pullMsgState", null, 1, this);
  }

  private void createRadioNodesTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("radioChannelNode", "VARCHAR(256)");
    localHashMap2.put("id", "int");
    localHashMap1.put("radioNodes", localHashMap2);
    this._radioHelper = new DBHelper(localHashMap1, null, this._context, "radioNodes", null, 1, this);
  }

  private void createRecCategoryTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("RecommendItemNode", "VARCHAR(1000)");
    localHashMap2.put("node", "VARCHAR(1000)");
    localHashMap2.put("nodeName", "VARCHAR(32)");
    localHashMap2.put("type", "int");
    localHashMap2.put("catid", "VARCHAR(32)");
    localHashMap1.put("recCategoryNodes", localHashMap2);
    this._recCategoryHelper = new DBHelper(localHashMap1, null, this._context, "recCategoryNodes", null, 1, this);
  }

  private void createReserveProgramTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("time", "long");
    localHashMap2.put("reserveProgram", "VARCHAR(1000)");
    localHashMap2.put("channelId", "VARCHAR(32)");
    localHashMap2.put("programName", "VARCHAR(64)");
    localHashMap2.put("programId", "VARCHAR(32)");
    localHashMap1.put("reserveprogram", localHashMap2);
    this._reserveProgramHelper = new DBHelper(localHashMap1, null, this._context, "reserveprogram", null, 1, this);
  }

  private void createShareTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("type", "VARCHAR(16)");
    localHashMap2.put("catid", "VARCHAR(16)");
    localHashMap2.put("subcatid", "VARCHAR(16)");
    localHashMap2.put("channelid", "VARCHAR(16)");
    localHashMap2.put("programid", "VARCHAR(16)");
    localHashMap2.put("time", "long");
    localHashMap2.put("snsname", "VARCHAR(16)");
    localHashMap1.put("shareTable", localHashMap2);
    this._shareTableHelper = new DBHelper(localHashMap1, null, this._context, "shareTable", null, 1, this);
  }

  private void createSocialUserProfileTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("userKey", "VARCHAR(64)");
    localHashMap2.put("userProfile", "VARCHAR(1024)");
    localHashMap1.put("socialUsers", localHashMap2);
    this._socialUserHelper = new DBHelper(localHashMap1, null, this._context, "socialuser", null, 1, this);
  }

  private void createSubCategoryNodeTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("id", "VARCHAR(30)");
    localHashMap2.put("subcategoryNode", "VARCHAR(1000)");
    localHashMap2.put("parentId", "VARCHAR(30)");
    localHashMap1.put("subcategoryNodes", localHashMap2);
    this._subcategoryNodesHelper = new DBHelper(localHashMap1, null, this._context, "subcategoryNodes", null, 1, this);
  }

  private void createUrlAttrTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("url", "VARCHAR(300)");
    localHashMap2.put("urlattr", "VARCHAR(500)");
    localHashMap1.put("urlattrs", localHashMap2);
    this._urlAttrHelper = new DBHelper(localHashMap1, null, this._context, "url_attr", null, 1, this);
  }

  private void createUserInfoTable()
  {
    if (this._context == null)
      return;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("sns_id", "VARCHAR(128)");
    localHashMap2.put("sns_site", "VARCHAR(64)");
    localHashMap2.put("sns_name", "VARCHAR(64)");
    localHashMap2.put("sns_account", "VARCHAR(64)");
    localHashMap2.put("sns_avatar", "VARCHAR(256)");
    localHashMap1.put("userInfos", localHashMap2);
    this._userInfosHelper = new DBHelper(localHashMap1, null, this._context, "userinfos", null, 1, this);
  }

  public static DBManager getInstance()
  {
    try
    {
      if (instance == null)
        instance = new DBManager();
      DBManager localDBManager = instance;
      return localDBManager;
    }
    finally
    {
    }
  }

  private SQLiteDatabase getReadableDbInternal(String paramString)
  {
    if (paramString.equalsIgnoreCase("url_attr"))
      return this._urlAttrHelper.getReadableDatabase();
    if (paramString.equalsIgnoreCase("favCategoryNodes"))
      return this._allFavCategoryHelper.getReadableDatabase();
    if (paramString.equalsIgnoreCase("recCategoryNodes"))
      return this._recCategoryHelper.getReadableDatabase();
    if (paramString.equalsIgnoreCase("qtradio"))
      return this._qtradioHelp.getReadableDatabase();
    if (paramString.equalsIgnoreCase("profile"))
      return this._profileHelp.getReadableDatabase();
    if (paramString.equalsIgnoreCase("alarm"));
    do
    {
      do
      {
        return null;
        if (paramString.equalsIgnoreCase("billboardNodes"))
          return this._billboardHelper.getReadableDatabase();
        if (paramString.equalsIgnoreCase("shareTable"))
          return this._shareTableHelper.getReadableDatabase();
        if (paramString.equalsIgnoreCase("pullMsgState"))
          return this._pullMsgStateHelper.getReadableDatabase();
        if (paramString.equalsIgnoreCase("playedMeta"))
          return this._playedMetaDataHelper.getReadableDatabase();
        if (paramString.equalsIgnoreCase("playList"))
          return this._playlistDataHelper.getReadableDatabase();
        if (paramString.equalsIgnoreCase("genericObjs"))
          return this._genericObjHelper.getReadableDatabase();
        if (paramString.equalsIgnoreCase("pullList"))
          return this._pulllistDataHelper.getReadableDatabase();
        if (paramString.equalsIgnoreCase("imContacts"))
          return this._imContactsDataHelper.getReadableDatabase();
        if (paramString.equalsIgnoreCase("imUserInfo"))
          return this._imUserHelper.getReadableDatabase();
        if (paramString.equalsIgnoreCase("imDatabase"))
          return this._imDataHelper.getReadableDatabase();
        if (paramString.equalsIgnoreCase("imBlack"))
          return this._imBlackDataHelper.getReadableDatabase();
      }
      while ((paramString.equalsIgnoreCase("fmfreq")) || (paramString.equalsIgnoreCase("notification")) || (paramString.equalsIgnoreCase("activity")));
      if (paramString.equalsIgnoreCase("predownloadingprogramNodes"))
        return this._predownloadingNodesHelper.getReadableDatabase();
      if (paramString.equalsIgnoreCase("downloadingprogramNodes"))
        return this._downloadingNodesHelper.getReadableDatabase();
      if (paramString.equalsIgnoreCase("reserve"))
        return this._reserveHelp.getReadableDatabase();
      if (paramString.equalsIgnoreCase("record"))
        return this._recordHelp.getReadableDatabase();
    }
    while (paramString.equalsIgnoreCase("searchhistroy"));
    if (paramString.equalsIgnoreCase("favouritechannels"))
      return this._favouritechannelsHelper.getReadableDatabase();
    if (paramString.equalsIgnoreCase("playhistory"))
      return this._playhistoryHelper.getReadableDatabase();
    if (paramString.equalsIgnoreCase("reserveprogram"))
      return this._reserveProgramHelper.getReadableDatabase();
    if (paramString.equalsIgnoreCase("contentcategory"))
      return this._contentCategoryHelper.getReadableDatabase();
    if (paramString.equalsIgnoreCase("categoryNodes"))
      return this._categoryNodesHelper.getReadableDatabase();
    if (paramString.equalsIgnoreCase("categoryAttributes"))
      return this._categoryAttributesHelper.getReadableDatabase();
    if (paramString.equalsIgnoreCase("subcategoryNodes"))
      return this._subcategoryNodesHelper.getReadableDatabase();
    if (paramString.equalsIgnoreCase("channelNodesv6"))
      return this._channelNodesHelper.getReadableDatabase();
    if (paramString.equalsIgnoreCase("frontpageNodes"))
      return this._frontPageHelper.getReadableDatabase();
    if (paramString.equalsIgnoreCase("radioNodes"))
      return this._radioHelper.getReadableDatabase();
    if (paramString.equalsIgnoreCase("mediaCenterDS"))
      return this._datacenterHelper.getReadableDatabase();
    if (paramString.equalsIgnoreCase("freqChannels"))
      return this._freqChannelsHelper.getReadableDatabase();
    if (paramString.equalsIgnoreCase("alarmInfos"))
      return this._alarmInfoHelper.getReadableDatabase();
    if (paramString.equalsIgnoreCase("userinfos"))
      return this._userInfosHelper.getReadableDatabase();
    if (paramString.equalsIgnoreCase("podcastersInfo"))
      return this._podcasterHelper.getReadableDatabase();
    if (paramString.equalsIgnoreCase("podcasterFollow"))
      return this._podcasterFollowHelper.getReadableDatabase();
    if (paramString.equalsIgnoreCase("commonRecords"))
      return this._commonRecordsHelper.getReadableDatabase();
    if (paramString.equalsIgnoreCase("socialuser"))
      return this._socialUserHelper.getReadableDatabase();
    if (paramString.equalsIgnoreCase("programNodes"))
      return this._programNodesHelper.getReadableDatabase();
    return this._weiboHelp.getReadableDatabase();
  }

  private SQLiteDatabase getWritableDbInternal(String paramString)
  {
    if (paramString.equalsIgnoreCase("url_attr"))
      return this._urlAttrHelper.getWritableDatabase();
    if (paramString.equalsIgnoreCase("favCategoryNodes"))
      return this._allFavCategoryHelper.getWritableDatabase();
    if (paramString.equalsIgnoreCase("recCategoryNodes"))
      return this._recCategoryHelper.getWritableDatabase();
    if (paramString.equalsIgnoreCase("qtradio"))
      return this._qtradioHelp.getWritableDatabase();
    if (paramString.equalsIgnoreCase("profile"))
      return this._profileHelp.getWritableDatabase();
    if (paramString.equalsIgnoreCase("shareTable"))
      return this._shareTableHelper.getWritableDatabase();
    if (paramString.equalsIgnoreCase("alarm"));
    do
    {
      do
      {
        return null;
        if (paramString.equalsIgnoreCase("pullMsgState"))
          return this._pullMsgStateHelper.getWritableDatabase();
        if (paramString.equalsIgnoreCase("playedMeta"))
          return this._playedMetaDataHelper.getWritableDatabase();
        if (paramString.equalsIgnoreCase("billboardNodes"))
          return this._billboardHelper.getWritableDatabase();
        if (paramString.equalsIgnoreCase("imBlack"))
          return this._imBlackDataHelper.getWritableDatabase();
        if (paramString.equalsIgnoreCase("playList"))
          return this._playlistDataHelper.getWritableDatabase();
        if (paramString.equalsIgnoreCase("genericObjs"))
          return this._genericObjHelper.getWritableDatabase();
        if (paramString.equalsIgnoreCase("pullList"))
          return this._pulllistDataHelper.getWritableDatabase();
        if (paramString.equalsIgnoreCase("imContacts"))
          return this._imContactsDataHelper.getWritableDatabase();
        if (paramString.equalsIgnoreCase("imUserInfo"))
          return this._imUserHelper.getWritableDatabase();
        if (paramString.equalsIgnoreCase("imDatabase"))
          return this._imDataHelper.getWritableDatabase();
      }
      while ((paramString.equalsIgnoreCase("fmfreq")) || (paramString.equalsIgnoreCase("notification")) || (paramString.equalsIgnoreCase("activity")));
      if (paramString.equalsIgnoreCase("downloadingprogramNodes"))
        return this._downloadingNodesHelper.getWritableDatabase();
      if (paramString.equalsIgnoreCase("predownloadingprogramNodes"))
        return this._predownloadingNodesHelper.getWritableDatabase();
      if (paramString.equalsIgnoreCase("reserve"))
        return this._reserveHelp.getWritableDatabase();
      if (paramString.equalsIgnoreCase("record"))
        return this._recordHelp.getWritableDatabase();
    }
    while (paramString.equalsIgnoreCase("searchhistroy"));
    if (paramString.equalsIgnoreCase("favouritechannels"))
      return this._favouritechannelsHelper.getWritableDatabase();
    if (paramString.equalsIgnoreCase("playhistory"))
      return this._playhistoryHelper.getWritableDatabase();
    if (paramString.equalsIgnoreCase("reserveprogram"))
      return this._reserveProgramHelper.getWritableDatabase();
    if (paramString.equalsIgnoreCase("contentcategory"))
      return this._contentCategoryHelper.getWritableDatabase();
    if (paramString.equalsIgnoreCase("categoryNodes"))
      return this._categoryNodesHelper.getWritableDatabase();
    if (paramString.equalsIgnoreCase("categoryAttributes"))
      return this._categoryAttributesHelper.getWritableDatabase();
    if (paramString.equalsIgnoreCase("subcategoryNodes"))
      return this._subcategoryNodesHelper.getWritableDatabase();
    if (paramString.equalsIgnoreCase("channelNodesv6"))
      return this._channelNodesHelper.getWritableDatabase();
    if (paramString.equalsIgnoreCase("frontpageNodes"))
      return this._frontPageHelper.getWritableDatabase();
    if (paramString.equalsIgnoreCase("radioNodes"))
      return this._radioHelper.getWritableDatabase();
    if (paramString.equalsIgnoreCase("mediaCenterDS"))
      return this._datacenterHelper.getWritableDatabase();
    if (paramString.equalsIgnoreCase("freqChannels"))
      return this._freqChannelsHelper.getWritableDatabase();
    if (paramString.equalsIgnoreCase("alarmInfos"))
      return this._alarmInfoHelper.getWritableDatabase();
    if (paramString.equalsIgnoreCase("userinfos"))
      return this._userInfosHelper.getWritableDatabase();
    if (paramString.equalsIgnoreCase("podcastersInfo"))
      return this._podcasterHelper.getWritableDatabase();
    if (paramString.equalsIgnoreCase("podcasterFollow"))
      return this._podcasterFollowHelper.getWritableDatabase();
    if (paramString.equalsIgnoreCase("commonRecords"))
      return this._commonRecordsHelper.getWritableDatabase();
    if (paramString.equalsIgnoreCase("socialuser"))
      return this._socialUserHelper.getWritableDatabase();
    if (paramString.equalsIgnoreCase("programNodes"))
      return this._programNodesHelper.getWritableDatabase();
    return this._weiboHelp.getWritableDatabase();
  }

  private void moveData(SQLiteDatabase paramSQLiteDatabase, String paramString)
  {
  }

  private void movePlayhistory(SQLiteDatabase paramSQLiteDatabase)
  {
    try
    {
      paramSQLiteDatabase.execSQL("alter table playhistory add column channelThumb");
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private void removeIdealConnection()
  {
    if (this.mSqlCaches.size() < 8);
    Object localObject1;
    label118: 
    do
    {
      return;
      long l1 = -9223372036854775808L;
      localObject1 = null;
      long l2 = System.currentTimeMillis();
      Iterator localIterator = this.mSqlCaches.keySet().iterator();
      if (localIterator.hasNext())
      {
        Object localObject2 = (String)localIterator.next();
        SQLDbCache localSQLDbCache2 = (SQLDbCache)this.mSqlCaches.get(localObject2);
        long l4;
        if (localSQLDbCache2 != null)
        {
          l4 = localSQLDbCache2.getTimeInterval(l2);
          if (l1 >= l4)
            break label118;
        }
        for (long l3 = l4; ; l3 = l1)
        {
          l1 = l3;
          localObject1 = localObject2;
          break;
          this.mSqlCaches.remove(localObject2);
          localObject2 = localObject1;
        }
      }
    }
    while (localObject1 == null);
    SQLDbCache localSQLDbCache1 = (SQLDbCache)this.mSqlCaches.get(localObject1);
    if (localSQLDbCache1 != null)
    {
      SQLiteDatabase localSQLiteDatabase = localSQLDbCache1.getDb();
      if (localSQLiteDatabase != null)
        localSQLiteDatabase.close();
    }
    this.mSqlCaches.remove(localObject1);
  }

  private void storeUrlAttrToDB()
  {
    SQLiteDatabase localSQLiteDatabase = getInstance().getWritableDB("url_attr");
    try
    {
      localSQLiteDatabase.beginTransaction();
      localSQLiteDatabase.execSQL("delete from urlattrs");
      Gson localGson = new Gson();
      Iterator localIterator = urlAttrMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        UrlAttr localUrlAttr = (UrlAttr)urlAttrMap.get(str);
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = str;
        arrayOfObject[1] = localGson.toJson(localUrlAttr);
        localSQLiteDatabase.execSQL("insert into urlattrs(url, urlattr) values(?, ?)", arrayOfObject);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    localSQLiteDatabase.setTransactionSuccessful();
    localSQLiteDatabase.endTransaction();
  }

  public void forceClearUrlAttr()
  {
    SQLiteDatabase localSQLiteDatabase = getInstance().getWritableDB("url_attr");
    try
    {
      localSQLiteDatabase.execSQL("delete from urlattrs");
      urlAttrMap.clear();
      HTTPConnection.setUrlAttrMap(urlAttrMap);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public SQLiteDatabase getReadableDB(String paramString)
  {
    SQLDbCache localSQLDbCache = (SQLDbCache)this.mSqlCaches.get(paramString);
    if (localSQLDbCache != null)
    {
      if (!localSQLDbCache.isWritable())
      {
        localSQLDbCache.setTime(System.currentTimeMillis());
        if (localSQLDbCache.getDb() != null)
          return localSQLDbCache.getDb();
        SQLiteDatabase localSQLiteDatabase3 = getReadableDbInternal(paramString);
        this.mSqlCaches.put(paramString, new SQLDbCache(localSQLiteDatabase3, false, System.currentTimeMillis()));
        removeIdealConnection();
        return localSQLiteDatabase3;
      }
      this.mSqlCaches.remove(paramString);
      SQLiteDatabase localSQLiteDatabase2 = getReadableDbInternal(paramString);
      this.mSqlCaches.put(paramString, new SQLDbCache(localSQLiteDatabase2, false, System.currentTimeMillis()));
      removeIdealConnection();
      return localSQLiteDatabase2;
    }
    SQLiteDatabase localSQLiteDatabase1 = getReadableDbInternal(paramString);
    this.mSqlCaches.put(paramString, new SQLDbCache(localSQLiteDatabase1, false, System.currentTimeMillis()));
    removeIdealConnection();
    return localSQLiteDatabase1;
  }

  public SQLiteDatabase getWritableDB(String paramString)
  {
    SQLDbCache localSQLDbCache = (SQLDbCache)this.mSqlCaches.get(paramString);
    if (localSQLDbCache != null)
    {
      if (localSQLDbCache.isWritable())
      {
        localSQLDbCache.setTime(System.currentTimeMillis());
        if (localSQLDbCache.getDb() != null)
          return localSQLDbCache.getDb();
        SQLiteDatabase localSQLiteDatabase3 = getWritableDbInternal(paramString);
        this.mSqlCaches.put(paramString, new SQLDbCache(localSQLiteDatabase3, true, System.currentTimeMillis()));
        removeIdealConnection();
        return localSQLiteDatabase3;
      }
      this.mSqlCaches.remove(paramString);
      SQLiteDatabase localSQLiteDatabase2 = getWritableDbInternal(paramString);
      this.mSqlCaches.put(paramString, new SQLDbCache(localSQLiteDatabase2, true, System.currentTimeMillis()));
      removeIdealConnection();
      return localSQLiteDatabase2;
    }
    SQLiteDatabase localSQLiteDatabase1 = getWritableDbInternal(paramString);
    this.mSqlCaches.put(paramString, new SQLDbCache(localSQLiteDatabase1, true, System.currentTimeMillis()));
    removeIdealConnection();
    return localSQLiteDatabase1;
  }

  public void init(Context paramContext)
  {
    if (this._context != null)
      return;
    this._context = paramContext;
    createFavouriteChannelTable();
    createPlayHistoryTable();
    createReserveProgramTable();
    createContentCategoryTable();
    createCategoryNodeTable();
    createCategoryAttributesTable();
    createSubCategoryNodeTable();
    createChannelNodeTable();
    createDataCenterTable();
    createFrontPageTable();
    createRadioNodesTable();
    createFreqChannelTable();
    createAlarmInfoTable();
    createUserInfoTable();
    createCommonRecordsTable();
    createUrlAttrTable();
    createSocialUserProfileTable();
    createProgramNodeTable();
    createPullMsgStateTable();
    createPullListTable();
    createPlayedMetaTable();
    createPlayListTable();
    createBillboardTable();
    createDownloadingProgramNodeTable();
    createPreDownloadProgramNodeTable();
    createRecCategoryTable();
    createGenericObjTable();
    createIMDatabase();
    createIMContacts();
    createIMUserInfo();
    createPodcasterInfoTable();
    createPodcasterFollowTable();
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("key", "VARCHAR(50)");
    localHashMap2.put("value", "VARCHAR(500)");
    localHashMap1.put("profile", localHashMap2);
    this._profileHelp = new DBHelper(localHashMap1, null, this._context, "profile", null, 1, this);
    HashMap localHashMap3 = new HashMap();
    HashMap localHashMap4 = new HashMap();
    localHashMap4.put("token", "VARCHAR(50)");
    localHashMap4.put("expires", "double");
    localHashMap4.put("openid", "VARCHAR(50)");
    localHashMap4.put("type", "VARCHAR(20)");
    localHashMap3.put("accessToken", localHashMap4);
    this._weiboHelp = new DBHelper(localHashMap3, null, this._context, "weibo", null, 2, this);
  }

  public void loadUrlAttrfromDB()
  {
    SQLiteDatabase localSQLiteDatabase = getInstance().getReadableDB("url_attr");
    Cursor localCursor;
    try
    {
      localCursor = localSQLiteDatabase.rawQuery("select * from urlattrs", null);
      Gson localGson = new Gson();
      while (localCursor.moveToNext())
      {
        String str = localCursor.getString(localCursor.getColumnIndex("url"));
        UrlAttr localUrlAttr = (UrlAttr)localGson.fromJson(localCursor.getString(localCursor.getColumnIndex("urlattr")), UrlAttr.class);
        urlAttrMap.put(str, localUrlAttr);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    localCursor.close();
    HTTPConnection.setUrlAttrMap(urlAttrMap);
  }

  public boolean onCreate(SQLiteDatabase paramSQLiteDatabase, String paramString)
  {
    return false;
  }

  public void onCreateComplete(SQLiteDatabase paramSQLiteDatabase, String paramString)
  {
  }

  public boolean onUpgrade(SQLiteDatabase paramSQLiteDatabase, String paramString, int paramInt1, int paramInt2)
  {
    if ((paramString != null) && (paramString.equalsIgnoreCase("playhistory")) && (paramInt1 == 1) && (paramInt2 == 2))
    {
      movePlayhistory(paramSQLiteDatabase);
      return true;
    }
    if (((paramInt1 <= 3) || (paramInt1 >= 9) || (!paramString.equalsIgnoreCase("qtradio"))) || (((paramInt1 > 1) && (paramInt1 < 9) && (paramString.equalsIgnoreCase("qtradio"))) || (paramString.equalsIgnoreCase("profile"))))
    {
      Cursor localCursor5 = paramSQLiteDatabase.rawQuery("select key, value from profile", null);
      while (localCursor5.moveToNext())
      {
        String str7 = localCursor5.getString(localCursor5.getColumnIndex("key"));
        String str8 = localCursor5.getString(localCursor5.getColumnIndex("value"));
        if (this.profile == null)
          this.profile = new ArrayList();
        HashMap localHashMap5 = new HashMap();
        localHashMap5.put("key", str7);
        localHashMap5.put("value", str8);
        this.profile.add(localHashMap5);
      }
      localCursor5.close();
      if (!paramString.equalsIgnoreCase("profile"))
        moveData(getWritableDB("profile"), "profile");
    }
    if (((paramInt1 > 4) && (paramInt1 < 9) && (paramString.equalsIgnoreCase("qtradio"))) || (paramString.equalsIgnoreCase("alarm")))
    {
      Cursor localCursor4 = paramSQLiteDatabase.rawQuery("select available, cycle, time, type, program from alarm", null);
      while (localCursor4.moveToNext())
      {
        int n = localCursor4.getInt(localCursor4.getColumnIndex("available"));
        int i1 = localCursor4.getInt(localCursor4.getColumnIndex("cycle"));
        int i2 = localCursor4.getInt(localCursor4.getColumnIndex("time"));
        int i3 = localCursor4.getInt(localCursor4.getColumnIndex("type"));
        String str6 = localCursor4.getString(localCursor4.getColumnIndex("program"));
        if (this.alarm == null)
          this.alarm = new ArrayList();
        HashMap localHashMap4 = new HashMap();
        localHashMap4.put("available", Integer.valueOf(n));
        localHashMap4.put("cycle", Integer.valueOf(i1));
        localHashMap4.put("time", Integer.valueOf(i2));
        localHashMap4.put("type", Integer.valueOf(i3));
        localHashMap4.put("program", str6);
        this.alarm.add(localHashMap4);
      }
      localCursor4.close();
      if (!paramString.equalsIgnoreCase("alarm"))
        moveData(getWritableDB("alarm"), "alarm");
    }
    if (((paramInt1 == 8) && (paramString.equalsIgnoreCase("qtradio"))) || (paramString.equalsIgnoreCase("weibo")))
      if ((paramInt1 != 8) || (!paramString.equalsIgnoreCase("qtradio")))
        break label1231;
    label1231: for (int i = 1; ; i = 0)
    {
      if ((paramInt1 == 1) && (paramString.equalsIgnoreCase("weibo")));
      for (int j = 1; ; j = 0)
      {
        if (j != 0);
        Cursor localCursor1;
        for (String str1 = "select token, expires from accessToken"; ; str1 = "select token, expires, openid, type from accessToken")
        {
          localCursor1 = paramSQLiteDatabase.rawQuery(str1, null);
          while (localCursor1.moveToNext())
          {
            String str4 = localCursor1.getString(localCursor1.getColumnIndex("token"));
            long l3 = localCursor1.getLong(localCursor1.getColumnIndex("expires"));
            if (this.accessToken == null)
              this.accessToken = new ArrayList();
            HashMap localHashMap3 = new HashMap();
            localHashMap3.put("token", str4);
            localHashMap3.put("expires", Long.valueOf(l3));
            if (j == 0)
            {
              String str5 = localCursor1.getString(localCursor1.getColumnIndex("type"));
              localHashMap3.put("openid", localCursor1.getString(localCursor1.getColumnIndex("openid")));
              localHashMap3.put("type", str5);
            }
            this.accessToken.add(localHashMap3);
          }
        }
        localCursor1.close();
        Cursor localCursor2 = paramSQLiteDatabase.rawQuery("select id, lasttime from signin", null);
        if (localCursor2.moveToNext())
        {
          String str3 = localCursor2.getString(localCursor2.getColumnIndex("id"));
          if (i != 0);
          for (long l2 = localCursor2.getInt(localCursor2.getColumnIndex("lasttime")); ; l2 = ()localCursor2.getDouble(localCursor2.getColumnIndex("lasttime")))
          {
            if (this.signin == null)
              this.signin = new ArrayList();
            HashMap localHashMap2 = new HashMap();
            localHashMap2.put("id", str3);
            localHashMap2.put("lasttime", Long.valueOf(l2));
            this.signin.add(localHashMap2);
            break;
          }
        }
        localCursor2.close();
        Cursor localCursor3 = paramSQLiteDatabase.rawQuery("select id, lasttime, diggcount, total from flowers", null);
        if (localCursor3.moveToNext())
        {
          String str2 = localCursor3.getString(localCursor3.getColumnIndex("id"));
          if (i != 0);
          for (long l1 = localCursor3.getInt(localCursor3.getColumnIndex("lasttime")); ; l1 = ()localCursor3.getDouble(localCursor3.getColumnIndex("lasttime")))
          {
            int k = localCursor3.getInt(localCursor3.getColumnIndex("diggcount"));
            int m = localCursor3.getInt(localCursor3.getColumnIndex("total"));
            if (this.flower == null)
              this.flower = new ArrayList();
            HashMap localHashMap1 = new HashMap();
            localHashMap1.put("id", str2);
            localHashMap1.put("lasttime", Long.valueOf(l1));
            localHashMap1.put("diggcount", Integer.valueOf(k));
            localHashMap1.put("total", Integer.valueOf(m));
            this.flower.add(localHashMap1);
            break;
          }
        }
        localCursor3.close();
        if (!paramString.equalsIgnoreCase("weibo"))
          moveData(getWritableDB("weibo"), "weibo");
        return false;
      }
    }
  }

  public void onUpgradeComplete(SQLiteDatabase paramSQLiteDatabase, String paramString, int paramInt1, int paramInt2)
  {
  }

  public void quit()
  {
    storeUrlAttrToDB();
    clearConnectionPool();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.DBManager
 * JD-Core Version:    0.6.2
 */