package com.origin.zapgrupos.ui.ListaGruposPorCategoria;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.RecyclerView;
import com.origin.zapgrupos.R;
import com.origin.zapgrupos.databinding.LoadStateItemBinding;
import org.jetbrains.annotations.NotNull;
 
public class GruposLoadState extends androidx.paging.LoadStateAdapter<GruposLoadState.LoadStateViewHolder> {
    // Define Retry Callback
    private View.OnClickListener mRetryCallback;

    public GruposLoadState(View.OnClickListener retryCallback) {
        // Init Retry Callback
        mRetryCallback = retryCallback;
    }
 
    @NotNull
    @Override
    public LoadStateViewHolder onCreateViewHolder(@NotNull ViewGroup parent,
                                                  @NotNull LoadState loadState) {
        // Return new LoadStateViewHolder object
        return new LoadStateViewHolder(parent, mRetryCallback);
    }
 
    @Override
    public void onBindViewHolder(@NotNull LoadStateViewHolder holder,
                                 @NotNull LoadState loadState) {
        // Call Bind Method to bind visibility  of views
        holder.bind(loadState);
    }
 
    public static class LoadStateViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar mProgressBar;
        private TextView mErrorMsg;
        private Button mRetry;
 
        LoadStateViewHolder(
                @NonNull ViewGroup parent,
                @NonNull View.OnClickListener retryCallback) {
            super(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.load_state_item, parent, false));
            LoadStateItemBinding binding = LoadStateItemBinding.bind(itemView);
            mProgressBar = binding.progressBarGrupos;
            mErrorMsg = binding.errorMsg;
            mRetry = binding.retryButton;
            mRetry.setOnClickListener(retryCallback);
        }
 
        public void bind(LoadState loadState) {

            if (loadState instanceof LoadState.Error) {
                // Get the error
                LoadState.Error loadStateError = (LoadState.Error) loadState;
                // Set text of Error message
                mErrorMsg.setText(loadStateError.getError().getLocalizedMessage());
            }
            Log.i("First Load", String.valueOf(loadState instanceof LoadState.Loading));
            // set visibility of widgets based on LoadState
            mProgressBar.setVisibility(loadState instanceof LoadState.Loading
                    ? View.VISIBLE : View.GONE);
            mRetry.setVisibility(loadState instanceof LoadState.Error
                    ? View.VISIBLE : View.GONE);
            mErrorMsg.setVisibility(loadState instanceof LoadState.Error
                    ? View.VISIBLE : View.GONE);
        }
    }
}