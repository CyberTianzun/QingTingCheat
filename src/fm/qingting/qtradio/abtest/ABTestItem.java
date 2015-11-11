package fm.qingting.qtradio.abtest;

public class ABTestItem
{
  public String OptionA;
  public String OptionB;
  public String OptionName;
  public GenerateMethod generateMethod = GenerateMethod.Auto;
  public int number;

  public static enum GenerateMethod
  {
    static
    {
      GenerateMethod[] arrayOfGenerateMethod = new GenerateMethod[2];
      arrayOfGenerateMethod[0] = Auto;
      arrayOfGenerateMethod[1] = Manual;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.abtest.ABTestItem
 * JD-Core Version:    0.6.2
 */