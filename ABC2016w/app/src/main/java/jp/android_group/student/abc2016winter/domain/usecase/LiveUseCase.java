package jp.android_group.student.abc2016winter.domain.usecase;

import java.util.List;

import jp.android_group.student.abc2016winter.domain.model.Live;

/**
 * Created by kinagafuji on 16/02/23.
 */
public interface LiveUseCase {
    interface LiveUseCaseCallback {
        void onSuccess(List<Live> liveList);

        void onFailire();
    }

    void execute(LiveUseCaseCallback callback);

    void setCallback(LiveUseCaseCallback callback);

    void removeCallback();
}
