package jp.android_group.student.abc2016winter.domain.usecase;

import android.support.v4.util.SparseArrayCompat;

import java.util.ArrayList;

import jp.android_group.student.abc2016winter.domain.repository.ConferenceRepository;
import jp.android_group.student.abc2016winter.domain.executor.PostExecutionThread;
import jp.android_group.student.abc2016winter.domain.model.Conference;

/**
 * Created by kinagafuji on 16/02/23.
 */
public class ConferenceUseCaseImpl extends UseCase implements ConferenceUseCase, ConferenceRepository.ConferenceRepositoryCallback {
    private static ConferenceUseCaseImpl sUseCase;
    private final ConferenceRepository mRepository;
    private PostExecutionThread mPostExecutionThread;
    private ConferenceUseCaseCallback mCallback;

    public static ConferenceUseCaseImpl getUseCase(ConferenceRepository repository, PostExecutionThread postExecutionThread) {
        if (sUseCase == null) {
            sUseCase = new ConferenceUseCaseImpl(repository, postExecutionThread);
        }
        return sUseCase;
    }

    public ConferenceUseCaseImpl(ConferenceRepository repository, PostExecutionThread postExecutionThread) {
        mRepository = repository;
        mPostExecutionThread = postExecutionThread;
    }

    @Override
    public void onSuccess(final SparseArrayCompat<ArrayList<Conference>> conferenceList) {
        mPostExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                if (mCallback != null) {
                    mCallback.onSuccess(conferenceList);
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
    public void execute(ConferenceUseCaseCallback callback) {
        mCallback = callback;
        this.start();
    }

    @Override
    public void setCallback(ConferenceUseCaseCallback callback) {
        mCallback = callback;
    }

    @Override
    public void removeCallback() {
        mCallback = null;
    }

    @Override
    protected void call() {
        mRepository.loadConference(this);
    }
}
