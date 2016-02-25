package sharemyscreen.sharemyscreen.DAO;

import android.content.Context;

/**
 * Created by roucou-c on 07/12/15.
 */
public class SettingsManager extends  SettingsDAO{

    public SettingsManager(Context pContext) {
        super(pContext);
    }

    public void addSettings(String key, String value) {
        if (super.select(key) != null) {
            super.modify(key, value);
        }
        else {
            super.add(key, value);
        }
    }

    public void delete(String key) {
        super.delete(key);
    }
}
