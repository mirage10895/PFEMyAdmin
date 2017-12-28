package fr.eseo.dis.amiaudluc.pfeproject.subjects;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.eseo.dis.amiaudluc.pfeproject.Content.Content;
import fr.eseo.dis.amiaudluc.pfeproject.R;
import fr.eseo.dis.amiaudluc.pfeproject.common.ItemInterface;
import fr.eseo.dis.amiaudluc.pfeproject.decoder.WebServerExtractor;
import fr.eseo.dis.amiaudluc.pfeproject.network.HttpHandler;

/**
 * Created by lucasamiaud on 28/12/2017.
 */

public class AllSubjectsFragment extends android.support.v4.app.Fragment implements ItemInterface{


    private Context ctx;
    private SubjectsAdapter subjectsAdapter;
    private boolean loaded = false;

    private String TAG = MySubjectsFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View allSubjectsView = inflater.inflate(R.layout.fragment_view_subjects, container, false);
        ctx = allSubjectsView.getContext();

        RecyclerView recycler = (RecyclerView) allSubjectsView.findViewById(R.id.cardList);
        recycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(ctx);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(llm);

        subjectsAdapter = new SubjectsAdapter(ctx,this);
        recycler.setAdapter(subjectsAdapter);

        if(!loaded) {
            GetAllProjects mGetProjTask = new GetAllProjects();
            mGetProjTask.execute();
        }

        loadAllSubjects();

        return allSubjectsView;
    }

    private void loadAllSubjects(){
        subjectsAdapter.setMySubjects(Content.allProjects);
        subjectsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        Content.project = Content.allProjects.get(position);
        Intent intent = new Intent(getContext(), SubjectActivity.class);
        startActivity(intent);
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetAllProjects extends android.os.AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            HttpHandler sh = new HttpHandler();
            String args = "&user="+ Content.user.getLogin()+"&token="+Content.user.getToken();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall("LIPRJ", args,ctx);

            return jsonStr;
        }

        @Override
        protected void onPostExecute(String result) {
            Content.allProjects = WebServerExtractor.extractProjects(result);
            Content.projects = Content.allProjects;
            loaded = true;
            subjectsAdapter.notifyDataSetChanged();
        }

    }
}
