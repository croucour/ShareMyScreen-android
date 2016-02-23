package sharemyscreen.sharemyscreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import sharemyscreen.sharemyscreen.DAO.RequestOfflineManager;
import sharemyscreen.sharemyscreen.Entities.RequestOfflineEntity;

/**
 * Created by cleme_000 on 22/02/2016.
 */
public class LogOfflineActivity extends AppCompatActivity {

    private RequestOfflineManager _requestOfflineManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _requestOfflineManager = new RequestOfflineManager(this);

        setContentView(R.layout.log_offline);

        TableLayout table = (TableLayout) findViewById(R.id.table_log);

//        ableRow row; // création d'un élément : ligne
//        TextView tv1,tv2; // création des cellules

        List<RequestOfflineEntity> requestOfflineEntities =  _requestOfflineManager.selectAll();

        TableRow row = null;
        TextView tv1 = null;
// pour chaque ligne
        for(int i=0;i<requestOfflineEntities.size();i++) {
            RequestOfflineEntity requestOfflineEntity = requestOfflineEntities.get(i);
            row = new TableRow(this); // création d'une nouvelle ligne

            tv1 = new TextView(this); // création cellule
            tv1.setText(String.valueOf(requestOfflineEntity.get_id())); // ajout du texte
            tv1.setGravity(Gravity.LEFT); // centrage dans la cellule
            // adaptation de la largeur de colonne à l'écran :
            tv1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            row.addView(tv1);

            // ajout de la ligne au tableau
            tv1 = new TextView(this); // création cellule
            tv1.setText(requestOfflineEntity.get_dataParams()); // ajout du texte
            tv1.setGravity(Gravity.LEFT); // centrage dans la cellule
            // adaptation de la largeur de colonne à l'écran :
            tv1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            row.addView(tv1);

            tv1 = new TextView(this); // création cellule
            tv1.setText(requestOfflineEntity.get_request()); // ajout du texte
            tv1.setGravity(Gravity.LEFT); // centrage dans la cellule
            // adaptation de la largeur de colonne à l'écran :
            tv1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            row.addView(tv1);

            tv1 = new TextView(this); // création cellule
            tv1.setText(String.valueOf(requestOfflineEntity.get_responseCode())); // ajout du texte
            tv1.setGravity(Gravity.LEFT); // centrage dans la cellule
            // adaptation de la largeur de colonne à l'écran :
            tv1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            row.addView(tv1);

            tv1 = new TextView(this); // création cellule
            tv1.setText(String.valueOf(requestOfflineEntity.is_treated())); // ajout du texte
            tv1.setGravity(Gravity.LEFT); // centrage dans la cellule
            // adaptation de la largeur de colonne à l'écran :
            tv1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            row.addView(tv1);




            // ajout de la ligne au tableau
            table.addView(row);
        }
    }
}
