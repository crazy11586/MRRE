package com.jsu.bigdot.mrre.regist.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jsu.bigdot.mrre.R;
import com.jsu.bigdot.mrre.utils.StringUtil;
import com.jsu.bigdot.mrre.utils.SystemUtil;
import com.jsu.bigdot.mrre.utils.ToastUtil;
import com.jsu.bigdot.mrre.base.BaseFragment;
import com.jsu.bigdot.mrre.regist.RegistContract;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;


/**
 * author：cheikh on 16/5/14 20:45
 * email：wanghonghi@126.com
 */

public class RegisterFirstStepFragment extends BaseFragment{

    private SharedPreferences sp;

    private static final String TAG = "RegisterFirstStepFragme";

    @Bind(R.id.edit_mobile)
    EditText mMobileEdit;

    @Bind(R.id.btn_clear_mobile)
    ImageView mClearMobileBtn;

    @Bind(R.id.btn_send_code)
    Button mSendCodeBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register_first_step, container, false);
        ButterKnife.bind(this,v);
        ToastUtil.init(getActivity());
        mSendCodeBtn.setEnabled(false);
        return v;
    }

    @OnTextChanged(R.id.edit_mobile)
    public void onMobileTextChange(CharSequence s) {
        int visible = StringUtil.isEmpty(s.toString()) ? View.GONE : View.VISIBLE;
        mClearMobileBtn.setVisibility(visible);

        if(isMobile(s.toString())){
            mSendCodeBtn.setEnabled(true);
        }else{
            mSendCodeBtn.setEnabled(false);
        }
    }
    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    @OnClick({R.id.btn_clear_mobile, R.id.btn_send_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clear_mobile:
                mMobileEdit.setText("");
                break;
            case R.id.btn_send_code:
                sendSmsCode();
                break;
        }
    }

    /**
     * 执行发送验证码的操作
     */
    private void sendSmsCode() {
        // 隐藏软键盘
        SystemUtil.hideKeyBoard(getContext());

      //   验证手机号是否为空
        final String mobile = mMobileEdit.getText().toString().trim();
        if (TextUtils.isEmpty(mobile)) {
            ToastUtil.showToast(R.string.toast_error_empty_phone);
            return;
        }


        int mobile_code = (int)((Math.random()*9+1)*100000);
        sp = getActivity().getSharedPreferences("regist_content", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("phone",mMobileEdit.getText().toString().trim());
        edit.putInt("mobile_code",mobile_code);

        edit.commit();

        Intent intent = new Intent();
        intent.setAction("com.jsu.activity.change");
        intent.putExtra("index","2");
        getActivity().sendBroadcast(intent);
        mSendCodeBtn.setEnabled(false);
        // 发送验证码
        new MyTask().execute(mMobileEdit.getText().toString().trim(),mobile_code);

    }

    @Override
    public void initView() {

    }

    class MyTask extends AsyncTask{

        private static final String TAG = "asdasd";

        @Override
        protected Object doInBackground(Object[] objects) {
            Send((String)objects[0],(int)objects[1]);
            return null;
        }
    }

    private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";

    private static void Send(String phone ,int mobile_code) {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(Url);

        client.getParams().setContentCharset("GBK");
        method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=GBK");


        String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");

        NameValuePair[] data = {//提交短信
                new NameValuePair("account", "C81578146"), //查看用户名请登录用户中心->验证码、通知短信->帐户及签名设置->APIID
                new NameValuePair("password", "39f1c387e23bc3093a770746cce10bc9"),  //查看密码请登录用户中心->验证码、通知短信->帐户及签名设置->APIKEY
                new NameValuePair("mobile", phone),
                new NameValuePair("content", content),
        };
        method.setRequestBody(data);
        try {
            client.executeMethod(method);
            String SubmitResult =method.getResponseBodyAsString();
            Document doc = DocumentHelper.parseText(SubmitResult);
            Element root = doc.getRootElement();

            String code = root.elementText("code");
            String msg = root.elementText("msg");
            String smsid = root.elementText("smsid");

            if("2".equals(code)){
                System.out.println("短信提交成功");
            }

        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}