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

import com.instabug.featuresrequest.ActionType;
import com.instabug.featuresrequest.FeatureRequests;
import com.instabug.library.Feature;

public class FeatureRequestsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_feature_requests);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FrameLayout enableButtonContainer = findViewById(R.id.enableButtonFrame);
        ToggleButton enableButton = findViewById(R.id.enableButton);
        LinearLayout contentLayout = findViewById(R.id.contentLayout);
        Button showFeatureRequests = findViewById(R.id.showFeatureRequests);
        RadioGroup newRequestsOptions = findViewById(R.id.newRequestOptionsGroup);
        RadioGroup newCommentOptions = findViewById(R.id.newCommentOptionsGroup);
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
                FeatureRequests.setState(Feature.State.ENABLED);
                fadeInView(contentLayout);
            } else {
                FeatureRequests.setState(Feature.State.DISABLED);
                fadeOutView(contentLayout);
            }
        });

        showFeatureRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeatureRequests.show();
            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedID ;
                selectedID = newRequestsOptions.getCheckedRadioButtonId();
                if (selectedID != -1){
                    if(selectedID == R.id.newRequestEmailRequired){
                        FeatureRequests.setEmailFieldRequired(true, ActionType.REQUEST_NEW_FEATURE);
                        newRequestsOptions.clearCheck();
                    } else if (selectedID == R.id.newRequestEmailOptional) {
                        FeatureRequests.setEmailFieldRequired(false, ActionType.REQUEST_NEW_FEATURE);
                        newRequestsOptions.clearCheck();
                    }
                }
               selectedID = newCommentOptions.getCheckedRadioButtonId();
                if(selectedID != -1){
                    if(selectedID ==R.id.newCommentEmailRequired){
                        FeatureRequests.setEmailFieldRequired(true, ActionType.ADD_COMMENT_TO_FEATURE);
                        newCommentOptions.clearCheck();
                    } else if (selectedID == R.id.newCommentEmailOptional) {
                        FeatureRequests.setEmailFieldRequired(false, ActionType.ADD_COMMENT_TO_FEATURE);
                        newCommentOptions.clearCheck();
                    }
                }
                Toast.makeText(FeatureRequestsActivity.this, "Changes saved successfully", Toast.LENGTH_SHORT).show();
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