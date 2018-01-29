package com.hand.avssign.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hand.avssign.R;
import com.hand.avssign.view.SignatureView;

public class SignatureActivity extends AppCompatActivity implements View.OnClickListener {

    SignatureView signatureView;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        Intent intent = getIntent();
        path = intent.getStringExtra(MainActivity.KEY_FILE);
        signatureView = findViewById(R.id.signature_view);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_reset:
                signatureView.clear();
                break;
            case R.id.btn_ok:
                signatureView.save(path);
                setResult(RESULT_OK, intent);
                finish();
                return;
            case R.id.btn_cancel:
                setResult(RESULT_CANCELED, intent);
                finish();
        }
    }
}
