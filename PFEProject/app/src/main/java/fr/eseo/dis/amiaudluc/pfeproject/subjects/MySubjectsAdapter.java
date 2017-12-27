package fr.eseo.dis.amiaudluc.pfeproject.subjects;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.amiaudluc.pfeproject.Content.Content;
import fr.eseo.dis.amiaudluc.pfeproject.R;
import fr.eseo.dis.amiaudluc.pfeproject.model.Project;

/**
 * Created by lucasamiaud on 27/12/2017.
 */

public class MySubjectsAdapter extends RecyclerView.Adapter<MySubjectsAdapter.MySubjectsViewHolder> {

    private MySubjectsFragment fragment;
    private List<Project> projects;
    private List<Integer> positionsExpanded;

    public MySubjectsAdapter(Context ctx, MySubjectsFragment mySubjectsFragment) {
        this.fragment = mySubjectsFragment;
        setMySubjects(Content.projects);
        positionsExpanded = new ArrayList<>();
    }

    public void setMySubjects(List<Project> projects){
        this.projects = projects;
    }

    @Override
    public MySubjectsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mySubjectView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        Log.d("SubjectAdapter","onCreateViewHolder()");
        return new MySubjectsViewHolder(mySubjectView);
    }

    @Override
    public void onBindViewHolder(MySubjectsViewHolder holder, int position) {
        holder.projectDescription.setText(Content.projects.get(0).getDescription());
        holder.projectTitle.setText(Content.projects.get(0).getTitle());
        holder.projectSupervisor.setText(Content.projects.get(0).getSupervisor().getForename());
        /*holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("FilmographyAdapter","Item 'clicked'");
                activity.clickItem(film);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    class MySubjectsViewHolder extends RecyclerView.ViewHolder {

        private final View view;

        private final TextView projectTitle;
        private final TextView projectDescription;
        private final TextView projectSupervisor;

        public MySubjectsViewHolder(View view) {
            super(view);
            Log.d("MySubjectsViewHolder","MySubjectsViewHolder()");
            this.view = view;
            projectTitle = (TextView) view.findViewById(R.id.title);
            projectDescription = (TextView) view.findViewById(R.id.descrip);
            projectSupervisor = (TextView) view.findViewById(R.id.forename);
        }
    }
}
