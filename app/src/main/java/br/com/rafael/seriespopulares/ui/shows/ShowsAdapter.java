package br.com.rafael.seriespopulares.ui.shows;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import br.com.rafael.seriespopulares.R;
import br.com.rafael.seriespopulares.data.model.Show;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rafael on 9/25/16.
 **/

public class ShowsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Show> mList;

    @Inject
    public ShowsAdapter() {
        mList = Collections.emptyList();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_shows, parent, false);
        return new ItemShowsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindShow((ItemShowsViewHolder) holder, position);
    }

    private void bindShow(ItemShowsViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Show show = mList.get(position);
        holder.tvTitle.setText(show.getTitle());
        holder.tvYear.setText(context.getString(
                R.string.layout_item_show_year_format,
                show.getYear()));
        holder.tvRating.setText(context.getString(
                R.string.layout_item_show_rating_format,
                show.getRating()));

        Picasso.with(context)
                .load(show.getImages().getFanart().getThumb())
                .placeholder(R.drawable.ic_download)
                .error(R.drawable.cloud_outline_off)
                .into(holder.ivShow);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void setList(List<Show> list) {
        mList = list;
    }

    protected class ItemShowsViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_title)
        TextView tvTitle;

        @Bind(R.id.tv_year)
        TextView tvYear;

        @Bind(R.id.tv_rating)
        TextView tvRating;

        @Bind(R.id.iv_show)
        ImageView ivShow;

        public ItemShowsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
