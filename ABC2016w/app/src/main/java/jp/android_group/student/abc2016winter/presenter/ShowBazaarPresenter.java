package jp.android_group.student.abc2016winter.presenter;

import java.util.List;

import jp.android_group.student.abc2016winter.domain.model.Bazaar;
import jp.android_group.student.abc2016winter.domain.usecase.BazaarUseCase;

/**
 * Created by kinagafuji on 16/02/24.
 */
public class ShowBazaarPresenter extends Presenter implements BazaarUseCase.BazaarUseCaseCallback {
    private BazaarUseCase mUseCase;
    private ShowBazaarView mView;

    public ShowBazaarPresenter(BazaarUseCase useCase) {
        mUseCase = useCase;
    }

    public void setBazaarView(ShowBazaarView view) {
        mView = view;
    }

    public void getBazaarData() {
        mView.showLoading();
        mUseCase.execute(this);
    }

    @Override
    public void onSuccess(List<Bazaar> bazaarList) {
        mView.hideLoading();
        mView.hideNoResultCase();
        mView.showResult(bazaarList);
    }

    @Override
    public void onFailure() {
        mView.hideLoading();
        mView.showNoResultCase();
    }

    @Override
    public void initialize() {

    }

    @Override
    public void resume() {
        mUseCase.setCallback(this);
    }

    @Override
    public void pause() {
        mUseCase.removeCallback();
    }

    @Override
    public void destroy() {

    }

    public interface ShowBazaarView {
        void showLoading();

        void hideLoading();

        void showNoResultCase();

        void hideNoResultCase();

        void showResult(List<Bazaar> bazaarList);
    }
}
