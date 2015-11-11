package weibo4android;

public enum Gender
{
  static
  {
    FEMALE = new Gender("FEMALE", 1);
    Gender[] arrayOfGender = new Gender[2];
    arrayOfGender[0] = MALE;
    arrayOfGender[1] = FEMALE;
  }

  public static String valueOf(Gender paramGender)
  {
    if (paramGender.ordinal() == 0)
      return "m";
    return "f";
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     weibo4android.Gender
 * JD-Core Version:    0.6.2
 */