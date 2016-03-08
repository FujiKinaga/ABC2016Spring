package jagsc.org.abc.info.domain.usecase;

import java.util.List;

import jagsc.org.abc.info.domain.model.Live;

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
