package fm.qingting.qtradio.view.personalcenter.feedback;

import android.content.Context;
import android.view.View.MeasureSpec;
import com.umeng.fb.model.DevReply;
import fm.qingting.framework.view.ImageViewElement;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.TextViewElement;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.view.chatroom.broadcastor.RoundAvatarElement;
import fm.qingting.qtradio.view.chatroom.chatlist.BubbleLeftViewElement;
import fm.qingting.qtradio.view.chatroom.chatlist.ChatItem;

public class FeedbackItemLeftView extends QtView
{
  private final ViewLayout avatarLayout = this.itemLayout.createChildLT(94, 94, 22, 25, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout bubbleLayout = this.itemLayout.createChildLT(540, 94, 130, 50, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout genderLayout = this.itemLayout.createChildLT(19, 18, 156, 16, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 124, 720, 124, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private RoundAvatarElement mAvatarElement;
  private BubbleLeftViewElement mBubbleLeftViewElement;
  private ImageViewElement mGenderElement;
  private TextViewElement mNameElement;
  private final ViewLayout nameLayout = this.itemLayout.createChildLT(500, 30, 180, 10, ViewLayout.SCALE_FLAG_SLTCW);

  public FeedbackItemLeftView(Context paramContext, int paramInt)
  {
    super(paramContext);
    this.mAvatarElement = new RoundAvatarElement(paramContext);
    this.mAvatarElement.setDefaultImageRes(2130837749);
    addElement(this.mAvatarElement, paramInt);
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
    String str;
    ImageViewElement localImageViewElement;
    if ((localChatItem.data instanceof DevReply))
    {
      str = ((DevReply)localChatItem.data).getContent();
      this.mAvatarElement.setImageUrl(null);
      localImageViewElement = this.mGenderElement;
      if (("f" != null) && (!"f".equalsIgnoreCase("m")))
        break label132;
    }
    label132: for (int i = 2130837587; ; i = 2130837577)
    {
      localImageViewElement.setImageRes(i);
      this.mNameElement.setText("客服MM", false);
      this.mBubbleLeftViewElement.setBubbleResource(getBubbleResource(localChatItem.type));
      this.mBubbleLeftViewElement.setText(str);
      requestLayout();
      return;
      str = (String)localChatItem.data;
      break;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.personalcenter.feedback.FeedbackItemLeftView
 * JD-Core Version:    0.6.2
 */