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

        List<RequestOfflineEntity> requestOfflineEntities =  _requestOfflineManager.selectAll();

        TableRow row;
        TextView tv1;

        for(int i=0;i<requestOfflineEntities.size();i++) {
            RequestOfflineEntity requestOfflineEntity = requestOfflineEntities.get(i);
            row = new TableRow(this);

            tv1 = new TextView(this);
            tv1.setText(String.valueOf(requestOfflineEntity.get_id()));
            tv1.setGravity(Gravity.LEFT);

            tv1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            row.addView(tv1);

            tv1 = new TextView(this);
            tv1.setText(requestOfflineEntity.get_dataParams());
            tv1.setGravity(Gravity.LEFT);

            tv1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            row.addView(tv1);

            tv1 = new TextView(this);
            tv1.setText(requestOfflineEntity.get_request());
            tv1.setGravity(Gravity.LEFT);

            tv1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            row.addView(tv1);

            tv1 = new TextView(this);
            tv1.setText(String.valueOf(requestOfflineEntity.get_responseCode()));
            tv1.setGravity(Gravity.LEFT);

            tv1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            row.addView(tv1);

            tv1 = new TextView(this);
            tv1.setText(String.valueOf(requestOfflineEntity.is_treated()));
            tv1.setGravity(Gravity.LEFT);

            tv1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            row.addView(tv1);

            table.addView(row);
        }
    }
}
