package cj1098.animeshare;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ListView;

/**
 * this class is the main homepage. It creates a 3d listView effect.
 */
public class HomeScreenList extends ListView {

    /** used as a tool to measure max overscroll distance */
    private static final int MAX_Y_OVERSCROLL_DISTANCE = 200;
    /** needed for context */
    private Context mContext;
    /** actual overscroll distance allowed */
    private int mMaxYOverscrollDistance;
    /** Ambient light intensity */
    private static final int AMBIENT_LIGHT = 50;
    /** Diffuse light intensity */
    private static final int DIFFUSE_LIGHT = 200;
    /** Specular light intensity */
    private static final float SPECULAR_LIGHT = 70;
    /** Shininess constant */
    private static final float SHININESS = 200;


    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        return super.getChildDrawingOrder(childCount, i);
    }

    /** The max intensity of the light */
    private static final int MAX_INTENSITY = 0xFF;

    private final Camera mCamera = new Camera();
    private final Matrix mMatrix = new Matrix();
    /** Paint object to draw with */
    private Paint mPaint;

    public HomeScreenList(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initBounceListView();
    }

    public HomeScreenList(Context context) {
        super(context);
        this.mContext = context;
        initBounceListView();
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        // get top left coordinates
        final int top = child.getTop();
        final int left = child.getLeft();
        Bitmap bitmap = child.getDrawingCache();
        if (bitmap == null) {
            child.setDrawingCacheEnabled(true);
            child.buildDrawingCache();
            bitmap = child.getDrawingCache();
        }

        final int centerY = child.getHeight() / 2;
        final int centerX = child.getWidth() / 2;
        final int radius = getHeight() * 2;
        final int absParentCenterY = getTop() + getHeight() / 2;
        final int absChildCenterY = child.getTop() + centerY;
        final int distanceY = absParentCenterY - absChildCenterY;
        final int absDistance = Math.min(radius, Math.abs(distanceY));

        final float translateZ = (float) Math.sqrt((radius * radius) - (absDistance * absDistance));

        double radians = Math.acos((float) absDistance / radius);
        double degree = 90 - (180 / Math.PI) * radians;

        mCamera.save();
        mCamera.translate(0, 0, radius - translateZ);
        //mCamera.rotateX((float) degree); // remove this line..
        if (distanceY < 0) {
            degree = 360 - degree;
        }
        mCamera.rotateX((float) degree); // and change this to rotateX() to get a
        // wheel like effect
        mCamera.getMatrix(mMatrix);
        mCamera.restore();

        // create and initialize the paint object
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setFilterBitmap(true);
        }
        //highlight elements in the middle
        mPaint.setColorFilter(calculateLight((float) degree));

        mMatrix.preTranslate(-centerX, -centerY);
        mMatrix.postTranslate(centerX, centerY);
        mMatrix.postTranslate(left, top);
        canvas.drawBitmap(bitmap, mMatrix, mPaint);
        return false;
    }

    private LightingColorFilter calculateLight(final float rotation) {
        final double cosRotation = Math.cos(Math.PI * rotation / 180);
        int intensity = AMBIENT_LIGHT + (int) (DIFFUSE_LIGHT * cosRotation);
        int highlightIntensity = (int) (SPECULAR_LIGHT * Math.pow(cosRotation, SHININESS));
        if (intensity > MAX_INTENSITY) {
            intensity = MAX_INTENSITY;
        }
        if (highlightIntensity > MAX_INTENSITY) {
            highlightIntensity = MAX_INTENSITY;
        }
        final int light = Color.rgb(intensity, intensity, intensity);
        final int highlight = Color.rgb(highlightIntensity, highlightIntensity, highlightIntensity);
        return new LightingColorFilter(light, highlight);
    }

    private void initBounceListView()
    {
        //get the density of the screen and do some maths with it on the max overscroll distance
        //variable so that you get similar behaviors no matter what the screen size

        final DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        final float density = metrics.density;

        mMaxYOverscrollDistance = (int) (density * MAX_Y_OVERSCROLL_DISTANCE);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent)
    {
        //This is where the magic happens, we have replaced the incoming maxOverScrollY with our own custom variable mMaxYOverscrollDistance;
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxYOverscrollDistance, isTouchEvent);
    }
}