package com.example.instabugandroidjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProductsNavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_products_navigation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button BRButton = findViewById(R.id.BR);
        Button CRButton = findViewById(R.id.CR);
        Button APMButton = findViewById(R.id.APM);
        Button SURButton = findViewById(R.id.SUR);
        Button FRButton = findViewById(R.id.FR);
        Button SRButton = findViewById(R.id.SR);
        Button sdkSettings = findViewById(R.id.sdkSettings);

        BRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductsNavigationActivity.this, BugReportingActivity.class);
                startActivity(intent);
            }
        });

        CRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductsNavigationActivity.this, CrashReportingActivity.class);
                startActivity(intent);
            }
        });

        APMButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductsNavigationActivity.this, APMActivity.class);
                startActivity(intent);
            }
        });

        SURButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductsNavigationActivity.this, SurveysActivity.class);
                startActivity(intent);
            }
        });

        FRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductsNavigationActivity.this, FeatureRequestsActivity.class);
                startActivity(intent);
            }
        });

        SRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(ProductsNavigationActivity.this, SessionReplayActivity.class);
                startActivity(intent);
            }
        });

        sdkSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductsNavigationActivity.this, sdkSettingsActivity.class);
                startActivity(intent);
            }
        });

    }
}