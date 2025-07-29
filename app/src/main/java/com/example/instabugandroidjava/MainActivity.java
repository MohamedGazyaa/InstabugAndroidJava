package com.example.instabugandroidjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.instabug.library.Instabug;
import com.instabug.library.invocation.InstabugInvocationEvent;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText appTokenInput = findViewById(R.id.appTokenInput);
        TextView advSettingsBanner = findViewById(R.id.advSettingsBtn);
        LinearLayout advSettingsCont = findViewById(R.id.advSettingsContainer);
        CheckBox checkBoxShake = findViewById(R.id.shake);
        CheckBox checkBoxFloatingButton = findViewById(R.id.floatingButton);
        CheckBox checkBoxScreenshot = findViewById(R.id.screenshot);
        CheckBox checkBoxFingerSwipe = findViewById(R.id.swipe);
        Button sdkInitializationBtn = findViewById(R.id.sdkInitializationBtn);

        advSettingsBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(advSettingsCont.getVisibility()==View.VISIBLE){
                    advSettingsCont.animate()
                            .alpha(0F)
                            .setDuration(200)
                            .withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    advSettingsCont.setVisibility(View.GONE);
                                    advSettingsBanner.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_arrow_down,0);
                                }
                            });
                }else{
                    advSettingsCont.setVisibility(View.VISIBLE);
                    advSettingsCont.setAlpha(0F);
                    advSettingsCont.animate()
                            .alpha(1F)
                            .setDuration(200);
                    advSettingsBanner.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_arrow_up, 0);
                }
            }
        });

        sdkInitializationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = appTokenInput.getText().toString().trim();
                List<InstabugInvocationEvent> selectedEvents = new ArrayList<>();

                if (!token.isEmpty()){
                    if(checkBoxShake.isChecked() || checkBoxFingerSwipe.isChecked() || checkBoxScreenshot.isChecked() || checkBoxFloatingButton.isChecked()){
                        if (checkBoxShake.isChecked()) {
                            selectedEvents.add(InstabugInvocationEvent.SHAKE);
                        }
                        if (checkBoxFloatingButton.isChecked()) {
                            selectedEvents.add(InstabugInvocationEvent.FLOATING_BUTTON);
                        }
                        if (checkBoxScreenshot.isChecked()) {
                            selectedEvents.add(InstabugInvocationEvent.SCREENSHOT);
                        }
                        if (checkBoxFingerSwipe.isChecked()) {
                            selectedEvents.add(InstabugInvocationEvent.TWO_FINGER_SWIPE_LEFT);
                        }
                        new Instabug.Builder(MainActivity.this.getApplication(), token)
                                .setInvocationEvents(selectedEvents.toArray(new InstabugInvocationEvent[0]))
                                .build();
                    }else{
                        new Instabug.Builder(MainActivity.this.getApplication(), token)
                                .setInvocationEvents(InstabugInvocationEvent.NONE)
                                .build();
                    }
                    Toast.makeText(MainActivity.this, "SDK initialized successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, ProductsNavigationActivity.class);
                    startActivity(intent);
                }else{
                    appTokenInput.setError("Please enter a valid App Token");
                }
            }
        });


        /*Button BRButton = findViewById(R.id.br);
        Button CRButton = findViewById(R.id.CR);

        BRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BugReportingActivity.class);
                startActivity(intent);
            }
        });

        CRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CrashReportingButton","clicked");
                Intent intent = new Intent(MainActivity.this, CrashReportingActivity.class);
                startActivity(intent);
            }
        });*/


    }
}