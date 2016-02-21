package jp.android_group.student.abc2015summer;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Spannable;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DrawerFooterFragment extends android.support.v4.app.Fragment {


    public DrawerFooterFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_footer, container, false);

        TextView address = (TextView) rootView.findViewById(R.id.address);
        String text = "神奈川県川崎市幸区堀川町66-20";
        Spannable t = Spannable.Factory.getInstance().newSpannable(text);
        UnderlineSpan us = new UnderlineSpan();
        t.setSpan(us, 0, text.length(), t.getSpanFlags(us));
        address.setText(t, TextView.BufferType.SPANNABLE);
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("geo:0,0?q=川崎市産業振興会館"));
                startActivity(intent);
            }
        });

        return rootView;
    }


}
