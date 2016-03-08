package jagsc.org.abc.info.domain.executor;

/**
 * Created by kinagafuji on 16/02/23.
 */
public interface PostExecutionThread {
    void post(Runnable runnable);
}
