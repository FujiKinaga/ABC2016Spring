package jagsc.org.abc.info.domain.usecase;

import java.util.List;

import jagsc.org.abc.info.domain.model.Bazaar;

/**
 * Created by kinagafuji on 16/02/29.
 */
public interface MyScheBazaarUseCase {
    interface MyScheBazaarUseCaseCallback {
        void onSuccess(List<Bazaar> bazaarList);

        void onFailure();
    }

    void execute(MyScheBazaarUseCaseCallback callback);

    void setCallback(MyScheBazaarUseCaseCallback callback);

    void removeCallback();
}
