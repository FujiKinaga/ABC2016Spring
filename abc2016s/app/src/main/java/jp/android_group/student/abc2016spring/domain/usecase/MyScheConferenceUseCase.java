package jp.android_group.student.abc2016spring.domain.usecase;

import java.util.List;

import jp.android_group.student.abc2016spring.domain.model.Conference;

/**
 * Created by kinagafuji on 16/02/29.
 */
public interface MyScheConferenceUseCase {
    interface MyScheConferenceUseCaseCallback {
        void onSuccess(List<Conference> conferenceList);

        void onFailire();
    }

    void execute(MyScheConferenceUseCaseCallback callback);

    void setCallback(MyScheConferenceUseCaseCallback callback);

    void removeCallback();
}
