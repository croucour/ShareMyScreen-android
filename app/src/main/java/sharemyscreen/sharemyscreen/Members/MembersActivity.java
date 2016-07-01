package sharemyscreen.sharemyscreen.Members;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import sharemyscreen.sharemyscreen.DAO.Manager;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.Entities.UserEntity;
import sharemyscreen.sharemyscreen.Organization.AdapterOrganizationMembers;
import sharemyscreen.sharemyscreen.R;

/**
 * Created by roucou-c on 09/12/15.
 */

public class MembersActivity extends AppCompatActivity implements View.OnClickListener, IMembersView, TextWatcher {

    private MembersPresenter _membersPresenter;
    private Toolbar _toolbar;
    private Manager _manager;
    private UserEntity _userEntity;
    private AdapterOrganizationMembers _adapterOrganizationMembers;
    private EditText _invitOrganization_search;
    private String _listType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this._manager = Manager.getInstance(getApplicationContext());

        this._userEntity = new UserEntity(_manager);

        this._membersPresenter = new MembersPresenter(this, _manager, _userEntity);
        _adapterOrganizationMembers = new AdapterOrganizationMembers(_manager._organizationManager, this);

        _adapterOrganizationMembers.set_organization_public_id(_userEntity._organizationEntity.get_public_id());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _listType = extras.getString("listType");
            _adapterOrganizationMembers.set_typeList(_listType);
        }

        if (Objects.equals(_listType, "invitation")) {
            setContentView(R.layout.members_invitation);
            setUpInvitationMember();
        }
        else if (Objects.equals(_listType, "members")) {
            setContentView(R.layout.members_list);
            setUpListMember();
        }

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
    }

    private void setUpListMember() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.organization_members_list);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(_adapterOrganizationMembers);

        _membersPresenter.getMembersOrginzation(_userEntity._organizationEntity.get_public_id());
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if (Objects.equals(_listType, "invitation")) {
            _toolbar.setTitle("Inviter des membres");
        }
        else if (Objects.equals(_listType, "members")) {
            _toolbar.setTitle("Liste des membres");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    private void setUpInvitationMember() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.organization_invit_list);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(_adapterOrganizationMembers);


        this._invitOrganization_search = (EditText) findViewById(R.id.invitOrganization_search_editText);
        if (this._invitOrganization_search != null) {
            this._invitOrganization_search.addTextChangedListener(this);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        _membersPresenter.searchUser();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public String getSearch() {
        return this._invitOrganization_search.getText().toString();
    }

    @Override
    public void setErrorSearch(int resId) {
        TextInputLayout invitOrganization_search_inputLayout = (TextInputLayout) findViewById(R.id.invitOrganization_search_inputLayout);
        invitOrganization_search_inputLayout.setError((resId == 0 ? null : getString(resId)));
    }

    @Override
    public void setMembersOrganization(List<ProfileEntity> profileEntities) {
        if (profileEntities != null) {
            for (Iterator<ProfileEntity> profileEntityIterator = profileEntities.listIterator(); profileEntityIterator.hasNext();) {
                ProfileEntity profileEntity = profileEntityIterator.next();
                if (Objects.equals(profileEntity.get_public_id(), _userEntity._profileEntity.get_public_id())) {
                    profileEntityIterator.remove();
                }
            }
        }
        _adapterOrganizationMembers.setProfileEntityList(profileEntities);
    }

    @Override
    public void buttonClicked(String typeList, String profile_public_id) {
        switch (typeList) {
            case "invitation":
                _membersPresenter.onInvitationClicked(_userEntity._organizationEntity.get_public_id(), profile_public_id);
                break;
            case "members":
                _membersPresenter.onKickMemberClicked(_userEntity._organizationEntity.get_public_id(), profile_public_id);
                break;
        }
    }

    @Override
    public void restartSearch() {
        _adapterOrganizationMembers.setProfileEntityList(null);
        _invitOrganization_search.setText("");
    }
}