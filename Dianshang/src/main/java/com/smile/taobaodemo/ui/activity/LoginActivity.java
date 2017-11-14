package com.smile.taobaodemo.ui.activity;

/**
 * Created by dell on 2017/11/13.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.smile.taobaodemo.R;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText name;
    private EditText pwd;
    private Button login;
    private TextView mobile;
    private TextView forgetPwd;
    private ImageView close;
    private CheckBox show_pwd;
    private ImageView delete_name;
    private ImageView delete_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //二维码使用
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_login);
        initView();
    }

//    UMAuthListener authListener = new UMAuthListener() {
//        /**
//         * @desc 授权开始的回调
//         * @param platform 平台名称
//         */
//        @Override
//        public void onStart(SHARE_MEDIA platform) {
//        }
//
//        /**
//         * @desc 授权成功的回调
//         * @param platform 平台名称
//         * @param action 行为序号，开发者用不上
//         * @param data 用户资料返回
//         */
//        @Override
//        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
//            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
//            String iconurl = data.get("iconurl");
//            String name = data.get("name");
//            //使用EventBus把值传给我的
//            EventBus.getDefault().post(new FirstEvent(iconurl, name));
//            finish();
//        }
//
//        /**
//         * @desc 授权失败的回调
//         * @param platform 平台名称
//         * @param action 行为序号，开发者用不上
//         * @param t 错误原因
//         */
//        @Override
//        public void onError(SHARE_MEDIA platform, int action, final Throwable t) {
//            Toast.makeText(LoginActivity.this, "登录失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
//        }
//
//        /**
//         * @desc 授权取消的回调
//         * @param platform 平台名称
//         * @param action 行为序号，开发者用不上
//         */
//        @Override
//        public void onCancel(SHARE_MEDIA platform, int action) {
//            Toast.makeText(LoginActivity.this, "取消了", Toast.LENGTH_LONG).show();
//        }
//    };

    private void initView() {
        //账号
        name = (EditText) findViewById(R.id.username);
        //密码
        pwd = (EditText) findViewById(R.id.userpwd);
        //登录
        login = (Button) findViewById(R.id.login);
        //手机注册
        mobile = (TextView) findViewById(R.id.mobile_register);
        //忘记密码
        forgetPwd = (TextView) findViewById(R.id.forget_pwd);
        //关闭按钮
        close = (ImageView) findViewById(R.id.close);
        //显示或隐藏密码
        show_pwd = (CheckBox) findViewById(R.id.show_pwd);
        //用户名输入行的删除(x) 图片
        delete_name = (ImageView) findViewById(R.id.delete_name);
        //密码输入行的删除(x) 图片
        delete_pwd = (ImageView) findViewById(R.id.delete_pwd);
        //普通注册
        TextView putongzhuce = (TextView) findViewById(R.id.putongzhuce);
        //QQ 微信 新浪
        ImageView QQ = (ImageView) findViewById(R.id.QQ);
        ImageView Sina = (ImageView) findViewById(R.id.Sina);
        ImageView weixin = (ImageView) findViewById(R.id.WeiXin);

        login.setOnClickListener(this);
        mobile.setOnClickListener(this);
        forgetPwd.setOnClickListener(this);
        close.setOnClickListener(this);
        delete_name.setOnClickListener(this);
        delete_pwd.setOnClickListener(this);
        QQ.setOnClickListener(this);
        Sina.setOnClickListener(this);
        weixin.setOnClickListener(this);
        putongzhuce.setOnClickListener(this);

        //判断用户名和密码是否为空,为空按钮不能点击
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (TextUtils.isEmpty(name.getText().toString().trim()) ||
                            TextUtils.isEmpty(pwd.getText().toString().trim())) {
                        //只要有一个为空,设置登录按钮不能点击
                        login.setEnabled(false);
                        login.setBackgroundResource(R.drawable.login_button1);
                    } else {
                        //都不为空,设置登录按钮能点击
                        login.setEnabled(true);
                        login.setBackgroundResource(R.drawable.login_button2);
                    }
                } finally {
                    //判断用户名输入框是否为空,为空隐藏删除的图片,不为空的时候,显示删除图片
                    if (TextUtils.isEmpty(name.getText().toString().trim())) {
                        //输入框为空隐藏删除的图片
                        delete_name.setVisibility(View.GONE);
                    } else {
                        //输入框不为空的时候,显示删除图片
                        delete_name.setVisibility(View.VISIBLE);
                    }
                }
                //下面方法也可以判断
                /*if (name.getText().length() > 0) {
                    //输入框不为空的时候,显示删除图片
                    delete_name.setVisibility(View.VISIBLE);

                } else {
                    //输入框为空隐藏删除的图片
                    delete_name.setVisibility(View.GONE);
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (TextUtils.isEmpty(name.getText().toString().trim()) ||
                            TextUtils.isEmpty(pwd.getText().toString().trim())) {
                        login.setEnabled(false);
                        login.setBackgroundResource(R.drawable.login_button1);
                    } else {
                        login.setEnabled(true);
                        login.setBackgroundResource(R.drawable.login_button2);
                    }
                } finally {
                    //判断密码输入框是否为空,为空隐藏删除的图片,不为空的时候,显示删除图片
                    if (TextUtils.isEmpty(pwd.getText().toString().trim())) {
                        //输入框为空隐藏删除的图片
                        delete_pwd.setVisibility(View.GONE);
                    } else {
                        //输入框不为空的时候,显示删除图片
                        delete_pwd.setVisibility(View.VISIBLE);
                    }
                }

                //把try fainnaly 删掉,使用下面的判断方法也可以
                //判断密码输入框是否为空,为空隐藏删除的图片,不为空的时候,显示删除图片
              /*  if (pwd.getText().length() > 0) {
                    //输入框不为空的时候,显示删除图片
                    delete_pwd.setVisibility(View.VISIBLE);
                } else {
                    //输入框为空隐藏删除的图片
                    delete_pwd.setVisibility(View.GONE);
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //判断复选框图片是否选中,如果选中显示密码,否则影藏密码
        show_pwd.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (show_pwd.isChecked()) {
                    pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login://登录按钮
                //如果都为空,登录按钮不能点击
                break;
            case R.id.mobile_register://手机注册
//                Mobile_ByMob_Register();
                break;
            case R.id.forget_pwd://忘记密码

                break;
            case R.id.close://左上角的关闭页面按钮
                finish();
                break;
            case R.id.delete_name://用户名输入框的删除图片
                name.setText("");
                delete_name.setVisibility(View.GONE);
                break;
            case R.id.delete_pwd://密码输入框的删除图片
                pwd.setText("");
                delete_pwd.setVisibility(View.GONE);
                break;
//            case R.id.QQ:
//                UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, authListener);
//                break;
//            case R.id.WeiXin:
//                UMShareAPI.get(MyApplication.AppContext()).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, authListener);
//                break;
//            case R.id.Sina:
//                UMShareAPI.get(MyApplication.AppContext()).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.SINA, authListener);
//                break;
            case R.id.putongzhuce://普通注册
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

//    //通过MOb注册手机
//    private void Mobile_ByMob_Register() {
//        //打开注册页面
//        RegisterPage registerPage = new RegisterPage();
//        registerPage.setRegisterCallback(new EventHandler() {
//            public void afterEvent(int event, int result, Object data) {
//                // 解析注册结果
//                if (result == SMSSDK.RESULT_COMPLETE) {
//                    @SuppressWarnings("unchecked")
//                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
//                    String country = (String) phoneMap.get("country");
//                    String phone = (String) phoneMap.get("phone");
//
//                }
//            }
//        });
//        registerPage.show(LoginActivity.this);
//    }
}
