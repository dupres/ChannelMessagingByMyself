package stephen.dupre.channelmessagingbymyself;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by dupres on 27/01/2017.
 */
public class Connexion extends AsyncTask<Void,Integer,String> {
    private Context context;
    private HashMap<String,String> parametres = new HashMap<String,String>();
    private String url;

    public Connexion(Context pcontext, HashMap<String, String> pparametres, String purl) {
        this.context = pcontext;
        this.parametres =pparametres;
        this.url=purl;
    }

    //Réécriture de onProgressUpdate(liste d'int) gérant l'avancement de la connexion
    //en général barre d'avancement
    @Override
    protected void onProgressUpdate(Integer...values){
        super.onProgressUpdate(values);
        Toast.makeText(context,"Avancement : "+values.toString()+"/100",Toast.LENGTH_SHORT).show();
    }

    //Réécriture de la méthode doInBackground obligatoire dans chaque tâche asynchrone
    //Gère l'action effectuée lors de la tâche, ici requête avec id et passe
    @Override
    protected String doInBackground(Void... arg0) {
        String response = this.performPostCall(this.url, this.parametres);
        return response;
    }

    //Ecriture de la fonction performaPostCall(URL auquel se connecter, parametres(id,passe)
    //Retourne un string "OK" ou ""
    public String performPostCall(String requestURL, HashMap<String, String> postDataParams) {
        //Ici url du service "Connexion"
        URL url;
        //Pour l'instant vide, est retourné avec "OK" ou ""
        String response = "";
        try {
            //requestURL : valeur de base...
            url = new URL(requestURL);
            //conn instancié à partir du retour de la fonction openConnection de url casté en HttpURLConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //Délais pour éviter l'overflow car Asynchrone
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            //"POST" pour plus de sécurité
            conn.setRequestMethod("POST");
            //Utilisé pour envoyer un body, ici inutile
            conn.setDoInput(true);
            //Inutile car true par defaut
            conn.setDoOutput(true);
            //Création d'un Stream permettant d'envoyer les identifiants
            OutputStream os = conn.getOutputStream();
            //Insère les identifiants dans le Stream
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            //Flush ou close pour nettoyer le BufferedWriter et éviter qu'il n'occupe de la RAM inutilement après utilisation
            writer.flush();
            writer.close();
            os.close();
            //Vérifie que la connection a abouti avec succès
            //Correspond à vérifier qu'un pdo renvoie true
            int responseCode=conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                //Si la connexion s'est bien passée, lit la réponse inscrite sur l'inputStream instancié
                //et la retourn sous forme de string
                String line;
                BufferedReader br=new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                //Valeur de retour si connexion échouée
                response="";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    //construit la chaine à envoyer au service connexion
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            //Vérifie quje params n'est pas vide
            if (first) first = false;
            //ici donne "&id=//pid//&password=//ppasse//"
            else result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    public void setOnDownloadCompleteListener (OnDownloadCompleteListener listener)
    {
        //Ajoute le listener à la collection de listeners de l'application
        this.listeners.add(listener);

    }


}
