package com.example.my_urlaub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.PrimitiveIterator;

public class NeuerUrlaub extends AppCompatActivity {
    private TextView mDisplayDateStart;
    public int monthSt;
    public int monthEd;
    private boolean BooleanOrt;
    private boolean BooleanStart;
    private boolean BooleanEnd;
    private boolean BooleanDescription;
    private boolean BooleanPicture;

    private ImageView imageView;
    private static final int PERMISSION_REQUEST = 0;
    private static final int RESULT_LOAD_IMAGE = 1;
    public Button SaveUrlaub;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText DescriptionUrlaub;
    private Button Save;
    private EditText OrtEingabe;
    private TextView DescriptionNewUrlaub;

    private TextView mDisplayDateEnd;
    private DatePickerDialog.OnDateSetListener mDateSetListenerStart;
    private DatePickerDialog.OnDateSetListener mDateSetListenerEnd;
    private String locFromInterface = "";
    private String dateStartFromInterface = "";
    private String dateEndFromInterface = "";
    private String descrFromInterface = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_neuer_urlaub);
        DateStart();
        ShowImage();
        SaveAndCheckUrlaub();
        clickTextView();


    }

    public void DateStart() {
        mDisplayDateStart = findViewById(R.id.DatumBeginnUrlaubEingabe);
        mDisplayDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calStart = Calendar.getInstance();
                int yearStart = calStart.get(Calendar.YEAR);
                int monthStart = calStart.get(Calendar.MONTH);
                int dayStart = calStart.get(Calendar.DATE);
                DatePickerDialog dialog = new DatePickerDialog(NeuerUrlaub.this,
                        android.R.style.Theme_Material_Dialog,mDateSetListenerStart,yearStart,monthStart,dayStart);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                dialog.show();
                monthSt = monthStart;
            }
        });

        mDateSetListenerStart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePickerStart, int yearStart, int monthStart, int dayStart) {
                monthStart = monthStart +1;
                String dateStart = dayStart + "." +monthStart + "." + yearStart;
                mDisplayDateStart.setText(dateStart);

            }

        };

        mDisplayDateEnd = findViewById(R.id.DatumEndeUrlaubEingabe);
        mDisplayDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DATE);
                DatePickerDialog dialog = new DatePickerDialog(NeuerUrlaub.this,
                        android.R.style.Theme_Material_Dialog,mDateSetListenerEnd,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                dialog.show();
                monthEd = month;


            }
        });

        mDateSetListenerEnd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                String date = day + "." +month + "." + year;
                mDisplayDateEnd.setText(date);

            }
        };



    }





    public void ShowImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST);
        }
        imageView = findViewById(R.id.OnePerfectImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,RESULT_LOAD_IMAGE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_LOAD_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                }
        }


    }

    public void SaveAndCheckUrlaub(){
        SaveUrlaub = findViewById(R.id.SaveButton);
        OrtEingabe = findViewById(R.id.EditTextOrt);

        SaveUrlaub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (monthSt == monthEd) {
                    System.out.println(monthEd);
                    System.out.println(monthSt);
                    Toast.makeText(NeuerUrlaub.this, "Das Enddatum darf nicht vor dem Startdatum sein", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void checkIfEverythingIsFilled(){
        if(OrtEingabe.getText().toString() != "" ) {
            BooleanOrt = true;
            System.out.println(BooleanOrt);
        }
        if (mDisplayDateStart.getText().toString() != "") {
            BooleanStart = true;
            System.out.println(BooleanStart);
        }
        if (mDisplayDateEnd.getText().toString() != "") {
            BooleanEnd = true;
            System.out.println(BooleanEnd);
        }
        if (DescriptionNewUrlaub.getText().toString() != "") {
            BooleanDescription = true;
            System.out.println(BooleanDescription);
        }

    }


    public void openPopUpWindow(){

        dialogBuilder = new AlertDialog.Builder(this);
        final View PopupView = getLayoutInflater().inflate(R.layout.popup_new_urlaub, null);
        DescriptionUrlaub = PopupView.findViewById(R.id.EingabeDescriptionPopUp);
        Save = (Button) PopupView.findViewById(R.id.ButtonPopUpHinzufügen);


        dialogBuilder.setView(PopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                DescriptionNewUrlaub.setText(DescriptionUrlaub.getText().toString());
            }
        });
    }

    public void clickTextView(){
        DescriptionNewUrlaub = findViewById(R.id.DesciptionNewUrlaubAnzeige);
        DescriptionNewUrlaub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPopUpWindow();
                DescriptionUrlaub.setText(DescriptionNewUrlaub.getText());
            }
        });
    }


}
