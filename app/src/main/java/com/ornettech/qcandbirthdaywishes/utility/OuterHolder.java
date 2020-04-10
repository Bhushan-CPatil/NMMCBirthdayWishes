package com.ornettech.qcandbirthdaywishes.utility;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ornettech.qcandbirthdaywishes.R;

public class OuterHolder extends RecyclerView.ViewHolder {
    public TextView txtroomnumber;
    public RecyclerView innerrecyclerview;

    public OuterHolder(@NonNull View itemView) {
        super(itemView);
        txtroomnumber = itemView.findViewById(R.id.txtroomnumber);
        innerrecyclerview = itemView.findViewById(R.id.innerrecyclerview);

    }
}
