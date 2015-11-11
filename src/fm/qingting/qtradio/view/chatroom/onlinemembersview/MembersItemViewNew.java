package fm.qingting.qtradio.view.chatroom.onlinemembersview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.View.MeasureSpec;
import fm.qingting.framework.view.ButtonViewElement;
import fm.qingting.framework.view.ImageViewElement;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.TextViewElement;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.room.SnsInfo;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.view.chatroom.broadcastor.RoundAvatarElement;

public class MembersItemViewNew extends QtView
{
  private final ViewLayout avatarLayout = this.itemLayout.createChildLT(84, 84, 60, 26, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout genderLayout = this.itemLayout.createChildLT(19, 18, 200, 59, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 136, 720, 136, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout lineLayout = this.itemLayout.createChildLT(720, 1, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private RoundAvatarElement mAvatarElement;
  private ButtonViewElement mBg;
  private ImageViewElement mGenderElement;
  private TextViewElement mNameElement;
  private final ViewLayout nameLayout = this.itemLayout.createChildLT(480, 50, 230, 43, ViewLayout.SCALE_FLAG_SLTCW);

  public MembersItemViewNew(Context paramContext, int paramInt)
  {
    super(paramContext);
    this.mBg = new ButtonViewElement(paramContext);
    this.mBg.setBackgroundColor(SkinManager.getItemHighlightMaskColor(), 0);
    addElement(this.mBg);
    this.mBg.setOnElementClickListener(new ViewElement.OnElementClickListener()
    {
      public void onElementClick(ViewElement paramAnonymousViewElement)
      {
        Point localPoint = new Point(MembersItemViewNew.this.avatarLayout.leftMargin + MembersItemViewNew.this.avatarLayout.width / 2, MembersItemViewNew.this.getTop() + MembersItemViewNew.this.avatarLayout.getBottom());
        MembersItemViewNew.this.dispatchActionEvent("select", localPoint);
      }
    });
    this.mAvatarElement = new RoundAvatarElement(paramContext);
    this.mAvatarElement.setDefaultImageRes(2130837590);
    addElement(this.mAvatarElement, paramInt);
    this.mGenderElement = new ImageViewElement(paramContext);
    addElement(this.mGenderElement, paramInt);
    this.mNameElement = new TextViewElement(paramContext);
    this.mNameElement.setColor(SkinManager.getTextColorNormal());
    this.mNameElement.setMaxLineLimit(1);
    addElement(this.mNameElement);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    SkinManager.getInstance().drawHorizontalLine(paramCanvas, 0, this.itemLayout.width, this.itemLayout.height - this.lineLayout.height, this.lineLayout.height);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.itemLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.avatarLayout.scaleToBounds(this.itemLayout);
    this.genderLayout.scaleToBounds(this.itemLayout);
    this.nameLayout.scaleToBounds(this.itemLayout);
    this.lineLayout.scaleToBounds(this.itemLayout);
    this.mBg.measure(this.itemLayout);
    this.mAvatarElement.measure(this.avatarLayout);
    this.mGenderElement.measure(this.genderLayout);
    this.mNameElement.measure(this.nameLayout);
    this.mNameElement.setTextSize(SkinManager.getInstance().getNormalTextSize());
    setMeasuredDimension(this.itemLayout.width, this.itemLayout.height);
  }

  public void update(String paramString, Object paramObject)
  {
    String str1;
    String str3;
    String str2;
    if (paramString.equalsIgnoreCase("content"))
    {
      UserInfo localUserInfo = (UserInfo)paramObject;
      str1 = "n";
      if (localUserInfo.snsInfo == null)
        break label124;
      str3 = localUserInfo.snsInfo.sns_avatar;
      str1 = localUserInfo.snsInfo.sns_gender;
      str2 = localUserInfo.snsInfo.sns_name;
    }
    while (true)
    {
      this.mAvatarElement.setImageUrl(str3);
      if ((str1 == null) || (str1.equalsIgnoreCase("n")))
        str1 = "f";
      ImageViewElement localImageViewElement = this.mGenderElement;
      if (str1.equalsIgnoreCase("m"));
      for (int i = 2130837587; ; i = 2130837577)
      {
        localImageViewElement.setImageRes(i);
        this.mNameElement.setText(str2);
        return;
      }
      label124: str2 = null;
      str3 = null;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.chatroom.onlinemembersview.MembersItemViewNew
 * JD-Core Version:    0.6.2
 */