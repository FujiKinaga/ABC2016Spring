package jagsc.org.abc.info.presenter;

import java.util.List;

import jagsc.org.abc.info.domain.model.Conference;
import jagsc.org.abc.info.domain.usecase.MyScheConferenceUseCase;

/**
 * Created by kinagafuji on 16/02/29.
 */
public class ShowMyScheConferencePresenter extends Presenter implements MyScheConferenceUseCase.MyScheConferenceUseCaseCallback {
    private MyScheConferenceUseCase mUseCase;
    private ShowMyScheConferenceView mView;

    public ShowMyScheConferencePresenter(MyScheConferenceUseCase useCase) {
        mUseCase = useCase;
    }

    public void setMyScheConferenceView(ShowMyScheConferenceView view) {
        mView = view;
    }

    public void getMyScheConferenceData() {
        mView.showLoading();
        mUseCase.execute(this);
    }

    @Override
    public void onSuccess(List<Conference> conferenceList) {
        mView.hideLoading();
        mView.hideNoResultCase();
        mView.showResult(conferenceList);
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

    public interface ShowMyScheConferenceView {
        void showLoading();

        void hideLoading();

        void showNoResultCase();

        void hideNoResultCase();

        void showResult(List<Conference> conferenceList);
    }
}
