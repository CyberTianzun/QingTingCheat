package fm.qingting.qtradio.view.search;

class SearchSectionItem
{
  static final int TYPE_CHANNEL = 3;
  static final int TYPE_COUNT = 7;
  static final int TYPE_EPISODE = 4;
  static final int TYPE_MORE = 6;
  static final int TYPE_PODCASTOR = 2;
  static final int TYPE_SECTION = 0;
  static final int TYPE_SHOW = 1;
  static final int TYPE_TAG = 5;
  public Object data;
  public final int type;

  private SearchSectionItem(int paramInt, Object paramObject)
  {
    this.type = paramInt;
    this.data = paramObject;
  }

  protected static SearchSectionItem build(int paramInt, Object paramObject)
  {
    return new SearchSectionItem(paramInt, paramObject);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.search.SearchSectionItem
 * JD-Core Version:    0.6.2
 */