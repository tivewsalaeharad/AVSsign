package com.hand.avssign.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hand.avssign.R;
import com.hand.avssign.api.APIError;
import com.hand.avssign.api.ApiFactory;
import com.hand.avssign.api.ErrorUtils;
import com.hand.avssign.model.DocumentForSignature;
import com.hand.avssign.model.Passport;
import com.hand.avssign.model.Weighing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int CODE_SIGNATURE = 8;
    public static final int CODE_SIGNATURE2 = 9;
    private static final String FORMAT_DATE = "dd.MM.yyyy";
    TextView txtPetition;
    ImageView signature2;
    GridLayout grid;
    ImageView signature;
    TextView txtDate;
    Button btnOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        txtPetition = findViewById(R.id.txt_petition);
        signature2 = findViewById(R.id.signature2);
        grid = findViewById(R.id.grid);
        signature = findViewById(R.id.signature);
        txtDate = findViewById(R.id.txt_date);
        btnOK = findViewById(R.id.btn_ok);
        getDocument();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE, Locale.getDefault());
        txtDate.setText(sdf.format(date));
        load();
        load2();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.signature:
                intent = new Intent(this, SignatureActivity.class);
                intent.putExtra(MainActivity.KEY_FILE, MainActivity.SIGNATURE_PATH);
                startActivityForResult(intent, CODE_SIGNATURE);
                break;
            case R.id.signature2:
                intent = new Intent(this, SignatureActivity.class);
                intent.putExtra(MainActivity.KEY_FILE, MainActivity.SIGNATURE2_PATH);
                startActivityForResult(intent, CODE_SIGNATURE2);
                break;
            case R.id.btn_ok:
                setResult(RESULT_OK, intent);
                finish();
                return;
            case R.id.btn_cancel:
                setResult(RESULT_CANCELED, intent);
                finish();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((data == null) || (resultCode == RESULT_CANCELED)) { return; }
        if (requestCode == CODE_SIGNATURE) load();
        else if (requestCode == CODE_SIGNATURE2) load2();
    }

    private void load() {
        try {
            File parentDir = new File(MainActivity.IMAGE_DIR);
            if (parentDir.exists() && parentDir.isDirectory()) Log.d("myLogs", "Exist and directory");
            else return;
            File file = new File(parentDir, MainActivity.SIGNATURE_PATH);
            if (!file.exists()) {
                Log.d("myLogs", MainActivity.SIGNATURE_PATH +" not exist");
                return;
            }
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            signature.getLayoutParams().width = bitmap.getWidth() / 4;
            signature.getLayoutParams().height = bitmap.getHeight() / 4;
            signature.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void load2() {
        try {
            File parentDir = new File(MainActivity.IMAGE_DIR);
            if (parentDir.exists() && parentDir.isDirectory()) Log.d("myLogs", "Exist and directory");
            else return;
            File file = new File(parentDir, MainActivity.SIGNATURE2_PATH);
            if (!file.exists()) {
                Log.d("myLogs", MainActivity.SIGNATURE2_PATH +" not exist");
                return;
            }
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            signature2.getLayoutParams().width = bitmap.getWidth() / 4;
            signature2.getLayoutParams().height = bitmap.getHeight() / 4;
            signature2.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getDocument() {
        Call<DocumentForSignature> call = ApiFactory.getService().getDocument(MainActivity.token, MainActivity.department);
        call.enqueue(new Callback<DocumentForSignature>() {
            @Override
            public void onResponse(Call<DocumentForSignature> call, Response<DocumentForSignature> response) {
                if (response.isSuccessful()) {
                    try {
                        btnOK.setEnabled(true);
                        DocumentForSignature d = response.body();
                        Passport p = d.getPassport();
                        //делаем текст заявления
                        String petition = String.format(getString(R.string.str_petition_format),
                                d.getClient(), p.getSeries(), p.getNumber(), p.getIssuedby(),
                                p.getIssuedate(), p.getRegistration(), MainActivity.petitionText);
                        txtPetition.setText(petition);

                        for (Weighing w : d.getWeighings()) {
                            int rowCount = grid.getRowCount();
                            putTextToGrid(String.valueOf(w.getId()), rowCount + 1, 0);
                            putTextToGrid(String.valueOf(w.getBrutto()),rowCount + 1,1);
                            putTextToGrid(String.valueOf(w.getTare()),rowCount + 1,2);
                            putTextToGrid(String.valueOf(w.getSor()) + "%",rowCount + 1,3);
                            putTextToGrid(String.valueOf(w.getNetto()),rowCount + 1,4);
                            putTextToGrid(String.valueOf(w.getPrice()),rowCount + 1,5);
                            putTextToGrid(String.valueOf(w.getSum()),rowCount + 1,6);
                        }
                    } catch (Exception e) {
                        Toast.makeText(SignActivity.this, "В полученных параметрах имеется ошибка", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(SignActivity.this, ErrorUtils.errorMessage(response), Toast.LENGTH_LONG).show();
                    btnOK.setEnabled(false);
                }
            }

            @Override
            public void onFailure(Call<DocumentForSignature> call, Throwable t) {
                Toast.makeText(SignActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                btnOK.setEnabled(false);
            }
        });
    }

    private void putTextToGrid(String txt, int row, int col)
    {
        GridLayout.LayoutParams textParams = new GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(col));
        textParams.setGravity(Gravity.CENTER_HORIZONTAL);
        textParams.setMargins(2,2,2,2);
        TextView txt_new = new TextView(this);
        txt_new.setTextSize(12);
        txt_new.setText(txt);
        txt_new.setLayoutParams(textParams);
        grid.addView(txt_new);
    }
}
