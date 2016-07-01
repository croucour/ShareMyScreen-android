package sharemyscreen.sharemyscreen.RoomSettings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import sharemyscreen.sharemyscreen.DAO.Manager;
import sharemyscreen.sharemyscreen.Entities.UserEntity;
import sharemyscreen.sharemyscreen.Members.MembersActivity;
import sharemyscreen.sharemyscreen.R;

/**
 * Created by roucou-c on 09/12/15.
 */

public class RoomSettingsActivity extends AppCompatActivity implements View.OnClickListener, IRoomSettingsView {

    private RoomSettingsPresenter _roomSettingsPresenter;
    private Toolbar _toolbar;
    private Manager _manager;
    private UserEntity _userEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this._manager = new Manager(getApplicationContext());

        this._userEntity = new UserEntity(_manager);

        this._roomSettingsPresenter = new RoomSettingsPresenter(this, _manager, _userEntity);
        setContentView(R.layout.room_settings);

        _toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(_toolbar);
        if (_toolbar != null) {
            _toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        LinearLayout room_settings_edit_name_layout = (LinearLayout) findViewById(R.id.room_settings_edit_name_layout);
        LinearLayout room_settings_add_member_layout = (LinearLayout) findViewById(R.id.room_settings_add_member_layout);
        LinearLayout room_settings_list_member_layout = (LinearLayout) findViewById(R.id.room_settings_list_member_layout);
        LinearLayout room_settings_leave_layout = (LinearLayout) findViewById(R.id.room_settings_leave_layout);
        LinearLayout room_settings_delete_layout = (LinearLayout) findViewById(R.id.room_settings_delete_layout);

        room_settings_edit_name_layout.setOnClickListener(this);
        room_settings_add_member_layout.setOnClickListener(this);
        room_settings_list_member_layout.setOnClickListener(this);
        room_settings_leave_layout.setOnClickListener(this);
        room_settings_delete_layout.setOnClickListener(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        _toolbar.setTitle("Paramètres de la conversation");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.room_settings_edit_name_layout:
                break;
            case R.id.room_settings_add_member_layout:
                break;
            case R.id.room_settings_list_member_layout:
                break;
            case R.id.room_settings_leave_layout:
                break;
            case R.id.room_settings_delete_layout:
                _roomSettingsPresenter.deleteRoomOnClicked(_userEntity._roomEntity.get_public_id());
                break;
        }
    }
}