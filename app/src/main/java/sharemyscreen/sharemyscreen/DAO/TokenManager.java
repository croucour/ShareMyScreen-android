package sharemyscreen.sharemyscreen.DAO;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import sharemyscreen.sharemyscreen.Entities.TokenEntity;

/**
 * Created by cleme_000 on 01/03/2016.
 */
public class TokenManager extends TokenDAO {
    public TokenManager(SQLiteDatabase mDb) {
        super(mDb);
    }

    @Override
    public long add(TokenEntity tokenEntity) {
        if (tokenEntity != null && tokenEntity.get_profile_public_id() != null) {
            TokenEntity tokenEntityExist = selectByProfilePublicId(tokenEntity.get_profile_public_id());

            if (tokenEntityExist != null) {
                this.modify(tokenEntity);
                return tokenEntity.get_id();
            }
            else {
                return super.add(tokenEntity);
            }
        }
        return 0;
    }

    @Override
    public void modify(TokenEntity tokenEntity) {
        super.modify(tokenEntity);
    }

    public TokenEntity selectByProfilePublicId(String profile_public_id_connected) {
        if (profile_public_id_connected == null) {
            return null;
        }
        return super.selectByProfileId(profile_public_id_connected);
    }
}
