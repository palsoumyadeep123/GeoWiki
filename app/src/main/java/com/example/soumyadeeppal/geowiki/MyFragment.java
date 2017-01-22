package com.example.soumyadeeppal.geowiki;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Soumyadeep Pal on 02-08-2016.
 */
public class MyFragment extends Fragment {

    TextView tv1,tv2;

    ImageView iv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView=inflater.inflate(R.layout.myfragment,container,false);

        tv1=(TextView)fragmentView.findViewById(R.id.tv1);

        tv2=(TextView)fragmentView.findViewById(R.id.tv2);

        iv=(ImageView)fragmentView.findViewById(R.id.iv);
        return fragmentView;
    }



    public void setText1(String text)
    {
        tv1.setText(text);
    }

    public void setText2(String text)
    {
        tv2.setText(text);
    }

    public void setImage(Bitmap bmp)
    {
        iv.setImageBitmap(bmp);
    }
}
