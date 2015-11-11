package fm.qingting.qtradio.view.personalcenter.mydownload;

import android.content.Context;
import android.view.View.MeasureSpec;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.view.ButtonViewElement;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.TextViewElement;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.DownLoadInfoNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.view.playview.LineElement;
import fm.qingting.qtradio.view.popviews.AlertParam;
import fm.qingting.qtradio.view.popviews.AlertParam.Builder;
import fm.qingting.qtradio.view.popviews.AlertParam.OnButtonClickListener;
import fm.qingting.utils.ScreenType;
import java.util.Locale;

public class MyDownloadChannelItemView extends QtView
  implements ViewElement.OnElementClickListener
{
  private final ViewLayout deleteLayout = this.itemLayout.createChildLT(50, 50, 630, 43, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout infoLayout = this.itemLayout.createChildLT(600, 45, 30, 75, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 136, 720, 800, 0, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout lineLayout = this.itemLayout.createChildLT(720, 1, 0, 135, ViewLayout.SCALE_FLAG_SLTCW);
  private ButtonViewElement mBg;
  private ButtonViewElement mDeleteElement;
  private TextViewElement mInfoElement;
  private LineElement mLineElement;
  private TextViewElement mNameElement;
  private ChannelNode mNode;
  private TextViewElement mTimeElement;
  private final ViewLayout nameLayout = this.itemLayout.createChildLT(600, 45, 30, 20, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout timeLayout = this.itemLayout.createChildLT(400, 45, 300, 75, ViewLayout.SCALE_FLAG_SLTCW);

  public MyDownloadChannelItemView(Context paramContext, int paramInt)
  {
    super(paramContext);
    this.mBg = new ButtonViewElement(paramContext);
    this.mBg.setBackgroundColor(0, SkinManager.getCardColor());
    addElement(this.mBg);
    this.mBg.setOnElementClickListener(this);
    this.mNameElement = new TextViewElement(paramContext);
    this.mNameElement.setMaxLineLimit(1);
    this.mNameElement.setColor(SkinManager.getTextColorNormal());
    addElement(this.mNameElement);
    this.mInfoElement = new TextViewElement(paramContext);
    this.mInfoElement.setMaxLineLimit(1);
    this.mInfoElement.setColor(SkinManager.getTextColorSubInfo());
    addElement(this.mInfoElement);
    this.mTimeElement = new TextViewElement(paramContext);
    this.mTimeElement.setMaxLineLimit(1);
    this.mTimeElement.setColor(SkinManager.getTextColorSubInfo());
    addElement(this.mTimeElement);
    this.mDeleteElement = new ButtonViewElement(paramContext);
    this.mDeleteElement.setBackground(2130837747, 2130837746);
    addElement(this.mDeleteElement, paramInt);
    this.mDeleteElement.setOnElementClickListener(this);
    this.mDeleteElement.expandHotPot(ScreenType.getCustomExtraBound());
    this.mLineElement = new LineElement(paramContext);
    this.mLineElement.setColor(SkinManager.getDividerColor());
    this.mLineElement.setOrientation(1);
    addElement(this.mLineElement);
  }

  private void delete()
  {
    AlertParam.Builder localBuilder = new AlertParam.Builder();
    localBuilder.setMessage("确定删除" + this.mNode.title + "及其中的所有节目吗？").addButton("取消").addButton("确定").setListener(new AlertParam.OnButtonClickListener()
    {
      public void onClick(int paramAnonymousInt, boolean paramAnonymousBoolean)
      {
        if (paramAnonymousInt == 1)
          InfoManager.getInstance().root().mDownLoadInfoNode.delDownLoad(MyDownloadChannelItemView.this.mNode, true);
        EventDispacthManager.getInstance().dispatchAction("cancelPop", null);
      }
    });
    AlertParam localAlertParam = localBuilder.create();
    EventDispacthManager.getInstance().dispatchAction("showAlert", localAlertParam);
  }

  public void onElementClick(ViewElement paramViewElement)
  {
    if (paramViewElement == this.mDeleteElement)
      delete();
    while (paramViewElement != this.mBg)
      return;
    ControllerManager.getInstance().redirectToDownloadProgramsController(this.mNode);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.itemLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.nameLayout.scaleToBounds(this.itemLayout);
    this.infoLayout.scaleToBounds(this.itemLayout);
    this.lineLayout.scaleToBounds(this.itemLayout);
    this.deleteLayout.scaleToBounds(this.itemLayout);
    this.timeLayout.scaleToBounds(this.itemLayout);
    this.mBg.measure(this.itemLayout);
    this.mNameElement.measure(this.nameLayout);
    this.mInfoElement.measure(this.infoLayout);
    this.mLineElement.measure(this.lineLayout);
    this.mDeleteElement.measure(this.deleteLayout);
    this.mTimeElement.measure(this.timeLayout);
    this.mNameElement.setTextSize(SkinManager.getInstance().getNormalTextSize());
    this.mInfoElement.setTextSize(SkinManager.getInstance().getSubTextSize());
    this.mTimeElement.setTextSize(SkinManager.getInstance().getSubTextSize());
    setMeasuredDimension(this.itemLayout.width, this.itemLayout.height);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("content"))
    {
      this.mNode = ((ChannelNode)paramObject);
      this.mNameElement.setText(this.mNode.title, false);
      TextViewElement localTextViewElement = this.mInfoElement;
      Locale localLocale = Locale.CHINA;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(this.mNode.programCnt);
      localTextViewElement.setText(String.format(localLocale, "%d个文件", arrayOfObject), false);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.personalcenter.mydownload.MyDownloadChannelItemView
 * JD-Core Version:    0.6.2
 */