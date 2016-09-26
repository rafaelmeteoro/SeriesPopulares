package br.com.rafael.seriespopulares.custom;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by rafael on 9/25/16.
 *
 * Classe listener usado em conjunto com RecyclerView para carregamento de uma lista em paginação.
 **/

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    // O número total de itens no conjunto de dados após a última carga
    private int previousTotal = 0;
    // Verdadeiro se ainda estamos à espera para o último conjunto de dados para carregar
    private boolean loading = true;
    // A quantidade mínima de itens para ter abaixo de sua posição de rolagem atual antes de carregar mais
    private int visibleThreshold = 5;

    private int firstVisibleItem, visibleItemCount, totalItemCount;

    // O índice atual de deslocamento de dados que você já tenha carregado
    private int currentPage = 1;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager, int visibleThreshold) {
        this.mLinearLayoutManager = linearLayoutManager;
        this.visibleThreshold = visibleThreshold;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (loading && (totalItemCount > previousTotal)) {
            loading = false;
            previousTotal = totalItemCount;
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            // Final da lista foi atingido
            // Faça alguma coisa
            currentPage++;
            onLoadMore(currentPage);
            loading = true;
        }
    }

    // Volta as configurações como o primeiro carregamento
    public void reset() {
        previousTotal = 0;
        currentPage = 1;
    }

    public abstract void onLoadMore(int currentPage);
}
