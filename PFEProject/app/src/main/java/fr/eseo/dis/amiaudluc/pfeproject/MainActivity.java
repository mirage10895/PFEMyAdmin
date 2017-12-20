package fr.eseo.dis.amiaudluc.pfeproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;

    ArrayList<HashMap<String, String>> projectList;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        //From the login page
        String user = i.getStringExtra("EXTRA_SESSION_USER");
        String token = i.getStringExtra("EXTRA_SESSION_TOKEN");

        // URL to get contacts JSON
        url = "https://192.168.4.10/www/pfe/webservice.php?q=LIPRJ&user="+user+"&token="+token;

        projectList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

        new GetContacts().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url,getApplicationContext());

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray projects = jsonObj.getJSONArray("projects");
                    String obj = jsonObj.getString("result");
                    Log.e(TAG,"Response (OK/KO): " + obj);

                    // looping through All Contacts
                    for (int i = 0; i < projects.length(); i++) {
                        JSONObject c = projects.getJSONObject(i);

                        String projectId = c.getString("projectId");
                        String titre = c.getString("title");
                        String descrip = c.getString("descrip");

                        JSONObject supervisor = c.getJSONObject("supervisor");
                        String forename = supervisor.getString("forename");
                        String surname = supervisor.getString("surname");


                        // tmp hash map for single contact
                        HashMap<String, String> project = new HashMap<>();

                        // adding each child node to HashMap key => value
                        project.put("projectId", projectId);
                        project.put("titre", titre);
                        project.put("descrip", descrip);
                        project.put("supervisorForename", forename);
                        project.put("supervisorSurname", surname);

                        // adding contact to contact list
                        projectList.add(project);
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, projectList,
                    R.layout.list_item, new String[]{"titre", "descrip",
                    "supervisorForename"}, new int[]{R.id.titre,
                    R.id.descrip, R.id.forename});

            lv.setAdapter(adapter);
        }

    }
}
