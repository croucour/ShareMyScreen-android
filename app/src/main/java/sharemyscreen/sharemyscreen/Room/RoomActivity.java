package sharemyscreen.sharemyscreen.Room;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.melnykov.fab.FloatingActionButton;

import java.util.List;

import sharemyscreen.sharemyscreen.DAO.RoomsManager;
import sharemyscreen.sharemyscreen.LogOfflineActivity;
import sharemyscreen.sharemyscreen.Logout.LogoutPresenter;
import sharemyscreen.sharemyscreen.Profile.ProfileActivity;
import sharemyscreen.sharemyscreen.R;
import sharemyscreen.sharemyscreen.SignIn.SignInActivity;

/**
 * Created by roucou-c on 09/12/15.
 */

public class RoomActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, IRoomView{

    private RoomsManager _roomsManager;
    private LogoutPresenter _logoutPresenter;
    private RoomPresenter _roomPresenter;

    private MyAdapter mAdapter;
    private SwipeMenuListView mListView;
    private SwipeRefreshLayout _swipeRefreshLayout;
    private Context _pContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room);

        this._pContext = getApplicationContext();
        this._roomsManager = new RoomsManager(this);
        this._logoutPresenter = new LogoutPresenter(this, _pContext);
        this._roomPresenter = new RoomPresenter(this, _pContext);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        _swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        _swipeRefreshLayout.setOnRefreshListener(this);

        mListView = (SwipeMenuListView) findViewById(R.id.room_recycler_view);


        mAdapter = new MyAdapter(this._roomsManager);
        mListView.setAdapter(mAdapter);

        mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToListView(mListView);
        fab.setOnClickListener(this);

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.display_snackbar);

            // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        mListView.setMenuCreator(creator);

        // step 2. listener item click event
//        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//                ApplicationInfo item = mAppList.get(position);
//                switch (index) {
//                    case 0:
//                        break;
//                    case 1:
//                        mAppList.remove(position);
//                        mAdapter.notifyDataSetChanged();
//                        break;
//                }
//                return false;
//            }
//        });

        // set SwipeListener
        mListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                _swipeRefreshLayout.setEnabled(false);
                Log.i("info", "start");
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                _swipeRefreshLayout.setEnabled(true);
                Log.i("info", "end");
                // swipe end
            }
        });

        // set MenuStateChangeListener
        mListView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
                Log.i("info", "open");
            }

            @Override
            public void onMenuClose(int position) {
                Log.i("info", "close");
            }
        });

        // other setting
//		listView.setCloseInterpolator(new BounceInterpolator());

        // test item long click
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Toast.makeText(getApplicationContext(), position + " long click", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    private void delete(ApplicationInfo item) {
        // delete app
        try {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.fromParts("package", item.packageName, null));
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    private void open(ApplicationInfo item) {
        // open app
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(item.packageName);
        List<ResolveInfo> resolveInfoList = getPackageManager()
                .queryIntentActivities(resolveIntent, 0);
        if (resolveInfoList != null && resolveInfoList.size() > 0) {
            ResolveInfo resolveInfo = resolveInfoList.get(0);
            String activityPackageName = resolveInfo.activityInfo.packageName;
            String className = resolveInfo.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName componentName = new ComponentName(
                    activityPackageName, className);

            intent.setComponent(componentName);
            startActivity(intent);
        }
    }



    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public void onRefresh() {
        this._roomPresenter.onSwipedForRefreshRooms();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.modify_profil:
                this.profile();
                break;
            case R.id.log_offline:
                this.logOffline();
                break;
            case R.id.disconnect:
                this._logoutPresenter.onLogoutCliked();
                break;
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fab:
                break;
        }
    }

    @Override
    public void logout() {
        Intent intent = new Intent(this, SignInActivity.class);
        this.startActivity(intent);
        this.finish();
    }

    @Override
    public void profile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void logOffline() {
        Intent intent = new Intent(this, LogOfflineActivity.class);
        startActivity(intent);
    }

    @Override
    public CoordinatorLayout getCoordinatorLayout() {
        return (CoordinatorLayout) findViewById(R.id.display_snackbar);
    }

    @Override
    public void setRefreshing(boolean state) {
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setRefreshing(state);
    }

    @Override
    public void setCallbackSnackbar(Snackbar snackbar) {
        snackbar.setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.animate().translationYBy((snackbar.getView().getHeight()));
            }

            @Override
            public void onShown(Snackbar snackbar) {
                super.onShown(snackbar);
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.animate().translationYBy(-(snackbar.getView().getHeight()));
            }
        });
    }
}

