package sharemyscreen.sharemyscreen.Settings;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

import sharemyscreen.sharemyscreen.MyActivityDrawer;
import sharemyscreen.sharemyscreen.R;

/**
 * Created by cleme_000 on 03/03/2016.
 */

public class SettingsActivity extends MyActivityDrawer implements ISettingsView, CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _layout_stub.setLayoutResource(R.layout.settings);
        _layout_stub.inflate();

        SwitchCompat settings_switch_offline = (SwitchCompat) findViewById(R.id.settings_switch_offline);
        settings_switch_offline.setOnCheckedChangeListener(this);

        SwitchCompat settings_switch_display_offline = (SwitchCompat) findViewById(R.id.settings_switch_display_offline);
        settings_switch_display_offline.setOnCheckedChangeListener(this);

        settings_switch_offline.setChecked(_userEntity._settingsEntity.is_offline());

        settings_switch_display_offline.setChecked(_userEntity._settingsEntity.is_displayOffline());
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        navigation.setCheckedItem(R.id.navigation_settings);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.settings_switch_offline :
                _userEntity._settingsEntity.set_offline(isChecked);
                _userEntity.update_settingsEntity();
                break;
            case R.id.settings_switch_display_offline:
               _userEntity._settingsEntity.set_displayOffline(isChecked);
                _userEntity.update_settingsEntity();
                break;
        }
    }
}
