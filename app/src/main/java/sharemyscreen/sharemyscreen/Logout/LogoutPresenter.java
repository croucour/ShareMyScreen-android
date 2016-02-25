package sharemyscreen.sharemyscreen.Logout;

import android.content.Context;

import sharemyscreen.sharemyscreen.Menu.IMenuView;

/**
 * Created by cleme_000 on 25/02/2016.
 */
public class LogoutPresenter {

    protected LogoutService _LogoutService;
    private final IMenuView _view;

    public LogoutPresenter(IMenuView view, Context pContext) {
        this._view = view;
        this._LogoutService = new LogoutService(view, pContext);
    }

    public void onLogoutCliked(){
        this._LogoutService.logout();
    }
}
