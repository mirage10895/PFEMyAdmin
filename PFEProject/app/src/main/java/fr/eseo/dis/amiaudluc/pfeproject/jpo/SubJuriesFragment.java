package fr.eseo.dis.amiaudluc.pfeproject.jpo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.amiaudluc.pfeproject.Content.Content;
import fr.eseo.dis.amiaudluc.pfeproject.R;
import fr.eseo.dis.amiaudluc.pfeproject.common.ItemInterface;
import fr.eseo.dis.amiaudluc.pfeproject.data.DAO.DBInitializer.AppDatabase;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.Project;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.SubJuryMark;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.User;
import fr.eseo.dis.amiaudluc.pfeproject.decoder.CacheFileGenerator;
import fr.eseo.dis.amiaudluc.pfeproject.decoder.WebServerExtractor;
import fr.eseo.dis.amiaudluc.pfeproject.network.HttpHandler;
import fr.eseo.dis.amiaudluc.pfeproject.subjects.MySubjectsFragment;
import fr.eseo.dis.amiaudluc.pfeproject.subjects.SubjectActivity;
import fr.eseo.dis.amiaudluc.pfeproject.subjects.SubjectsAdapter;


/**
 * Created by Samuel on 10/01/2018.
 */

public class SubJuriesFragment extends android.support.v4.app.Fragment implements ItemInterface {

    private Context ctx;
    private SubjectsAdapter subjectsAdapter;
    private boolean loaded = false;
    private Fragment frag = this;

    private AlertDialog pDialog,noNetworkDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View subJuriesView = inflater.inflate(R.layout.layout_main, container, false);
        ctx = subJuriesView.getContext();

        // Aller chercher les projets du Sub-jury et les stocker dans le Content
        Content.subJuryProjects = (ArrayList<Project>) AppDatabase.getAppDatabase(ctx).projectsDao().getAll();
        Log.d("TEST DATABASE", "OnPreExecute"+Content.subJuryProjects.size());

        // AJOUT D'UN PROJET A LA BDD (pour tester)
        //Project prj = new Project(2,"Test","Ce projet est juste un petit test");
        //AppDatabase.getAppDatabase(ctx).projectsDao().insertProject(prj);


        final fr.eseo.dis.amiaudluc.pfeproject.jpo.SubJuriesFragment.Get5RandomProjects mGetProjTask = new fr.eseo.dis.amiaudluc.pfeproject.jpo.SubJuriesFragment.Get5RandomProjects();

        // Code for the button which call the Web Service "PORTE"
        Button generateSubJury = (Button) subJuriesView.findViewById(R.id.button);
        generateSubJury.setVisibility(View.VISIBLE);
        if (Content.projects.size() != 0) {
            generateSubJury.setText("Generate a new sub-Jury");
        }
        generateSubJury.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mGetProjTask.execute();
            }
        });

        RecyclerView recycler = subJuriesView.findViewById(R.id.cardList);
        recycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(ctx);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(llm);

        subjectsAdapter = new SubjectsAdapter(ctx,this);
        recycler.setAdapter(subjectsAdapter);

        if(!loaded && !loadCache()) {
            mGetProjTask.execute();
        }

        loadTheFiveSubjects();


        // TEST BDD NOTES POUR UN PROJECT
        SubJuryMark note = new SubJuryMark(1,6);

        //AppDatabase.getAppDatabase(ctx).su
        Log.d("Restult DataBase : ", "INSERTION");

        List<User> userRet = AppDatabase.getAppDatabase(ctx).usersDao().getAll();

        Log.d("Restult DataBase : ", "" + userRet.size());


        return subJuriesView;
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

    private void loadTheFiveSubjects(){
        List<Project> bddProjects = AppDatabase.getAppDatabase(ctx).projectsDao().getAll();
        Log.d("TEST DATABASE", ""+bddProjects.size());
        subjectsAdapter.setMySubjects(Content.subJuryProjects);
        Log.d("TEST DATABASE", "Load the five prj"+Content.subJuryProjects.size());
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
    private class Get5RandomProjects extends android.os.AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            pDialog = new AlertDialog.Builder(ctx)
                    .setTitle(R.string.dialog_loading_title)
                    .setCancelable(false)
                    .setMessage(R.string.dialog_loading).show();
        }

        @Override
        protected String doInBackground(String... urls) {
            Log.e("TEST WEB SERVICE", "doInBackground");
            HttpHandler sh = new HttpHandler();
            String args = "&user="+ Content.currentUser.getLogin()
                    + "&seed=" + "360" //On peut changer le "Seed"
                    +"&token="+Content.currentUser.getToken();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall("PORTE", args,ctx);
            Log.d("TEST WEBSERVICE",jsonStr);
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