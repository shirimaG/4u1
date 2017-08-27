package com.example.goodluck.a4u;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.RestrictionEntry;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//hapaaaaaaaaaa
public class HomeSearch extends Activity implements AdapterView.OnItemClickListener {

    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);

//        String[] components ={"Arduino", "gsm shield", "bluetooth model", "Jumpers", "Sensors", "Bread board", "gun", "multimeter", "screw drivers"};
//        ListAdapter compAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,components);
//        ListView generalistview =(ListView) findViewById(R.id.generalistview);
//        generalistview.setAdapter(compAdapter);
//
//        generalistview.setOnItemClickListener(
//                new AdapterView.OnItemClickListener(){
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        String components =String.valueOf(parent.getItemAtPosition(position));
//                        Toast.makeText(HomeSearch.this, components, Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
//        ***********************************************************************************************************
        list= (ListView) findViewById(R.id.listView);
        list.setAdapter(new VivzAdapter(this));
        list.setOnItemClickListener(this);


    }
//##############ON CLICK METHOD#####################
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this," "+position,Toast.LENGTH_SHORT).show();
        // MAIN LIST ITEM ONCLICK LISTENER
        if (position==0) {
            Intent intent = new Intent(this, categoryItem.class);
            startActivity(intent);
        } else if (position==1){
            Intent intent = new Intent(this, categoryItem1.class);
            startActivity(intent);
        } else if(position==2){
            Intent intent = new Intent(this, categoryItem2.class);
            startActivity(intent);
        }else if(position==3){
            Intent intent = new Intent(this, categoryItem3.class);
            startActivity(intent);
        } else if(position==4){
            Intent intent = new Intent(this, categoryItem4.class);
            startActivity(intent);
        } else if(position==5){
            Intent intent = new Intent(this, categoryItem5.class);
            startActivity(intent);
        } else if(position==6){
            Intent intent = new Intent(this, categoryItem6.class);
            startActivity(intent);
        } else if(position==7){
            Intent intent = new Intent(this, categoryItem7.class);
            startActivity(intent);
        } else if(position==8){
            Intent intent = new Intent(this, categoryItem8.class);
            startActivity(intent);
        }

    }
}

// IMPLEMENTING BASE ADAPTER

class singleRow
 {
    String title;
    String description;
    int image;

    singleRow(String title,String description,int image){
        this.title=title;
        this.description= description;
        this.image= image;
    }
}
class VivzAdapter extends BaseAdapter{
    ArrayList<singleRow> list;
    Context context;

    VivzAdapter (Context c){
        context =c;
       list=new ArrayList<singleRow>();
        Resources res = c.getResources();
        String[] titles =res.getStringArray(R.array.titles);
        String[] descriptions =res.getStringArray(R.array.price);
        int[] images = {R.drawable.arduino, R.drawable.bluetoothmodule, R.drawable.gsmmodule, R.drawable.lcddisplay, R.drawable.malefemale, R.drawable.rasp, R.drawable.utrasonicdistancesensor, R.drawable.breadboard, R.drawable.pic};
        for (int i=0;i<9;i++){
            list.add(new singleRow(titles[i], descriptions[i],images[i]));

        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row= inflater.inflate(R.layout.single_row, viewGroup,false );
        TextView title= (TextView) row.findViewById(R.id.name);
        TextView description = (TextView) row.findViewById(R.id.price);
        ImageView image= (ImageView) row.findViewById(R.id.imageView2);

        singleRow temp=list.get(i);

        title.setText(temp.title);
        description.setText(temp.description);
        image.setImageResource(temp.image);
       return row;
    }
}
