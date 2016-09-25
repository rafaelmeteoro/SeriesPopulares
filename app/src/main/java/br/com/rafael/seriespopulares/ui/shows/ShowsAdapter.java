package br.com.rafael.seriespopulares.ui.shows;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import br.com.rafael.seriespopulares.R;
import br.com.rafael.seriespopulares.data.model.Show;

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

    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void setList(List<Show> list) {
        mList = list;
    }

    protected class ItemShowsViewHolder extends RecyclerView.ViewHolder {

        public ItemShowsViewHolder(View view) {
            super(view);
        }
    }
}
