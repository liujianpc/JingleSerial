package com.jingle.testrecycleview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


/**
 * Created by Administrator on 2017/7/18.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private static final String TAG = RecyclerAdapter.class.getSimpleName();
    private List<Model> data;
    private LayoutInflater layoutInflater;
    private RecyclerView mRecyclerView;
    private OnItemClickListener onItemClickListener;
    private int width;
    private Context context;

    public RecyclerAdapter(Context context, List<Model> data, int width) {
        this.context = context;
        this.data = data;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.width = width;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item, parent, false);
        itemView.setOnClickListener(this);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        fuckOnBindViewHolder((ViewHolder) holder, position);
    }


    public void fuckOnBindViewHolder(final ViewHolder holder, final int position) {
        holder.textView.setText(data.get(position).getTitle());
        ViewGroup.LayoutParams layoutParams = holder.imageView.getLayoutParams();
        //layoutParams.height = data.get(position).getBitmap().getHeight()+40;
        layoutParams.width = this.width / 2;
//        layoutParams.height = data.get(position).getBitmap().getHeight()+40;
        holder.imageView.setLayoutParams(layoutParams);
        // holder.imageView.setImageBitmap(data.get(position).getBitmap());

        Glide.with(context).load(data.get(position).getImageUrl()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public void onClick(View v) {
        int childPosition = mRecyclerView.getChildPosition(v);
        //Log.d("TAG", "子元素" + childPosition);
        //ToastUtil.showToast(v.getContext(),"子元素" + childPosition);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.get(childPosition).getIndexUrl()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    public interface OnItemClickListener {
        void onItemClick(int position, Model model);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }


}

