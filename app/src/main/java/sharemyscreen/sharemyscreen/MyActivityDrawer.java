package sharemyscreen.sharemyscreen;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;

import java.net.URISyntaxException;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import sharemyscreen.sharemyscreen.DAO.Manager;
import sharemyscreen.sharemyscreen.Entities.OrganizationEntity;
import sharemyscreen.sharemyscreen.Entities.RoomEntity;
import sharemyscreen.sharemyscreen.Entities.UserEntity;
import sharemyscreen.sharemyscreen.Members.MembersActivity;
import sharemyscreen.sharemyscreen.Organization.AdapterOrganizationMembers;
import sharemyscreen.sharemyscreen.Organization.OrganizationPresenter;
import sharemyscreen.sharemyscreen.RoomSettings.RoomSettingsActivity;
import sharemyscreen.sharemyscreen.Room.RoomPresenter;
import sharemyscreen.sharemyscreen.SignIn.SignInActivity;

/**
 * Created by cleme_000 on 06/03/2016.
 */
public class MyActivityDrawer extends AppCompatActivity implements IView, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    protected DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    protected NavigationView navigation;

    protected Manager _manager;
    protected ViewStub _layout_stub;
    protected UserEntity _userEntity;
    private OrganizationPresenter _organizationPresenter;
    private String _profile_public_id;
    private List<OrganizationEntity> _organizationEntities;
    private View _viewDialogCreateOrganization;
    private EditText _organization_dialog_create_name;
    private Button _organization_dialog_create_submit;
    private Button _organization_dialog_create_cancel;
    private DialogPlus _dialogCreateOrganization;
    private View _navigation_organization_header;
    private Toolbar _toolbar;
    private View _navigation_room_header;
    private List<RoomEntity> _roomEntities;
    private RoomPresenter _roomPresenter;
    private String _create_type_room;
    private DialogPlus _dialogCreateRoom;
    private View _viewDialogCreateRoom;
    private EditText _room_dialog_create_name;
    private Button _room_dialog_create_submit;
    private Button _room_dialog_create_cancel;
    private TextView _room_name;
    private View _viewDialogInvit;
    private DialogPlus _dialogInvit;
    private AdapterOrganizationMembers _adapterOrganizationMembers;
    private EditText _invitOrganization_search;
    private Menu _toolbarMenu;
    private Socket socket;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this._manager = new Manager(getApplicationContext());

        this._userEntity = new UserEntity(_manager);

        _profile_public_id = _userEntity._profileEntity.get_public_id();

        this._organizationPresenter = new OrganizationPresenter(this, _manager, _userEntity);

        _roomPresenter = new RoomPresenter(this, _manager, _userEntity);
        setContentView(R.layout.layout_with_drawer);

