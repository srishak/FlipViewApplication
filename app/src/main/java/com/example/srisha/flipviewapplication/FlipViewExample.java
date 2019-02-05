package com.example.srisha.flipviewapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;


public class FlipViewExample extends AppCompatActivity {

    private static final int DURATION = 1500;
    private SeekBar mSeekBar;

    private static final String[] LIST_STRINGS_EN = new String[]{
            "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"
    };
    private static final String[] LIST_STRINGS_FR = new String[]{
            "Apple", "Orange", "Mango", "Papaya", "Cucumber", "Banana", "Grape", "Jackfruit","Kiwi"
    };

    ListView mEnglishList;
    ListView mFrenchList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_view_example);
        mEnglishList = (ListView) findViewById(R.id.list_en);
        mFrenchList = (ListView) findViewById(R.id.list_fr);

        final ArrayAdapter adapterEn = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, LIST_STRINGS_EN);
// Prepare the ListView
        final ArrayAdapter adapterFr = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, LIST_STRINGS_FR);

        mEnglishList.setAdapter(adapterEn);
        mFrenchList.setAdapter(adapterFr);
        mFrenchList.setRotationY(-90f);

        Button starter = (Button) findViewById(R.id.button);
        starter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                flipit();
            }
        });


    }

    private Interpolator accelerator = new AccelerateInterpolator();
    private Interpolator decelerator = new DecelerateInterpolator();

    private void flipit() {
        final ListView visibleList;
        final ListView invisibleList;
        if (mEnglishList.getVisibility() == View.GONE) {
            visibleList = mFrenchList;
            invisibleList = mEnglishList;
        } else {
            invisibleList = mFrenchList;
            visibleList = mEnglishList;
        }
        ObjectAnimator visToInvis = ObjectAnimator.ofFloat(visibleList, "rotationY", 0f, 90f);
        visToInvis.setDuration(500);
        visToInvis.setInterpolator(accelerator);
        final ObjectAnimator invisToVis = ObjectAnimator.ofFloat(invisibleList, "rotationY",
                -90f, 0f);
        invisToVis.setDuration(500);
        invisToVis.setInterpolator(decelerator);
        visToInvis.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator anim) {
                visibleList.setVisibility(View.GONE);
                invisToVis.start();
                invisibleList.setVisibility(View.VISIBLE);
            }
        });
        visToInvis.start();
    }
}
