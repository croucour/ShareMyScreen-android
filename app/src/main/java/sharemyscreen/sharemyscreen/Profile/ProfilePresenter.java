package sharemyscreen.sharemyscreen.Profile;

import android.content.Context;
import android.support.design.widget.TextInputLayout;

import java.util.HashMap;

import sharemyscreen.sharemyscreen.R;
import sharemyscreen.sharemyscreen.Room.IRoomView;

/**
 * Created by cleme_000 on 25/02/2016.
 */
public class ProfilePresenter {

    private final IProfileView _view;
    private final ProfileService _profileService;

    public ProfilePresenter(IProfileView view, Context pContext) {
        this._view = view;
        this._profileService = new ProfileService(view, pContext);
    }

    protected void getProfile() {
        this._profileService.getProfile();
    }

    public void onSaveClicked() {
        boolean error = false;

        String firstName = this._view.getFirstName();
        String lastName = this._view.getLastName();
        String email = this._view.getEmail();
        String phone = this._view.getPhone();


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
//        if (phone.isEmpty()) {
//            this._view.setErrorPhone(R.string.profile_PhoneEmpty);
//            error = true;
//        }


        if (!error) {
            this._view.setProcessLoadingButton(1);

            HashMap<String, String> userParams = new HashMap<>();

            userParams.put("firstName", firstName);
            userParams.put("lastName", lastName);
            userParams.put("phone", phone);
            userParams.put("email", email);

            this._profileService.saveProfile(userParams);
        }
    }
}
