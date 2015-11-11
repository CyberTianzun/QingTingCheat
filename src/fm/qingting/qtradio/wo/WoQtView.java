package fm.qingting.qtradio.wo;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import fm.qingting.framework.view.ScrollViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.controller.ControllerManager;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WoQtView extends ScrollViewImpl
  implements View.OnClickListener
{
  private static final String REMINDER_BOUND = "您正在使用“蜻蜓流量包”，在蜻蜓FM手机客户端在线收听，下载节目流量全免";
  private static final String REMINDER_CANCELBOUND = "您已经成功退订“蜻蜓流量包”，您还享受此业务到月底。本月可以随时开通，当月重复开通只计费1次";
  private static final String REMINDER_NOTBOUND = "在蜻蜓FM客户端内，联通2G/3G/4G网络下在线收听音频内容，流量全免（不消耗你您原套餐内的流量）";
  private static final String REMINDER_SUCCESS = "注意事项：\n1.开通后，可在”我的>免流量畅听”中查看相关信息或退订当前业务。\n2.请保证使用开通号码联网。少数流量监控软件，可能导致流量包失效。\n3.开通畅听包服务后，若更换手机，请在免流量畅听页面点击\"立即绑定\"，绑定后，即可在新手机上享受免流量服务。";
  private static final String RULE = "1. 资费：10元/月，开通期间使用蜻蜓FM手机客户端收听、下载节目，当月内流量全包。\n2. 业务开通后立即生效，每月月初自动续费。 包月业务退订后，当月可继续使用至月底，下月不再扣费。\n3. 联通沃3G预付费20元卡不支持开通此业务。\n4. 联通2G/3G/4G用户上网流量上限为6G，当月流量超出上限后，联通将自动关闭上网功能，下月自动开通。\n5. 请保证使用开通号码联网，谨慎安装流量监控软件，使用业务时请尽量关闭WIFI，避免因 WIFI和2G/3G网络切换频繁导致流量包失效。\n6. 分享、聊天等社交功能暂不支持免流量（消耗少量流量）。 \n7. 如有疑问，请咨询客服：4000 666 976";
  private TextView mBindTv;
  private RelativeLayout mContentView;
  private TextView mFaqTv;
  private RelativeLayout mHomeContainer;
  private boolean mIsHome = true;
  private String mPhoneNumber;
  private EditText mPhoneNumberEt;
  private TextView mReminderTv;
  private TextView mRuleTv;
  private final ViewLayout mStandLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 800, 480, 800, 0, 0, ViewLayout.FILL);
  private Button mSubmitBtn;
  private RelativeLayout mSubscribeContainer;
  private TextView mSubscribeStateTv;
  private Button mSubscribeSubmitBtn;
  private TextView mSubscribeTitle;
  private RelativeLayout mSuccessContainer;
  private TextView mSuccessRemiderTv;
  private boolean mTryBind = false;
  private BoundState mUserState = BoundState.NOT_BOUND;
  private Button mVerifycodeBtn;
  private EditText mVerifycodeEt;
  private WoAsyncTaskHandler mWoAsyncTaskHandler = new WoAsyncTaskHandler();

  public WoQtView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(-1);
    setView();
    setListener();
    setState();
    setInfo();
  }

  private void getVerfyCode()
  {
    WoApiRequest.sendEventMessage("unicomClickVerfycode");
    try
    {
      this.mPhoneNumber = this.mPhoneNumberEt.getText().toString();
      if (!WoApiRequest.isMobilePhoneNumber(this.mPhoneNumber))
      {
        Toast.makeText(getContext(), "请输入正确的手机号", 1).show();
        return;
      }
      if (!WoApiRequest.isChinaUnicomPhoneNumber(this.mPhoneNumber))
      {
        Toast.makeText(getContext(), "号码不在服务范围，请用其他号段的联通号码", 1).show();
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    this.mVerifycodeBtn.setText("正在发送..");
    this.mVerifycodeBtn.setEnabled(false);
    WoApiRequest.sendVerifyCodeForPhoneNumber(this.mPhoneNumber, this.mWoAsyncTaskHandler, "sendVerifyCodeForPhoneNumber");
  }

  private void setInfo()
  {
    this.mRuleTv.setText("1. 资费：10元/月，开通期间使用蜻蜓FM手机客户端收听、下载节目，当月内流量全包。\n2. 业务开通后立即生效，每月月初自动续费。 包月业务退订后，当月可继续使用至月底，下月不再扣费。\n3. 联通沃3G预付费20元卡不支持开通此业务。\n4. 联通2G/3G/4G用户上网流量上限为6G，当月流量超出上限后，联通将自动关闭上网功能，下月自动开通。\n5. 请保证使用开通号码联网，谨慎安装流量监控软件，使用业务时请尽量关闭WIFI，避免因 WIFI和2G/3G网络切换频繁导致流量包失效。\n6. 分享、聊天等社交功能暂不支持免流量（消耗少量流量）。 \n7. 如有疑问，请咨询客服：4000 666 976");
    switch (1.$SwitchMap$fm$qingting$qtradio$wo$WoQtView$BoundState[this.mUserState.ordinal()])
    {
    default:
      this.mReminderTv.setText("在蜻蜓FM客户端内，联通2G/3G/4G网络下在线收听音频内容，流量全免（不消耗你您原套餐内的流量）");
      this.mSubscribeStateTv.setText("流量包");
      this.mSubmitBtn.setText("立即开通");
      return;
    case 1:
      this.mReminderTv.setText("您正在使用“蜻蜓流量包”，在蜻蜓FM手机客户端在线收听，下载节目流量全免");
      this.mSubscribeStateTv.setText("已开通");
      this.mSubmitBtn.setText("退订");
      return;
    case 2:
    }
    this.mReminderTv.setText("您已经成功退订“蜻蜓流量包”，您还享受此业务到月底。本月可以随时开通，当月重复开通只计费1次");
    this.mSubscribeStateTv.setText("流量包");
    this.mSubmitBtn.setText("重新开通");
  }

  private void setListener()
  {
    this.mSubmitBtn.setOnClickListener(this);
    this.mFaqTv.setOnClickListener(this);
    this.mVerifycodeBtn.setOnClickListener(this);
    this.mSubscribeSubmitBtn.setOnClickListener(this);
    this.mBindTv.setOnClickListener(this);
  }

  private void setPhoneNumber()
  {
    this.mPhoneNumber = WoApiRequest.getBindCallNumber();
    if ((this.mPhoneNumber == null) || (this.mPhoneNumber.equals("")))
    {
      this.mPhoneNumber = WoApiRequest.getCacheCallNumber();
      if ((this.mPhoneNumber == null) || (this.mPhoneNumber.equals("")))
        this.mPhoneNumber = WoApiRequest.getSimcardCallNumber();
    }
    if (WoApiRequest.isMobilePhoneNumber(this.mPhoneNumber))
    {
      this.mPhoneNumberEt.setText(this.mPhoneNumber);
      return;
    }
    this.mPhoneNumberEt.setHint("请输入电话号码");
  }

  private void setState()
  {
    if (WoApiRequest.hasInited())
      if (WoApiRequest.hasBound())
        if (WoApiRequest.hasSubedProductByQt())
          if (WoApiRequest.productHasCanceled("0000001010"))
            this.mUserState = BoundState.BOUND_SUBED_HAVE_CANCELED;
    while ((this.mUserState == BoundState.NOT_BOUND) || (this.mUserState == BoundState.BOUNDED_UNSUBED))
    {
      WoApiRequest.disableWoProxy();
      return;
      this.mUserState = BoundState.BOUND_SUBED;
      continue;
      this.mUserState = BoundState.NOT_BOUND;
      continue;
      this.mUserState = BoundState.NOT_BOUND;
      continue;
      this.mUserState = BoundState.NOT_BOUND;
    }
    WoApiRequest.enableWoProxy();
  }

  private void setView()
  {
    this.mContentView = ((RelativeLayout)inflate(getContext(), 2130903066, null));
    addView(this.mContentView);
    this.mHomeContainer = ((RelativeLayout)this.mContentView.findViewById(2131230871));
    this.mReminderTv = ((TextView)this.mHomeContainer.findViewById(2131230874));
    this.mSubscribeStateTv = ((TextView)this.mHomeContainer.findViewById(2131230879));
    this.mSubmitBtn = ((Button)this.mHomeContainer.findViewById(2131230880));
    this.mRuleTv = ((TextView)this.mHomeContainer.findViewById(2131230885));
    this.mFaqTv = ((TextView)this.mHomeContainer.findViewById(2131230886));
    this.mBindTv = ((TextView)this.mHomeContainer.findViewById(2131230882));
    this.mSubscribeContainer = ((RelativeLayout)this.mContentView.findViewById(2131230887));
    this.mPhoneNumberEt = ((EditText)this.mSubscribeContainer.findViewById(2131230889));
    this.mVerifycodeEt = ((EditText)this.mSubscribeContainer.findViewById(2131230891));
    this.mVerifycodeBtn = ((Button)this.mSubscribeContainer.findViewById(2131230892));
    this.mSubscribeSubmitBtn = ((Button)this.mSubscribeContainer.findViewById(2131230893));
    this.mSubscribeTitle = ((TextView)this.mSubscribeContainer.findViewById(2131230888));
    this.mSubscribeContainer.setVisibility(8);
    this.mSuccessContainer = ((RelativeLayout)this.mContentView.findViewById(2131230894));
    this.mSuccessRemiderTv = ((TextView)this.mSuccessContainer.findViewById(2131230897));
    this.mSuccessRemiderTv.setText("注意事项：\n1.开通后，可在”我的>免流量畅听”中查看相关信息或退订当前业务。\n2.请保证使用开通号码联网。少数流量监控软件，可能导致流量包失效。\n3.开通畅听包服务后，若更换手机，请在免流量畅听页面点击\"立即绑定\"，绑定后，即可在新手机上享受免流量服务。");
    this.mSuccessContainer.setVisibility(8);
  }

  private void submitSubscribe()
  {
    String str = this.mVerifycodeEt.getText().toString();
    this.mPhoneNumber = this.mPhoneNumberEt.getText().toString();
    if (!WoApiRequest.isMobilePhoneNumber(this.mPhoneNumber))
    {
      Toast.makeText(getContext(), "请输入正确的手机号", 1).show();
      return;
    }
    if (!WoApiRequest.isChinaUnicomPhoneNumber(this.mPhoneNumber))
    {
      Toast.makeText(getContext(), "号码不在服务范围，请用其他号段的联通号码", 1).show();
      return;
    }
    if ((this.mPhoneNumber.equals(WoApiRequest.getBindCallNumber())) && (this.mTryBind))
    {
      Toast.makeText(getContext(), "此号码已经绑定，无需重复绑定", 1).show();
      return;
    }
    if (str.equalsIgnoreCase(""))
    {
      Toast.makeText(getContext(), "请输入验证码", 1).show();
      return;
    }
    WoApiRequest.setCacheCallNumber(this.mPhoneNumber);
    hideSoftKeyboard((Activity)getContext());
    this.mSubscribeSubmitBtn.setEnabled(false);
    WoApiRequest.sendEventMessage("unicomClickSubscribe");
    WoApiRequest.setActionNumber(this.mPhoneNumber);
    if (this.mTryBind)
    {
      this.mSubscribeSubmitBtn.setText("绑定中..");
      WoApiRequest.getAuthTokenWithVCode(this.mPhoneNumber, str, this.mWoAsyncTaskHandler, "getAuthTokenWithVCodeToBind");
    }
    while (true)
    {
      WoApiRequest.sendEventMessage("unicomClickSubscribe");
      WoApiRequest.setActionNumber(this.mPhoneNumber);
      return;
      this.mSubscribeSubmitBtn.setText("开通中..");
      WoApiRequest.getAuthTokenWithVCode(this.mPhoneNumber, str, this.mWoAsyncTaskHandler, "getAuthTokenWithVCodeToSubToBind");
    }
  }

  public void hideSoftKeyboard(Activity paramActivity)
  {
    InputMethodManager localInputMethodManager = (InputMethodManager)paramActivity.getSystemService("input_method");
    if (localInputMethodManager != null)
      localInputMethodManager.hideSoftInputFromWindow(paramActivity.getCurrentFocus().getWindowToken(), 0);
  }

  public boolean isHome()
  {
    if (this.mIsHome)
      return true;
    this.mIsHome = true;
    setState();
    setInfo();
    this.mHomeContainer.setVisibility(0);
    this.mSubscribeContainer.setVisibility(8);
    this.mSuccessContainer.setVisibility(8);
    return false;
  }

  public void onClick(View paramView)
  {
    if (paramView == this.mSubmitBtn)
      switch (1.$SwitchMap$fm$qingting$qtradio$wo$WoQtView$BoundState[this.mUserState.ordinal()])
      {
      default:
        this.mIsHome = false;
        this.mSubscribeContainer.setVisibility(0);
        this.mHomeContainer.setVisibility(8);
        this.mSuccessContainer.setVisibility(8);
        this.mSubscribeSubmitBtn.setText("立即开通");
        this.mSubscribeTitle.setText("开通联通\"蜻蜓FM10元流量包\"");
        setPhoneNumber();
      case 1:
      }
    do
    {
      return;
      WoApiRequest.sendEventMessage("unicomClickUnsubscribe");
      this.mSubmitBtn.setText("退订中...");
      this.mSubmitBtn.setEnabled(false);
      WoApiRequest.unSubProductWithToken(WoApiRequest.getLocalToken(), this.mWoAsyncTaskHandler, "unSubProductWithToken");
      return;
      if (paramView == this.mFaqTv)
      {
        ControllerManager.getInstance().openWoFaqController();
        return;
      }
      if (paramView == this.mVerifycodeBtn)
      {
        getVerfyCode();
        return;
      }
      if (paramView == this.mSubscribeSubmitBtn)
      {
        submitSubscribe();
        return;
      }
    }
    while (paramView != this.mBindTv);
    this.mTryBind = true;
    this.mIsHome = false;
    this.mHomeContainer.setVisibility(8);
    this.mSubscribeContainer.setVisibility(0);
    this.mSuccessContainer.setVisibility(8);
    this.mSubscribeSubmitBtn.setText("立即绑定");
    this.mSubscribeTitle.setText("绑定已经开通流量包的手机号码");
    setPhoneNumber();
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.mStandLayout.scaleToBounds(i, j);
    int k = View.MeasureSpec.makeMeasureSpec(536870911, -2147483648);
    this.mContentView.measure(paramInt1, k);
    setMeasuredDimension(this.mStandLayout.width, this.mStandLayout.height);
  }

  static enum BoundState
  {
    static
    {
      NEW_USER_NOT_ON_TRIAL = new BoundState("NEW_USER_NOT_ON_TRIAL", 1);
      OLD_USER_SUBED = new BoundState("OLD_USER_SUBED", 2);
      OLD_USER_NOT_SUBED = new BoundState("OLD_USER_NOT_SUBED", 3);
      OLD_USER_SUBED_HAVE_CANCELED = new BoundState("OLD_USER_SUBED_HAVE_CANCELED", 4);
      NOT_BOUND = new BoundState("NOT_BOUND", 5);
      BOUND_SUBED = new BoundState("BOUND_SUBED", 6);
      BOUND_SUBED_HAVE_CANCELED = new BoundState("BOUND_SUBED_HAVE_CANCELED", 7);
      BOUNDED_UNSUBED = new BoundState("BOUNDED_UNSUBED", 8);
      BoundState[] arrayOfBoundState = new BoundState[9];
      arrayOfBoundState[0] = NEW_USER_ON_TRIAL;
      arrayOfBoundState[1] = NEW_USER_NOT_ON_TRIAL;
      arrayOfBoundState[2] = OLD_USER_SUBED;
      arrayOfBoundState[3] = OLD_USER_NOT_SUBED;
      arrayOfBoundState[4] = OLD_USER_SUBED_HAVE_CANCELED;
      arrayOfBoundState[5] = NOT_BOUND;
      arrayOfBoundState[6] = BOUND_SUBED;
      arrayOfBoundState[7] = BOUND_SUBED_HAVE_CANCELED;
      arrayOfBoundState[8] = BOUNDED_UNSUBED;
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

  class WoAsyncTaskHandler
    implements IHttpAsyncTaskListener
  {
    WoAsyncTaskHandler()
    {
    }

    public void onGetResult(Object paramObject1, Object paramObject2)
    {
      JSONObject localJSONObject1;
      String str1;
      label412: label1359: 
      try
      {
        JSONObject localJSONObject11 = WoApiRequest.parseJsonString((String)paramObject2);
        localJSONObject1 = localJSONObject11;
      }
      catch (Exception localException2)
      {
        label851: label1377: 
        try
        {
          String str12 = localJSONObject1.getString("res");
          str1 = str12;
          str2 = (String)paramObject1;
          if ((str2.equals("sendVerifyCodeForSubProduct")) || (str2.equals("sendVerifyCodeForPhoneNumber")))
          {
            WoQtView.this.mVerifycodeBtn.setEnabled(true);
            WoQtView.this.mVerifycodeBtn.setText("获取短信验证码");
          }
        }
        catch (Exception localException2)
        {
          String str2;
          label500: label891: 
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
                    Toast.makeText(WoQtView.this.getContext(), "验证码已发送，请稍候", 1).show();
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
                  Toast.makeText(WoQtView.this.getContext(), "验证码发送失败，请重新发送", 1).show();
                  WoApiRequest.log(localJSONObject1, "1", str3, str1);
                  continue;
                }
              }
              catch (JSONException localJSONException1)
              {
                localJSONException1.printStackTrace();
                Toast.makeText(WoQtView.this.getContext(), "验证码发送失败，请重新发送", 1).show();
                WoApiRequest.log(localJSONObject1, "1", null, str1);
                continue;
                Toast.makeText(WoQtView.this.getContext(), "验证码发送失败，请重新发送", 1).show();
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
                    break label500;
                  str4 = localJSONObject3.getString("returnCode");
                  if (!str4.equals("000000"))
                    break label412;
                  str5 = localJSONObject3.getJSONObject("token").getString("access_token");
                  WoApiRequest.setActionNumberToken(str5);
                  WoApiRequest.updateTokenToServer(WoQtView.this.mPhoneNumber, str5, WoQtView.this.mWoAsyncTaskHandler, "updateToken");
                  if (str2.equals("getAuthTokenWithVCodeToBind"))
                  {
                    WoApiRequest.qrySubedProductsWithToken(str5, WoQtView.this.mWoAsyncTaskHandler, "qrySubedProductsWithTokenToBind");
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
                  WoApiRequest.subProductWithToken(str5, WoQtView.this.mWoAsyncTaskHandler, "subProductWithTokenToBind");
                  return;
                  Toast.makeText(WoQtView.this.getContext(), "验证码过时或者错误！", 1).show();
                  WoQtView.this.mSubmitBtn.setEnabled(true);
                  WoQtView.this.mSubscribeSubmitBtn.setEnabled(true);
                  if (WoQtView.this.mTryBind)
                    WoQtView.this.mSubscribeSubmitBtn.setText("立刻绑定");
                  while (true)
                  {
                    WoApiRequest.log(localJSONObject1, "1", str4, str1);
                    return;
                    WoQtView.this.mSubscribeSubmitBtn.setText("立即开通");
                  }
                  Toast.makeText(WoQtView.this.getContext(), "验证码过时或者错误！", 1).show();
                  WoQtView.this.mSubmitBtn.setEnabled(true);
                  WoQtView.this.mSubscribeSubmitBtn.setEnabled(true);
                  if (WoQtView.this.mTryBind)
                    WoQtView.this.mSubscribeSubmitBtn.setText("立刻绑定");
                  while (true)
                  {
                    WoApiRequest.log(localJSONObject1, "1", null, str1);
                    return;
                    WoQtView.this.mSubscribeSubmitBtn.setText("立即开通");
                  }
                }
              }
              else
              {
                if ((str2.equals("subProductWithVCode")) || (str2.equals("subProductWithTokenToBind")))
                {
                  WoQtView.this.mSubscribeSubmitBtn.setText("立即开通");
                  WoQtView.this.mSubscribeSubmitBtn.setEnabled(true);
                  try
                  {
                    JSONObject localJSONObject4 = WoApiRequest.parseJsonString(str1);
                    if (localJSONObject4 != null)
                    {
                      str6 = localJSONObject4.getString("returnCode");
                      if (str2.equals("subProductWithTokenToBind"))
                      {
                        if ((!str6.equals("000000")) && (!str6.equals("803009")))
                          break label891;
                        WoApiRequest.addSubedProduct(new WoApiRequest.Product("0000001010"));
                        WoApiRequest.setBindInfo(WoApiRequest.getActionNumber());
                        WoQtView.access$602(WoQtView.this, WoQtView.BoundState.BOUND_SUBED);
                        WoApiRequest.setCacheCallNumber(WoApiRequest.getActionNumber());
                        WoApiRequest.setLocalToken(WoApiRequest.getActionNumberToken());
                        WoApiRequest.enableWoProxy();
                        if (WoApiRequest.isMobilePhoneNumber(WoApiRequest.getActionNumber()))
                        {
                          if (!str6.equals("000000"))
                            break label851;
                          WoQtView.this.mSubscribeContainer.setVisibility(8);
                          WoQtView.this.mHomeContainer.setVisibility(8);
                          WoQtView.this.mSuccessContainer.setVisibility(0);
                          WoApiRequest.sendMsg(WoApiRequest.getActionNumber(), WoInfo.genOldUserSubMsg(), WoQtView.this.mWoAsyncTaskHandler, "sendMsg");
                        }
                      }
                      while (true)
                      {
                        WoApiRequest.getActionNumberToken();
                        WoApiRequest.Product localProduct1 = new WoApiRequest.Product(new Date(), null);
                        WoApiRequest.updateUserInfoToServer(WoApiRequest.getActionNumber(), localProduct1, WoQtView.this.mWoAsyncTaskHandler, "updateUserInfoThenReQrySubedProductsToUpdateSubInfo");
                        WoQtView.this.requestLayout();
                        WoQtView.this.invalidate();
                        return;
                        Toast.makeText(WoQtView.this.getContext(), "该号码已经开通过，直接绑定。", 1).show();
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
                      Toast.makeText(WoQtView.this.getContext(), "开通失败，您的手机可能不支持本服务，或者稍后重试。", 1).show();
                      WoQtView.this.mSubmitBtn.setEnabled(true);
                      WoQtView.this.mSubscribeSubmitBtn.setEnabled(true);
                      if (WoQtView.this.mTryBind)
                        WoQtView.this.mSubscribeSubmitBtn.setText("立刻绑定");
                      while (true)
                      {
                        WoApiRequest.log(localJSONObject1, "1", str6, str1);
                        break;
                        WoQtView.this.mSubscribeSubmitBtn.setText("立即开通");
                      }
                      WoApiRequest.log(localJSONObject1, "1", null, str1);
                    }
                  }
                }
                if ((str2.equals("qrySubedProductsWithTokenToBind")) || (str2.equals("qrySubedProductsToUpdateSubInfo")))
                {
                  WoQtView.this.mSubmitBtn.setEnabled(true);
                  WoQtView.this.mSubscribeSubmitBtn.setEnabled(true);
                  if (WoQtView.this.mTryBind)
                    WoQtView.this.mSubscribeSubmitBtn.setText("立刻绑定");
                  String str7;
                  while (true)
                  {
                    try
                    {
                      JSONObject localJSONObject5 = WoApiRequest.parseJsonString(str1);
                      if (localJSONObject5 == null)
                        break label1412;
                      str7 = localJSONObject5.getString("returnCode");
                      if (!str7.equals("000000"))
                        break label1377;
                      JSONArray localJSONArray = localJSONObject5.getJSONArray("subedProducts");
                      ArrayList localArrayList1 = new ArrayList();
                      ArrayList localArrayList2 = new ArrayList();
                      int i = 0;
                      if (localJSONArray != null)
                        if (i < localJSONArray.length())
                        {
                          WoApiRequest.Product localProduct2 = new WoApiRequest.Product(localJSONArray.getJSONObject(i));
                          localArrayList2.add(localProduct2);
                          localArrayList1.add(localProduct2.productId);
                          i++;
                          continue;
                          WoQtView.this.mSubscribeSubmitBtn.setText("立即开通");
                          continue;
                        }
                      WoApiRequest.updateSubedProducts(localArrayList2);
                      if (!WoApiRequest.hasSubedProductByQt())
                        break label1359;
                      WoApiRequest.enableWoProxy();
                      if (!str2.equals("qrySubedProductsWithTokenToBind"))
                        break;
                      if (WoApiRequest.productByQtHasCanceled())
                      {
                        WoQtView.access$602(WoQtView.this, WoQtView.BoundState.BOUND_SUBED_HAVE_CANCELED);
                        WoApiRequest.enableWoProxy();
                        String str8 = WoApiRequest.getActionNumber();
                        String str9 = WoApiRequest.getActionNumberToken();
                        WoApiRequest.setCacheCallNumber(str8);
                        WoApiRequest.setLocalToken(str9);
                        WoApiRequest.setBindInfo(str8);
                        WoApiRequest.updateUserInfoToServer(str8, WoApiRequest.getSubedProductByQt(), WoQtView.this.mWoAsyncTaskHandler, "updateUserInfo");
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
                    WoQtView.access$602(WoQtView.this, WoQtView.BoundState.BOUND_SUBED);
                  }
                  if (str2.equals("qrySubedProductsToUpdateSubInfo"))
                  {
                    WoApiRequest.updateSubInfoToServer(WoApiRequest.getCacheCallNumber(), WoApiRequest.getSubedProduct("0000001010"), WoQtView.this.mWoAsyncTaskHandler, "updateSubInfoAfterQry");
                    return;
                    Toast.makeText(WoQtView.this.getContext(), "欲绑定的手机没有订购免包流量服务，请先开通服务。", 1).show();
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
                      break label1731;
                    j = localJSONObject6.getInt("errCode");
                    if (j == 0)
                    {
                      if ((!str2.equals("updateUserInfoThenReQrySubedProductsToUpdateSubInfo")) && (!str2.equals("updateUserInfoThenReQrySubedProductsToUpdateSubInfoAgain")))
                        continue;
                      WoApiRequest.qrySubedProductsWithToken(WoApiRequest.getLocalToken(), WoQtView.this.mWoAsyncTaskHandler, "qrySubedProductsToUpdateSubInfo");
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
                    WoApiRequest.updateUserInfoToServer(WoApiRequest.getCacheCallNumber(), localProduct3, WoQtView.this.mWoAsyncTaskHandler, "updateUserInfoThenReQrySubedProductsToUpdateSubInfoAgain");
                  WoApiRequest.log(localJSONObject1, "0", String.valueOf(j), str1);
                  return;
                  WoApiRequest.Product localProduct4 = new WoApiRequest.Product(new Date(), null);
                  if (str2.equals("updateUserInfoThenReQrySubedProductsToUpdateSubInfo"))
                    WoApiRequest.updateUserInfoToServer(WoApiRequest.getCacheCallNumber(), localProduct4, WoQtView.this.mWoAsyncTaskHandler, "updateUserInfoThenReQrySubedProductsToUpdateSubInfoAgain");
                  WoApiRequest.log(localJSONObject1, "0", null, str1);
                }
                else
                {
                  if (str2.equals("tryProduct"))
                  {
                    WoQtView.this.mSubmitBtn.setText("开通");
                    WoQtView.this.mSubmitBtn.setEnabled(true);
                    try
                    {
                      JSONObject localJSONObject9 = WoApiRequest.parseJsonString(str1);
                      if (localJSONObject9 != null)
                      {
                        if (localJSONObject9.getInt("errCode") != 0)
                          break label1971;
                        WoQtView.this.mSubscribeContainer.setVisibility(8);
                        WoQtView.this.mHomeContainer.setVisibility(8);
                        WoQtView.this.mSuccessContainer.setVisibility(0);
                        WoQtView.access$602(WoQtView.this, WoQtView.BoundState.BOUND_SUBED);
                        WoQtView.this.mSubmitBtn.setText("立刻退订");
                        WoApiRequest.setTryInfo(new WoApiRequest.TryInfo(true));
                        if (WoApiRequest.isMobilePhoneNumber(WoQtView.this.mPhoneNumber))
                          WoApiRequest.sendMsg(WoQtView.this.mPhoneNumber, WoInfo.genFirstTryMsg(), WoQtView.this.mWoAsyncTaskHandler, "sendMsg");
                        WoApiRequest.enableWoProxy();
                      }
                      while (true)
                      {
                        WoQtView.this.requestLayout();
                        WoQtView.this.invalidate();
                        return;
                        Toast.makeText(WoQtView.this.getContext(), "订购失败，请稍后再试。", 1).show();
                        WoQtView.this.mSubmitBtn.setText("开通");
                        WoQtView.this.mSubmitBtn.setEnabled(true);
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
        label1412: label1731: JSONObject localJSONObject7 = WoApiRequest.parseJsonString(str1);
        label1971: if (localJSONObject7 != null)
        {
          str10 = localJSONObject7.getString("returnCode");
          if (str10.equals("000000"))
          {
            WoQtView.access$602(WoQtView.this, WoQtView.BoundState.BOUND_SUBED_HAVE_CANCELED);
            WoQtView.this.mSubmitBtn.setText("重新开通");
            WoApiRequest.setProductCanceled("0000001010");
            Toast.makeText(WoQtView.this.getContext(), "已成功退订", 1).show();
            if (WoApiRequest.getCacheCallNumber() != null)
              WoApiRequest.qrySubedProductsWithToken(WoApiRequest.getLocalToken(), WoQtView.this.mWoAsyncTaskHandler, "qrySubedProductsToUpdateSubInfo");
          }
          while (true)
          {
            WoQtView.this.mSubmitBtn.setEnabled(true);
            WoQtView.this.setState();
            WoQtView.this.setInfo();
            return;
            if (!str10.equals("770002"))
              break;
            Toast.makeText(WoQtView.this.getContext(), "验证码错误或已过时", 1).show();
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
            Toast.makeText(WoQtView.this.getContext(), "退订失败，请重新操作", 1).show();
            WoApiRequest.resetLocalToken();
            WoApiRequest.reset();
            WoApiRequest.init(WoQtView.this.getContext());
            WoApiRequest.log(localJSONObject1, "1", str10, str1);
          }
          else if (str10.equals("803022"))
          {
            Toast.makeText(WoQtView.this.getContext(), "本手机不存在订购关系，请用订购号码所在手机退订！", 1).show();
            WoApiRequest.log(localJSONObject1, "1", str10, str1);
          }
          else
          {
            Toast.makeText(WoQtView.this.getContext(), "退订失败，请检查联网情况，退出并重新操作", 1).show();
            WoApiRequest.log(localJSONObject1, "1", str10, str1);
            continue;
            Toast.makeText(WoQtView.this.getContext(), "退订失败，请检查联网情况，稍后请重新操作", 1).show();
            WoApiRequest.log(localJSONObject1, "1", null, str1);
          }
        }
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.wo.WoQtView
 * JD-Core Version:    0.6.2
 */