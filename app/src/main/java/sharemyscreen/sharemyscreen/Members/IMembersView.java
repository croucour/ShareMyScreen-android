package sharemyscreen.sharemyscreen.Members;

import java.util.List;

import sharemyscreen.sharemyscreen.Entities.ProfileEntity;

/**
 * Created by cleme_000 on 25/02/2016.
 */
public interface IMembersView {

    String getSearch();

    void setErrorSearch(int resId);

    void setMembersOrganization(List<ProfileEntity> profileEntities);

    void buttonClicked(String typeList, String profile_public_id);

    void restartSearch();
}
