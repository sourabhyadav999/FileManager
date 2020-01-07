package com.yadavsourabh4035.com.intern;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import java.util.ArrayList;

import  android.os.Message;

public class MainActivity extends Activity {
    @SuppressLint("HandlerLeak")

    private static final String TAG = "MainActivity";
    File file;
    Button butupDirectory, butsdCard, audio;
    ArrayList<String> pathhistory;
    String lastDirectory;
    int count = 0;
    ListView li;
    File myExternalFile;








    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        li = findViewById(R.id.list);
        butupDirectory = findViewById(R.id.butupDirectory);
        butsdCard = findViewById(R.id.Storage);





        checkFilepermission();
        li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lastDirectory = pathhistory.get(count);
                if (lastDirectory.equals(adapterView.getItemAtPosition(i))) {
                    Log.d(TAG, "IN INTERNAL STORAGE" + lastDirectory);
                    readData(lastDirectory);
                } else {
                    count++;
                    pathhistory.add(count, (String) adapterView.getItemAtPosition(i));
                    checkInternalStorage();
                    Log.d(TAG, "IN INTERNAL STORAGE" + pathhistory.get(count));
                }

            }
        });
        butupDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    Log.d(TAG, "but up directory");
                } else {
                    pathhistory.remove(count);
                    count--;
                    checkInternalStorage();
                    Log.d(TAG, "But up directory" + pathhistory.get(count));
                }
            }
        });
        butsdCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 0;
                pathhistory = new ArrayList<>();
                pathhistory.add(count, System.getenv("EXTERNAL_STORAGE"));
                Log.d(TAG, "but sd card" + pathhistory.get(count));
                checkInternalStorage();
            }
        });



    }






    @RequiresApi(api = Build.VERSION_CODES.O)
    private void readData(final String filepath) {
        String[] ids;
        File inputfile = new File(filepath);



        try {
                InputStream inputStream = new FileInputStream(inputfile);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));


            try {
                    int c;

                    while ((c = reader.read()) != -1)
                    {



// Data read. Perform what you want to do



                    }
                   // startActivity(new Intent(getApplicationContext(),show.class));





            }catch (Exception e){
                Log.e("unknown", e.toString());

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }













    private void checkInternalStorage() {
        Log.d(TAG, "check internal storage");
        try{
            if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                Toast.makeText(this,"NO SD CARD FOUND", Toast.LENGTH_SHORT).show();
        }
        else{
                file = new File(pathhistory.get(count));
                Log.d(TAG, "check internal storage "+pathhistory.get(count));
            }
            File[] listfile = file.listFiles();
            String[] filepathstring = new String[listfile.length];
            String[] filenamestring = new String[listfile.length];
            for(int i = 0; i< listfile.length; i++){
                filepathstring[i] = listfile[i].getAbsolutePath();
                filenamestring[i]= listfile[i].getName();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filepathstring);
            li.setAdapter(adapter);

        }
        catch (NullPointerException e){
            Log.d(TAG, " check " + e.getMessage());

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkFilepermission() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissioncheck = this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            permissioncheck += this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissioncheck+= this.checkSelfPermission("Manifest.permission.CALL_PHONE");
            if(permissioncheck != 0){
                this.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE},1);
            }
        }

    }







}





