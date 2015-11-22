package root.com.jiranimmicrocredit.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import root.com.jiranimmicrocredit.MainActivity;
import root.com.jiranimmicrocredit.R;

/**
 * Created by root on 11/4/15.
 */
public class GridAdapter  extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    private static final String TAG = "GridAdapter";
    List<SetterGetters> mItems;
    Context context;
    OnItemClickListener mItemClickListener;




    public interface AdapterClickevents{
        public void onGridClicked(int postion);


    }
    SetterGetters setter=new SetterGetters();
    AdapterClickevents listener ;
    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public GridAdapter(Context context) {
        super();
        this.context=context;
        mItems = new ArrayList<SetterGetters>();
        SetterGetters species = new SetterGetters();
        species.setName("Income");
        species.setThumbnail(R.drawable.ic_call_black_24dp);
        mItems.add(species);

        species = new SetterGetters();
       // species.setName("Loans");
        species.setThumbnail(R.drawable.ic_call_black_24dp);
        mItems.add(species);

        species = new SetterGetters();
        species.setName("Gurantors");
        species.setThumbnail(R.drawable.ic_call_black_24dp);
        mItems.add(species);

        species = new SetterGetters();
        species.setName("Reports");
        species.setThumbnail(R.drawable.ic_call_black_24dp);
        mItems.add(species);


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int position) {
        SetterGetters nature = mItems.get(position);
        viewHolder.tvitem.setText(nature.getName());
        viewHolder.imgThumbnail.setImageResource(nature.getThumbnail());
        viewHolder.position=position;

        viewHolder.imgThumbnail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context,"postion"+ position,Toast.LENGTH_SHORT).show();
                setter.setGridPosition(position);
            }
        });
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imgThumbnail;
        public TextView tvitem;
        public int position;
        public LinearLayout placeHolder;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
            placeHolder = (LinearLayout)itemView.findViewById(R.id.mainHolder);
            tvitem = (TextView)itemView.findViewById(R.id.tv_item);
            imgThumbnail.setOnClickListener(this);
          /*  public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
              //  this.mItemClickListener = mItemClickListener;
            }*/
           // placeHolder.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView, getAdapterPosition());
                Toast.makeText(context,"position" + getAdapterPosition(),Toast.LENGTH_SHORT).show();
;            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


}
