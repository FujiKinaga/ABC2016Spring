package jagsc.org.abc.info.domain.usecase;

import android.support.v4.util.SparseArrayCompat;

import java.util.ArrayList;

import jagsc.org.abc.info.domain.executor.PostExecutionThread;
import jagsc.org.abc.info.domain.model.Conference;
import jagsc.org.abc.info.domain.repository.TimeTableRepository;

/**
 * Created by kinagafuji on 16/02/28.
 */
public class TimeTableUseCaseImpl extends UseCase implements TimeTableUseCase, TimeTableRepository.TimeTableRepositoryCallback {
    private static TimeTableUseCaseImpl sUseCase;
    private final TimeTableRepository mRepository;
    private PostExecutionThread mPostExecutionThread;
    private TimeTableUseCaseCallback mCallback;

    public static TimeTableUseCaseImpl getUseCase(TimeTableRepository repository, PostExecutionThread postExecutionThread) {
        if (sUseCase == null) {
            sUseCase = new TimeTableUseCaseImpl(repository, postExecutionThread);
        }
        return sUseCase;
    }

    public TimeTableUseCaseImpl(TimeTableRepository repository, PostExecutionThread postExecutionThread) {
        mRepository = repository;
        mPostExecutionThread = postExecutionThread;
    }

    @Override
    public void onSuccess(final SparseArrayCompat<ArrayList<Conference>> timeTableList) {
        mPostExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                if (mCallback != null) {
                    mCallback.onSuccess(timeTableList);
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
    public void execute(TimeTableUseCaseCallback callback) {
        mCallback = callback;
        this.start();
    }

    @Override
    public void setCallback(TimeTableUseCaseCallback callback) {
        mCallback = callback;
    }

    @Override
    public void removeCallback() {
        mCallback = null;
    }

    @Override
    protected void call() {
        mRepository.loadTimeTable(this);
    }
}
