package jagsc.org.abc.info.event;

/**
 * Created by kinagafuji on 16/03/01.
 */
public class BusHolder {
    private static MainThreadBus sBus = new MainThreadBus();

    public static MainThreadBus get() {
        return sBus;
    }
}
