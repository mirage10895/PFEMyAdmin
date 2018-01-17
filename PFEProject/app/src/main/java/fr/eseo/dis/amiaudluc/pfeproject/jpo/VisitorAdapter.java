package fr.eseo.dis.amiaudluc.pfeproject.jpo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.amiaudluc.pfeproject.Content.Content;
import fr.eseo.dis.amiaudluc.pfeproject.R;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.Project;

/**
 * Created by lucasamiaud on 16/01/2018.
 */

public class VisitorAdapter extends RecyclerView.Adapter<VisitorAdapter.VisitorViewHolder>{

    private List<Project> projects = Content.projects;
    private List<Integer> positionsExpanded;
    private Context ctx;
    private Spinner spinnerView;
    private ArrayAdapter<Integer> adapter;

    public VisitorAdapter(Context ctx) {
        this.ctx = ctx;
        setMySubjects(Content.projects);
        positionsExpanded = new ArrayList<>();
    }

    public void setMySubjects(List<Project> projects){
        this.projects = projects;
    }

    @Override
    public VisitorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mySubjectView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_visitor, parent, false);

        spinnerView = (Spinner) mySubjectView.findViewById(R.id.spinnerVisitor);
        adapter = new ArrayAdapter<Integer>(ctx,R.layout.support_simple_spinner_dropdown_item);
        for(int i = 1;i<11;i++){
            adapter.add(i);
        }
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerView.setAdapter(adapter);

        return new VisitorViewHolder(mySubjectView);
    }

    @Override
    public void onBindViewHolder(VisitorViewHolder holder, int position) {
        if(getItemCount() != 0){
            final Project project = projects.get(position);
            if(project.isPoster()){
                Bitmap bmp = project.getBmpPoster();
                holder.fLayout.setMinimumHeight(bmp.getHeight());
                holder.fLayout.setImageDrawable(new BitmapDrawable(this.ctx.getResources(),bmp));
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

    class VisitorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final View view;
        private ImageView fLayout;
        private TextView title;
        private TextView description;
        private Spinner spinner;
        private Button button;

        public VisitorViewHolder(View view) {
            super(view);
            this.view = view;
            fLayout = (ImageView) view.findViewById(R.id.frameVisitor);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.descrip);
            spinner = (Spinner) view.findViewById(R.id.spinnerVisitor);
            button = (Button) view.findViewById(R.id.add_note_button);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
