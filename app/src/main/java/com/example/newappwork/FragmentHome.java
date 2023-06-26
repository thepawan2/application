package com.example.newappwork;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.net.URL;
import java.util.ArrayList;


public class FragmentHome extends Fragment {



    public FragmentHome() {
        // Required empty public constructor
    }


    Button btn1;
    ImageSlider imageslider;

    RecyclerView recyclerView;


    ArrayList<classDataforcontent> classData = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recycler);
        btn1= view.findViewById(R.id.bt);
        imageslider = view.findViewById(R.id.image_slider);


        //Recycle view .............................................................................




        classData.add(new classDataforcontent(R.drawable.alogo,"Class 12"));
        classData.add(new classDataforcontent(R.drawable.alogo,"Class 11"));
        classData.add(new classDataforcontent(R.drawable.alogo,"Class 10"));
        classData.add(new classDataforcontent(R.drawable.alogo,"Class 9"));
        classData.add(new classDataforcontent(R.drawable.alogo,"Class 8"));
        classData.add(new classDataforcontent(R.drawable.alogo,"Class 7"));
        classData.add(new classDataforcontent(R.drawable.alogo,"Class 6"));
        classData.add(new classDataforcontent(R.drawable.alogo,"Class 5"));

        classDataAdapter adapter2 = new classDataAdapter(getContext(),classData);
        recyclerView.setAdapter(adapter2);




        //Button for special activity...............................................................

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext() , MainActivity3.class));
            }
        });

        //image slider code.........................................................................

        ArrayList<SlideModel> image = new ArrayList<>();

       // image.add(new SlideModel(R.drawable.a1,"Certification",null));
        image.add(new SlideModel("https://etimg.etb2bimg.com/photo/75729614.cms",ScaleTypes.FIT));
        image.add(new SlideModel(R.drawable.a2,"Events",null));
        image.add(new SlideModel(R.drawable.a3,"Competition",null));
        image.add(new SlideModel(R.drawable.a4,"Knowledge Boostup",null));
        image.add(new SlideModel("https://education.ec.europa.eu/sites/default/files/styles/eac_ratio_16_9_xl/public/2023-02/day-authors-2023.png?h=8abcec71&itok=4h345uKR",ScaleTypes.FIT));

        imageslider.setImageList(image, ScaleTypes.CENTER_CROP);


        return view;
    }
}