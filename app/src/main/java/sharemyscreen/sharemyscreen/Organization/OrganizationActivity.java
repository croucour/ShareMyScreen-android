package sharemyscreen.sharemyscreen.Organization;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;

import java.util.ArrayList;
import java.util.List;

import sharemyscreen.sharemyscreen.Entities.OrganizationEntity;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.Fab;
import sharemyscreen.sharemyscreen.MyActivityDrawer;
import sharemyscreen.sharemyscreen.R;

/**
 * Created by roucou_c on 20/06/2016.
 */
public class OrganizationActivity extends MyActivityDrawer implements View.OnClickListener, IOrganizationView, TextWatcher, SwipeRefreshLayout.OnRefreshListener{
    private OrganizationPresenter _organizationPresenter;

    /**
     * Creation
     */
    private View _viewDialogCreate;
    private DialogPlus _dialogCreate;
    private EditText _organization_dialog_create_name;
    private Button _organization_dialog_create_submit;
    private Button _organization_dialog_create_cancel;
    /**
     * Invitation
     */
    private EditText _invitOrganization_search = null;
    private SwipeRefreshLayout _swipeRefreshLayout;
    private AdapterOrganization _adapterOrganization;
    private View _viewDialogInvit;
    private DialogPlus _dialogInvit;
    private AdapterOrganizationMembers _adapterOrganizationMembers;
    private String _organization_public_id;
    private View _viewDialogMembers;
    private DialogPlus _dialogMembers;
    private Fab _fab;
    private RecyclerView _recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ProfileEntity profileEntity = _manager._profileManager.selectByPublic_id("007");

        if (profileEntity == null) {
            profileEntity = new ProfileEntity(0, "test2", "test2@test.fr", "");
            profileEntity.set__id("007");
            profileEntity.set_firstName("Clément");
            profileEntity.set_lastName("Roucour");
            _manager._profileManager.add(profileEntity);
            OrganizationEntity organizationEntity = new OrganizationEntity();
            organizationEntity.set_name("orga");
            organizationEntity.set_creator(profileEntity);
            organizationEntity.set_creator_public_id("007");
            organizationEntity.set_owner_public_id("007");
            organizationEntity.set_owner(profileEntity);
            organizationEntity.set_public_id("79");

            List<ProfileEntity> profileEntities = new ArrayList<ProfileEntity>();

            profileEntities.add(profileEntity);
            ProfileEntity profileEntity2 = new ProfileEntity(1, "test3", "test3@test.fr", "");
            profileEntity2.set__id("009");
            profileEntity2.set_firstName("Clément3");
            profileEntity2.set_lastName("Roucour3");
            profileEntities.add(profileEntity2);

            organizationEntity.set_members(profileEntities);
            _manager._organizationManager.add(organizationEntity);

            organizationEntity.set_name("organization");
            organizationEntity.set_public_id("80");

            _manager._organizationManager.add(organizationEntity);
            organizationEntity.set_name("nom de l'organization couzin");
            organizationEntity.set_public_id("81");

            _manager._organizationManager.add(organizationEntity);

        }

//        setContentView(R.layout.invit_organization);

        _layout_stub.setLayoutResource(R.layout.organization);
        _layout_stub.inflate();

        this._organizationPresenter = new OrganizationPresenter(this, _manager, _userEntity);

