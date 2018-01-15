package ddgm.store;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ddgm.store.utils.MyListAdapter;
import ddgm.store.utils.Product;
import ddgm.store.utils.User;

public class SearchActivity extends BaseActivity {
    ArrayList<Product> lis = new ArrayList<>();
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        list = (ListView)findViewById(R.id.product_list);

        final FirebaseUser user = mAuth.getInstance().getCurrentUser();

        //products list listener
       DatabaseReference products = mDatabase.child("products").getRef();
        products.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                updateList(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //update the list view
    private void updateList(DataSnapshot dataSnapshot){

        Map<String,Product> map = (HashMap<String,Product>)dataSnapshot.getValue();
        //get the products
        for (Product pro: map.values()) {
            lis.add(pro);

        }
        //set the adapter
        MyListAdapter adapter = new MyListAdapter(this,1,lis);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
