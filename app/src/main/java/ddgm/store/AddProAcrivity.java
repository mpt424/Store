package ddgm.store;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import ddgm.store.utils.Product;

public class AddProAcrivity extends BaseActivity {

    EditText dep;
    EditText name;
    EditText sup;
    EditText stock;
    EditText order;
    EditText price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pro_acrivity);

        dep = (EditText)findViewById(R.id.dep_in);
        name = (EditText)findViewById(R.id.namep_in);
        sup = (EditText)findViewById(R.id.supl_in);
        stock = (EditText)findViewById(R.id.stock_in);
        order = (EditText)findViewById(R.id.order_in);
        price = (EditText)findViewById(R.id.price_in);

    }

    public void addTheProduct(View v){

        String department = dep.getText().toString();
        String pname = name.getText().toString();
        String supplier = sup.getText().toString();
        String stocks = stock.getText().toString();
        String orders = order.getText().toString();
        String uprice = price.getText().toString();
        String[] arr = {department,pname,supplier,stocks,orders,uprice};

        //check that evreything is full
        for(String s : arr)
            if(s == "") {
                Toast.makeText(this, "All field must be full...", Toast.LENGTH_SHORT).show();
                return;
            }

        Product p = new Product(department,pname,Integer.parseInt(supplier),Integer.parseInt(stocks),Integer.parseInt(orders),Double.parseDouble(uprice));

        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        String key = mDatabase.child("products").push().getKey();
        mDatabase.child("products").child(key).setValue(p);


         dep.setText("");
         name.setText("");
         sup.setText("");
         stock.setText("");
         order.setText("");
         price.setText("");




    }
}
