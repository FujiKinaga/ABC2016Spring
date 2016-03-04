package jp.android_group.student.abc2016spring.domain.usecase;

import java.util.List;

import jp.android_group.student.abc2016spring.domain.model.Bazaar;

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
