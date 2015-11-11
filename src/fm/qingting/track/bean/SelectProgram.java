package fm.qingting.track.bean;

public class SelectProgram extends UserAction
{
  public int id;
  public String pname;

  public SelectProgram(int paramInt, String paramString)
  {
    super(0, "select_program");
    this.id = paramInt;
    this.pname = paramString;
  }

  public String toString()
  {
    return addQuotes(this.name) + "," + addQuotes(new StringBuilder().append(this.pname).append("---").append(this.id).toString());
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.track.bean.SelectProgram
 * JD-Core Version:    0.6.2
 */