package com.example.scannerapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnScan, btnView;
    TextView tvScanContent, tvScanFormat;
    String code;
    FirebaseHandler firebaseHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnScan=findViewById(R.id.btnScan);
        btnScan.setOnClickListener(this);
        tvScanContent = findViewById(R.id.tvScanContent);
        tvScanFormat = findViewById(R.id.tvScanFormat);
        btnView = findViewById(R.id.btnView);
        btnView.setOnClickListener(this);
        firebaseHandler = new FirebaseHandler();
    }

    public void startScan() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Scan a barcode");
        integrator.setOrientationLocked(true);
        integrator.initiateScan();
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()){
            case R.id.btnScan:
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setPrompt("Scan a barcode");
            integrator.setOrientationLocked(true);
            integrator.initiateScan();
            break;
            case R.id.btnView:
                Intent i = new Intent(this,ViewItems.class);
                startActivity(i);
                break;

        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if (result !=null){
            if(result.getContents()==null){
                Toast.makeText(getBaseContext(),"Cancelled",Toast.LENGTH_LONG).show();
            }
            if(firebaseHandler.isExist(result.getContents()))
            {
                Toast.makeText(getBaseContext(),"Exists",Toast.LENGTH_LONG).show();
            }
            else{
                tvScanFormat.setText(result.getFormatName());
                tvScanContent.setText(result.getContents());
                code = result.getContents();

                switchAfterScan();

            }


        }
        else {
            super.onActivityResult(requestCode,resultCode,data);

        }


    }

    private void switchAfterScan(){
        Intent switchActInt = new Intent(this,DBActivity.class);
        switchActInt.putExtra("variable",code);
        startActivity(switchActInt);


    }
}