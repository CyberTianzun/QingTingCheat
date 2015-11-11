package fm.qingting.qtradio.view.search;

import android.content.Context;
import android.view.View.MeasureSpec;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.view.ButtonViewElement;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.controller.SearchController;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.search.SearchNode;
import java.util.List;

public class SearchViewMoreView extends QtView
{
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 90, 720, 90, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private ButtonViewElement mButtonViewElement;
  private int mType;

  public SearchViewMoreView(Context paramContext)
  {
    super(paramContext);
    this.mButtonViewElement = new ButtonViewElement(paramContext);
    this.mButtonViewElement.setBackgroundColor(SkinManager.getItemHighlightMaskColor(), SkinManager.getCardColor());
    this.mButtonViewElement.setTextColor(SkinManager.getTextColorNormal());
    addElement(this.mButtonViewElement);
    this.mButtonViewElement.setOnElementClickListener(new ViewElement.OnElementClickListener()
    {
      public void onElementClick(ViewElement paramAnonymousViewElement)
      {
        ViewController localViewController = ControllerManager.getInstance().getLastViewController();
        if ((localViewController instanceof SearchController))
          ((SearchController)localViewController).config("selectTab", Integer.valueOf(SearchViewMoreView.this.mType));
      }
    });
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.itemLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.mButtonViewElement.measure(this.itemLayout);
    this.mButtonViewElement.setTextSize(SkinManager.getInstance().getSubTextSize());
    setMeasuredDimension(this.itemLayout.width, this.itemLayout.height);
  }

  public void update(String paramString, Object paramObject)
  {
    List localList;
    if (paramString.equalsIgnoreCase("content"))
    {
      this.mType = ((Integer)paramObject).intValue();
      localList = InfoManager.getInstance().root().mSearchNode.getResult(this.mType);
      if (localList != null)
        break label88;
    }
    label88: for (int i = 0; ; i = localList.size())
    {
      this.mButtonViewElement.setText("查看全部" + i + "个" + SearchNode.TABNAMES[this.mType]);
      return;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.search.SearchViewMoreView
 * JD-Core Version:    0.6.2
 */