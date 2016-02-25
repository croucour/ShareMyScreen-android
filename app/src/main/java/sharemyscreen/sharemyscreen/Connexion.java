package sharemyscreen.sharemyscreen;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Connexion {
    public static boolean haveInternetConnection(Context pContext){
        NetworkInfo network = ((ConnectivityManager) pContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return !(network == null || !network.isConnected());
    }
}
