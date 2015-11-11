package fm.qingting.qtradio.view.im.profile;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout.Alignment;
import android.view.View.MeasureSpec;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.ButtonViewElement;
import fm.qingting.framework.view.ImageViewElement;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.TextViewElement;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.room.SnsInfo;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.view.chatroom.broadcastor.RoundAvatarElement;
import java.util.List;

public class GroupMembersView extends QtView
{
  private static final String GROUPTYPE = "群组成员";
  private static final int MAXMEMBER = 4;
  private final ViewLayout arrowLayout = this.itemLayout.createChildLT(36, 36, 660, 42, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 120, 720, 120, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout lineLayout = this.itemLayout.createChildLT(720, 1, 180, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private ImageViewElement mArrowElement;
  private ButtonViewElement mBg;
  private int mHash = hashCode();
  private List<UserInfo> mInfos;
  private RoundAvatarElement[] mMembersElements;
  private boolean mNeedMatchParentLine = false;
  private boolean mNeedMeasure = true;
  private TextViewElement mNumberElement;
  private TextViewElement mTypeElement;
  private final ViewLayout memberLayout = this.itemLayout.createChildLT(90, 90, 20, 15, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout numberLayout = this.itemLayout.createChildLT(160, 45, 0, 65, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout typeLayout = this.itemLayout.createChildLT(160, 45, 0, 17, ViewLayout.SCALE_FLAG_SLTCW);

  public GroupMembersView(Context paramContext)
  {
    super(paramContext);
    this.mBg = new ButtonViewElement(paramContext);
    this.mBg.setBackgroundColor(SkinManager.getItemHighlightMaskColor(), 0);
    addElement(this.mBg);
    this.mBg.setOnElementClickListener(new ViewElement.OnElementClickListener()
    {
      public void onElementClick(ViewElement paramAnonymousViewElement)
      {
        if (GroupMembersView.this.mInfos == null)
          return;
        ControllerManager.getInstance().openImGroupMemberListController(GroupMembersView.this.mInfos);
      }
    });
    this.mTypeElement = new TextViewElement(paramContext);
    this.mTypeElement.setMaxLineLimit(1);
    this.mTypeElement.setText("群组成员", false);
    this.mTypeElement.setAlignment(Layout.Alignment.ALIGN_CENTER);
    this.mTypeElement.setColor(SkinManager.getTextColorNormal());
    addElement(this.mTypeElement);
    this.mNumberElement = new TextViewElement(paramContext);
    this.mNumberElement.setAlignment(Layout.Alignment.ALIGN_CENTER);
    this.mNumberElement.setMaxLineLimit(1);
    this.mNumberElement.setColor(SkinManager.getTextColorSubInfo());
    addElement(this.mNumberElement);
    this.mMembersElements = new RoundAvatarElement[4];
    this.mArrowElement = new ImageViewElement(paramContext);
    this.mArrowElement.setImageRes(2130837694);
    addElement(this.mArrowElement, this.mHash);
  }

  private void drawLine(Canvas paramCanvas)
  {
    SkinManager localSkinManager = SkinManager.getInstance();
    if (this.mNeedMatchParentLine);
    for (int i = 0; ; i = this.lineLayout.leftMargin)
    {
      localSkinManager.drawHorizontalLine(paramCanvas, i, this.itemLayout.width, getMeasuredHeight() - this.lineLayout.height, this.lineLayout.height);
      return;
    }
  }

  private void measureAvatars()
  {
    int i = this.lineLayout.leftMargin;
    for (int j = 0; j < this.mMembersElements.length; j++)
    {
      RoundAvatarElement localRoundAvatarElement = this.mMembersElements[j];
      if (localRoundAvatarElement != null)
      {
        localRoundAvatarElement.measure(i + this.memberLayout.leftMargin, this.memberLayout.topMargin, i + this.memberLayout.getRight(), this.memberLayout.getBottom());
        i += this.memberLayout.getRight();
      }
    }
  }

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    drawLine(paramCanvas);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.itemLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.typeLayout.scaleToBounds(this.itemLayout);
    this.memberLayout.scaleToBounds(this.itemLayout);
    this.lineLayout.scaleToBounds(this.itemLayout);
    this.numberLayout.scaleToBounds(this.itemLayout);
    this.arrowLayout.scaleToBounds(this.itemLayout);
    measureAvatars();
    this.mBg.measure(this.itemLayout);
    this.mArrowElement.measure(this.arrowLayout);
    this.mTypeElement.measure(this.typeLayout);
    this.mTypeElement.setTextSize(SkinManager.getInstance().getMiddleTextSize());
    this.mNumberElement.measure(this.numberLayout);
    this.mNumberElement.setTextSize(SkinManager.getInstance().getSubTextSize());
    this.mNeedMeasure = true;
    setMeasuredDimension(this.itemLayout.width, this.itemLayout.height);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.mInfos = ((List)paramObject);
      if (this.mInfos == null)
        break label274;
    }
    label274: for (int i = this.mInfos.size(); ; i = 0)
    {
      int j = Math.min(i, 4);
      int k = 0;
      if (k < j)
      {
        if (this.mMembersElements[k] == null)
        {
          RoundAvatarElement localRoundAvatarElement1 = new RoundAvatarElement(getContext());
          localRoundAvatarElement1.setDefaultImageRes(2130837698);
          localRoundAvatarElement1.setImageUrl(((UserInfo)this.mInfos.get(k)).snsInfo.sns_avatar);
          this.mMembersElements[k] = localRoundAvatarElement1;
          addElement(localRoundAvatarElement1, this.mHash);
        }
        while (true)
        {
          k++;
          break;
          RoundAvatarElement localRoundAvatarElement2 = this.mMembersElements[k];
          localRoundAvatarElement2.setImageUrl(((UserInfo)this.mInfos.get(k)).snsInfo.sns_avatar);
          localRoundAvatarElement2.setVisible(0);
        }
      }
      if (j < 4)
        for (int m = j; m < 4; m++)
          if (this.mMembersElements[m] != null)
            this.mMembersElements[m].setVisible(4);
      if (this.mNeedMeasure)
        measureAvatars();
      do
      {
        do
        {
          return;
          if (!paramString.equalsIgnoreCase("setCnt"))
            break;
        }
        while (paramObject == null);
        this.mNumberElement.setText(String.valueOf(paramObject));
        return;
      }
      while (!paramString.equalsIgnoreCase("needfillline"));
      this.mNeedMatchParentLine = true;
      return;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.im.profile.GroupMembersView
 * JD-Core Version:    0.6.2
 */