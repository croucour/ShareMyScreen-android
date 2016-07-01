package sharemyscreen.sharemyscreen.Profile;

import java.util.HashMap;

import sharemyscreen.sharemyscreen.DAO.Manager;
import sharemyscreen.sharemyscreen.Entities.UserEntity;
import sharemyscreen.sharemyscreen.R;

/**
 * Created by cleme_000 on 25/02/2016.
 */
public class ProfilePresenter {

    private final IProfileView _view;
    private final ProfileService _profileService;

    public ProfilePresenter(IProfileView view, Manager manager, UserEntity userEntity) {
        this._view = view;
        this._profileService = new ProfileService(view, manager, userEntity);
    }

    protected void getProfile() {
        this._profileService.getProfile(null);
    }

    public void onSaveClicked() {
        boolean error = false;

        String firstName = this._view.getFirstName();
        String lastName = this._view.getLastName();
        String email = this._view.getEmail();

        this._view.initializeInputLayout();

        if (firstName.isEmpty()) {
            this._view.setErrorFirstName(R.string.profile_usernameEmpty);
            error = true;
        }
        if (lastName.isEmpty()) {
            this._view.setErrorLastName(R.string.profile_lastNameEmpty);

            error = true;
        }
        if (email.isEmpty()) {
            this._view.setErrorEmail(R.string.profile_EmailEmpty);
            this._view.setErrorEmail(R.string.notEmail);
            error = true;
        }

        if (!error) {
            this._view.setProcessLoadingButton(1);

            HashMap<String, String> userParams = new HashMap<>();

            userParams.put("firstName", firstName);
            userParams.put("lastName", lastName);
            userParams.put("email", email);

            this._profileService.patchProfile(userParams);
        }
    }
}
