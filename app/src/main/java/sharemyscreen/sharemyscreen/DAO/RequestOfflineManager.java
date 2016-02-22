package sharemyscreen.sharemyscreen.DAO;

import android.content.Context;

import sharemyscreen.sharemyscreen.Entities.RequestOfflineEntity;

/**
 * Created by cleme_000 on 21/02/2016.
 */
public class RequestOfflineManager {
    private RequestOfflineDAO _requestOfflineDAO;

    public RequestOfflineDAO get_requestOfflineDAO() {
        return _requestOfflineDAO;
    }

    public RequestOfflineManager(Context pContext) {

        this._requestOfflineDAO = new RequestOfflineDAO(pContext);
        this._requestOfflineDAO.open();
    }

    public void add(RequestOfflineEntity requestOfflineEntity) {
        this._requestOfflineDAO.add(requestOfflineEntity);
    }

    public void modify(RequestOfflineEntity requestOfflineEntity) {
        this._requestOfflineDAO.modify(requestOfflineEntity);
    }
}
