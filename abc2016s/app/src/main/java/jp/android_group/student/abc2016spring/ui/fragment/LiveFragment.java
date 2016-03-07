package jp.android_group.student.abc2016spring.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import jp.android_group.student.abc2016spring.R;
import jp.android_group.student.abc2016spring.datasource.repository.LiveRepositoryImpl;
import jp.android_group.student.abc2016spring.domain.executor.UIThread;
import jp.android_group.student.abc2016spring.domain.model.Live;
import jp.android_group.student.abc2016spring.domain.repository.LiveRepository;
import jp.android_group.student.abc2016spring.domain.usecase.LiveUseCase;
import jp.android_group.student.abc2016spring.domain.usecase.LiveUseCaseImpl;
import jp.android_group.student.abc2016spring.presenter.ShowLivePresenter;

/**
 * Created by kinagafuji on 16/02/27.
 */
public class LiveFragment extends Fragment implements ShowLivePresenter.ShowLiveView {

    private ShowLivePresenter mLivePresenter;

    public LiveFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //mSectionNum = getArguments().getInt(ARG_SECTION_NUMBER);
        View rootView = inflater.inflate(R.layout.fragment_time_table, container, false);

        LiveRepository liveRepository = LiveRepositoryImpl.getRepository();
        LiveUseCase liveUseCase = LiveUseCaseImpl.getUseCase(liveRepository, UIThread.getInstance());
        mLivePresenter = new ShowLivePresenter(liveUseCase);
        mLivePresenter.setLiveView(this);
        mLivePresenter.getLiveData();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mLivePresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mLivePresenter.pause();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showNoResultCase() {

    }

    @Override
    public void hideNoResultCase() {

    }

    @Override
    public void showResult(List<Live> liveList) {

    }
}
