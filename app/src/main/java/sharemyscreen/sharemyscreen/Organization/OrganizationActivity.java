package sharemyscreen.sharemyscreen.Organization;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import sharemyscreen.sharemyscreen.MyActivityDrawer;
import sharemyscreen.sharemyscreen.R;

/**
 * Created by roucou_c on 20/06/2016.
 */
public class OrganizationActivity extends MyActivityDrawer implements View.OnClickListener, IOrganizationView, TextWatcher, SwipeRefreshLayout.OnRefreshListener {
    private OrganizationPresenter _organizationPresenter;

    /**
     * Creation
     */
    private Button _createOrganization_submit = null;
    private Button _createOrganization_skip = null;
    private EditText _createOrganization_name = null;

    /**
     * Invitation
     */
    private Button _invitOrganization_back = null;
    private Button _invitOrganization_submit = null;


    private EditText _invitOrganization_search = null;
    private SwipeRefreshLayout _swipeRefreshLayout;
    private AdapterOrganization _adapterOrganization;
    private View _viewDialogInvit;
    private DialogPlus _dialogInvit;
    private AdapterOrganizationMembers _adapterOrganizationMembers;
    private String _organization_public_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ProfileEntity profileEntity = _manager._profileManager.selectByPublic_id("007");

        if (profileEntity == null) {
            profileEntity = new ProfileEntity(0, "test2", "test2@test.fr", "");
            profileEntity.set__id("007");
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

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.organization_list);

        _adapterOrganization = new AdapterOrganization(_manager._organizationManager, "007", this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(_adapterOrganization);


        /**
         * Creation
         */
        this._createOrganization_name = (EditText) findViewById(R.id.createOrganization_name_editText);
        this._createOrganization_submit = (Button) findViewById(R.id.createOrganization_submit);
        this._createOrganization_skip = (Button) findViewById(R.id.createOrganization_skip);
        if (this._createOrganization_submit != null && this._createOrganization_skip != null) {
            this._createOrganization_submit.setOnClickListener(this);
            this._createOrganization_skip.setOnClickListener(this);
        }

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
        setUpOrganizationInvit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createOrganization_submit:
                this._organizationPresenter.onCreateClicked();
                break;
            case R.id.createOrganization_skip:
                break;
//            case R.id.invitOrganization_submit:
//                break;
//            case R.id.invitOrganization_back:
//                break;
        }
    }

    @Override
    public String getName() {
        return this._createOrganization_name.getText().toString();
    }

    @Override
    public String getSearch() {
        return this._invitOrganization_search.getText().toString();
    }

    @Override
    public void setErrorName(int resId) {
        TextInputLayout signin_password_inputLayout = (TextInputLayout) findViewById(R.id.createOrganization_name_inputLayout);
        signin_password_inputLayout.setError((resId == 0 ? null : getString(resId)));
    }

    @Override
    public void setErrorSearch(int resId) {
//        TextInputLayout invitOrganization_search_inputLayout = (TextInputLayout) findViewById(R.id.invitOrganization_search_inputLayout);
//        invitOrganization_search_inputLayout.setError((resId == 0 ? null : getString(resId)));
    }

    @Override
    public void setRefreshing(boolean state) {
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setRefreshing(state);
    }

    @Override
    public void showInvitation() {
        setContentView(R.layout.invit_organization);
    }

    @Override
    public void onRefresh() {
        this._organizationPresenter.onSwipedForRefreshOrganizations();
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

        _adapterOrganizationMembers = new AdapterOrganizationMembers(_manager._organizationManager, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(_adapterOrganizationMembers);


        this._invitOrganization_search = (EditText) _viewDialogInvit.findViewById(R.id.invitOrganization_search_editText);
        if (this._invitOrganization_search != null) {
            this._invitOrganization_search.addTextChangedListener(this);
        }
    }

    @Override
    public void showDialog(String action, String organization_public_id) {
        OrganizationEntity organizationEntity = _manager._organizationManager.selectByPublic_id(organization_public_id);

        switch (action) {
            case "invit" :
                _organization_public_id = organization_public_id;
                TextView title = (TextView) _dialogInvit.getHolderView().findViewById(R.id.invitOrganization_title);
                title.setText(getString(R.string.invitOrganization_title)+" "+organizationEntity.get_name());
                _dialogInvit.show();
                break;
            case "members" :
                break;
            case "quit" :
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
            case "kick":
                break;
        }
    }
}
