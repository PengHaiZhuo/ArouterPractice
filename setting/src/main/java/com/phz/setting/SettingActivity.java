package com.phz.setting;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.phz.base.config.Constant;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author haizhuo
 */
@Route(path = Constant.ROUTER_PATH_SETTING)
public class SettingActivity extends AppCompatActivity {

    @BindView(R2.id.tv_setting)
    TextView tvSetting;
    @BindView(R2.id.iv_setting)
    ImageView ivSetting;
    @BindView(R2.id.bt_setting)
    Button btSetting;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        unbinder= ButterKnife.bind(this);
    }

    @OnClick(R2.id.bt_setting)
    public void onViewClicked() {
        tvSetting.setText("你再骂？那你去物管啊！");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
