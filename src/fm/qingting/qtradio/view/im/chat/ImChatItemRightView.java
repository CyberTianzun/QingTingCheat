package fm.qingting.qtradio.view.im.chat;

import android.content.Context;
import android.view.View.MeasureSpec;
import fm.qingting.framework.view.ImageViewElement;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.im.BaseUserInfoPool;
import fm.qingting.qtradio.im.IMAgent;
import fm.qingting.qtradio.im.message.IMMessage;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.room.SnsInfo;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.social.UserProfile;
import fm.qingting.qtradio.view.chatroom.broadcastor.RoundAvatarElement;
import fm.qingting.qtradio.view.chatroom.chatlist.BubbleRightViewElement;
import fm.qingting.qtradio.view.chatroom.chatlist.ChatItem;

public class ImChatItemRightView extends QtView
{
  private final ViewLayout avatarLayout = this.itemLayout.createChildLT(94, 94, 604, 5, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout bubbleLayout = this.itemLayout.createChildLT(540, 94, 50, 16, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 124, 720, 124, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private RoundAvatarElement mAvatarElement;
  private BubbleRightViewElement mBubbleRightViewElement;
  private IMMessage mData;
  private ImageViewElement mDefaultElement;

  public ImChatItemRightView(Context paramContext, int paramInt)
  {
    super(paramContext);
    this.mDefaultElement = new ImageViewElement(paramContext);
    this.mDefaultElement.setImageRes(2130837590);
    addElement(this.mDefaultElement, paramInt);
    this.mAvatarElement = new RoundAvatarElement(paramContext);
    addElement(this.mAvatarElement, paramInt);
    this.mBubbleRightViewElement = new BubbleRightViewElement(paramContext);
    addElement(this.mBubbleRightViewElement, paramInt);
  }

  private int getBubbleResource(int paramInt)
  {
    int i = 2130837571;
    switch (paramInt)
    {
    default:
      i = 2130837566;
    case 2:
    case 4:
      return i;
    case 3:
      return 2130837570;
    case 1:
      return 2130837572;
    case 19:
      return 2130837566;
    case 18:
      return 2130837567;
    case 17:
      return 2130837568;
    case 20:
    }
    return 2130837569;
  }

  private int getThisHeight()
  {
    int i = this.mBubbleRightViewElement.getTopMargin() + this.mBubbleRightViewElement.getHeight() + this.bubbleLayout.topMargin;
    if (i < this.itemLayout.height)
      i = this.itemLayout.height;
    return i;
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.itemLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.avatarLayout.scaleToBounds(this.itemLayout);
    this.bubbleLayout.scaleToBounds(this.itemLayout);
    this.mDefaultElement.measure(this.avatarLayout);
    this.mAvatarElement.measure(this.avatarLayout);
    this.mBubbleRightViewElement.measure(this.bubbleLayout);
    setMeasuredDimension(this.itemLayout.width, getThisHeight());
  }

  public void update(String paramString, Object paramObject)
  {
    ChatItem localChatItem;
    if (paramString.equalsIgnoreCase("content"))
    {
      if (paramObject == null)
        return;
      localChatItem = (ChatItem)paramObject;
      this.mData = ((IMMessage)localChatItem.data);
      if (InfoManager.getInstance().getUserProfile().getUserInfo() == null)
        break label193;
    }
    label193: for (String str3 = InfoManager.getInstance().getUserProfile().getUserInfo().snsInfo.sns_avatar; ; str3 = null)
    {
      if (str3 == null)
      {
        str3 = BaseUserInfoPool.getAvatar(this.mData.mFromID);
        if (str3 == null)
          BaseUserInfoPool.loadBaseInfo(this.mData.mFromID, null, IMAgent.getInstance());
      }
      String str4 = this.mData.mMessage;
      this.mAvatarElement.setImageUrl(str3);
      this.mBubbleRightViewElement.setBubbleResource(getBubbleResource(localChatItem.type));
      this.mBubbleRightViewElement.setText(str4);
      requestLayout();
      return;
      if (!paramString.equalsIgnoreCase("invalidateAvatar"))
        break;
      String str1 = (String)paramObject;
      if ((str1 == null) || (!str1.equalsIgnoreCase(this.mData.mFromID)))
        break;
      String str2 = BaseUserInfoPool.getAvatar(str1);
      this.mAvatarElement.setImageUrl(str2);
      return;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.im.chat.ImChatItemRightView
 * JD-Core Version:    0.6.2
 */