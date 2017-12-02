package user.innovative.demoapp.camera;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import user.innovative.demoapp.R;

public class CameraImageView extends AppCompatActivity {

    ImageView iv_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_image_view);

        iv_image = (ImageView) findViewById(R.id.iv_image);

        String imageUri = getIntent().getStringExtra("image");

        Glide.with(getApplicationContext())
                .load(Uri.parse(imageUri))
                .into(iv_image);

    }

}
