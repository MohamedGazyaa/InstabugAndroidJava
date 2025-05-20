package com.example.instabugandroidjava;

import static com.instabug.library.InstabugCustomTextPlaceHolder.Key.REPORT_SUCCESSFULLY_SENT;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.instabug.bug.BugReporting;
import com.instabug.bug.ProactiveReportingConfigs;
import com.instabug.bug.invocation.Option;
import com.instabug.library.Feature;
import com.instabug.library.Instabug;
import com.instabug.library.InstabugCustomTextPlaceHolder;
import com.instabug.library.extendedbugreport.ExtendedBugReport;
import com.instabug.library.invocation.InstabugInvocationEvent;

import java.util.ArrayList;
import java.util.List;

public class BugReportingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bug_reporting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        CheckBox checkBoxShake = findViewById(R.id.checkbox_shake);
        CheckBox checkBoxFloatingButton = findViewById(R.id.checkbox_floating_button);
        CheckBox checkBoxScreenshot = findViewById(R.id.checkbox_screenshot);
        CheckBox checkBoxTwoFingerSwipe = findViewById(R.id.checkbox_two_finger_swipe);
        Button setButton = findViewById(R.id.set_button);
        Button bugButton = findViewById(R.id.bugs);
        Button feedbackButton = findViewById(R.id.feedback);
        Button questionButton = findViewById(R.id.question);
        Button fullPromptButton = findViewById(R.id.all);
        ToggleButton enableButton = findViewById(R.id.on_off);
        FrameLayout enableButtonContainer = findViewById(R.id.button_frame);
        LinearLayout contentLayout = findViewById(R.id.contentLayout);
        TextView advSettingsBtn = findViewById(R.id.advSettingsBtn);
        LinearLayout advSettingsCont = findViewById(R.id.advSettingsContainer);
        EditText shakeThresholdInput = findViewById(R.id.shakeThresholdInput);
        RadioGroup emailOptionsGroup = findViewById(R.id.emailOptionsGroup);
        EditText commentsMin = findViewById(R.id.commentsMinInput);
        CheckBox commentsRequired = findViewById(R.id.commentsRequired);
        CheckBox firstScreenshot = findViewById(R.id.firstScreenshot);
        CheckBox extraScreenshot = findViewById(R.id.extraScreenshot);
        CheckBox galleryImage = findViewById(R.id.galleryImage);
        CheckBox screenRecording = findViewById(R.id.screenRecording);
        RadioGroup MPoptionsGroup = findViewById(R.id.MPOptionsGroup);
        RadioGroup SROptionsGroup = findViewById(R.id.SROptionsGroup);
        CheckBox PROption = findViewById(R.id.PROption);
        CheckBox EBRState = findViewById(R.id.EBRState);
        CheckBox EBRfields = findViewById(R.id.EBRfields);
        Button saveChanges = findViewById(R.id.saveChangesBtn);

        InstabugCustomTextPlaceHolder placeHolder = new InstabugCustomTextPlaceHolder();
        placeHolder.set(REPORT_SUCCESSFULLY_SENT, "Thank you adjusted");
        Instabug.setCustomTextPlaceHolders(placeHolder);


        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<InstabugInvocationEvent> selectedEvents = new ArrayList<>();

                if (checkBoxShake.isChecked()) {
                    selectedEvents.add(InstabugInvocationEvent.SHAKE);
                }
                if (checkBoxFloatingButton.isChecked()) {
                    selectedEvents.add(InstabugInvocationEvent.FLOATING_BUTTON);
                }
                if (checkBoxScreenshot.isChecked()) {
                    selectedEvents.add(InstabugInvocationEvent.SCREENSHOT);
                }
                if (checkBoxTwoFingerSwipe.isChecked()) {
                    selectedEvents.add(InstabugInvocationEvent.TWO_FINGER_SWIPE_LEFT);
                }

                if (selectedEvents.isEmpty()) {
                    // No checkboxes selected, set to NONE
                    BugReporting.setInvocationEvents(InstabugInvocationEvent.NONE);
                    Toast.makeText(BugReportingActivity.this, "Invocation Events set to NONE", Toast.LENGTH_SHORT).show();
                } else {
                    // Convert the list to an array and pass it to Instabug
                    BugReporting.setInvocationEvents(selectedEvents.toArray(new InstabugInvocationEvent[0]));
                    Toast.makeText(BugReportingActivity.this, "Invocation Events Updated!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bugButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BugReporting.show(BugReporting.ReportType.BUG);
            }
        });
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BugReporting.show(BugReporting.ReportType.FEEDBACK);
            }
        });
        questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BugReporting.show(BugReporting.ReportType.QUESTION);
            }
        });
        fullPromptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Instabug.show();
            }
        });

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
                BugReporting.setState(Feature.State.ENABLED);
                fadeInView(contentLayout);
            } else {
                BugReporting.setState(Feature.State.DISABLED);
                fadeOutView(contentLayout);
            }
        });

        advSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(advSettingsCont.getVisibility()==View.VISIBLE){
                    advSettingsCont.animate()
                            .alpha(0F)
                            .setDuration(300)
                            .withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    advSettingsCont.setVisibility(View.GONE);
                                    advSettingsBtn.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_arrow_down,0);
                                }
                            });
                }else{
                    advSettingsCont.setVisibility(View.VISIBLE);
                    advSettingsCont.setAlpha(0F);
                    advSettingsCont.animate()
                            .alpha(1F)
                            .setDuration(300);
                    advSettingsBtn.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_arrow_up, 0);
                }
            }
        });

       saveChanges.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String input;
               int selectedID;
               input = shakeThresholdInput.getText().toString().trim();
               if(!input.isEmpty()){
                   int threshold = Integer.parseInt(input);
                   BugReporting.setShakingThreshold(threshold);
                   shakeThresholdInput.setText("");
               }
              selectedID = emailOptionsGroup.getCheckedRadioButtonId();
              if (selectedID != -1){
                  if (selectedID == R.id.emailOptional) {
                      BugReporting.setOptions(Option.EMAIL_FIELD_OPTIONAL);
                      emailOptionsGroup.clearCheck();
                  } else if (selectedID == R.id.emailHidden) {
                      BugReporting.setOptions(Option.EMAIL_FIELD_HIDDEN);
                      emailOptionsGroup.clearCheck();
                  }
              }
             input = commentsMin.getText().toString().trim();
               if(!input.isEmpty()) {
                   int minCharacters = Integer.parseInt(input);
                   BugReporting.setCommentMinimumCharacterCount(minCharacters, BugReporting.ReportType.BUG, BugReporting.ReportType.QUESTION, BugReporting.ReportType.FEEDBACK);
                   commentsMin.setText("");
               }
               if (commentsRequired.isChecked()) {
                   BugReporting.setOptions(Option.COMMENT_FIELD_REQUIRED);
                   commentsRequired.setChecked(false);
               }
             BugReporting.setAttachmentTypesEnabled(firstScreenshot.isChecked(), extraScreenshot.isChecked(), galleryImage.isChecked(), screenRecording.isChecked());
             firstScreenshot.setChecked(false);
             extraScreenshot.setChecked(false);
             galleryImage.setChecked(false);
             screenRecording.setChecked(false);
             selectedID = MPoptionsGroup.getCheckedRadioButtonId();
             if(selectedID != -1){
                 if(selectedID== R.id.MPOn){
                     BugReporting.setScreenshotByMediaProjectionEnabled(true);
                     MPoptionsGroup.clearCheck();
                 }else if (selectedID == R.id.MPOff){
                     BugReporting.setScreenshotByMediaProjectionEnabled(false);
                     MPoptionsGroup.clearCheck();
                 }
             }
            selectedID = SROptionsGroup.getCheckedRadioButtonId();
            if(selectedID != -1){
                if (selectedID == R.id.SROn){
                    BugReporting.setAutoScreenRecordingEnabled(true);
                    SROptionsGroup.clearCheck();
                }else if (selectedID == R.id.SROff){
                    BugReporting.setAutoScreenRecordingEnabled(false);
                    SROptionsGroup.clearCheck();
                }
            }
               if (PROption.isChecked()){
                   ProactiveReportingConfigs configs = new ProactiveReportingConfigs.Builder()
                           .setGapBetweenModals(24)
                           .setModalDelayAfterDetection(20)
                           .isEnabled(true)
                           .build();
                   BugReporting.setProactiveReportingConfigurations(configs);
                   PROption.setChecked(false);
               }else{
                   ProactiveReportingConfigs configs = new ProactiveReportingConfigs.Builder()
                           .setGapBetweenModals(24)
                           .setModalDelayAfterDetection(20)
                           .isEnabled(false)
                           .build();
                   BugReporting.setProactiveReportingConfigurations(configs);
               }
               if (EBRState.isChecked()){
                   if (EBRfields.isChecked()){
                       BugReporting.setExtendedBugReportState(ExtendedBugReport.State.ENABLED_WITH_REQUIRED_FIELDS);
                       EBRfields.setChecked(false);

                   }else{
                       BugReporting.setExtendedBugReportState(ExtendedBugReport.State.ENABLED_WITH_OPTIONAL_FIELDS);
                   }
                  EBRState.setChecked(false);
               }else{
                   BugReporting.setExtendedBugReportState(ExtendedBugReport.State.DISABLED);
               }
               Toast.makeText(BugReportingActivity.this, "Changes saved successfully", Toast.LENGTH_SHORT).show();
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