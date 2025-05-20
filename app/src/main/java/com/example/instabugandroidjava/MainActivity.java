package com.example.instabugandroidjava;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.instabug.library.Instabug;
import com.instabug.library.invocation.InstabugInvocationEvent;

import java.io.Console;

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
        Button sdkInitializationBtn = findViewById(R.id.sdkInitializationBtn);

        sdkInitializationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = appTokenInput.getText().toString().trim();
                if (!token.isEmpty()){
                    new Instabug.Builder(MainActivity.this.getApplication(), token)
                            .setInvocationEvents(InstabugInvocationEvent.NONE)
                            .build();
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