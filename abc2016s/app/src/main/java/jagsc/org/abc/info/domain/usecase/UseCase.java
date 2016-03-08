package jagsc.org.abc.info.domain.usecase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kinagafuji on 16/02/23.
 */
public abstract class UseCase {
    private ExecutorService mExecutorService = Executors.newSingleThreadExecutor();

    public void start() {
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                call();
            }
        });
    }

    abstract protected void call();
}
