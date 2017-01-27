package stephen.dupre.channelmessagingbymyself;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

/**
 * Created by dupres on 27/01/2017.
 */
public class loginActivity extends AppCompatActivity{
    private EditText identifiant;
    private EditText passe;
    private Button btnValider;

    public static final String PREFS_NAME = "Fichier_preferences";

    //Fonction executée lors de l'instanciation de loginActivity
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //Affichage de la vue xml activity_login
        setContentView(R.layout.activity_login);

        //récupération du bouton "Valider" ainsi que de l'identifiant et du passe rentrés
        btnValider = (Button) findViewById(R.id.button);
        identifiant = (EditText) findViewById(R.id.et_identifiant);
        passe = (EditText) findViewById(R.id.et_passe);

        //Création du Listener de l'évènemenent OnClick du bouton "Valider"
        btnValider.setOnClickListener(new View.OnClickListener(){
            //Réécriture de l'évènement OnClick() de "Valider"
            @Override
            public void onClick(View v){
                //Création d'une HashMap ~Tableau [String,String] contenant id et passe
                HashMap<String,String> parametres = new HashMap<String,String>();
                parametres.put("username",identifiant.getText().toString());
                parametres.put("password",passe.getText().toString());

                //Instanciation d'une nouvelle connexion à l'URL "http://www.raphaelbischof.fr/messaging/?function=connect"
                //avec en paramètres id et passe
                Connexion connexion = new Connexion(getApplicationContext(),parametres,"http://www.raphaelbischof.fr/messaging/?function=connect");
            }
        });

    }
}

