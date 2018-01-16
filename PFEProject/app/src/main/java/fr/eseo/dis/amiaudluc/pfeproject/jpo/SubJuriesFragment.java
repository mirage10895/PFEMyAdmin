package fr.eseo.dis.amiaudluc.pfeproject.jpo;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import fr.eseo.dis.amiaudluc.pfeproject.Content.Content;
import fr.eseo.dis.amiaudluc.pfeproject.R;
import fr.eseo.dis.amiaudluc.pfeproject.common.ItemInterface;
import fr.eseo.dis.amiaudluc.pfeproject.data.DAO.DBInitializer.AppDatabase;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.Project;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.SubJuryMark;
import fr.eseo.dis.amiaudluc.pfeproject.decoder.CacheFileGenerator;
import fr.eseo.dis.amiaudluc.pfeproject.decoder.WebServerExtractor;
import fr.eseo.dis.amiaudluc.pfeproject.network.HttpsHandler;


/**
 * Created by Samuel on 10/01/2018.
 */

public class SubJuriesFragment extends android.support.v4.app.Fragment implements ItemInterface {

    private Context ctx;
    private SubJuriesAdapter subJuriesAdapter;
    private boolean loaded = false;
    private Fragment frag = this;

    private final int ITEM_COUNTER = 5;
    private int CURRENT_ITEM = 0;

    private AlertDialog.Builder alertDialog;
    private AlertDialog noNetworkDialog,pDial;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View subJuriesView = inflater.inflate(R.layout.layout_main, container, false);
        ctx = subJuriesView.getContext();

        alertDialog = new AlertDialog.Builder(ctx)
                .setTitle(R.string.dialog_loading_title)
                .setCancelable(false)
                .setPositiveButton("Dismiss", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });
        pDial = alertDialog.create();

        // AJOUT D'UN PROJET A LA BDD (pour tester)
        //Project prj = new Project(50,"Test","Ce projet est juste un petit test");
        //AppDatabase.getAppDatabase(ctx).projectsDao().insertProject(prj);

        // Aller chercher les projets du Sub-jury et les stocker dans le Content
        Content.porteProjects = (ArrayList<Project>) AppDatabase.getAppDatabase(ctx).projectsDao().getAll();

        final Get5RandomProjects mGetProjTask = new SubJuriesFragment.Get5RandomProjects();

        // Code for the button which call the Web Service "PORTE"
        Button generateSubJury = (Button) subJuriesView.findViewById(R.id.button);
        generateSubJury.setVisibility(View.VISIBLE);
        if (Content.porteProjects.size() != 0) {
            generateSubJury.setText("Generate a new sub-Jury");
        } else {
            generateSubJury.setText("Generate a sub-Jury");
        }

        generateSubJury.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Content.porteProjects.clear();
                AppDatabase.getAppDatabase(ctx).projectsDao().deleteAllProjects();
                mGetProjTask.execute();
            }
        });

        RecyclerView recycler = subJuriesView.findViewById(R.id.cardList);
        recycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(ctx);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(llm);

        subJuriesAdapter = new SubJuriesAdapter(ctx,this);
        recycler.setAdapter(subJuriesAdapter);


        return subJuriesView;
    }

    @Override
    public void onItemClick(int position) {

    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class Get5RandomProjects extends android.os.AsyncTask<Project, Void, Project> {

        AlertDialog pDial2;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDial.setMessage("Subject " + (CURRENT_ITEM+1)+"/"+ITEM_COUNTER);
            pDial.show();
            pDial.getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.GONE);
        }

        @Override
        protected Project doInBackground(Project... urls) {
            HttpsHandler sh = new HttpsHandler();
            String args = "&user="+ Content.currentUser.getLogin()
                    +"&token="+Content.currentUser.getToken();

            // Making a request to url and getting response
            String jsonAns = sh.makeServiceCall("PORTE", args,ctx);
            CacheFileGenerator.getInstance().write(ctx,CacheFileGenerator.PORTE,jsonAns);
            Project jsonPorte = WebServerExtractor.extractPorte(jsonAns);
            return jsonPorte;
        }

        @Override
        protected void onPostExecute(Project result) {
            if(result != null) {
                result.setIdProject(CURRENT_ITEM);
                AppDatabase.getAppDatabase(ctx).projectsDao().insertProject(result); // C'est ici qu'il faut que j'ajoute à la bdd
                Content.porteProjects.add(result);
                CURRENT_ITEM++;
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
            if(CURRENT_ITEM < ITEM_COUNTER){
                Get5RandomProjects newtask = new Get5RandomProjects();
                newtask.execute();
            }else {
                loaded = true;
                pDial2.setMessage("Loading complete !");
                subJuriesAdapter.notifyDataSetChanged();
                pDial.setTitle("Loading completed");
                pDial.setMessage("Great !");
                pDial.getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.VISIBLE);
            }
        }

    }

}