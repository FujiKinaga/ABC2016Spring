package jp.android_group.student.abc2016winter.domain.usecase;

import java.util.List;

import jp.android_group.student.abc2016winter.datasource.repository.LiveRepository;
import jp.android_group.student.abc2016winter.domain.executor.PostExecutionThread;
import jp.android_group.student.abc2016winter.domain.model.Live;

/**
 * Created by kinagafuji on 16/02/23.
 */
public class LiveUseCaseImpl extends UseCase implements LiveUseCase, LiveRepository.LiveRepositoryCallback {
    private static LiveUseCaseImpl sUseCase;
    private final LiveRepository mRepository;
    private PostExecutionThread mPostExecutionThread;
    private LiveUseCaseCallback mCallback;

    public static LiveUseCaseImpl getUseCase(LiveRepository repository, PostExecutionThread postExecutionThread) {
        if (sUseCase == null) {
            sUseCase = new LiveUseCaseImpl(repository, postExecutionThread);
        }
        return sUseCase;
    }

    public LiveUseCaseImpl(LiveRepository repository, PostExecutionThread postExecutionThread) {
        mRepository = repository;
        mPostExecutionThread = postExecutionThread;
    }

    @Override
    public void onSuccess(final List<Live> liveList) {
        mPostExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                if (mCallback != null) {
                    mCallback.onSuccess(liveList);
                }
            }
        });
    }

    @Override
    public void onFailure() {
        mPostExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                if (mCallback != null) {
                    mCallback.onFailire();
                }
            }
        });
    }

    @Override
    public void execute(LiveUseCaseCallback callback) {
        mCallback = callback;
        this.start();
    }

    @Override
    public void setCallback(LiveUseCaseCallback callback) {
        mCallback = callback;
    }

    @Override
    public void removeCallback() {
        mCallback = null;
    }

    @Override
    protected void call() {
        mRepository.loadLive(this);
    }
}
