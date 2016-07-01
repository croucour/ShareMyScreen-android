package sharemyscreen.sharemyscreen.DAO;

import android.database.sqlite.SQLiteDatabase;

import sharemyscreen.sharemyscreen.Entities.ProfileEntity;

/**
 * Created by roucou-c on 09/12/15.
 */
public class ProfileManager extends ProfileDAO{

    public ProfileManager(SQLiteDatabase mDb) {
        super(mDb);
    }

    public long add(ProfileEntity profileEntity) {
        if (profileEntity !=  null && profileEntity.get_public_id() != null) {
            ProfileEntity newProfile = this.selectByPublic_id(profileEntity.get_public_id());
            if (newProfile != null) {
                profileEntity.set_id(newProfile.get_id());
                super.modify(profileEntity);
                return newProfile.get_id();
            }
            else {
                return super.add(profileEntity);
            }
        }
        return 0;
    }

    public void modifyProfil(ProfileEntity profile) {
        this.modify(profile);
    }

//    public ProfileEntity modifyProfil(String username, String password) {
//
//        ProfileEntity profile = this.selectByEmail(username);
//
//        if (profile == null) {
//            long id = this.add(new ProfileEntity(username, password));
//            return this.selectByProfilePublicId(id);
//        }
//        else {
//            profile.set_password(password);
//            this.modify(profile);
//            return profile;
//        }
//    }
//
//    public void modifyProfil(HashMap<String, String> mapProfile) {
//        String username = mapProfile.get("username");
//        if (username != null) {
//            ProfileEntity profile = this.selectByEmail(username);
//            if (profile != null) {
//                profile.update(mapProfile);
//                this.modify(profile);
//            }
//        }
//
//    }
}
