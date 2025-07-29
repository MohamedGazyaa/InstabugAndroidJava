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

import com.instabug.crash.CrashReporting;
import com.instabug.crash.models.IBGNonFatalException;
import com.instabug.library.Feature;
import com.instabug.library.Instabug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CrashReportingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crash_reporting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FrameLayout enableButtonContainer = findViewById(R.id.enableButtonFrame);
        ToggleButton enableButton = findViewById(R.id.enableButton);
        LinearLayout contentLayout = findViewById(R.id.contentLayout);
        RadioGroup crashLevelGroup = findViewById(R.id.ManualCrashLevelGroup);
        Button reportManualCrashBtn = findViewById(R.id.reportManualCrashBtn);
        Button reportFatalCrashBtn = findViewById(R.id.reportFatalCrashBtn);
        Button reportANRCrashBtn = findViewById(R.id.reportANRCrashBtn);
        Button reportOOMCrashBtn = findViewById(R.id.reportOOMCrashBtn);
        RadioGroup ANROptionsGroup = findViewById(R.id.ANROptionsGroup);
        RadioGroup NDKOptionsGroup = findViewById(R.id.NDKOptionsGroup);
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
                CrashReporting.setState(Feature.State.ENABLED);
                fadeInView(contentLayout);
            } else {
                CrashReporting.setState(Feature.State.DISABLED);
                fadeOutView(contentLayout);
            }
        });

       reportManualCrashBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               int selectedID = crashLevelGroup.getCheckedRadioButtonId();
               if(selectedID == R.id.criticalCrash){
                       IBGNonFatalException exception = new IBGNonFatalException.Builder(new NullPointerException("Test Exception"))
                               .setUserAttributes(new HashMap<>())
                               .setFingerprint("My Custom Fingerprint")
                               .setLevel(IBGNonFatalException.Level.CRITICAL)
                               .build();
                       CrashReporting.report(exception);
               }
               if(selectedID == R.id.waringCrash){
                   IBGNonFatalException exception = new IBGNonFatalException.Builder(new NullPointerException("Test Exception"))
                           .setUserAttributes(new HashMap<>())
                           .setFingerprint("My Custom Fingerprint")
                           .setLevel(IBGNonFatalException.Level.WARNING)
                           .build();
                   CrashReporting.report(exception);
               }
               if(selectedID == R.id.errorCrash){
                   IBGNonFatalException exception = new IBGNonFatalException.Builder(new NullPointerException("Test Exception"))
                           .setUserAttributes(new HashMap<>())
                           .setFingerprint("My Custom Fingerprint")
                           .setLevel(IBGNonFatalException.Level.ERROR)
                           .build();
                   CrashReporting.report(exception);
               }
               if(selectedID == R.id.informationCrash){
                   IBGNonFatalException exception = new IBGNonFatalException.Builder(new NullPointerException("Test Exception"))
                           .setUserAttributes(new HashMap<>())
                           .setFingerprint("My Custom Fingerprint")
                           .setLevel(IBGNonFatalException.Level.INFO)
                           .build();
                   CrashReporting.report(exception);
               }
               crashLevelGroup.clearCheck();
           }
       });

       reportFatalCrashBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               int crash = 10 / 0;
           }
       });

       reportANRCrashBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               try {
                   Thread.sleep(10000);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }
       });

       reportOOMCrashBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Instabug.setTrackingUserStepsState(Feature.State.ENABLED);
               List<byte[]> memoryHog = new ArrayList<>();
               while (true) {
                   memoryHog.add(new byte[1024 * 1024]); // Allocate 1MB repeatedly
               }
           }
       });

       saveChanges.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               int selectedID;
               selectedID = ANROptionsGroup.getCheckedRadioButtonId();
               if(selectedID != -1){
                   if(selectedID== R.id.enableANR){
                       CrashReporting.setAnrState(Feature.State.ENABLED);
                       reportANRCrashBtn.setVisibility(View.VISIBLE);
                       ANROptionsGroup.clearCheck();
                   } else if (selectedID == R.id.disableANR) {
                       CrashReporting.setAnrState(Feature.State.DISABLED);
                       reportANRCrashBtn.setVisibility(View.GONE);
                       ANROptionsGroup.clearCheck();
                   }
               }
              selectedID = NDKOptionsGroup.getCheckedRadioButtonId();
               if (selectedID != -1){
                   if(selectedID == R.id.enableNDK){
                       CrashReporting.setNDKCrashesState(Feature.State.ENABLED);
                       NDKOptionsGroup.clearCheck();
                   } else if (selectedID == R.id.disableNDK) {
                       CrashReporting.setNDKCrashesState(Feature.State.DISABLED);
                       NDKOptionsGroup.clearCheck();
                   }
               }
               Toast.makeText(CrashReportingActivity.this, "Changes saved successfully", Toast.LENGTH_SHORT).show();
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