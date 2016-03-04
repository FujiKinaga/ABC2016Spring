package jp.android_group.student.abc2016spring;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by kinagafuji on 16/02/28.
 */
public class App extends Application {

    private static App sInstance;

    public App() {
        super();
    }

    public static synchronized App getInstance() {
        return sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        sInstance = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
