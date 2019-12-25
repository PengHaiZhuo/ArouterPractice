### 组件化框架Arouter

#### project层级下，新建config.gradle

```
ext {
    //true 每个业务Module可以单独开发
    //false 每个业务Module以lib的方式运行
    //修改之后需要Sync方可生效
    isModule = false
}
```

#### 在project层级build.gradle添加刚刚增加的config文件依赖

> apply from: "config.gradle"

#### 新建一个library的module，姑且叫作module_base
在library的gradle下添加Arouter依赖

```
api 'com.alibaba:arouter-api:1.5.0'
annotationProcessor'com.alibaba:arouter-compiler:1.2.2'
```

并且要指定一下javaSDKVersion

```
android{
    ...
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
```
不然使用包的时候会报错
> Error: Static interface methods are only supported starting with Android N (--min-api 24)


添加Arouter路由配置

```
android{
    ...
    defaultConfig{
        ...
        //Arouter路由配置
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = [AROUTER_MODULE_NAME: project.getName()]
                includeCompileClasspath = true
            }
        }
    }
}
```
注意：需要使用Arouter跳转的module都要添加路由配置以及依赖annotationProcessor，这点和butterknife挺像的。

添加一个配置文件config,写上一些常量

```
/**
 * @author haizhuo
 * @introduction 配置
 */
public class Constant {
    /**
     * 是否使用拦截器
     */
    public static final boolean IS_USE_INTERCEPTOR=true;

    /**
     * 主页面路径标签
     */
    public static final String ROUTER_PATH_MAIN="/app/MainActivity";

    /**
     * 商店路径标签
     */
    public static final String ROUTER_PATH_SHOP="/shop/ShopActivity";

    /**
     * 商店路径标签
     */
    public static final String ROUTER_PATH_SETTING="/setting/SettingActivity";
}
```



#### 新建application的module，在main目录下添加module文件夹，并把AndroidManifest复制进去，接下来只需要修改AndroidManifest和gradle文件
gradle文件需要修改的地方
```
if (Boolean.valueOf(rootProject.ext.isModule)) {
    apply plugin: 'com.android.application'
} else {
    apply plugin: 'com.android.library'
}

android {
   ...
    defaultConfig{
        ...
        //Arouter路由配置
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = [AROUTER_MODULE_NAME: project.getName()]
                includeCompileClasspath = true
            }
        }
    }
    
    //通过修改SourceSets中的属性，可以指定哪些源文件要被编译，那些源文件要被排除
    sourceSets {
        main {
            if (Boolean.valueOf(rootProject.ext.isModule)) {
                manifest.srcFile 'src/main/module/AndroidManifest.xml'
            } else {
                manifest.srcFile 'src/main/AndroidManifest.xml'
            }
        }
    }
    
    
}

dependencies {
    ...
    //依赖module_base
    implementation project(':module_base')
    annotationProcessor'com.alibaba:arouter-compiler:1.2.2'
}
```

#### 接下来开始初始化Arouter以及配置路径
app下新建自定义工程类MyApplication

```
/**
 * @author haizhuo
 * @introduction 自定义工程类
 */
public class MyApplication extends Application {

    private static volatile MyApplication myApplication;

    /**
     * 获取application实例
     * @return
     */
    public static MyApplication getInstance(){
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (BuildConfig.DEBUG) {
            // 打印日志
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
            // 打印日志的时候打印线程堆栈
            ARouter.printStackTrace();
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(myApplication);
    }

    /**
     * 注：onTerminate只会在模拟器上调用，商用设备上不会执行
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
```

app的发射界面MainActivity

```
/**
 * @author haizhuo
 */
@Route(path = Constant.ROUTER_PATH_MAIN)
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ARouter.getInstance().inject(this);
    }
}

```

#### 准备得差不多了，可以开始跳转了，比如跳转到商店模块的ShopActivity

```
/**
 * @author haizhuo
 */
@Route(path = Constant.ROUTER_PATH_SHOP)
public class ShopActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_activiy);
    }

}

```

在MainActivity中通过点击按钮跳转

```
//简单的路由跳转
ARouter.getInstance().build(Constant.ROUTER_PATH_SHOP).navigation();
```

大致就这样吧，如果你还要传递参数，添加拦截器等，具体可以参考文档
[https://github.com/alibaba/ARouter/blob/master/README_CN.md](https://github.com/alibaba/ARouter/blob/master/README_CN.md)
