package jp.android_group.student.abc2015summer;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutFragment extends android.support.v4.app.Fragment {


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        TextView linkText = (TextView)rootView.findViewById(R.id.join);
        ImageView imageMikami = (ImageView)rootView.findViewById(R.id.imageMikami);
        ImageView imageHikawa = (ImageView)rootView.findViewById(R.id.imageHikawa);
        ImageView imageWatanabe = (ImageView)rootView.findViewById(R.id.imageWatanabe);
        ImageView imageSuzumura = (ImageView)rootView.findViewById(R.id.imageSuzumura);
        ImageView imageHamanaka = (ImageView)rootView.findViewById(R.id.imageHamanaka);
        ImageView imageSuzukiK = (ImageView)rootView.findViewById(R.id.imageSuzukiK);
        ImageView imageKonishi = (ImageView)rootView.findViewById(R.id.imageKonishi);
        ImageView imageSuzukiH = (ImageView)rootView.findViewById(R.id.imageSuzukiH);

        MovementMethod movementMethod = LinkMovementMethod.getInstance();
        linkText.setMovementMethod(movementMethod);

        String html = "入部歓迎（入部は<a href=\"http://student.android-group.jp/\">こちら</a>から）";
        CharSequence charSequence = Html.fromHtml(html);
        linkText.setAutoLinkMask(0);
        linkText.setText(charSequence);

        Resources resources = getResources();
        Bitmap mikamiSource = BitmapFactory.decodeResource(resources, R.drawable.mikami);
        Bitmap mikamiCircle = getRoundedBitmap(mikamiSource);

        Bitmap hikawaSource = BitmapFactory.decodeResource(resources, R.drawable.hikawa);
        Bitmap hikawaCircle = getRoundedBitmap(hikawaSource);

        Bitmap watanabeSource = BitmapFactory.decodeResource(resources, R.drawable.watanabe);
        Bitmap watanabeCircle = getRoundedBitmap(watanabeSource);

        Bitmap suzumuraSource = BitmapFactory.decodeResource(resources, R.drawable.suzumura);
        Bitmap suzumuraCircle = getRoundedBitmap(suzumuraSource);

        Bitmap hamanakaSource = BitmapFactory.decodeResource(resources, R.drawable.hamanaka);
        Bitmap hamanakaCircle = getRoundedBitmap(hamanakaSource);

        Bitmap suzukikSource = BitmapFactory.decodeResource(resources, R.drawable.suzukik);
        Bitmap suzukikCircle = getRoundedBitmap(suzukikSource);

        Bitmap konishiSource = BitmapFactory.decodeResource(resources, R.drawable.konishi);
        Bitmap konishiCircle = getRoundedBitmap(konishiSource);

        Bitmap suzukihSource = BitmapFactory.decodeResource(resources, R.drawable.suzukih);
        Bitmap suzukihCircle = getRoundedBitmap(suzukihSource);

        imageMikami.setImageBitmap(mikamiCircle);
        imageHikawa.setImageBitmap(hikawaCircle);
        imageWatanabe.setImageBitmap(watanabeCircle);
        imageSuzumura.setImageBitmap(suzumuraCircle);
        imageHamanaka.setImageBitmap(hamanakaCircle);
        imageSuzukiK.setImageBitmap(suzukikCircle);
        imageKonishi.setImageBitmap(konishiCircle);
        imageSuzukiH.setImageBitmap(suzukihCircle);

        return rootView;
    }

    public static Bitmap getRoundedBitmap(final Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();
        return bitmap;
    }
}
