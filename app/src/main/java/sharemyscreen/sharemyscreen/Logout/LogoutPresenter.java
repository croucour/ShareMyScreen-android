package sharemyscreen.sharemyscreen.Logout;

import sharemyscreen.sharemyscreen.DAO.Manager;
import sharemyscreen.sharemyscreen.Entities.UserEntity;
import sharemyscreen.sharemyscreen.Menu.IMenuView;

/**
 * Created by cleme_000 on 25/02/2016.
 */
public class LogoutPresenter {

    private final IMenuView _view;
    private final UserEntity _userEntity;


    public LogoutPresenter(IMenuView view, Manager manager, UserEntity userEntity) {
        this._view = view;
        _userEntity = userEntity;
    }

    public void onLogoutClicked(){
        _userEntity.logout();

//        _view.startSignInActivity();
    }
}
