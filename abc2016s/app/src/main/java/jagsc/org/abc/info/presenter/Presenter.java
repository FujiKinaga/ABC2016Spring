package jagsc.org.abc.info.presenter;

/**
 * Created by kinagafuji on 16/02/24.
 */
public abstract class Presenter {
    public abstract void initialize();

    public abstract void resume();

    public abstract void pause();

    public abstract void destroy();
}
