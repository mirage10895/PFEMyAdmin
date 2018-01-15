package fr.eseo.dis.amiaudluc.pfeproject.common;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;

import java.io.InputStream;

import fr.eseo.dis.amiaudluc.pfeproject.Content.Content;
import fr.eseo.dis.amiaudluc.pfeproject.R;
import fr.eseo.dis.amiaudluc.pfeproject.decoder.WebServerExtractor;
import fr.eseo.dis.amiaudluc.pfeproject.network.HttpsHandler;
import fr.eseo.dis.amiaudluc.pfeproject.subjects.SubjectActivity;

/**
 * Created by lucasamiaud on 12/01/2018.
 */

public class GetPoster extends android.os.AsyncTask<InputStream, Void, Bitmap> {

    private AlertDialog pDialog, noNetworkDialog;
    private Context ctx;

    public GetPoster(Context ctx){
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        pDialog = new AlertDialog.Builder(ctx)
                .setTitle(R.string.dialog_loading_title)
                .setCancelable(false)
                .setMessage(R.string.dialog_loading).show();
    }

    @Override
    protected Bitmap doInBackground(InputStream... inputStreams) {
        HttpsHandler sh = new HttpsHandler();
        String args = "&user="+ Content.currentUser.getLogin()
                +"&proj="+Content.project.getIdProject()
                +"&style=FULL"
                +"&token="+Content.currentUser.getToken();

        Bitmap bmp = WebServerExtractor.extractPoster(sh.makeServiceCallStream("POSTR", args,ctx));

        return bmp;
    }

    @Override
    protected void onPostExecute(Bitmap bmp) {
        if(bmp != null){
            Content.poster = bmp;
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
        Intent intent = new Intent(ctx, SubjectActivity.class);
        ctx.startActivity(intent);
        pDialog.hide();
    }
}
