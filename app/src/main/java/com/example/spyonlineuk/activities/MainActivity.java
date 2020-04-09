package com.example.spyonlineuk.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.spyonlineuk.helpers.CartHelper;
import com.example.spyonlineuk.R;
import com.example.spyonlineuk.fragments.CategoryFragment;
import com.example.spyonlineuk.fragments.HomeFragment;
import com.example.spyonlineuk.helpers.SessionHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private int counter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Spy Gadgets");
        toolbar.setSubtitle("Home");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // Fragment currentFragment = getSupportFragmentManager().getFragment(R.id.frame_holder);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_holder, new HomeFragment())
                .commitAllowingStateLoss();

        navigationView.getMenu().getItem(0).setChecked(true);


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_holder);

            if (currentFragment instanceof HomeFragment) {
                counter++;
          /* new AlertDialog.Builder(MainActivity.this)
                   .setTitle("Are you sure close app?")
                   .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           MainActivity.super.onBackPressed();
                       }
                   })
                   .setNegativeButton("No",null)
                   .show();*/

                if (counter == 1) {
                    Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            counter = 0;
                        }
                    }, 2000);
                } else {
                    super.onBackPressed();
                }
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_holder, new HomeFragment()).commitAllowingStateLoss();


            }
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        CartHelper cartHelper = new CartHelper(MainActivity.this);
        int counter = cartHelper.getTotalItemInCart();
        Drawable cartIcon = VectorDrawableCompat.create(getResources(), R.drawable.ic_shopping_cart_black_24dp, null);
        if (counter > 0) {
            ActionItemBadge.update(MainActivity.this, menu.findItem(R.id.action_cart), cartIcon, ActionItemBadge.BadgeStyles.YELLOW, counter);
        } else {
            ActionItemBadge.hide(menu.findItem(R.id.action_cart));
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        invalidateOptionsMenu();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        if (id == R.id.action_cart) {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        }


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_holder);
            if (!(currentFragment instanceof HomeFragment)) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_holder, new HomeFragment())
                        .commitAllowingStateLoss();
                toolbar.setSubtitle("Home");
            }

        } else if (id == R.id.nav_categories) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_holder, new CategoryFragment())
                    .commitAllowingStateLoss();
            toolbar.setSubtitle("Categories");
        } else if (id == R.id.nav_myorder) {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_feedback) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {
            SessionHelper.logout(MainActivity.this);
            Toast.makeText(this, "Logout successful", Toast.LENGTH_SHORT).show();
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
