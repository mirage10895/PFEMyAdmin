package fr.eseo.dis.amiaudluc.pfeproject.jurys;

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
import fr.eseo.dis.amiaudluc.pfeproject.data.model.Jury;

/**
 * Created by lucasamiaud on 09/01/2018.
 */

public class JurysAdapter extends RecyclerView.Adapter<JurysAdapter.JurysViewHolder>{

    private ItemInterface fragment;
    private List<Jury> jurys = Content.jurys;
    private List<Integer> positionsExpanded;
    private Context ctx;

    public JurysAdapter(Context ctx, ItemInterface fragment) {
        this.ctx = ctx;
        this.fragment = fragment;
        setJurys(Content.jurys);
        positionsExpanded = new ArrayList<>();
    }

    public void setJurys(List<Jury> jurys){
        this.jurys = jurys;
    }

    @Override
    public JurysViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View jurysView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_project, parent, false);
        return new JurysAdapter.JurysViewHolder(jurysView);
    }

    @Override
    public void onBindViewHolder(JurysViewHolder holder, int position) {
        if(getItemCount() != 0){
            Jury jury = Content.jurys.get(position);
            holder.projectDescription.setText(ctx.getString(R.string.emptyField));
            if (jury.getDescription() != null) {
                holder.projectDescription.setText(jury.getDescription());
            }

            holder.projectTitle.setText(ctx.getString(R.string.emptyField));
            if(jury.getIdJury() != -1){
                holder.projectTitle.setText(jury.getIdJury());
            }

        }else{
            holder.projectDescription.setText("Vous n'avez aucun sujet !");
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setMyJurys(ArrayList<Jury> myJurys) {
        this.jurys = myJurys;
    }

    class JurysViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final View view;

        private final TextView projectTitle;
        private final TextView projectDescription;
        private final TextView projectSupervisorName;

        public JurysViewHolder(View view) {
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
