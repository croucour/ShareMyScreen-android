package sharemyscreen.sharemyscreen;

import sharemyscreen.sharemyscreen.DAO.Manager;
import sharemyscreen.sharemyscreen.Entities.UserEntity;

/**
 * Created by cleme_000 on 25/02/2016.
 */
public class MyService {
    protected Manager _manager;
    public UserEntity _userEntity;

    public MyService(Manager manager, UserEntity userEntity) {
        this._manager = manager;
        this._userEntity = userEntity;
        if (_userEntity != null) {
            this._userEntity.refresh();
        }
    }

    public MyService(Manager manager) {
        this._manager = manager;
        this._userEntity = new UserEntity(_manager);
        this._userEntity.refresh();
    }
}
