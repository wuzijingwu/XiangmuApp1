package com.smile.taobaodemo.ui.fragment;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;




public class MyApplication extends Application {
    private static Context context;
    private static OkHttpClient okHttpClient;
    private static String MyIp = null;

//    {
//        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
//        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
//        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        //友盟注册
//        UMShareAPI.get(this);
        x.Ext.init(this);
        context = this;
        MyIp = "169.254.105.18";//本机Ip,后面的接口中使用的IP都是这个,以后如果Ip更改,直接修改这里就可以了

        // Mob短信验证,代码注册你的AppKey和AppSecret
//        MobSDK.init(context, "1ff537dccc1b0", "2831ae8f2322b75878c0637cc140aaea");
        //okHttpClient对象建议只有一个,所以在Application里面创建一个对象,在下面自定义一个方法得到这个对象
        okHttpClient = new OkHttpClient();
        okHttpClient = okHttpClient.newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)//设置联网请求时间,第二个设置参数的类型,SECONDS代表秒
                .readTimeout(5, TimeUnit.SECONDS)//设置读取数据的时间
//                .addInterceptor(new MyInterceptor())
                .build();
        //初始化ImageLoader
        initImageLoader();
        //控制Logger是否答应日志,LogLevel.FULL 开启,LogLevel.NONE 关闭
//        Logger.init("JingDong").logLevel(LogLevel.FULL);
        //腾讯Bugly初始化
//        CrashReport.initCrashReport(getApplicationContext(), "008889c41c", true);
    }

    public class MyInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request build = chain.request().newBuilder().head().build();
            String url = build.url().toString();
//            Logger.e(url);
            Response proceed = chain.proceed(build);
            int code = proceed.code();
//            Logger.d(code);
            return proceed;
        }
    }

    //初始化ImageLoader
    private void initImageLoader() {
        File file = new File(Environment.getDownloadCacheDirectory().getParent() + "/JDImage");
        ImageLoaderConfiguration build = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheExtraOptions(100, 100)
                .threadPoolSize(3)
                .threadPriority(100)
                .diskCache(new UnlimitedDiskCache(file))
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(100 * 1024 * 1024)
                .build();
        ImageLoader.getInstance().init(build);
    }

    public static Context AppContext() {
        return context;
    }

    //得到OkHttpClient对象
    public static OkHttpClient okHttpClient() {
        return okHttpClient;
    }

    //返回本机Ip
    public static String getMyIP() {
        return MyIp;
    }
}
