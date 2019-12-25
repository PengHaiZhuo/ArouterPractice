package com.phz.shop;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
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
@Route(path = Constant.ROUTER_PATH_SHOP)
public class ShopActivity extends AppCompatActivity {

    @BindView(R2.id.tv_shop)
    TextView tvShop;
    @BindView(R2.id.iv_shop)
    ImageView ivShop;
    @BindView(R2.id.bt_shop)
    Button btShop;

    Unbinder unbinder;

    @Autowired
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_activiy);
        unbinder = ButterKnife.bind(this);

        //inject来注入@Autowired注解的字段
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    @OnClick(R2.id.bt_shop)
    public void onViewClicked() {
        if (user!=null){
            tvShop.setText(user.getName()+"|"+user.getPhone());
        }else {
            tvShop.setText("17张牌你能秒我？");
        }

    }
}
