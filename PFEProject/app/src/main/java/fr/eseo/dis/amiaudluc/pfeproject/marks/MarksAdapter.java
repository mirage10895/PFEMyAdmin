package fr.eseo.dis.amiaudluc.pfeproject.marks;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import fr.eseo.dis.amiaudluc.pfeproject.Content.Content;
import fr.eseo.dis.amiaudluc.pfeproject.R;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.StudentMark;

/**
 * Created by lucasamiaud on 12/01/2018.
 */

public class MarksAdapter extends RecyclerView.Adapter<MarksAdapter.ViewHolder> {

    private Context ctx;
    private ArrayList<StudentMark> studentMarks = Content.marks;

    /**
     * The constructor of the TeamAdapter.
     *
     * @param ctx  : The current context.
     * @param studentMarks : The ArrayList of mates which will be displayed.
     */
    public MarksAdapter(Context ctx, ArrayList<StudentMark> studentMarks) {
        this.ctx = ctx;
        this.studentMarks = studentMarks;
    }

    /**
     * This function will create a view with the corresponding layout.
     */
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marks,
                parent, false);
        return new ViewHolder(itemView);
    }

    /**
     * This function fill the layout with the correct values.
     *
     * @param holder   : The holder of the elements on the view.
     * @param position : The position in the team array.
     */
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(ctx.getString(R.string.emptyField));
        if (studentMarks.get(position).getStudent().getFullName() != null) {
            holder.name.setText(studentMarks.get(position).getStudent().getFullName());
        }

        holder.marks.setText(ctx.getString(R.string.emptyField));
        if (studentMarks.get(position).getMark() != -1){
            holder.marks.setText(""+studentMarks.get(position).getMark());
        }
        holder.avgMarks.setText(ctx.getString(R.string.emptyField));
        if (studentMarks.get(position).getAvgMark() != -1){
            holder.avgMarks.setText(""+studentMarks.get(position).getAvgMark());
        }

    }

    @Override
    public int getItemCount() {
        return studentMarks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView marks;
        TextView avgMarks;

        ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.name);
            marks = view.findViewById(R.id.mark);
            avgMarks = view.findViewById(R.id.avgmark);

        }
    }
}
