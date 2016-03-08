package jagsc.org.abc.info.domain.usecase;

import java.util.List;

import jagsc.org.abc.info.domain.model.Bazaar;

/**
 * Created by kinagafuji on 16/02/23.
 */
public interface BazaarUseCase {
    interface BazaarUseCaseCallback {
        void onSuccess(List<Bazaar> bazaarList);

        void onFailure();
    }

    void execute(BazaarUseCaseCallback callback);

    void setCallback(BazaarUseCaseCallback callback);

    void removeCallback();
}
