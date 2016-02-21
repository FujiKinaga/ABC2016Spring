package jp.android_group.student.abc2015summer;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import jp.android_group.student.abc2015summer.api.Bazaar;
import uk.co.senab.photoview.PhotoView;

public class BazaarDetailFragment extends Fragment implements View.OnClickListener {

    private ImageView mImage;
    private View rootView;
    private boolean isFavorite;

    public static BazaarDetailFragment newInstance(Bazaar bazaar) {
        Bundle args = new Bundle();
        args.putString(Bazaar.TAG_TITLE, bazaar.getTitle());
        args.putString(Bazaar.TAG_GROUP, bazaar.getGroup());
        args.putString(Bazaar.TAG_CONTENT, bazaar.getContent());
        args.putString(Bazaar.TAG_LOCATION, bazaar.getLocation());
        args.putString(Bazaar.TAG_COLOR, bazaar.getColor());
        BazaarDetailFragment fragment = new BazaarDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public BazaarDetailFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_bazaar_detail, container, false);

        Bundle args = getArguments();
        Bazaar bazaar = new Bazaar(args.getString(Bazaar.TAG_TITLE)
                , args.getString(Bazaar.TAG_GROUP), args.getString(Bazaar.TAG_CONTENT)
                , args.getString(Bazaar.TAG_LOCATION), args.getString(Bazaar.TAG_COLOR));

        TextView title = (TextView) rootView.findViewById(R.id.title);
        TextView group = (TextView) rootView.findViewById(R.id.group);
        TextView content = (TextView) rootView.findViewById(R.id.content);
        TextView location = (TextView) rootView.findViewById(R.id.location);

        mImage = (ImageView) rootView.findViewById(R.id.map);
        mImage.setScaleType(ImageView.ScaleType.FIT_XY);
        mImage.setOnClickListener(this);

        title.setText(bazaar.getTitle());
        group.setText(bazaar.getGroup());
        content.setText(bazaar.getContent());
        location.setText(bazaar.getLocation());

        if (bazaar.getLocation().startsWith("No.")) {
            mImage.setImageResource(R.drawable.bazaar_site);
        } else {
            mImage.setImageResource(R.drawable.floor01);
        }

        int color;
        switch (bazaar.getColor()) {
            case "blue":
                color = R.color.blue;
                break;
            case "green":
                color = R.color.green;
                break;
            case "light_blue":
                color = R.color.light_blue;
                break;
            case "purple":
                color = R.color.purple;
                break;
            case "pink":
                color = R.color.pink;
                break;
            case "orange":
                color = R.color.orange;
                break;
            case "lime":
                color = R.color.lime;
                break;
            default:
                color = R.color.red;
                break;
        }
        rootView.setBackgroundColor(getActivity().getResources().getColor(color));

        ImageButton btnFav = (ImageButton) rootView.findViewById(R.id.btn_favorite);
        btnFav.setVisibility(View.GONE);
        // TODO: テスト用にisFavoriteをfalseにしてます。登録メソッドできたら削除
        isFavorite = false;
        if (isFavorite) {
            btnFav.setImageResource(R.drawable.star_pink_pushed);
        }

        btnFav.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.map:
                PhotoView photoView = new PhotoView(getActivity());
                Bitmap bitmap = ((BitmapDrawable)mImage.getDrawable()).getBitmap();
                photoView.setImageBitmap(bitmap);

                Dialog dialog = new Dialog(getActivity());
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                photoView.setScaleType(ImageView.ScaleType.FIT_XY);
                dialog.setContentView(photoView);
                dialog.show();
                break;
            case R.id.btn_favorite:
                ImageButton btn = (ImageButton) rootView.findViewById(R.id.btn_favorite);
                if (isFavorite) {
                    // TODO: マイスケジュールから消去する処理
                    isFavorite = false;
                    btn.setImageResource(R.drawable.star_pink);
                    Snackbar.make(rootView, "マイスケジュールから削除しました！", Snackbar.LENGTH_LONG).show();
                } else {
                    // TODO: マイスケジュールに登録する処理
                    isFavorite = true;
                    btn.setImageResource(R.drawable.star_pink_pushed);
                    Snackbar.make(rootView, "マイスケジュールに追加しました！",  Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }
}
