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
import jagsc.org.abc.info.domain.model.Bazaar;

/**
 * Created by kinagafuji on 16/02/29.
 */
public class ListMyScheBazaarAdapter extends RecyclerView.Adapter<Const.MyScheBazaarViewHolder> {
    private Context mContext;

    private List<Bazaar> mData = new ArrayList<>();

    private MyScheBazaarCallback mCallback;

    public ListMyScheBazaarAdapter(Context context, List<Bazaar> data) {
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
                .inflate(R.layout.list_my_sche_bazaar_item, parent, false);
        return new Const.MyScheBazaarViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final Const.MyScheBazaarViewHolder holder, final int position) {
        final Bazaar bazaar = mData.get(holder.getAdapterPosition());

        holder.mTitle.setText(bazaar.getTitle());
        holder.mGroup.setText(bazaar.getGroup());
        holder.mLocation.setText(bazaar.getLocation());
        holder.mLocation.setBackgroundResource(R.drawable.background_bazaar);

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

    public interface MyScheBazaarCallback {
        void onCellClick(int position);
    }
}
