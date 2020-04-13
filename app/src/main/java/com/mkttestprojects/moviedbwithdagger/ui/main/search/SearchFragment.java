package com.mkttestprojects.moviedbwithdagger.ui.main.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.mkttestprojects.moviedbwithdagger.BaseFragment;
import com.mkttestprojects.moviedbwithdagger.R;
import com.mkttestprojects.moviedbwithdagger.models.MovieListInfo;
import com.mkttestprojects.moviedbwithdagger.models.MovieListModel;
import com.mkttestprojects.moviedbwithdagger.ui.MovieAdapter;
import com.mkttestprojects.moviedbwithdagger.ui.main.Resource;
import com.mkttestprojects.moviedbwithdagger.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import butterknife.BindView;

public class SearchFragment extends BaseFragment {

    private static final String TAG = "SearchFragment";

    @BindView(R.id.rv_search_result_items)
    RecyclerView rvSearchResults;

    @BindView(R.id.et_search)
    EditText etSearch;

    @BindView(R.id.ibtn_close)
    ImageView ibtnClose;

    @Inject
    MovieAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    private SearchFragmentViewModel viewModel;

    private String search = " ";

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_search;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(getActivity(), providerFactory).get(SearchFragmentViewModel.class);

        init();
    }

    private void init() {

        rvSearchResults.setLayoutManager(new StaggeredGridLayoutManager(3, GridLayoutManager.VERTICAL));
        rvSearchResults.setAdapter(adapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.equals("")) {
                    adapter.clear();
                } else if (s.length() != 0) {
                    search = s.toString();
                    adapter.clear();
                    getSearchResults(search);

                } else {
                    adapter.clear();
                }

            }
        });
        ibtnClose.setOnClickListener(v -> {
            etSearch.setText("");
            adapter.clear();
        });

    }

    private void getSearchResults(String search) {
        viewModel.observeSearchResult(search).removeObservers(getViewLifecycleOwner());
        viewModel.observeSearchResult(search).observe(getViewLifecycleOwner(), new Observer<Resource<MovieListModel>>() {
            @Override
            public void onChanged(Resource<MovieListModel> movieListModelResource) {
                if(movieListModelResource != null){
                    switch (movieListModelResource.status){
                        case LOADING:
                            adapter.showLoading();
                            break;
                        case SUCCESS:
                            adapter.clear();
                            for(MovieListInfo movieListInfo : movieListModelResource.data.getResults()){
                                adapter.add(movieListInfo);
                            }
                            break;
                        case ERROR:
                            Log.e(TAG, "onChanged: error" );
                            break;
                    }
                }
            }
        });
    }
}

