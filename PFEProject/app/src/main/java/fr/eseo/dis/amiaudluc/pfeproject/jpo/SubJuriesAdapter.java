package fr.eseo.dis.amiaudluc.pfeproject.jpo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.amiaudluc.pfeproject.Content.Content;
import fr.eseo.dis.amiaudluc.pfeproject.R;
import fr.eseo.dis.amiaudluc.pfeproject.common.ItemInterface;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.Project;

/**
 * Created by lucasamiaud on 14/01/2018.
 */

public class SubJuriesAdapter extends RecyclerView.Adapter<SubJuriesAdapter.SubJuriesViewHolder>{

    private ItemInterface fragment;
    private List<Project> projects = new ArrayList<Project>();
    private List<Integer> positionsExpanded;
    private Context ctx;

    public SubJuriesAdapter(Context ctx, ItemInterface fragment) {
        this.ctx = ctx;
        this.fragment = fragment;
        setProjects(Content.porteProjects);
        positionsExpanded = new ArrayList<>();
    }

    public void setProjects(List<Project> projects){
        this.projects = projects;
    }

    @Override
    public SubJuriesAdapter.SubJuriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View jurysView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_porte, parent, false);
        return new SubJuriesAdapter.SubJuriesViewHolder(jurysView);
    }

    @Override
    public void onBindViewHolder(SubJuriesViewHolder holder, int position) {
        if(getItemCount() != 0){
            Project project = projects.get(position);

            if(project.isPoster()){
                Bitmap bmp = project.getBmpPoster();
                holder.fLayout.setMinimumHeight(bmp.getHeight());
                holder.fLayout.setBackground(new BitmapDrawable(this.ctx.getResources(),bmp));
            }

            holder.title.setText(ctx.getString(R.string.emptyField));
            if(project.getTitle() != null){
                holder.title.setText(project.getTitle());
            }

            holder.description.setText(ctx.getString(R.string.emptyField));
            if(project.getDescription() != null){
                holder.description.setText(project.getDescription());
            }

        }else{
           holder.title.setText("You have no jury!");
        }
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    class SubJuriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final View view;
        private FrameLayout fLayout;
        private TextView title;
        private TextView description;

        public SubJuriesViewHolder(View view) {
            super(view);
            this.view = view;
            fLayout = (FrameLayout) view.findViewById(R.id.framePorte);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.descrip);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            fragment.onItemClick(getAdapterPosition());
        }
    }
}
