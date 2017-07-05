package com.jsu.bigdot.mrre.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.jsu.bigdot.mrre.R;
import com.jsu.bigdot.mrre.base.BaseActivity;
import com.jsu.bigdot.mrre.regist.RegistActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Bigdot on 2017/7/2.
 */

public class LoginActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.View{

    @Bind(R.id.email)
    public AutoCompleteTextView mEmailView;
    @Bind(R.id.password)
    public EditText mPasswordView;
    @Bind(R.id.login_progress)
    public View mProgressView;
    @Bind(R.id.login_form)
    public View mLoginFormView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mPresenter=new LoginPresenter(this);
        mPresenter.start();
    }

    public void setPresenter(LoginPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void navigateToRegister() {
        Intent intent =new Intent(getBaseContext(),RegistActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.email_sign_in_button, R.id.email_register_button})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.email_sign_in_button:
                mPresenter.login(mEmailView.getText().toString(),mPasswordView.getText().toString());
                hideProgress();
                break;

            case R.id.email_register_button:
                navigateToRegister();
                break;

        }
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void initView() {
    }
}
