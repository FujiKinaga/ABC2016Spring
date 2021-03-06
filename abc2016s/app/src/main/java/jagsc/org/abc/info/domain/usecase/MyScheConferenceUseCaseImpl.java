package jagsc.org.abc.info.domain.usecase;

import java.util.List;

import jagsc.org.abc.info.domain.executor.PostExecutionThread;
import jagsc.org.abc.info.domain.model.Conference;
import jagsc.org.abc.info.domain.repository.MyScheConferenceRepository;

/**
 * Created by kinagafuji on 16/02/29.
 */
public class MyScheConferenceUseCaseImpl extends UseCase implements MyScheConferenceUseCase, MyScheConferenceRepository.MyScheConferenceRepositoryCallback {
    private static MyScheConferenceUseCaseImpl sUseCase;
    private final MyScheConferenceRepository mRepository;
    private PostExecutionThread mPostExecutionThread;
    private MyScheConferenceUseCaseCallback mCallback;

    public static MyScheConferenceUseCaseImpl getUseCase(MyScheConferenceRepository repository, PostExecutionThread postExecutionThread) {
        if (sUseCase == null) {
            sUseCase = new MyScheConferenceUseCaseImpl(repository, postExecutionThread);
        }
        return sUseCase;
    }

    public MyScheConferenceUseCaseImpl(MyScheConferenceRepository repository, PostExecutionThread postExecutionThread) {
        mRepository = repository;
        mPostExecutionThread = postExecutionThread;
    }

    @Override
    public void onSuccess(final List<Conference> conferenceList) {
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
    public void execute(MyScheConferenceUseCaseCallback callback) {
        mCallback = callback;
        this.start();
    }

    @Override
    public void setCallback(MyScheConferenceUseCaseCallback callback) {
        mCallback = callback;
    }

    @Override
    public void removeCallback() {
        mCallback = null;
    }

    @Override
    protected void call() {
        mRepository.loadMyScheConference(this);
    }
}
