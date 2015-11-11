package fm.qingting.qtradio.view.im.chat;

import android.content.Context;
import android.view.View.MeasureSpec;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.view.ImageViewElement;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.TextViewElement;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.im.BaseUserInfoPool;
import fm.qingting.qtradio.im.IMAgent;
import fm.qingting.qtradio.im.message.IMMessage;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.view.chatroom.broadcastor.RoundAvatarElement;
import fm.qingting.qtradio.view.chatroom.chatlist.BubbleLeftViewElement;
import fm.qingting.qtradio.view.chatroom.chatlist.ChatItem;

public class ImChatItemLeftView extends QtView
{
  private final ViewLayout avatarLayout = this.itemLayout.createChildLT(94, 94, 22, 25, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout bubbleLayout = this.itemLayout.createChildLT(540, 94, 130, 50, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout genderLayout = this.itemLayout.createChildLT(19, 18, 156, 16, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 124, 720, 124, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private RoundAvatarElement mAvatarElement;
  private BubbleLeftViewElement mBubbleLeftViewElement;
  private IMMessage mCustomData;
  private ImageViewElement mDefaultElement;
  private ImageViewElement mGenderElement;
  private TextViewElement mNameElement;
  private final ViewLayout nameLayout = this.itemLayout.createChildLT(500, 30, 180, 10, ViewLayout.SCALE_FLAG_SLTCW);

  public ImChatItemLeftView(Context paramContext, int paramInt, boolean paramBoolean)
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
          if (ImChatItemLeftView.this.mCustomData != null)
            EventDispacthManager.getInstance().dispatchAction("showimmenu", ImChatItemLeftView.this.mCustomData);
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
    int i = 2130837577;
    ChatItem localChatItem;
    String str4;
    String str5;
    String str6;
    int j;
    if (paramString.equalsIgnoreCase("content"))
    {
      if (paramObject == null)
        return;
      localChatItem = (ChatItem)paramObject;
      this.mCustomData = ((IMMessage)localChatItem.data);
      str4 = this.mCustomData.mGender;
      str5 = this.mCustomData.mFromAvatar;
      if ((str5 != null) && (!str5.equalsIgnoreCase("")))
        break label377;
      str6 = BaseUserInfoPool.getAvatar(this.mCustomData.mFromID);
      if (str6 != null)
        break label371;
      j = 1;
      BaseUserInfoPool.loadBaseInfo(this.mCustomData.mFromID, this.mCustomData.mFromGroupId, IMAgent.getInstance());
    }
    while (true)
    {
      String str7 = this.mCustomData.mFromName;
      String str8 = this.mCustomData.mMessage;
      this.mAvatarElement.setImageUrl(str6);
      String str9;
      if ((str4 == null) || (str4.equalsIgnoreCase("n")))
      {
        str9 = BaseUserInfoPool.getGender(this.mCustomData.mFromID);
        if ((str9 == null) && (j == 0))
          BaseUserInfoPool.loadBaseInfo(this.mCustomData.mFromID, this.mCustomData.mFromGroupId, IMAgent.getInstance());
      }
      for (String str10 = str9; ; str10 = str4)
      {
        ImageViewElement localImageViewElement2 = this.mGenderElement;
        if ((str10 == null) || (str10.equalsIgnoreCase("m")));
        for (int k = 2130837587; ; k = i)
        {
          localImageViewElement2.setImageRes(k);
          this.mNameElement.setText(str7, false);
          this.mBubbleLeftViewElement.setBubbleResource(getBubbleResource(localChatItem.type));
          this.mBubbleLeftViewElement.setText(str8);
          requestLayout();
          return;
        }
        if (!paramString.equalsIgnoreCase("invalidateAvatar"))
          break;
        String str1 = (String)paramObject;
        if ((str1 == null) || (!str1.equalsIgnoreCase(this.mCustomData.mFromID)))
          break;
        String str2 = BaseUserInfoPool.getGender(str1);
        ImageViewElement localImageViewElement1 = this.mGenderElement;
        if ((str2 == null) || (str2.equalsIgnoreCase("m")))
          i = 2130837587;
        localImageViewElement1.setImageRes(i);
        String str3 = BaseUserInfoPool.getAvatar(str1);
        this.mAvatarElement.setImageUrl(str3);
        return;
      }
      label371: j = 0;
      continue;
      label377: str6 = str5;
      j = 0;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.im.chat.ImChatItemLeftView
 * JD-Core Version:    0.6.2
 */