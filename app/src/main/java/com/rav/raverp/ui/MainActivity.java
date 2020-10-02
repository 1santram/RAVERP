package com.rav.raverp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ViewUtils;
import androidx.core.text.PrecomputedTextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.rav.raverp.MyApplication;
import com.rav.raverp.R;
import com.rav.raverp.View.ExpandableNavigationListView;
import com.rav.raverp.data.local.prefs.PrefsHelper;
import com.rav.raverp.data.model.ChildModel;
import com.rav.raverp.data.model.HeaderModel;
import com.rav.raverp.data.model.api.Login;
import com.rav.raverp.ui.fragment.DashBoardFragment;
import com.rav.raverp.ui.fragment.GoalFragment;
import com.rav.raverp.ui.fragment.PloastAvailabilityFragment;
import com.rav.raverp.ui.fragment.WalletFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static String mobile="mobile";
    public static String userid="userid";

    Toolbar toolbar;
    TextView editprodile;
    DrawerLayout drawer;


    private ExpandableNavigationListView navigationExpandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Dashboard");
        setSupportActionBar(toolbar);

        navigationExpandableListView = (ExpandableNavigationListView) findViewById(R.id.expandable_navigation);

        drawer = findViewById(R.id.drawer_layouts);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);







       /* String a=getIntent().getStringExtra("dash");
        if (getIntent().getStringExtra("dash").equalsIgnoreCase("Role")){
            show1stDashboard();
        }else{
            show2ndDashboard();
        }*/


        show1stDashboard();



    }

    private void show1stDashboard() {
        navigationExpandableListView
                .init(this)
                .addHeaderModel(new HeaderModel("Dashboard",R.drawable.ic_home_black_24dp))
                .addHeaderModel(
                        new HeaderModel("Plot Moduls", R.drawable.ic_baseline_my_location_24, true)
                                .addChildModel(new ChildModel("Plot Availability",R.drawable.ic_baseline_arrow_forward_24)))
                .addHeaderModel(
                        new HeaderModel("Wallet", R.drawable.ic_baseline_my_location_24, true)
                                .addChildModel(new ChildModel("Add Wallet",R.drawable.ic_baseline_arrow_forward_24)))
                .addHeaderModel(
                        new HeaderModel("Goal", R.drawable.ic_baseline_my_location_24, true)
                                .addChildModel(new ChildModel("My Goal",R.drawable.ic_baseline_arrow_forward_24)))

                .build()
                .addOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                        navigationExpandableListView.setSelected(groupPosition);
                        //drawer.closeDrawer(GravityCompat.START);
                        if (id == 0) {
                            toolbar.setTitle("Dashboard");
                            Fragment fragment=new DashBoardFragment();
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.homepage, fragment,"MYFRAGMENT");
                            transaction.addToBackStack("MYFRAGMENT").commit();
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (id == 1) {


                        } else if (id == 2) {

                        } else if (id == 3) {

                        }
                        return false;
                    }
                })
                .addOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        navigationExpandableListView.setSelected(groupPosition, childPosition);

                        if (groupPosition==1){
                            if (id == 0) {
                                toolbar.setTitle("Plot Available");
                                Fragment fragment=new PloastAvailabilityFragment();
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.homepage, fragment);
                                transaction.addToBackStack(null).commit();
                                drawer.closeDrawer(GravityCompat.START);
                            }
                        }else if (groupPosition==2){
                            if (id == 0) {
                                toolbar.setTitle("Wallet");
                                Fragment fragment=new WalletFragment();
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.homepage, fragment);
                                transaction.addToBackStack(null).commit();
                                drawer.closeDrawer(GravityCompat.START);
                            }
                        }if (groupPosition==3){
                            if (id == 0) {
                                toolbar.setTitle("Goal List");
                                Fragment fragment=new GoalFragment();
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.homepage, fragment);
                                transaction.addToBackStack(null).commit();
                                drawer.closeDrawer(GravityCompat.START);
                            }
                        }

                        return false;
                    }
                });

        Fragment fragment=new DashBoardFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.homepage, fragment,"MYFRAGMENT");
        transaction.addToBackStack("MYFRAGMENT").commit();
        navigationExpandableListView.setSelected(0);
    }

    private void show2ndDashboard() {

        navigationExpandableListView
                .init(this)
                .addHeaderModel(new HeaderModel("Home",R.drawable.ic_home_black_24dp))
                .addHeaderModel(
                        new HeaderModel("Home 1", R.drawable.ic_baseline_my_location_24, true)
                                .addChildModel(new ChildModel("abc",R.drawable.ic_home_black_24dp)))
                .addHeaderModel(
                        new HeaderModel("Home 2", R.drawable.add, true)
                                .addChildModel(new ChildModel("kbc",R.drawable.ic_home_black_24dp)))
                .addHeaderModel(
                        new HeaderModel("Home 2", R.drawable.add, true)
                                .addChildModel(new ChildModel("lbc",R.drawable.ic_home_black_24dp)))

                .build()
                .addOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                        navigationExpandableListView.setSelected(groupPosition);
                        //drawer.closeDrawer(GravityCompat.START);
                        if (id == 0) {
                            toolbar.setTitle("Home");
                            Fragment fragment=new DashBoardFragment();
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.homepage, fragment,"MYFRAGMENT");
                            transaction.addToBackStack("MYFRAGMENT").commit();
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (id == 1) {

                        } else if (id == 2) {

                        } else if (id == 3) {

                        }
                        return false;
                    }
                })
                .addOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        navigationExpandableListView.setSelected(groupPosition, childPosition);

                        if (groupPosition==1){
                            if (id == 0) {
                                toolbar.setTitle("abc");
                                Fragment fragment=new PloastAvailabilityFragment();
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.homepage, fragment);
                                transaction.addToBackStack(null).commit();
                                drawer.closeDrawer(GravityCompat.END);
                            }
                        }else if (groupPosition==2){
                            if (id == 0) {
                                toolbar.setTitle("kbc");
                                Fragment fragment=new WalletFragment();
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.homepage, fragment);
                                transaction.addToBackStack(null).commit();
                                drawer.closeDrawer(GravityCompat.END);
                            }
                        }if (groupPosition==3){
                            if (id == 0) {
                                toolbar.setTitle("lbc");
                                Fragment fragment=new GoalFragment();
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.homepage, fragment);
                                transaction.addToBackStack(null).commit();
                                drawer.closeDrawer(GravityCompat.END);
                            }
                        }

                        return false;
                    }
                });

        Fragment fragment=new DashBoardFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.homepage, fragment,"MYFRAGMENT");
        transaction.addToBackStack("MYFRAGMENT").commit();
        navigationExpandableListView.setSelected(0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layouts);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layouts);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            DashBoardFragment fragment;
            fragment = (DashBoardFragment) getSupportFragmentManager().findFragmentByTag("MYFRAGMENT");
            if (fragment==null) {
                toolbar.setTitle("Home");
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.homepage, new DashBoardFragment(toolbar),"MYFRAGMENT");
                fragmentTransaction.addToBackStack("MYFRAGMENT").commit();
                navigationExpandableListView.setSelected(0);
            }else{
                if ( fragment.isVisible()) {
                    Intent a = new Intent(Intent.ACTION_MAIN);
                    a.addCategory(Intent.CATEGORY_HOME);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(a);
                }else{
                    toolbar.setTitle("Home");
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.homepage, new DashBoardFragment(),"MYFRAGMENT");
                    fragmentTransaction.addToBackStack("MYFRAGMENT").commit();
                    navigationExpandableListView.setSelected(0);
                }
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
       if (id == R.id.change_password) {
            toolbar.setTitle("Change Password");

            Intent intent=new Intent(MainActivity.this,ChangePasswordActivity.class);
            startActivity(intent);

            return true;
        }else if (id == R.id.activity_log){

            toolbar.setTitle("Activity Log");


        }else if (id == R.id.profile){


            toolbar.setTitle("Profile");

        }else if (id == R.id.signout){


                ViewUtils.showConfirmationDialog(MainActivity.this, getString(R.string.msg_dialog_logout),
                        new DialogActionCallback() {
                            @Override
                            public void okAction() {
                                PrefsHelper.remove(MainActivity.this, AppConstants.LOGIN);
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        });
        }

        return super.onOptionsItemSelected(item);
    }*/

}
