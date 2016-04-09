package com.insantani_nostra.arisyaag.insantani;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by Suci Ayu on 3/23/2016.
 */
public class SplashScreenActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

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
                Intent ii = new Intent(getBaseContext(), SignInActivity.class);
                startActivity(ii);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
