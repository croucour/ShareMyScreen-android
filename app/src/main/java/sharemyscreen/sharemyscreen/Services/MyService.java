package sharemyscreen.sharemyscreen.Services;

/**
 * Created by cleme_000 on 20/02/2016.
 */
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

import sharemyscreen.sharemyscreen.DAO.ProfileManager;
import sharemyscreen.sharemyscreen.DAO.RequestOfflineManager;
import sharemyscreen.sharemyscreen.Entities.ProfileEntity;
import sharemyscreen.sharemyscreen.Entities.RequestOfflineEntity;

public class MyService  extends Service{

    private static String CLASSE = MyService.class.getName();
    private static Thread _myThead = null;

    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public void onCreate ()
    {
        Log.d (CLASSE,"Création du service + lancement d'un thread.");

        SurveillanceRunnable Surveillance = new SurveillanceRunnable(getApplicationContext());
        _myThead = new Thread(Surveillance);
        _myThead.start();
    }

    public void onDestroy() {
        Log.d(CLASSE, "Destruction du service");

        if (_myThead != null) {
            _myThead.interrupt();
        }
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent,flags,startId);
    }

    public class SurveillanceRunnable implements Runnable {
        private final ProfileManager _profileManager;
        private boolean _bThreadExec = false;
        private Context _pContext;
        private final RequestOfflineManager _requestOfflineManager;
        private RequestOfflineService _requestOfflineModel = null;

        private ProfileEntity _currentprofile = null;

        public SurveillanceRunnable(Context pContext) {
            _pContext = pContext;
            _requestOfflineManager = new RequestOfflineManager(pContext);
            _profileManager = new ProfileManager(pContext);
            _requestOfflineModel = new RequestOfflineService(null, pContext);
        }

        @Override
        public void run() {
            _bThreadExec = true;
            while (_bThreadExec) {
                try {
                    Thread.sleep(1000);

                    if (this.haveInternetConnection()) {
                        this.requestOfflineTreatment();
                    }

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    _bThreadExec = false;
                }
            }

            stopForeground(true);
        }

        private void requestOfflineTreatment() {
            RequestOfflineEntity requestOfflineEntity = _requestOfflineManager.selectUntreated();
            if (requestOfflineEntity != null) {
                Log.d(CLASSE, requestOfflineEntity.toString());

                _requestOfflineManager.setRequestOfflineTreated(requestOfflineEntity.get_id());

                Log.d(CLASSE, _requestOfflineManager.selectById(requestOfflineEntity.get_id()).toString());

                _currentprofile = _profileManager.selectById(requestOfflineEntity.get_profile_id());
                if (_currentprofile !=  null) {
                    Log.d(CLASSE, _currentprofile.toString());
                    _requestOfflineModel.set_profileLogged(_currentprofile);
                    _requestOfflineModel.runRequest(requestOfflineEntity);
                }
            }
        }

        private boolean haveInternetConnection(){
            NetworkInfo network = ((ConnectivityManager) _pContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            return !(network == null || !network.isConnected());
        }
    }
}