        _swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh_organization);
        _swipeRefreshLayout.setOnRefreshListener(this);

        _recyclerView = (RecyclerView) findViewById(R.id.organization_list);

        _adapterOrganization = new AdapterOrganization(_manager._organizationManager, "007", this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        _recyclerView.setLayoutManager(mLayoutManager);
        _recyclerView.setItemAnimator(new DefaultItemAnimator());
        _recyclerView.setAdapter(_adapterOrganization);


        /**
         * Creation
         */

        /**
         * Invitation
         */
//        this._invitOrganization_search = (EditText) findViewById(R.id.invitOrganization_search_editText);
//        this._invitOrganization_submit = (Button) findViewById(R.id.invitOrganization_submit);
//        this._invitOrganization_back = (Button) findViewById(R.id.invitOrganization_back);
//        if (this._invitOrganization_submit != null && this._invitOrganization_back != null) {
//            this._invitOrganization_submit.setOnClickListener(this);
//            this._invitOrganization_back.setOnClickListener(this);
//            this._invitOrganization_search.setOnKeyListener(this);
//            this._invitOrganization_search.addTextChangedListener(this);
//        }

        _adapterOrganizationMembers = new AdapterOrganizationMembers(_manager._organizationManager, this);

        setUpCreateOrganization();
        setUpFab();
        setUpOrganizationInvit();
        setUpOrganizationMembers();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.organization_fab:
                this.showDialog("create", null);
                break;
        }
    }

    @Override
    public String getName() {
        return this._organization_dialog_create_name.getText().toString();
    }

    @Override
    public String getSearch() {
        return this._invitOrganization_search.getText().toString();
    }

    @Override
    public void setErrorName(int resId) {
        TextInputLayout signin_password_inputLayout = (TextInputLayout) findViewById(R.id.organization_dialog_create_name_inputLayout);
        signin_password_inputLayout.setError((resId == 0 ? null : getString(resId)));
    }

    @Override
    public void setErrorSearch(int resId) {
//        TextInputLayout invitOrganization_search_inputLayout = (TextInputLayout) findViewById(R.id.invitOrganization_search_inputLayout);
//        invitOrganization_search_inputLayout.setError((resId == 0 ? null : getString(resId)));
    }

    @Override
    public void setRefreshing(boolean state) {
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh_organization);
        swipeRefreshLayout.setRefreshing(state);
    }

    @Override
    public void onRefresh() {
        this._organizationPresenter.onSwipedForRefreshOrganizations();
    }

    private void setUpFab() {

        _fab = (Fab) findViewById(R.id.organization_fab);
        _fab.attachToRecyclerView(_recyclerView);
        _fab.setOnClickListener(this);
    }

    public void setUpOrganizationInvit() {

        _viewDialogInvit = this.getLayoutInflater().inflate(R.layout.organization_dialog_invit, null, false);

        com.orhanobut.dialogplus.ViewHolder viewHolder = new com.orhanobut.dialogplus.ViewHolder(_viewDialogInvit);

        OnClickListener onClickListener = new OnClickListener() {

            @Override
            public void onClick(DialogPlus dialog, View view) {
                switch (view.getId()) {
                    case R.id.organization_dialog_invit_cancel:
                        dialog.dismiss();
                        break;
                }
            }
        };

        _dialogInvit = DialogPlus.newDialog(this)
                .setContentHolder(viewHolder)
                .setGravity(Gravity.CENTER)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setCancelable(true)
                .setOnClickListener(onClickListener)
                .create();

        RecyclerView recyclerView = (RecyclerView) _viewDialogInvit.findViewById(R.id.organization_invit_list);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(_adapterOrganizationMembers);


        this._invitOrganization_search = (EditText) _viewDialogInvit.findViewById(R.id.invitOrganization_search_editText);
        if (this._invitOrganization_search != null) {
            this._invitOrganization_search.addTextChangedListener(this);
        }
    }

    private void setUpOrganizationMembers() {
        _viewDialogMembers = this.getLayoutInflater().inflate(R.layout.organization_dialog_members, null, false);

        com.orhanobut.dialogplus.ViewHolder viewHolder = new com.orhanobut.dialogplus.ViewHolder(_viewDialogMembers);


        _dialogMembers = DialogPlus.newDialog(this)
                .setContentHolder(viewHolder)
                .setGravity(Gravity.CENTER)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setCancelable(true)
                .create();

        RecyclerView recyclerView = (RecyclerView) _viewDialogMembers.findViewById(R.id.organization_members_list);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(_adapterOrganizationMembers);
    }

    private void setUpCreateOrganization() {
        _viewDialogCreate = this.getLayoutInflater().inflate(R.layout.organization_dialog_create, null, false);

        this._organization_dialog_create_name = (EditText) _viewDialogCreate.findViewById(R.id.organization_dialog_create_name_editText);
        this._organization_dialog_create_submit = (Button) _viewDialogCreate.findViewById(R.id.organization_dialog_create_submit);
        this._organization_dialog_create_cancel = (Button) _viewDialogCreate.findViewById(R.id.organization_dialog_create_cancel);
        if (this._organization_dialog_create_submit  != null && this._organization_dialog_create_cancel != null) {
            this._organization_dialog_create_submit.setOnClickListener(this);
            this._organization_dialog_create_cancel.setOnClickListener(this);
        }


        com.orhanobut.dialogplus.ViewHolder viewHolder = new com.orhanobut.dialogplus.ViewHolder(_viewDialogCreate);

        OnClickListener onClickListener = new OnClickListener() {

            @Override
            public void onClick(DialogPlus dialog, View view) {
                switch (view.getId()) {
                    case R.id.organization_dialog_create_submit:
                        _organizationPresenter.onCreateClicked();
                        break;
                    case R.id.organization_dialog_create_cancel:
                        closeDialog("create");
                        break;
                }
            }
        };

        _dialogCreate = DialogPlus.newDialog(this)
                .setContentHolder(viewHolder)
                .setGravity(Gravity.CENTER)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOnClickListener(onClickListener)
                .setCancelable(true)
                .create();
    }


    @Override
    public void showDialog(String action, String organization_public_id) {

        OrganizationEntity organizationEntity = null;

        TextView title = (TextView) _dialogInvit.getHolderView().findViewById(R.id.organization_title);
        if (organization_public_id != null) {
            organizationEntity = _manager._organizationManager.selectByPublic_id(organization_public_id);

            _organization_public_id = organization_public_id;

            _adapterOrganizationMembers.set_organization_public_id(organization_public_id);
        }
        switch (action) {
            case "invitation" :
                title.setText(getString(R.string.invitOrganization_title)+" "+organizationEntity.get_name());
                _dialogInvit.show();
                _adapterOrganizationMembers.setProfileEntityList(null);
                _adapterOrganizationMembers.set_typeDialog(action);
                break;
            case "members" :
                title.setText(getString(R.string.membersOrganization_title)+" "+organizationEntity.get_name());
                _dialogMembers.show();
                _organizationPresenter.getOrganizations();
                _adapterOrganizationMembers.set_typeDialog(action);
                break;
            case "quit" :
                break;
            case "create" :
                _dialogCreate.show();
                break;
        }
    }

    @Override
    public void closeDialog(String target) {
        switch (target) {
            case "invitation":
                _dialogInvit.dismiss();
                _invitOrganization_search.setText("");
                _adapterOrganizationMembers.setProfileEntityList(null);
                break;
            case "members" :
                _dialogMembers.dismiss();
                _adapterOrganizationMembers.setProfileEntityList(null);
                break;
            case "create" :
                _dialogCreate.dismiss();
                _organizationPresenter.getOrganizations();
                break;
        }
    }

    @Override
    public void setMembersOrganization(List<ProfileEntity> profileEntityList) {
        _adapterOrganizationMembers.setProfileEntityList(profileEntityList);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        _organizationPresenter.searchUser();
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void buttonDialogClicked(String action, String profile_public_id) {
        switch (action) {
            case "invitation":
                _organizationPresenter.onInvitationClicked(_organization_public_id, profile_public_id);
                break;
            case "members":
                _organizationPresenter.onKickMemberClicked(_organization_public_id, profile_public_id);
                break;
        }
    }

    public void updateOrganizationEntityList() {
        _adapterOrganization.updateOrganizationEntityList();

        if ((_dialogMembers.isShowing()) && _organization_public_id != null) {
            _adapterOrganizationMembers.updateOrganizationProfileEntityList(_organization_public_id);
        }
    }

    @Override
    public void onBackPressed() {
        if (_dialogInvit.isShowing()) {
            closeDialog("invitation");
        }
        else if (_dialogMembers.isShowing()) {
            closeDialog("members");
        }
        else {
            super.onBackPressed();
        }
    }
}
