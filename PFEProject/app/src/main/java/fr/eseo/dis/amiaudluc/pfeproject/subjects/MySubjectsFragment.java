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
 * Created by lucasamiaud on 22/12/2017.
 */

public class MySubjectsFragment extends android.support.v4.app.Fragment implements ItemInterface{

    private Context ctx;
    private SubjectsAdapter subjectsAdapter;

    private boolean loaded = false;
    private String TAG = MySubjectsFragment.class.getSimpleName();
    private View mySubjectsView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mySubjectsView = inflater.inflate(R.layout.layout_subjects, container, false);
        ctx = mySubjectsView.getContext();

        RecyclerView recycler = (RecyclerView) mySubjectsView.findViewById(R.id.cardList);
        recycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(ctx);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(llm);

        subjectsAdapter = new SubjectsAdapter(ctx,this);
        recycler.setAdapter(subjectsAdapter);

        if(!loaded) {
            GetMyProjects mGetProjTask = new GetMyProjects();
            mGetProjTask.execute();
        }

        loadAllMySubjects();

        return mySubjectsView;
    }

    private void loadAllMySubjects(){
        subjectsAdapter.setMySubjects(Content.myProjects);
        subjectsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        Content.project = Content.myProjects.get(position);
        Intent intent = new Intent(getContext(), SubjectActivity.class);
        startActivity(intent);
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetMyProjects extends android.os.AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            //TODO process dialog
            /*pDialog = new AlertDialog.Builder(ctx)
                    .setTitle(R.string.dialog_loading_title)
                    .setMessage(R.string.dialog_loading).show();*/
        }

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
            if(!result.isEmpty() && WebServerExtractor.extractResult(result) == 1) {
                Content.myProjects = WebServerExtractor.extractProjects(result);
                Content.projects = Content.myProjects;
            }else{
                //TODO Print an error message
            }
            loaded = true;
            subjectsAdapter.notifyDataSetChanged();
        }

    }
}
