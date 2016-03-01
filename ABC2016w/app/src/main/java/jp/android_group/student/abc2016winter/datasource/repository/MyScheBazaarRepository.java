package jp.android_group.student.abc2016winter.datasource.repository;

import java.util.List;

import jp.android_group.student.abc2016winter.domain.model.Bazaar;

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
