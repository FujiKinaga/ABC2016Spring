package jp.android_group.student.abc2016winter.domain.usecase;

import java.util.List;

import jp.android_group.student.abc2016winter.datasource.repository.MyScheBazaarRepository;
import jp.android_group.student.abc2016winter.domain.executor.PostExecutionThread;
import jp.android_group.student.abc2016winter.domain.model.Bazaar;

/**
 * Created by kinagafuji on 16/02/29.
 */
public class MyScheBazaarUseCaseImpl extends UseCase implements MyScheBazaarUseCase, MyScheBazaarRepository.MyScheBazaarRepositoryCallback {
    private static MyScheBazaarUseCaseImpl sUseCase;
    private final MyScheBazaarRepository mRepository;
    private PostExecutionThread mPostExecutionThread;
    private MyScheBazaarUseCaseCallback mCallback;

    public static MyScheBazaarUseCaseImpl getUseCase(MyScheBazaarRepository repository, PostExecutionThread postExecutionThread) {
        if (sUseCase == null) {
            sUseCase = new MyScheBazaarUseCaseImpl(repository, postExecutionThread);
        }
        return sUseCase;
    }

    public MyScheBazaarUseCaseImpl(MyScheBazaarRepository repository, PostExecutionThread postExecutionThread) {
        mRepository = repository;
        mPostExecutionThread = postExecutionThread;
    }

    @Override
    public void onSuccess(final List<Bazaar> bazaarList) {
        mPostExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                if (mCallback != null) {
                    mCallback.onSuccess(bazaarList);
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
                    mCallback.onFailure();
                }
            }
        });
    }

    @Override
    public void execute(MyScheBazaarUseCaseCallback callback) {
        mCallback = callback;
        this.start();
    }

    @Override
    public void setCallback(MyScheBazaarUseCaseCallback callback) {
        mCallback = callback;
    }

    @Override
    public void removeCallback() {
        mCallback = null;
    }

    @Override
    protected void call() {
        mRepository.loadMyScheBazaar(this);
    }
}
