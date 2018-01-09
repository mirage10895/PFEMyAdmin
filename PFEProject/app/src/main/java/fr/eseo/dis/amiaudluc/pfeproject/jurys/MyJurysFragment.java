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

import fr.eseo.dis.amiaudluc.pfeproject.Content.Content;
import fr.eseo.dis.amiaudluc.pfeproject.R;
import fr.eseo.dis.amiaudluc.pfeproject.common.ItemInterface;
import fr.eseo.dis.amiaudluc.pfeproject.decoder.CacheFileGenerator;
import fr.eseo.dis.amiaudluc.pfeproject.decoder.WebServerExtractor;
import fr.eseo.dis.amiaudluc.pfeproject.network.HttpHandler;

/**
 * Created by lucasamiaud on 09/01/2018.
 */

public class MyJurysFragment extends Fragment implements ItemInterface {
    private Context ctx;
    private JurysAdapter jurysAdapter;
    private boolean loaded = false;
    private Fragment frag = this;

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
            MyJurysFragment.GetMyJurys mGetJuryTask = new MyJurysFragment.GetMyJurys();
            mGetJuryTask.execute();
        }

        loadAllJurys();

        return allJurysView;
    }

    private boolean loadCache() {
        String data = CacheFileGenerator.getInstance().read(ctx, CacheFileGenerator.MYJUR);
        if (!data.isEmpty()) {
            Content.myJurys = WebServerExtractor.extractJurys(data);
            Content.jurys = Content.myJurys;
            loaded = true;
            return true;
        } else {
            return false;
        }
    }

    private void loadAllJurys() {
        jurysAdapter.setJurys(Content.myJurys);
        jurysAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        Content.jury = Content.myJurys.get(position);
        Intent intent = new Intent(getContext(), JuryActivity.class);
        startActivity(intent);
    }

    private class GetMyJurys extends android.os.AsyncTask<String, Void, String> {
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
            String jsonStr = sh.makeServiceCall("MYJUR", args,ctx);

            return jsonStr;
        }

        @Override
        protected void onPostExecute(String result) {
            if(!result.isEmpty() && WebServerExtractor.extractResult(result) == 1) {
                Content.myJurys = WebServerExtractor.extractJurys(result);
                CacheFileGenerator.getInstance().write(ctx,CacheFileGenerator.MYJUR,result);
                Content.jurys = Content.myJurys;
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
}
