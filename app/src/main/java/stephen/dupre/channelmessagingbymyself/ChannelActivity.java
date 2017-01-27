package stephen.dupre.channelmessagingbymyself;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.Collections;
import java.util.HashMap;

/**
 * Created by dupres on 27/01/2017.
 */
public class ChannelActivity {
    import android.content.SharedPreferences;
    import android.os.Handler;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.widget.ListView;

    import com.google.gson.Gson;

    import java.util.Collections;
    import java.util.HashMap;
    import java.util.Timer;
    import java.util.TimerTask;

    public class ChannelActivity extends AppCompatActivity {
        private ListView messages;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_channel);

            messages = (ListView) findViewById(R.id.listViewMessages);

            final Handler handler = new Handler();

            final Runnable r = new Runnable() {
                public void run() {
                    SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
                    String accesstoken = settings.getString("accesstoken","");

                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("accesstoken",accesstoken);
                    params.put("channelid", getIntent().getStringExtra("channelid"));
                    Connexion connexion = new Connexion(getApplicationContext(), params, "http://www.raphaelbischof.fr/messaging/?function=getmessages");
                    connexion.execute();

                    connexion.setOnDownloadCompleteListener(new  OnDownloadCompleteListener() {
                        @Override
                        public void onDownloadCompleted(String result) {
                            //déserialisation
                            Gson gson = new Gson();
                            MessageContainer obj = gson.fromJson(result, MessageContainer.class);
                            Collections.reverse(obj.getMessages());
                            messages.setAdapter((new MessageListAdapter(getApplicationContext(), obj.getMessages())));
                        }
                    });

                    handler.postDelayed(this, 1000);
                }
            };

            handler.postDelayed(r, 1000);
        }
    }
}
