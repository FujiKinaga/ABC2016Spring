package jagsc.org.abc.info.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

import jagsc.org.abc.info.R;

/**
 * Created by kinagafuji on 16/03/01.
 */
public class StarLayout extends RelativeLayout {
    private AbstractPathAnimator mAnimator;
    private AttributeSet attrs = null;
    private int defStyleAttr = 0;

    private Random mRandom = new Random();

    public StarLayout(Context context) {
        super(context);
    }

    public StarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attrs = attrs;

    }

    public StarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attrs = attrs;
        this.defStyleAttr = defStyleAttr;

    }

    private void init(AttributeSet attrs, int defStyleAttr, float initX, float initY, int heartWidth, int heartHeight) {

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.HeartLayout, defStyleAttr, 0);
        int pointx = (int) initX + new Random().nextInt(100) - 200;//随机上浮方向的x坐标
        mAnimator = new PathAnimator(AbstractPathAnimator.Config.fromTypeArray(a, initX, initY, pointx, heartWidth, heartHeight));
        a.recycle();
    }

    public AbstractPathAnimator getAnimator() {
        return mAnimator;
    }

    public void setAnimator(AbstractPathAnimator animator) {
        clearAnimation();
        mAnimator = animator;
    }

    public void clearAnimation() {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).clearAnimation();
        }
        removeAllViews();
    }

    public void addStar(float initX, float initY) {
        int ResourceId = getResourceId();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), ResourceId);

        init(attrs, defStyleAttr, initX, initY, bitmap.getWidth(), bitmap.getWidth());
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(ResourceId);
        mAnimator.start(imageView, this);
    }

    private int getResourceId() {
        return R.drawable.ic_star_yellow_600_36dp;
    }
}
