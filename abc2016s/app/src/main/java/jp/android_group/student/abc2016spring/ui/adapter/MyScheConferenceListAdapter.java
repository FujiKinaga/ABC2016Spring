package jp.android_group.student.abc2016spring.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andexert.library.RippleView;

import java.util.ArrayList;
import java.util.List;

import jp.android_group.student.abc2016spring.R;
import jp.android_group.student.abc2016spring.consts.Const;
import jp.android_group.student.abc2016spring.domain.model.Conference;

/**
 * Created by kinagafuji on 16/02/29.
 */
public class MyScheConferenceListAdapter extends RecyclerView.Adapter<Const.MyScheConferenceViewHolder> {
    private Context mContext;

    private List<Conference> mData = new ArrayList<>();

    private MyScheConferenceCallback mCallback;

    public MyScheConferenceListAdapter(Context context, List<Conference> data) {
        mContext = context;
        mData = data;
    }

    public void setMyScheConferenceCallback(MyScheConferenceCallback callback) {
        mCallback = callback;
    }

    public void setData() {
        this.notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return mData.isEmpty();
    }

    @Override
    public Const.MyScheConferenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.conference_my_schedule_list_item, parent, false);
        return new Const.MyScheConferenceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final Const.MyScheConferenceViewHolder holder, final int position) {
        final Conference conference = mData.get(holder.getAdapterPosition());

        holder.mTime.setText(conference.getStartTime().substring(11, 16) + " - " + conference.getEndTime().substring(11, 16));
        holder.mTitle.setText(conference.getTitle());

        StringBuilder speakerNames = new StringBuilder(conference.getSpeaker().getName().get(0));
        for (int i = 1; i < conference.getSpeaker().getName().size(); i++) {
            speakerNames.append(" / ").append(conference.getSpeaker().getName().get(i));
        }
        holder.mSpeaker.setText(speakerNames.toString());

        switch (conference.getRoom_id()) {
            case 1:
                holder.mRoom.setText(R.string.room_1);
                break;
            case 2:
                holder.mRoom.setText(R.string.room_2);
                break;
            case 3:
                holder.mRoom.setText(R.string.room_3);
                break;
            case 4:
                holder.mRoom.setText(R.string.room_4);
                break;
            case 5:
                holder.mRoom.setText(R.string.room_5);
                break;
            case 6:
                holder.mRoom.setText(R.string.room_6);
                break;
            case 7:
                holder.mRoom.setText(R.string.room_7);
                break;
            case 8:
                holder.mRoom.setText(R.string.room_8);
                break;
        }

        holder.mCellRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                mCallback.onCellClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface MyScheConferenceCallback {
        void onCellClick(int position);
    }
}
