package sharemyscreen.sharemyscreen.SignIn;

import android.support.design.widget.CoordinatorLayout;

import com.dd.processbutton.iml.ActionProcessButton;

/**
 * Created by cleme_000 on 23/02/2016.
 */
public interface ISignInView {

    String getUsername();
    String getPassword();

    void showUsernameError(int resId);
    void showPasswordError(int resId);

    void disableUsernameError();

    void disablePasswordError();

    void setProcessLoadingButton(int process);

    CoordinatorLayout getCoordinatorLayout();

    ActionProcessButton getActionProcessButton();

    void startRoomActivity();
}
