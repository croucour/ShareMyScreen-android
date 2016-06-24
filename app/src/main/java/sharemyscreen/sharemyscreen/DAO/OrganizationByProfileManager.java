package sharemyscreen.sharemyscreen.DAO;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import sharemyscreen.sharemyscreen.Entities.OrganizationEntity;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;

/**
 * Created by roucou_c on 20/06/2016.
 */
public class OrganizationByProfileManager extends OrganizationByProfileDAO {

    public OrganizationByProfileManager(SQLiteDatabase mDb) {
        super(mDb);
    }

    public void add(OrganizationEntity organizationEntity) {

        if (organizationEntity != null && organizationEntity.get_members() != null) {
            List<ProfileEntity> profileEntities = organizationEntity.get_members();

            super.deleteByOrganization(organizationEntity.get_public_id());

            for (ProfileEntity profileEntity : profileEntities) {
                super.add(organizationEntity, profileEntity);
            }
        }
    }
}
