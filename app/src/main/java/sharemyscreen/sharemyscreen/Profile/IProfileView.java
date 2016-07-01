package sharemyscreen.sharemyscreen.Profile;

import android.support.design.widget.CoordinatorLayout;

import com.dd.processbutton.iml.ActionProcessButton;

import sharemyscreen.sharemyscreen.Entities.ProfileEntity;

/**
 * Created by cleme_000 on 25/02/2016.
 */
public interface IProfileView {
    CoordinatorLayout getCoordinatorLayout();

    void populateProfile(ProfileEntity profileLogged);

    String getFirstName();

    String getLastName();

    String getEmail();

    void setProcessLoadingButton(int process);

    void setErrorFirstName(int resId);

    void setErrorLastName(int resId);

    void setErrorEmail(int resId);

    void initializeInputLayout();

    ActionProcessButton getActionProcessButton();

    void finish();
}
