package com.sergei.permissiondemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Layout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout parent;
    final String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.INTERNET,
            Manifest.permission.BLUETOOTH };

    private boolean hasPermissions(Context context, String... permissions){
        if(context != null && permissions != null){
            for(String permission : permissions){
                if(ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parent = findViewById(R.id.mainLayout);
        findViewById(R.id.btnPermission).setOnClickListener(view -> onPermission());
        if(!hasPermissions(this, permissions))ActivityCompat.requestPermissions(this, permissions, 1);
    }

    private void onPermission() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Access to camera given!", Toast.LENGTH_SHORT).show();
        }else{
            requestCameraPermission();
        }
    }

    private void requestCameraPermission() {
        //Permission has not been granted and must be requested

        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
            Snackbar.make(parent, "Camera access needed because ...", Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, view->
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.CAMERA}, 100)).show();
                    }else{
                        Toast.makeText(this, "Need access to camera!", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                                {Manifest.permission.CAMERA}, 100);
            }
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1){
            for(int i = 0; i<permissions.length; i++){
                if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, permissions[i]+" granted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, permissions[i]+" denied", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if(requestCode == 100){
                if(grantResults[3]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, permissions[3]+" granted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, permissions[3]+" denied", Toast.LENGTH_SHORT).show();
                }

        }
    }
}
