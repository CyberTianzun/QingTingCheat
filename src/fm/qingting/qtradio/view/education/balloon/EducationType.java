package fm.qingting.qtradio.view.education.balloon;

import android.content.Context;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.view.education.EducationFrontpageView;

public enum EducationType
{
  public static final int BOTTOM = 65536;
  public static final int CENTER_HORIZON = 256;
  public static final int CENTER_VERTICAL = 1048576;
  public static final int FILL = 0;
  public static final int LEFT = 1;
  public static final int MASK = 1118481;
  public static final int RIGHT = 16;
  public static final int TOP = 4096;
  private final ViewLayout collapseLayout = this.standardLayout.createChildLT(240, 100, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout favLayout = this.standardLayout.createChildLT(150, 75, 30, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout sortLayout = this.standardLayout.createChildLT(260, 100, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);

  static
  {
    COLLAPSE = new EducationType("COLLAPSE", 1);
    FRONTPAGE = new EducationType("FRONTPAGE", 2);
    FAV = new EducationType("FAV", 3);
    NONE = new EducationType("NONE", 4);
    EducationType[] arrayOfEducationType = new EducationType[5];
    arrayOfEducationType[0] = SORT;
    arrayOfEducationType[1] = COLLAPSE;
    arrayOfEducationType[2] = FRONTPAGE;
    arrayOfEducationType[3] = FAV;
    arrayOfEducationType[4] = NONE;
  }

  public String getLocation()
  {
    switch (1.$SwitchMap$fm$qingting$qtradio$view$education$balloon$EducationType[ordinal()])
    {
    default:
      return null;
    case 1:
      return "mainplayview";
    case 4:
      return "frontpage";
    case 2:
      return "frontpage";
    case 3:
    }
    return "frontpage";
  }

  public IView getView(Context paramContext)
  {
    switch (1.$SwitchMap$fm$qingting$qtradio$view$education$balloon$EducationType[ordinal()])
    {
    default:
      return null;
    case 1:
      return new BalloonFavView(paramContext);
    case 4:
      return new EducationFrontpageView(paramContext);
    case 2:
      return new BalloonCollapseView(paramContext);
    case 3:
    }
    return new BalloonSortView(paramContext);
  }

  public ViewLayout getViewLayout()
  {
    ViewLayout localViewLayout = this.standardLayout;
    switch (1.$SwitchMap$fm$qingting$qtradio$view$education$balloon$EducationType[ordinal()])
    {
    default:
      return localViewLayout;
    case 1:
      return this.favLayout;
    case 2:
      return this.collapseLayout;
    case 3:
    }
    return this.sortLayout;
  }

  public boolean isTransient()
  {
    int i = 1.$SwitchMap$fm$qingting$qtradio$view$education$balloon$EducationType[ordinal()];
    boolean bool = false;
    switch (i)
    {
    default:
      bool = true;
    case 1:
    case 2:
    case 3:
    }
    return bool;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.education.balloon.EducationType
 * JD-Core Version:    0.6.2
 */