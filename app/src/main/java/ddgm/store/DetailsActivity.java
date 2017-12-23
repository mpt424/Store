package ddgm.store;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    TextView userName;
    TextView firstName;
    TextView lastName;
    TextView address;
    TextView city;
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
        pic=(ImageView) findViewById(R.id.imageView);
    }
}
