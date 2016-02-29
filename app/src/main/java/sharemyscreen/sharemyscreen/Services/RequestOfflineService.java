package sharemyscreen.sharemyscreen.Services;

import android.content.Context;

import sharemyscreen.sharemyscreen.*;
import sharemyscreen.sharemyscreen.DAO.RequestOfflineManager;
import sharemyscreen.sharemyscreen.Entities.RequestOfflineEntity;
import sharemyscreen.sharemyscreen.MyService;

/**
 * Created by cleme_000 on 22/02/2016.
 */
public class RequestOfflineService extends MyService {

    private RequestOfflineManager _requestOfflineManager = null;

    public RequestOfflineService(RequestOfflineEntity requestOfflineEntity, Context pContext) {
        super(requestOfflineEntity, pContext);
        _requestOfflineManager = new RequestOfflineManager(pContext);
    }

    public void runRequest(final RequestOfflineEntity requestOfflineEntity) {
       MyApi myApi = new MyApi(_profileLogged, _pContext) {
            @Override
            protected void onPostExecute(String str) {
                RequestOfflineEntity requestOfflineEntityResult =  _requestOfflineManager.selectById(requestOfflineEntity.get_id());
                if (requestOfflineEntityResult != null) {
                    requestOfflineEntityResult.set_responseCode(this.get_responseCode());
//                    requestOfflineEntity.set_responseCode(this.get_responseCode()); // TODO : set error msg
                    _requestOfflineManager.modify(requestOfflineEntityResult);
                }
            }
        };

        myApi.setDataParams(requestOfflineEntity.get_dataParams());
        myApi.setCurrentRequest(requestOfflineEntity.get_request(), requestOfflineEntity.get_methode());
        myApi.execute();
    }
}
