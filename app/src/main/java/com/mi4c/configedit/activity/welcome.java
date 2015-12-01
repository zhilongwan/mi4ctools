package com.mi4c.configedit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.mi4c.configedit.R;

import net.youmi.android.AdManager;
import net.youmi.android.spot.SplashView;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class welcome extends Activity implements View.OnClickListener {
    LinearLayout ad;
    SplashView splashView;
    boolean haveAd = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        if (haveAd) {
            SpotManager.getInstance(this).loadSplashSpotAds();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ad();
                }
            }, 2000);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(welcome.this, main.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);
        }
    }

    public void ad() {
        ad = (LinearLayout) findViewById(R.id.ad);
        splashView = new SplashView(this, welcome.class);
        ad.addView(splashView.getSplashView());
        SpotManager.getInstance(this).showSplashSpotAds(this, splashView,
                new SpotDialogListener() {

                    @Override
                    public void onShowSuccess() {
                        findViewById(R.id.main).setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onShowFailed() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(welcome.this, main.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 2000);
                    }

                    @Override
                    public void onSpotClosed() {

                    }

                    @Override
                    public void onSpotClick(boolean isWebPath) {

                    }
                });
        splashView.setShowReciprocal(false);
        splashView.hideCloseBtn(true);
        Intent intent = new Intent(this, main.class);
        splashView.setIntent(intent);
        splashView.setIsJumpTargetWhenFail(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main:
                splashView.stopJumpIntent();
                Intent intent = new Intent(welcome.this, main.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
