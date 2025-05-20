package com.example.instabugandroidjava;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import com.instabug.library.Feature;
import com.instabug.survey.Surveys;

public class SurveysActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_surveys);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FrameLayout enableButtonContainer = findViewById(R.id.enableButtonFrame);
        ToggleButton enableButton = findViewById(R.id.enableButton);
        LinearLayout contentLayout = findViewById(R.id.contentLayout);
        RadioGroup autoShowingSurveysGroup = findViewById(R.id.autoShowingSUROptionsGroup);
        Button showAvailableSurveys = findViewById(R.id.showAvailableSUR);
        EditText manualSurveyTokenInput = findViewById(R.id.manualSURTokenInput);
        Button showManualSurvey = findViewById(R.id.showManualSURToken);
        RadioGroup welcomeScreenGroup = findViewById(R.id.welcomeScreenOptionsGroup);
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
                Surveys.setState(Feature.State.ENABLED);
                fadeInView(contentLayout);
            } else {
                Surveys.setState(Feature.State.DISABLED);
                fadeOutView(contentLayout);
            }
        });

        showAvailableSurveys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Surveys.showSurveyIfAvailable();
            }
        });

        showManualSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String surveyToken = manualSurveyTokenInput.getText().toString().trim();

                if(!surveyToken.isEmpty()){
                    Surveys.showSurvey(surveyToken);
                    manualSurveyTokenInput.setText("");
                }
            }
        });

       saveChanges.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               int selectedID;
               selectedID = autoShowingSurveysGroup.getCheckedRadioButtonId();
               if(selectedID != -1){
                   if (selectedID == R.id.enableAutoShowingSUR){
                       Surveys.setAutoShowingEnabled(true);
                       autoShowingSurveysGroup.clearCheck();
                       showAvailableSurveys.setVisibility(View.GONE);
                   } else if (selectedID == R.id.disableAutoShowingSUR) {
                       Surveys.setAutoShowingEnabled(false);
                       autoShowingSurveysGroup.clearCheck();
                       showAvailableSurveys.setVisibility(View.VISIBLE);
                   }
               }
              selectedID = welcomeScreenGroup.getCheckedRadioButtonId();
              if (selectedID != -1){
                  if(selectedID == R.id.enableWelcomeScreen){
                      Surveys.setShouldShowWelcomeScreen(true);
                      welcomeScreenGroup.clearCheck();
                  } else if (selectedID == R.id.disableWelcomeScreen) {
                      Surveys.setShouldShowWelcomeScreen(false);
                      welcomeScreenGroup.clearCheck();
                  }
              }
               Toast.makeText(SurveysActivity.this, "Changes saved successfully", Toast.LENGTH_SHORT).show();
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