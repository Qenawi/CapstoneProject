package com.example.qenawi.ttasker_capstone.adapterx;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.qenawi.ttasker_capstone.R;
import com.example.qenawi.ttasker_capstone.modle.TaskItem;

import java.util.ArrayList;

/**
 * Created by QEnawi on 7/19/2017
 */
public class ProjectViewUserAdp extends RecyclerView.Adapter<ProjectViewUserAdp.MainVIewHOlder> {
    private Context context;
    private int rotate;
    private onClickListner mOnClickListener;
    private ArrayList<TaskItem> recipeItemArrayList;

    public ProjectViewUserAdp(Context C, onClickListner L, ArrayList<TaskItem> D, int Rotate) {
        context = C;
        mOnClickListener = L;
        recipeItemArrayList = D;
        rotate = Rotate;
    }

    //basic Fn
    @Override
    public MainVIewHOlder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context parent_C = parent.getContext();
        int Layoutidforitem = R.layout.projectview_user_list_item;
        LayoutInflater inflater = LayoutInflater.from(parent_C);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(Layoutidforitem, parent, shouldAttachToParentImmediately);
        MainVIewHOlder hOlder = new MainVIewHOlder(view);
        return hOlder;
    }

    @Override
    public void onBindViewHolder(MainVIewHOlder holder, int position)
    {
        try {
            holder.bind(recipeItemArrayList.get(position), rotate);
        }catch (Exception ignore){}

    }

    @Override
    public int getItemCount() {
        if (recipeItemArrayList == null) {
            return 0;
        }
        return recipeItemArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public interface onClickListner {
        void onListItemClick(int Clickpos);
    }

    class MainVIewHOlder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tName, tData, tDate;
        CheckBox tState;

        public MainVIewHOlder(View itemView) {
            super(itemView);
            tName = (TextView) itemView.findViewById(R.id.NAME);
            tData = (TextView) itemView.findViewById(R.id.DATA);
            tDate = (TextView) itemView.findViewById(R.id.DATE);
            tState = (CheckBox) itemView.findViewById(R.id.checkBox);
            tState.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        void bind(TaskItem task, int rotate)

        {
            tName.setText(task.getTaskName());
            tData.setText(task.getTaskDesc());
            tDate.setText(task.getDate());
            tState.setChecked(task.getDoneB());
        }

        @Override
        public void onClick(View view) {
            int Clickpos = getAdapterPosition();
            mOnClickListener.onListItemClick(Clickpos);
        }
    }
}
