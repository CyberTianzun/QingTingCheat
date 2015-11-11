package fm.qingting.qtradio.view.playview;

import android.content.Context;
import android.text.Layout.Alignment;
import android.view.View.MeasureSpec;
import android.widget.Toast;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.ButtonViewElement;
import fm.qingting.framework.view.ImageViewElement;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.TextViewElement;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.im.IMAgent;
import fm.qingting.qtradio.manager.QtApiLevelManager;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.InfoManager.DataExceptionStatus;
import fm.qingting.qtradio.model.InfoManager.ISubscribeEventListener;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.IInfoUpdateEventListener;
import fm.qingting.qtradio.room.SnsInfo;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.view.chatroom.broadcastor.RoundAvatarElement;
import fm.qingting.utils.QTMSGManage;
import java.util.List;

public class PlayJoinChatView extends QtView
  implements RootNode.IInfoUpdateEventListener, ViewElement.OnElementClickListener, InfoManager.ISubscribeEventListener
{
  private final int MAX_CNT = 4;
  private final ViewLayout arrowLayout = this.standardLayout.createChildLT(36, 36, 660, 31, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout avatarLayout = this.standardLayout.createChildLT(60, 60, 12, 19, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout labelLayout = this.standardLayout.createChildLT(40, 40, 52, 13, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout lineLayout = this.standardLayout.createChildLT(720, 1, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private ImageViewElement mArrowElement;
  private RoundAvatarElement[] mAvatarElements;
  private ButtonViewElement mBg;
  private int mChannelId;
  private String mGroupId;
  private LabelViewElement mLabelViewElement;
  private LineElement mLineElement;
  private int mProgramId;
  private TextViewElement mTipElement;
  private String mWid;
  private ImageViewElement mWsqElement;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 98, 720, 98, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout tipLayout = this.standardLayout.createChildLT(300, 50, 340, 24, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout wsqLayout = this.standardLayout.createChildLT(60, 60, 12, 22, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout wsqTipLayout = this.standardLayout.createChildLT(520, 50, 110, 27, ViewLayout.SCALE_FLAG_SLTCW);

  public PlayJoinChatView(Context paramContext)
  {
    super(paramContext);
    this.mBg = new ButtonViewElement(paramContext);
    addElement(this.mBg);
    this.mBg.setOnElementClickListener(this);
    int j = hashCode();
    this.mAvatarElements = new RoundAvatarElement[4];
    while (i < this.mAvatarElements.length)
    {
      RoundAvatarElement localRoundAvatarElement = new RoundAvatarElement(paramContext);
      addElement(localRoundAvatarElement, j);
      this.mAvatarElements[i] = localRoundAvatarElement;
      i++;
    }
    this.mTipElement = new TextViewElement(paramContext);
    this.mTipElement.setMaxLineLimit(1);
    this.mTipElement.setColor(SkinManager.getNewPlaySubColor());
    this.mTipElement.setAlignment(Layout.Alignment.ALIGN_OPPOSITE);
    this.mTipElement.setText("进入官方群聊天");
    addElement(this.mTipElement);
    this.mArrowElement = new ImageViewElement(paramContext);
    this.mArrowElement.setImageRes(2130837694);
    addElement(this.mArrowElement, j);
    this.mLineElement = new LineElement(paramContext);
    this.mLineElement.setOrientation(1);
    this.mLineElement.setColor(654311423);
    addElement(this.mLineElement);
    this.mWsqElement = new ImageViewElement(paramContext);
    this.mWsqElement.setImageRes(2130837788);
    addElement(this.mWsqElement);
    this.mLabelViewElement = new LabelViewElement(paramContext);
    addElement(this.mLabelViewElement);
    InfoManager.getInstance().root().registerInfoUpdateListener(this, 7);
    InfoManager.getInstance().registerSubscribeEventListener(this, "WSQNEW");
  }

  private void measureAvatars()
  {
    if (this.mAvatarElements == null);
    while (true)
    {
      return;
      int i = this.avatarLayout.width / 3;
      for (int j = 0; j < this.mAvatarElements.length; j++)
      {
        this.mAvatarElements[j].measure(this.avatarLayout);
        this.mAvatarElements[j].setTranslationX(i);
        i += this.avatarLayout.getRight();
      }
    }
  }

  private void setDatas(List<UserInfo> paramList)
  {
    if (this.mWid == null)
    {
      int i;
      if ((paramList == null) || (paramList.size() == 0))
        i = 0;
      while (i < this.mAvatarElements.length)
      {
        this.mAvatarElements[i].setVisible(4);
        i++;
        continue;
        int j = Math.min(paramList.size(), this.mAvatarElements.length);
        for (int k = 0; k < j; k++)
        {
          RoundAvatarElement localRoundAvatarElement = this.mAvatarElements[k];
          localRoundAvatarElement.setImageUrl(((UserInfo)paramList.get(k)).snsInfo.sns_avatar);
          localRoundAvatarElement.setVisible(0);
        }
        for (int m = j; m < this.mAvatarElements.length; m++)
          this.mAvatarElements[m].setVisible(4);
      }
    }
  }

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  public void onElementClick(ViewElement paramViewElement)
  {
    if (this.mWid == null)
    {
      QTMSGManage.getInstance().sendStatistcsMessage("PlayView", "joinchat");
      if ((this.mGroupId != null) && (this.mGroupId.length() > 0) && (InfoManager.getInstance().enableSocial(this.mGroupId)))
        ControllerManager.getInstance().openImChatController(this.mGroupId);
      Node localNode;
      do
      {
        return;
        if (!QtApiLevelManager.isApiLevelSupported(8))
        {
          Toast.makeText(getContext(), "非常抱歉直播间只能在Android2.2版本以上使用", 0).show();
          return;
        }
        localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
      }
      while ((localNode == null) || (!localNode.nodeName.equalsIgnoreCase("program")));
      ControllerManager.getInstance().openChatRoom("", null, null, localNode, new Object[0]);
      return;
    }
    if (InfoManager.getInstance().jumpToProgram(this.mChannelId))
    {
      ControllerManager.getInstance().redirectToLocalWebView("http://qtime.qingting.fm/wsq/" + this.mWid + "/channels/" + this.mChannelId + "/programs/" + this.mProgramId, "蜻蜓微社区", false);
      return;
    }
    ControllerManager.getInstance().redirectToLocalWebView("http://qtime.qingting.fm/wsq/" + this.mWid, "蜻蜓微社区", false);
  }

  public void onInfoUpdated(int paramInt)
  {
    if (paramInt == 7)
      setDatas(IMAgent.getInstance().getGroupMembers(this.mGroupId));
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.avatarLayout.scaleToBounds(this.standardLayout);
    this.tipLayout.scaleToBounds(this.standardLayout);
    this.arrowLayout.scaleToBounds(this.standardLayout);
    this.lineLayout.scaleToBounds(this.standardLayout);
    this.wsqLayout.scaleToBounds(this.standardLayout);
    this.labelLayout.scaleToBounds(this.standardLayout);
    this.wsqTipLayout.scaleToBounds(this.standardLayout);
    this.mLineElement.measure(this.lineLayout);
    if (this.mWid == null)
      this.mTipElement.measure(this.tipLayout);
    while (true)
    {
      this.mArrowElement.measure(this.arrowLayout);
      this.mWsqElement.measure(this.wsqLayout);
      this.mLabelViewElement.measure(this.labelLayout);
      this.mTipElement.setTextSize(SkinManager.getInstance().getMiddleTextSize());
      this.mBg.measure(this.standardLayout);
      measureAvatars();
      setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
      return;
      this.mTipElement.measure(this.wsqTipLayout);
    }
  }

  public void onNotification(String paramString)
  {
    if (this.mWid != null)
      this.mLabelViewElement.setId(this.mWid);
  }

  public void onRecvDataException(String paramString, InfoManager.DataExceptionStatus paramDataExceptionStatus)
  {
  }

  public void update(String paramString, Object paramObject)
  {
    int i = 0;
    ProgramNode localProgramNode;
    if (paramString.equalsIgnoreCase("setNode"))
    {
      localProgramNode = (ProgramNode)paramObject;
      if (localProgramNode != null);
    }
    do
    {
      return;
      this.mGroupId = null;
      this.mProgramId = localProgramNode.id;
      if (localProgramNode.channelType == 0)
      {
        if (localProgramNode.groupId != 0)
          this.mGroupId = String.valueOf(localProgramNode.groupId);
        this.mChannelId = localProgramNode.channelId;
      }
      while ((this.mGroupId != null) && (this.mGroupId.length() > 0))
      {
        List localList = IMAgent.getInstance().getGroupMembers(this.mGroupId);
        if (localList == null)
        {
          IMAgent.getInstance().loadGroupMembers(this.mGroupId, 1, 5);
          return;
          ChannelNode localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
          if (localChannelNode != null)
          {
            if (localChannelNode.groupId != 0)
              this.mGroupId = String.valueOf(localChannelNode.groupId);
            this.mChannelId = localChannelNode.channelId;
          }
        }
        else
        {
          setDatas(localList);
          return;
        }
      }
      setDatas(null);
      return;
      if (paramString.equalsIgnoreCase("useWsq"))
      {
        this.mWid = ((String)paramObject);
        if (InfoManager.getInstance().getWsqEntry(this.mChannelId) == null)
          this.mTipElement.setText("进入微社区，火热话题进行中");
        while (true)
        {
          this.mTipElement.setAlignment(Layout.Alignment.ALIGN_NORMAL);
          this.mWsqElement.setVisible(0);
          this.mLabelViewElement.setVisible(0);
          RoundAvatarElement[] arrayOfRoundAvatarElement2 = this.mAvatarElements;
          int m = arrayOfRoundAvatarElement2.length;
          while (i < m)
          {
            arrayOfRoundAvatarElement2[i].setVisible(4);
            i++;
          }
          this.mTipElement.setText(InfoManager.getInstance().getWsqEntry(this.mChannelId));
        }
        requestLayout();
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("noWsq"));
    this.mWid = null;
    this.mTipElement.setText("进入官方群聊天");
    this.mTipElement.setAlignment(Layout.Alignment.ALIGN_OPPOSITE);
    this.mWsqElement.setVisible(4);
    this.mLabelViewElement.setVisible(4);
    RoundAvatarElement[] arrayOfRoundAvatarElement1 = this.mAvatarElements;
    int j = arrayOfRoundAvatarElement1.length;
    for (int k = 0; k < j; k++)
      arrayOfRoundAvatarElement1[k].setVisible(0);
    requestLayout();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.playview.PlayJoinChatView
 * JD-Core Version:    0.6.2
 */