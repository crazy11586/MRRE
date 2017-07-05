package com.jsu.bigdot.mrre.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jsu.bigdot.mrre.AppManager;
import com.jsu.bigdot.mrre.R;

/**
 * Created by Bigdot on 2017/7/2.
 */

public class BaseActivity <T extends BasePresenter> extends AppCompatActivity implements BaseView{

    protected T mPresenter;

    protected Toolbar toolbar;
    protected Context mContext;
    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  AppManager.get
        mContext = this;
        initDialog();
    }

    private void initDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.show_loading_msg));
    }

    public void initToolBar() {
      //  toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.tool_bar_white));
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public Toolbar getToolbar() {
        return toolbar;
    }


    @Override
    public void initView() {

    }

    /**
     * 资源释放
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mPresenter != null)
            mPresenter.onDestroy();

        AppManager.getAppManager().finishActivity(this);
    }

}
