package fr.eseo.dis.amiaudluc.pfeproject.subjects;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import fr.eseo.dis.amiaudluc.pfeproject.Content.Content;
import fr.eseo.dis.amiaudluc.pfeproject.R;
import fr.eseo.dis.amiaudluc.pfeproject.common.TeamAdapter;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.StudentMark;
import fr.eseo.dis.amiaudluc.pfeproject.decoder.WebServerExtractor;
import fr.eseo.dis.amiaudluc.pfeproject.marks.MarksActivity;
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
            imageView.setImageBitmap(Content.poster);
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
        if (Content.project.getConfidentiality() != -1) {
            String confidTS;
            if(Content.project.getConfidentiality() == 0){
                confidTS = "None (0)";
            }else if(Content.project.getConfidentiality() == 1){
                confidTS = "Low (1)";
            }else{
                confidTS = "High (2)";
            }
            confid.setText(confidTS);
        }

        TextView projectId = (TextView) findViewById(R.id.idProject);
        TextView emptyTeam = (TextView) findViewById(R.id.txtEmptyTeam);
        projectId.setText(ctx.getString(R.string.emptyField));
        if(Content.project.getIdProject() != -1) {
            projectId.setText(""+Content.project.getIdProject());
        }

        if(Content.project.getTeam() != null){
            emptyTeam.setVisibility(View.GONE);
        }
        RecyclerView recyclerView = findViewById(R.id.team);
        recyclerView.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL,
                false));
        recyclerView.setAdapter(new TeamAdapter(ctx, Content.project.getTeam()));

    }

    public void onClick(View v){
        if(v.getId() == R.id.expandTeam){
            RelativeLayout carac = (RelativeLayout) findViewById(R.id.txtTeam);
            if(carac.getVisibility() == View.GONE){
                carac.setVisibility(View.VISIBLE);
            }else{
                carac.setVisibility(View.GONE);
            }
        }else if(v.getId() == R.id.expandCo){
            RelativeLayout team = (RelativeLayout) findViewById(R.id.txtCo);
            if(team.getVisibility() == View.GONE){
                team.setVisibility(View.VISIBLE);
            }else{
                team.setVisibility(View.GONE);
            }
        }else if(v.getId() == R.id.expandDescrip){
            RelativeLayout des = (RelativeLayout) findViewById(R.id.txtDescrip);
            if(des.getVisibility() == View.GONE){
                des.setVisibility(View.VISIBLE);
            }else{
                des.setVisibility(View.GONE);
            }
        }else if(v.getId() == R.id.marks_button){
            GetMarks mGetMarksTask = new GetMarks();
            mGetMarksTask.execute();
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

    private class GetMarks extends android.os.AsyncTask<StudentMark, Void, ArrayList<StudentMark>> {

        @Override
        protected void onPreExecute() {
            pDialog = new AlertDialog.Builder(ctx)
                    .setTitle(R.string.dialog_loading_title)
                    .setCancelable(false)
                    .setMessage(R.string.dialog_loading).show();
        }

        @Override
        protected ArrayList<StudentMark> doInBackground(StudentMark... inputStreams) {
            HttpHandler sh = new HttpHandler();
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
            Intent intent = new Intent(ctx, MarksActivity.class);
            startActivity(intent);
            pDialog.hide();
        }
    }

}
