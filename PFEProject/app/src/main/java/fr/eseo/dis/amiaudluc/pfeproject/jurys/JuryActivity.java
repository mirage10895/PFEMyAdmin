package fr.eseo.dis.amiaudluc.pfeproject.jurys;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;

import fr.eseo.dis.amiaudluc.pfeproject.Content.Content;
import fr.eseo.dis.amiaudluc.pfeproject.R;
import fr.eseo.dis.amiaudluc.pfeproject.decoder.CacheFileGenerator;
import fr.eseo.dis.amiaudluc.pfeproject.decoder.WebServerExtractor;
import fr.eseo.dis.amiaudluc.pfeproject.network.HttpHandler;

public class JuryActivity extends AppCompatActivity {

    private Context ctx;
    private JurysAdapter jurysAdapter;
    private boolean loaded = false;

    private AlertDialog pDialog, noNetworkDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_jury);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

            actionBar.setTitle(getString(R.string.emptyField));
            if (Content.jury.getProject().getTitle() != null) {
                actionBar.setTitle(Content.jury.getProject().getTitle());
            }
        }
    }

    private boolean loadCache(){
        String data = CacheFileGenerator.getInstance().read(ctx,CacheFileGenerator.LIPRJ);
        if(!data.isEmpty()){
            Content.allProjects = WebServerExtractor.extractProjects(data);
            Content.projects = Content.allProjects;
            loaded = true;
            return true;
        }else{
            return false;
        }
    }

    private void loadAllSubjects(){
        jurysAdapter.setMyJurys(Content.allJurys);
        jurysAdapter.notifyDataSetChanged();
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

    private class GetJury extends android.os.AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            pDialog = new AlertDialog.Builder(ctx)
                    .setTitle(R.string.dialog_loading_title)
                    .setCancelable(false)
                    .setMessage(R.string.dialog_loading).show();
        }

        @Override
        protected String doInBackground(String... urls) {
            HttpHandler sh = new HttpHandler();
            String args = "&user="
                    + Content.currentUser.getLogin()
                    + "&jury="+Content.jury.getIdJury()
                    +"&token="+Content.currentUser.getToken();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall("JYINF", args,ctx);

            return jsonStr;
        }

        @Override
        protected void onPostExecute(String result) {
            if(!result.isEmpty() && WebServerExtractor.extractResult(result) == 1) {
                Content.jury = WebServerExtractor.extractFullJury(result);
                CacheFileGenerator.getInstance().write(ctx,CacheFileGenerator.JYINF,result);
            }else{
                noNetworkDialog = new AlertDialog.Builder(ctx)
                        .setTitle(R.string.dialog_no_network)
                        .setCancelable(false)
                        .setNegativeButton("Dismiss", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                noNetworkDialog.hide();
                            }
                        })
                        .setMessage(R.string.dialog_try_again).show();
            }
            loaded = true;
            jurysAdapter.notifyDataSetChanged();
            reload();
        }

        private void reload() {
            finish();
            startActivity(getIntent());
            pDialog.hide();
        }


    }
}
