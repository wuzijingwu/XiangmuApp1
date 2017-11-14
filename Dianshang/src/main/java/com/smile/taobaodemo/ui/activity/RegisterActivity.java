package com.smile.taobaodemo.ui.activity;

/**
 * Created by dell on 2017/11/13.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.smile.taobaodemo.R;
import com.smile.taobaodemo.presenter.Register_Presenter;
import com.smile.taobaodemo.dao.MyApplication;
import com.smile.taobaodemo.view.Register_View;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, Register_View {

    private EditText username;
    private EditText userpwd1;
    private EditText userpwd2;
    private EditText useremail;
    private String path = "http://" + MyApplication.getMyIP() + "/mobile/index.php?act=login&op=register";
    private Register_Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        ImageView back = (ImageView) findViewById(R.id.zhucegouback);
        username = (EditText) findViewById(R.id.username);
        userpwd1 = (EditText) findViewById(R.id.userpwd1);
        userpwd2 = (EditText) findViewById(R.id.userpwd2);
        useremail = (EditText) findViewById(R.id.useremail);
        Button register = (Button) findViewById(R.id.register);
        presenter = new Register_Presenter(this);
        back.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zhucegouback://返回
                finish();
                break;
            case R.id.register:
                if (TextUtils.isEmpty(username.getText().toString().trim())) {
                    Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(userpwd1.getText().toString().trim())) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(userpwd2.getText().toString().trim())) {
                    Toast.makeText(this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(useremail.getText().toString().trim())) {
                    Toast.makeText(this, "请输入邮箱", Toast.LENGTH_SHORT).show();
                } else if (!TextUtils.equals(userpwd1.getText().toString().trim(), userpwd2.getText().toString().trim())) {
                    Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                } else if (!useremail.getText().toString().trim().contains("@")) {
                    Toast.makeText(this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
                } else {
                    String name = username.getText().toString().trim();
                    String pwd1 = userpwd1.getText().toString().trim();
                    String pwd2 = userpwd2.getText().toString().trim();
                    String eamil = useremail.getText().toString().trim();
                    String registurl = path + "&username=" + name + "&password=" + pwd1 + "&password_confirm=" + pwd2 + "&email=" + eamil + "&client=android";
                    //请求数据
                    presenter.getData(registurl);
                    Toast.makeText(this, "正在注册", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onSuccedData(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Logger.json(result);
                Logger.e(result);
            }
        });
    }

    @Override
    public void onFiledData(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Logger.e(result);
            }
        });
    }

    @Override
    public Context context() {
        return MyApplication.AppContext();
    }
}
