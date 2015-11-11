package fm.qingting.qtradio.view.personalcenter.woqt;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.TextPaint;
import android.text.method.KeyListener;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import fm.qingting.framework.data.IResultRecvHandler;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.wo.IHttpAsyncTaskListener;
import fm.qingting.qtradio.wo.WoApiRequest;
import fm.qingting.qtradio.wo.WoApiRequest.Product;
import fm.qingting.qtradio.wo.WoApiRequest.TryInfo;
import fm.qingting.qtradio.wo.WoAutoResizeTextView;
import fm.qingting.qtradio.wo.WoInfo;
import fm.qingting.qtradio.wo.WoTextView;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WoQtView extends ViewGroupViewImpl
{
  private static String TAG = "WoQtView";
  private WoAutoResizeTextView activateLinkText;
  private WoAutoResizeTextView activatePlainText;
  private final ViewLayout activateTextLayout = this.middleLayout.createChildLT(250, 40, 20, 5, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout bannerLayout = this.upperLayout.createChildLT(480, 180, 0, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private ImageView bannerView;
  private View bottomBar;
  private final ViewLayout bottomBarLayout = this.bottomLayout.createChildLT(440, 2, 20, 5, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout bottomLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 380, 480, 800, 0, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CH);
  private WoTextView bottomText;
  private final ViewLayout bottomTextLayout = this.bottomLayout.createChildLT(440, 200, 20, 5, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private ScrollView bottomTextScroll;
  private TextView bottomTitle;
  private final ViewLayout bottomTitleLayout = this.bottomLayout.createChildLT(150, 30, 40, 20, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private TextView cautionText;
  private final ViewLayout cautionTextLayout = this.middleLayout.createChildLT(440, 40, 20, 5, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private Button checkButton;
  private final ViewLayout checkButtonLayout = this.middleLayout.createChildLT(120, 50, 40, 8, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private TextView clockText;
  private final Context context;
  private TextView faqText;
  private final ViewLayout faqTextLayout = this.bottomLayout.createChildLT(150, 20, 20, 10, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private ImageView feeIcon;
  private final ViewLayout feeIconLayout = this.middleLayout.createChildLT(50, 50, 40, 8, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private TextView feeText;
  private final ViewLayout feeTextLayout = this.middleLayout.createChildLT(200, 40, 15, 18, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private View middleBar;
  private final ViewLayout middleBarLayout = this.middleLayout.createChildLT(440, 2, 20, 8, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout middleBarV2Layout = this.middleLayout.createChildLT(440, 1, 20, 15, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private View middleBarV2_1;
  private TextView middleHighlightText;
  private final ViewLayout middleHighlightTextLayout = this.middleLayout.createChildLT(440, 75, 20, 5, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout middleInfoTextLayout = this.middleLayout.createChildLT(440, 30, 20, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout middleLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 200, 480, 800, 0, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CH);
  private TextView middleTitle;
  private final ViewLayout middleTitleLayout = this.middleLayout.createChildLT(150, 30, 40, 8, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout modifyPhoneButtonLayout = this.middleLayout.createChildLT(120, 60, 20, 10, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private String phoneNumber;
  private EditText phoneNumberText;
  private final ViewLayout phoneNumberTextLayout = this.middleLayout.createChildLT(440, 50, 20, 10, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private RecvHandler recvHandler;
  private Button sendVerifyCodeButton;
  private final ViewLayout sendVerifyCodeButtonLayout = this.middleLayout.createChildLT(180, 50, 20, 10, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 800, 480, 800, 0, 0, ViewLayout.FILL);
  private Button subButton;
  private final ViewLayout subButtonLayout = this.middleLayout.createChildLT(440, 50, 20, 8, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private ImageView succIcon;
  private final ViewLayout succIconLayout = this.upperLayout.createChildLT(100, 100, 0, 20, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private TextView succText;
  private final ViewLayout succTextLayout = this.upperLayout.createChildLT(400, 40, 0, 15, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private TelephonyManager tm;
  private TextView topBindText;
  private final ViewLayout topBindTextLayout = this.topFeeTextLayout;
  private TextView topFeeText;
  private final ViewLayout topFeeTextLayout = this.middleLayout.createChildLT(440, 28, 20, 10, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private Button unsubButton;
  private final ViewLayout unsubButtonbLayout = this.checkButtonLayout;
  private final ViewLayout upperLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 180, 480, 800, 0, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CH);
  private USER_STATE userState = null;
  private EditText verfCodeText;
  private final ViewLayout verfCodeTextLayout = this.middleLayout.createChildLT(250, 50, 20, 10, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private VIEW_STATE viewState = null;
  private WoAsyncTaskHandler woAsyncTaskHandler;
  private WoQtView woQtView = this;

  public WoQtView(Context paramContext)
  {
    super(paramContext);
    this.context = paramContext;
    this.woAsyncTaskHandler = new WoAsyncTaskHandler();
    this.viewState = VIEW_STATE.HOME_PAGE;
    if (WoApiRequest.hasInited())
      if (WoApiRequest.hasBound())
        if (WoApiRequest.hasSubedProductByQt())
          if (WoApiRequest.productHasCanceled("0000001010"))
            this.userState = USER_STATE.BOUND_SUBED_HAVE_CANCELED;
    while (true)
    {
      if ((this.userState == USER_STATE.NOT_BOUND) || (this.userState == USER_STATE.BOUNDED_UNSUBED))
      {
        WoApiRequest.disableWoProxy();
        label934: this.recvHandler = new RecvHandler();
        setBackgroundColor(-1);
        this.bannerView = new ImageView(paramContext);
      }
      try
      {
        this.bannerView.setImageResource(2130838049);
        this.bannerView.setScaleType(ImageView.ScaleType.FIT_XY);
        addView(this.bannerView);
        this.clockText = new TextView(paramContext);
        this.clockText.setText("免费取消剩余：\n");
        addView(this.clockText);
        this.middleTitle = new TextView(paramContext);
        this.middleTitle.setGravity(17);
        this.middleTitle.setText("服务资费");
        addView(this.middleTitle);
        this.middleBar = new View(paramContext);
        this.middleBar.setBackgroundColor(-65536);
        addView(this.middleBar);
        this.feeIcon = new ImageView(paramContext);
      }
      catch (OutOfMemoryError localOutOfMemoryError2)
      {
        try
        {
          this.feeIcon.setImageResource(2130838051);
          this.feeIcon.setScaleType(ImageView.ScaleType.FIT_XY);
          addView(this.feeIcon);
          this.feeText = new TextView(paramContext);
          this.feeText.setGravity(3);
          this.feeText.setText("10元/月");
          addView(this.feeText);
          this.checkButton = new Button(paramContext);
          if (this.userState == USER_STATE.NOT_BOUND)
          {
            this.checkButton.setText("开通");
            addView(this.checkButton);
            this.checkButton.setOnClickListener(new View.OnClickListener()
            {
              public void onClick(View paramAnonymousView)
              {
                if (WoQtView.this.userState == WoQtView.USER_STATE.BOUND_SUBED_HAVE_CANCELED)
                {
                  WoApiRequest.sendEventMessage("unicomClickReopen");
                  WoQtView.access$102(WoQtView.this, WoQtView.VIEW_STATE.PHONE_NUMBER);
                  String str = WoApiRequest.getBindCallNumber();
                  if ((str == null) || (str.equals("")))
                  {
                    str = WoApiRequest.getCacheCallNumber();
                    if ((str == null) || (str.equals("")))
                      str = WoApiRequest.getSimcardCallNumber();
                  }
                  if (!WoQtView.isMobilePhoneNumber(str))
                    break label132;
                  WoQtView.this.phoneNumberText.setText(str);
                }
                while (true)
                {
                  WoQtView.this.verfCodeText.setText("");
                  WoQtView.this.subButton.setText("立即开通");
                  WoQtView.this.requestLayout();
                  WoQtView.this.invalidate();
                  return;
                  WoApiRequest.sendEventMessage("unicomClickOpen");
                  break;
                  label132: WoQtView.this.phoneNumberText.setText("");
                }
              }
            });
            this.middleBarV2_1 = new View(paramContext);
            this.middleBarV2_1.setBackgroundColor(-2039584);
            addView(this.middleBarV2_1);
            this.activatePlainText = new WoAutoResizeTextView(paramContext);
            if ((this.userState != USER_STATE.BOUND_SUBED) && (this.userState != USER_STATE.BOUND_SUBED_HAVE_CANCELED))
              break label2313;
            this.activatePlainText.setText("如您需要绑定其他已开通的手机号，");
            addView(this.activatePlainText);
            this.activateLinkText = new WoAutoResizeTextView(paramContext);
            this.activateLinkText.setText("立即绑定>>");
            this.activateLinkText.setPaintFlags(0x8 | this.activateLinkText.getPaintFlags());
            this.activateLinkText.setOnClickListener(new View.OnClickListener()
            {
              public void onClick(View paramAnonymousView)
              {
                WoQtView.access$102(WoQtView.this, WoQtView.VIEW_STATE.PHONE_NUMBER_TO_BIND);
                WoQtView.this.phoneNumberText.setKeyListener((KeyListener)WoQtView.this.phoneNumberText.getTag());
                WoQtView.this.phoneNumberText.setHint("已开通服务的手机号码");
                WoQtView.this.phoneNumberText.setText("");
                WoQtView.this.subButton.setText("立刻绑定");
                WoQtView.this.subButton.setText("立即绑定");
                WoQtView.this.verfCodeText.setText("");
                WoQtView.this.requestLayout();
                WoQtView.this.invalidate();
              }
            });
            addView(this.activateLinkText);
            this.middleHighlightText = new TextView(paramContext);
            this.middleHighlightText.setText("");
            this.middleHighlightText.setTextColor(-12632193);
            addView(this.middleHighlightText);
            this.subButton = new Button(paramContext);
            this.subButton.setText("立即开通");
            this.subButton.setTextColor(-1);
            this.subButton.setBackgroundColor(-65536);
            this.subButton.setOnClickListener(new View.OnClickListener()
            {
              public void onClick(View paramAnonymousView)
              {
                String str1 = WoQtView.this.verfCodeText.getText().toString();
                String str2 = WoQtView.this.phoneNumberText.getText().toString();
                WoQtView.access$602(WoQtView.this.woQtView, str2);
                if (!WoQtView.isMobilePhoneNumber(str2))
                  Toast.makeText(WoQtView.access$500(WoQtView.this).context, "请输入正确的手机号", 1).show();
                label290: label318: 
                do
                {
                  return;
                  if (!WoApiRequest.isChinaUnicomPhoneNumber(str2))
                  {
                    Toast.makeText(WoQtView.access$500(WoQtView.this).context, "号码不在服务范围，请用其他号段的联通号码", 1).show();
                    return;
                  }
                  if ((str2.equals(WoApiRequest.getBindCallNumber())) && (WoQtView.this.viewState == WoQtView.VIEW_STATE.PHONE_NUMBER_TO_BIND))
                  {
                    Toast.makeText(WoQtView.access$500(WoQtView.this).context, "此号码已经绑定，无需重复绑定", 1).show();
                    return;
                  }
                  if (WoQtView.this.viewState == WoQtView.VIEW_STATE.PHONE_NUMBER)
                  {
                    WoQtView.this.subButton.setText("开通中..");
                    Activity localActivity = (Activity)WoQtView.access$500(WoQtView.this).context;
                    WoQtView.this.hideSoftKeyboard(localActivity);
                    WoQtView.this.subButton.setEnabled(false);
                    if (!str1.equals(""))
                      break label318;
                    Toast.makeText(WoQtView.access$500(WoQtView.this).context, "验证码", 1).show();
                    if (WoQtView.this.viewState != WoQtView.VIEW_STATE.PHONE_NUMBER)
                      break label290;
                    WoQtView.this.subButton.setText("立即开通");
                  }
                  while (true)
                  {
                    WoQtView.this.subButton.setEnabled(true);
                    return;
                    if (WoQtView.this.viewState != WoQtView.VIEW_STATE.PHONE_NUMBER_TO_BIND)
                      break;
                    WoQtView.this.subButton.setText("绑定中..");
                    break;
                    if (WoQtView.this.viewState == WoQtView.VIEW_STATE.PHONE_NUMBER_TO_BIND)
                      WoQtView.this.subButton.setText("立即绑定");
                  }
                  WoApiRequest.sendEventMessage("unicomClickSubscribe");
                  if (WoQtView.this.viewState == WoQtView.VIEW_STATE.PHONE_NUMBER_TO_BIND)
                  {
                    WoApiRequest.setActionNumber(str2);
                    WoApiRequest.getAuthTokenWithVCode(str2, str1, WoQtView.this.woAsyncTaskHandler, "getAuthTokenWithVCodeToBind");
                    return;
                  }
                }
                while ((WoQtView.this.userState != WoQtView.USER_STATE.NOT_BOUND) && (WoQtView.this.userState != WoQtView.USER_STATE.BOUND_SUBED_HAVE_CANCELED) && (WoQtView.this.userState != WoQtView.USER_STATE.BOUNDED_UNSUBED));
                WoApiRequest.setActionNumber(str2);
                WoApiRequest.getAuthTokenWithVCode(str2, str1, WoQtView.this.woAsyncTaskHandler, "getAuthTokenWithVCodeToSubToBind");
              }
            });
            addView(this.subButton);
            this.unsubButton = new Button(paramContext);
            this.unsubButton.setBackgroundColor(-49345);
            this.unsubButton.setTextColor(-1);
            this.unsubButton.setBackgroundColor(-65536);
            this.unsubButton.setText("立刻退订");
            addView(this.unsubButton);
            this.unsubButton.setOnClickListener(new View.OnClickListener()
            {
              public void onClick(View paramAnonymousView)
              {
                WoApiRequest.sendEventMessage("unicomClickUnsubscribe");
                WoQtView.this.unsubButton.setText("退订中..");
                WoQtView.this.unsubButton.setEnabled(false);
                if (WoQtView.this.userState == WoQtView.USER_STATE.BOUND_SUBED)
                  WoApiRequest.unSubProductWithToken(WoApiRequest.getLocalToken(), WoQtView.this.woAsyncTaskHandler, "unSubProductWithToken");
              }
            });
            this.bottomTitle = new TextView(paramContext);
            this.bottomTitle.setText("蜻蜓FM业务说明");
            this.bottomTitle.setGravity(3);
            this.bottomTitle.setTextColor(-65536);
            addView(this.bottomTitle);
            this.bottomBar = new View(paramContext);
            this.bottomBar.setBackgroundColor(-65536);
            addView(this.bottomBar);
            this.bottomTextScroll = new ScrollView(paramContext);
            this.bottomTextScroll.setBackgroundColor(-1);
            this.bottomText = new WoTextView(paramContext);
            str = InfoManager.getInstance().getWoQtRuleText();
            if (str != null)
              break label2326;
            str = WoInfo.getDefaultRuleText();
            this.bottomText.setText(str);
            this.bottomText.setTextColor(-16777216);
            addView(this.bottomTextScroll);
            this.bottomTextScroll.addView(this.bottomText);
            this.faqText = new TextView(paramContext);
            this.faqText.setText("常见问题说明 >>");
            this.faqText.setGravity(3);
            this.faqText.setTextColor(-16777216);
            addView(this.faqText);
            this.faqText.setOnClickListener(new View.OnClickListener()
            {
              public void onClick(View paramAnonymousView)
              {
                ControllerManager.getInstance().openWoFaqController();
              }
            });
            this.topFeeText = new TextView(paramContext);
            this.topFeeText.setText(Html.fromHtml("<html><font color=\"#000000\">您正在开通蜻蜓FM-包流量畅听：</font><font color=\"#ff8000\">10元/月</font></html>"));
            addView(this.topFeeText);
            this.topBindText = new TextView(paramContext);
            this.topBindText.setText("绑定已开通包流量服务的手机号码");
            this.topBindText.setTextColor(-16777216);
            addView(this.topBindText);
            this.cautionText = new TextView(paramContext);
            this.cautionText.setText("通过联通检测到您的手机号如下：\n（如未检测到或者不正确，请按修改）");
            this.cautionText.setTextColor(-12632257);
            addView(this.cautionText);
            this.phoneNumberText = new EditText(paramContext);
            this.phoneNumberText.setBackgroundColor(-2105377);
            this.phoneNumberText.setGravity(3);
            this.phoneNumberText.setTextColor(-8421505);
            this.phoneNumberText.setHint("手机号码");
            addView(this.phoneNumberText);
            this.phoneNumberText.setOnClickListener(new View.OnClickListener()
            {
              public void onClick(View paramAnonymousView)
              {
              }
            });
            this.phoneNumberText.setTag(this.phoneNumberText.getKeyListener());
            this.phoneNumberText.setKeyListener(null);
            this.phoneNumberText.setKeyListener((KeyListener)this.phoneNumberText.getTag());
            this.verfCodeText = new EditText(paramContext);
            this.verfCodeText.setBackgroundColor(-2105377);
            this.verfCodeText.setTextColor(-8421505);
            this.verfCodeText.setHint("验证码");
            this.verfCodeText.setText("");
            addView(this.verfCodeText);
            this.sendVerifyCodeButton = new Button(paramContext);
            this.sendVerifyCodeButton.setBackgroundColor(-8421377);
            this.sendVerifyCodeButton.setTextColor(-1);
            this.sendVerifyCodeButton.setText("发送短信验证码");
            addView(this.sendVerifyCodeButton);
            this.sendVerifyCodeButton.setOnClickListener(new View.OnClickListener()
            {
              public void onClick(View paramAnonymousView)
              {
                WoApiRequest.sendEventMessage("unicomClickVerfycode");
                String str;
                try
                {
                  str = WoQtView.this.phoneNumberText.getText().toString();
                  WoQtView.access$602(WoQtView.this.woQtView, str);
                  if (!WoQtView.isMobilePhoneNumber(str))
                  {
                    Toast.makeText(WoQtView.access$500(WoQtView.this).context, "请输入正确的手机号", 1).show();
                    return;
                  }
                  if (!WoApiRequest.isChinaUnicomPhoneNumber(str))
                  {
                    Toast.makeText(WoQtView.access$500(WoQtView.this).context, "号码不在服务范围，请用其他号段的联通号码", 1).show();
                    return;
                  }
                }
                catch (Exception localException)
                {
                  localException.printStackTrace();
                  return;
                }
                WoQtView.this.sendVerifyCodeButton.setText("正在发送..");
                WoQtView.this.sendVerifyCodeButton.setEnabled(false);
                if (WoQtView.this.viewState == WoQtView.VIEW_STATE.PHONE_NUMBER_TO_BIND)
                {
                  WoApiRequest.sendVerifyCodeForPhoneNumber(str, WoQtView.this.woAsyncTaskHandler, "sendVerifyCodeForPhoneNumber");
                  return;
                }
                if (((WoQtView.this.userState == WoQtView.USER_STATE.NOT_BOUND) || (WoQtView.this.userState == WoQtView.USER_STATE.BOUND_SUBED_HAVE_CANCELED) || (WoQtView.this.userState == WoQtView.USER_STATE.BOUNDED_UNSUBED)) && (WoQtView.this.viewState == WoQtView.VIEW_STATE.PHONE_NUMBER))
                  WoApiRequest.sendVerifyCodeForPhoneNumber(str, WoQtView.this.woAsyncTaskHandler, "sendVerifyCodeForPhoneNumber");
              }
            });
            this.succIcon = new ImageView(paramContext);
          }
        }
        catch (OutOfMemoryError localOutOfMemoryError2)
        {
          try
          {
            label2313: label2326: 
            do
              while (true)
              {
                this.succIcon.setImageResource(2130838052);
                this.succIcon.setScaleType(ImageView.ScaleType.FIT_XY);
                addView(this.succIcon);
                this.succText = new TextView(paramContext);
                this.succText.setBackgroundColor(-1);
                this.succText.setGravity(17);
                this.succText.setTextColor(-16711936);
                this.succText.setText("您已成功开通");
                addView(this.succText);
                return;
                this.userState = USER_STATE.BOUND_SUBED;
                break;
                this.userState = USER_STATE.NOT_BOUND;
                break;
                this.userState = USER_STATE.NOT_BOUND;
                break;
                this.userState = USER_STATE.NOT_BOUND;
                break;
                WoApiRequest.enableWoProxy();
                break label934;
                localOutOfMemoryError1 = localOutOfMemoryError1;
                localOutOfMemoryError1.printStackTrace();
                continue;
                localOutOfMemoryError2 = localOutOfMemoryError2;
                localOutOfMemoryError2.printStackTrace();
                continue;
                if (this.userState == USER_STATE.BOUND_SUBED_HAVE_CANCELED)
                {
                  this.checkButton.setText("重新开通");
                }
                else
                {
                  this.checkButton.setText("开通");
                  continue;
                  this.activatePlainText.setText("我已开通了流量包，");
                }
              }
            while (!str.equalsIgnoreCase(""));
            String str = WoInfo.getDefaultRuleText();
          }
          catch (OutOfMemoryError localOutOfMemoryError3)
          {
            while (true)
              localOutOfMemoryError3.printStackTrace();
          }
        }
      }
    }
  }

  public static boolean isMobilePhoneNumber(String paramString)
  {
    if (paramString == null)
      return false;
    return Pattern.compile("^1[0-9]{10}$").matcher(paramString).matches();
  }

  private void resetVisibilities()
  {
    this.bannerView.setVisibility(8);
    this.clockText.setVisibility(8);
    this.middleTitle.setVisibility(8);
    this.middleBar.setVisibility(8);
    this.feeIcon.setVisibility(8);
    this.feeText.setVisibility(8);
    this.checkButton.setVisibility(8);
    this.middleBarV2_1.setVisibility(8);
    this.activatePlainText.setVisibility(8);
    this.activateLinkText.setVisibility(8);
    this.middleHighlightText.setVisibility(8);
    if ((this.viewState != VIEW_STATE.PHONE_NUMBER) && (this.viewState != VIEW_STATE.PHONE_NUMBER_TO_BIND))
    {
      this.topFeeText.setVisibility(8);
      this.topBindText.setVisibility(8);
      this.cautionText.setVisibility(8);
      this.phoneNumberText.setVisibility(8);
      this.verfCodeText.setVisibility(8);
      this.sendVerifyCodeButton.setVisibility(8);
      this.subButton.setVisibility(8);
    }
    this.bottomTitle.setVisibility(8);
    this.bottomBar.setVisibility(8);
    this.bottomTextScroll.setVisibility(8);
    this.faqText.setVisibility(8);
    this.succIcon.setVisibility(8);
    this.succText.setVisibility(8);
    this.unsubButton.setVisibility(8);
  }

  public void hideSoftKeyboard(Activity paramActivity)
  {
    InputMethodManager localInputMethodManager = (InputMethodManager)paramActivity.getSystemService("input_method");
    if (localInputMethodManager != null)
      localInputMethodManager.hideSoftInputFromWindow(paramActivity.getCurrentFocus().getWindowToken(), 0);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    resetVisibilities();
    int m;
    int n;
    if ((this.userState != null) && (this.viewState == VIEW_STATE.HOME_PAGE))
    {
      this.middleTitle.setVisibility(0);
      this.middleBar.setVisibility(0);
      this.feeIcon.setVisibility(0);
      this.feeText.setVisibility(0);
      this.middleBarV2_1.setVisibility(0);
      this.bannerView.setVisibility(0);
      this.clockText.setVisibility(8);
      this.bannerView.layout((this.upperLayout.width - this.bannerLayout.width) / 2, this.bannerLayout.topMargin, (this.upperLayout.width + this.bannerLayout.width) / 2, this.bannerLayout.topMargin + this.bannerLayout.height);
      (this.upperLayout.topMargin + this.upperLayout.height);
      int i15 = this.bannerLayout.topMargin + this.bannerLayout.height;
      this.middleTitle.layout(this.middleTitleLayout.leftMargin, i15 + this.middleTitleLayout.topMargin, this.middleLayout.width, i15 + this.middleTitleLayout.height + this.middleTitleLayout.topMargin);
      int i16 = i15 + this.middleTitleLayout.height + this.middleTitleLayout.topMargin;
      this.middleBar.layout((this.middleLayout.width - this.middleBarLayout.width) / 2, i16 + this.middleBarLayout.topMargin, (this.middleLayout.width + this.middleBarLayout.width) / 2, i16 + this.middleBarLayout.height + this.middleBarLayout.topMargin);
      int i17 = i16 + this.middleBarLayout.height + this.middleBarLayout.topMargin;
      this.feeIcon.layout(this.feeIconLayout.leftMargin, i17 + this.feeIconLayout.topMargin, this.feeIconLayout.leftMargin + this.feeIconLayout.width, i17 + this.feeIconLayout.topMargin + this.feeIconLayout.height);
      int i18 = this.feeIconLayout.leftMargin + this.feeIconLayout.width;
      this.feeText.layout(i18 + this.feeTextLayout.leftMargin, i17 + this.feeTextLayout.topMargin, i18 + this.feeTextLayout.leftMargin + this.feeTextLayout.width, i17 + this.feeTextLayout.topMargin + this.feeTextLayout.height);
      String str2;
      if ((this.userState == USER_STATE.NOT_BOUND) || (this.userState == USER_STATE.BOUNDED_UNSUBED) || (this.userState == USER_STATE.BOUND_SUBED_HAVE_CANCELED))
      {
        this.checkButton.setVisibility(0);
        this.checkButton.layout(this.middleLayout.width - this.checkButtonLayout.leftMargin - this.checkButtonLayout.width, i17 + this.checkButtonLayout.topMargin, this.middleLayout.width - this.checkButtonLayout.leftMargin, i17 + this.checkButtonLayout.topMargin + this.checkButtonLayout.height);
        m = i17 + this.checkButtonLayout.topMargin + this.checkButtonLayout.height;
        this.middleBarV2_1.layout(this.middleBarV2Layout.leftMargin, m + this.middleBarLayout.topMargin, this.middleLayout.width - this.middleBarV2Layout.leftMargin, m + this.middleBarLayout.topMargin + this.middleBarLayout.height);
        this.middleHighlightText.setVisibility(0);
        str2 = WoApiRequest.getCacheCallNumber();
        if (this.userState != USER_STATE.BOUND_SUBED)
          break label1292;
        this.middleHighlightText.setText("您正在使用包流量服务 \n开通手机号码：" + str2);
      }
      while (true)
      {
        if ((this.userState == USER_STATE.BOUND_SUBED) || (this.userState == USER_STATE.BOUND_SUBED_HAVE_CANCELED))
        {
          int i19 = m + this.middleBarLayout.topMargin + this.middleBarLayout.height;
          this.middleHighlightText.layout(this.middleHighlightTextLayout.leftMargin, i19 + this.middleHighlightTextLayout.topMargin, this.middleHighlightTextLayout.leftMargin + this.middleHighlightTextLayout.width, i19 + this.middleHighlightTextLayout.topMargin + this.middleHighlightTextLayout.height);
          m = i19 + this.middleHighlightTextLayout.topMargin + this.middleHighlightTextLayout.height;
        }
        if (this.userState == USER_STATE.NOT_BOUND)
        {
          this.activatePlainText.setVisibility(0);
          this.activateLinkText.setVisibility(0);
          int i20 = m + this.activateTextLayout.topMargin;
          this.activatePlainText.layout(this.activateTextLayout.leftMargin, i20 + this.activateTextLayout.topMargin, this.activateTextLayout.leftMargin + this.activateTextLayout.width, i20 + this.activateTextLayout.topMargin + this.activateTextLayout.height);
          int i21 = this.activateTextLayout.width * this.activateLinkText.getText().toString().length() / this.activatePlainText.getText().toString().length();
          this.activateLinkText.layout(this.activateTextLayout.leftMargin + this.activateTextLayout.width, i20 + this.activateTextLayout.topMargin, i21 + (this.activateTextLayout.leftMargin + this.activateTextLayout.width), i20 + this.activateTextLayout.topMargin + this.activateTextLayout.height);
          m = i20 + this.activateTextLayout.topMargin + this.activateTextLayout.height;
        }
        n = this.standardLayout.height - this.bottomTextLayout.height - this.bottomTextLayout.topMargin - this.bottomTitleLayout.height - this.bottomTitleLayout.topMargin - this.bottomBarLayout.height - this.bottomBarLayout.topMargin - this.faqTextLayout.height - 2 * this.faqTextLayout.topMargin;
        if (((this.viewState != VIEW_STATE.PHONE_NUMBER) && (this.viewState != VIEW_STATE.PHONE_NUMBER_TO_BIND) && (this.viewState != VIEW_STATE.TRY_SUCCESS)) || (m < n))
          break label3346;
        this.bottomTitle.setVisibility(8);
        this.bottomBar.setVisibility(8);
        this.bottomTextScroll.setVisibility(8);
        this.faqText.setVisibility(8);
        return;
        if (this.userState != USER_STATE.BOUND_SUBED)
          break;
        this.unsubButton.setVisibility(0);
        this.unsubButton.layout(this.middleLayout.width - this.unsubButtonbLayout.leftMargin - this.unsubButtonbLayout.width, i17 + this.unsubButtonbLayout.topMargin, this.middleLayout.width - this.unsubButtonbLayout.leftMargin, i17 + this.unsubButtonbLayout.topMargin + this.unsubButtonbLayout.height);
        break;
        label1292: if (this.userState == USER_STATE.BOUND_SUBED_HAVE_CANCELED)
          this.middleHighlightText.setText("您正在使用包流量服务 \n开通手机号码：" + str2 + "\n（该号码当前已经退订，可包流量至本月底。）");
        else
          this.middleHighlightText.setVisibility(8);
      }
    }
    int i;
    if ((this.viewState == VIEW_STATE.PHONE_NUMBER) || (this.viewState == VIEW_STATE.PHONE_NUMBER_TO_BIND))
    {
      this.phoneNumberText.setVisibility(0);
      this.subButton.setVisibility(0);
      if (this.viewState == VIEW_STATE.PHONE_NUMBER)
      {
        this.topFeeText.setVisibility(0);
        this.cautionText.setVisibility(0);
        this.topFeeText.layout(this.topFeeTextLayout.leftMargin, this.topFeeTextLayout.topMargin, this.middleLayout.width - this.topFeeTextLayout.leftMargin, 0 + this.topFeeTextLayout.topMargin + this.topFeeTextLayout.height);
        int i4 = 0 + this.topFeeTextLayout.topMargin + this.topFeeTextLayout.height;
        this.cautionText.layout(this.cautionTextLayout.leftMargin, i4 + this.cautionTextLayout.topMargin, this.middleLayout.width - this.cautionTextLayout.leftMargin, i4 + this.cautionTextLayout.topMargin + this.cautionTextLayout.height);
        i = i4 + this.cautionTextLayout.topMargin + this.cautionTextLayout.height;
      }
    }
    while (true)
    {
      label1562: if (this.viewState == VIEW_STATE.PHONE_NUMBER)
        this.phoneNumberText.layout(this.phoneNumberTextLayout.leftMargin, i + this.phoneNumberTextLayout.topMargin, this.phoneNumberTextLayout.leftMargin + this.phoneNumberTextLayout.width, i + this.phoneNumberTextLayout.topMargin + this.phoneNumberTextLayout.height);
      while (true)
      {
        int j = i + this.phoneNumberTextLayout.topMargin + this.phoneNumberTextLayout.height;
        this.verfCodeText.setVisibility(0);
        this.sendVerifyCodeButton.setVisibility(0);
        this.verfCodeText.layout(this.verfCodeTextLayout.leftMargin, j + this.verfCodeTextLayout.topMargin, this.verfCodeTextLayout.leftMargin + this.verfCodeTextLayout.width, j + this.verfCodeTextLayout.topMargin + this.verfCodeTextLayout.height);
        this.sendVerifyCodeButton.layout(this.middleLayout.width - this.sendVerifyCodeButtonLayout.leftMargin - this.sendVerifyCodeButtonLayout.width, j + this.sendVerifyCodeButtonLayout.topMargin, this.middleLayout.width - this.sendVerifyCodeButtonLayout.leftMargin, j + this.sendVerifyCodeButtonLayout.topMargin + this.sendVerifyCodeButtonLayout.height);
        int k = j + this.sendVerifyCodeButtonLayout.topMargin + this.sendVerifyCodeButtonLayout.height;
        this.subButton.layout((this.middleLayout.width - this.subButtonLayout.width) / 2, k + this.subButtonLayout.topMargin, (this.middleLayout.width + this.subButtonLayout.width) / 2, k + this.subButtonLayout.topMargin + this.subButtonLayout.height);
        m = k + this.subButtonLayout.topMargin + this.subButtonLayout.height;
        break;
        if (this.viewState != VIEW_STATE.PHONE_NUMBER_TO_BIND)
          break label3673;
        this.topBindText.setVisibility(0);
        this.topBindText.layout(this.topBindTextLayout.leftMargin, this.topBindTextLayout.topMargin, this.middleLayout.width - this.topBindTextLayout.leftMargin, 0 + this.topBindTextLayout.topMargin + this.topBindTextLayout.height);
        i = 0 + this.topBindTextLayout.topMargin + this.topBindTextLayout.height;
        break label1562;
        if (this.viewState == VIEW_STATE.PHONE_NUMBER_TO_BIND)
          this.phoneNumberText.layout(this.phoneNumberTextLayout.leftMargin, i + this.phoneNumberTextLayout.topMargin, this.middleLayout.width - this.phoneNumberTextLayout.leftMargin, i + this.phoneNumberTextLayout.topMargin + this.phoneNumberTextLayout.height);
      }
      if ((this.viewState == VIEW_STATE.TRY_SUCCESS) || (this.viewState == VIEW_STATE.SUB_SUCCESS) || (this.viewState == VIEW_STATE.BOUND_SUCCESS))
      {
        this.succIcon.setVisibility(0);
        this.succText.setVisibility(0);
        this.middleTitle.setVisibility(0);
        this.middleBar.setVisibility(0);
        this.middleBarV2_1.setVisibility(0);
        this.feeIcon.setVisibility(0);
        this.feeText.setVisibility(0);
        label2174: int i8;
        label2734: int i11;
        String str1;
        if (this.userState == USER_STATE.BOUND_SUBED)
        {
          this.unsubButton.setVisibility(0);
          this.succIcon.layout((this.upperLayout.width - this.succIconLayout.width) / 2, this.succIconLayout.topMargin, (this.upperLayout.width + this.succIconLayout.width) / 2, this.succIconLayout.topMargin + this.succIconLayout.height);
          int i5 = this.succIconLayout.topMargin + this.succIconLayout.height;
          this.succText.layout((this.upperLayout.width - this.succTextLayout.width) / 2, i5 + this.succTextLayout.topMargin, (this.upperLayout.width + this.succTextLayout.width) / 2, i5 + this.succTextLayout.topMargin + this.succTextLayout.height);
          int i6 = this.succIconLayout.topMargin + this.succIconLayout.height + this.succTextLayout.topMargin + this.succTextLayout.height;
          this.middleTitle.layout(this.middleTitleLayout.leftMargin, i6 + this.middleTitleLayout.topMargin, this.middleLayout.width, i6 + this.middleTitleLayout.height + this.middleTitleLayout.topMargin);
          int i7 = i6 + this.middleTitleLayout.height + this.middleTitleLayout.topMargin;
          this.middleBar.layout((this.middleLayout.width - this.middleBarLayout.width) / 2, i7 + this.middleBarLayout.topMargin, (this.middleLayout.width + this.middleBarLayout.width) / 2, i7 + this.middleBarLayout.height + this.middleBarLayout.topMargin);
          i8 = i7 + this.middleBarLayout.height + this.middleBarLayout.topMargin;
          this.feeIcon.layout(this.feeIconLayout.leftMargin, i8 + this.feeIconLayout.topMargin, this.feeIconLayout.leftMargin + this.feeIconLayout.width, i8 + this.feeIconLayout.topMargin + this.feeIconLayout.height);
          int i9 = this.feeIconLayout.leftMargin + this.feeIconLayout.width;
          this.feeText.layout(i9 + this.feeTextLayout.leftMargin, i8 + this.feeTextLayout.topMargin, i9 + this.feeTextLayout.leftMargin + this.feeTextLayout.width, i8 + this.feeTextLayout.topMargin + this.feeTextLayout.height);
          if (this.userState != USER_STATE.BOUND_SUBED)
            break label3211;
          this.unsubButton.layout(this.middleLayout.width - this.unsubButtonbLayout.leftMargin - this.unsubButtonbLayout.width, i8 + this.unsubButtonbLayout.topMargin, this.middleLayout.width - this.unsubButtonbLayout.leftMargin, i8 + this.unsubButtonbLayout.topMargin + this.unsubButtonbLayout.height);
          int i10 = i8 + (this.unsubButtonbLayout.topMargin + this.unsubButtonbLayout.height);
          this.middleBarV2_1.layout(this.middleBarV2Layout.leftMargin, i10 + this.middleBarLayout.topMargin, this.middleLayout.width - this.middleBarV2Layout.leftMargin, i10 + this.middleBarLayout.topMargin + this.middleBarLayout.height);
          i11 = i10 + (this.middleBarLayout.topMargin + this.middleBarLayout.height);
          this.middleHighlightText.setVisibility(0);
          str1 = WoApiRequest.getCacheCallNumber();
          if (this.userState != USER_STATE.BOUND_SUBED)
            break label3287;
          this.middleHighlightText.setText("您正在使用包流量服务 \n开通手机号码：" + str1);
        }
        while (true)
        {
          if ((this.userState == USER_STATE.BOUND_SUBED) || (this.userState == USER_STATE.BOUND_SUBED_HAVE_CANCELED))
          {
            int i12 = i11 + this.middleBarLayout.topMargin + this.middleBarLayout.height;
            this.middleHighlightText.layout(this.middleHighlightTextLayout.leftMargin, i12 + this.middleHighlightTextLayout.topMargin, this.middleHighlightTextLayout.leftMargin + this.middleHighlightTextLayout.width, i12 + this.middleHighlightTextLayout.topMargin + this.middleHighlightTextLayout.height);
            i11 = i12 + this.middleHighlightTextLayout.topMargin + this.middleHighlightTextLayout.height;
          }
          int i13 = i11 + this.activateTextLayout.topMargin;
          this.activatePlainText.layout(this.activateTextLayout.leftMargin, i13 + this.activateTextLayout.topMargin, this.activateTextLayout.leftMargin + this.activateTextLayout.width, i13 + this.activateTextLayout.topMargin + this.activateTextLayout.height);
          int i14 = this.activateTextLayout.width * this.activateLinkText.getText().toString().length() / this.activatePlainText.getText().toString().length();
          this.activateLinkText.layout(this.activateTextLayout.leftMargin + this.activateTextLayout.width, i13 + this.activateTextLayout.topMargin, i14 + (this.activateTextLayout.leftMargin + this.activateTextLayout.width), i13 + this.activateTextLayout.topMargin + this.activateTextLayout.height);
          m = i13 + this.activateTextLayout.topMargin + this.activateTextLayout.height;
          break;
          this.checkButton.setVisibility(0);
          break label2174;
          label3211: this.checkButton.layout(this.middleLayout.width - this.checkButtonLayout.leftMargin - this.checkButtonLayout.width, i8 + this.checkButtonLayout.topMargin, this.middleLayout.width - this.checkButtonLayout.leftMargin, i8 + this.checkButtonLayout.topMargin + this.checkButtonLayout.height);
          break label2734;
          label3287: if (this.userState == USER_STATE.BOUND_SUBED_HAVE_CANCELED)
            this.middleHighlightText.setText("您正在使用包流量服务 \n开通手机号码：" + str1 + "\n（该号码已经退订，可享用包流量至本月底）");
          else
            this.middleHighlightText.setVisibility(8);
        }
        label3346: this.bottomTitle.setVisibility(0);
        this.bottomBar.setVisibility(0);
        this.bottomTextScroll.setVisibility(0);
        this.faqText.setVisibility(0);
        this.bottomTitle.layout(this.bottomTitleLayout.leftMargin, n + this.bottomTitleLayout.topMargin, this.bottomLayout.width - this.bottomTextLayout.leftMargin, n + this.bottomTitleLayout.topMargin + this.bottomTitleLayout.height);
        int i1 = n + this.bottomTitleLayout.topMargin + this.bottomTitleLayout.height;
        this.bottomBar.layout(this.bottomBarLayout.leftMargin, i1 + this.bottomBarLayout.topMargin, this.bottomLayout.width - this.bottomBarLayout.leftMargin, i1 + this.bottomBarLayout.topMargin + this.bottomBarLayout.height);
        int i2 = i1 + this.bottomBarLayout.topMargin + this.bottomBarLayout.height;
        this.bottomTextScroll.layout(this.bottomTextLayout.leftMargin, i2 + this.bottomTextLayout.topMargin, this.bottomLayout.width - this.bottomTextLayout.leftMargin, i2 + this.bottomTextLayout.topMargin + this.bottomTextLayout.height);
        int i3 = i2 + this.bottomTextLayout.topMargin + this.bottomTextLayout.height;
        this.faqText.layout(this.faqTextLayout.leftMargin, i3 + this.faqTextLayout.topMargin, this.faqTextLayout.leftMargin + this.faqTextLayout.width, i3 + this.faqTextLayout.topMargin + this.faqTextLayout.height);
        return;
      }
      m = 0;
      break;
      label3673: i = 0;
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.standardLayout.scaleToBounds(i, j);
    this.upperLayout.scaleToBounds(this.standardLayout);
    this.upperLayout.width = this.standardLayout.width;
    this.bannerLayout.scaleToBounds(this.upperLayout);
    this.bannerLayout.measureView(this.bannerView);
    this.middleLayout.scaleToBounds(this.standardLayout);
    this.middleLayout.width = this.standardLayout.width;
    this.middleTitleLayout.scaleToBounds(this.middleLayout);
    this.middleTitleLayout.measureView(this.middleTitle);
    this.middleTitle.setTextSize(0, 0.8F * this.middleTitleLayout.height);
    this.middleTitle.setTextColor(-65536);
    this.middleTitle.setPadding(0, 0, 0, 0);
    this.middleTitle.setGravity(3);
    this.middleBarLayout.scaleToBounds(this.middleLayout);
    this.middleBarLayout.measureView(this.middleBar);
    this.topFeeTextLayout.scaleToBounds(this.middleLayout);
    this.topFeeTextLayout.measureView(this.topFeeText);
    this.topFeeText.setTextSize(0, 0.75F * this.topFeeTextLayout.height);
    this.topFeeText.setSingleLine();
    this.topBindTextLayout.scaleToBounds(this.middleBarLayout);
    this.topBindTextLayout.measureView(this.topBindText);
    this.topBindText.setTextSize(0, 0.8F * this.topBindTextLayout.height);
    this.topBindText.setSingleLine();
    this.cautionTextLayout.scaleToBounds(this.middleLayout);
    this.cautionTextLayout.measureView(this.cautionText);
    this.cautionText.setTextSize(0, 0.4F * this.cautionTextLayout.height);
    this.cautionText.setPadding(0, 0, 0, 0);
    this.cautionText.setLineSpacing(0.0F, 1.0F);
    this.cautionText.measure(this.cautionTextLayout.getWidthMeasureSpec(), View.MeasureSpec.makeMeasureSpec(this.cautionTextLayout.height, 0));
    this.phoneNumberTextLayout.scaleToBounds(this.middleLayout);
    this.phoneNumberTextLayout.measureView(this.phoneNumberText);
    this.modifyPhoneButtonLayout.scaleToBounds(this.middleLayout);
    this.verfCodeTextLayout.scaleToBounds(this.middleLayout);
    this.verfCodeTextLayout.measureView(this.verfCodeText);
    this.sendVerifyCodeButtonLayout.scaleToBounds(this.middleLayout);
    this.sendVerifyCodeButtonLayout.measureView(this.sendVerifyCodeButton);
    this.subButtonLayout.scaleToBounds(this.middleLayout);
    this.subButtonLayout.measureView(this.subButton);
    this.feeIconLayout.scaleToBounds(this.middleLayout);
    this.feeIconLayout.measureView(this.feeIcon);
    this.feeTextLayout.scaleToBounds(this.middleLayout);
    this.feeTextLayout.measureView(this.feeIcon);
    this.feeText.setTextSize(0, 0.6F * this.feeTextLayout.height);
    this.feeText.setTextColor(-5197648);
    this.checkButtonLayout.scaleToBounds(this.middleLayout);
    this.checkButtonLayout.measureView(this.checkButton);
    this.checkButton.setTextSize(0, 0.5F * this.checkButtonLayout.height);
    this.checkButton.setPadding(0, 0, 0, 0);
    GradientDrawable localGradientDrawable = new GradientDrawable();
    localGradientDrawable.setColor(-16777089);
    localGradientDrawable.setCornerRadius(0.1F * this.checkButtonLayout.height);
    this.checkButton.setBackgroundDrawable(localGradientDrawable);
    this.checkButton.setTextColor(-1);
    this.middleBarV2Layout.scaleToBounds(this.middleLayout);
    this.middleBarV2Layout.measureView(this.middleBarV2_1);
    this.activateTextLayout.scaleToBounds(this.middleLayout);
    this.activateTextLayout.measureView(this.activatePlainText);
    this.activateTextLayout.measureView(this.activateLinkText);
    this.activatePlainText.setTextSize(0, 1.0F * this.activateTextLayout.height);
    this.activateLinkText.setTextSize(0, 1.0F * this.activateTextLayout.height);
    this.activatePlainText.setTextColor(-16777216);
    this.activateLinkText.setTextColor(-32768);
    this.middleHighlightTextLayout.scaleToBounds(this.middleLayout);
    this.middleHighlightText.setTextSize(0, 0.25F * this.middleHighlightTextLayout.height);
    this.middleHighlightText.setPadding(0, 0, 0, 0);
    this.middleHighlightText.setLineSpacing(0.0F, 1.0F);
    this.middleHighlightTextLayout.measureView(this.middleHighlightText);
    this.bottomLayout.scaleToBounds(this.standardLayout);
    this.bottomLayout.width = this.standardLayout.width;
    this.bottomTitleLayout.scaleToBounds(this.bottomLayout);
    this.bottomTextLayout.measureView(this.bottomTitle);
    this.bottomTitle.setTextSize(0, 0.8F * this.bottomTitleLayout.height);
    this.bottomTitle.setPadding(0, 0, 0, 0);
    this.bottomTitle.setGravity(3);
    this.bottomBarLayout.scaleToBounds(this.bottomLayout);
    this.bottomBarLayout.measureView(this.bottomBar);
    this.bottomTextLayout.scaleToBounds(this.bottomLayout);
    this.bottomText.setTextSize(0.6F * this.bottomTitleLayout.height);
    this.bottomTextLayout.measureView(this.bottomTextScroll);
    this.faqTextLayout.scaleToBounds(this.bottomLayout);
    this.faqTextLayout.measureView(this.faqText);
    this.faqText.setTextSize(0, 0.8F * this.faqTextLayout.height);
    this.faqText.setPadding(0, 0, 0, 0);
    this.succIconLayout.scaleToBounds(this.upperLayout);
    this.succIconLayout.measureView(this.succIcon);
    this.succIcon.setPadding(0, 0, 0, 0);
    this.succTextLayout.scaleToBounds(this.upperLayout);
    this.succTextLayout.measureView(this.succText);
    this.succText.setTextSize(0, 0.6F * this.succTextLayout.height);
    this.unsubButtonbLayout.scaleToBounds(this.middleLayout);
    this.unsubButtonbLayout.measureView(this.unsubButton);
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }

  public void showSoftKeyboard(Activity paramActivity)
  {
    InputMethodManager localInputMethodManager = (InputMethodManager)paramActivity.getSystemService("input_method");
    if (localInputMethodManager != null)
      localInputMethodManager.showSoftInput(this.woQtView, 0);
  }

  public class CustomTypefaceSpan extends TypefaceSpan
  {
    private final Typeface newType;

    public CustomTypefaceSpan(String paramTypeface, Typeface arg3)
    {
      super();
      Object localObject;
      this.newType = localObject;
    }

    private void applyCustomTypeFace(Paint paramPaint, Typeface paramTypeface)
    {
      Typeface localTypeface = paramPaint.getTypeface();
      if (localTypeface == null);
      for (int i = 0; ; i = localTypeface.getStyle())
      {
        int j = i & (0xFFFFFFFF ^ paramTypeface.getStyle());
        if ((j & 0x1) != 0)
          paramPaint.setFakeBoldText(true);
        if ((j & 0x2) != 0)
          paramPaint.setTextSkewX(-0.25F);
        paramPaint.setTypeface(paramTypeface);
        return;
      }
    }

    public void updateDrawState(TextPaint paramTextPaint)
    {
      applyCustomTypeFace(paramTextPaint, this.newType);
    }

    public void updateMeasureState(TextPaint paramTextPaint)
    {
      applyCustomTypeFace(paramTextPaint, this.newType);
    }
  }

  class RecvHandler
    implements IResultRecvHandler
  {
    RecvHandler()
    {
    }

    public void onRecvResult(Result paramResult, Object paramObject1, IResultToken paramIResultToken, Object paramObject2)
    {
    }
  }

  private static abstract interface RequestType
  {
    public static final String checkVerifyCodeForPhoneNumberThenTryProduct = "checkVerifyCodeForPhoneNumberThenTryProduct";
    public static final String checkVerifyCodeForPhoneNumberToSub = "checkVerifyCodeForPhoneNumberToSub";
    public static final String getAuthTokenWithVCodeToBind = "getAuthTokenWithVCodeToBind";
    public static final String getAuthTokenWithVCodeToSub = "getAuthTokenWithVCodeToSub";
    public static final String getAuthTokenWithVCodeToSubToBind = "getAuthTokenWithVCodeToSubToBind";
    public static final String qrySubedProductsToUpdateSubInfo = "qrySubedProductsToUpdateSubInfo";
    public static final String qrySubedProductsWithTokenToBind = "qrySubedProductsWithTokenToBind";
    public static final String subProductWithTokenToBind = "subProductWithTokenToBind";
    public static final String updateSubInfoAfterQry = "updateSubInfoAfterQry";
    public static final String updateSubInfoAfterQryAgain = "updateSubInfoAfterQryAgain";
    public static final String updateUserInfoThenReQrySubedProductsToUpdateSubInfo = "updateUserInfoThenReQrySubedProductsToUpdateSubInfo";
    public static final String updateUserInfoThenReQrySubedProductsToUpdateSubInfoAgain = "updateUserInfoThenReQrySubedProductsToUpdateSubInfoAgain";
  }

  static enum USER_STATE
  {
    static
    {
      NEW_USER_NOT_ON_TRIAL = new USER_STATE("NEW_USER_NOT_ON_TRIAL", 1);
      OLD_USER_SUBED = new USER_STATE("OLD_USER_SUBED", 2);
      OLD_USER_NOT_SUBED = new USER_STATE("OLD_USER_NOT_SUBED", 3);
      OLD_USER_SUBED_HAVE_CANCELED = new USER_STATE("OLD_USER_SUBED_HAVE_CANCELED", 4);
      NOT_BOUND = new USER_STATE("NOT_BOUND", 5);
      BOUND_SUBED = new USER_STATE("BOUND_SUBED", 6);
      BOUND_SUBED_HAVE_CANCELED = new USER_STATE("BOUND_SUBED_HAVE_CANCELED", 7);
      BOUNDED_UNSUBED = new USER_STATE("BOUNDED_UNSUBED", 8);
      USER_STATE[] arrayOfUSER_STATE = new USER_STATE[9];
      arrayOfUSER_STATE[0] = NEW_USER_ON_TRIAL;
      arrayOfUSER_STATE[1] = NEW_USER_NOT_ON_TRIAL;
      arrayOfUSER_STATE[2] = OLD_USER_SUBED;
      arrayOfUSER_STATE[3] = OLD_USER_NOT_SUBED;
      arrayOfUSER_STATE[4] = OLD_USER_SUBED_HAVE_CANCELED;
      arrayOfUSER_STATE[5] = NOT_BOUND;
      arrayOfUSER_STATE[6] = BOUND_SUBED;
      arrayOfUSER_STATE[7] = BOUND_SUBED_HAVE_CANCELED;
      arrayOfUSER_STATE[8] = BOUNDED_UNSUBED;
    }
  }

  static enum VIEW_STATE
  {
    static
    {
      TRY_FAILED = new VIEW_STATE("TRY_FAILED", 2);
      CANCEL_TRY_SUCCESS = new VIEW_STATE("CANCEL_TRY_SUCCESS", 3);
      CANCEL_TRY_FAILED = new VIEW_STATE("CANCEL_TRY_FAILED", 4);
      SUB_SUCCESS = new VIEW_STATE("SUB_SUCCESS", 5);
      SUB_FAILED = new VIEW_STATE("SUB_FAILED", 6);
      UNSUB_SUCCESS = new VIEW_STATE("UNSUB_SUCCESS", 7);
      UNSUB_FAILED = new VIEW_STATE("UNSUB_FAILED", 8);
      NOT_SUBED = new VIEW_STATE("NOT_SUBED", 9);
      NEW_USER = new VIEW_STATE("NEW_USER", 10);
      HAVE_SUBED = new VIEW_STATE("HAVE_SUBED", 11);
      PHONE_NUMBER = new VIEW_STATE("PHONE_NUMBER", 12);
      PHONE_NUMBER_TO_BIND = new VIEW_STATE("PHONE_NUMBER_TO_BIND", 13);
      BOUND_SUCCESS = new VIEW_STATE("BOUND_SUCCESS", 14);
      BOUND_FAILED = new VIEW_STATE("BOUND_FAILED", 15);
      TO_BIND = new VIEW_STATE("TO_BIND", 16);
      VIEW_STATE[] arrayOfVIEW_STATE = new VIEW_STATE[17];
      arrayOfVIEW_STATE[0] = HOME_PAGE;
      arrayOfVIEW_STATE[1] = TRY_SUCCESS;
      arrayOfVIEW_STATE[2] = TRY_FAILED;
      arrayOfVIEW_STATE[3] = CANCEL_TRY_SUCCESS;
      arrayOfVIEW_STATE[4] = CANCEL_TRY_FAILED;
      arrayOfVIEW_STATE[5] = SUB_SUCCESS;
      arrayOfVIEW_STATE[6] = SUB_FAILED;
      arrayOfVIEW_STATE[7] = UNSUB_SUCCESS;
      arrayOfVIEW_STATE[8] = UNSUB_FAILED;
      arrayOfVIEW_STATE[9] = NOT_SUBED;
      arrayOfVIEW_STATE[10] = NEW_USER;
      arrayOfVIEW_STATE[11] = HAVE_SUBED;
      arrayOfVIEW_STATE[12] = PHONE_NUMBER;
      arrayOfVIEW_STATE[13] = PHONE_NUMBER_TO_BIND;
      arrayOfVIEW_STATE[14] = BOUND_SUCCESS;
      arrayOfVIEW_STATE[15] = BOUND_FAILED;
      arrayOfVIEW_STATE[16] = TO_BIND;
    }
  }

  public class WoAsyncTaskHandler
    implements IHttpAsyncTaskListener
  {
    public WoAsyncTaskHandler()
    {
    }

    public void onGetResult(Object paramObject1, Object paramObject2)
    {
      JSONObject localJSONObject1;
      String str1;
      label427: label2133: 
      try
      {
        JSONObject localJSONObject11 = WoApiRequest.parseJsonString((String)paramObject2);
        localJSONObject1 = localJSONObject11;
      }
      catch (Exception localException2)
      {
        label982: label1891: 
        try
        {
          String str12 = localJSONObject1.getString("res");
          str1 = str12;
          str2 = (String)paramObject1;
          if ((str2.equals("sendVerifyCodeForSubProduct")) || (str2.equals("sendVerifyCodeForPhoneNumber")))
          {
            WoQtView.this.sendVerifyCodeButton.setText("发送短信验证码");
            WoQtView.this.sendVerifyCodeButton.setEnabled(true);
          }
        }
        catch (Exception localException2)
        {
          String str2;
          label1515: 
          do
            while (true)
            {
              try
              {
                JSONObject localJSONObject2 = WoApiRequest.parseJsonString(str1);
                if (localJSONObject2 != null)
                {
                  String str3 = localJSONObject2.getString("returnCode");
                  if (str3.equals("000000"))
                  {
                    Toast.makeText(WoQtView.access$500(WoQtView.this).context, "验证码已发送，请稍候", 1).show();
                    WoQtView.this.requestLayout();
                    WoQtView.this.invalidate();
                    return;
                    localException1 = localException1;
                    localJSONObject1 = null;
                    continue;
                    localException2 = localException2;
                    str1 = null;
                    continue;
                  }
                  Toast.makeText(WoQtView.access$500(WoQtView.this).context, "验证码发送失败，请重新发送", 1).show();
                  WoApiRequest.log(localJSONObject1, "1", str3, str1);
                  continue;
                }
              }
              catch (JSONException localJSONException1)
              {
                localJSONException1.printStackTrace();
                Toast.makeText(WoQtView.access$500(WoQtView.this).context, "验证码发送失败，请重新发送", 1).show();
                WoApiRequest.log(localJSONObject1, "1", null, str1);
                continue;
                Toast.makeText(WoQtView.access$500(WoQtView.this).context, "验证码发送失败，请重新发送", 1).show();
                WoApiRequest.log(localJSONObject1, "1", null, str1);
                continue;
              }
              if ((str2.equals("getAuthTokenWithVCodeToBind")) || (str2.equals("getAuthTokenWithVCodeToSubToBind")))
              {
                String str4;
                String str5;
                try
                {
                  JSONObject localJSONObject3 = WoApiRequest.parseJsonString(str1);
                  if (localJSONObject3 == null)
                    break label523;
                  str4 = localJSONObject3.getString("returnCode");
                  if (!str4.equals("000000"))
                    break label427;
                  str5 = localJSONObject3.getJSONObject("token").getString("access_token");
                  WoApiRequest.setActionNumberToken(str5);
                  WoApiRequest.updateTokenToServer(WoQtView.access$500(WoQtView.this).phoneNumber, str5, WoQtView.this.woAsyncTaskHandler, "updateToken");
                  if (str2.equals("getAuthTokenWithVCodeToBind"))
                  {
                    WoApiRequest.qrySubedProductsWithToken(str5, WoQtView.this.woAsyncTaskHandler, "qrySubedProductsWithTokenToBind");
                    return;
                  }
                }
                catch (JSONException localJSONException2)
                {
                  localJSONException2.printStackTrace();
                  WoApiRequest.log(localJSONObject1, "1", null, str1);
                  return;
                }
                if (str2.equals("getAuthTokenWithVCodeToSubToBind"))
                {
                  WoApiRequest.subProductWithToken(str5, WoQtView.this.woAsyncTaskHandler, "subProductWithTokenToBind");
                  return;
                  Toast.makeText(WoQtView.access$500(WoQtView.this).context, "验证码过时或者错误！", 1).show();
                  WoQtView.this.subButton.setEnabled(true);
                  if (WoQtView.this.viewState == WoQtView.VIEW_STATE.PHONE_NUMBER)
                    WoQtView.this.subButton.setText("立即开通");
                  while (true)
                  {
                    WoApiRequest.log(localJSONObject1, "1", str4, str1);
                    return;
                    if (WoQtView.this.viewState == WoQtView.VIEW_STATE.PHONE_NUMBER_TO_BIND)
                      WoQtView.this.subButton.setText("立刻绑定");
                  }
                  Toast.makeText(WoQtView.access$500(WoQtView.this).context, "验证码过时或者错误！", 1).show();
                  WoQtView.this.subButton.setEnabled(true);
                  if (WoQtView.this.viewState == WoQtView.VIEW_STATE.PHONE_NUMBER)
                    WoQtView.this.subButton.setText("立即开通");
                  while (true)
                  {
                    WoApiRequest.log(localJSONObject1, "1", null, str1);
                    return;
                    if (WoQtView.this.viewState == WoQtView.VIEW_STATE.PHONE_NUMBER_TO_BIND)
                      WoQtView.this.subButton.setText("立刻绑定");
                  }
                }
              }
              else
              {
                if ((str2.equals("subProductWithVCode")) || (str2.equals("subProductWithTokenToBind")))
                {
                  WoQtView.this.subButton.setText("立即开通");
                  WoQtView.this.subButton.setEnabled(true);
                  try
                  {
                    JSONObject localJSONObject4 = WoApiRequest.parseJsonString(str1);
                    if (localJSONObject4 != null)
                    {
                      str6 = localJSONObject4.getString("returnCode");
                      if (str2.equals("subProductWithTokenToBind"))
                      {
                        if ((!str6.equals("000000")) && (!str6.equals("803009")))
                          break label982;
                        WoApiRequest.addSubedProduct(new WoApiRequest.Product("0000001010"));
                        WoApiRequest.setBindInfo(WoApiRequest.getActionNumber());
                        WoQtView.access$002(WoQtView.this, WoQtView.USER_STATE.BOUND_SUBED);
                        WoQtView.access$102(WoQtView.this, WoQtView.VIEW_STATE.SUB_SUCCESS);
                        WoApiRequest.setCacheCallNumber(WoApiRequest.getActionNumber());
                        WoApiRequest.setLocalToken(WoApiRequest.getActionNumberToken());
                        WoApiRequest.enableWoProxy();
                        if (WoQtView.isMobilePhoneNumber(WoApiRequest.getActionNumber()))
                        {
                          if (!str6.equals("000000"))
                            break label913;
                          Toast.makeText(WoQtView.access$500(WoQtView.this).context, "已成功开通", 1).show();
                          WoApiRequest.sendMsg(WoApiRequest.getActionNumber(), WoInfo.genOldUserSubMsg(), WoQtView.this.woAsyncTaskHandler, "sendMsg");
                        }
                      }
                      while (true)
                      {
                        WoApiRequest.getActionNumberToken();
                        WoApiRequest.Product localProduct1 = new WoApiRequest.Product(new Date(), null);
                        WoApiRequest.updateUserInfoToServer(WoApiRequest.getActionNumber(), localProduct1, WoQtView.this.woAsyncTaskHandler, "updateUserInfoThenReQrySubedProductsToUpdateSubInfo");
                        if (!str2.equals("subProductWithVCode"))
                          break;
                        WoQtView.this.succText.setText("您已成功开通");
                        WoQtView.this.requestLayout();
                        WoQtView.this.invalidate();
                        WoQtView.this.requestLayout();
                        WoQtView.this.invalidate();
                        return;
                        Toast.makeText(WoQtView.access$500(WoQtView.this).context, "该号码已经开通过，直接绑定。", 1).show();
                      }
                    }
                  }
                  catch (JSONException localJSONException3)
                  {
                    while (true)
                    {
                      String str6;
                      localJSONException3.printStackTrace();
                      WoApiRequest.log(localJSONObject1, "1", null, str1);
                      continue;
                      if (str6.equals("803009"))
                      {
                        WoQtView.this.succText.setText("号码已开通过，绑定成功！");
                        continue;
                        Toast.makeText(WoQtView.access$500(WoQtView.this).context, "开通失败，您的手机可能不支持本服务，或者稍后重试。", 1).show();
                        WoQtView.this.subButton.setEnabled(true);
                        if (WoQtView.this.viewState == WoQtView.VIEW_STATE.PHONE_NUMBER)
                          WoQtView.this.subButton.setText("立即开通");
                        while (true)
                        {
                          WoApiRequest.log(localJSONObject1, "1", str6, str1);
                          break;
                          if (WoQtView.this.viewState == WoQtView.VIEW_STATE.PHONE_NUMBER_TO_BIND)
                            WoQtView.this.subButton.setText("立刻绑定");
                        }
                        WoApiRequest.log(localJSONObject1, "1", null, str1);
                      }
                    }
                  }
                }
                if ((str2.equals("qrySubedProductsWithTokenToBind")) || (str2.equals("qrySubedProductsToUpdateSubInfo")))
                {
                  WoQtView.this.subButton.setEnabled(true);
                  if (WoQtView.this.viewState == WoQtView.VIEW_STATE.PHONE_NUMBER)
                    WoQtView.this.subButton.setText("立即开通");
                  String str7;
                  while (true)
                  {
                    try
                    {
                      JSONObject localJSONObject5 = WoApiRequest.parseJsonString(str1);
                      if (localJSONObject5 == null)
                        break label1571;
                      str7 = localJSONObject5.getString("returnCode");
                      if (!str7.equals("000000"))
                        break label1536;
                      JSONArray localJSONArray = localJSONObject5.getJSONArray("subedProducts");
                      ArrayList localArrayList1 = new ArrayList();
                      ArrayList localArrayList2 = new ArrayList();
                      if (localJSONArray != null)
                      {
                        int i = 0;
                        if (i < localJSONArray.length())
                        {
                          WoApiRequest.Product localProduct2 = new WoApiRequest.Product(localJSONArray.getJSONObject(i));
                          localArrayList2.add(localProduct2);
                          localArrayList1.add(localProduct2.productId);
                          i++;
                          continue;
                          if (WoQtView.this.viewState != WoQtView.VIEW_STATE.PHONE_NUMBER_TO_BIND)
                            continue;
                          WoQtView.this.subButton.setText("立刻绑定");
                          continue;
                        }
                      }
                      WoApiRequest.updateSubedProducts(localArrayList2);
                      if (!WoApiRequest.hasSubedProductByQt())
                        break label1515;
                      WoApiRequest.enableWoProxy();
                      if (!str2.equals("qrySubedProductsWithTokenToBind"))
                        break;
                      if (WoApiRequest.productByQtHasCanceled())
                      {
                        WoQtView.access$002(WoQtView.this, WoQtView.USER_STATE.BOUND_SUBED_HAVE_CANCELED);
                        WoQtView.access$102(WoQtView.this, WoQtView.VIEW_STATE.BOUND_SUCCESS);
                        WoQtView.this.checkButton.setText("重新开通");
                        WoApiRequest.enableWoProxy();
                        String str8 = WoApiRequest.getActionNumber();
                        String str9 = WoApiRequest.getActionNumberToken();
                        WoApiRequest.setCacheCallNumber(str8);
                        WoApiRequest.setLocalToken(str9);
                        WoApiRequest.setBindInfo(str8);
                        WoApiRequest.updateUserInfoToServer(str8, WoApiRequest.getSubedProductByQt(), WoQtView.this.woAsyncTaskHandler, "updateUserInfo");
                        WoQtView.this.succText.setText("您已成功绑定！");
                        WoQtView.this.requestLayout();
                        WoQtView.this.invalidate();
                        return;
                      }
                    }
                    catch (JSONException localJSONException4)
                    {
                      localJSONException4.printStackTrace();
                      WoApiRequest.log(localJSONObject1, "1", null, str1);
                      return;
                    }
                    WoQtView.access$002(WoQtView.this, WoQtView.USER_STATE.BOUND_SUBED);
                    WoQtView.access$102(WoQtView.this, WoQtView.VIEW_STATE.BOUND_SUCCESS);
                  }
                  if (str2.equals("qrySubedProductsToUpdateSubInfo"))
                  {
                    WoApiRequest.updateSubInfoToServer(WoApiRequest.getCacheCallNumber(), WoApiRequest.getSubedProduct("0000001010"), WoQtView.this.woAsyncTaskHandler, "updateSubInfoAfterQry");
                    return;
                    Toast.makeText(WoQtView.access$500(WoQtView.this).context, "欲绑定的手机没有订购免包流量服务，请先开通服务。", 1).show();
                    return;
                    if ((str7.equals("40305")) || (str7.equals("40307")))
                    {
                      WoApiRequest.log(localJSONObject1, "1", str7, str1);
                      return;
                      WoApiRequest.log(localJSONObject1, "1", null, str1);
                    }
                  }
                }
                else if (str2.equals("updateSubInfoAfterQry"))
                {
                  try
                  {
                    JSONObject localJSONObject10 = WoApiRequest.parseJsonString(str1);
                    if (localJSONObject10 != null)
                    {
                      int k = localJSONObject10.getInt("errCode");
                      if (k == 0)
                        continue;
                      WoApiRequest.updateSubInfoToServer(WoApiRequest.getCacheCallNumber(), WoApiRequest.getSubedProduct("0000001010"), null, "updateSubInfoAfterQryAgain");
                      WoApiRequest.log(localJSONObject1, "0", String.valueOf(k), str1);
                      return;
                    }
                  }
                  catch (Exception localException3)
                  {
                    WoApiRequest.updateSubInfoToServer(WoApiRequest.getCacheCallNumber(), WoApiRequest.getSubedProduct("0000001010"), null, "updateSubInfoAfterQryAgain");
                    WoApiRequest.log(localJSONObject1, "0", null, str1);
                    return;
                  }
                  WoApiRequest.updateSubInfoToServer(WoApiRequest.getCacheCallNumber(), WoApiRequest.getSubedProduct("0000001010"), null, "updateSubInfoAfterQryAgain");
                  WoApiRequest.log(localJSONObject1, "0", null, str1);
                }
                else if ((str2.equals("updateUserInfo")) || (str2.equals("updateUserInfoThenReQrySubedProductsToUpdateSubInfo")) || (str2.equals("updateUserInfoThenReQrySubedProductsToUpdateSubInfoAgain")))
                {
                  int j;
                  try
                  {
                    JSONObject localJSONObject6 = WoApiRequest.parseJsonString(str1);
                    if (localJSONObject6 == null)
                      break label1891;
                    j = localJSONObject6.getInt("errCode");
                    if (j == 0)
                    {
                      if ((!str2.equals("updateUserInfoThenReQrySubedProductsToUpdateSubInfo")) && (!str2.equals("updateUserInfoThenReQrySubedProductsToUpdateSubInfoAgain")))
                        continue;
                      WoApiRequest.qrySubedProductsWithToken(WoApiRequest.getLocalToken(), WoQtView.this.woAsyncTaskHandler, "qrySubedProductsToUpdateSubInfo");
                      return;
                    }
                  }
                  catch (JSONException localJSONException5)
                  {
                    localJSONException5.printStackTrace();
                    WoApiRequest.log(localJSONObject1, "0", null, str1);
                    return;
                  }
                  WoApiRequest.Product localProduct3 = new WoApiRequest.Product(new Date(), null);
                  if (str2.equals("updateUserInfoThenReQrySubedProductsToUpdateSubInfo"))
                    WoApiRequest.updateUserInfoToServer(WoApiRequest.getCacheCallNumber(), localProduct3, WoQtView.this.woAsyncTaskHandler, "updateUserInfoThenReQrySubedProductsToUpdateSubInfoAgain");
                  WoApiRequest.log(localJSONObject1, "0", String.valueOf(j), str1);
                  return;
                  WoApiRequest.Product localProduct4 = new WoApiRequest.Product(new Date(), null);
                  if (str2.equals("updateUserInfoThenReQrySubedProductsToUpdateSubInfo"))
                    WoApiRequest.updateUserInfoToServer(WoApiRequest.getCacheCallNumber(), localProduct4, WoQtView.this.woAsyncTaskHandler, "updateUserInfoThenReQrySubedProductsToUpdateSubInfoAgain");
                  WoApiRequest.log(localJSONObject1, "0", null, str1);
                }
                else
                {
                  if (str2.equals("tryProduct"))
                  {
                    WoQtView.this.subButton.setText("开通");
                    WoQtView.this.subButton.setEnabled(true);
                    try
                    {
                      JSONObject localJSONObject9 = WoApiRequest.parseJsonString(str1);
                      if (localJSONObject9 != null)
                      {
                        if (localJSONObject9.getInt("errCode") != 0)
                          break label2133;
                        Toast.makeText(WoQtView.access$500(WoQtView.this).context, "已成功订购", 1).show();
                        WoQtView.access$002(WoQtView.this, WoQtView.USER_STATE.NEW_USER_ON_TRIAL);
                        WoQtView.access$102(WoQtView.this, WoQtView.VIEW_STATE.TRY_SUCCESS);
                        WoQtView.this.unsubButton.setText("立刻退订");
                        WoApiRequest.setTryInfo(new WoApiRequest.TryInfo(true));
                        if (WoQtView.isMobilePhoneNumber(WoQtView.access$500(WoQtView.this).phoneNumber))
                          WoApiRequest.sendMsg(WoQtView.access$500(WoQtView.this).phoneNumber, WoInfo.genFirstTryMsg(), WoQtView.this.woAsyncTaskHandler, "sendMsg");
                        WoApiRequest.enableWoProxy();
                      }
                      while (true)
                      {
                        WoQtView.this.requestLayout();
                        WoQtView.this.invalidate();
                        return;
                        Toast.makeText(WoQtView.access$500(WoQtView.this).context, "订购失败，请稍后再试。", 1).show();
                        WoQtView.this.subButton.setText("开通");
                        WoQtView.this.subButton.setEnabled(true);
                      }
                    }
                    catch (JSONException localJSONException8)
                    {
                      while (true)
                        localJSONException8.printStackTrace();
                    }
                  }
                  if (str2.equals("sendMsg"))
                    try
                    {
                      JSONObject localJSONObject8 = WoApiRequest.parseJsonString(str1);
                      if (localJSONObject8 != null)
                      {
                        String str11 = localJSONObject8.getString("returnCode");
                        if (!str11.equals("000000"))
                        {
                          WoApiRequest.log(localJSONObject1, "1", str11, str1);
                          return;
                        }
                      }
                    }
                    catch (JSONException localJSONException7)
                    {
                      localJSONException7.printStackTrace();
                      WoApiRequest.log(localJSONObject1, "1", null, str1);
                      return;
                    }
                }
              }
            }
          while (!str2.equals("unSubProductWithToken"));
        }
      }
      try
      {
        label523: label913: label1571: JSONObject localJSONObject7 = WoApiRequest.parseJsonString(str1);
        label1536: if (localJSONObject7 != null)
        {
          str10 = localJSONObject7.getString("returnCode");
          if (str10.equals("000000"))
          {
            WoQtView.access$002(WoQtView.this, WoQtView.USER_STATE.BOUND_SUBED_HAVE_CANCELED);
            WoQtView.access$102(WoQtView.this, WoQtView.VIEW_STATE.HOME_PAGE);
            WoQtView.this.checkButton.setText("重新开通");
            WoApiRequest.setProductCanceled("0000001010");
            Toast.makeText(WoQtView.access$500(WoQtView.this).context, "已成功退订", 1).show();
            if (WoApiRequest.getCacheCallNumber() != null)
              WoApiRequest.qrySubedProductsWithToken(WoApiRequest.getLocalToken(), WoQtView.this.woAsyncTaskHandler, "qrySubedProductsToUpdateSubInfo");
          }
          while (true)
          {
            WoQtView.this.unsubButton.setText("立刻退订");
            WoQtView.this.unsubButton.setEnabled(true);
            WoQtView.this.requestLayout();
            WoQtView.this.invalidate();
            return;
            if (!str10.equals("770002"))
              break;
            Toast.makeText(WoQtView.access$500(WoQtView.this).context, "验证码错误或已过时", 1).show();
            WoApiRequest.log(localJSONObject1, "1", str10, str1);
          }
        }
      }
      catch (JSONException localJSONException6)
      {
        while (true)
        {
          String str10;
          localJSONException6.printStackTrace();
          continue;
          if ((str10.equals("40305")) || (str10.equals("40307")))
          {
            Toast.makeText(WoQtView.access$500(WoQtView.this).context, "退订失败，请重新操作", 1).show();
            WoApiRequest.resetLocalToken();
            WoApiRequest.reset();
            WoApiRequest.init(WoQtView.access$500(WoQtView.this).context);
            WoApiRequest.log(localJSONObject1, "1", str10, str1);
          }
          else if (str10.equals("803022"))
          {
            Toast.makeText(WoQtView.access$500(WoQtView.this).context, "本手机不存在订购关系，请用订购号码所在手机退订！", 1).show();
            WoApiRequest.log(localJSONObject1, "1", str10, str1);
          }
          else
          {
            Toast.makeText(WoQtView.access$500(WoQtView.this).context, "退订失败，请检查联网情况，退出并重新操作", 1).show();
            WoApiRequest.log(localJSONObject1, "1", str10, str1);
            continue;
            Toast.makeText(WoQtView.access$500(WoQtView.this).context, "退订失败，请检查联网情况，稍后请重新操作", 1).show();
            WoApiRequest.log(localJSONObject1, "1", null, str1);
          }
        }
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.personalcenter.woqt.WoQtView
 * JD-Core Version:    0.6.2
 */