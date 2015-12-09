package sharemyscreen.sharemyscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import sharemyscreen.sharemyscreen.DAO.RoomsManager;
import sharemyscreen.sharemyscreen.Model.LogoutModel;
import sharemyscreen.sharemyscreen.Model.ProfileModel;

/**
 * Created by roucou-c on 09/12/15.
 */
public class RoomActivity extends AppCompatActivity implements View.OnClickListener {

    private RoomsManager _roomsManager;
    private LogoutModel _logoutModel;
    private ProfileModel _profileModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this._roomsManager = new RoomsManager(this);
        this._logoutModel = new LogoutModel(this);
        _profileModel = new ProfileModel(this);

        setContentView(R.layout.room);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.room_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        MyAdapter mAdapter = new MyAdapter(this._roomsManager);
        mRecyclerView.setAdapter(mAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    public void onClick(View v) {

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
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.disconnect:
                this._logoutModel.logout(this);
                break;
        }

        return false;
    }

}
