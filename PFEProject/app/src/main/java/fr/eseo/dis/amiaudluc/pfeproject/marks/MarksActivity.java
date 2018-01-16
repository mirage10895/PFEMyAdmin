package fr.eseo.dis.amiaudluc.pfeproject.marks;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import fr.eseo.dis.amiaudluc.pfeproject.Content.Content;
import fr.eseo.dis.amiaudluc.pfeproject.R;
import fr.eseo.dis.amiaudluc.pfeproject.common.GetMarks;
import fr.eseo.dis.amiaudluc.pfeproject.decoder.WebServerExtractor;
import fr.eseo.dis.amiaudluc.pfeproject.network.HttpsHandler;

public class MarksActivity extends AppCompatActivity {

    private Context ctx = this;

    Spinner spinnerView;
    AutoCompleteTextView mNewNoteView;
    ArrayAdapter<String> adapter;
    NewNoteTask mNotetask;
    AlertDialog.Builder pDialog;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_marks);

        pDialog = new AlertDialog.Builder(ctx);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

            actionBar.setTitle(getString(R.string.emptyField));
            if (Content.project.getTitle() != null) {
                actionBar.setTitle("Student marks for "+Content.project.getTitle());
            }
        }

        spinnerView = (Spinner) findViewById(R.id.spinner);
        adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item);
        for(int i = 0;i<Content.marks.size();i++){
            adapter.add(Content.marks.get(i).getStudent().getFullName());
        }
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerView.setAdapter(adapter);

        mNewNoteView = (AutoCompleteTextView) findViewById(R.id.newNoteTxt);

        Button mEmailSignInButton = (Button) findViewById(R.id.add_note_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptNote();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.marks);
        recyclerView.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL,
                false));
        recyclerView.setAdapter(new MarksAdapter(ctx, Content.marks));
    }

    public void attemptNote(){
        int id = -1;
        for(int j = 0;j<Content.marks.size();j++) {
            if (spinnerView.getSelectedItem().toString().equals(Content.marks.get(j).getStudent().getFullName())) {
                id = Content.marks.get(j).getStudent().getUserId();
            }
        }
        if(id != -1) {
            mNotetask = new NewNoteTask(mNewNoteView.getText().toString(), id);
            mNotetask.execute();
        }else{
            mNotetask = null;
            mNewNoteView.setError("Invalid data");
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

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class NewNoteTask extends android.os.AsyncTask<String, Void, String> {

        private final String note;
        private final int id;

        NewNoteTask(String note,int id) {
            this.note = note;
            this.id = id;
        }

        @Override
        protected String doInBackground(String ... urls) {

            HttpsHandler sh = new HttpsHandler();
            String args = "&user="+Content.currentUser.getLogin()
                    +"&proj="+Content.project.getIdProject()
                    +"&student="+id
                    +"&note="+note
                    +"&token="+Content.currentUser.getToken();

            // Making a request to url and getting response
            return sh.makeServiceCall("NEWNT",args,getApplicationContext());
        }

        @Override
        protected void onPostExecute(final String result) {
            //When no results are returned due to a HttpsHandler exception
            if(result == null){
                mNotetask = null;
                pDialog.setTitle(R.string.dialog_no_network)
                        .setCancelable(false)
                        .setNegativeButton("Dismiss", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                pDialog.show().hide();
                            }
                        })
                        .setMessage(R.string.dialog_try_again).show();
            }else {
                mNotetask = null;
                if (WebServerExtractor.extractResult(result) == 1) {
                    GetMarks mGetMarksReloadTask = new GetMarks(ctx);
                    mGetMarksReloadTask.execute();
                    Toast toast = Toast.makeText(ctx, "The mark was added successfuly", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    mNewNoteView.setError("Invalid data");
                }
            }
        }

        @Override
        protected void onCancelled() {
            mNotetask = null;
        }
    }
}
