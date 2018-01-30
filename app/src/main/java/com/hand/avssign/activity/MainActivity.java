package com.hand.avssign.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.circulardialog.CDialog;
import com.example.circulardialog.extras.CDConstants;
import com.hand.avssign.R;
import com.hand.avssign.api.ApiFactory;
import com.hand.avssign.api.ErrorUtils;
import com.hand.avssign.model.AccessToken;
import com.hand.avssign.model.SignatureItself;
import com.hand.avssign.utils.FileUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY_COLOR = "Color";
    public static final String KEY_COLOR_INDEX = "ColorIndex";
    public static final String KEY_THICKNESS = "Thickness";
    public static final String KEY_THICKNESS_INDEX = "ThicknessIndex";
    public static final String KEY_API_URL = "Api URL";
    public static final String KEY_DEPARTMENT = "Department";
    public static final String KEY_SECRET_CODE = "Secret code";
    public static final String KEY_TEXT = "Text";
    public static final int CODE_SIGN = 1;
    public static final int CODE_TEXT = 2;
    public static final int CODE_SETTINGS = 3;

    private static final int READ_STORAGE_REQUEST = 3124;
    public static final String SIGNATURE_PATH = "signature.jpg";
    public static final String SIGNATURE2_PATH = "signature2.jpg";

    public static final String ROOT_URL = "http://app.avs.com.ru/psa/";
    public static final String TOKEN_REQUEST_CODE = "202cb962ac59075b964b07152d234b70";
    public static final int PARAMETER_DEPARTMENT = 5;
    public static final String PARAMETER_ID = "45";

    public static Integer selectedColor;
    public static int selectedColorIndex;
    public static Integer selectedThickness;
    public static int selectedThicknessIndex;
    public static String api_url;
    public static int department;
    public static String secret_code;
    public static String petitionText;
    public static String token;

    private SharedPreferences sp;
    public static Boolean tokenAcquired;

    //Button btnSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //btnSign = findViewById(R.id.btn_sign);
        loadPreferences();
        token = "";
        tokenAcquired = false;
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_REQUEST);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_sign:
                intent = new Intent(this, SignUniqueActivity.class);
                startActivityForResult(intent, CODE_SIGN);
                break;
            case R.id.btn_text:
                intent = new Intent(this, TextActivity.class);
                startActivityForResult(intent, CODE_TEXT);
                break;
            case R.id.btn_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent, CODE_SETTINGS);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((data == null) || (resultCode == RESULT_CANCELED)) { return; }
        switch (requestCode) {
            case CODE_SIGN:
                sendSignatures();
                break;
            case CODE_TEXT:
                petitionText = data.getStringExtra(KEY_TEXT);
                break;
            case CODE_SETTINGS:
                //btnSign.setEnabled(false);
                selectedColor = data.getIntExtra(KEY_COLOR, Color.BLACK);
                selectedColorIndex = data.getIntExtra(KEY_COLOR_INDEX, 0);
                selectedThickness = data.getIntExtra(KEY_THICKNESS, 1);
                selectedThicknessIndex = data.getIntExtra(KEY_THICKNESS_INDEX, 0);
                api_url = data.getStringExtra(KEY_API_URL);
                department = data.getIntExtra(KEY_DEPARTMENT, PARAMETER_DEPARTMENT);
                secret_code = data.getStringExtra(KEY_SECRET_CODE);
                token = "";
                tokenAcquired = false;
                //getToken();
                break;
        }
    }

    @Override
    public void onStop(){
        sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt(KEY_COLOR, selectedColor);
        ed.putInt(KEY_COLOR_INDEX, selectedColorIndex);
        ed.putInt(KEY_THICKNESS, selectedThickness);
        ed.putInt(KEY_THICKNESS_INDEX, selectedThicknessIndex);
        ed.putString(KEY_TEXT, petitionText);
        ed.putString(KEY_API_URL, api_url);
        ed.putInt(KEY_DEPARTMENT, department);
        ed.putString(KEY_SECRET_CODE, secret_code);
        ed.apply();
        super.onStop();
    }

    private void loadPreferences() {
        sp = getPreferences(MODE_PRIVATE);
        selectedColor = sp.getInt(KEY_COLOR, Color.BLACK);
        selectedColorIndex = sp.getInt(KEY_COLOR_INDEX, 0);
        selectedThickness = sp.getInt(KEY_THICKNESS, 1);
        selectedThicknessIndex = sp.getInt(KEY_THICKNESS_INDEX, 0);
        petitionText = sp.getString(KEY_TEXT, "");
        api_url = sp.getString(KEY_API_URL, ROOT_URL);
        department = sp.getInt(KEY_DEPARTMENT, PARAMETER_DEPARTMENT);
        secret_code = sp.getString(KEY_SECRET_CODE, TOKEN_REQUEST_CODE);
    }

    private void sendSignatures(){
        RequestBody description = RequestBody.create(okhttp3.MultipartBody.FORM, getString(R.string.str_file_description));
        File parentDir = new File(imageDir());
        if (!parentDir.exists() || !parentDir.isDirectory()) return;
        File file2 = new File(parentDir, SIGNATURE2_PATH);
        if (!file2.exists()) return;
        File file = new File(parentDir, SIGNATURE_PATH);
        if (!file.exists()) return;
        RequestBody requestFile2 = RequestBody.create(MediaType.parse("image/jpeg"), file2);
        MultipartBody.Part body2 = MultipartBody.Part.createFormData("sign1", file2.getName(), requestFile2);

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("sign2", file.getName(), requestFile);

        Call<SignatureItself> call = ApiFactory.getService().sendSignature(token, PARAMETER_ID, description, body2, body);
        call.enqueue(new Callback<SignatureItself>() {
            @Override
            public void onResponse(Call<SignatureItself> call, Response<SignatureItself> response) {
                if (response.isSuccessful()) {
                    new CDialog(MainActivity.this).createAlert("Подписи успешно отправлены на сервер", CDConstants.SUCCESS, CDConstants.LARGE)
                            .setAnimation(CDConstants.SCALE_FROM_BOTTOM_TO_TOP)
                            .setDuration(5000)
                            .setTextSize(CDConstants.NORMAL_TEXT_SIZE)
                            .show();
                } else if (response.code() == 400) getTokenForSignature();
                else circle(ErrorUtils.errorMessage(response));
            }

            @Override
            public void onFailure(Call<SignatureItself> call, Throwable t) { circle(t.toString());  }
        });
    }

    private void getTokenForSignature() {
        Call<AccessToken> call = ApiFactory.getService().getToken(MainActivity.secret_code);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    MainActivity.token = response.body().getAccessToken();
                    MainActivity.tokenAcquired = true;
                    sendSignatures();
                } else {
                    circle(ErrorUtils.errorMessage(response));
                    MainActivity.token = "";
                    MainActivity.tokenAcquired = false;
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {circle(t.toString()); }
        });
    }

    private void circle(String text) {
        new CDialog(this).createAlert(text, CDConstants.ERROR, CDConstants.LARGE)
                .setAnimation(CDConstants.SCALE_FROM_BOTTOM_TO_TOP)
                .setDuration(5000)
                .setTextSize(CDConstants.NORMAL_TEXT_SIZE)
                .show();
    }

    public static String imageDir() {
        return FileUtils.picturesDirStr() + "/Signatures";
    }
}
