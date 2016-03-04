package jp.android_group.student.abc2016spring.domain.repository;

import java.util.List;

import jp.android_group.student.abc2016spring.domain.model.Bazaar;

/**
 * Created by kinagafuji on 16/02/23.
 */
public interface BazaarRepository {
    void loadBazaar(BazaarRepositoryCallback cb);

    interface BazaarRepositoryCallback {
        void onSuccess(List<Bazaar> bazaarList);

        void onFailure();
    }
}
