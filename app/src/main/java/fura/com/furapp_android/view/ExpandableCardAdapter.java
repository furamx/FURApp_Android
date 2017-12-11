package fura.com.furapp_android.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import fura.com.furapp_android.R;

/**
 * Created by ramon on 17/11/17.
 */

public class ExpandableCardAdapter extends RecyclerView.Adapter<ExpandableCardAdapter.ViewHolder> {
    private boolean isExpand=false;
    private Context context; 
    @Override
    public ExpandableCardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        this.context=viewGroup.getContext();
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.expandable_card_details,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExpandableCardAdapter.ViewHolder viewHolder, int position) {
        viewHolder.setIsRecyclable(false);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout buttonLayout, expandableLayout;
        public ViewHolder(View view) {
            super(view);
            buttonLayout=view.findViewById(R.id.lyt_card_event);
            expandableLayout=view.findViewById(R.id.lyt_expandable_card);
        }
    }

    private void OnClickButton(final LinearLayout expandableLayout,final LinearLayout buttonLayout){
        if(expandableLayout.getVisibility()==View.VISIBLE){
            CreateRotateAnimator(buttonLayout,180f,0f).start();
            expandableLayout.setVisibility(View.GONE);
            isExpand=false;
        }
        else{
            CreateRotateAnimator(buttonLayout,0f,180f).start();
            expandableLayout.setVisibility(View.VISIBLE);
            isExpand=true;
        }
    }
    private ObjectAnimator CreateRotateAnimator(final View target, final float from, final float to){
        ObjectAnimator animator=ObjectAnimator.ofFloat(target,"rotation",from,to);
        animator.setDuration(300);
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }
}
