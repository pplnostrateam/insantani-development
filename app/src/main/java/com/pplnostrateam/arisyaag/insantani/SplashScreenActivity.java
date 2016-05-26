
/**
 * Created by Suci Ayu on 3/23/2016.
 *
 * Edited by Hartico on 4/9/2016
 */

package com.pplnostrateam.arisyaag.insantani;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.pplnostrateam.arisyaag.insantani.R;

import io.fabric.sdk.android.Fabric;
import java.lang.Thread;

public class SplashScreenActivity extends Activity {
    //Thread to splashscreen event
    private Thread mSplashThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        //splash screen view
        setContentView(R.layout.activity_splash_screen);
        final SplashScreenActivity sPlashScreen = this;
        //splash screen animation
        final ImageView imavi = (ImageView) findViewById(R.id.imageView);
        final Animation anim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        final Animation anim2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_fade_out);

        imavi.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imavi.startAnimation(anim2);
                finish();
                Intent ii = new Intent(getBaseContext(), SearchingActivity.class);
                startActivity(ii);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /*
     * Processes splash screen touch events
     */
    @Override
    public boolean onTouchEvent(MotionEvent evt)
    {
        if(evt.getAction() == MotionEvent.ACTION_DOWN)
        {
            synchronized(mSplashThread){
                mSplashThread.notifyAll();
            }
        }
        return true;
    }
}
