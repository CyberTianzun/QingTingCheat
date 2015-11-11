package fm.qingting.qtradio.controller;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.qtradio.view.search.SearchViewNew;

public class SearchController extends ViewController
{
  private SearchViewNew mainView;

  public SearchController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "search";
    this.mainView = new SearchViewNew(paramContext);
    attachView(this.mainView);
  }

  public void config(String paramString, Object paramObject)
  {
    this.mainView.update(paramString, paramObject);
  }

  public void controllerDidPopped()
  {
    this.mainView.close(false);
    super.controllerDidPopped();
  }

  public void controllerDidPushed()
  {
    this.mainView.update("openKeyBoard", null);
    super.controllerDidPushed();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.SearchController
 * JD-Core Version:    0.6.2
 */