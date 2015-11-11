package fm.qingting.track.bean;

public class SelectSystemRadio extends UserAction
{
  public String localName;

  public SelectSystemRadio(String paramString)
  {
    super(6, "select_system_radio");
    this.localName = paramString;
  }

  public String toString()
  {
    return addQuotes(this.name) + "," + addQuotes(this.localName);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.track.bean.SelectSystemRadio
 * JD-Core Version:    0.6.2
 */