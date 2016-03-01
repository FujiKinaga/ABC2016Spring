package jp.android_group.student.abc2016winter.presenter;

import java.util.List;

import jp.android_group.student.abc2016winter.domain.model.Bazaar;
import jp.android_group.student.abc2016winter.domain.usecase.MyScheBazaarUseCase;

/**
 * Created by kinagafuji on 16/02/29.
 */
public class ShowMyScheBazaarPresenter extends Presenter implements MyScheBazaarUseCase.MyScheBazaarUseCaseCallback {
    private MyScheBazaarUseCase mUseCase;
    private ShowMyScheBazaarView mView;

    public ShowMyScheBazaarPresenter(MyScheBazaarUseCase useCase) {
        mUseCase = useCase;
    }

    public void setMyScheBazaarView(ShowMyScheBazaarView view) {
        mView = view;
    }

    public void getMyScheBazaarData() {
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

    public interface ShowMyScheBazaarView {
        void showLoading();

        void hideLoading();

        void showNoResultCase();

        void hideNoResultCase();

        void showResult(List<Bazaar> bazaarList);
    }
}
