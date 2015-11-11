package fm.qingting.qtradio.view.popviews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.Layout.Alignment;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.widget.Toast;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.ButtonViewElement;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.TextViewElement;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.view.playview.LineElement;
import fm.qingting.utils.QTMSGManage;
import java.util.List;

public class CustomPopActionView extends QtView
  implements ViewElement.OnElementClickListener
{
  private final int MAX_CNT = 3;
  private final ViewLayout cancelLayout = this.standardLayout.createChildLT(720, 100, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout itemLayout = this.standardLayout.createChildLT(136, 220, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout lineLayout = this.standardLayout.createChildLT(720, 1, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private ButtonViewElement mBg;
  private int mButtonCnt = 0;
  private ButtonViewElement mCancelElement;
  private CustomPopActionParam mCustomPopActionParam;
  private int mHash = hashCode();
  private PopActionButtonElement[] mItems;
  private LineElement mLineElement;
  private TextViewElement mTitleElement;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);
  private final ViewLayout titleLayout = this.standardLayout.createChildLT(720, 100, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);

  public CustomPopActionView(Context paramContext)
  {
    super(paramContext);
    this.mBg = new ButtonViewElement(paramContext);
    this.mBg.setBackgroundColor(SkinManager.getNewPopBgColor(), SkinManager.getNewPopBgColor());
    addElement(this.mBg);
    this.mTitleElement = new TextViewElement(paramContext);
    this.mTitleElement.setMaxLineLimit(1);
    this.mTitleElement.setAlignment(Layout.Alignment.ALIGN_CENTER);
    this.mTitleElement.setColor(SkinManager.getNewPopTextColor());
    this.mTitleElement.setText("分享内容到", false);
    addElement(this.mTitleElement);
    this.mCancelElement = new ButtonViewElement(paramContext);
    this.mCancelElement.setBackgroundColor(SkinManager.getItemHighlightMaskColor(), 0);
    this.mCancelElement.setText("取消");
    addElement(this.mCancelElement);
    this.mCancelElement.setTextColor(SkinManager.getNewPopTextColor());
    this.mCancelElement.setOnElementClickListener(this);
    this.mLineElement = new LineElement(paramContext);
    this.mLineElement.setColor(SkinManager.getDividerColor());
    this.mLineElement.setOrientation(1);
    addElement(this.mLineElement);
  }

  private void dispatchSelectEvent()
  {
  }

  private int getLineCnt(int paramInt)
  {
    int i = paramInt / 3;
    if (paramInt % 3 == 0);
    for (int j = 0; ; j = 1)
      return j + i;
  }

  private void pickPhoto()
  {
    Intent localIntent = new Intent("android.intent.action.GET_CONTENT", null);
    localIntent.setType("image/*");
    ((Activity)getContext()).startActivityForResult(localIntent, 71);
  }

  private void takePhoto()
  {
    if (Environment.getExternalStorageState().equals("mounted"))
    {
      Intent localIntent = new Intent("android.media.action.IMAGE_CAPTURE");
      localIntent.putExtra("output", Uri.parse("file:///sdcard/qt_danmaku_capture.jpg"));
      ((Activity)getContext()).startActivityForResult(localIntent, 73);
      return;
    }
    Toast.makeText(getContext(), "内存卡不存在", 1).show();
  }

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getAction() == 0)
    {
      int i = this.titleLayout.height + this.itemLayout.height * getLineCnt(this.mButtonCnt) + this.cancelLayout.height;
      if (paramMotionEvent.getY() < this.standardLayout.height - i)
      {
        dispatchActionEvent("cancelPop", null);
        return true;
      }
    }
    return super.dispatchTouchEvent(paramMotionEvent);
  }

  public void onElementClick(ViewElement paramViewElement)
  {
    if (this.mCancelElement == paramViewElement)
      dispatchActionEvent("cancelPop", null);
    if (this.mCustomPopActionParam == null);
    label307: 
    while (true)
    {
      return;
      List localList = this.mCustomPopActionParam.getButtons();
      if ((localList != null) && (localList.size() != 0))
        for (int i = 0; ; i++)
        {
          if (i >= this.mButtonCnt)
            break label307;
          if (paramViewElement == this.mItems[i])
            switch (((Integer)localList.get(i)).intValue())
            {
            default:
              return;
            case 0:
              QTMSGManage.getInstance().sendStatistcsMessage("VirtualProgramPopClick", "shareChoose");
              dispatchActionEvent("cancelPopWithoutAnimation", null);
              EventDispacthManager.getInstance().dispatchAction("shareChoose", this.mCustomPopActionParam.getNode());
              return;
            case 1:
              QTMSGManage.getInstance().sendStatistcsMessage("VirtualProgramPopClick", "download");
              dispatchActionEvent("download", this.mCustomPopActionParam.getNode());
              return;
            case 2:
              dispatchActionEvent("cancelPopWithoutAnimation", null);
              ControllerManager.getInstance().redirectToAddAlarmView(this.mCustomPopActionParam.getNode());
              return;
            case 3:
              dispatchActionEvent("cancelPopWithoutAnimation", null);
              ViewController localViewController = ControllerManager.getInstance().getLastViewController();
              if ((localViewController == null) || (!localViewController.controllerName.equalsIgnoreCase("mainplayview")))
                break;
              localViewController.config("showSchedule", null);
              return;
            case 4:
              dispatchActionEvent("cancelPopWithoutAnimation", null);
              takePhoto();
              return;
            case 5:
              dispatchActionEvent("cancelPopWithoutAnimation", null);
              pickPhoto();
              return;
            case 6:
              QTMSGManage.getInstance().sendStatistcsMessage("newnavi", "help");
              EventDispacthManager.getInstance().dispatchAction("showFeedbackPop", "faq");
              return;
            }
        }
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = 0;
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.titleLayout.scaleToBounds(this.standardLayout);
    this.itemLayout.scaleToBounds(this.standardLayout);
    this.cancelLayout.scaleToBounds(this.standardLayout);
    this.lineLayout.scaleToBounds(this.standardLayout);
    int j = this.mButtonCnt;
    int k = getLineCnt(j);
    int m = this.titleLayout.height + k * this.itemLayout.height + this.cancelLayout.height;
    int n = this.standardLayout.height - m;
    this.mBg.measure(0, n, this.standardLayout.width, this.standardLayout.height);
    this.mTitleElement.measure(this.titleLayout);
    this.mTitleElement.setTextSize(SkinManager.getInstance().getNormalTextSize());
    this.mTitleElement.setTranslationY(n);
    int i1 = Math.min(j, 3);
    int i2 = (this.standardLayout.width - i1 * this.itemLayout.width) / (i1 + 1);
    int i3 = n + this.titleLayout.height;
    int i4 = i2;
    while (i < this.mButtonCnt)
    {
      if (i % 3 == 0)
        i4 = i2;
      this.mItems[i].measure(this.itemLayout);
      this.mItems[i].setTranslationX(i4);
      this.mItems[i].setTranslationY(i3);
      if (i % 3 == 2)
        i3 += this.itemLayout.height;
      i4 += i2 + this.itemLayout.width;
      i++;
    }
    this.mCancelElement.measure(this.cancelLayout);
    this.mCancelElement.setTranslationY(this.standardLayout.height - this.cancelLayout.height);
    this.mCancelElement.setTextSize(SkinManager.getInstance().getNormalTextSize());
    this.mLineElement.measure(this.lineLayout);
    this.mLineElement.setTranslationY(this.standardLayout.height - this.cancelLayout.height);
    super.onMeasure(paramInt1, paramInt2);
  }

  public void update(String paramString, Object paramObject)
  {
    int i = 0;
    if (paramString.equalsIgnoreCase("setBubbleData"))
    {
      this.mCustomPopActionParam = ((CustomPopActionParam)paramObject);
      if (this.mCustomPopActionParam == null)
        requestLayout();
    }
    else
    {
      return;
    }
    List localList = this.mCustomPopActionParam.getButtons();
    if ((localList == null) || (localList.size() == 0))
    {
      requestLayout();
      return;
    }
    PopActionButtonElement[] arrayOfPopActionButtonElement = this.mItems;
    this.mTitleElement.setText(this.mCustomPopActionParam.getTitle(), false);
    int j = localList.size();
    if (this.mButtonCnt == 0)
    {
      this.mItems = new PopActionButtonElement[j];
      while (i < j)
      {
        int i5 = ((Integer)localList.get(i)).intValue();
        PopActionButtonElement localPopActionButtonElement4 = new PopActionButtonElement(getContext());
        this.mItems[i] = localPopActionButtonElement4;
        localPopActionButtonElement4.setAction(CustomPopAction.getName(i5), CustomPopAction.getIcon(i5));
        addElement(localPopActionButtonElement4, this.mHash);
        localPopActionButtonElement4.setOnElementClickListener(this);
        i++;
      }
      this.mButtonCnt = j;
    }
    while (true)
    {
      requestLayout();
      return;
      if (j > this.mButtonCnt)
      {
        this.mItems = new PopActionButtonElement[j];
        System.arraycopy(arrayOfPopActionButtonElement, 0, this.mItems, 0, this.mButtonCnt);
        for (int i1 = 0; i1 < this.mButtonCnt; i1++)
        {
          int i4 = ((Integer)localList.get(i1)).intValue();
          PopActionButtonElement localPopActionButtonElement3 = this.mItems[i1];
          localPopActionButtonElement3.setAction(CustomPopAction.getName(i4), CustomPopAction.getIcon(i4));
          localPopActionButtonElement3.setVisible(0);
        }
        for (int i2 = this.mButtonCnt; i2 < j; i2++)
        {
          PopActionButtonElement localPopActionButtonElement2 = new PopActionButtonElement(getContext());
          int i3 = ((Integer)localList.get(i2)).intValue();
          this.mItems[i2] = localPopActionButtonElement2;
          localPopActionButtonElement2.setAction(CustomPopAction.getName(i3), CustomPopAction.getIcon(i3));
          addElement(localPopActionButtonElement2);
          localPopActionButtonElement2.setOnElementClickListener(this);
        }
        this.mButtonCnt = j;
      }
      else
      {
        for (int k = 0; k < j; k++)
        {
          PopActionButtonElement localPopActionButtonElement1 = this.mItems[k];
          int n = ((Integer)localList.get(k)).intValue();
          localPopActionButtonElement1.setAction(CustomPopAction.getName(n), CustomPopAction.getIcon(n));
          localPopActionButtonElement1.setVisible(0);
        }
        for (int m = j; m < this.mButtonCnt; m++)
          this.mItems[m].setVisible(4);
        this.mButtonCnt = j;
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.popviews.CustomPopActionView
 * JD-Core Version:    0.6.2
 */