package ddgm.store;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import ddgm.store.utils.ChooseFile;
import ddgm.store.utils.User;

import static android.R.attr.bitmap;

public class RegisterActivity extends BaseActivity {
private String TAG = "REGISTER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1337) {
            photo = (Bitmap) data.getExtras().get("data");
            ImageView imageview = (ImageView) findViewById(R.id.cam_btn); //sets imageview as the bitmap
            imageview.setImageBitmap(photo);

        }
    }
    public void cameraClick(View v){
        AlertDialog.Builder build = new AlertDialog.Builder(RegisterActivity.this);
        build.setTitle("Upload photo");
        build.setMessage("Which way you like to add photo?");
        build.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 1337);
            }
        });


        build.setNegativeButton("Memory", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ChooseFile choose = new ChooseFile();
                choose.dialog(RegisterActivity.this);

            }
        });

        build.show();
    }

    public void registerClick(View v){
        Toast.makeText(getBaseContext(),"register",Toast.LENGTH_SHORT).show();

         street = (EditText)findViewById(R.id.street_in);
         addr = (EditText)findViewById(R.id.addr_in);
         city = (EditText)findViewById(R.id.city_in);
         firstname = (EditText)findViewById(R.id.first_in);
         famname = (EditText)findViewById(R.id.fam_in);
         mail = (EditText)findViewById(R.id.mail_in);
         user = (EditText)findViewById(R.id.usr_in);
         passw = (EditText)findViewById(R.id.pswd_in);
         imageview = (ImageView) findViewById(R.id.cam_btn);

        register(mail.getText().toString(),passw.getText().toString());





    }
    Bitmap photo;

    EditText street;
    EditText addr;
    EditText city;
    EditText firstname;
    EditText famname;
    EditText mail;
    EditText user;
    EditText passw;
    ImageView imageview;

    public void setImage(File f){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap image = BitmapFactory.decodeFile(f.getAbsolutePath(), options);
        ImageView imageview = (ImageView) findViewById(R.id.cam_btn); //sets imageview as the bitmap
        imageview.setImageBitmap(image);
        photo = image;
    }

    private void register(final String email, String password){
        final String usrN = user.getText().toString();
        String frst = firstname.getText().toString();
        String fam = famname.getText().toString();
        String add = addr.getText().toString();
        String ct = city.getText().toString();
        String st = street.getText().toString();

        final User usr = new User(false,usrN,frst,fam,add,ct,st,email);



    try {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser userA = mAuth.getInstance().getCurrentUser();
                            Toast.makeText(getBaseContext(),"register success",Toast.LENGTH_SHORT).show();

                            mDatabase.child("users").child(userA.getUid()).setValue(usr);
                            try {
                                if (photo != null)
                                    uploadPhoto();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            intent.putExtra("username", email);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getBaseContext(),"register failed",Toast.LENGTH_SHORT).show();

                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                             /*analyze how uch user failed to register*/
                            Bundle bundle = new Bundle();
                            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, email);
                            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, usrN);
                            mFirebaseAnalytics.logEvent("FAILURE_SIGN_UP", bundle);
                        }

                        // ...
                    }
                });
    }catch(Exception e){
        e.printStackTrace();
        Toast.makeText(getBaseContext(),"something went wrong",Toast.LENGTH_SHORT).show();

    }

    }

    public void uploadPhoto() throws FileNotFoundException {
        File f = new File(getExternalCacheDir().getAbsolutePath()+"/image.jpg");

        try {
            OutputStream os = new BufferedOutputStream(new FileOutputStream(f));
            photo.compress(Bitmap.CompressFormat.JPEG,100, os);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final FirebaseUser userA = mAuth.getInstance().getCurrentUser();

        Uri file = Uri.fromFile(f);
        StorageReference riversRef = mStorageRef.child("images/users/"+userA.getUid()+"/"+f.getName());

        riversRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                       // Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(RegisterActivity.this,"success upload photo",Toast.LENGTH_SHORT).show();
                         /*analyze how much users upload photo*/
                        Bundle bundle = new Bundle();
                        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, userA.getUid());
                        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, userA.getEmail());
                        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
                        mFirebaseAnalytics.logEvent("IMAGE_UPLOAD", bundle);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...

                        Toast.makeText(RegisterActivity.this,"faild upload photo",Toast.LENGTH_SHORT).show();

                    }
                });
    }
}

