package fm.qingting.qtradio.view.chatroom.chatlist;

import android.content.Context;
import android.view.View.MeasureSpec;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.view.ImageViewElement;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.TextViewElement;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.room.ChatData;
import fm.qingting.qtradio.room.CustomData;
import fm.qingting.qtradio.room.SnsInfo;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.room.WeiboData;
import fm.qingting.qtradio.social.CloudCenter;
import fm.qingting.qtradio.view.chatroom.broadcastor.RoundAvatarElement;

public class ChatroomItemLeftView extends QtView
{
  private final ViewLayout avatarLayout = this.itemLayout.createChildLT(94, 94, 22, 25, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout bubbleLayout = this.itemLayout.createChildLT(540, 94, 130, 50, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout genderLayout = this.itemLayout.createChildLT(19, 18, 176, 16, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 124, 720, 124, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private RoundAvatarElement mAvatarElement;
  private BubbleLeftViewElement mBubbleLeftViewElement;
  private CustomData mCustomData;
  private ImageViewElement mDefaultElement;
  private ImageViewElement mGenderElement;
  private TextViewElement mNameElement;
  private final ViewLayout nameLayout = this.itemLayout.createChildLT(500, 30, 200, 10, ViewLayout.SCALE_FLAG_SLTCW);

  public ChatroomItemLeftView(Context paramContext, int paramInt, boolean paramBoolean)
  {
    super(paramContext);
    this.mDefaultElement = new ImageViewElement(paramContext);
    this.mDefaultElement.setImageRes(2130837590);
    addElement(this.mDefaultElement, paramInt);
    this.mAvatarElement = new RoundAvatarElement(paramContext);
    addElement(this.mAvatarElement, paramInt);
    if (paramBoolean)
      this.mAvatarElement.setOnElementClickListener(new ViewElement.OnElementClickListener()
      {
        public void onElementClick(ViewElement paramAnonymousViewElement)
        {
          EventDispacthManager.getInstance().dispatchAction("showChatUserAction", ChatroomItemLeftView.this.mCustomData);
        }
      });
    this.mGenderElement = new ImageViewElement(paramContext);
    this.mGenderElement.setImageRes(2130837577);
    addElement(this.mGenderElement, paramInt);
    this.mNameElement = new TextViewElement(paramContext);
    this.mNameElement.setColor(SkinManager.getTextColorNormal());
    this.mNameElement.setMaxLineLimit(1);
    this.mNameElement.setTextSize(SkinManager.getInstance().getTinyTextSize());
    addElement(this.mNameElement);
    this.mBubbleLeftViewElement = new BubbleLeftViewElement(paramContext);
    addElement(this.mBubbleLeftViewElement, paramInt);
  }

  private int getBubbleResource(int paramInt)
  {
    switch (paramInt)
    {
    case 19:
    default:
      return 2130837566;
    case 3:
      return 2130837570;
    case 2:
      return 2130837571;
    case 1:
      return 2130837572;
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
    int i = this.mBubbleLeftViewElement.getTopMargin() + this.mBubbleLeftViewElement.getHeight() + this.genderLayout.topMargin;
    if (i < this.itemLayout.height)
      i = this.itemLayout.height;
    return i;
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.itemLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.avatarLayout.scaleToBounds(this.itemLayout);
    this.genderLayout.scaleToBounds(this.itemLayout);
    this.nameLayout.scaleToBounds(this.itemLayout);
    this.bubbleLayout.scaleToBounds(this.itemLayout);
    this.mDefaultElement.measure(this.avatarLayout);
    this.mAvatarElement.measure(this.avatarLayout);
    this.mGenderElement.measure(this.genderLayout);
    this.mNameElement.measure(this.nameLayout);
    this.mBubbleLeftViewElement.measure(this.bubbleLayout);
    setMeasuredDimension(this.itemLayout.width, getThisHeight());
  }

  public void update(String paramString, Object paramObject)
  {
    if ((!paramString.equalsIgnoreCase("content")) || (paramObject == null))
      return;
    ChatItem localChatItem = (ChatItem)paramObject;
    this.mCustomData = ((CustomData)localChatItem.data);
    ChatData localChatData;
    String str2;
    String str5;
    String str1;
    Object localObject2;
    Object localObject1;
    if (this.mCustomData.type == 1)
    {
      localChatData = (ChatData)this.mCustomData;
      str2 = localChatData.user.snsInfo.sns_avatar;
      if (CloudCenter.getInstance().isLiveRoomAdmin())
      {
        str5 = localChatData.user.snsInfo.sns_name + "_" + localChatData.user.snsInfo.sns_id;
        String str6 = localChatData.content;
        str1 = localChatData.user.snsInfo.sns_gender;
        localObject2 = str5;
        localObject1 = str6;
      }
    }
    while (true)
    {
      this.mAvatarElement.setImageUrl(str2);
      ImageViewElement localImageViewElement = this.mGenderElement;
      if ((str1 == null) || (str1.equalsIgnoreCase("m")));
      for (int i = 2130837587; ; i = 2130837577)
      {
        localImageViewElement.setImageRes(i);
        this.mNameElement.setText((String)localObject2, false);
        this.mBubbleLeftViewElement.setBubbleResource(getBubbleResource(localChatItem.type));
        this.mBubbleLeftViewElement.setText((String)localObject1);
        requestLayout();
        return;
        str5 = localChatData.user.snsInfo.sns_name;
        break;
        if (this.mCustomData.type != 2)
          break label382;
        WeiboData localWeiboData = (WeiboData)this.mCustomData;
        str2 = localWeiboData.user.snsInfo.sns_avatar;
        if (CloudCenter.getInstance().isLiveRoomAdmin());
        for (String str3 = localWeiboData.user.snsInfo.sns_name + "_" + localWeiboData.user.snsInfo.sns_id; ; str3 = localWeiboData.user.snsInfo.sns_name)
        {
          String str4 = localWeiboData.content;
          str1 = localWeiboData.user.snsInfo.sns_gender;
          localObject2 = str3;
          localObject1 = str4;
          break;
        }
      }
      label382: str1 = null;
      localObject1 = null;
      localObject2 = null;
      str2 = null;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.chatroom.chatlist.ChatroomItemLeftView
 * JD-Core Version:    0.6.2
 */