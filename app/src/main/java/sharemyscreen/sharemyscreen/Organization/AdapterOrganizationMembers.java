package sharemyscreen.sharemyscreen.Organization;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import sharemyscreen.sharemyscreen.DAO.OrganizationManager;
import sharemyscreen.sharemyscreen.Entities.OrganizationEntity;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.R;

/**
 * Created by roucou_c on 23/06/2016.
 */
public class AdapterOrganizationMembers extends RecyclerView.Adapter<AdapterOrganizationMembers.ViewHolder> {
    private final OrganizationManager _organizationManager;
    private final IOrganizationView _IOrganizationView;
    private List<ProfileEntity> _profileEntityList = null;
    private String _typeDialog = null;
    private String _organization_public_id;

    public AdapterOrganizationMembers(OrganizationManager organizationManager, IOrganizationView view) {
        _organizationManager = organizationManager;
        _IOrganizationView = view;
    }

    public void updateOrganizationProfileEntityList(String organization_public_id) {
        OrganizationEntity organizationEntity = _organizationManager.selectByPublic_id(organization_public_id);
        if (organizationEntity != null && organizationEntity.get_members() != null) {
            this.setProfileEntityList(organizationEntity.get_members());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.organization_member_single_row, parent, false);

        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProfileEntity profileEntity  = _profileEntityList.get(position);
        if (profileEntity != null) {
            holder.email.setText(profileEntity.get_email());
            holder.name.setText(profileEntity.get_lastName() + " " + profileEntity.get_firstName());
            holder.picture.setImageResource(R.drawable.default_avatar);
            holder.profile_public_id = profileEntity.get__id();

            if (Objects.equals(_typeDialog, "invitation")) {
                holder.button.setText("Inviter");
            }
            else if (Objects.equals(_typeDialog, "members")) {
                holder.button.setText("supprimer");
            }
            if (_organizationManager.isOwner(_organization_public_id, profileEntity.get__id())) {
                holder.button.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return _profileEntityList == null ? 0 : _profileEntityList.size();
    }

    public void setProfileEntityList(List<ProfileEntity> profileEntityList) {
        _profileEntityList = profileEntityList;
        this.notifyDataSetChanged();
    }

    public void set_typeDialog(String _typeDialog) {
        this._typeDialog = _typeDialog;
    }

    public void set_organization_public_id(String _organization_public_id) {
        this._organization_public_id = _organization_public_id;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView picture;
        TextView email;
        TextView name;
        Button button;
        String profile_public_id = null;

        public ViewHolder(View view) {
            super(view);
            picture = (ImageView) view.findViewById(R.id.organization_member_picture);
            email = (TextView) view.findViewById(R.id.organization_member_email);
            name = (TextView) view.findViewById(R.id.organization_member_name);
            button = (Button) view.findViewById(R.id.organization_member_button);
            button.setOnClickListener(this);
            view.setTag(this);
        }

        @Override
        public void onClick(View v) {
            if (v == button) {
                _IOrganizationView.buttonDialogClicked(_typeDialog, profile_public_id);
            }
        }
    }
}
