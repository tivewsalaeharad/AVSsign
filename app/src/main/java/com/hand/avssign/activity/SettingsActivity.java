package com.hand.avssign.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.hand.avssign.R;
import com.hand.avssign.adapter.ColorAdapter;
import com.hand.avssign.adapter.ThicknessAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    Integer[] thicknesses = {1, 2, 4, 7, 10, 14, 17, 21, 25};
    Integer[] colors = {0xff000005, 0xff041e95, 0xff2b1b73, 0xff007129, 0xff950000, 0xfffc2700};
    public static Integer selectedColor;
    private static int selectedColorIndex;
    private static Integer selectedThickness;
    private static int selectedThicknessIndex;
    ColorAdapter colorAdapter;
    ThicknessAdapter thicknessAdapter;
    Spinner colorPicker;
    Spinner thicknessPicker;
    EditText editApiUrl;
    EditText editDepartment;
    EditText editSecretCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        colorPicker = findViewById(R.id.spinner_color_picker);
        thicknessPicker = findViewById(R.id.spinner_thickness_picker);
        editApiUrl = findViewById(R.id.edit_api_url);
        editDepartment = findViewById(R.id.edit_subunit_code);
        editSecretCode = findViewById(R.id.edit_secret_key);
        getSettings();
        initColorPicker();
        initThicknessPicker();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_ok:
                int subunitCode;
                try { subunitCode = Integer.parseInt(editDepartment.getText().toString()); }
                catch (NumberFormatException e) { subunitCode = MainActivity.PARAMETER_DEPARTMENT; }
                intent.putExtra(MainActivity.KEY_COLOR, selectedColor);
                intent.putExtra(MainActivity.KEY_COLOR_INDEX, selectedColorIndex);
                intent.putExtra(MainActivity.KEY_THICKNESS, selectedThickness);
                intent.putExtra(MainActivity.KEY_THICKNESS_INDEX, selectedThicknessIndex);
                intent.putExtra(MainActivity.KEY_API_URL, editApiUrl.getText().toString());
                intent.putExtra(MainActivity.KEY_DEPARTMENT, subunitCode);
                intent.putExtra(MainActivity.KEY_SECRET_CODE, editSecretCode.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                return;
            case R.id.btn_cancel:
                setResult(RESULT_CANCELED, intent);
                finish();
        }
    }

    private void getSettings() {
        selectedColor = MainActivity.selectedColor;
        selectedColorIndex = MainActivity.selectedColorIndex;
        selectedThickness = MainActivity.selectedThickness;
        selectedThicknessIndex = MainActivity.selectedThicknessIndex;
        editApiUrl.setText(MainActivity.api_url);
        editDepartment.setText(String.valueOf(MainActivity.department));
        editSecretCode.setText(MainActivity.secret_code);
    }

    private void initColorPicker() {
        colorAdapter = new ColorAdapter(this, new ArrayList<>(Arrays.asList(colors)));
        colorPicker.setAdapter(colorAdapter);
        colorPicker.setSelection(selectedColorIndex);
        colorPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedColor = colors[position];
                selectedColorIndex = position;
                View thicknessView = thicknessPicker.getSelectedView();
                if (thicknessView != null) thicknessView.invalidate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }

    private void initThicknessPicker() {
        thicknessAdapter = new ThicknessAdapter(this, new ArrayList<>(Arrays.asList(thicknesses)));
        thicknessPicker.setAdapter(thicknessAdapter);
        thicknessPicker.setSelection(selectedThicknessIndex);
        thicknessPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedThickness = thicknesses[position];
                selectedThicknessIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

}
