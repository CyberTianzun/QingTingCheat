package com.taobao.newxp.common.a.a;

import android.os.Bundle;

public class d
{
  public String a = "";
  public String b = "";
  public String c = "";
  public String d = "";
  public int e = 255;
  public int f = -1;
  public int g = -1;
  public float h = 0.0F;
  public double i = 0.0D;
  public double j = 0.0D;

  public void a(Bundle paramBundle)
  {
    String str1;
    String str2;
    label37: String str3;
    if (paramBundle != null)
    {
      if (paramBundle.getString("psid") != null)
        break label169;
      str1 = this.a;
      this.a = str1;
      if (paramBundle.getString("tab_sid") != null)
        break label179;
      str2 = this.b;
      this.b = str2;
      if (paramBundle.getString("utdid") != null)
        break label189;
      str3 = this.c;
      label57: this.c = str3;
      if (paramBundle.getString("nick") != null)
        break label200;
    }
    label169: label179: label189: label200: for (String str4 = this.d; ; str4 = paramBundle.getString("nick"))
    {
      this.d = str4;
      this.e = paramBundle.getInt("view_opacity", this.e);
      this.f = paramBundle.getInt("view_width", this.f);
      this.g = paramBundle.getInt("view_height", this.g);
      this.h = paramBundle.getFloat("view_pos_z", this.h);
      this.i = paramBundle.getDouble("longitude", this.i);
      this.j = paramBundle.getDouble("latitude", this.j);
      return;
      str1 = paramBundle.getString("psid");
      break;
      str2 = paramBundle.getString("tab_sid");
      break label37;
      str3 = paramBundle.getString("utdid");
      break label57;
    }
  }

  public static class a
  {
    public static final String a = "psid";
    public static final String b = "tab_sid";
    public static final String c = "view_opacity";
    public static final String d = "view_pos_z";
    public static final String e = "view_width";
    public static final String f = "view_height";
    public static final String g = "longitude";
    public static final String h = "latitude";
    public static final String i = "utdid";
    public static final String j = "nick";
  }

  private static class b
  {
    static final String a = "";
    static final int b = 255;
    static final int c = -1;
    static final int d;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.common.a.a.d
 * JD-Core Version:    0.6.2
 */