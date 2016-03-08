package jagsc.org.abc.info.domain.repository;

import java.util.List;

import jagsc.org.abc.info.domain.model.Bazaar;

/**
 * Created by kinagafuji on 16/02/29.
 */
public interface MyScheBazaarRepository {
    void loadMyScheBazaar(MyScheBazaarRepositoryCallback cb);

    interface MyScheBazaarRepositoryCallback {
        void onSuccess(List<Bazaar> bazaarList);

        void onFailure();
    }
}
