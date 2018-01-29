package com.hand.avssign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.circulardialog.CDialog;
import com.example.circulardialog.extras.CDConstants;
import com.hand.avssign.R;
import com.hand.avssign.api.ApiFactory;
import com.hand.avssign.api.ErrorUtils;
import com.hand.avssign.model.DocumentForSignature;
import com.hand.avssign.model.Passport;
import com.hand.avssign.model.Weighing;
import com.hand.avssign.view.SignatureView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUniqueActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String FORMAT_DATE = "dd.MM.yyyy";
    TextView txtPetition;
    SignatureView signature2;
    GridLayout grid;
    SignatureView signature;
    TextView txtDate;
    Button btnOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_unique);
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
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_reset:
                signature.clear();
                signature2.clear();
                break;
            case R.id.btn_ok:
                signature.save(MainActivity.SIGNATURE_PATH);
                signature2.save(MainActivity.SIGNATURE2_PATH);
                setResult(RESULT_OK, intent);
                finish();
                return;
            case R.id.btn_cancel:
                setResult(RESULT_CANCELED, intent);
                finish();
        }
    }

    private void getDocument() {
        Call<DocumentForSignature> call = ApiFactory.getService().getDocument(MainActivity.token, MainActivity.department);
        Log.d("myLogs", call.request().toString());
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
                            putTextToGrid(String.valueOf(w.getSor())+"%",rowCount + 1,3);
                            putTextToGrid(String.valueOf(w.getNetto()),rowCount + 1,4);
                            putTextToGrid(String.valueOf(w.getPrice()),rowCount + 1,5);
                            putTextToGrid(String.valueOf(w.getSum()),rowCount + 1,6);
                        }
                    } catch (Exception e) { circle("В полученных параметрах имеется ошибка");  }
                } else { circle(ErrorUtils.errorMessage(response)); }
            }

            @Override
            public void onFailure(Call<DocumentForSignature> call, Throwable t) { circle(t.toString()); }
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

    private void circle(String text) {
        new CDialog(this).createAlert(text, CDConstants.ERROR, CDConstants.LARGE)
                .setAnimation(CDConstants.SCALE_FROM_BOTTOM_TO_TOP)
                .setDuration(5000)
                .setTextSize(CDConstants.NORMAL_TEXT_SIZE)
                .show();
    }
}
