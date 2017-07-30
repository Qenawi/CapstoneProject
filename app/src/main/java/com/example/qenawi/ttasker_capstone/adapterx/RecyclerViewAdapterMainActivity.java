package com.example.qenawi.ttasker_capstone.adapterx;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qenawi.ttasker_capstone.R;

import java.util.ArrayList;

/**
 * Created by QEnawi on 7/19/2017
 */
public class RecyclerViewAdapterMainActivity extends RecyclerView.Adapter<RecyclerViewAdapterMainActivity.MainVIewHOlder> {
    private Context context;
    private int rotate;
    private onClickListner mOnClickListener;
    private ArrayList<String> recipeItemArrayList;

    public RecyclerViewAdapterMainActivity(Context C, onClickListner L, ArrayList<String> D, int Rotate) {
        context = C;
        mOnClickListener = L;
        recipeItemArrayList = D;
        rotate = Rotate;
    }

    //basic Fn
    @Override
    public MainVIewHOlder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context parent_C = parent.getContext();
        int Layoutidforitem = R.layout.list_item_txt2;
        LayoutInflater inflater = LayoutInflater.from(parent_C);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(Layoutidforitem, parent, shouldAttachToParentImmediately);
        MainVIewHOlder hOlder = new MainVIewHOlder(view);
        return hOlder;
    }

    @Override
    public void onBindViewHolder(MainVIewHOlder holder, int position) {
        holder.bind(recipeItemArrayList.get(position), rotate);
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
        TextView PName;

        public MainVIewHOlder(View itemView) {
            super(itemView);
            PName = (TextView) itemView.findViewById(R.id.item2text2);
            itemView.setOnClickListener(this);
        }

        void bind(String Pname, int rotate) {
            PName.setText(Pname);
        }

        @Override
        public void onClick(View view) {
            int Clickpos = getAdapterPosition();
            mOnClickListener.onListItemClick(Clickpos);
        }
    }
}
