package sharemyscreen.sharemyscreen.DAO;

import android.content.Context;

import sharemyscreen.sharemyscreen.Entities.TokenEntity;

/**
 * Created by cleme_000 on 01/03/2016.
 */
public class TokenManager extends TokenDAO {
    public TokenManager(Context pContext) {
        super(pContext);
    }

    @Override
    public void modify(TokenEntity tokenEntity) {

        super.modify(tokenEntity);
    }

    public TokenEntity selectById(String current_token_id) {
        if (current_token_id == null) {
            return null;
        }
        return super.selectById(Long.parseLong(current_token_id));
    }
}
