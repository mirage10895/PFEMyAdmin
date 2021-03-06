package fr.eseo.dis.amiaudluc.pfeproject.subjects;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.amiaudluc.pfeproject.Content.Content;
import fr.eseo.dis.amiaudluc.pfeproject.R;
import fr.eseo.dis.amiaudluc.pfeproject.common.ItemInterface;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.Project;

/**
 * Created by lucasamiaud on 27/12/2017.
 */

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.MySubjectsViewHolder> {

    private ItemInterface fragment;
    private List<Project> projects = Content.projects;
    private List<Integer> positionsExpanded;
    private Context ctx;

    public SubjectsAdapter(Context ctx, ItemInterface fragment) {
        this.ctx = ctx;
        this.fragment = fragment;
        setMySubjects(Content.projects);
        positionsExpanded = new ArrayList<>();
    }

    public void setMySubjects(List<Project> projects){
        this.projects = projects;
    }

    @Override
    public MySubjectsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mySubjectView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_project, parent, false);
        return new MySubjectsViewHolder(mySubjectView);
    }

    @Override
    public void onBindViewHolder(MySubjectsViewHolder holder, int position) {
        if(getItemCount() != 0){
            Project project = projects.get(position);
            holder.projectDescription.setText(ctx.getString(R.string.emptyField));
            if (project.getDescription() != null) {
                holder.projectDescription.setText(project.getDescription());
            }

            holder.projectTitle.setText(ctx.getString(R.string.emptyField));
            if(project.getTitle() != null){
                holder.projectTitle.setText(project.getTitle());
            }

            holder.projectSupervisorName.setText(ctx.getString(R.string.emptyField));
            if(project.getSupervisor() != null) {
                String allName = project.getSupervisor().getFullName();
                holder.projectSupervisorName.setText(allName);
            }
        }else{
            holder.projectDescription.setText("No subjects !");
        }

    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    class MySubjectsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final View view;

        private final TextView projectTitle;
        private final TextView projectDescription;
        private final TextView projectSupervisorName;

        public MySubjectsViewHolder(View view) {
            super(view);
            this.view = view;
            projectTitle = (TextView) view.findViewById(R.id.title);
            projectDescription = (TextView) view.findViewById(R.id.descrip);
            projectSupervisorName = (TextView) view.findViewById(R.id.name);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            fragment.onItemClick(getAdapterPosition());
        }
    }
}