//        _layout_stub = (ViewStub) findViewById(R.id.layout_stub);

        _room_name = (TextView) findViewById(R.id.room_name);
        _room_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.room_name :
                        Intent intent = new Intent(MyActivityDrawer.this, RoomSettingsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        break;
                }
            }
        });

        socket = null;
        try {
            socket = IO.socket("http://192.168.1.24:3000/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                socket.emit("message", "hi");
            }

        }).on("event", new Emitter.Listener() {

            @Override
            public void call(Object... args) {}

        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {}

        });
        socket.connect();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        this.onCreateDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDrawer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        _toolbarMenu = menu;
        if (_userEntity._organizationEntity == null) {
            MenuItem item = menu.findItem(R.id.menu_invitation_member);
            item.setEnabled(false);
            item = menu.findItem(R.id.menu_list_member);
            item.setEnabled(false);

        }
        return true;
    }


    protected void onCreateDrawer() {
        _toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);

        if (_toolbar != null) {
            setSupportActionBar(_toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.hello_world, R.string.hello_world) {
                @Override
                public void onDrawerOpened(View drawerView) {
                    updateDrawer();
                    super.onDrawerOpened(drawerView);
                }
            };

            drawerLayout.setDrawerListener(drawerToggle);

            navigation = (NavigationView) findViewById(R.id.navigation_view);
            navigation.setNavigationItemSelectedListener(this);

            setUpCreateOrganization();
            setUpCreateRoom();
        }
    }

    private void updateDrawer() {
        _userEntity.refresh();
        if (_userEntity._organizationEntity != null) {
            changeOrganization(_userEntity._organizationEntity.get_public_id());
        }
        else {
            selectOrganization();
        }

        String room_public_id_selected = _manager._globalManager.select("room_public_id_selected");
        if (room_public_id_selected  != null) {
            changeRoom(room_public_id_selected);
        }
    }


    private void setUpCreateRoom() {
        _viewDialogCreateRoom = this.getLayoutInflater().inflate(R.layout.room_dialog_create, null, false);

        this._room_dialog_create_name = (EditText) _viewDialogCreateRoom.findViewById(R.id.room_dialog_create_name_editText);
        this._room_dialog_create_submit = (Button) _viewDialogCreateRoom.findViewById(R.id.room_dialog_create_submit);
        this._room_dialog_create_cancel = (Button) _viewDialogCreateRoom.findViewById(R.id.room_dialog_create_cancel);

        com.orhanobut.dialogplus.ViewHolder viewHolder = new com.orhanobut.dialogplus.ViewHolder(_viewDialogCreateRoom);

        OnClickListener onClickListener = new OnClickListener() {

            @Override
            public void onClick(DialogPlus dialog, View view) {
                switch (view.getId()) {
                    case R.id.room_dialog_create_submit:
                        _roomPresenter.onCreateClicked(_userEntity._organizationEntity.get_public_id(), _create_type_room);
                        break;
                    case R.id.room_dialog_create_cancel:
                        closeDialog("createRoom");
                        break;
                }
            }
        };

        _dialogCreateRoom = DialogPlus.newDialog(this)
                .setContentHolder(viewHolder)
                .setGravity(Gravity.CENTER)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOnClickListener(onClickListener)
                .setCancelable(true)
                .create();
    }

    private void setUpCreateOrganization() {
        _viewDialogCreateOrganization = this.getLayoutInflater().inflate(R.layout.organization_dialog_create, null, false);

        this._organization_dialog_create_name = (EditText) _viewDialogCreateOrganization.findViewById(R.id.organization_dialog_create_name_editText);
        this._organization_dialog_create_submit = (Button) _viewDialogCreateOrganization.findViewById(R.id.organization_dialog_create_submit);
        this._organization_dialog_create_cancel = (Button) _viewDialogCreateOrganization.findViewById(R.id.organization_dialog_create_cancel);

        com.orhanobut.dialogplus.ViewHolder viewHolder = new com.orhanobut.dialogplus.ViewHolder(_viewDialogCreateOrganization);

        OnClickListener onClickListener = new OnClickListener() {

            @Override
            public void onClick(DialogPlus dialog, View view) {
                switch (view.getId()) {
                    case R.id.organization_dialog_create_submit:
                        _organizationPresenter.onCreateClicked();
                        break;
                    case R.id.organization_dialog_create_cancel:
                        closeDialog("createOrganization");
                        break;
                }
            }
        };

        _dialogCreateOrganization = DialogPlus.newDialog(this)
                .setContentHolder(viewHolder)
                .setGravity(Gravity.CENTER)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOnClickListener(onClickListener)
                .setCancelable(true)
                .create();
    }

    @Override
    public void closeDialog(String target) {
        switch (target) {
            case "invitation":
//                _dialogInvit.dismiss();
//                _invitOrganization_search.setText("");
//                _adapterOrganizationMembers.setProfileEntityList(null);
                break;
            case "members" :
//                _dialogMembers.dismiss();
//                _adapterOrganizationMembers.setProfileEntityList(null);
                break;
            case "createOrganization" :
                _dialogCreateOrganization.dismiss();
                _organization_dialog_create_name.setText("");

                drawerLayout.closeDrawer(navigation);
                break;
            case "createRoom" :
                _dialogCreateRoom.dismiss();
                _room_dialog_create_name.setText("");

                drawerLayout.closeDrawer(navigation);
                break;
        }

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showDialog(String action, String organization_public_id) {

//        OrganizationEntity organizationEntity = null;
//
//        TextView title = (TextView) _dialogInvit.getHolderView().findViewById(R.id.organization_title);
//        if (organization_public_id != null) {
//            organizationEntity = _manager._organizationManager.selectByPublic_id(organization_public_id);
//
//            _organization_public_id = organization_public_id;
//
//            _adapterOrganizationMembers.set_organization_public_id(organization_public_id);
//        }
        switch (action) {
            case "invitation" :
//                title.setText(getString(R.string.invitOrganization_title)+" "+organizationEntity.get_name());
//                _dialogInvit.show();
//                _adapterOrganizationMembers.setProfileEntityList(null);
//                _adapterOrganizationMembers.set_typeDialog(action);
                break;
            case "members" :
//                title.setText(getString(R.string.membersOrganization_title)+" "+organizationEntity.get_name());
//                _dialogMembers.show();
//                _organizationPresenter.getOrganizations();
//                _adapterOrganizationMembers.set_typeDialog(action);
                break;
            case "quit" :
                break;
            case "createOrganization" :
                _dialogCreateOrganization.show();
                break;
            case "createRoomPublic" :
                _create_type_room = "public";
                _dialogCreateRoom.show();
                break;
            case "createRoomPrivate" :
                _create_type_room = "private";
                _dialogCreateRoom.show();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;
        else {
            switch (item.getItemId()) {
                case R.id.menu_invitation_member:
                    Intent intent = new Intent(this, MembersActivity.class);
                    intent.putExtra("listType", "invitation");
                    startActivity(intent);
                    break;
                case R.id.menu_list_member:
                    Intent intent2 = new Intent(this, MembersActivity.class);
                    intent2.putExtra("listType", "members");
                    startActivity(intent2);
                    break;
                case R.id.menu_settings:
                    break;
                case R.id.menu_logout:
                    _userEntity.logout();
                    this.finish();
                    Intent intent3 = new Intent(this, SignInActivity.class);
                    startActivity(intent3);
                    break;
            }
        }
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

//    @Override
//    public void startRoomActivity() {
//        Intent intent = new Intent(this, RoomSettingsActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        startActivity(intent);
//    }

    @Override
    public void startOrganizationActivity() {
//        Intent intent = new Intent(this, OrganizationActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        startActivity(intent);
    }

    @Override
    public void updateOrganizationEntityList() {
        Menu menu = navigation.getMenu();
        _organizationEntities = _manager._organizationManager.selectAllByProfile_id(_profile_public_id);

        menu.clear();
        if (_organizationEntities != null) {
            for (OrganizationEntity organizationEntity : _organizationEntities) {
                MenuItem menuItem = menu.add(R.id.menu_organization, Menu.NONE, Menu.NONE, organizationEntity.get_name());
                menuItem.setTitleCondensed(organizationEntity.get_public_id());
                menuItem.setCheckable(true);
            }
        }

        MenuItem menuItem = menu.add(R.id.menu_organization, R.id.add_organization, Menu.NONE, "Créer une organisation");
        menuItem.setIcon(R.drawable.ic_add_black_24dp);
        menuItem.setCheckable(true);
    }

    @Override
    public void updateRoomEntityList() {
        Menu menu = navigation.getMenu();
        _roomEntities = _manager._roomsManager.selectAllByProfile_idAndOrganization_id(_profile_public_id, _userEntity._organizationEntity.get_public_id());

        menu.clear();

        SubMenu subMenuPublic = menu.addSubMenu(R.id.menu_room_public, Menu.NONE, Menu.NONE, "Conversation public");
        SubMenu subMenuPrivate = menu.addSubMenu(R.id.menu_room_private, Menu.NONE, Menu.NONE, "Conversation privées");
        SubMenu subMenuShortcut = menu.addSubMenu(R.id.menu_room_shortcut, Menu.NONE, Menu.NONE, "Accés rapide");


        if (_roomEntities != null) {
            for (RoomEntity roomEntity : _roomEntities) {
                if (!roomEntity.is_private()) {
                    MenuItem menuItem = subMenuPublic.add(R.id.menu_room_public, Menu.NONE, Menu.NONE, roomEntity.get_name());
                    menuItem.setTitleCondensed(roomEntity.get_public_id());
                    menuItem.setCheckable(true);
                } else {
                    MenuItem menuItem = subMenuPrivate.add(R.id.menu_room_private, Menu.NONE, Menu.NONE, roomEntity.get_name());
                    menuItem.setTitleCondensed(roomEntity.get_public_id());
                    menuItem.setCheckable(true);
                }

            }
        }

        MenuItem menuItem = subMenuPublic.add(R.id.menu_room_public, R.id.add_room_public, Menu.NONE, "Ajouter conversation");
        menuItem.setIcon(R.drawable.ic_add_black_24dp);
        menuItem.setCheckable(true);

        menuItem = subMenuPrivate.add(R.id.menu_room_private, R.id.add_room_private, Menu.NONE, "Ajouter conversation");
        menuItem.setIcon(R.drawable.ic_add_black_24dp);
        menuItem.setCheckable(true);

        menuItem = subMenuShortcut.add(R.id.menu_room_private, R.id.organization_delete, Menu.NONE, R.string.organization_delete);
        menuItem.setIcon(R.drawable.ic_delete_white_24dp);
        menuItem.setCheckable(true);
    }


    @Override
    public String getNameOrganization() {
        return this._organization_dialog_create_name.getText().toString();
    }

    @Override
    public void setErrorNameOrganization(int resId) {
        TextInputLayout inputLayout = (TextInputLayout) findViewById(R.id.room_dialog_create_name_inputLayout);
        inputLayout.setError((resId == 0 ? null : getString(resId)));
    }

    @Override
    public String getNameRoom() {
        return this._room_dialog_create_name.getText().toString();
    }

    @Override
    public void setErrorNameRoom(int resId) {
        TextInputLayout signin_password_inputLayout = (TextInputLayout) findViewById(R.id.room_dialog_create_name_inputLayout);
        signin_password_inputLayout.setError((resId == 0 ? null : getString(resId)));
    }

//    @Override
//    public void startProfileActivity() {
//        Intent intent = new Intent(this, ProfileActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        startActivity(intent);
//    }
//
//    @Override
//    public void startLogOfflineActivity() {
//        finish();
//        Intent intent = new Intent(this, LogOfflineActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        startActivity(intent);
//    }
//
//    @Override
//    public void logout() {
//        LogoutPresenter logoutPresenter = new LogoutPresenter(this, _manager, _userEntity);
//        logoutPresenter.onLogoutClicked();
//    }
//
//    @Override
//    public void startSignInActivity() {
//        Intent intent = new Intent(this, SignInActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//    }
//
//    @Override
//    public void startSettingsActivity() {
//        Intent intent = new Intent(this, SettingsActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        startActivity(intent);
//    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        item.setChecked(true);

        if (item.getGroupId() == R.id.menu_organization) {
            item.setChecked(true);
            if (item.getItemId() == R.id.add_organization) {
                this.showDialog("createOrganization", null);
            }
            else {
                changeOrganization(String.valueOf(item.getTitleCondensed()));
            }
        }
        else {
            int itemId = item.getItemId();
            if (itemId == R.id.organization_delete) {
                _organizationPresenter.deleteOrganization(_userEntity._organizationEntity.get_public_id());
            }
            if (itemId == R.id.add_room_public) {
                this.showDialog("createRoomPublic", null);
            }
            if (itemId == R.id.add_room_private) {
                this.showDialog("createRoomPrivate", null);
            }
            else {
                changeRoom(String.valueOf(item.getTitleCondensed()));
            }
        }
        return false;
    }

    public void selectRoom() {

    }

    @Override
    public void changeRoom(String room_public_id) {
        RoomEntity roomEntity = _manager._roomsManager.selectByPublic_id(room_public_id);
        if (roomEntity != null) {
            if (_room_name != null) {
                _room_name.setText("# " + roomEntity.get_name());
            }
            _manager._globalManager.addGlobal("room_public_id_selected", room_public_id);
        }
    }

    @Override
    public void changeOrganization(String organization_public_id) {
        if (organization_public_id != null) {
            navigation.getMenu().clear();
            navigation.inflateMenu(R.menu.rooms);

            invalidateOptionsMenu();

            if (_navigation_room_header == null) {
                _navigation_room_header = navigation.inflateHeaderView(R.layout.navigation_room_header);
            }
            ImageView imageView= (ImageView)_navigation_room_header.findViewById(R.id.icon_select_organization);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.icon_select_organization :
                            selectOrganization();
                            break;
                    }
                }
            });

            if (_navigation_organization_header != null) {
                navigation.removeHeaderView(_navigation_organization_header);
                _navigation_organization_header = null;
            }

            _userEntity._organizationEntity = _manager._organizationManager.selectByPublic_id(organization_public_id);

            if(_userEntity._organizationEntity != null) {
                _toolbar.setTitle(_userEntity._organizationEntity.get_name());
            }

            _manager._globalManager.addGlobal("organization_public_id_selected", organization_public_id);
            _roomPresenter.getRooms();
        }
    }

    @Override
    protected void onStop() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(navigation);
        }
        super.onStop();
    }

    @Override
    public void selectOrganization() {

        if (_manager._globalManager.select("organization_public_id_selected") == null) {
            _userEntity._organizationEntity = null;
            _toolbar.setTitle("Aucune organisation sélectionnée");

            invalidateOptionsMenu();
        }

        navigation.getMenu().clear();
        navigation.inflateMenu(R.menu.organizations);
        if (_navigation_room_header != null) {
            navigation.removeHeaderView(_navigation_room_header);
            _navigation_room_header = null;
        }
        if (_navigation_organization_header == null) {
            _navigation_organization_header = navigation.inflateHeaderView(R.layout.navigation_organization_header);
        }
        _organizationPresenter.getOrganizations();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.room_name :
                if (_userEntity._organizationEntity != null &&  _userEntity._roomEntity != null) {
                    Intent intent = new Intent(this, RoomSettingsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
                break;
        }
    }
}
