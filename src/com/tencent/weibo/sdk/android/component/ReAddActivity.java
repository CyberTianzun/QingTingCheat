package com.tencent.weibo.sdk.android.component;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.weibo.sdk.android.api.WeiboAPI;
import com.tencent.weibo.sdk.android.api.adapter.GalleryAdapter;
import com.tencent.weibo.sdk.android.api.util.BackGroudSeletor;
import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.model.AccountModel;
import com.tencent.weibo.sdk.android.model.ImageInfo;
import com.tencent.weibo.sdk.android.model.ModelResult;
import com.tencent.weibo.sdk.android.network.HttpCallback;
import java.util.ArrayList;
import org.json.JSONObject;

public class ReAddActivity extends Activity
{
  private String accessToken;
  private WeiboAPI api;
  private EditText content = null;
  private String contentStr = "";
  private Gallery gallery;
  private RelativeLayout galleryLayout = null;
  private ArrayList<ImageInfo> imageList = new ArrayList();
  private LinearLayout layout = null;
  private PopupWindow loadingWindow = null;
  private HttpCallback mCallBack = new HttpCallback()
  {
    public void onResult(Object paramAnonymousObject)
    {
      ModelResult localModelResult = (ModelResult)paramAnonymousObject;
      if (localModelResult.isExpires())
      {
        Toast.makeText(ReAddActivity.this, localModelResult.getError_message(), 0).show();
        return;
      }
      if (localModelResult.isSuccess())
      {
        Toast.makeText(ReAddActivity.this, "转播成功", 0).show();
        ReAddActivity.this.finish();
        return;
      }
      Toast.makeText(ReAddActivity.this, localModelResult.getError_message(), 0).show();
      ReAddActivity.this.finish();
    }
  };
  private Handler mHandler = null;
  private String musicAuthor = "";
  private String musicPath = "";
  private String musicTitle = "";
  private String picPath = "";
  private ProgressBar progressBar = null;
  private TextView textView_num;
  private HttpCallback videoCallBack = new HttpCallback()
  {
    public void onResult(Object paramAnonymousObject)
    {
      ModelResult localModelResult = (ModelResult)paramAnonymousObject;
      if (localModelResult != null)
        if ((localModelResult.isExpires()) || (!localModelResult.isSuccess()));
      while ((ReAddActivity.this.loadingWindow == null) || (!ReAddActivity.this.loadingWindow.isShowing()))
        try
        {
          JSONObject localJSONObject = ((JSONObject)localModelResult.getObj()).getJSONObject("data");
          ImageInfo localImageInfo = new ImageInfo();
          localImageInfo.setImagePath(localJSONObject.getString("minipic"));
          localImageInfo.setImageName(localJSONObject.getString("title"));
          localImageInfo.setPlayPath(localJSONObject.getString("real"));
          ReAddActivity.this.imageList.add(localImageInfo);
          GalleryAdapter localGalleryAdapter = new GalleryAdapter(ReAddActivity.this.getApplicationContext(), ReAddActivity.this.loadingWindow, ReAddActivity.this.imageList);
          ReAddActivity.this.gallery.setAdapter(localGalleryAdapter);
          return;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          return;
        }
      ReAddActivity.this.loadingWindow.dismiss();
    }
  };
  private String videoPath = "";

