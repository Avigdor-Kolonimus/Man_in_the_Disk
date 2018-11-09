/**
 * The MIDT lab for cyber lesson
 * @author Alexey Titov
 * @date 11.2018
 * @version 1.0
 */
package com.lab2.midt;
//libraries
import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {
    //variables
    private Button btCrash;
    private static final int REQUEST_CODE_PERMISSION = 1;
    AlertDialog.Builder alert1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        alert1 = new AlertDialog.Builder(this);
        //
        btCrash = (Button) findViewById(R.id.button);
        btCrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkWritingPermission();
            }
        });
    }
    public void OKcrush(){
        alert1.setMessage("OK crush");
        alert1.setCancelable(true);
        alert1.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = alert1.create();
        alert.show();
    }
    public void Nocrush(){
        alert1.setMessage("NO crush");
        alert1.setCancelable(true);
        alert1.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = alert1.create();
        alert.show();
    }
    //check if folder exists
    public boolean isNameFolder(String name){
        //checks if external storage is available for read and write
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            //get the path to Folder on SD
            String PathName=Environment.getExternalStorageDirectory().toString() +
                    File.separator + name;
            File dir = new File(PathName);
            if(dir.exists() && dir.isDirectory()) {
                ReadAndWrite(PathName);
                return true;
            }
        }
        return false;
    }
    //function reads name files from path
    public void ReadAndWrite(String path){
        File rootFolder=new File(path);
        File[] filesArray=rootFolder.listFiles();
        for(File f:filesArray){
            if (f.isDirectory()){

            }else{
                String filepath=f.getPath();
                if (filepath.endsWith(".txt")) {
                    PrintWriter writer = null;
                    try {
                        writer = new PrintWriter(f);
                        writer.print("XAXA misha");
                        writer.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    //check permission
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted
                if(isNameFolder("Test"))        //name folder of attack
                    OKcrush();
                else
                    Nocrush();
            } else {
                // permission wasn't granted
                Nocrush();
            }
        }
    }

    private void checkWritingPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // permission wasn't granted
                Nocrush();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
            }
        }else{
            if(isNameFolder("Test"))            //name folder of attack
                OKcrush();
            else
                Nocrush();
        }
    }
}
