package sharemyscreen.sharemyscreen.Model;

import android.content.Context;

import sharemyscreen.sharemyscreen.DAO.RequestOfflineManager;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.Entities.RequestOfflineEntity;
import sharemyscreen.sharemyscreen.MyApi;

/**
 * Created by cleme_000 on 22/02/2016.
 */
public class RequestOfflineModel extends MyModel {

    private RequestOfflineManager _requestOfflineManager = null;

    public RequestOfflineModel(RequestOfflineEntity requestOfflineEntity, Context pContext) {
        super(requestOfflineEntity, pContext);
        _requestOfflineManager = new RequestOfflineManager(pContext);
    }

    public void runRequest(final RequestOfflineEntity requestOfflineEntity) {
        _myApi = new MyApi(_profileLogged, _pContext) {
            @Override
            protected void onPostExecute(String str) {
                RequestOfflineEntity requestOfflineEntityResult =  _requestOfflineManager.get_requestOfflineDAO().selectById(requestOfflineEntity.get_id());
                if (requestOfflineEntityResult != null) {
                    requestOfflineEntityResult.set_responseCode(this.get_responseCode());
//                    requestOfflineEntity.set_responseCode(this.get_responseCode()); // TODO : set error msg
                    _requestOfflineManager.modify(requestOfflineEntityResult);
                }

            }
        };

        _myApi.setdataParams(requestOfflineEntity.get_dataParams());
        _myApi.setCurrentResquest(requestOfflineEntity.get_request(), requestOfflineEntity.get_methode());
        _myApi.execute();
    }
}
