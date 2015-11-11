package fm.qingting.qtradio.view.chatroom;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View.MeasureSpec;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.TextViewElement;
import fm.qingting.framework.view.TextViewElement.VerticalAlignment;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.room.ChatData;
import fm.qingting.qtradio.room.SnsInfo;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.view.chatroom.broadcastor.RoundAvatarElement;

public class ActionsFloatView extends QtView
{
  private final ViewLayout avatarLayout = this.standardLayout.createChildLT(70, 70, 20, 10, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout buttonLayout = this.standardLayout.createChildLT(100, 60, 600, 15, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private boolean isMine = false;
  private final ViewLayout leftButtonLayout = this.standardLayout.createChildLT(100, 60, 480, 15, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private RoundAvatarElement mAvatarElement;
  private ChatAction mChatAction = ChatAction.None;
  private TextViewElement mContent;
  private ButtonElement mLeftButtonViewElement;
  private UserInfo mMemberInfo;
  private ButtonElement mRightButtonViewElement;
  private final ViewLayout paddingLayout = this.standardLayout.createChildLT(720, 20, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private int requestCnt = 0;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 90, 720, 90, 0, 0, ViewLayout.FILL);

  public ActionsFloatView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(-400161);
    ViewElement.OnElementClickListener local1 = new ViewElement.OnElementClickListener()
    {
      public void onElementClick(ViewElement paramAnonymousViewElement)
      {
        if (paramAnonymousViewElement == ActionsFloatView.this.mLeftButtonViewElement)
          if (ActionsFloatView.this.mChatAction == ActionsFloatView.ChatAction.RequestSn)
            ActionsFloatView.this.dispatchActionEvent("asktoo", null);
        do
        {
          do
          {
            return;
            if (paramAnonymousViewElement != ActionsFloatView.this.mRightButtonViewElement)
              break;
            if (ActionsFloatView.this.mChatAction == ActionsFloatView.ChatAction.SayHi)
            {
              ActionsFloatView.this.dispatchActionEvent("sayhi", ActionsFloatView.this.mMemberInfo);
              return;
            }
          }
          while (ActionsFloatView.this.mChatAction != ActionsFloatView.ChatAction.RequestSn);
          ActionsFloatView.this.dispatchActionEvent("response", ActionsFloatView.this.mMemberInfo);
          return;
        }
        while ((paramAnonymousViewElement != ActionsFloatView.this.mContent) || (ActionsFloatView.this.mChatAction != ActionsFloatView.ChatAction.Remind));
        ActionsFloatView.this.dispatchActionEvent("scrollToFirstIndexAtMe", null);
      }
    };
    int i = hashCode();
    this.mAvatarElement = new RoundAvatarElement(paramContext);
    this.mAvatarElement.setDefaultImageRes(2130837590);
    addElement(this.mAvatarElement, i);
    this.mContent = new TextViewElement(paramContext);
    this.mContent.setMaxLineLimit(3);
    this.mContent.setColor(SkinManager.getTextColorNormal());
    this.mContent.setTextSize(SkinManager.getInstance().getSubTextSize());
    this.mContent.setVerticalAlignment(TextViewElement.VerticalAlignment.CENTER);
    addElement(this.mContent);
    this.mContent.setOnElementClickListener(local1);
    this.mLeftButtonViewElement = new ButtonElement(paramContext);
    this.mLeftButtonViewElement.setFrameColor(SkinManager.getTextColorHighlight(), -824195);
    this.mLeftButtonViewElement.setText("抢答");
    this.mLeftButtonViewElement.setTextColor(SkinManager.getTextColorHighlight(), -824195);
    this.mLeftButtonViewElement.setTextSize(SkinManager.getInstance().getSubTextSize());
    addElement(this.mLeftButtonViewElement);
    this.mLeftButtonViewElement.setOnElementClickListener(local1);
    this.mRightButtonViewElement = new ButtonElement(paramContext);
    this.mRightButtonViewElement.setFrameColor(SkinManager.getTextColorHighlight(), -824195);
    this.mRightButtonViewElement.setTextColor(SkinManager.getTextColorHighlight(), -824195);
    this.mRightButtonViewElement.setTextSize(SkinManager.getInstance().getSubTextSize());
    addElement(this.mRightButtonViewElement);
    this.mRightButtonViewElement.setOnElementClickListener(local1);
  }

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    int i = (getMeasuredHeight() - this.mContent.getHeight()) / 2;
    this.mContent.setTranslationY(i);
    super.onDraw(paramCanvas);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.avatarLayout.scaleToBounds(this.standardLayout);
    this.buttonLayout.scaleToBounds(this.standardLayout);
    this.leftButtonLayout.scaleToBounds(this.standardLayout);
    this.paddingLayout.scaleToBounds(this.standardLayout);
    this.mLeftButtonViewElement.measure(this.leftButtonLayout);
    this.mRightButtonViewElement.measure(this.buttonLayout);
    this.mAvatarElement.measure(this.avatarLayout);
    int i;
    switch (2.$SwitchMap$fm$qingting$qtradio$view$chatroom$ActionsFloatView$ChatAction[this.mChatAction.ordinal()])
    {
    default:
      i = 0;
    case 2:
    case 4:
    case 1:
    case 3:
    }
    while (true)
    {
      setMeasuredDimension(this.standardLayout.width, i);
      return;
      this.mAvatarElement.measure(this.avatarLayout);
      TextViewElement localTextViewElement = this.mContent;
      int j = this.avatarLayout.getRight() + this.avatarLayout.leftMargin;
      if (this.isMine);
      for (int k = this.mLeftButtonViewElement.getLeftMargin(); ; k = this.mRightButtonViewElement.getLeftMargin())
      {
        localTextViewElement.measure(j, 0, k, this.standardLayout.height);
        i = this.standardLayout.height;
        break;
      }
      this.mContent.measure(this.avatarLayout.leftMargin, 0, this.standardLayout.width - this.avatarLayout.leftMargin, this.standardLayout.height);
      i = this.mContent.getHeight() + 2 * this.paddingLayout.height;
      continue;
      this.mContent.measure(this.avatarLayout.getRight() + this.avatarLayout.leftMargin, 0, this.mRightButtonViewElement.getLeftMargin(), this.standardLayout.height);
      i = this.standardLayout.height;
      continue;
      this.mContent.measure(this.avatarLayout.leftMargin, 0, this.standardLayout.width - this.avatarLayout.leftMargin, this.standardLayout.height);
      i = this.mContent.getHeight() + 2 * this.paddingLayout.height;
    }
  }

  public void setAtMeParam(ChatAction paramChatAction, String paramString)
  {
    String str = "@" + paramString + "有新消息给你，" + "请下拉聊天记录查看";
    this.mChatAction = paramChatAction;
    this.mAvatarElement.setVisible(4);
    this.mRightButtonViewElement.setVisible(4);
    this.mLeftButtonViewElement.setVisible(4);
    this.mContent.setText(str, true);
    requestLayout();
  }

  public void setCheckinParam(ChatAction paramChatAction, String paramString)
  {
    this.mChatAction = paramChatAction;
    this.mAvatarElement.setVisible(4);
    this.mRightButtonViewElement.setVisible(0);
    this.mLeftButtonViewElement.setVisible(4);
    this.mContent.setText(paramString, false);
    this.mRightButtonViewElement.setText("签到");
    requestLayout();
  }

  public void setRequestsnParam(ChatAction paramChatAction, ChatData paramChatData, boolean paramBoolean)
  {
    this.requestCnt = paramChatData.askForSongCnt;
    this.mChatAction = paramChatAction;
    UserInfo localUserInfo;
    String str1;
    label107: ButtonElement localButtonElement;
    if (this.requestCnt > 1)
    {
      localUserInfo = paramChatData.toUser;
      this.mMemberInfo = localUserInfo;
      this.isMine = paramBoolean;
      StringBuilder localStringBuilder = new StringBuilder().append("用户").append(this.mMemberInfo.snsInfo.sns_name);
      if (this.requestCnt <= 1)
        break label182;
      str1 = "和其他" + (-1 + this.requestCnt) + "个用户";
      String str2 = str1 + "在求歌名";
      this.mContent.setText(str2, false);
      this.mRightButtonViewElement.setText("抢答");
      localButtonElement = this.mLeftButtonViewElement;
      if (!this.isMine)
        break label190;
    }
    label182: label190: for (int i = 4; ; i = 0)
    {
      localButtonElement.setVisible(i);
      requestLayout();
      return;
      localUserInfo = paramChatData.user;
      break;
      str1 = "";
      break label107;
    }
  }

  public void setSayHiParam(ChatAction paramChatAction, UserInfo paramUserInfo)
  {
    this.mChatAction = paramChatAction;
    if (paramUserInfo == null)
      return;
    this.mMemberInfo = paramUserInfo;
    String str = this.mMemberInfo.snsInfo.sns_name + "进入直播间";
    this.mContent.setText(str, false);
    this.mRightButtonViewElement.setText("打招呼");
    this.mAvatarElement.setImageUrl(this.mMemberInfo.snsInfo.sns_avatar);
    this.mAvatarElement.setVisible(0);
    this.mLeftButtonViewElement.setVisible(4);
    this.mRightButtonViewElement.setVisible(0);
    requestLayout();
  }

  public void setTopicParam(ChatAction paramChatAction, String paramString)
  {
    this.mChatAction = paramChatAction;
    this.mAvatarElement.setVisible(4);
    this.mRightButtonViewElement.setVisible(4);
    this.mLeftButtonViewElement.setVisible(4);
    this.mContent.setText(paramString, false);
    requestLayout();
  }

  public static enum ChatAction
  {
    static
    {
      RequestSn = new ChatAction("RequestSn", 2);
      Remind = new ChatAction("Remind", 3);
      Topic = new ChatAction("Topic", 4);
      ChatAction[] arrayOfChatAction = new ChatAction[5];
      arrayOfChatAction[0] = None;
      arrayOfChatAction[1] = SayHi;
      arrayOfChatAction[2] = RequestSn;
      arrayOfChatAction[3] = Remind;
      arrayOfChatAction[4] = Topic;
    }

    public String getMessageEventType()
    {
      switch (ActionsFloatView.2.$SwitchMap$fm$qingting$qtradio$view$chatroom$ActionsFloatView$ChatAction[ordinal()])
      {
      default:
        return "";
      case 1:
        return "showNewMember";
      case 2:
        return "setAskSong";
      case 3:
        return "showAtMeRemind";
      case 4:
      }
      return "setTopic";
    }

    public long getTimeLength()
    {
      switch (ActionsFloatView.2.$SwitchMap$fm$qingting$qtradio$view$chatroom$ActionsFloatView$ChatAction[ordinal()])
      {
      case 1:
      case 2:
      default:
        return 4000L;
      case 3:
        return 8000L;
      case 4:
      }
      return 8000L;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.chatroom.ActionsFloatView
 * JD-Core Version:    0.6.2
 */