package sharemyscreen.sharemyscreen.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by cleme_000 on 20/02/2016.
 */


public class MyBroadcastreceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent monServiceIntent = new Intent(context, MyService.class);
        context.startService(monServiceIntent);
    }
}
