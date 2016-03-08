package jagsc.org.abc.info.domain.repository;

import java.util.List;

import jagsc.org.abc.info.domain.model.Bazaar;

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
