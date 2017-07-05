package com.jsu.bigdot.mrre.regist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.jsu.bigdot.mrre.R;
import com.jsu.bigdot.mrre.base.BaseActivity;
import com.jsu.bigdot.mrre.login.LoginActivity;
import com.jsu.bigdot.mrre.regist.fragment.RegisterFirstStepFragment;
import com.jsu.bigdot.mrre.regist.fragment.RegisterSecondStepFragment;
import com.jsu.bigdot.mrre.regist.fragment.RegisterThirdStepFragment;
import com.jsu.bigdot.mrre.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Bigdot on 2017/7/3.
 */

public class RegistActivity extends BaseActivity<RegistContract.Presenter> implements RegistContract.View{

    FragmentTransaction fragmentTransaction;
    public static RegistActivity registActivity;

    @Bind(R.id.back)
    ImageView iv_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registActivity = new RegistActivity();
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mPresenter = new RegistPresenter(this);
        mPresenter.start();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mPresenter.setFragmentTransaction(fragmentTransaction);
        mPresenter.Change(1);

        receiver = new MyReceiver();
        registerReceiver(receiver, new IntentFilter("com.jsu.activity.change"));
    }


    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("哈哈哈哈，广播事件到了。");

            String index = intent.getStringExtra("index");
            if(index.equals("4")){
                SharedPreferences sp = getSharedPreferences("regist_content", Context.MODE_PRIVATE);
                String userphone = sp.getString("phone","null");
                String username = userphone;
                String userimg = "http://img2.imgtn.bdimg.com/it/u=49292017,22064401&fm=28&gp=0.jpg";
                String password = sp.getString("password","null");
                Log.d("test", "onReceive: "+userimg+password+username);
                mPresenter.register(userphone,username,password,userimg);

            }else {
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                mPresenter.setFragmentTransaction(fragmentTransaction);
                mPresenter.Change(Integer.parseInt(index));
            }
        }
    }
    private MyReceiver receiver;

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void PasswordOk() {
        ToastUtil.showLongToast("yes");
    }

    @Override
    public void PasswordError() {
        ToastUtil.showLongToast("no");
    }

    @OnClick(R.id.back)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                Intent it = new Intent(RegistActivity.this, LoginActivity.class);
                startActivity(it);
                this.finish();
                break;
        }
    }
    @Override
    public void showProgress() {
        progressDialog.show();
    }

//    Handler handler = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message message) {
//            mPresenter.Change(message.what);
//            return false;
//        }
//    });

}
