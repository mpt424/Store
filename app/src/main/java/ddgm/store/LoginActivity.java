package ddgm.store;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private  Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        intent = getIntent();

        String str = intent.getStringExtra("username");
        if(str!=null && !str.equals("")) {
            TextView username = (TextView) findViewById(R.id.name_in);
            username.setText(str);
        }
    }

    public void openDetails(View view){
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
}
