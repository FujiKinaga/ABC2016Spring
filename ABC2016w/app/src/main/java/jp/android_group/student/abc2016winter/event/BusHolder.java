package jp.android_group.student.abc2016winter.event;

/**
 * Created by kinagafuji on 16/03/01.
 */
public class BusHolder {
    private static MainThreadBus sBus = new MainThreadBus();

    public static MainThreadBus get() {
        return sBus;
    }
}
