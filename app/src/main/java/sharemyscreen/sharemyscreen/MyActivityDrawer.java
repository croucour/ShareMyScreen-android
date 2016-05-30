package sharemyscreen.sharemyscreen;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewStub;

import sharemyscreen.sharemyscreen.DAO.Manager;
import sharemyscreen.sharemyscreen.Entities.UserEntity;
import sharemyscreen.sharemyscreen.Logout.LogoutPresenter;
import sharemyscreen.sharemyscreen.Profile.ProfileActivity;
import sharemyscreen.sharemyscreen.Room.RoomActivity;
import sharemyscreen.sharemyscreen.Settings.SettingsActivity;
import sharemyscreen.sharemyscreen.SignIn.SignInActivity;

/**
 * Created by cleme_000 on 06/03/2016.
 */
public class MyActivityDrawer extends AppCompatActivity implements IView, NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    protected NavigationView navigation;

    protected Manager _manager;
    protected ViewStub _layout_stub;
    protected UserEntity _userEntity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_with_drawer);

        _layout_stub = (ViewStub) findViewById(R.id.layout_stub);

        this._manager = new Manager(getApplicationContext());

        this._userEntity = new UserEntity(_manager);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        this.onCreateDrawer();
    }

    protected void onCreateDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.hello_world, R.string.hello_world);

            drawerLayout.setDrawerListener(drawerToggle);

            navigation = (NavigationView) findViewById(R.id.navigation_view);
            navigation.setNavigationItemSelectedListener(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        this._userEntity = new UserEntity(_manager);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

//    @Override
//    public void onBackPressed() {
//            super.onBackPressed();
//    }

    @Override
    public CoordinatorLayout getCoordinatorLayout() {
        return (CoordinatorLayout) findViewById(R.id.display_snackbar);
    }

    @Override
    public void setCallbackSnackbar(Snackbar snackbar) {
        snackbar.setCallback(new MySnacbarCallBack(this));
    }

    @Override
    public void startRoomActivity() {

        Intent intent = new Intent(this, RoomActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void startProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void startLogOfflineActivity() {
        finish();
        Intent intent = new Intent(this, LogOfflineActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void logout() {
        LogoutPresenter logoutPresenter = new LogoutPresenter(this, _manager, _userEntity);
        logoutPresenter.onLogoutClicked();
    }

    @Override
    public void startSignInActivity() {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void startSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public Fab getFab() {
        return (Fab) findViewById(R.id.fab);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.navigation_room:
                this.startRoomActivity();
                break;
            case R.id.navigation_log:
                this.startLogOfflineActivity();
                break;
            case R.id.navigation_profile:
                this.startProfileActivity();
                break;
            case R.id.navigation_settings:
                this.startSettingsActivity();
                break;
            case R.id.navigation_logout:
                this.logout();
                break;
        }
        return false;
    }

    @Override
    protected void onStop() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(navigation);
        }
        super.onStop();
    }
}
