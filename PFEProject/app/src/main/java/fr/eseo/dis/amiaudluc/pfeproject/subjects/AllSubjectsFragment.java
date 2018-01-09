package fr.eseo.dis.amiaudluc.pfeproject.subjects;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.eseo.dis.amiaudluc.pfeproject.Content.Content;
import fr.eseo.dis.amiaudluc.pfeproject.R;
import fr.eseo.dis.amiaudluc.pfeproject.common.ItemInterface;
import fr.eseo.dis.amiaudluc.pfeproject.decoder.CacheFileGenerator;
import fr.eseo.dis.amiaudluc.pfeproject.decoder.WebServerExtractor;
import fr.eseo.dis.amiaudluc.pfeproject.network.HttpHandler;

/**
 * Created by lucasamiaud on 28/12/2017.
 */

public class AllSubjectsFragment extends android.support.v4.app.Fragment implements ItemInterface{


    private Context ctx;
    private SubjectsAdapter subjectsAdapter;
    private boolean loaded = false;
    private Fragment frag = this;

    private AlertDialog pDialog,noNetworkDialog;

    private String TAG = MySubjectsFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View allSubjectsView = inflater.inflate(R.layout.layout_main, container, false);
        ctx = allSubjectsView.getContext();

        RecyclerView recycler = (RecyclerView) allSubjectsView.findViewById(R.id.cardList);
        recycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(ctx);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(llm);

        subjectsAdapter = new SubjectsAdapter(ctx,this);
        recycler.setAdapter(subjectsAdapter);

        if(!loaded && !loadCache()) {
            GetAllProjects mGetProjTask = new GetAllProjects();
            mGetProjTask.execute();
        }

        loadAllSubjects();

        return allSubjectsView;
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
        protected void onPreExecute() {
            pDialog = new AlertDialog.Builder(ctx)
                    .setTitle(R.string.dialog_loading_title)
                    .setCancelable(false)
                    .setMessage(R.string.dialog_loading).show();
        }

        @Override
        protected String doInBackground(String... urls) {
            HttpHandler sh = new HttpHandler();
            String args = "&user="+ Content.currentUser.getLogin()+"&token="+Content.currentUser.getToken();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall("LIPRJ", args,ctx);

            return jsonStr;
        }

        @Override
        protected void onPostExecute(String result) {
            if(!result.isEmpty() && WebServerExtractor.extractResult(result) == 1) {
                Content.allProjects = WebServerExtractor.extractProjects(result);
                CacheFileGenerator.getInstance().write(ctx,CacheFileGenerator.LIPRJ,result);
                Content.projects = Content.allProjects;
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
            subjectsAdapter.notifyDataSetChanged();
            reLoadFragment(frag);
        }

    }
    public void reLoadFragment(Fragment fragment)
    {
        // Reload current fragment;
        fragment.onDetach();
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(fragment);
        ft.attach(fragment);
        ft.commit();
        pDialog.hide();
    }
}
