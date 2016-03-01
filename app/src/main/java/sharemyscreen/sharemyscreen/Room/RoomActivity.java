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
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.dd.processbutton.iml.ActionProcessButton;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.List;

import sharemyscreen.sharemyscreen.DAO.ProfileManager;
import sharemyscreen.sharemyscreen.DAO.RoomsManager;
import sharemyscreen.sharemyscreen.DAO.SettingsManager;
import sharemyscreen.sharemyscreen.DAO.TokenManager;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.Entities.RoomEntity;
import sharemyscreen.sharemyscreen.Entities.TokenEntity;
import sharemyscreen.sharemyscreen.Fab;
import sharemyscreen.sharemyscreen.LogOfflineActivity;
import sharemyscreen.sharemyscreen.Logout.LogoutPresenter;
import sharemyscreen.sharemyscreen.Profile.ProfileActivity;
import sharemyscreen.sharemyscreen.R;
import sharemyscreen.sharemyscreen.SignIn.SignInActivity;

/**
 * Created by roucou-c on 09/12/15.
 */

public class RoomActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, IRoomView {

    private RoomsManager _roomsManager;
    private LogoutPresenter _logoutPresenter;
    private RoomPresenter _roomPresenter;

    private MyAdapter mAdapter;
    private SwipeMenuListView mListView;
    private SwipeRefreshLayout _swipeRefreshLayout;
    private Context _pContext;
    private DialogPlus _dialogByUser;
    private View _viewDialogByUser;
    private ActionProcessButton _createRoom_by_user_cancel;
    private ActionProcessButton _createRoom_by_user_submit;
    private MaterialBetterSpinner _createRoom_by_user_choose_user;
    private EditText _createRoom_by_user_name_editText;
    private MaterialSheetFab<Fab> _materialSheetFab;

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

        SettingsManager settingsManager = new SettingsManager(_pContext);
        TokenManager tokenManager = new TokenManager(_pContext);

        String token_id = settingsManager.select("current_token_id");
        if (token_id != null) {
            TokenEntity tokenEntity = tokenManager.selectById(Long.parseLong(token_id));

            ProfileManager profileManager = new ProfileManager(this);
            ProfileEntity profileEntity = profileManager.selectById(tokenEntity.get_profile_id());

            mAdapter = new MyAdapter(this._roomsManager, profileEntity == null ? null : profileEntity.get__id());
            mAdapter.set_roomEntityList(null);
            mListView.setAdapter(mAdapter);
        }

        mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

        this.setUpFab();

        this.setUpSwipeMenuItem();

        this.setupDialogCreateRoomByUser();

