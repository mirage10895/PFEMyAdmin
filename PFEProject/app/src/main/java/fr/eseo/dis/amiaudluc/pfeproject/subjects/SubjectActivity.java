package fr.eseo.dis.amiaudluc.pfeproject.subjects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import fr.eseo.dis.amiaudluc.pfeproject.Content.Content;
import fr.eseo.dis.amiaudluc.pfeproject.R;

public class SubjectActivity extends AppCompatActivity {

    public static int NEW_CARD_COUNTER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_subjects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_my_subjects);

        TextView txtTitle = (TextView) findViewById(R.id.title);
        txtTitle.setText(getString(R.string.emptyField));
        if (Content.project != null) {
            txtTitle.setText(Content.project.getTitle());
        }

        TextView txtAuthor = (TextView) findViewById(R.id.forename);
        txtAuthor.setText(getString(R.string.emptyField));
        if (Content.project.getSupervisor() != null) {
            txtAuthor.setText(Content.project.getSupervisor().getForename());
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
