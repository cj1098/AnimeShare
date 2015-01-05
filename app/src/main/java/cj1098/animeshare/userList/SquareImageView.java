package cj1098.animeshare.userList;


import android.content.Context;
import android.util.AttributeSet;


/**
 * this is kind of a cool class that makes sure an image is the same dimensions
 * regardless of screen size. Look into it more!
 */
public class SquareImageView extends WebImageView {
    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
    }
}