package ddgm.store.utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ddgm.store.R;
import ddgm.store.RegisterActivity;

/**
 * Created by mpt on 28/12/2017.
 */

public class ChooseFile {

    private List<String> files = new ArrayList<>();
    File root;
    File current;
    ListView filelist;
    TextView textfolder;
    Button buttonUp;

    public ChooseFile(){
         root = new File(Environment.getExternalStorageDirectory().getPath());
         current = root;
    }

    public void dialog(final RegisterActivity activity){
        final Dialog dial  = new Dialog(activity);
        dial.setContentView(R.layout.choosefile_dial);
         textfolder = (TextView) dial.findViewById(R.id.folder);
         filelist = (ListView)dial.findViewById(R.id.file_list);
         buttonUp = (Button)dial.findViewById(R.id.rrtrn_btn);
        textfolder.setText(root.getPath());
        final File[] selected = {null};
        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listDir(current.getParentFile(),activity);
            }
        });

        filelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 selected[0] = new File(files.get(i));
                if(selected[0].isDirectory()){
                    listDir(selected[0],activity);
                }else{
                    if(endWith(selected[0].getName())) {
                        Toast.makeText(activity, "Selected " + selected[0].getAbsolutePath(), Toast.LENGTH_SHORT).show();
                        activity.setImage(selected[0]);
                        dial.dismiss();
                    }
                    else
                        Toast.makeText(activity,"only JPG or PNG allowed ",Toast.LENGTH_SHORT).show();
                }
            }
        });

        listDir(current,activity);
        dial.show();


    }

    private void listDir(File f,RegisterActivity activity){
        if(f.equals(root)){
            buttonUp.setEnabled(false);
        }else{
            buttonUp.setEnabled(true);
        }
        current = f;

        textfolder.setText(f.getPath());
        File[] filess = f.listFiles();
        files.clear();

        if(filess!=null)
         for(File fil : filess){
            files.add(fil.getPath());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1,files);

        filelist.setAdapter(adapter);
    }

    public boolean endWith(String s) {

        return s.endsWith( "jpg" ) || s.endsWith( "png" );

    }
}