        this._roomPresenter.onSwipedForRefreshRooms();
    }

    @Override
    public void setRoomEntityList(List<RoomEntity> roomEntityList) {
        mAdapter.set_roomEntityList(roomEntityList);
    }

    @Override
    public void setRoomEntity(RoomEntity roomEntity) {
        mAdapter.set_roomEntity(roomEntity);
        mListView.invalidateViews();
        mListView.smoothScrollToPosition(mAdapter.getCount());
        _materialSheetFab.hideSheet();
    }

    private static final String[] USERS = new String[] {
        "test98"
    };

    private void setupDialogCreateRoomByUser() {

        _viewDialogByUser = this.getLayoutInflater().inflate(R.layout.dialog_create_room_by_user, null);

        _createRoom_by_user_submit = (ActionProcessButton) _viewDialogByUser.findViewById(R.id.createRoom_by_user_submit);
        _createRoom_by_user_cancel = (ActionProcessButton) _viewDialogByUser.findViewById(R.id.createRoom_by_user_cancel);


        _createRoom_by_user_name_editText = (EditText) _viewDialogByUser.findViewById(R.id.createRoom_by_user_name_editText);
        ViewHolder viewHolder = new ViewHolder(_viewDialogByUser);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, USERS);


        _createRoom_by_user_choose_user = (MaterialBetterSpinner) _viewDialogByUser.findViewById(R.id.createRoom_by_user_choose_user);
        _createRoom_by_user_choose_user.setAdapter(adapter);

        OnClickListener onClickListener = new OnClickListener() {

            @Override
            public void onClick(DialogPlus dialog, View view) {
                switch (view.getId()) {
                    case R.id.createRoom_by_user_submit:
                        _roomPresenter.onCreateRoomByUserClicked();
                        break;
                    case R.id.createRoom_by_user_cancel:
                        dialog.dismiss();
                        break;
                }
            }
        };

        _dialogByUser = DialogPlus.newDialog(this)
                .setContentHolder(viewHolder)
                .setGravity(Gravity.CENTER)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setCancelable(true)
                .setOnClickListener(onClickListener)
                .create();
    }

    private void setUpFab() {

        Fab fab = (Fab) findViewById(R.id.fab);
        fab.attachToListView(mListView);
        fab.setOnClickListener(this);

        View sheetView = findViewById(R.id.fab_sheet);
        View overlay = findViewById(R.id.overlay);
        int sheetColor = getResources().getColor(R.color.background);
        int fabColor = getResources().getColor(R.color.colorPrimaryDark);

//         Initialize material sheet FAB
        _materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay,
                sheetColor, fabColor);



        TextView fab_sheet_item_add_by_group = (TextView) findViewById(R.id.fab_sheet_item_add_by_group);
        TextView fab_sheet_item_add_by_user = (TextView) findViewById(R.id.fab_sheet_item_add_by_user);
        TextView fab_sheet_item_note = (TextView) findViewById(R.id.fab_sheet_item_note);

        fab_sheet_item_add_by_group.setOnClickListener(this);
        fab_sheet_item_add_by_user.setOnClickListener(this);
        fab_sheet_item_note.setOnClickListener(this);
    }

    private void setUpSwipeMenuItem() {
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

        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                RoomEntity item = mAdapter.getItem(position);
                switch (index) {
                    case 0:
                        // open
                        break;
                    case 1:
                        _roomPresenter.deleteRoomOnClicked(item);
                        Log.i("info", "remove");

                        break;
                }
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
            case R.id.fab_sheet_item_add_by_group:
                Log.d("onclick", "fab_sheet_item_add_by_group");
                break;
            case R.id.fab_sheet_item_add_by_user:
                initializeEditTextCreateRoomByUser();
                initializeInputLayoutCreateRoomByUser();
                _dialogByUser.show();
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
                Fab fab = (Fab) findViewById(R.id.fab);
                fab.animate().translationYBy((snackbar.getView().getHeight()));
            }

            @Override
            public void onShown(Snackbar snackbar) {
                super.onShown(snackbar);
                Fab fab = (Fab) findViewById(R.id.fab);
                fab.animate().translationYBy(-(snackbar.getView().getHeight()));
            }
        });
    }

    @Override
    public String getNameOfCreateRoomByUser() {
        return _createRoom_by_user_name_editText.getText().toString();
    }

    public void setNameOfCreateRoomByUser(String string) {
        _createRoom_by_user_name_editText.setText(string);
    }

    @Override
    public void setErrorNameOfCreateRoomByUser(int resId) {
        TextInputLayout  createRoom_by_user_name_inputLayout = (TextInputLayout) _viewDialogByUser.findViewById(R.id.createRoom_by_user_name_inputLayout);
        createRoom_by_user_name_inputLayout.setError((resId == 0 ? null : getString(resId)));
    }

    @Override
    public String getUserOfCreateRoomByUser() {
        return _createRoom_by_user_choose_user.getText().toString();
    }

    public void setUserOfCreateRoomByUser(String string) {
        _createRoom_by_user_choose_user.setText(string);
    }

    @Override
    public void setErrorUserOfCreateRoomByUser(int resId) {
        _createRoom_by_user_choose_user.setError((resId == 0 ? null : getString(resId)));
    }

    @Override
    public void initializeInputLayoutCreateRoomByUser() {
        this.setErrorNameOfCreateRoomByUser(0);
        this.setErrorUserOfCreateRoomByUser(0);
    }

    public void initializeEditTextCreateRoomByUser() {
        this.setNameOfCreateRoomByUser("");
        this.setUserOfCreateRoomByUser("");
    }

    @Override
    public void deleteRoomEntityList(RoomEntity roomEntity) {
        mAdapter.delete(roomEntity);
        mListView.invalidateViews();
    }

    @Override
    public void hideDialogCreateRoomByUser() {
        _dialogByUser.dismiss();
    }
}

