package jp.android_group.student.abc2015summer;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyScheduleBazaarFragment extends android.support.v4.app.Fragment {

    public MyScheduleBazaarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_schedule_bazaar, container, false);
    }


}
