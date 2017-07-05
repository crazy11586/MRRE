package com.jsu.bigdot.mrre.regist.fragment;

import android.content.Context;
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

import com.jsu.bigdot.mrre.R;
import com.jsu.bigdot.mrre.utils.StringUtil;
import com.jsu.bigdot.mrre.utils.SystemUtil;
import com.jsu.bigdot.mrre.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * author：cheikh on 16/5/14 20:45
 * email：wanghonghi@126.com
 */
public class RegisterThirdStepFragment extends Fragment {

    @Bind(R.id.edit_password)
    EditText mPasswordEdit;

    @Bind(R.id.btn_clear_password)
    ImageView mClearPasswordBtn;

    @Bind(R.id.edit_password_again)
    EditText mPasswordAgainEdit;

    @Bind(R.id.btn_clear_password_again)
    ImageView mClearPasswordAgainBtn;

    @Bind(R.id.btn_register)
    Button mRegisterBtn;

    private String mMobile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register_third_step, container, false);
        ButterKnife.bind(this,v);
        ToastUtil.init(getActivity());
        SharedPreferences sp = getActivity().getSharedPreferences("regist_content", Context.MODE_PRIVATE);
        mMobile = sp.getString("phone","null");
        return v;
    }

    @OnTextChanged(R.id.edit_password)
    public void onPasswordTextChange(CharSequence s) {
        int visible = StringUtil.isEmpty(s.toString()) ? View.GONE : View.VISIBLE;
        mPasswordEdit.setVisibility(visible);
    }

    @OnTextChanged(R.id.edit_password_again)
    public void onPasswordAgainTextChange(CharSequence s) {
        int visible = StringUtil.isEmpty(s.toString()) ? View.GONE : View.VISIBLE;
        mPasswordAgainEdit.setVisibility(visible);
    }

    @OnClick({R.id.btn_clear_password, R.id.btn_clear_password_again, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clear_password:
                mPasswordEdit.setText("");
                break;
            case R.id.btn_clear_password_again:
                mPasswordAgainEdit.setText("");
                break;
            case R.id.btn_register:
                register();
                break;
        }
    }

    private void register() {

        // 隐藏软键盘
        SystemUtil.hideKeyBoard(getContext());

        // 验证密码是否为空
        final String password = mPasswordEdit.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showToast(R.string.toast_error_empty_password);
            return;
        }
        // 验证确认密码是否为空
        final String passwordAgain = mPasswordAgainEdit.getText().toString().trim();
        if (TextUtils.isEmpty(passwordAgain)) {
            ToastUtil.showToast(R.string.toast_error_empty_password_confirm);
            return;
        }
        // 验证两次的密码输入是否一致
        if (!password.equals(passwordAgain)) {
            ToastUtil.showToast(R.string.toast_error_password_not_consistent);
            return;
        }

        // 避免重复点击
        mRegisterBtn.setEnabled(false);
        ToastUtil.showLongToast("Success: mMobile "+ mMobile);
    }

}