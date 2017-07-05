package com.jsu.bigdot.mrre.regist.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsu.bigdot.mrre.utils.CountDownTimerView;
import com.jsu.bigdot.mrre.R;
import com.jsu.bigdot.mrre.utils.StringUtil;
import com.jsu.bigdot.mrre.utils.SystemUtil;
import com.jsu.bigdot.mrre.utils.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;


/**
 * author：cheikh on 16/5/14 20:45
 * email：wanghonghi@126.com
 */
public class RegisterSecondStepFragment extends Fragment {

    @Bind(R.id.txt_title)
    TextView mTitleTxt;

    @Bind(R.id.edit_code)
    EditText mCodeEdit;

    @Bind(R.id.btn_clear_code)
    ImageView mClearCodeBtn;

    @Bind(R.id.btn_send_code)
    CountDownTimerView mSendCodeBtn;

    @Bind(R.id.btn_submit)
    Button mSubmitCodeBtn;

    @Bind(R.id.phone)
    TextView mPhoneTextView;

    private String mMobile;
    private int mCode;

    private int recLen = 60;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register_second_step, container, false);
        ButterKnife.bind(this,v);
        ToastUtil.init(getActivity());
        SharedPreferences sp = getActivity().getSharedPreferences("regist_content", Context.MODE_PRIVATE);
        mMobile = sp.getString("phone","null");
        mPhoneTextView.setText("  "+mMobile);
        mCode = sp.getInt("mobile_code",0);

        Refuse();
        new Timer().schedule(task, 1000, 1000);
        mSendCodeBtn.setEnabled(false);
        return v;
    }

    TimerTask task = new TimerTask(){
        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    recLen--;
                    mSendCodeBtn.setText(""+recLen+"秒后可重新发送");
                    if(recLen < 0){
                        mSendCodeBtn.setText("重新发送");
                        mSendCodeBtn.setEnabled(true);
                    }
                }
            });
        }
    };

    public void Refuse(){
        mSendCodeBtn.setText("");
        mSendCodeBtn.setEnabled(false);
    }


    @OnTextChanged(R.id.edit_code)
    public void onCodeTextChange(CharSequence s) {
        int visible = StringUtil.isEmpty(s.toString()) ? View.GONE : View.VISIBLE;
        mClearCodeBtn.setVisibility(visible);

        if(s.toString().length() == 6){
            mSubmitCodeBtn.setEnabled(true);
        }else{
            mSubmitCodeBtn.setEnabled(false);
        }
    }

    @OnClick({R.id.btn_clear_code, R.id.btn_send_code, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clear_code:
                mCodeEdit.setText("");
                break;
            case R.id.btn_send_code:
                sendSmsCode();
                break;
            case R.id.btn_submit:
                submitCode();
                break;
        }
    }

    private void sendSmsCode() {
        ToastUtil.showLongToast("Send");
    }

    /**
     * 执行发送验证码的操作
     */
    private void submitCode() {
        // 隐藏软键盘
        SystemUtil.hideKeyBoard(getContext());

        // 避免重复点击
        mSubmitCodeBtn.setEnabled(false);

        // 验证验证码是否为空
        final String code = mCodeEdit.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            ToastUtil.showToast(R.string.toast_error_empty_code);
            mSubmitCodeBtn.setEnabled(true);
            return;
        }else if(mCode == Integer.parseInt(code)){
            Intent intent = new Intent();
            intent.setAction("com.jsu.activity.change");
            intent.putExtra("index","3");
            getActivity().sendBroadcast(intent);
        }else{
            ToastUtil.showLongToast("验证码错误，请重新输入");
            mSubmitCodeBtn.setEnabled(true);
        }
    }



}