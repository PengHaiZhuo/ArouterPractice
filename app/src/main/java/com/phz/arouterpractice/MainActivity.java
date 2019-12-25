package com.phz.arouterpractice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.phz.base.bean.User;
import com.phz.base.config.Constant;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author haizhuo
 */
@Route(path = Constant.ROUTER_PATH_MAIN)
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_main)
    TextView tvMain;
    @BindView(R.id.iv_main)
    ImageView ivMain;
    @BindView(R.id.bt_to_shop)
    Button btToShop;
    @BindView(R.id.bt_to_setting)
    Button button;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder=ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
    }

    @OnClick({R.id.bt_to_shop, R.id.bt_to_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_to_shop:
                //跳转并携带参数,带跳转结果处理
                User user=new User("明凯","1377777777","777777");
                ARouter.getInstance().build(Constant.ROUTER_PATH_SHOP).withParcelable("user",user).navigation(this, new NavigationCallback() {
                    @Override
                    public void onFound(Postcard postcard) {

                    }

                    @Override
                    public void onLost(Postcard postcard) {

                    }

                    @Override
                    public void onArrival(Postcard postcard) {

                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {

                    }
                });
                break;
            case R.id.bt_to_setting:
                //简单的路由跳转
                ARouter.getInstance().build(Constant.ROUTER_PATH_SETTING).navigation();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
