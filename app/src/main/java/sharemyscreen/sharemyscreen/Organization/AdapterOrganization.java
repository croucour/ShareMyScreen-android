package sharemyscreen.sharemyscreen.Organization;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.List;

import sharemyscreen.sharemyscreen.DAO.OrganizationManager;
import sharemyscreen.sharemyscreen.Entities.OrganizationEntity;
import sharemyscreen.sharemyscreen.R;

/**
 * Created by roucou_c on 23/06/2016.
 */
public class AdapterOrganization extends RecyclerView.Adapter<AdapterOrganization.ViewHolder> {
    private final OrganizationManager _organizationManager;
    private final String _profile_public_id;
    private final IOrganizationView _IOrganizationView;
    private List<OrganizationEntity> _organizationEntityList;

    public AdapterOrganization(OrganizationManager organizationManager, String profile_public_id, IOrganizationView view) {
        _organizationManager = organizationManager;
        _profile_public_id = profile_public_id;
        updateOrganizationEntityList();
        _IOrganizationView = view;


    }

    public boolean updateOrganizationEntityList() {
        _organizationEntityList = this._organizationManager.selectAllByProfile_id(_profile_public_id);
        return _organizationEntityList != null;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.organization_single_row, parent, false);

        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrganizationEntity organizationEntity = _organizationEntityList.get(position);
        if (organizationEntity != null) {
            holder.organization_name.setText(organizationEntity.get_name());
            holder.organization_public_id = organizationEntity.get_public_id();
        }
    }

    @Override
    public int getItemCount() {
        return _organizationEntityList == null ? 0 : _organizationEntityList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        TextView organization_name;
        ImageView organization_img_submenu;
        String organization_public_id = null;

//        private final IOrganizationView _IOrganizationView;

        public ViewHolder(View view) {
            super(view);
            organization_name = (TextView) view.findViewById(R.id.organization_name);
            organization_img_submenu = (ImageView) view.findViewById(R.id.organization_img_submenu);
            organization_img_submenu.setOnClickListener(this);
            view.setTag(this);
//            _IOrganizationView = iOrganizationView;
        }

        @Override
        public void onClick(View v) {
            if (v == organization_img_submenu) {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.inflate(R.menu.organization_submenu);
                popup.setOnMenuItemClickListener(this);
                popup.show();
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.organization_submenu_invit:
                    _IOrganizationView.showDialog("invitation", organization_public_id);
                    break;
                case R.id.organization_submenu_members:
                    _IOrganizationView.showDialog("members", organization_public_id);
                    break;
                case R.id.organization_submenu_quit:
                    break;
            }
            return true;
        }
    }
}