  public View initLayout()
  {
    RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(-1, -1);
    RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(-1, -2);
    RelativeLayout.LayoutParams localLayoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
    LinearLayout localLinearLayout = new LinearLayout(this);
    this.layout = localLinearLayout;
    this.layout.setLayoutParams(localLayoutParams1);
    this.layout.setOrientation(1);
    this.layout.setBackgroundDrawable(BackGroudSeletor.getdrawble("readd_bg", getApplication()));
    RelativeLayout localRelativeLayout1 = new RelativeLayout(this);
    localRelativeLayout1.setLayoutParams(localLayoutParams2);
    localRelativeLayout1.setBackgroundDrawable(BackGroudSeletor.getdrawble("up_bg2x", getApplication()));
    localRelativeLayout1.setGravity(0);
    Button localButton1 = new Button(this);
    localButton1.setBackgroundDrawable(BackGroudSeletor.createBgByImageIds(new String[] { "quxiao_btn2x", "quxiao_btn_hover" }, getApplication()));
    localButton1.setText("取消");
    localLayoutParams3.addRule(9, -1);
    localLayoutParams3.addRule(15, -1);
    localLayoutParams3.topMargin = 10;
    localLayoutParams3.leftMargin = 10;
    localLayoutParams3.bottomMargin = 10;
    localButton1.setLayoutParams(localLayoutParams3);
    View.OnClickListener local3 = new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ReAddActivity.this.finish();
      }
    };
    localButton1.setOnClickListener(local3);
    localRelativeLayout1.addView(localButton1);
    TextView localTextView1 = new TextView(this);
    localTextView1.setText("转播");
    localTextView1.setTextColor(-1);
    localTextView1.setTextSize(24.0F);
    RelativeLayout.LayoutParams localLayoutParams4 = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams4.addRule(13, -1);
    localTextView1.setLayoutParams(localLayoutParams4);
    localRelativeLayout1.addView(localTextView1);
    Button localButton2 = new Button(this);
    localButton2.setBackgroundDrawable(BackGroudSeletor.createBgByImageIds(new String[] { "sent_btn2x", "sent_btn_hover" }, getApplication()));
    localButton2.setText("转播");
    RelativeLayout.LayoutParams localLayoutParams5 = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams5.addRule(11, -1);
    localLayoutParams5.addRule(15, -1);
    localLayoutParams5.topMargin = 10;
    localLayoutParams5.rightMargin = 10;
    localLayoutParams5.bottomMargin = 10;
    localButton2.setLayoutParams(localLayoutParams5);
    View.OnClickListener local4 = new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ReAddActivity.this.reAddWeibo();
      }
    };
    localButton2.setOnClickListener(local4);
    localRelativeLayout1.addView(localButton2);
    RelativeLayout localRelativeLayout2 = new RelativeLayout(this);
    localRelativeLayout2.setLayoutParams(new RelativeLayout.LayoutParams(-1, 240));
    RelativeLayout localRelativeLayout3 = new RelativeLayout(this);
    RelativeLayout.LayoutParams localLayoutParams6 = new RelativeLayout.LayoutParams(440, -1);
    localLayoutParams6.addRule(13);
    localLayoutParams6.topMargin = 50;
    localRelativeLayout3.setLayoutParams(localLayoutParams6);
    localRelativeLayout3.setBackgroundDrawable(BackGroudSeletor.getdrawble("input_bg", getApplication()));
    TextView localTextView2 = new TextView(this);
    this.textView_num = localTextView2;
    TextView localTextView3 = this.textView_num;
    if (this.contentStr == null);
    for (String str = "140"; ; str = String.valueOf(140 - this.contentStr.length()))
    {
      localTextView3.setText(str);
      this.textView_num.setTextColor(Color.parseColor("#999999"));
      this.textView_num.setGravity(5);
      this.textView_num.setTextSize(18.0F);
      RelativeLayout.LayoutParams localLayoutParams7 = new RelativeLayout.LayoutParams(-2, -2);
      localLayoutParams7.addRule(12, -1);
      localLayoutParams7.addRule(11, -1);
      localLayoutParams7.rightMargin = 10;
      this.textView_num.setLayoutParams(localLayoutParams7);
      localRelativeLayout3.addView(this.textView_num);
      EditText localEditText1 = new EditText(this);
      this.content = localEditText1;
      RelativeLayout.LayoutParams localLayoutParams8 = new RelativeLayout.LayoutParams(-1, -2);
      localLayoutParams8.addRule(14);
      localLayoutParams8.addRule(10);
      this.content.setLayoutParams(localLayoutParams8);
      this.content.setMaxLines(4);
      this.content.setMinLines(4);
      this.content.setScrollbarFadingEnabled(true);
      this.content.setGravity(48);
      this.content.setMovementMethod(ScrollingMovementMethod.getInstance());
      this.content.setText(this.contentStr);
      this.content.setSelection(this.contentStr.length());
      this.content.setBackgroundDrawable(null);
      EditText localEditText2 = this.content;
      TextWatcher local5 = new TextWatcher()
      {
        private int selectionEnd;
        private int selectionStart;
        private CharSequence temp;

        public void afterTextChanged(Editable paramAnonymousEditable)
        {
          this.selectionStart = ReAddActivity.this.content.getSelectionStart();
          this.selectionEnd = ReAddActivity.this.content.getSelectionEnd();
          if (this.temp.length() > 140)
          {
            Toast.makeText(ReAddActivity.this, "最多可输入140字符", 0).show();
            paramAnonymousEditable.delete(-1 + this.selectionStart, this.selectionEnd);
            int i = this.selectionStart;
            ReAddActivity.this.content.setText(paramAnonymousEditable);
            ReAddActivity.this.content.setSelection(i);
            return;
          }
          ReAddActivity.this.textView_num.setText(String.valueOf(140 - paramAnonymousEditable.length()));
        }

        public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
        {
          this.temp = paramAnonymousCharSequence;
        }

        public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
        {
        }
      };
      localEditText2.addTextChangedListener(local5);
      localRelativeLayout3.addView(this.content);
      localRelativeLayout2.addView(localRelativeLayout3);
      RelativeLayout localRelativeLayout4 = new RelativeLayout(this);
      this.galleryLayout = localRelativeLayout4;
      this.galleryLayout.setLayoutParams(localLayoutParams1);
      Gallery localGallery = new Gallery(this);
      this.gallery = localGallery;
      RelativeLayout.LayoutParams localLayoutParams9 = new RelativeLayout.LayoutParams(303, 203);
      localLayoutParams9.addRule(14, -1);
      localLayoutParams9.addRule(10, -1);
      localLayoutParams9.topMargin = 50;
      this.gallery.setLayoutParams(localLayoutParams9);
      this.gallery.setBackgroundDrawable(BackGroudSeletor.getdrawble("pic_biankuang2x", getApplication()));
      requestForGallery();
      this.galleryLayout.addView(this.gallery);
      this.layout.addView(localRelativeLayout1);
      this.layout.addView(localRelativeLayout2);
      if ((this.picPath != null) && (!"".equals(this.picPath)) && (this.videoPath != null) && (!"".equals(this.videoPath)))
        this.layout.addView(this.galleryLayout);
      return this.layout;
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    getWindow().setFlags(1024, 1024);
    requestWindowFeature(1);
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
    BackGroudSeletor.setPix(localDisplayMetrics.widthPixels + "x" + localDisplayMetrics.heightPixels);
    this.accessToken = Util.getSharePersistent(getApplicationContext(), "ACCESS_TOKEN");
    if ((this.accessToken == null) || ("".equals(this.accessToken)))
    {
      Toast.makeText(this, "请先授权", 0).show();
      finish();
      return;
    }
    Bundle localBundle = getIntent().getExtras();
    if (localBundle != null)
    {
      this.contentStr = localBundle.getString("content");
      this.videoPath = localBundle.getString("video_url");
      this.picPath = localBundle.getString("pic_url");
      this.musicPath = localBundle.getString("music_url");
      this.musicTitle = localBundle.getString("music_title");
      this.musicAuthor = localBundle.getString("music_author");
    }
    this.api = new WeiboAPI(new AccountModel(this.accessToken));
    setContentView(initLayout());
  }

  protected void reAddWeibo()
  {
    this.contentStr = this.content.getText().toString();
    this.api.reAddWeibo(getApplicationContext(), this.contentStr, this.picPath, this.videoPath, this.musicPath, this.musicTitle, this.musicAuthor, this.mCallBack, null, 4);
  }

  public ArrayList<ImageInfo> requestForGallery()
  {
    if (this.picPath != null)
    {
      ImageInfo localImageInfo = new ImageInfo();
      localImageInfo.setImagePath(this.picPath);
      this.imageList.add(localImageInfo);
    }
    if (this.videoPath != null)
    {
      new ImageInfo();
      this.api.getVideoInfo(getApplicationContext(), this.videoPath, this.videoCallBack, null, 4);
    }
    return this.imageList;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.weibo.sdk.android.component.ReAddActivity
 * JD-Core Version:    0.6.2
 */