package sharemyscreen.sharemyscreen.DAO;

import android.content.Context;

/**
 * Created by roucou-c on 07/12/15.
 */
public class GlobalManager extends GlobalDAO {

    public GlobalManager(Context pContext) {
        super(pContext);

        if (this.select("settings_offline") == null) {
            super.add("settings_offline", "true");
        }
        if (this.select("settings_display_offline") == null) {
            super.add("settings_display_offline", "true");
        }
    }

    public void addGlobal(String key, String value) {
        if (super.select(key) != null) {
            super.modify(key, value);
        }
        else {
            super.add(key, value);
        }
    }

    public void deleteGlobal(String key) {
        super.delete(key);
    }
}
