package jp.android_group.student.abc2016spring;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

/**
 * Created by kinagafuji on 16/03/01.
 */
public class Util {

    public enum NetworkStatus {
        OFF,
        MOBILE,
        WIFI,
        WIMAX,
        LTE,
    }

    public static NetworkStatus getConnectedState(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkStatus status = NetworkStatus.OFF;

        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null || !info.isConnected()) {
            // OFF
            return status;
        }

        switch (info.getType()) {
            case ConnectivityManager.TYPE_WIFI:         // Wifi
                status = NetworkStatus.WIFI;
                break;
            case ConnectivityManager.TYPE_MOBILE_DUN:   // Mobile 3G
            case ConnectivityManager.TYPE_MOBILE_HIPRI:
            case ConnectivityManager.TYPE_MOBILE_MMS:
            case ConnectivityManager.TYPE_MOBILE_SUPL:
            case ConnectivityManager.TYPE_MOBILE:
                switch (info.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        status = NetworkStatus.LTE;         // LTE
                        break;
                    default:
                        status = NetworkStatus.MOBILE;      // Mobile 3G
                        break;
                }
                break;
            case ConnectivityManager.TYPE_WIMAX:        // Wimax
                status = NetworkStatus.WIMAX;
                break;
        }
        return status;
    }

    public static int getScreenHeightInPx(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (displayMetrics.heightPixels);
    }
}
