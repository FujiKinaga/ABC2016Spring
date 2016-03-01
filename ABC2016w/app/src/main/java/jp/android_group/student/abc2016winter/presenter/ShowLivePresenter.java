package jp.android_group.student.abc2016winter.presenter;

import java.util.List;

import jp.android_group.student.abc2016winter.domain.model.Live;
import jp.android_group.student.abc2016winter.domain.usecase.LiveUseCase;

/**
 * Created by kinagafuji on 16/02/24.
 */
public class ShowLivePresenter extends Presenter implements LiveUseCase.LiveUseCaseCallback {
    private LiveUseCase mUseCase;
    private ShowLiveView mView;

    public ShowLivePresenter(LiveUseCase useCase) {
        mUseCase = useCase;
    }

    public void setLiveView(ShowLiveView view) {
        mView = view;
    }

    public void getLiveData() {
        mView.showLoading();
        mUseCase.execute(this);
    }

    @Override
    public void onSuccess(List<Live> liveList) {
        mView.hideLoading();
        mView.hideNoResultCase();
        mView.showResult(liveList);
    }

    @Override
    public void onFailire() {
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

    public interface ShowLiveView {
        void showLoading();

        void hideLoading();

        void showNoResultCase();

        void hideNoResultCase();

        void showResult(List<Live> liveList);
    }
}
