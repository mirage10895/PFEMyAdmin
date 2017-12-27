package fr.eseo.dis.amiaudluc.pfeproject.subjects;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.eseo.dis.amiaudluc.pfeproject.Content.Content;
import fr.eseo.dis.amiaudluc.pfeproject.R;
import fr.eseo.dis.amiaudluc.pfeproject.decoder.WebServerExtractor;
import fr.eseo.dis.amiaudluc.pfeproject.network.HttpHandler;

/**
 * Created by lucasamiaud on 22/12/2017.
 */

public class MySubjectsFragment extends android.support.v4.app.Fragment{

    private Context ctx;
    private GetProjects mGetProjTask = new GetProjects();

    private String TAG = MySubjectsFragment.class.getSimpleName();

    private ProgressDialog pDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View mySubjectsView = inflater.inflate(R.layout.fragment_view_subjects, container, false);
        ctx = mySubjectsView.getContext();
        mGetProjTask.execute();

        return mySubjectsView;
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetProjects extends android.os.AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            HttpHandler sh = new HttpHandler();
            String args = "&user="+ Content.user.getLogin()+"&token="+Content.user.getToken();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall("MYPRJ", args,ctx);

            Log.e(TAG, "Response from url: " + jsonStr);

            return jsonStr;
        }

        @Override
        protected void onPostExecute(String result) {
            Content.projects = WebServerExtractor.extractProjects(result);
        }

    }
}
