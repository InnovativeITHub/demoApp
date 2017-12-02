package user.innovative.demoapp;

import android.Manifest;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;

import user.innovative.demoapp.camera.CameraImageView;
import user.innovative.demoapp.config.AppConstant;
import user.innovative.demoapp.custom_gallery.CustomGallery;
import user.innovative.demoapp.util.CommonUtil;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_CODE = 901;
    private static final int REQUEST_CUSTOM_GALLERY_CODE = 902;
    private static final int REQUEST_SINGLE_PICK_IMAGE_CODE = 903;
    private static final int REQUEST_MULTIPLE_PICK_IMAGE_CODE = 904;
    private static final int REQUEST_MULTIPLE_PICK_DATA_TO_SHARE = 905;

    private Uri fileUri;
    private File filepath, dir;

    Button btn_camera, btn_custom_gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findIds();
        init();

    }

    private void findIds() {

        btn_camera = (Button) findViewById(R.id.btn_camera);
        btn_custom_gallery = (Button) findViewById(R.id.btn_custom_gallery);
    }

    private void init() {

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraView();
            }
        });

        btn_custom_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customGallery();
            }
        });


    }

    private void cameraView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (CommonUtil.checkAndRequestPermission(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    AppConstant.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)) {
                cameraIntent();
            }
        } else {
            cameraIntent();
        }
    }

    /*Through Camera*/
    public void cameraIntent() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(getExternalCacheDir(), String.valueOf(System.currentTimeMillis()) + ".jpg");
        fileUri = Uri.fromFile(file);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        //check is there any app available to do this
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE);
        }
    }

    private void customGallery() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (CommonUtil.checkAndRequestPermission(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    AppConstant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)) {
                customGalleryIntent();
            }
        } else {
            customGalleryIntent();
        }
    }

    private void customGalleryIntent() {
        Intent intent = new Intent(getApplicationContext(), CustomGallery.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case REQUEST_CAMERA_CODE:

                String getData = getImageContentUri(getApplicationContext(),
                        new File((fileUri + "").substring(7, (fileUri + "").length()))) + "";

                Intent intent = new Intent(getApplicationContext(), CameraImageView.class);
                intent.putExtra("image", getData);
                startActivity(intent);

                break;

            case REQUEST_CUSTOM_GALLERY_CODE:

                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case AppConstant.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraIntent();
                } else {
                    CommonUtil.checkAndRequestPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE, AppConstant.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                }

                break;

            case AppConstant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    customGalleryIntent();
                } else {
                    CommonUtil.checkAndRequestPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, AppConstant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }

                break;

            case AppConstant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_SINGLE_PICKER:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    CommonUtil.checkAndRequestPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, AppConstant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }

                break;

            case AppConstant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_MULTIPLE_PICKER:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    CommonUtil.checkAndRequestPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, AppConstant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }

                break;
        }
    }


    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }


}
