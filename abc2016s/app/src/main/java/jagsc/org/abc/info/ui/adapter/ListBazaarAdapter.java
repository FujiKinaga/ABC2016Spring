package jagsc.org.abc.info.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andexert.library.RippleView;

import java.util.ArrayList;
import java.util.List;

import jagsc.org.abc.info.R;
import jagsc.org.abc.info.consts.Const;
import jagsc.org.abc.info.datasource.action.FavoriteAction;
import jagsc.org.abc.info.domain.model.Bazaar;

/**
 * Created by kinagafuji on 16/02/27.
 */
public class ListBazaarAdapter extends RecyclerView.Adapter<Const.BazaarViewHolder> {

    private Context mContext;

    private List<Bazaar> mData = new ArrayList<>();

    private BazaarCallback mCallback;

    public ListBazaarAdapter(Context context, List<Bazaar> data) {
        mContext = context;
        mData = data;
    }

    public void setBazaarCallback(BazaarCallback callback) {
        mCallback = callback;
    }

    public void setData() {
        this.notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return mData.isEmpty();
    }

    @Override
    public Const.BazaarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.list_bazaar_item, parent, false);
        return new Const.BazaarViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final Const.BazaarViewHolder holder, final int position) {
        final Bazaar bazaar = mData.get(holder.getAdapterPosition());

        holder.mTitle.setText(bazaar.getTitle());
        holder.mGroup.setText(bazaar.getGroup());
        holder.mLocation.setText(bazaar.getLocation());
        holder.mLocation.setBackgroundResource(R.drawable.background_bazaar);

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
                mCallback.onCellClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface BazaarCallback {
        void onCellClick(int position);

        void onFavoriteClick();
    }
}
