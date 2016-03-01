package jp.android_group.student.abc2016winter.presenter;

import android.support.v4.util.SparseArrayCompat;

import java.util.ArrayList;

import jp.android_group.student.abc2016winter.domain.model.Conference;
import jp.android_group.student.abc2016winter.domain.usecase.TimeTableUseCase;

/**
 * Created by kinagafuji on 16/02/28.
 */
public class ShowTimeTablePresenter extends Presenter implements TimeTableUseCase.TimeTableUseCaseCallback {
    private TimeTableUseCase mUseCase;
    private ShowTimeTableView mView;

    public ShowTimeTablePresenter(TimeTableUseCase useCase) {
        mUseCase = useCase;
    }

    public void setTimeTableView(ShowTimeTableView view) {
        mView = view;
    }

    public void getTimeTableData() {
        mView.showLoading();
        mUseCase.execute(this);
    }

    @Override
    public void onSuccess(SparseArrayCompat<ArrayList<Conference>> timeTableList) {
        mView.hideLoading();
        mView.hideNoResultCase();
        mView.showResult(timeTableList);
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

    public interface ShowTimeTableView {
        void showLoading();

        void hideLoading();

        void showNoResultCase();

        void hideNoResultCase();

        void showResult(SparseArrayCompat<ArrayList<Conference>> timeTableList);
    }
}
