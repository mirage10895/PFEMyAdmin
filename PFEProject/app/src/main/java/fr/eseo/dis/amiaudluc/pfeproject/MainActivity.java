package fr.eseo.dis.amiaudluc.pfeproject;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import fr.eseo.dis.amiaudluc.pfeproject.Content.Content;
import fr.eseo.dis.amiaudluc.pfeproject.decoder.CacheFileGenerator;
import fr.eseo.dis.amiaudluc.pfeproject.subjects.AllSubjectsFragment;
import fr.eseo.dis.amiaudluc.pfeproject.subjects.MySubjectsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String ARGUMENT = "FRAGMENT";
    private String currentFragment;
    private HashMap<String, Fragment> fragments = new HashMap<>();

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragments.put(getString(R.string.fragment_mySubject), new MySubjectsFragment());
        fragments.put(getString(R.string.fragment_all_subjects),new AllSubjectsFragment());

        // Find our drawer view
        mDrawer = findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        nvDrawer = findViewById(R.id.nav_view);
        // Setup drawer view
        //setupDrawerContent(nvDrawer);

        nvDrawer.setNavigationItemSelectedListener(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_mesSujets);

        // SET HEADER INFORMATIONS
        View headerView = navigationView.getHeaderView(0);

        TextView txtName = headerView.findViewById(R.id.txtName);
        txtName.setText(getString(R.string.emptyField));
        if (Content.currentUser.getLogin() != null && txtName != null) {
            txtName.setText(Content.currentUser.getLogin());
        }

        TextView connected = headerView.findViewById(R.id.connected);
        connected.setText(getString(R.string.connected));

        ImageView imgAvatar = headerView.findViewById(R.id.imgAvatar);
        if (imgAvatar != null) {
            imgAvatar.setImageResource(R.drawable.icone_utilisateur_navbar);
        }

        ActionBar actionBar = getSupportActionBar();
        if (savedInstanceState != null) {
            currentFragment = savedInstanceState.getString(ARGUMENT, getString(R.string.fragment_mySubject));
            getSupportFragmentManager().beginTransaction().replace(R.id.content,
                    fragments.get(currentFragment), currentFragment).commit();
            if (actionBar != null) {
                actionBar.setTitle(currentFragment);
            }
        } else {
            currentFragment = getString(R.string.fragment_mySubject);
            getSupportFragmentManager().beginTransaction().replace(R.id.content,
                    fragments.get(currentFragment), currentFragment).commit();

            if (actionBar != null) {
                actionBar.setTitle(currentFragment);
            }
        }
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (id) {
            case R.id.nav_mesSujets:
                currentFragment = getString(R.string.fragment_mySubject);
                fragment = fragments.get(currentFragment);
                fragmentManager.beginTransaction().replace(R.id.content, fragment, currentFragment).commit();
                break;
            case R.id.nav_all_subjects:
                currentFragment = getString(R.string.fragment_all_subjects);
                fragment = fragments.get(currentFragment);
                fragmentManager.beginTransaction().replace(R.id.content,fragment,currentFragment).commit();
                break;
            case R.id.nav_disconnect:
                CacheFileGenerator.getInstance().removeAll(this);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                this.finish();
            default:
                break;
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(currentFragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARGUMENT, currentFragment);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
}
