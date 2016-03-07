package sharemyscreen.sharemyscreen;

import android.content.Context;
import android.util.AttributeSet;

import com.gordonwong.materialsheetfab.AnimatedFab;
import com.melnykov.fab.FloatingActionButton;

/**
 * Created by cleme_000 on 28/02/2016.
 */
public class Fab extends FloatingActionButton implements AnimatedFab {
    public Fab(Context context) {
        super(context);
    }

    public Fab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Fab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Shows the FAB.
     */
    @Override
    public void show() {
        show(0, 0);
    }

    /**
     * Shows the FAB and sets the FAB's translation.
     *
     * @param translationX translation X value
     * @param translationY translation Y value
     */
    @Override
    public void show(float translationX, float translationY) {
        super.show();
        // NOTE: Using the parameters is only needed if you want
        // to support moving the FAB around the screen.
        // NOTE: This immediately hides the FAB. An animation can
        // be used instead - see the sample app.
//        setVisibility(View.VISIBLE);
    }

    /**
     * Hides the FAB.
     */
    @Override
    public void hide() {
        super.hide();
        // NOTE: This immediately hides the FAB. An animation can
        // be used instead - see the sample app.
//        setVisibility(View.INVISIBLE);
    }


}
