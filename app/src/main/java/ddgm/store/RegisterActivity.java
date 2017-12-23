package ddgm.store;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        Button rgstr = (Button)findViewById(R.id.reg_btn);
        ImageButton camera = (ImageButton)findViewById(R.id.cam_btn);

        rgstr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView usrname = (TextView)findViewById(R.id.usr_in);
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                intent.putExtra("username",usrname.getText());
                startActivity(intent);

            }
        });



        camera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 1337);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1337) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            ImageView imageview = (ImageView) findViewById(R.id.img_view); //sets imageview as the bitmap
            imageview.setImageBitmap(image);
        }
    }

}

