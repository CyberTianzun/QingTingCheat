package com.tencent.weibo.sdk.android.component;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class GeneralDataShowActivity extends Activity
{
  private TextView tv;

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.tv = new TextView(this);
    String str = getIntent().getExtras().getString("data");
    this.tv.setText(str);
    this.tv.setMovementMethod(ScrollingMovementMethod.getInstance());
    setContentView(this.tv);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.weibo.sdk.android.component.GeneralDataShowActivity
 * JD-Core Version:    0.6.2
 */