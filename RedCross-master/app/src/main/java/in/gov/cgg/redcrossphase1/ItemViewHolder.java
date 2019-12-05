package in.gov.cgg.redcrossphase1;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView itemContent;

    public ItemViewHolder(View itemView) {
        super(itemView);
        // itemContent = (TextView)itemView.findViewById(R.id.item_content);
    }
}
