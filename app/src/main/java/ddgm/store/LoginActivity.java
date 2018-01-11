package ddgm.store;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class LoginActivity extends BaseActivity {
    final int PERMISSION_REQUEST_CODE = 123;
    private  Intent intent;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TAG = "LOGIN";

        intent = getIntent();

        String str = intent.getStringExtra("username");
        if(str!=null && !str.equals("")) {
            TextView username = (TextView) findViewById(R.id.name_in);
            username.setText(str);
        }

        checkPermission(getBaseContext());


    }




    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean checkPermission(Context context){
        String result = Manifest.permission.ACCESS_FINE_LOCATION;
        String result1 = Manifest.permission.READ_CONTACTS;
        String result2= Manifest.permission.ACCESS_COARSE_LOCATION;
        String result3 = Manifest.permission.GET_ACCOUNTS;
        String result4 = Manifest.permission.CAMERA;
        String result5 = Manifest.permission.READ_EXTERNAL_STORAGE;
        String[] permissions = new String[]{result,result1,result2,result3,result4,result5};

        for(String res : permissions)
        if (getBaseContext().checkSelfPermission(res)!= PackageManager.PERMISSION_GRANTED){
                requestPermission(context,res);

            }

            return true;
    }

    private void requestPermission(Context  context,String perID){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,perID)){

            Toast.makeText(context," Need to allow "+perID,Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(this,new String[]{perID},PERMISSION_REQUEST_CODE);
        }
    }

    public void openDetails(FirebaseUser user){
        Intent intent1 = new Intent(LoginActivity.this,DetailsActivity.class);
        startActivity(intent1);
    }

    public void openRegActivity(View view){
        Intent intent1 = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent1);
    }

    public void openReadmeActivity(View view){
        Intent intent1 = new Intent(LoginActivity.this,ReadmeActivity.class);
        startActivity(intent1);
    }

    public void openMapActivity(View view){
        Intent intent1 = new Intent(LoginActivity.this,MapsActivity.class);
        startActivity(intent1);
    }

    public void signIn(View v){
        EditText user = (EditText)findViewById(R.id.name_in);
        EditText passw = (EditText)findViewById(R.id.pswd_in);

        mAuth.signInWithEmailAndPassword(user.getText().toString(), passw.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(LoginActivity.this, "Confirm",
                                    Toast.LENGTH_SHORT).show();
                            openDetails(mAuth.getCurrentUser());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}
