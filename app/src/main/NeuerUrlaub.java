package com.example.my_urlaub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.Manifest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.PrimitiveIterator;
import java.util.UUID;

public class NeuerUrlaub extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public TextView mDisplayDateStart;
    public int yearSt;
    public int yearEd;
    public int monthSt;
    public int daySt;
    public int dayEd;
    public int monthEd;
    public int UnixDateStartYear;
    public int UnixDateStartMonth;
    public int UnixDateStartDay;
    public int UnixDateEndYear;
    public int UnixDateEndMonth;
    public int UnixDateEndDay;
    public int UnixDateStart;
    public int UnixDateEnd;
    public boolean BooleanOrt;
    public boolean BooleanStart;
    public boolean BooleanEnd;
    public boolean BooleanDescription;
    public Spinner SpinnerNewUrlaub;
    public Spinner SpinnerNewUrlaubMove;
    private ImageView imageView;
    private static final int PERMISSION_REQUEST = 0;
    private static final int RESULT_LOAD_IMAGE = 1;
    public Button SaveUrlaub;
    public AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText DescriptionUrlaub;
    public Button Save;
    private EditText OrtEingabe;
    private TextView DescriptionNewUrlaub;

    public TextView mDisplayDateEnd;
    private DatePickerDialog.OnDateSetListener mDateSetListenerStart;
    private DatePickerDialog.OnDateSetListener mDateSetListenerEnd;
    private String locFromInterface = "";
    private String dateStartFromInterface = "";
    private String dateEndFromInterface = "";
    private String descrFromInterface = "";

    NotificationCompat.Builder notBuilder = new NotificationCompat.Builder(this);
    private static final String CHANNEL_ID = UUID.randomUUID().toString();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neuer_urlaub);
        DateStartAndEnd();
        ShowImage();
        SaveAndCheckUrlaub();
        clickTextView();
        SpinnerNewUrlaubColor();
        setUpNotifications();
    }
    
    private void setUpNotifications() {
        createNotificationChannel();
    //    notBuilder.setSmallIcon();
        notBuilder.setContentTitle(getString(R.string.notif_title));
        notBuilder.setContentText(getString(R.string.notif_text));
    }
    
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void DateStartAndEnd() {
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
            }
        });

        mDateSetListenerStart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePickerStart, int yearStart, int monthStart, int dayStart) {
                monthStart = monthStart +1;
                String dateStart = dayStart + "." +monthStart + "." + yearStart;
                mDisplayDateStart.setText(dateStart);
                yearSt = yearStart;
                monthSt = monthStart;
                daySt = dayStart;

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



            }
        });

        mDateSetListenerEnd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                String date = day + "." +month + "." + year;
                mDisplayDateEnd.setText(date);
                yearEd = year;
                monthEd = month;
                dayEd = day;

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
                createPDF();
                UnixConverterStart();
                UnixConverterEnd();
                Intent intent = new Intent();
                intent.putExtra("startDate",getStartDate());
                intent.putExtra("endDate", getEndDate());
                intent.putExtra("location", getLocation());
                intent.putExtra("description", getDescription());
                intent.putExtra("imgSrc", getImgSource());
                setResult(Activity.RESULT_OK, intent);
                finish();

                if(UnixDateStart > UnixDateEnd) {
                    Toast.makeText(NeuerUrlaub.this, "Das Enddatum darf nicht vor dem Startdatum sein !", Toast.LENGTH_SHORT).show();
                }
                checkIfEverythingIsFilled();
                System.out.println(BooleanDescription);
                System.out.println(BooleanEnd);
                System.out.println(BooleanOrt);
                System.out.println(BooleanStart);
                if ((BooleanDescription && BooleanEnd && BooleanOrt && BooleanStart) == false ) {

                }else{

                }
            }
        });
    }

    public void SpinnerNewUrlaubColor() {
        SpinnerNewUrlaub = findViewById(R.id.SpinnerUrlaubColor);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Color_Array, android.R.layout.simple_list_item_activated_1);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);

        SpinnerNewUrlaub.setAdapter(adapter);
        SpinnerNewUrlaub.setOnItemSelectedListener(this);
   }

  /*  private void SpinnerUrlaubMove() {
        SpinnerNewUrlaubMove = findViewById(R.id.SpinnerUrlaubMove);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Move_Array, android.R.layout.simple_list_item_activated_2);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_2);
        SpinnerNewUrlaubMove.setAdapter(adapter);
        SpinnerNewUrlaubMove.setOnItemSelectedListener(this);


    }

*/


    public void checkIfEverythingIsFilled(){
        if(OrtEingabe.getText().toString() != "" ) {
            BooleanOrt = true;

        }
        if (mDisplayDateStart.getText().toString() != "") {
            BooleanStart = true;

        }
        if (mDisplayDateEnd.getText().toString() != "") {
            BooleanEnd = true;

        }
        if (DescriptionNewUrlaub.getText().toString() != "Geben Sie eine Beschreibung für ihren Urlaub ein") {
            BooleanDescription = true;

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


    public void UnixConverterStart() {
        UnixDateStartYear = (yearSt - 1970) * 365 ;
        UnixDateStartMonth = (monthSt -1)  * 30 ;
        UnixDateStartDay = daySt - 1;
        UnixDateStart = UnixDateStartYear + UnixDateStartMonth + UnixDateStartDay;
    }
    public void UnixConverterEnd() {
        UnixDateEndYear = (yearEd - 1970) * 365 ;
        UnixDateEndMonth = (monthEd -1)  * 30 ;
        UnixDateEndDay = dayEd - 1;
        UnixDateEnd = UnixDateEndYear + UnixDateEndMonth + UnixDateEndDay;
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

    private void createPDF() {


        PdfDocument myPDFCocument = new PdfDocument();
        PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(1200,2010,1).create();
        PdfDocument.Page myPage1 = myPDFCocument.startPage(myPageInfo1);
        myPDFCocument.finishPage(myPage1);


        File file = new File(Environment.getExternalStorageDirectory(),"/Hello.pdf");
        try {
            myPDFCocument.writeTo(new FileOutputStream(file));

        } catch (IOException e) {
            e.printStackTrace();
        }
        myPDFCocument.close();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public String getStartDate(){
        String startDate = mDisplayDateStart.getText().toString();
        return startDate;
    }

    public String getEndDate(){
        String endDate = mDisplayDateEnd.getText().toString();
        return endDate;
    }

    public String getLocation(){
        String location = OrtEingabe.getText().toString();
        return location;
    }

    public String getDescription(){
        String description = DescriptionNewUrlaub.getText().toString();
        return description;
    }
    
    public String getImgSource(){
        String imgSource = imageView.toString();
        return imgSource;
    }

    public Urlaub newUrlaub(){
        return new Urlaub(getLocation(),getStartDate(), getEndDate(), getDescription(), getImgSource());
    }
}





