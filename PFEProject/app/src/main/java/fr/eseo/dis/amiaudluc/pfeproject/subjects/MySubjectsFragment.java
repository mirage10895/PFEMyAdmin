package fr.eseo.dis.amiaudluc.pfeproject.subjects;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private GetMyProjects mGetProjTask = new GetMyProjects();
    private MySubjectsAdapter mySubjectsAdapter;

    private String TAG = MySubjectsFragment.class.getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View mySubjectsView = inflater.inflate(R.layout.fragment_view_subjects, container, false);
        ctx = mySubjectsView.getContext();
        mGetProjTask.execute();

        RecyclerView recycler = (RecyclerView) mySubjectsView.findViewById(R.id.cardList);
        recycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(ctx);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(llm);

        mySubjectsAdapter = new MySubjectsAdapter(ctx,this);
        recycler.setAdapter(mySubjectsAdapter);

        loadAllMySubjects();

        return mySubjectsView;
    }

    private void loadAllMySubjects(){
        mySubjectsAdapter.setMySubjects(Content.projects);
        mySubjectsAdapter.notifyDataSetChanged();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetMyProjects extends android.os.AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            HttpHandler sh = new HttpHandler();
            String args = "&user="+ Content.user.getLogin()+"&token="+Content.user.getToken();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall("MYPRJ", args,ctx);

            return jsonStr;
        }

        @Override
        protected void onPostExecute(String result) {
            Content.projects = WebServerExtractor.extractProjects(result);
            mySubjectsAdapter.notifyDataSetChanged();
        }

    }
}
