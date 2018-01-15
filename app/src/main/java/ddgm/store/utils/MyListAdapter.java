package ddgm.store.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;

import ddgm.store.R;


/**
 * Created by mpt on 15/01/2018.
 */

public class MyListAdapter extends ArrayAdapter<Product> {

    private ArrayList<Product> data;

    public MyListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }
    public MyListAdapter(Context context, int resource, ArrayList<Product> items){

        super(context,resource,items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.item, null);
        }

        Product p = getItem(position);

        TextView name = (TextView)v.findViewById(R.id.name_o);
        TextView price = (TextView)v.findViewById(R.id.price_o);
        TextView dep = (TextView)v.findViewById(R.id.dep_o);
        TextView onstok = (TextView)v.findViewById(R.id.stk_o);
        Button add = (Button)v.findViewById(R.id.addtocart);
        EditText qua = (EditText)v.findViewById(R.id.qua_o);
        if(p != null) {
            //set item tags
            name.setText(p.getName());
            price.setText("Price: " + p.getUnitPrice() + "$");
            dep.setText(" Dpartment: " + p.getDepartment());
            onstok.setText(" Unit In Stock: " + p.getUnitInStock());

            //add to cart and update the database
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }

        return v;
    }
}
