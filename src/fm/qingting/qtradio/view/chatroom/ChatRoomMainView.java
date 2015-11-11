package fm.qingting.qtradio.view.chatroom;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.BroadcasterNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.room.ChatData;
import fm.qingting.qtradio.room.CustomData;
import fm.qingting.qtradio.room.SnsInfo;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.room.WeiboData;
import fm.qingting.qtradio.social.CloudCenter;
import fm.qingting.qtradio.social.CloudCenter.OnLoginEventListerner;
import fm.qingting.qtradio.view.chatroom.chatlist.ChatroomListView;
import fm.qingting.qtradio.view.chatroom.expression.ExpressionView;
import fm.qingting.utils.QTMSGManage;
import java.util.HashMap;
import java.util.Map;

public class ChatRoomMainView extends ViewGroupViewImpl
  implements IEventHandler
{
  private final ViewLayout bottomLayout = this.standardLayout.createChildLT(720, 106, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private BroadcasterNode broadcasterNode = null;
  private ChatData chatData = null;
  private final ViewLayout chatMemberLayout = this.standardLayout.createChildLT(720, 380, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout checkinLayout = this.standardLayout.createChildLT(156, 74, 564, 130, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout headerLayout = this.standardLayout.createChildLT(720, 256, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private boolean inputShowingLayout = false;
  private boolean isInputShowing = false;
  private boolean isReportSn = false;
  private MessageType lastMessageType = MessageType.none;
  private ChatActionsView mActionsView;
  private Button mCheckinButton;
  private ExpressionView mExpressionView;
  private ChatroomHeadView mHeadView;
  private ChatroomInputView mInputView;
  private ChatInviteView mInviteView;
  private ChatroomListView mListView;
  private ActionsFloatView mRemindView;
  private ViewState mViewState = ViewState.Normal;
  private MessageType messageType = MessageType.none;
  private int normalLayoutHeight = 0;
  private final ViewLayout remindLayout = this.standardLayout.createChildLT(720, 90, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private int scrollOffset = 0;
  private int scrollToPosition = -1;
  private int scrollY = 0;
  private int specialLayoutHeight = -1;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);

  public ChatRoomMainView(Context paramContext)
  {
    super(paramContext);
    this.mListView = new ChatroomListView(paramContext, true);
    this.mListView.setEventHandler(this);
    addView(this.mListView);
    this.mHeadView = new ChatroomHeadView(paramContext);
    addView(this.mHeadView);
    this.mHeadView.setEventHandler(this);
    this.mRemindView = new ActionsFloatView(paramContext);
    addView(this.mRemindView);
    this.mRemindView.setVisibility(4);
    this.mRemindView.setEventHandler(this);
    this.mInputView = new ChatroomInputView(paramContext);
    addView(this.mInputView);
    this.mInputView.setEventHandler(this);
    this.mListView.setOnScrollListener(new AbsListView.OnScrollListener()
    {
      public void onScroll(AbsListView paramAnonymousAbsListView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
      }

      public void onScrollStateChanged(AbsListView paramAnonymousAbsListView, int paramAnonymousInt)
      {
        switch (paramAnonymousInt)
        {
        case 1:
        case 2:
        default:
          return;
        case 0:
        }
        ChatRoomMainView.access$002(ChatRoomMainView.this, paramAnonymousAbsListView.getScrollY());
      }
    });
    this.mActionsView = new ChatActionsView(paramContext);
    this.mActionsView.setEventHandler(this);
    addView(this.mActionsView);
    this.mExpressionView = new ExpressionView(paramContext);
    this.mExpressionView.setEventHandler(this);
    addView(this.mExpressionView);
    this.mInviteView = new ChatInviteView(paramContext);
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
        ChatRoomMainView.this.dispatchActionEvent("checkIn", null);
      }
    });
  }

  private int getActionOffset()
  {
    if (this.mViewState == ViewState.ShowingAction)
      return this.mActionsView.getMeasuredHeight();
    return 0;
  }

  private int getExpressionOffset()
  {
    if (this.mViewState == ViewState.ShowingExpression)
      return this.chatMemberLayout.height;
    return 0;
  }

  private int getInputOffset()
  {
    switch (4.$SwitchMap$fm$qingting$qtradio$view$chatroom$ChatRoomMainView$ViewState[this.mViewState.ordinal()])
    {
    default:
      return 0;
    case 1:
      return this.chatMemberLayout.height;
    case 2:
      return this.mActionsView.getMeasuredHeight();
    case 3:
    }
    return this.mInviteView.getMeasuredHeight();
  }

  private int getInviteOffset()
  {
    if (this.mViewState == ViewState.ShowingInvite)
      return this.mInviteView.getMeasuredHeight();
    return 0;
  }

  private void hideAction()
  {
    this.mViewState = ViewState.Normal;
    requestLayout();
  }

  private void layoutViews(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt4 - paramInt2 > this.normalLayoutHeight)
    {
      this.specialLayoutHeight = (paramInt4 - paramInt2);
      if (this.normalLayoutHeight == 0)
        this.normalLayoutHeight = (paramInt4 - paramInt2);
    }
    int i = this.mHeadView.getMeasuredHeight();
    if (paramInt4 - paramInt2 < this.normalLayoutHeight)
    {
      this.isInputShowing = true;
      if (!this.inputShowingLayout)
        this.mInputView.update("resetState", null);
      try
      {
        this.mListView.layout(0, i, this.standardLayout.width, this.standardLayout.height - this.mInputView.getMeasuredHeight());
        if (!this.inputShowingLayout)
        {
          this.mListView.scrollToPosition(this.scrollToPosition, this.scrollOffset);
          this.scrollToPosition = -1;
          this.scrollOffset = 0;
          this.mInputView.update("isinputting", Boolean.valueOf(true));
        }
        this.inputShowingLayout = true;
        this.mHeadView.layout(0, 0, this.standardLayout.width, i);
        this.mRemindView.layout(0, i, this.standardLayout.width, i + this.mRemindView.getMeasuredHeight());
        this.mInputView.layout(0, this.standardLayout.height - this.mInputView.getMeasuredHeight(), this.standardLayout.width, this.standardLayout.height);
        this.mActionsView.layout(0, this.standardLayout.height, this.standardLayout.width, this.standardLayout.height + this.mActionsView.getMeasuredHeight());
        this.mExpressionView.layout(0, this.standardLayout.height, this.standardLayout.width, this.standardLayout.height + this.chatMemberLayout.height);
        this.mInviteView.layout(0, this.standardLayout.height, this.standardLayout.width, this.standardLayout.height + this.mInviteView.getMeasuredHeight());
        this.mCheckinButton.layout(this.checkinLayout.leftMargin, i + this.checkinLayout.topMargin, this.checkinLayout.getRight(), i + this.checkinLayout.getBottom());
        return;
      }
      catch (IllegalStateException localIllegalStateException2)
      {
        while (true)
          localIllegalStateException2.printStackTrace();
      }
    }
    this.isInputShowing = false;
    if ((this.inputShowingLayout) && (this.isReportSn))
    {
      this.isReportSn = false;
      this.mInputView.update("exitReport", null);
    }
    if ((this.inputShowingLayout) && (this.messageType == MessageType.toDj))
    {
      this.messageType = this.lastMessageType;
      this.mInputView.update("leaveTodj", null);
    }
    this.inputShowingLayout = false;
    int j = getInputOffset();
    try
    {
      this.mListView.layout(0, i - j, this.standardLayout.width, this.standardLayout.height - this.mInputView.getMeasuredHeight() - j);
      this.mHeadView.layout(0, 0, this.standardLayout.width, i);
      this.mCheckinButton.layout(this.checkinLayout.leftMargin, i + this.checkinLayout.topMargin, this.checkinLayout.getRight(), i + this.checkinLayout.getBottom());
      this.mRemindView.layout(0, i, this.standardLayout.width, i + this.mRemindView.getMeasuredHeight());
      this.mInputView.layout(0, this.standardLayout.height - this.mInputView.getMeasuredHeight() - j, this.standardLayout.width, this.standardLayout.height - j);
      int k = getActionOffset();
      this.mActionsView.layout(0, this.standardLayout.height - k, this.standardLayout.width, this.standardLayout.height + this.mActionsView.getMeasuredHeight() - k);
      int m = getExpressionOffset();
      this.mExpressionView.layout(0, this.standardLayout.height - m, this.standardLayout.width, this.standardLayout.height + this.chatMemberLayout.height - m);
      int n = getInviteOffset();
      this.mInviteView.layout(0, this.standardLayout.height - n, this.standardLayout.width, this.standardLayout.height + this.mInviteView.getMeasuredHeight() - n);
      return;
    }
    catch (IllegalStateException localIllegalStateException1)
    {
      while (true)
        localIllegalStateException1.printStackTrace();
    }
  }

  private void sayToIt(Object paramObject)
  {
    CustomData localCustomData;
    String str3;
    if ((paramObject instanceof CustomData))
    {
      localCustomData = (CustomData)paramObject;
      if (localCustomData.type == 1)
        str3 = ((ChatData)paramObject).user.snsInfo.sns_name;
    }
    while (true)
    {
      this.mInputView.update("replyToUser", str3);
      UserInfo localUserInfo;
      do
      {
        do
        {
          return;
          if (localCustomData.type != 2)
            break label149;
          str3 = ((WeiboData)paramObject).user.snsInfo.sns_name;
          break;
          if ((paramObject instanceof BroadcasterNode))
          {
            String str2 = ((BroadcasterNode)paramObject).nick;
            this.mInputView.update("replyToUser", str2);
            return;
          }
        }
        while (!(paramObject instanceof UserInfo));
        localUserInfo = (UserInfo)paramObject;
      }
      while ((localUserInfo == null) || (localUserInfo.snsInfo == null));
      String str1 = localUserInfo.snsInfo.sns_name;
      this.mInputView.update("replyToUser", str1);
      return;
      label149: str3 = null;
    }
  }

  private void sendPraise()
  {
    if (this.chatData == null)
      return;
    ChatData localChatData = new ChatData();
    localChatData.replyedContent = this.chatData.content;
    localChatData.content = "赞一个";
    localChatData.toUser = this.chatData.user;
    localChatData.id = this.chatData.id;
    localChatData.commentid = this.chatData.commentid;
    dispatchActionEvent("praise", localChatData);
  }

  private void sendReply(String paramString)
  {
    if (this.chatData == null)
      return;
    boolean bool = ((Boolean)this.mInputView.getValue("getMode", null)).booleanValue();
    ChatData localChatData = new ChatData();
    localChatData.replyedContent = this.chatData.content;
    localChatData.content = paramString;
    localChatData.toUser = this.chatData.user;
    localChatData.id = this.chatData.id;
    localChatData.commentid = this.chatData.commentid;
    if (bool);
    for (int i = 3; ; i = 0)
    {
      localChatData.conentType = i;
      dispatchActionEvent("sendReply", localChatData);
      if (!bool)
        break;
      QTMSGManage.getInstance().sendStatistcsMessage("chat_functionclick", "secret");
      return;
    }
  }

  private void showAction()
  {
    this.mViewState = ViewState.ShowingAction;
    requestLayout();
  }

  private void speakToDj(String paramString)
  {
    if (this.broadcasterNode == null)
      return;
    HashMap localHashMap = new HashMap();
    localHashMap.put("dj", this.broadcasterNode);
    localHashMap.put("message", paramString);
    dispatchActionEvent("tosay", localHashMap);
    this.messageType = this.lastMessageType;
    this.mInputView.update("leaveTodj", null);
  }

  public void close(boolean paramBoolean)
  {
    this.mInputView.close(paramBoolean);
    this.mHeadView.close(paramBoolean);
    this.mListView.close(paramBoolean);
    this.mActionsView.close(paramBoolean);
    this.mInviteView.close(paramBoolean);
    this.mRemindView.close(paramBoolean);
    super.close(paramBoolean);
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getAction() == 0)
    {
      float f = paramMotionEvent.getY();
      if (this.isInputShowing)
      {
        if ((f > this.mHeadView.getMeasuredHeight()) && (f < this.standardLayout.height - this.mInputView.getMeasuredHeight()))
        {
          this.mViewState = ViewState.Normal;
          this.mInputView.update("closeKeyboard", null);
          return true;
        }
      }
      else if ((this.mViewState != ViewState.Normal) && (f > this.mHeadView.getMeasuredHeight()) && (f < this.standardLayout.height - getInputOffset() - this.mInputView.getMeasuredHeight()))
      {
        this.mViewState = ViewState.Normal;
        requestLayout();
        return true;
      }
      if (f > this.mHeadView.getMeasuredHeight())
        this.mHeadView.update("resetState", null);
    }
    return super.dispatchTouchEvent(paramMotionEvent);
  }

  public Object getValue(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("keyboardState"))
      return Boolean.valueOf(this.isInputShowing);
    return super.getValue(paramString, paramObject);
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("sendDiscuss"))
    {
      this.mViewState = ViewState.Normal;
      if (this.isReportSn)
        dispatchActionEvent("sendSn", paramObject2);
    }
    do
    {
      return;
      if (this.messageType == MessageType.reply)
      {
        sendReply((String)paramObject2);
        return;
      }
      if (this.messageType == MessageType.toDj)
      {
        speakToDj((String)paramObject2);
        return;
      }
      dispatchActionEvent(paramString, paramObject2);
      return;
      if (paramString.equalsIgnoreCase("scrollTo"))
      {
        this.mListView.scrollTo(0, this.scrollY);
        return;
      }
    }
    while (paramString.equalsIgnoreCase("selectBlank"));
    if (paramString.equalsIgnoreCase("replyToUser"))
    {
      this.messageType = MessageType.reply;
      this.chatData = ((ChatData)paramObject2);
      this.mInputView.update(paramString, this.chatData.user.snsInfo.sns_name);
      return;
    }
    if (paramString.equalsIgnoreCase("praise"))
    {
      this.chatData = ((ChatData)paramObject2);
      sendPraise();
      return;
    }
    if (paramString.equalsIgnoreCase("tosay"))
    {
      this.lastMessageType = this.messageType;
      this.messageType = MessageType.toDj;
      this.broadcasterNode = ((BroadcasterNode)paramObject2);
      this.mInputView.update(paramString, this.broadcasterNode.nick);
      return;
    }
    if (paramString.equalsIgnoreCase("expand"))
    {
      this.mInputView.update("closeKeyboard", null);
      if (this.mViewState != ViewState.ShowingAction)
      {
        showAction();
        return;
      }
      hideAction();
      return;
    }
    if (paramString.equalsIgnoreCase("inviteFriends"))
    {
      this.mInputView.update("closeKeyboard", null);
      if (this.mViewState != ViewState.ShowingInvite)
      {
        this.mViewState = ViewState.ShowingInvite;
        requestLayout();
        return;
      }
      this.mViewState = ViewState.Normal;
      requestLayout();
      return;
    }
    if (paramString.equalsIgnoreCase("expression"))
    {
      if (this.mViewState == ViewState.ShowingExpression)
      {
        this.mViewState = ViewState.Normal;
        this.mInputView.update("openKeyboard", null);
        return;
      }
      if (!CloudCenter.getInstance().isLogin())
      {
        CloudCenter.OnLoginEventListerner local3 = new CloudCenter.OnLoginEventListerner()
        {
          public void onLoginFailed(int paramAnonymousInt)
          {
          }

          public void onLoginSuccessed(int paramAnonymousInt)
          {
            QTMSGManage.getInstance().sendStatistcsMessage("chat_functionclick", "expression");
            ChatRoomMainView.this.mInputView.update("closeKeyboard", null);
            ChatRoomMainView.access$302(ChatRoomMainView.this, ChatRoomMainView.ViewState.ShowingExpression);
            ChatRoomMainView.this.requestLayout();
          }
        };
        EventDispacthManager.getInstance().dispatchAction("showLogin", local3);
        return;
      }
      QTMSGManage.getInstance().sendStatistcsMessage("chat_functionclick", "expression");
      this.mInputView.update("closeKeyboard", null);
      this.mViewState = ViewState.ShowingExpression;
      this.mInputView.update("setArrowState", Boolean.valueOf(false));
      this.mInputView.update("setExpressionState", Boolean.valueOf(false));
      requestLayout();
      return;
    }
    if (paramString.equalsIgnoreCase("selectExpression"))
    {
      this.mInputView.update("addExpression", paramObject2);
      return;
    }
    if (paramString.equalsIgnoreCase("scrollToFirstIndexAtMe"))
    {
      dispatchActionEvent(paramString, paramObject2);
      return;
    }
    dispatchActionEvent(paramString, paramObject2);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    layoutViews(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.standardLayout.scaleToBounds(i, j);
    this.checkinLayout.scaleToBounds(this.standardLayout);
    this.headerLayout.scaleToBounds(this.standardLayout);
    this.headerLayout.measureView(this.mHeadView);
    this.bottomLayout.scaleToBounds(this.standardLayout);
    this.remindLayout.scaleToBounds(this.standardLayout);
    this.remindLayout.measureView(this.mRemindView);
    this.bottomLayout.measureView(this.mInputView);
    this.mListView.measure(this.standardLayout.getWidthMeasureSpec(), View.MeasureSpec.makeMeasureSpec(this.standardLayout.height - this.mHeadView.getMeasuredHeight() - this.mInputView.getMeasuredHeight(), 1073741824));
    this.chatMemberLayout.scaleToBounds(this.standardLayout);
    this.chatMemberLayout.measureView(this.mActionsView);
    this.chatMemberLayout.measureView(this.mExpressionView);
    this.chatMemberLayout.measureView(this.mInviteView);
    this.checkinLayout.measureView(this.mCheckinButton);
    this.mCheckinButton.setPadding((int)(0.3F * this.checkinLayout.width), 0, 0, 0);
    this.mCheckinButton.setTextSize(0, SkinManager.getInstance().getSubTextSize());
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }

  public void setActivate(boolean paramBoolean)
  {
    if (paramBoolean)
      super.setActivate(paramBoolean);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
      this.mListView.update(paramString, paramObject);
    do
    {
      CustomData localCustomData;
      do
      {
        do
        {
          do
          {
            do
            {
              return;
              if (paramString.equalsIgnoreCase("setHeadInfo"))
              {
                this.mHeadView.update(paramString, paramObject);
                return;
              }
              if (paramString.equalsIgnoreCase("setTopic"))
              {
                this.mRemindView.setTopicParam(ActionsFloatView.ChatAction.Topic, (String)paramObject);
                this.mRemindView.setVisibility(0);
                return;
              }
              if (paramString.equalsIgnoreCase("showAtMeRemind"))
              {
                this.mRemindView.setAtMeParam(ActionsFloatView.ChatAction.Remind, (String)paramObject);
                this.mRemindView.setVisibility(0);
                return;
              }
              if (paramString.equalsIgnoreCase("setLocalHeadInfo"))
              {
                this.mHeadView.update(paramString, paramObject);
                return;
              }
              if (paramString.equalsIgnoreCase("speakTodj"))
              {
                onEvent(this, "tosay", paramObject);
                return;
              }
              if (paramString.equalsIgnoreCase("closeKeyboard"))
              {
                this.mInputView.update(paramString, paramObject);
                return;
              }
              if (paramString.equalsIgnoreCase("scroll"))
              {
                this.mListView.update("scrollToBottom", null);
                return;
              }
              if (!paramString.equalsIgnoreCase("talkWithIt"))
                break;
              this.messageType = MessageType.reply;
              this.chatData = new ChatData();
              this.chatData.user = ((UserInfo)paramObject);
              this.mInputView.update(paramString, paramObject);
            }
            while (paramObject != null);
            hideAction();
            this.mListView.update("scroll", null);
            return;
            if (!paramString.equalsIgnoreCase("lookItsMessage"))
              break;
            this.messageType = MessageType.reply;
            this.chatData = new ChatData();
            this.chatData.user = ((UserInfo)paramObject);
            this.mInputView.update(paramString, paramObject);
          }
          while (paramObject != null);
          hideAction();
          this.mListView.update("scroll", null);
          return;
          if (paramString.equalsIgnoreCase("setChatMembers"))
          {
            this.mActionsView.update(paramString, paramObject);
            return;
          }
        }
        while (paramString.equalsIgnoreCase("showCheckin"));
        if (paramString.equalsIgnoreCase("showNewMember"))
        {
          this.mRemindView.setSayHiParam(ActionsFloatView.ChatAction.SayHi, (UserInfo)paramObject);
          this.mRemindView.setVisibility(0);
          return;
        }
        if (!paramString.equalsIgnoreCase("setAskSong"))
          break;
        localCustomData = (CustomData)paramObject;
      }
      while (localCustomData.type != 1);
      boolean bool = ((ChatData)localCustomData).user.userId.equalsIgnoreCase(InfoManager.getInstance().getDeviceId());
      this.mRemindView.setRequestsnParam(ActionsFloatView.ChatAction.RequestSn, (ChatData)localCustomData, bool);
      this.mRemindView.setVisibility(0);
      return;
      if (paramString.equalsIgnoreCase("hideMessage"))
      {
        this.mRemindView.setVisibility(4);
        return;
      }
      if (paramString.equalsIgnoreCase("reportSongName"))
      {
        this.isReportSn = true;
        this.mInputView.update("enterReportSn", null);
        return;
      }
      if (paramString.equalsIgnoreCase("needAccount"))
      {
        onEvent(this, paramString, paramObject);
        return;
      }
      if (paramString.equalsIgnoreCase("sayToIt"))
      {
        sayToIt(paramObject);
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("scrollToFirstIndexAtMe"));
    this.mListView.update(paramString, paramObject);
  }

  private static enum MessageType
  {
    static
    {
      discuss = new MessageType("discuss", 2);
      none = new MessageType("none", 3);
      MessageType[] arrayOfMessageType = new MessageType[4];
      arrayOfMessageType[0] = reply;
      arrayOfMessageType[1] = toDj;
      arrayOfMessageType[2] = discuss;
      arrayOfMessageType[3] = none;
    }
  }

  private static enum ViewState
  {
    static
    {
      ShowingAction = new ViewState("ShowingAction", 1);
      ShowingInvite = new ViewState("ShowingInvite", 2);
      Normal = new ViewState("Normal", 3);
      ViewState[] arrayOfViewState = new ViewState[4];
      arrayOfViewState[0] = ShowingExpression;
      arrayOfViewState[1] = ShowingAction;
      arrayOfViewState[2] = ShowingInvite;
      arrayOfViewState[3] = Normal;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.chatroom.ChatRoomMainView
 * JD-Core Version:    0.6.2
 */