package fr.eseo.dis.amiaudluc.pfeproject.subjects;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import fr.eseo.dis.amiaudluc.pfeproject.MainActivity;
import fr.eseo.dis.amiaudluc.pfeproject.R;
import fr.eseo.dis.amiaudluc.pfeproject.network.HttpHandler;

public class MySubjectsActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    ArrayList<HashMap<String, String>> projectList;

    private String url;

    private ProgressDialog pDialog;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_subjects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_my_subjects);

        // URL to get contacts JSON
        //url = "https://192.168.4.10/www/pfe/webservice.php?q=MYPRJ&user="+user+"&token="+token;

        projectList = new ArrayList<>();

        lv = findViewById(R.id.list);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetProjects extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MySubjectsActivity.this);
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
                    MySubjectsActivity.this, projectList,
                    R.layout.list_item, new String[]{"titre", "descrip",
                    "supervisorForename"}, new int[]{R.id.titre,
                    R.id.descrip, R.id.forename});

            lv.setAdapter(adapter);
        }

    }

}
