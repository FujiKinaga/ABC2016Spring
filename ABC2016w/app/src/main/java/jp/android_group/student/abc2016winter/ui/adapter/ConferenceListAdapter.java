package jp.android_group.student.abc2016winter.ui.adapter;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andexert.library.RippleView;

import java.util.ArrayList;

import jp.android_group.student.abc2016winter.R;
import jp.android_group.student.abc2016winter.consts.Const;
import jp.android_group.student.abc2016winter.domain.model.Conference;

/**
 * Created by kinagafuji on 16/02/28.
 */
public class ConferenceListAdapter extends RecyclerView.Adapter<Const.ConferenceViewHolder> {

    private Context mContext;

    private SparseArrayCompat<ArrayList<Conference>> mData = new SparseArrayCompat<>();

    private ConferenceCallback mCallback;

    public ConferenceListAdapter(Context context, SparseArrayCompat<ArrayList<Conference>> data) {
        mContext = context;
        mData = data;
    }

    public void setConferenceCallback(ConferenceCallback callback) {
        mCallback = callback;
    }

    public void setData() {
        this.notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return mData.size() == 0;
    }

    @Override
    public Const.ConferenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.conference_list_item, parent, false);
        return new Const.ConferenceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final Const.ConferenceViewHolder holder, final int position) {
        switch (mData.keyAt(position)) {
            case 1:
                holder.mCategory.setText(mContext.getString(R.string.category_1));
                break;
            case 2:
                holder.mCategory.setText(mContext.getString(R.string.category_2));
                break;
            case 3:
                holder.mCategory.setText(mContext.getString(R.string.category_3));
                break;
            case 4:
                holder.mCategory.setText(mContext.getString(R.string.category_4));
                break;
            case 5:
                holder.mCategory.setText(mContext.getString(R.string.category_5));
                break;
            case 6:
                holder.mCategory.setText(mContext.getString(R.string.category_6));
                break;
            case 7:
                holder.mCategory.setText(mContext.getString(R.string.category_7));
                break;
            case 8:
                holder.mCategory.setText(mContext.getString(R.string.category_8));
                break;
            case 9:
                holder.mCategory.setText(mContext.getString(R.string.category_9));
                break;
            case 10:
                holder.mCategory.setText(mContext.getString(R.string.category_10));
                break;
            case 11:
                holder.mCategory.setText(mContext.getString(R.string.category_11));
                break;
        }

        holder.mCellRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                mCallback.onCellClick(position, holder.mCategory.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface ConferenceCallback {
        void onCellClick(int position, String title);
    }
}
