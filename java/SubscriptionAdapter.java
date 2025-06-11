package com.project.gudasi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.ViewHolder> {

    private List<Subscription> subscriptions;
    private Context context;

    public SubscriptionAdapter(Context context, List<Subscription> subscriptions) {
        this.context = context;
        this.subscriptions = subscriptions;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView appNameText;
        TextView priceText;
        TextView renewalDateText;
        ImageView iconImage;

        public ViewHolder(View view) {
            super(view);
            appNameText = view.findViewById(R.id.appNameText);
            priceText = view.findViewById(R.id.priceText);
            renewalDateText = view.findViewById(R.id.renewalDateText);
            iconImage = view.findViewById(R.id.iconImage);
        }
    }

    @Override
    public SubscriptionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_subscription, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubscriptionAdapter.ViewHolder holder, int position) {
        Subscription subscription = subscriptions.get(position);
        holder.appNameText.setText(subscription.getApp_name());
        holder.priceText.setText("₩" + subscription.getPrice());
        holder.renewalDateText.setText(subscription.getRenewal_date());

        // 아이콘 셋팅 (예: drawable 리소스명으로 변환)
        int resId = context.getResources().getIdentifier(subscription.getIcon(), "drawable", context.getPackageName());
        if (resId != 0) {
            holder.iconImage.setImageResource(resId);
        } else {
            holder.iconImage.setImageResource(R.drawable.ic_default_icon);
        }
    }

    @Override
    public int getItemCount() {
        return subscriptions.size();
    }
}
