package fr.eseo.dis.amiaudluc.pfeproject.jurys;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.eseo.dis.amiaudluc.pfeproject.Content.Content;
import fr.eseo.dis.amiaudluc.pfeproject.R;
import fr.eseo.dis.amiaudluc.pfeproject.common.ItemInterface;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.Jury;

/**
 * Created by lucasamiaud on 09/01/2018.
 */

public class JurysAdapter extends RecyclerView.Adapter<JurysAdapter.JurysViewHolder>{

    private ItemInterface fragment;
    private List<Jury> jurys;
    private List<Integer> positionsExpanded;
    private Context ctx;

    public JurysAdapter(Context ctx, ItemInterface fragment) {
        this.ctx = ctx;
        this.fragment = fragment;
        setJurys(Content.jurys);
        positionsExpanded = new ArrayList<>();
    }

    public void setJurys(List<Jury> jurys){
        ArrayList<Jury> newJurys = new ArrayList<>();
        //deleting those who aren't specified
        for (int i=0;i<jurys.size();i++) {
            if (!jurys.get(i).getMembers().isEmpty()) {
                newJurys.add(jurys.get(i));
            }
        }
        this.jurys = newJurys;
    }

    @Override
    public JurysViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View jurysView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_jury, parent, false);
        return new JurysAdapter.JurysViewHolder(jurysView);
    }

    @Override
    public void onBindViewHolder(JurysViewHolder holder, int position) {
        if(getItemCount() != 0){
            Jury jury = jurys.get(position);

            holder.nbProjects.setText(ctx.getString(R.string.emptyField));
            if (jury.getProject().size() == 0) {
                String nope = "No projects assigned";
                holder.nbProjects.setText(nope);
            } else if (jury.getProject().size() != -1) {
                String yep = "Project(s) assigned: " + jury.getProject().size();
                holder.nbProjects.setText(yep);
            }

            holder.juryId.setText(ctx.getString(R.string.emptyField));
            if (jury.getIdJury() != -1) {
                String juryTS = "Jury n°" + jury.getIdJury();
                holder.juryId.setText(juryTS);
            }

            holder.members.setText(ctx.getString(R.string.emptyField));
            if (!jury.getMembers().isEmpty()) {
                String memberTS = jury.getMembers().get(0).getFullName();
                for (int i = 1; i < jury.getMembers().size(); i++) {
                    memberTS += "\n\n" + jury.getMembers().get(i).getFullName();
                }
                holder.members.setText(memberTS);
            }

            holder.date.setText(ctx.getString(R.string.emptyField));
            if(jury.getDate() != null){
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                try {
                    Date date = format.parse(jury.getDate());
                    Log.e("date",date.toString());
                    holder.date.setText(DateFormat.getDateInstance().format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }else{
            holder.juryId.setText("You have no jury!");
        }
    }

    @Override
    public int getItemCount() {
        return jurys.size();
    }

    public void setMyJurys(ArrayList<Jury> myJurys) {
        this.jurys = myJurys;
    }

    class JurysViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final View view;

        private final TextView juryId;
        private final TextView nbProjects;
        private final TextView members;
        private final TextView date;

        public JurysViewHolder(View view) {
            super(view);
            this.view = view;
            juryId = (TextView) view.findViewById(R.id.title);
            nbProjects = (TextView) view.findViewById(R.id.name);
            members = (TextView) view.findViewById(R.id.descrip);
            date = (TextView) view.findViewById(R.id.date);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            fragment.onItemClick(getAdapterPosition());
        }
    }
}
