package ddgm.store;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import ddgm.store.utils.User;

public class DetailsActivity extends BaseActivity {
    TextView userName;
    TextView firstName;
    TextView lastName;
    TextView address;
    TextView city;
    TextView isMan;
    TextView email;
    ImageView pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        userName=(TextView)findViewById(R.id.userNamesStatic);
        firstName=(TextView)findViewById(R.id.firstNameStatic);
        lastName=(TextView)findViewById(R.id.lastNameStatic);
        address=(TextView)findViewById(R.id.addessStatic);
        city=(TextView)findViewById(R.id.cityStatic);
        email=(TextView)findViewById(R.id.emailStatic);
        isMan=(TextView)findViewById(R.id.user_type);
        pic=(ImageView) findViewById(R.id.imageView);


        final FirebaseUser user = mAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            userName.setText(user.getDisplayName());
            email.setText(user.getEmail());
            String userID = mDatabase.push().getKey();
            mDatabase.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User usr = dataSnapshot.getValue(User.class);
                    boolean manage = (boolean)dataSnapshot.child("manager").getValue();
                    userName.setText(usr.getUserName());
                    address.setText(usr.getAddress());
                    firstName.setText(usr.getFirsrName());
                    lastName.setText(usr.getLastName());
                    city.setText(usr.getCity());
                    /*id is manager the update the label*/
                    if(manage){
                        isMan.setText("[Manager]");
                        isMan.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            File localFile = null;
            try {
                localFile = File.createTempFile("images", "jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
            StorageReference riversRef = mStorageRef.child("images/users/"+user.getUid()+"/image.jpg");

            final File finalLocalFile = localFile;
            riversRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Successfully downloaded data to local file
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                            Bitmap image = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath(), options);
                            pic.setImageBitmap(image);
                            Toast.makeText(DetailsActivity.this,"success download photo",Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    Toast.makeText(DetailsActivity.this,"faild download photo",Toast.LENGTH_SHORT).show();
                }
            });
            //Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            //String uid = user.getUid();
        }
    }
}
