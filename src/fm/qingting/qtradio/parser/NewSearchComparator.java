package fm.qingting.qtradio.parser;

import fm.qingting.qtradio.search.SearchItemNode;
import java.util.Comparator;

class NewSearchComparator
  implements Comparator<SearchItemNode>
{
  public int compare(SearchItemNode paramSearchItemNode1, SearchItemNode paramSearchItemNode2)
  {
    if (paramSearchItemNode1.totalScore > paramSearchItemNode2.totalScore)
      return -1;
    if (paramSearchItemNode1.totalScore < paramSearchItemNode2.totalScore)
      return 1;
    return 0;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.parser.NewSearchComparator
 * JD-Core Version:    0.6.2
 */