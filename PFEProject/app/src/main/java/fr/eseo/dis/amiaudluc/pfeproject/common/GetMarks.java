package fr.eseo.dis.amiaudluc.pfeproject.common;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;

import fr.eseo.dis.amiaudluc.pfeproject.Content.Content;
import fr.eseo.dis.amiaudluc.pfeproject.R;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.StudentMark;
import fr.eseo.dis.amiaudluc.pfeproject.decoder.WebServerExtractor;
import fr.eseo.dis.amiaudluc.pfeproject.marks.MarksActivity;
import fr.eseo.dis.amiaudluc.pfeproject.network.HttpsHandler;

/**
 * Created by lucasamiaud on 16/01/2018.
 */

public class GetMarks extends android.os.AsyncTask<StudentMark, Void, ArrayList<StudentMark>> {

    private AlertDialog pDialog,noNetworkDialog;

    private Context ctx;

    public GetMarks(Context ctx){
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
    protected ArrayList<StudentMark> doInBackground(StudentMark... inputStreams) {
        HttpsHandler sh = new HttpsHandler();
        String args = "&user="+ Content.currentUser.getLogin()
                +"&proj="+Content.project.getIdProject()
                +"&token="+Content.currentUser.getToken();

        ArrayList<StudentMark> listMarks = WebServerExtractor.extractMarks(sh.makeServiceCall("NOTES", args,ctx));

        return listMarks;
    }

    @Override
    protected void onPostExecute(ArrayList<StudentMark> listMarks) {
        if(!listMarks.isEmpty()) {
            Content.marks = listMarks;
            Intent intent = new Intent(ctx, MarksActivity.class);
            ctx.startActivity(intent);
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
        pDialog.hide();
    }
}
