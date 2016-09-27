package br.com.rafael.seriespopulares.ui.shows;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class ShowsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<Show> mList;
    private ShowsItemClickListener mListener;

    public interface ShowsItemClickListener {
        void onShowClick(Show show, int position);
        void onFavoriteClick(Show show, int position);
    }

    @Inject
    public ShowsAdapter() {
        mList = Collections.emptyList();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_shows, parent, false);
        ItemShowsViewHolder holder = new ItemShowsViewHolder(view);
        holder.llItem.setOnClickListener(this);
        holder.ibHeart.setOnClickListener(this);
        return holder;
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
        holder.ibHeart.setImageResource(show.isFavorite() ? R.drawable.ic_heart : R.drawable.ic_heart_outline);
        holder.llItem.setTag(holder);
        holder.ibHeart.setTag(holder);

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

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            ItemShowsViewHolder holder = (ItemShowsViewHolder) v.getTag();
            int position = holder.getAdapterPosition();
            if (v.getId() == R.id.ll_item) {
                mListener.onShowClick(mList.get(position), position);
            } else if (v.getId() == R.id.ib_heart) {
                mListener.onFavoriteClick(mList.get(position), position);
            }
        }
    }

    public void setList(List<Show> list) {
        mList = list;
    }

    public void addList(List<Show> list) {
        mList.addAll(list);
    }

    public void update(Show show, int position) {
        mList.set(position, show);
    }

    public void setListener(ShowsItemClickListener listener) {
        mListener = listener;
    }

    protected class ItemShowsViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.ll_item)
        LinearLayout llItem;

        @Bind(R.id.tv_title)
        TextView tvTitle;

        @Bind(R.id.tv_year)
        TextView tvYear;

        @Bind(R.id.tv_rating)
        TextView tvRating;

        @Bind(R.id.iv_show)
        ImageView ivShow;

        @Bind(R.id.ib_heart)
        ImageButton ibHeart;

        public ItemShowsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
