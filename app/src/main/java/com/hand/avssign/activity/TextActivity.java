package com.hand.avssign.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.hand.avssign.R;

public class TextActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        edit = findViewById(R.id.edit);
        edit.setText(MainActivity.petitionText);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_ok:
                intent.putExtra(MainActivity.KEY_TEXT, edit.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                return;
            case R.id.btn_cancel:
                setResult(RESULT_CANCELED, intent);
                finish();
        }
    }
}
