package fr.eseo.dis.amiaudluc.pfeproject.subjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

import fr.eseo.dis.amiaudluc.pfeproject.Content.Content;
import fr.eseo.dis.amiaudluc.pfeproject.R;
import fr.eseo.dis.amiaudluc.pfeproject.decoder.WebServerExtractor;
import fr.eseo.dis.amiaudluc.pfeproject.network.HttpHandler;

public class SubjectActivity extends AppCompatActivity {

    private Context ctx = this;

    private ImageView imageView;

    private AlertDialog pDialog,noNetworkDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_subjects);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

            actionBar.setTitle(getString(R.string.emptyField));
            if (Content.project.getTitle() != null) {
                actionBar.setTitle(Content.project.getTitle());
            }
        }

        if(Content.project.isPoster()){
            imageView = (ImageView) findViewById(R.id.header);
            imageView.setVisibility(View.VISIBLE);
            GetPoster mGetPostTask = new GetPoster();
            mGetPostTask.execute();
        }

        TextView txtTitle = (TextView) findViewById(R.id.title);
        txtTitle.setText(getString(R.string.emptyField));
        if (Content.project != null) {
            txtTitle.setText(Content.project.getTitle());
        }

        TextView description = (TextView) findViewById(R.id.descrip);
        description.setText(getString(R.string.emptyField));
        if (Content.project.getDescription() != null) {
            description.setText(Content.project.getDescription());
        }

        TextView txtAuthor = (TextView) findViewById(R.id.supervisor);
        txtAuthor.setText(getString(R.string.emptyField));
        if (Content.project.getSupervisor() != null) {
            String allName = Content.project.getSupervisor().getForename()
                    + " " +Content.project.getSupervisor().getSurname();
            txtAuthor.setText(allName);
        }

        TextView confid = (TextView) findViewById(R.id.confid);
        confid.setText(getString(R.string.emptyField));
        if (Content.project.getConfidentiality() == -1) {
            confid.setText((String.valueOf(Content.project.getConfidentiality())));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class GetPoster extends android.os.AsyncTask<InputStream, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            pDialog = new AlertDialog.Builder(ctx)
                    .setTitle(R.string.dialog_loading_title)
                    .setCancelable(false)
                    .setMessage(R.string.dialog_loading).show();
        }

        @Override
        protected Bitmap doInBackground(InputStream... inputStreams) {
            HttpHandler sh = new HttpHandler();
            String args = "&user="+ Content.currentUser.getLogin()
                    +"&projectid="+Content.project.getIdProject()
                    +"&style=full"
                    +"&token="+Content.currentUser.getToken();

            Bitmap bmp = WebServerExtractor.Poster(sh.makeServiceCallStream("POSTR", args,ctx));

            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap bmp) {
            imageView.setImageBitmap(bmp);
            pDialog.hide();
        }
    }

}
