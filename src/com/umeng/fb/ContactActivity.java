package com.umeng.fb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.umeng.fb.model.UserInfo;
import com.umeng.fb.res.AnimMapper;
import com.umeng.fb.res.IdMapper;
import com.umeng.fb.res.LayoutMapper;
import com.umeng.fb.res.StringMapper;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ContactActivity extends Activity
{
  private static final String KEY_UMENG_CONTACT_INFO_PLAIN_TEXT = "plain";
  private FeedbackAgent agent;
  private ImageView backBtn;
  private EditText contactInfoEdit;
  private TextView lastUpdateAtText;
  private ImageView saveBtn;

  @SuppressLint({"NewApi"})
  void back()
  {
    finish();
    if (Build.VERSION.SDK_INT > 4)
      new Object()
      {
        public void overridePendingTransition(Activity paramAnonymousActivity)
        {
          paramAnonymousActivity.overridePendingTransition(AnimMapper.umeng_fb_slide_in_from_left(ContactActivity.this), AnimMapper.umeng_fb_slide_out_from_right(ContactActivity.this));
        }
      }
      .overridePendingTransition(this);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(LayoutMapper.umeng_fb_activity_contact(this));
    this.agent = new FeedbackAgent(this);
    this.backBtn = ((ImageView)findViewById(IdMapper.umeng_fb_back(this)));
    this.saveBtn = ((ImageView)findViewById(IdMapper.umeng_fb_save(this)));
    this.contactInfoEdit = ((EditText)findViewById(IdMapper.umeng_fb_contact_info(this)));
    this.lastUpdateAtText = ((TextView)findViewById(IdMapper.umeng_fb_contact_update_at(this)));
    try
    {
      String str1 = (String)this.agent.getUserInfo().getContact().get("plain");
      this.contactInfoEdit.setText(str1);
      long l = this.agent.getUserInfoLastUpdateAt();
      if (l > 0L)
      {
        Date localDate = new Date(l);
        String str2 = getResources().getString(StringMapper.umeng_fb_contact_update_at(this));
        this.lastUpdateAtText.setText(str2 + SimpleDateFormat.getDateTimeInstance().format(localDate));
        this.lastUpdateAtText.setVisibility(0);
      }
      while (true)
      {
        if (TextUtils.isEmpty(str1))
        {
          this.contactInfoEdit.requestFocus();
          InputMethodManager localInputMethodManager = (InputMethodManager)getSystemService("input_method");
          if (localInputMethodManager != null)
            localInputMethodManager.toggleSoftInput(2, 0);
        }
        this.backBtn.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            ContactActivity.this.back();
          }
        });
        this.saveBtn.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            try
            {
              UserInfo localUserInfo = ContactActivity.this.agent.getUserInfo();
              if (localUserInfo == null)
                localUserInfo = new UserInfo();
              Object localObject = localUserInfo.getContact();
              if (localObject == null)
                localObject = new HashMap();
              ((Map)localObject).put("plain", ContactActivity.this.contactInfoEdit.getEditableText().toString());
              localUserInfo.setContact((Map)localObject);
              ContactActivity.this.agent.setUserInfo(localUserInfo);
              ContactActivity.this.back();
              return;
            }
            catch (Exception localException)
            {
              while (true)
                localException.printStackTrace();
            }
          }
        });
        return;
        this.lastUpdateAtText.setVisibility(8);
      }
    }
    catch (NullPointerException localNullPointerException)
    {
      while (true)
        localNullPointerException.printStackTrace();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.fb.ContactActivity
 * JD-Core Version:    0.6.2
 */