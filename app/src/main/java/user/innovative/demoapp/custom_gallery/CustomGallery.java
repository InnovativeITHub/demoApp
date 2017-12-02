package user.innovative.demoapp.custom_gallery;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;

import user.innovative.demoapp.R;

public class CustomGallery extends AppCompatActivity {

    RecyclerView rv_gallery_images;

    private GalleryAdapter galleryAdapter;
    private ArrayList<String> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_gallery);

        ArrayList<String> imageUrls = loadPhotosFromNativeGallery();
        galleryAdapter = new GalleryAdapter(imageUrls, new GalleryAdapter.OnImageSelectListener() {
            @Override
            public void onImageSelect(int position, ArrayList<String> imagesList) {
                imageList.clear();
                imageList.addAll(imagesList);

            }
        }, getApplicationContext());

        rv_gallery_images.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_gallery_images.setAdapter(galleryAdapter);

    }

    private ArrayList<String> loadPhotosFromNativeGallery() {
        final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        Cursor imagecursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy + " DESC");
        ArrayList<String> imageUrls = new ArrayList<>();
        for (int i = 0; i < imagecursor.getCount(); i++) {
            imagecursor.moveToPosition(i);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
            imageUrls.add(imagecursor.getString(dataColumnIndex));
        }
        return imageUrls;
    }
}
