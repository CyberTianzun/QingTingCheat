package fm.qingting.qtradio.view.frontpage.discover;

import fm.qingting.qtradio.model.CategoryNode;
import java.util.Locale;

public class CategoryNameWrapper
{
  private static final int ID_BAOSHI = 604;
  private static final int ID_COMPETATION = 656;
  private static final int ID_CROSSTALK = 209;
  private static final int ID_MOVIE = 569;
  private static final int ID_NOVEL = 208;
  private static final int ID_OPENCOURSE = 74;
  private static final int ID_RADIODRAMA = 206;
  private static final int ID_TALKSHOW = 200;
  private static final String NOVEL_ALL = "分类筛选";
  private static final String NOVEL_RANK = "排行榜";
  private static final String PATTERN_OTHER_ALL = "分类筛选";
  private static final String PATTERN_OTHER_RANK = "排行榜";
  private static final String PATTERN_SPECIAL_ALL = "分类筛选";
  private static final String PATTERN_SPECIAL_RANK = "排行榜";

  public static String wrapNameAll(CategoryNode paramCategoryNode)
  {
    switch (paramCategoryNode.sectionId)
    {
    default:
      Locale localLocale2 = Locale.CHINA;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = paramCategoryNode.name;
      return String.format(localLocale2, "分类筛选", arrayOfObject2);
    case 208:
      return "分类筛选";
    case 74:
    case 200:
    case 206:
    case 209:
    case 569:
    case 604:
      Locale localLocale1 = Locale.CHINA;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = paramCategoryNode.name;
      return String.format(localLocale1, "分类筛选", arrayOfObject1);
    case 656:
    }
    return String.format(Locale.CHINA, "分赛区", new Object[0]);
  }

  public static String wrapNameRank(CategoryNode paramCategoryNode)
  {
    switch (paramCategoryNode.sectionId)
    {
    default:
      Locale localLocale2 = Locale.CHINA;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = paramCategoryNode.name;
      return String.format(localLocale2, "排行榜", arrayOfObject2);
    case 208:
      return "排行榜";
    case 74:
    case 200:
    case 206:
    case 209:
    case 569:
    case 604:
    case 656:
    }
    Locale localLocale1 = Locale.CHINA;
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = paramCategoryNode.name;
    return String.format(localLocale1, "排行榜", arrayOfObject1);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.frontpage.discover.CategoryNameWrapper
 * JD-Core Version:    0.6.2
 */