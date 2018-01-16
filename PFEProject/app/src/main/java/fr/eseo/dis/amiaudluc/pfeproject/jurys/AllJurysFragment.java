package fr.eseo.dis.amiaudluc.pfeproject.jurys;

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
import android.widget.Toast;

import fr.eseo.dis.amiaudluc.pfeproject.Content.Content;
import fr.eseo.dis.amiaudluc.pfeproject.R;
import fr.eseo.dis.amiaudluc.pfeproject.common.ItemInterface;
import fr.eseo.dis.amiaudluc.pfeproject.decoder.CacheFileGenerator;
import fr.eseo.dis.amiaudluc.pfeproject.decoder.WebServerExtractor;
import fr.eseo.dis.amiaudluc.pfeproject.network.HttpsHandler;

/**
 * Created by lucasamiaud on 09/01/2018.
 */

public class AllJurysFragment extends Fragment implements ItemInterface {

    private Context ctx;
    private JurysAdapter jurysAdapter;
    private boolean loaded = false;
    private Fragment frag = this;

    String TAG = this.getClass().getSimpleName();
    private AlertDialog pDialog, noNetworkDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View allJurysView = inflater.inflate(R.layout.layout_main, container, false);
        ctx = allJurysView.getContext();

        RecyclerView recycler = (RecyclerView) allJurysView.findViewById(R.id.cardList);
        recycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(ctx);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(llm);

        jurysAdapter = new JurysAdapter(ctx, this);
        recycler.setAdapter(jurysAdapter);

        if (!loaded && !loadCache()) {
            AllJurysFragment.GetAllJurys mGetJuryTask = new AllJurysFragment.GetAllJurys();
            mGetJuryTask.execute();
        }

        loadAllSubjects();

        return allJurysView;
    }

    private boolean loadCache() {
        String data = CacheFileGenerator.getInstance().read(ctx, CacheFileGenerator.LIJUR);
        if (!data.isEmpty()) {
            Content.allJurys = WebServerExtractor.extractJurys(data);
            Content.jurys = Content.allJurys;
            loaded = true;
            return true;
        } else {
            return false;
        }
    }

    private void loadAllSubjects() {
        jurysAdapter.setJurys(Content.allJurys);
        jurysAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        if(Content.currentUser.getListeIdJurys().contains(Content.allJurys.get(position).getIdJury())) {
            Content.jury = Content.allJurys.get(position);
            GetFullJury mGetJurTask = new GetFullJury();
            mGetJurTask.execute();
        }else{
            Toast toast = Toast.makeText(ctx, "Access denied", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private class GetAllJurys extends android.os.AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            pDialog = new AlertDialog.Builder(ctx)
                    .setTitle(R.string.dialog_loading_title)
                    .setCancelable(false)
                    .setMessage(R.string.dialog_loading).show();
        }

        @Override
        protected String doInBackground(String... urls) {
            HttpsHandler sh = new HttpsHandler();
            String args = "&user="+ Content.currentUser.getLogin()+"&token="+Content.currentUser.getToken();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall("LIJUR", args,ctx);

            return jsonStr;
        }

        @Override
        protected void onPostExecute(String result) {
            if(!result.isEmpty() && WebServerExtractor.extractResult(result) == 1) {
                Content.allJurys = WebServerExtractor.extractJurys(result);
                CacheFileGenerator.getInstance().write(ctx,CacheFileGenerator.LIJUR,result);
                Content.jurys = Content.allJurys;
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
            reLoadFragment(frag);
        }

        public void reLoadFragment(Fragment fragment) {
            // Reload current fragment;
            fragment.onDetach();
            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(fragment);
            ft.attach(fragment);
            ft.commit();
            pDialog.hide();
        }
    }

    private class GetFullJury extends android.os.AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            pDialog = new AlertDialog.Builder(ctx)
                    .setTitle(R.string.dialog_loading_title)
                    .setCancelable(false)
                    .setMessage(R.string.dialog_loading).show();
        }

        @Override
        protected String doInBackground(String... urls) {
            HttpsHandler sh = new HttpsHandler();
            String args = "&user="+ Content.currentUser.getLogin()
                    +"&jury="+Content.jury.getIdJury()
                    +"&token="+Content.currentUser.getToken();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall("JYINF", args,ctx);

            return jsonStr;
        }

        @Override
        protected void onPostExecute(String result) {
            if(!result.isEmpty() && WebServerExtractor.extractResult(result) == 1) {
                Content.projects = WebServerExtractor.extractProjects(result);
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

            Intent intent = new Intent(getContext(), JuryActivity.class);
            startActivity(intent);
            pDialog.hide();
        }
    }

}
