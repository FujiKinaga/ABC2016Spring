package jagsc.org.abc.info;

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
//
//    private static final String PROPERTY_ID = "UA-63362687-2";
//
//    public static GoogleAnalytics analytics;
//    public static Tracker tracker;

    public App() {
        super();
    }
//
//    synchronized public Tracker getDefaultTracker() {
//        if (tracker == null) {
//            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
//            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
//            tracker = analytics.newTracker(R.xml.global_tracker);
//        }
//        return tracker;
//    }

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
//
//        analytics = GoogleAnalytics.getInstance(this);
//        analytics.setLocalDispatchPeriod(1800);
//
//        tracker = analytics.newTracker(PROPERTY_ID); // Replace with actual tracker/property Id
//        tracker.enableExceptionReporting(true);
//        tracker.enableAdvertisingIdCollection(false);
//        tracker.enableAutoActivityTracking(true);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
