package fr.eseo.dis.amiaudluc.pfeproject.subjects;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import fr.eseo.dis.amiaudluc.pfeproject.Content.Content;
import fr.eseo.dis.amiaudluc.pfeproject.R;

public class SubjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_my_subjects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

            actionBar.setTitle(getString(R.string.emptyField));
            if (Content.project.getTitle() != null) {
                actionBar.setTitle(Content.project.getTitle());
            }
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
            txtAuthor.setText(Content.project.getSupervisor().getForename());
        }

        TextView confid = (TextView) findViewById(R.id.confid);
        confid.setText(getString(R.string.emptyField));
        if (Content.project.getConfidentiality() == 0) {
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

}
