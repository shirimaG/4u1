package com.example.goodluck.a4u;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class categoryItem2 extends Activity {

    ListView list2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item2);
        list2= (ListView) findViewById(R.id.listView2);
        list2.setAdapter(new vivzAdapter3(this));
    }
}


class singleGsm{
    String tittle;
    String description;
    int image;

    singleGsm(String tittle,String description,int image){
        this.tittle=tittle;
        this.description=description;
        this.image=image;
    }
}
class vivzAdapter3 extends BaseAdapter{

    ArrayList<singleGsm> list2;
    Context context;
    vivzAdapter3(Context c){
        context=c;
        list2=new ArrayList<singleGsm>();

        Resources res= c.getResources();
        String[] gsm= res.getStringArray(R.array.gsm);
        String[] gsm_price=res.getStringArray(R.array.gsm_price);
        int[] images={R.drawable.simnine,R.drawable.gsmmodule};

                for (int i=0; i<2; i++){
                    list2.add(new singleGsm(gsm[i],gsm_price[i],images[i]));

                }
    }


    @Override
    public int getCount() {
        return list2.size();
    }

    @Override
    public Object getItem(int i) {
        return list2.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View row= inflater.inflate(R.layout.single_gsm,viewGroup,false);
        TextView title= (TextView) row.findViewById(R.id.gsmDesc);
        TextView description= (TextView) row.findViewById(R.id.gsmPrice);
        ImageView image = (ImageView) row.findViewById(R.id.gsmImage);

        singleGsm temp = list2.get(i);

        title.setText(temp.tittle);
        description.setText(temp.description);
        image.setImageResource(temp.image);
        return row;
    }
}