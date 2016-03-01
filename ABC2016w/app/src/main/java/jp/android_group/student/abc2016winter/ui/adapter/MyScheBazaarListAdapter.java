package jp.android_group.student.abc2016winter.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andexert.library.RippleView;

import java.util.ArrayList;
import java.util.List;

import jp.android_group.student.abc2016winter.R;
import jp.android_group.student.abc2016winter.consts.Const;
import jp.android_group.student.abc2016winter.datasource.api.FavoriteAction;
import jp.android_group.student.abc2016winter.domain.model.Bazaar;

/**
 * Created by kinagafuji on 16/02/29.
 */
public class MyScheBazaarListAdapter extends RecyclerView.Adapter<Const.MyScheBazaarViewHolder> {
    private Context mContext;

    private List<Bazaar> mData = new ArrayList<>();

    private MyScheBazaarCallback mCallback;

    public MyScheBazaarListAdapter(Context context, List<Bazaar> data) {
        mContext = context;
        mData = data;
    }

    public void setMyScheBazaarCallback(MyScheBazaarCallback callback) {
        mCallback = callback;
    }

    public void setData() {
        this.notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return mData.isEmpty();
    }

    @Override
    public Const.MyScheBazaarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.bazaar_my_schedule_list_item, parent, false);
        return new Const.MyScheBazaarViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final Const.MyScheBazaarViewHolder holder, final int position) {
        final Bazaar bazaar = mData.get(position);

        holder.mTitle.setText(bazaar.getTitle());
        holder.mGroup.setText(bazaar.getGroup());
        holder.mLocation.setText(bazaar.getLocation());
        switch (bazaar.getLocation().substring(0, 1)) {
            case "A":
                holder.mLocation.setBackgroundResource(R.drawable.background_bazaar_a);
                break;
            case "B":
                holder.mLocation.setBackgroundResource(R.drawable.background_bazaar_b);
                break;
            case "C":
                holder.mLocation.setBackgroundResource(R.drawable.background_bazaar_c);
                break;
            case "D":
                holder.mLocation.setBackgroundResource(R.drawable.background_bazaar_d);
                break;
            case "E":
                holder.mLocation.setBackgroundResource(R.drawable.background_bazaar_e);
                break;
            case "F":
                holder.mLocation.setBackgroundResource(R.drawable.background_bazaar_f);
                break;
            case "G":
                holder.mLocation.setBackgroundResource(R.drawable.background_bazaar_g);
                break;
            case "H":
                holder.mLocation.setBackgroundResource(R.drawable.background_bazaar_h);
                break;
            case "I":
                holder.mLocation.setBackgroundResource(R.drawable.background_bazaar_i);
                break;
        }

        holder.mFavoriteButton.setBackgroundResource(FavoriteAction.isFavoriteBazaar(mContext, bazaar.getId()) ?
                R.drawable.ic_star_yellow_600_36dp : R.drawable.ic_star_border_grey_600_36dp);

        holder.mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FavoriteAction.isFavoriteBazaar(mContext, bazaar.getId())) {
                    FavoriteAction.unFavoriteBazaar(mContext, bazaar.getId());
                    holder.mFavoriteButton.setBackgroundResource(R.drawable.ic_star_border_grey_600_36dp);
                } else {
                    FavoriteAction.favoriteBazaar(mContext, bazaar.getId());
                    holder.mFavoriteButton.setBackgroundResource(R.drawable.ic_star_yellow_600_36dp);
                    mCallback.onFavoriteClick();
                }
            }
        });

        holder.mCellRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                mCallback.onCellClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface MyScheBazaarCallback {
        void onCellClick(int position);

        void onFavoriteClick();
    }
}
