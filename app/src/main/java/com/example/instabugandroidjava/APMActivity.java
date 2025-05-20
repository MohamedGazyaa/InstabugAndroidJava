package com.example.instabugandroidjava;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.instabug.apm.APM;

public class APMActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_apm);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FrameLayout enableButtonContainer = findViewById(R.id.enableButtonFrame);
        ToggleButton enableButton = findViewById(R.id.enableButton);
        LinearLayout contentLayout = findViewById(R.id.contentLayout);
        RadioGroup HALGroup = findViewById(R.id.HALOptionsGroup);
        RadioGroup CALGroup = findViewById(R.id.CALOptionsGroup);
        RadioGroup WALGroup = findViewById(R.id.WALOptionsGroup);
        RadioGroup UIHangsGroup = findViewById(R.id.UIHangsOptionsGroup);
        RadioGroup SLGroup = findViewById(R.id.SLOptionsGroup);
        RadioGroup WVTGroup = findViewById(R.id.WVTOptionsGroup);
        Button saveChanges = findViewById(R.id.saveChangesBtn);


        enableButton.setOnCheckedChangeListener((buttonView, isChecked)->{

            float slideDistance = enableButtonContainer.getWidth() - enableButton.getWidth();

            ObjectAnimator animator = ObjectAnimator.ofFloat(
                    enableButton,
                    "translationX",
                    isChecked ? 0 : slideDistance
            );
            animator.setDuration(300);
            animator.start();

            if (isChecked) {
                APM.setEnabled(true);
                fadeInView(contentLayout);
            } else {
                APM.setEnabled(false);
                fadeOutView(contentLayout);
            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedID;
                selectedID = HALGroup.getCheckedRadioButtonId();
                if(selectedID != -1){
                    if(selectedID == R.id.enableHAL){
                        APM.setHotAppLaunchEnabled(true);
                        HALGroup.clearCheck();
                    } else if (selectedID == R.id.disableHAL) {
                        APM.setHotAppLaunchEnabled(false);
                        HALGroup.clearCheck();
                    }
                }
               selectedID = CALGroup.getCheckedRadioButtonId();
               if(selectedID != -1){
                   if (selectedID == R.id.enableCAL){
                       APM.setColdAppLaunchEnabled(true);
                       CALGroup.clearCheck();
                   } else if (selectedID == R.id.disableCAL) {
                       APM.setColdAppLaunchEnabled(false);
                       CALGroup.clearCheck();
                   }
               }
              selectedID = WALGroup.getCheckedRadioButtonId();
               if (selectedID != -1){
                   if (selectedID == R.id.enableWAL){
                       APM.setWarmAppLaunchEnabled(true);
                       WALGroup.clearCheck();
                   } else if (selectedID == R.id.disableWAL) {
                       APM.setWarmAppLaunchEnabled(false);
                       WALGroup.clearCheck();
                   }
               }
              selectedID = UIHangsGroup.getCheckedRadioButtonId();
               if(selectedID != -1){
                   if (selectedID == R.id.enableUIHangs){
                       APM.setUIHangEnabled(true);
                       UIHangsGroup.clearCheck();
                   } else if (selectedID == R.id.disableUIHangs) {
                       APM.setUIHangEnabled(false);
                       UIHangsGroup.clearCheck();
                   }
               }
              selectedID =  SLGroup.getCheckedRadioButtonId();
               if (selectedID != -1){
                   if(selectedID == R.id.enableSL){
                       APM.setScreenLoadingEnabled(true);
                       SLGroup.clearCheck();
                   } else if (selectedID == R.id.disableSL) {
                       APM.setScreenLoadingEnabled(false);
                       SLGroup.clearCheck();
                   }
               }
              selectedID = WVTGroup.getCheckedRadioButtonId();
              if(selectedID != -1){
                  if (selectedID == R.id.enableWVT){
                      APM.setWebViewsTrackingEnabled(true);
                      WVTGroup.clearCheck();
                  } else if (selectedID == R.id.disableWVT) {
                      APM.setWebViewsTrackingEnabled(false);
                      WVTGroup.clearCheck();
                  }
              }
                Toast.makeText(APMActivity.this, "Changes saved successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void fadeInView(View view) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0f);
        view.animate()
                .alpha(1f)
                .setDuration(400)
                .setListener(null);
    }

    // Fade Out Animation
    private void fadeOutView(View view) {
        view.animate()
                .alpha(0f)
                .setDuration(400)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.GONE);
                    }
                });
    }

}