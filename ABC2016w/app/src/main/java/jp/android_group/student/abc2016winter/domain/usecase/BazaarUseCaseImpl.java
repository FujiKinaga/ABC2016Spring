package jp.android_group.student.abc2016winter.domain.usecase;

import java.util.List;

import jp.android_group.student.abc2016winter.datasource.repository.BazaarRepository;
import jp.android_group.student.abc2016winter.domain.executor.PostExecutionThread;
import jp.android_group.student.abc2016winter.domain.model.Bazaar;

/**
 * Created by kinagafuji on 16/02/23.
 */
public class BazaarUseCaseImpl extends UseCase implements BazaarUseCase, BazaarRepository.BazaarRepositoryCallback {
    private static BazaarUseCaseImpl sUseCase;
    private final BazaarRepository mRepository;
    private PostExecutionThread mPostExecutionThread;
    private BazaarUseCaseCallback mCallback;

    public static BazaarUseCaseImpl getUseCase(BazaarRepository repository, PostExecutionThread postExecutionThread) {
        if (sUseCase == null) {
            sUseCase = new BazaarUseCaseImpl(repository, postExecutionThread);
        }
        return sUseCase;
    }

    public BazaarUseCaseImpl(BazaarRepository repository, PostExecutionThread postExecutionThread) {
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
    public void execute(BazaarUseCaseCallback callback) {
        mCallback = callback;
        this.start();
    }

    @Override
    public void setCallback(BazaarUseCaseCallback callback) {
        mCallback = callback;
    }

    @Override
    public void removeCallback() {
        mCallback = null;
    }

    @Override
    protected void call() {
        mRepository.loadBazaar(this);
    }
}
