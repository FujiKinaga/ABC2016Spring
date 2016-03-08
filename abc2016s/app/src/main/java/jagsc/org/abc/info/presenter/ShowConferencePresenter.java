package jagsc.org.abc.info.presenter;

import android.support.v4.util.SparseArrayCompat;

import java.util.ArrayList;

import jagsc.org.abc.info.domain.model.Conference;
import jagsc.org.abc.info.domain.usecase.ConferenceUseCase;

/**
 * Created by kinagafuji on 16/02/24.
 */
public class ShowConferencePresenter extends Presenter implements ConferenceUseCase.ConferenceUseCaseCallback {
    private ConferenceUseCase mUseCase;
    private ShowConferenceView mView;

    public ShowConferencePresenter(ConferenceUseCase useCase) {
        mUseCase = useCase;
    }

    public void setConferenceView(ShowConferenceView view) {
        mView = view;
    }

    public void getConferenceData() {
        mView.showLoading();
        mUseCase.execute(this);
    }

    @Override
    public void onSuccess(SparseArrayCompat<ArrayList<Conference>> conferenceList) {
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

    public interface ShowConferenceView {
        void showLoading();

        void hideLoading();

        void showNoResultCase();

        void hideNoResultCase();

        void showResult(SparseArrayCompat<ArrayList<Conference>> conferenceList);
    }
}
