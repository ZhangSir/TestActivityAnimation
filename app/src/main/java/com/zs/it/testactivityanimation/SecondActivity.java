package com.zs.it.testactivityanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Property;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

public class SecondActivity extends Activity {

    private AnimationViewGroup mAnimated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        FrameLayout activityRoot = (FrameLayout) findViewById(android.R.id.content);
        View parent = activityRoot.getChildAt(0);

        // better way ?
        mAnimated = new AnimationViewGroup(this);
        activityRoot.removeView(parent);
        activityRoot.addView(mAnimated, parent.getLayoutParams());
        mAnimated.addView(parent);

        ObjectAnimator animator = ObjectAnimator.ofFloat(mAnimated, ANIMATED_RECT_LAYOUT_FLOAT_PROPERTY, 1).setDuration(600);
        animator.start();
    }

    @Override
    public void onBackPressed() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mAnimated, ANIMATED_RECT_LAYOUT_FLOAT_PROPERTY, 0).setDuration(600);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
            }
        });
        animator.start();
    }

    private static final Property<AnimationViewGroup, Float> ANIMATED_RECT_LAYOUT_FLOAT_PROPERTY =
            new Property<AnimationViewGroup, Float>(Float.class, "ANIMATED_RECT_LAYOUT_FLOAT_PROPERTY") {

                @Override
                public void set(AnimationViewGroup layout, Float value) {
                    layout.setProgress(value);
                }

                @Override
                public Float get(AnimationViewGroup layout) {
                    return layout.getProgress();
                }
            };

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
