package sharemyscreen.sharemyscreen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import sharemyscreen.sharemyscreen.DAO.SettingsManager;

/**
 * Created by roucou-c on 01/10/15.
 */
public class SettingsActivity extends Activity implements View.OnClickListener, TextView.OnEditorActionListener {

    protected Button settings_submit = null;
    protected Button settings_cancel = null;

    protected EditText settings_ip = null;
    protected EditText settings_port = null;

    private MyError myError = new MyError(this);

    private SettingsManager settingsManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.settingsManager = new SettingsManager(this);

        setContentView(R.layout.settings);

        this.settings_submit = (Button) findViewById(R.id.settings_submit);
        this.settings_cancel = (Button) findViewById(R.id.settings_cancel);

        this.settings_ip = (EditText) findViewById(R.id.settings_ip);
        this.settings_port = (EditText) findViewById(R.id.settings_port);


        this.settings_cancel.setOnClickListener(this);
        this.settings_submit.setOnClickListener(this);

        this.settings_port.setOnEditorActionListener(this);

        this.populate();
    }

    protected void populate()
    {
        String ip = this.settingsManager.select("ip");
        String port = this.settingsManager.select("port");

        if (ip != null && port != null)
        {
            this.settings_ip.setText(ip);
            this.settings_port.setText(port);
        }
        else
        {
            this.settings_ip.setText("sharemyscreen-api.herokuapp.com");
            this.settings_port.setText("80");
        }

    }

    protected boolean setErrorSubmitSettings()
    {
        String StringIp = this.settings_ip.getText().toString();
        String StringPort = this.settings_port.getText().toString();

        String msg_error = (StringIp.isEmpty() ? getString(R.string.settings_ipEmpty) : null);
        msg_error = (msg_error == null && StringPort.isEmpty() ? getString(R.string.settings_portEmpty) : msg_error);

        this.myError.msg_error = msg_error;

        return msg_error == null;
    }

    protected boolean saveSettings()
    {

        this.settingsManager.addSettings("ip", this.settings_ip.getText().toString());
        this.settingsManager.addSettings("port", this.settings_port.getText().toString());

        this.finish();
        Log.i("info", "settings sauvegardé !");
        return true;
    }

    protected boolean onSubmit() {
        this.setErrorSubmitSettings();

//        if (this.myError.displayError())
//        {
            return this.saveSettings();
//        }
//        return false;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.settings_submit) {
            this.onSubmit();
        }
        else if (v.getId() == R.id.settings_cancel)
        {
            this.finish();
        }

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (v.getId() == R.id.settings_port)
        {
            return this.onSubmit();
        }
        return false;

    }
}
