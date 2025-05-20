package com.example.instabugandroidjava;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.instabug.library.Instabug;
import com.instabug.library.ui.onboarding.WelcomeMessage;

public class sdkSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sdk_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText emailIdentifier = findViewById(R.id.emailIdentificationInput);
        EditText usernameIdentifier = findViewById(R.id.usernameIdentificationInput);
        EditText idIdentifier = findViewById(R.id.idIdentificationInput);
        Button identifyUser = findViewById(R.id.identifyUserBtn);
        EditText attributeKeyInput = findViewById(R.id.userAttributeKeyInput);
        EditText attributeValueInput = findViewById(R.id.userAttributeValueInput);
        Button addAttribute = findViewById(R.id.addAttributeButton);
        RadioGroup welcomeMessageOptions = findViewById(R.id.welcomeMessageOptionsGroup);
        Button saveChanges = findViewById(R.id.saveChangesBtn);

        identifyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailIdentifier.getText().toString().trim();
                String username = usernameIdentifier.getText().toString().trim();
                String id = idIdentifier.getText().toString().trim();

                if (email.isEmpty()){
                    email = null;
                }
                if (username.isEmpty()){
                    username= null;
                }
                if(id.isEmpty()){
                    id = null;
                }
                Instabug.identifyUser(username, email, id);
                emailIdentifier.setText("");
                usernameIdentifier.setText("");
                idIdentifier.setText("");
                Toast.makeText(sdkSettingsActivity.this, "User identification saved successfully", Toast.LENGTH_SHORT).show();
            }
        });

        addAttribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String attributeKey = attributeKeyInput.getText().toString().trim();
                String attributeValue = attributeValueInput.getText().toString().trim();
                if (!attributeKey.isEmpty() && !attributeValue.isEmpty()){
                    Instabug.setUserAttribute(attributeKey, attributeValue);
                    attributeKeyInput.setText("");
                    attributeValueInput.setText("");
                    Toast.makeText(sdkSettingsActivity.this, "User attribute saved successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedID = welcomeMessageOptions.getCheckedRadioButtonId();
                if (selectedID == R.id.welcomeMessageLive){
                    Instabug.setWelcomeMessageState(WelcomeMessage.State.LIVE);
                } else if (selectedID == R.id.welcomeMessageBeta) {
                    Instabug.setWelcomeMessageState(WelcomeMessage.State.BETA);
                } else if (selectedID == R.id.welcomeMessageDisabled) {
                    Instabug.setWelcomeMessageState(WelcomeMessage.State.DISABLED);
                }
               welcomeMessageOptions.clearCheck();
            }
        });

    }
}