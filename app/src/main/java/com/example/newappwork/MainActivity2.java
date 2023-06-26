package com.example.newappwork;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.ContentInfoCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.newappwork.NavFragment.AboutFragment;
import com.example.newappwork.NavFragment.AssignmentFragment;
import com.example.newappwork.NavFragment.CareerCounsellingFragment;
import com.example.newappwork.NavFragment.FeePaymentFragment;
import com.example.newappwork.NavFragment.MyAccountFragment;
import com.example.newappwork.NavFragment.NotificationFragment;
import com.example.newappwork.NavFragment.StudyMaterialFragment;
import com.example.newappwork.NavFragment.SyllabusFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {



    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore mStore;

    TextView textView;

    AppCompatButton btn;

    NavigationView navTop;

    BottomNavigationView bottomNavigationView;

    String ROOT_FRAGMENT_TAG  = "root fragment";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);



       getSupportActionBar().setTitle("PATH CREATION ACADEMY");

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mStore = FirebaseFirestore.getInstance();


        fatchData();


        String email = mUser.getEmail();




        //code for Top nav...................................................................

        navTop = findViewById(R.id.nav_top);
        View headerView = navTop.getHeaderView(0);
        TextView userEmail = (TextView) headerView.findViewById(R.id.log_email);

        userEmail.setText(email);

        navTop.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_account:{
                        loadfragmet(new MyAccountFragment(),1);
                        break;
                    }
                    case R.id.notification:{
                        loadfragmet(new NotificationFragment(),1);
                        break;
                    }
                    case R.id.nav_syllabus:{
                        loadfragmet(new SyllabusFragment(),1);
                        break;
                    }
                    case R.id.nav_assignment:{
                        loadfragmet(new AssignmentFragment(),1);
                        break;
                    }
                    case R.id.nav_studyMaterial:{
                        loadfragmet(new StudyMaterialFragment(),1);
                        break;
                    }
                    case R.id.nav_career:{
                        loadfragmet(new CareerCounsellingFragment(),1);
                        break;
                    }
                    case R.id.nav_onlineFeePayment:{
                        loadfragmet(new FeePaymentFragment(),1);
                        break;
                    }
                    case R.id.nav_about:{
                        loadfragmet(new AboutFragment(),1);
                        break;
                    }
                    case R.id.nav_logout: {
                        mAuth.signOut();
                        Intent intent = new Intent(MainActivity2.this,LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    }
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        //code for bottom nav...................................................................

        bottomNavigationView = findViewById(R.id.bottomnav);

        loadfragmet(new FragmentHome(),0);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home: {
                        loadfragmet(new FragmentHome(),0);
                        break;
                    }
                    case R.id.Store: {
                        loadfragmet(new FragmentBuyCourse(),1);
                        break;
                    }
                    case R.id.Money: {
                        loadfragmet(new FragmentChat(),1);
                        break;
                    }
                    case R.id.Setting: {
                        loadfragmet(new FragmentClasswork(),1);
                        break;
                    }
                    case R.id.Proflie: {
                        loadfragmet(new FragmentProfile(),1);
                        break;
                    }
                }
                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.Home);
   //........................................................................................................


        //drawer code

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    public void fatchData(){
        String currentId = mUser.getUid();
        DocumentReference documentReference = mStore.collection("User").document(currentId);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String name = documentSnapshot.getString("name");
                    View headerView = navTop.getHeaderView(0);
                    TextView userName = (TextView) headerView.findViewById(R.id.log_name);
                    userName.setText(name);
                }else {
                    Toast.makeText(MainActivity2.this, "kuch problem hai", Toast.LENGTH_SHORT).show();
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity2.this, "mothed m error hai", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    public void loadfragmet(Fragment fragment,int flags){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if(flags==0){
            ft.replace(R.id.for_main_activiy, fragment);
        }
        else {
            ft.replace(R.id.for_main_activiy, fragment);
        }
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if(bottomNavigationView.getSelectedItemId()==R.id.Home){
            super.onBackPressed();
            finish();
        }
        else
        {
            bottomNavigationView.setSelectedItemId(R.id.Home);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar,menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
