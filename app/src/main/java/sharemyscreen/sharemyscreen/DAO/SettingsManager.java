package sharemyscreen.sharemyscreen.DAO;

import android.content.Context;

/**
 * Created by roucou-c on 07/12/15.
 */
public class SettingsManager {

    private SettingsDAO settingsDAO;

    public SettingsManager(Context pContext) {

        this.settingsDAO = new SettingsDAO(pContext);
        this.settingsDAO.open();
    }

    public void addSettings(String key, String value) {
        if (this.settingsDAO.select(key) != null) {
            this.settingsDAO.modify(key, value);
        }
        else {
            this.settingsDAO.add(key, value);
        }
    }

    public String select(String key) {
        return this.settingsDAO.select(key);
    }

    public void restartSettings() {
        this.settingsDAO.restartDatabase();
    }

    public void delete(String key) {
        this.settingsDAO.delete(key);
    }
}
