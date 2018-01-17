package fr.eseo.dis.amiaudluc.pfeproject.poster;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import fr.eseo.dis.amiaudluc.pfeproject.Content.Content;
import fr.eseo.dis.amiaudluc.pfeproject.R;
import fr.eseo.dis.amiaudluc.pfeproject.data.DAO.DBInitializer.AppDatabase;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.ProjectComment;

public class PosterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_poster);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Poster of "+ Content.project.getTitle());
        }

        ImageView imageView = (ImageView) findViewById(R.id.posterHeader);
        Drawable bmpD = new BitmapDrawable(getResources(),Content.poster);
        imageView.setImageDrawable(bmpD);

        final int idProject = Content.project.getIdProject();

        // Code for the button which send the comment to the Database
        Button sendComment = (Button) this.findViewById(R.id.comments_button);
        final EditText posterComment = (EditText) this.findViewById(R.id.poster_comment);

        // Recuperate the content of the database for the current project
        if (AppDatabase.getAppDatabase(this).posterCommentDao().numberOfCommentByID(idProject) != 0) {
            ProjectComment prjCom = AppDatabase.getAppDatabase(this).posterCommentDao().getCommentByID(idProject);
            String reuperateComment = prjCom.getPosterComments();
            posterComment.setText(reuperateComment);
        }

        sendComment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Save in the database
                insertOrUpdateDataBase(idProject,posterComment.getText().toString());
            }});


    }

    private void insertOrUpdateDataBase(int idPrj, String comment){

        if (AppDatabase.getAppDatabase(this).posterCommentDao().numberOfCommentByID(idPrj) == 0){
            ProjectComment prjCom = new ProjectComment(idPrj,comment);
            AppDatabase.getAppDatabase(this).posterCommentDao().insertComment(prjCom);
            Toast toast = Toast.makeText(this, "Comment Added", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            AppDatabase.getAppDatabase(this).posterCommentDao().updatePosterComments(comment,idPrj);
            Toast toast = Toast.makeText(this, "Comment Changed", Toast.LENGTH_SHORT);
            toast.show();
        }
        int nbComments = AppDatabase.getAppDatabase(this).posterCommentDao().getAll().size();
        Log.e("DATABASE TEST", "NB COMMENTS : " + nbComments);
    }

    public void onClick(View view) {
    }
}
