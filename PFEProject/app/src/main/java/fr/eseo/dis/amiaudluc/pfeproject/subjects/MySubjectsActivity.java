package fr.eseo.dis.amiaudluc.pfeproject.subjects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import fr.eseo.dis.amiaudluc.pfeproject.Content.Content;
import fr.eseo.dis.amiaudluc.pfeproject.R;

public class MySubjectsActivity extends AppCompatActivity {

    public static int NEW_CARD_COUNTER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_subjects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NEW_CARD_COUNTER = 0;

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_my_subjects);

        for(int i = 0;i<Content.projects.size();i++) {

            TextView txtTitle = (TextView) findViewById(R.id.title);
            txtTitle.setText(getString(R.string.emptyField));
            if (Content.projects.get(i) != null) {
                txtTitle.setText(Content.projects.get(i).getTitle());
            }

            TextView txtAuthor = (TextView) findViewById(R.id.forename);
            txtAuthor.setText(getString(R.string.emptyField));
            if (Content.projects.get(i).getSupervisor() != null) {
                txtAuthor.setText(Content.projects.get(i).getSupervisor().getForename());
            }

            NEW_CARD_COUNTER++;

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
