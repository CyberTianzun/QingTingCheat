package fm.qingting.qtradio.view.im.chat;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.im.BaseUserInfoPool;
import fm.qingting.qtradio.im.BaseUserInfoPool.AvatarAndGender;
import fm.qingting.qtradio.im.BaseUserInfoPool.OnBaseUserinfoPutListener;
import fm.qingting.qtradio.im.IMAgent;
import fm.qingting.qtradio.im.IMContacts;
import fm.qingting.qtradio.im.UserProfileManager;
import fm.qingting.qtradio.im.info.GroupInfo;
import fm.qingting.qtradio.im.message.IMMessage;
import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.logger.QTLogger;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.RootNode.IInfoUpdateEventListener;
import fm.qingting.qtradio.room.AdminInfo;
import fm.qingting.qtradio.room.SnsInfo;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.share.ShareUtil;
import fm.qingting.qtradio.social.CloudCenter;
import fm.qingting.qtradio.social.CloudCenter.OnLoginEventListerner;
import fm.qingting.qtradio.social.UserProfile;
import fm.qingting.qtradio.view.chatroom.ChatActionsView;
import fm.qingting.qtradio.view.chatroom.ChatActionsView.ChatActionType;
import fm.qingting.qtradio.view.chatroom.chatlist.ChatItem;
import fm.qingting.qtradio.view.chatroom.chatlist.ChatroomTimestampView;
import fm.qingting.qtradio.view.chatroom.chatlist.IChatAdapterIViewFactory;
import fm.qingting.qtradio.view.chatroom.expression.ExpressionView;
import fm.qingting.qtradio.view.im.ChatMode;
import fm.qingting.qtradio.view.im.ImChatInviteView;
import fm.qingting.qtradio.view.im.profile.UserActionView;
import fm.qingting.utils.QTMSGManage;
import fm.qingting.utils.ScreenType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class ImChatMainView extends ViewGroupViewImpl
  implements IEventHandler, RootNode.IInfoUpdateEventListener, BaseUserInfoPool.OnBaseUserinfoPutListener
{
  public static final String ADD_HISTORY = "addhistory";
  public static final String ADD_MESSAGE = "addmessage";
  public static final String ADD_MESSAGES = "addMessages";
  private static final String[] CONTENTS = { "虽然这里有上千个主播，百万的节目，一天二十四小时不断更新，但我还是觉得@%s 的节目最有趣，献朵小花支持你！你又支持谁？下载链接：http://qingting.fm/app/download_xiaoyuan", "分享一个@蜻蜓FM 的群组%s,边听节目边聊天真的好欢乐~蜻蜓FM，倾听世界的声音。下载链接：http://qingting.fm/app/download_zazhi", "报告大家！发现一个新功能，只要使用@蜻蜓FM 收听【%s】就可以在群组里和%s全国各地的听众聊天啦！赶紧来【%s】群里和大家一起贫吧！下载请戳：http://qingting.fm/app/download_xiaoyuan2014", "用了@蜻蜓FM 才知道原来声音的世界也那么精彩~小说，音乐，娱乐八卦甚至还有全国的校园电台，一天二十四小时听不完的节目！和全国听友边听边聊还能和电台主播们实时互动，新时代的电台就是这么拽！下载链接：http://qingting.fm/app/download_quange" };
  private static final int NORMAL = 0;
  private static final int SHOWINGEXPRESSION = 1;
  private static final int SHOWINGINVITE = 2;
  private static final int SHOWINGMORE = 3;
  private final long TIME_INTERVAL = 1800L;
  private final ViewLayout bottomLayout = this.standardLayout.createChildLT(720, 106, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout chatMemberLayout = this.standardLayout.createChildLT(720, 380, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout checkinLayout = this.standardLayout.createChildLT(156, 74, 564, 130, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout headerLayout = this.standardLayout.createChildLT(720, 256, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private boolean isInputShowing = false;
  private ChatActionsView mActionsView;
  private ImChatAdapter mAdapter;
  private HashSet<String> mAdmins = new HashSet();
  private Calendar mCalendar;
  private Button mCheckinButton;
  private List<ChatItem> mDatas = new ArrayList();
  private int mDay;
  private ExpressionView mExpressionView;
  private IChatAdapterIViewFactory mFactory;
  private ImChatHeadView mHeadView;
  private ImChatInputView mInputView;
  private ImChatInviteView mInviteView;
  private UserActionView mJoinView;
  private long mLastTimestamp = 0L;
  private LinearLayout mListContainer;
  private PullToRefreshListView mListView;
  private boolean mLoading = false;
  private IMMessage mMessage;
  private long mOldestMsgSeq = 0L;
  private long mOldestTimeStamp = 0L;
  private boolean mTalkingBlocked = false;
  private GroupInfo mTalkingGroupInfo;
  private String mTalkingId;
  private UserInfo mTalkingUserInfo;
  private int mViewState = 0;
  private int normalLayoutHeight = 0;
  private int specialLayoutHeight = -1;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);

  public ImChatMainView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(-1118482);
    this.mFactory = new IChatAdapterIViewFactory()
    {
      public IView createView(int paramAnonymousInt)
      {
        switch (paramAnonymousInt)
        {
        default:
          return null;
        case 16:
          return new ImChatItemLeftView(ImChatMainView.this.getContext(), this.val$hash, true);
        case 0:
          return new ImChatItemRightView(ImChatMainView.this.getContext(), this.val$hash);
        case 32:
        }
        return new ChatroomTimestampView(ImChatMainView.this.getContext());
      }
    };
    this.mAdapter = new ImChatAdapter(this.mDatas, this.mFactory);
    this.mListContainer = ((LinearLayout)LayoutInflater.from(paramContext).inflate(2130903040, null));
    this.mListView = ((PullToRefreshListView)this.mListContainer.findViewById(2131230731));
    this.mListView.setVerticalScrollBarEnabled(false);
    this.mListView.setVerticalFadingEdgeEnabled(false);
    this.mListView.setSelector(17170445);
    addView(this.mListContainer);
    this.mListView.setAdapter(this.mAdapter);
    this.mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener()
    {
      public void onRefresh(PullToRefreshBase<ListView> paramAnonymousPullToRefreshBase)
      {
        String str = DateUtils.formatDateTime(ImChatMainView.this.getContext(), System.currentTimeMillis(), 524305);
        paramAnonymousPullToRefreshBase.getLoadingLayoutProxy().setLastUpdatedLabel(str);
        new ImChatMainView.GetDataTask(ImChatMainView.this, null).execute(new Void[0]);
      }
    });
    this.mHeadView = new ImChatHeadView(paramContext);
    addView(this.mHeadView);
    this.mHeadView.setEventHandler(this);
    this.mInputView = new ImChatInputView(paramContext);
    addView(this.mInputView);
    this.mInputView.setEventHandler(this);
    this.mActionsView = new ChatActionsView(paramContext);
    this.mActionsView.setEventHandler(this);
    addView(this.mActionsView);
    this.mExpressionView = new ExpressionView(paramContext);
    this.mExpressionView.setEventHandler(this);
    addView(this.mExpressionView);
    this.mInviteView = new ImChatInviteView(paramContext);
    this.mInviteView.setEventHandler(this);
    addView(this.mInviteView);
    this.mCheckinButton = new Button(paramContext);
    this.mCheckinButton.setBackgroundResource(2130837574);
    this.mCheckinButton.setText("签到");
    addView(this.mCheckinButton);
    this.mCheckinButton.setTextColor(-1);
    this.mCheckinButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ImChatMainView.this.checkIn();
      }
    });
    this.mJoinView = new UserActionView(paramContext);
    this.mJoinView.update("setData", "加入群聊");
    addView(this.mJoinView);
    this.mJoinView.setEventHandler(this);
    this.mJoinView.setVisibility(8);
    resetBaseTime();
    BaseUserInfoPool.addListener(this);
  }

  private void actualCheckin()
  {
    if (this.mTalkingGroupInfo == null)
      return;
    if (!IMContacts.getInstance().hasWatchedGroup(this.mTalkingId))
      InfoManager.getInstance().getUserProfile().followGroup(this.mTalkingId);
    String str1 = "";
    List localList = this.mTalkingGroupInfo.lstAdmins;
    int i;
    Object localObject;
    UserInfo localUserInfo;
    String str6;
    String str4;
    if ((localList != null) && (localList.size() > 0))
    {
      i = 0;
      localObject = str1;
      if (i < localList.size())
      {
        localUserInfo = (UserInfo)localList.get(i);
        if ((localUserInfo instanceof AdminInfo))
        {
          str6 = ((AdminInfo)localUserInfo).weiboName;
          if ((str6 != null) && (str6.length() != 0))
            break label290;
          str4 = localUserInfo.snsInfo.sns_name;
        }
      }
    }
    while (true)
    {
      String str5 = (String)localObject + "@" + str4 + " ";
      i++;
      localObject = str5;
      break;
      str4 = localUserInfo.snsInfo.sns_name;
      continue;
      str1 = (String)localObject + "还有";
      Locale localLocale = Locale.CHINESE;
      String str2 = CONTENTS[2];
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = this.mTalkingGroupInfo.groupName;
      arrayOfObject[1] = str1;
      arrayOfObject[2] = this.mTalkingGroupInfo.groupName;
      String str3 = String.format(localLocale, str2, arrayOfObject);
      sendMessage("签个到，大家好呀~~", 1);
      QTMSGManage.getInstance().sendStatistcsMessage("im_checkin");
      ShareUtil.shareToPlatform(str3, 8, 9);
      return;
      label290: str4 = str6;
    }
  }

  private void actualFlowerToUser(UserInfo paramUserInfo)
  {
    if (!IMContacts.getInstance().hasWatchedGroup(this.mTalkingId))
      InfoManager.getInstance().getUserProfile().followGroup(this.mTalkingId);
    if ((paramUserInfo instanceof AdminInfo))
    {
      str1 = ((AdminInfo)paramUserInfo).weiboName;
      if ((str1 != null) && (str1.length() != 0));
    }
    for (String str1 = paramUserInfo.snsInfo.sns_name; ; str1 = paramUserInfo.snsInfo.sns_name)
    {
      String str2 = String.format(Locale.CHINESE, CONTENTS[0], new Object[] { str1 });
      QTMSGManage.getInstance().sendStatistcsMessage("im_flower");
      ShareUtil.shareToPlatform(str2, 10, 11);
      return;
    }
  }

  private void actualSendMessage(String paramString, int paramInt)
  {
    int i = 4;
    if (this.mLoading)
    {
      this.mDatas.clear();
      this.mLoading = false;
    }
    String str = InfoManager.getInstance().getUserProfile().getUserKey();
    if (ChatMode.isGroup())
    {
      if ((IMAgent.getInstance().isCheckin(paramInt)) && (this.mTalkingGroupInfo != null) && (!IMAgent.getInstance().hasCheckIn(this.mTalkingGroupInfo.groupId)))
        paramString = IMAgent.getInstance().getCheckinText();
      IMAgent.getInstance().sendGroupMsg(paramString, this.mTalkingGroupInfo, paramInt);
      IMMessage localIMMessage2 = new IMMessage();
      localIMMessage2.mMessage = paramString;
      localIMMessage2.chatType = 1;
      localIMMessage2.publish = (System.currentTimeMillis() / 1000L);
      long l2 = localIMMessage2.publish;
      if (l2 - this.mLastTimestamp >= 1800L)
      {
        this.mDatas.add(new ChatItem(32, getTimestampBySecond(l2)));
        this.mLastTimestamp = l2;
      }
      List localList2 = this.mDatas;
      if (isAdmin(str));
      while (true)
      {
        localList2.add(new ChatItem(i, localIMMessage2));
        IMContacts.getInstance().addRecentContacts(this.mTalkingGroupInfo);
        this.mAdapter.notifyDataSetChanged();
        return;
        i = 1;
      }
    }
    if (this.mTalkingBlocked)
    {
      Toast.makeText(getContext(), "该账号已经被举报,无法接收消息", 1).show();
      return;
    }
    IMAgent.getInstance().sendUserMsg(paramString, this.mTalkingUserInfo, paramInt);
    IMMessage localIMMessage1 = new IMMessage();
    localIMMessage1.mMessage = paramString;
    localIMMessage1.chatType = 0;
    localIMMessage1.publish = (System.currentTimeMillis() / 1000L);
    long l1 = localIMMessage1.publish;
    if (l1 - this.mLastTimestamp >= 1800L)
    {
      this.mDatas.add(new ChatItem(32, getTimestampBySecond(l1)));
      this.mLastTimestamp = l1;
    }
    List localList1 = this.mDatas;
    if (isAdmin(str));
    while (true)
    {
      localList1.add(new ChatItem(i, localIMMessage1));
      IMContacts.getInstance().addRecentContacts(this.mTalkingUserInfo);
      break;
      i = 1;
    }
  }

  private final void actualShareGroup()
  {
    if (this.mTalkingGroupInfo == null)
      return;
    Locale localLocale = Locale.CHINESE;
    String str1 = CONTENTS[1];
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.mTalkingGroupInfo.groupName;
    String str2 = String.format(localLocale, str1, arrayOfObject);
    QTMSGManage.getInstance().sendStatistcsMessage("im_share");
    ShareUtil.shareToPlatform(str2, 1, 2);
  }

  private void addHistoryMessages(List<IMMessage> paramList)
  {
    String str = InfoManager.getInstance().getUserProfile().getUserKey();
    int i;
    int j;
    int k;
    long l1;
    int m;
    label41: IMMessage localIMMessage;
    boolean bool2;
    int n;
    label152: long l2;
    if (this.mDatas.size() == 0)
    {
      i = 1;
      j = paramList.size();
      k = 0;
      l1 = 0L;
      m = 0;
      if (m >= j)
        break label275;
      localIMMessage = (IMMessage)paramList.get(m);
      if (m == 0)
      {
        this.mOldestMsgSeq = localIMMessage.msgSeq;
        long l3 = localIMMessage.publish;
        if ((i == 0) && (l3 > this.mOldestTimeStamp - 1800L))
          this.mDatas.remove(0);
        this.mOldestTimeStamp = l3;
      }
      boolean bool1 = TextUtils.equals(str, localIMMessage.mFromID);
      bool2 = isAdmin(localIMMessage.mFromID);
      if (!bool1)
        break label256;
      if (!bool2)
        break label250;
      n = 4;
      l2 = localIMMessage.publish;
      if (l2 - l1 < 1800L)
        break label337;
      this.mDatas.add(m + k, new ChatItem(32, getTimestampBySecond(l2)));
    }
    for (int i1 = k + 1; ; i1 = k)
    {
      this.mDatas.add(m + i1, new ChatItem(n, localIMMessage));
      m++;
      k = i1;
      l1 = l2;
      break label41;
      i = 0;
      break;
      label250: n = 1;
      break label152;
      label256: if (bool2)
      {
        n = 20;
        break label152;
      }
      n = 17;
      break label152;
      label275: this.mAdapter.notifyDataSetChanged();
      this.mListView.onRefreshComplete();
      if (i != 0)
      {
        if (this.mLastTimestamp < l1)
          this.mLastTimestamp = l1;
        this.mListView.setSelection(this.mAdapter.getCount());
        return;
      }
      this.mListView.setSelection(j + k);
      return;
      label337: l2 = l1;
    }
  }

  private void changeChatMode()
  {
    switch (ChatMode.getCurrentMode())
    {
    default:
    case 0:
    case 1:
    }
    while (true)
    {
      this.mInputView.update("changeMode", null);
      return;
      this.mCheckinButton.setVisibility(8);
      this.mJoinView.setVisibility(8);
      continue;
      this.mCheckinButton.setVisibility(0);
      this.mJoinView.setVisibility(8);
    }
  }

  private void checkIn()
  {
    if (!CloudCenter.getInstance().isLogin())
    {
      CloudCenter.OnLoginEventListerner local7 = new CloudCenter.OnLoginEventListerner()
      {
        public void onLoginFailed(int paramAnonymousInt)
        {
        }

        public void onLoginSuccessed(int paramAnonymousInt)
        {
          ImChatMainView.this.actualCheckin();
        }
      };
      EventDispacthManager.getInstance().dispatchAction("showLogin", local7);
      return;
    }
    actualCheckin();
  }

  private void flowerToUser(final UserInfo paramUserInfo)
  {
    if (!CloudCenter.getInstance().isLogin())
    {
      CloudCenter.OnLoginEventListerner local8 = new CloudCenter.OnLoginEventListerner()
      {
        public void onLoginFailed(int paramAnonymousInt)
        {
        }

        public void onLoginSuccessed(int paramAnonymousInt)
        {
          ImChatMainView.this.actualFlowerToUser(paramUserInfo);
          ImChatMainView.this.mJoinView.setVisibility(8);
        }
      };
      EventDispacthManager.getInstance().dispatchAction("showLogin", local8);
      return;
    }
    actualFlowerToUser(paramUserInfo);
  }

  private int getActionsOffset()
  {
    if (this.mViewState == 3)
      return this.mActionsView.getMeasuredHeight();
    return 0;
  }

  private int getExpressionOffset()
  {
    if (this.mViewState == 1)
      return this.mExpressionView.getMeasuredHeight();
    return 0;
  }

  private int getInputOffset()
  {
    if (this.isInputShowing)
      return 0;
    switch (this.mViewState)
    {
    default:
      return 0;
    case 1:
      return this.mExpressionView.getMeasuredHeight();
    case 3:
      return this.mActionsView.getMeasuredHeight();
    case 2:
    }
    return this.mInviteView.getMeasuredHeight();
  }

  private int getInviteOffset()
  {
    if (this.mViewState == 2)
      return this.mInviteView.getMeasuredHeight();
    return 0;
  }

  private String getTimeInDay(int paramInt1, int paramInt2)
  {
    String str;
    if (paramInt1 < 6)
      str = "凌晨";
    while (true)
    {
      Locale localLocale = Locale.CHINESE;
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = str;
      arrayOfObject[1] = Integer.valueOf(paramInt1);
      arrayOfObject[2] = Integer.valueOf(paramInt2);
      return String.format(localLocale, "%s%02d:%02d", arrayOfObject);
      if (paramInt1 < 12)
        str = "早上";
      else if (paramInt1 < 13)
        str = "中午";
      else if (paramInt1 < 18)
        str = "下午";
      else
        str = "晚上";
    }
  }

  private String getTimestampBySecond(long paramLong)
  {
    int i = 11;
    this.mCalendar.setTimeInMillis(1000L * paramLong);
    int j = this.mCalendar.get(6);
    int k = this.mCalendar.get(i);
    int m = this.mCalendar.get(12);
    if (j == this.mDay)
      return getTimeInDay(k, m);
    if (j == -1 + this.mDay)
      return "昨天 " + getTimeInDay(k, m);
    int n = this.mCalendar.get(2);
    int i1 = this.mCalendar.get(5);
    if (i1 == 12);
    while (true)
    {
      Locale localLocale = Locale.CHINESE;
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = Integer.valueOf(n + 1);
      arrayOfObject[1] = Integer.valueOf(i);
      arrayOfObject[2] = getTimeInDay(k, m);
      return String.format(localLocale, "%d月%d日 %s", arrayOfObject);
      i = i1;
    }
  }

  private void invalidateAvatar(String paramString)
  {
    int i = this.mListView.getListChildCnt();
    for (int j = 0; j < i; j++)
    {
      View localView = this.mListView.getListChildAt(j);
      if ((localView != null) && ((localView instanceof IView)))
        ((IView)localView).update("invalidateAvatar", paramString);
    }
  }

  private void invite(int paramInt)
  {
    QTMSGManage.getInstance().sendStatistcsMessage("im_invite", String.valueOf(paramInt));
    ShareUtil.inviteByPlatformIm(getContext(), paramInt, CONTENTS[3]);
  }

  private boolean isAdmin(String paramString)
  {
    return this.mAdmins.contains(paramString);
  }

  private void layoutViews(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = this.mHeadView.getMeasuredHeight();
    int j = ScreenType.getNaviHeight() / 2;
    int k = this.standardLayout.height;
    int m = this.mInputView.getMeasuredHeight();
    if (this.isInputShowing)
      try
      {
        this.mListContainer.layout(0, i, this.standardLayout.width, k - m);
        this.mHeadView.layout(0, 0, this.standardLayout.width, i);
        this.mInputView.layout(0, k - m, this.standardLayout.width, k);
        this.mJoinView.layout(0, k - m, this.standardLayout.width, k);
        this.mExpressionView.layout(0, k, this.standardLayout.width, k + this.chatMemberLayout.height);
        this.mActionsView.layout(0, k, this.standardLayout.width, k + this.mActionsView.getMeasuredHeight());
        this.mInviteView.layout(0, k, this.standardLayout.width, k + this.mInviteView.getMeasuredHeight());
        this.mCheckinButton.layout(this.checkinLayout.leftMargin, i + j, this.checkinLayout.getRight(), i + j + this.checkinLayout.height);
        return;
      }
      catch (IllegalStateException localIllegalStateException2)
      {
        while (true)
          localIllegalStateException2.printStackTrace();
      }
    int n = getInputOffset();
    try
    {
      this.mListContainer.layout(0, i, this.standardLayout.width, k - m - n);
      this.mHeadView.layout(0, 0, this.standardLayout.width, i);
      this.mInputView.layout(0, k - m - n, this.standardLayout.width, k - n);
      this.mJoinView.layout(0, k - m - n, this.standardLayout.width, k - n);
      int i1 = getExpressionOffset();
      this.mExpressionView.layout(0, k - i1, this.standardLayout.width, k + this.mExpressionView.getMeasuredHeight() - i1);
      int i2 = getActionsOffset();
      this.mActionsView.layout(0, k - i2, this.standardLayout.width, k + this.mActionsView.getMeasuredHeight() - i2);
      int i3 = getInviteOffset();
      this.mInviteView.layout(0, k - i3, this.standardLayout.width, k + this.mInviteView.getMeasuredHeight() - i3);
      this.mCheckinButton.layout(this.checkinLayout.leftMargin, i + j, this.checkinLayout.getRight(), i + j + this.checkinLayout.height);
      return;
    }
    catch (IllegalStateException localIllegalStateException1)
    {
      while (true)
        localIllegalStateException1.printStackTrace();
    }
  }

  private void loadMoreHistory()
  {
    dispatchActionEvent("loadMore", Long.valueOf(this.mOldestMsgSeq));
  }

  private void resetBaseTime()
  {
    if (this.mCalendar == null)
      this.mCalendar = Calendar.getInstance();
    this.mCalendar.setTimeInMillis(System.currentTimeMillis());
    this.mDay = this.mCalendar.get(6);
  }

  private void sendMessage(String paramString, int paramInt)
  {
    if ((ChatMode.isGroup()) && (!IMContacts.getInstance().hasWatchedGroup(this.mTalkingId)))
      InfoManager.getInstance().getUserProfile().followGroup(this.mTalkingId);
    actualSendMessage(paramString, paramInt);
  }

  private void shareGroup()
  {
    if (!CloudCenter.getInstance().isLogin())
    {
      CloudCenter.OnLoginEventListerner local6 = new CloudCenter.OnLoginEventListerner()
      {
        public void onLoginFailed(int paramAnonymousInt)
        {
        }

        public void onLoginSuccessed(int paramAnonymousInt)
        {
          ImChatMainView.this.actualShareGroup();
        }
      };
      EventDispacthManager.getInstance().dispatchAction("showLogin", local6);
      return;
    }
    actualShareGroup();
  }

  public void close(boolean paramBoolean)
  {
    this.mInputView.close(paramBoolean);
    this.mHeadView.close(paramBoolean);
    this.mActionsView.close(paramBoolean);
    this.mInviteView.close(paramBoolean);
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    BaseUserInfoPool.removeListener(this);
    super.close(paramBoolean);
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    if ((!this.isInputShowing) && (this.mViewState == 1))
    {
      if ((paramKeyEvent.getKeyCode() == 4) && (paramKeyEvent.getAction() == 1))
      {
        this.mViewState = 0;
        requestLayout();
        return true;
      }
    }
    else if ((!this.isInputShowing) && (this.mViewState == 2) && (paramKeyEvent.getKeyCode() == 4) && (paramKeyEvent.getAction() == 1))
    {
      this.mViewState = 0;
      requestLayout();
      return true;
    }
    return super.dispatchKeyEvent(paramKeyEvent);
  }

  public Object getValue(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("keyboardState"))
      return Boolean.valueOf(this.isInputShowing);
    return super.getValue(paramString, paramObject);
  }

  public void onBaseInfoPut(String paramString, BaseUserInfoPool.AvatarAndGender paramAvatarAndGender)
  {
    if (paramString == null)
      return;
    invalidateAvatar(paramString);
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("sendDiscuss"))
      sendMessage((String)paramObject2, 0);
    ChatActionsView.ChatActionType localChatActionType;
    do
    {
      return;
      if (paramString.equalsIgnoreCase("expression"))
      {
        if (this.isInputShowing)
        {
          this.mViewState = 1;
          this.mInputView.update("closeKeyboard", null);
          return;
        }
        if (this.mViewState == 1)
        {
          this.mViewState = 0;
          requestLayout();
          return;
        }
        if (!CloudCenter.getInstance().isLogin())
        {
          CloudCenter.OnLoginEventListerner local4 = new CloudCenter.OnLoginEventListerner()
          {
            public void onLoginFailed(int paramAnonymousInt)
            {
            }

            public void onLoginSuccessed(int paramAnonymousInt)
            {
              QTMSGManage.getInstance().sendStatistcsMessage("chat_functionclick", "expression");
              ImChatMainView.access$302(ImChatMainView.this, 1);
              ImChatMainView.this.mInputView.update("closeKeyboard", null);
              ImChatMainView.this.requestLayout();
            }
          };
          EventDispacthManager.getInstance().dispatchAction("showLogin", local4);
          return;
        }
        QTMSGManage.getInstance().sendStatistcsMessage("chat_functionclick", "expression");
        this.mViewState = 1;
        this.mInputView.update("closeKeyboard", null);
        requestLayout();
        return;
      }
      if (paramString.equalsIgnoreCase("expand"))
      {
        if (this.isInputShowing)
        {
          this.mViewState = 3;
          this.mInputView.update("closeKeyboard", null);
          return;
        }
        if (this.mViewState == 3)
        {
          this.mViewState = 0;
          requestLayout();
          return;
        }
        this.mViewState = 3;
        requestLayout();
        return;
      }
      if (paramString.equalsIgnoreCase("inviteFriends"))
      {
        if (this.isInputShowing)
        {
          this.mViewState = 2;
          this.mInputView.update("closeKeyboard", null);
          return;
        }
        if (this.mViewState != 2)
        {
          this.mViewState = 2;
          requestLayout();
          return;
        }
        this.mViewState = 0;
        requestLayout();
        return;
      }
      if (paramString.equalsIgnoreCase("selectExpression"))
      {
        this.mInputView.update("addExpression", paramObject2);
        return;
      }
      if (paramString.equalsIgnoreCase("deleteExpression"))
      {
        this.mInputView.update(paramString, paramObject2);
        return;
      }
      if (paramString.equalsIgnoreCase("scrollToFirstIndexAtMe"))
      {
        dispatchActionEvent(paramString, paramObject2);
        return;
      }
      if (paramString.equalsIgnoreCase("useraction"))
      {
        if (!CloudCenter.getInstance().isLogin())
        {
          CloudCenter.OnLoginEventListerner local5 = new CloudCenter.OnLoginEventListerner()
          {
            public void onLoginFailed(int paramAnonymousInt)
            {
            }

            public void onLoginSuccessed(int paramAnonymousInt)
            {
              InfoManager.getInstance().getUserProfile().followGroup(ImChatMainView.this.mTalkingId);
              ImChatMainView.this.mJoinView.setVisibility(8);
            }
          };
          EventDispacthManager.getInstance().dispatchAction("showLogin", local5);
          return;
        }
        InfoManager.getInstance().getUserProfile().followGroup(this.mTalkingId);
        this.mJoinView.setVisibility(8);
        return;
      }
      if (paramString.equalsIgnoreCase("flowerToAdmin"))
      {
        flowerToUser((UserInfo)paramObject2);
        return;
      }
      if (!paramString.equalsIgnoreCase("chatActionType"))
        break;
      localChatActionType = (ChatActionsView.ChatActionType)paramObject2;
      if (localChatActionType == ChatActionsView.ChatActionType.SHARE)
      {
        shareGroup();
        return;
      }
      if (localChatActionType == ChatActionsView.ChatActionType.ASKNAME)
      {
        sendMessage("现在播的什么歌？", 0);
        return;
      }
    }
    while ((localChatActionType == ChatActionsView.ChatActionType.ANSWERNAME) || (localChatActionType != ChatActionsView.ChatActionType.COLLECTION));
    return;
    if (paramString.equalsIgnoreCase("shareToPlatform"))
    {
      invite(((Integer)paramObject2).intValue());
      return;
    }
    dispatchActionEvent(paramString, paramObject2);
  }

  public void onInfoUpdated(int paramInt)
  {
    if (paramInt == 3)
      if (this.mMessage != null)
      {
        UserProfile localUserProfile = UserProfileManager.getInstance().getUserProfile(this.mTalkingId);
        if (localUserProfile != null)
          this.mTalkingUserInfo = localUserProfile.getUserInfo();
      }
    GroupInfo localGroupInfo;
    do
    {
      do
        return;
      while ((paramInt != 6) || (!ChatMode.isGroup()) || ((this.mAdmins != null) && (this.mAdmins.size() != 0)));
      localGroupInfo = IMAgent.getInstance().getGroupInfo(this.mTalkingId);
    }
    while (localGroupInfo == null);
    if ((localGroupInfo.lstAdmins != null) && (localGroupInfo.lstAdmins.size() > 0))
      for (int i = 0; i < localGroupInfo.lstAdmins.size(); i++)
        this.mAdmins.add(((UserInfo)localGroupInfo.lstAdmins.get(i)).userKey);
    this.mHeadView.update("setData", localGroupInfo);
    this.mTalkingUserInfo = null;
    this.mTalkingGroupInfo = localGroupInfo;
    this.mTalkingBlocked = false;
    this.mMessage = null;
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getAction() == 0)
    {
      float f = paramMotionEvent.getY();
      if (this.isInputShowing)
      {
        if ((f > this.mHeadView.getMeasuredHeight()) && (f < this.standardLayout.height - this.mInputView.getMeasuredHeight()))
        {
          this.mViewState = 0;
          this.mInputView.update("closeKeyboard", null);
          return true;
        }
      }
      else if ((this.mViewState != 0) && (f > this.mHeadView.getMeasuredHeight()) && (f < this.standardLayout.height - getInputOffset() - this.mInputView.getMeasuredHeight()))
      {
        this.mViewState = 0;
        requestLayout();
        return true;
      }
    }
    return super.onInterceptTouchEvent(paramMotionEvent);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    layoutViews(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    this.mListView.setTranscriptMode(1);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.standardLayout.scaleToBounds(i, j);
    this.bottomLayout.scaleToBounds(this.standardLayout);
    this.checkinLayout.scaleToBounds(this.standardLayout);
    this.headerLayout.scaleToBounds(this.standardLayout);
    this.headerLayout.measureView(this.mHeadView);
    this.bottomLayout.measureView(this.mInputView);
    this.bottomLayout.measureView(this.mJoinView);
    int k = this.standardLayout.height;
    if (k > this.normalLayoutHeight)
    {
      this.specialLayoutHeight = k;
      if (this.normalLayoutHeight == 0)
        this.normalLayoutHeight = k;
    }
    if (k < this.normalLayoutHeight);
    for (this.isInputShowing = true; ; this.isInputShowing = false)
    {
      this.chatMemberLayout.scaleToBounds(this.standardLayout);
      this.chatMemberLayout.measureView(this.mExpressionView);
      this.chatMemberLayout.measureView(this.mActionsView);
      this.chatMemberLayout.measureView(this.mInviteView);
      int m = getInputOffset();
      this.mListContainer.measure(this.standardLayout.getWidthMeasureSpec(), View.MeasureSpec.makeMeasureSpec(k - this.mHeadView.getMeasuredHeight() - this.mInputView.getMeasuredHeight() - m, 1073741824));
      this.checkinLayout.measureView(this.mCheckinButton);
      this.mCheckinButton.setPadding((int)(0.3F * this.checkinLayout.width), 0, 0, 0);
      this.mCheckinButton.setTextSize(0, SkinManager.getInstance().getSubTextSize());
      setMeasuredDimension(this.standardLayout.width, k);
      return;
    }
  }

  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mListView.setTranscriptMode(2);
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public void setActivate(boolean paramBoolean)
  {
    if (paramBoolean)
      super.setActivate(paramBoolean);
  }

  public void update(String paramString, Object paramObject)
  {
    GroupInfo localGroupInfo2;
    GroupInfo localGroupInfo3;
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.mAdmins.clear();
      if ((paramObject instanceof GroupInfo))
      {
        localGroupInfo2 = (GroupInfo)paramObject;
        this.mTalkingId = localGroupInfo2.groupId;
        ChatMode.setMode(1);
        int m;
        if ((localGroupInfo2.lstAdmins != null) && (localGroupInfo2.lstAdmins.size() > 0))
          m = 0;
        while (m < localGroupInfo2.lstAdmins.size())
        {
          this.mAdmins.add(((UserInfo)localGroupInfo2.lstAdmins.get(m)).userKey);
          m++;
          continue;
          localGroupInfo3 = IMAgent.getInstance().getGroupInfo(this.mTalkingId);
          if (localGroupInfo3 != null)
            break label266;
          IMAgent.getInstance().loadGroupInfo(this.mTalkingId, this);
        }
        this.mTalkingUserInfo = null;
        this.mTalkingGroupInfo = localGroupInfo2;
        this.mTalkingBlocked = false;
        this.mMessage = null;
        QTMSGManage.getInstance().sendStatistcsMessage("groupChat", "enter");
        String str4 = QTLogger.getInstance().buildEnterIMLog(7);
        if (str4 != null)
          LogModule.getInstance().send("IMUI", str4);
        this.mHeadView.update("setData", localGroupInfo2);
        label213: this.mDatas.clear();
        this.mLastTimestamp = 0L;
        this.mLoading = true;
        this.mDatas.add(new ChatItem(32, "正在加载聊天消息"));
        this.mAdapter.notifyDataSetChanged();
        changeChatMode();
      }
    }
    label266: 
    do
    {
      return;
      localGroupInfo2.update(localGroupInfo3);
      if ((localGroupInfo2.lstAdmins == null) || (localGroupInfo2.lstAdmins.size() <= 0))
        break;
      for (int k = 0; k < localGroupInfo2.lstAdmins.size(); k++)
        this.mAdmins.add(((UserInfo)localGroupInfo2.lstAdmins.get(k)).userKey);
      break;
      if ((paramObject instanceof UserInfo))
      {
        UserInfo localUserInfo = (UserInfo)paramObject;
        this.mTalkingId = localUserInfo.userKey;
        ChatMode.setMode(0);
        this.mTalkingUserInfo = localUserInfo;
        this.mTalkingGroupInfo = null;
        this.mTalkingBlocked = localUserInfo.isBlocked;
        this.mMessage = null;
        QTMSGManage.getInstance().sendStatistcsMessage("userChat", "enter");
        String str3 = QTLogger.getInstance().buildEnterIMLog(8);
        if (str3 != null)
          LogModule.getInstance().send("IMUI", str3);
        this.mHeadView.update("setData", localUserInfo);
        break label213;
      }
      if ((paramObject instanceof IMMessage))
      {
        IMMessage localIMMessage2 = (IMMessage)paramObject;
        this.mTalkingId = localIMMessage2.mFromID;
        ChatMode.setMode(0);
        UserProfile localUserProfile = UserProfileManager.getInstance().getUserProfile(this.mTalkingId);
        if (localUserProfile == null)
          UserProfileManager.getInstance().loadUserInfo(this.mTalkingId, this);
        while (true)
        {
          this.mTalkingGroupInfo = null;
          this.mMessage = localIMMessage2;
          QTMSGManage.getInstance().sendStatistcsMessage("userChat", "enter");
          String str2 = QTLogger.getInstance().buildEnterIMLog(8);
          if (str2 != null)
            LogModule.getInstance().send("IMUI", str2);
          this.mHeadView.update("setData", localIMMessage2);
          break;
          this.mTalkingUserInfo = localUserProfile.getUserInfo();
        }
      }
      if (!(paramObject instanceof String))
        break label213;
      this.mTalkingId = ((String)paramObject);
      ChatMode.setMode(1);
      GroupInfo localGroupInfo1 = IMAgent.getInstance().getGroupInfo(this.mTalkingId);
      if (localGroupInfo1 == null)
        IMAgent.getInstance().loadGroupInfo(this.mTalkingId, this);
      while (true)
      {
        this.mTalkingUserInfo = null;
        this.mTalkingGroupInfo = localGroupInfo1;
        this.mTalkingBlocked = false;
        this.mMessage = null;
        QTMSGManage.getInstance().sendStatistcsMessage("groupChat", "enter");
        String str1 = QTLogger.getInstance().buildEnterIMLog(7);
        if (str1 != null)
          LogModule.getInstance().send("IMUI", str1);
        this.mHeadView.update("setData", localGroupInfo1);
        break;
        if ((localGroupInfo1.lstAdmins != null) && (localGroupInfo1.lstAdmins.size() > 0))
          for (int j = 0; j < localGroupInfo1.lstAdmins.size(); j++)
            this.mAdmins.add(((UserInfo)localGroupInfo1.lstAdmins.get(j)).userKey);
      }
      if (paramString.equalsIgnoreCase("addmessage"))
      {
        if (this.mLoading)
        {
          this.mDatas.clear();
          this.mLoading = false;
        }
        IMMessage localIMMessage1 = (IMMessage)paramObject;
        long l = localIMMessage1.publish;
        if (l - this.mLastTimestamp >= 1800L)
        {
          this.mDatas.add(new ChatItem(32, getTimestampBySecond(l)));
          this.mLastTimestamp = l;
        }
        List localList = this.mDatas;
        if (isAdmin(localIMMessage1.mFromID));
        for (int i = 20; ; i = 17)
        {
          localList.add(new ChatItem(i, localIMMessage1));
          this.mAdapter.notifyDataSetChanged();
          return;
        }
      }
      if (paramString.equalsIgnoreCase("addhistory"))
      {
        if (paramObject == null)
        {
          if (this.mLoading)
          {
            this.mDatas.clear();
            this.mLoading = false;
            this.mOldestMsgSeq = 0L;
            this.mOldestTimeStamp = 0L;
            this.mAdapter.notifyDataSetChanged();
          }
          this.mAdapter.notifyDataSetChanged();
          this.mListView.onRefreshComplete();
          return;
        }
        if (this.mLoading)
        {
          this.mDatas.clear();
          this.mLoading = false;
        }
        addHistoryMessages((List)paramObject);
        return;
      }
      if (paramString.equalsIgnoreCase("closeKeyboard"))
      {
        this.mInputView.update(paramString, paramObject);
        return;
      }
      if (paramString.equalsIgnoreCase("needAccount"))
      {
        onEvent(this, paramString, paramObject);
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("atTa"));
    if (this.mJoinView.getVisibility() == 0)
    {
      Toast.makeText(getContext(), "请先加入群聊", 0).show();
      return;
    }
    this.mJoinView.setVisibility(8);
    this.mInputView.update(paramString, paramObject);
  }

  private class GetDataTask extends AsyncTask<Void, Void, String[]>
  {
    private GetDataTask()
    {
    }

    protected String[] doInBackground(Void[] paramArrayOfVoid)
    {
      try
      {
        Thread.sleep(300L);
        label6: return null;
      }
      catch (InterruptedException localInterruptedException)
      {
        break label6;
      }
    }

    protected void onPostExecute(String[] paramArrayOfString)
    {
      ImChatMainView.this.loadMoreHistory();
      super.onPostExecute(paramArrayOfString);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.im.chat.ImChatMainView
 * JD-Core Version:    0.6.2
 */