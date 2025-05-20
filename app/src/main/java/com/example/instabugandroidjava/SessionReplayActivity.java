package com.example.instabugandroidjava;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
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
import com.instabug.library.sessionreplay.SessionReplay;

public class SessionReplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_session_replay);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FrameLayout enableButtonContainer = findViewById(R.id.enableButtonFrame);
        ToggleButton enableButton = findViewById(R.id.enableButton);
        LinearLayout contentLayout = findViewById(R.id.contentLayout);
        RadioGroup networkLogsGroup = findViewById(R.id.networkLogsOptionsGroup);
        RadioGroup instabugLogsGroup = findViewById(R.id.instabugLogsOptionsGroup);
        RadioGroup userStepsGroup = findViewById(R.id.userStepsOptionsGroup);
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
                SessionReplay.setEnabled(true);
                fadeInView(contentLayout);
            } else {
                SessionReplay.setEnabled(false);
                fadeOutView(contentLayout);
            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedID;
                selectedID = networkLogsGroup.getCheckedRadioButtonId();
                if(selectedID != -1){
                    if(selectedID == R.id.enableNetworkLogs){
                        SessionReplay.setNetworkLogsEnabled(true);
                        networkLogsGroup.clearCheck();
                    } else if (selectedID == R.id.disableNetworkLogs) {
                        SessionReplay.setNetworkLogsEnabled(false);
                        networkLogsGroup.clearCheck();
                    }
                }
               selectedID = instabugLogsGroup.getCheckedRadioButtonId();
               if (selectedID !=-1){
                   if(selectedID == R.id.enableInstabugLogs){
                       SessionReplay.setIBGLogsEnabled(true);
                       instabugLogsGroup.clearCheck();
                   } else if (selectedID == R.id.disableInstabugLogs) {
                       SessionReplay.setIBGLogsEnabled(false);
                       instabugLogsGroup.clearCheck();
                   }
               }
               selectedID = userStepsGroup.getCheckedRadioButtonId();
               if(selectedID != -1){
                   if (selectedID == R.id.enableUserSteps){
                       SessionReplay.setUserStepsEnabled(true);
                       userStepsGroup.clearCheck();
                   } else if (selectedID == R.id.disableUserSteps) {
                       SessionReplay.setUserStepsEnabled(false);
                       userStepsGroup.clearCheck();
                   }
               }
                Toast.makeText(SessionReplayActivity.this, "Changes saved successfully", Toast.LENGTH_SHORT).show();
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