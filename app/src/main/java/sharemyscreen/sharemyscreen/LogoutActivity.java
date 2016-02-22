package sharemyscreen.sharemyscreen;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by roucou-c on 01/10/15.
 */
public class LogoutActivity extends Activity implements View.OnClickListener {

    protected Button logout_submit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.logout);

        this.logout_submit = (Button) findViewById(R.id.logout_submit);

        this.logout_submit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.logout_submit) {
//            MyApi myApi = new MyApi(this.getApplicationContext()) {
//                @Override
//                protected void onPostExecute(String str) {
//
//                    SharedPreferences tokenFile = this._pContext.getSharedPreferences(TOKENFILE, android.content.Context.MODE_PRIVATE);
//
//                    SharedPreferences.Editor edit = tokenFile.edit();
//                    edit.clear();
//                    edit.apply();
//                    Log.i("info", "vous etes déconnecté");
//                }
//            };
//
//            myApi.setCurrentResquest("/user/logout");
//            myApi.execute();
            this.finish();
        }
    }
}
