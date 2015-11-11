package com.ixintui.plugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public abstract interface IPushActivity
{
  public abstract void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent);

  public abstract void onBackPressed();

  public abstract void onCreate(Activity paramActivity, Bundle paramBundle);

  public abstract void onNewIntent(Intent paramIntent);

  public abstract void onPause();

  public abstract void onPostCreate(Bundle paramBundle);

  public abstract void onPostResume();

  public abstract void onRestart();

  public abstract void onResume();

  public abstract void onStart();

  public abstract void onStop();
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.ixintui.plugin.IPushActivity
 * JD-Core Version:    0.6.2
 */