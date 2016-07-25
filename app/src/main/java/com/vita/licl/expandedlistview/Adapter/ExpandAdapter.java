package com.vita.licl.expandedlistview.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.vita.licl.expandedlistview.R;
import java.util.List;

/**
 *
 * Created by pc on 2016/7/22.
 */
public abstract class ExpandAdapter<T> extends BaseAdapter {

    private List<T> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private ViewHolder viewHolder;

    public ExpandAdapter(Context ctxt,List<T> lst){
        context=ctxt;
        list=lst;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    protected abstract void getItemView(View view,ViewHolder viewHolder,T item);
    protected abstract ViewHolder getViewHolder();

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //view只会在第一页时为null，接下来滚动就不是null，不用再建立viewholedr可减少findviewbyid的次数，提升性能
        if(view ==null){
            layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.item_list, viewGroup, false);
            viewHolder = getViewHolder();
            viewHolder.setViewHolder(view);
            getItemView(view, viewHolder,getItem(i));
            //viewHolder = new ViewHolder();
            view.setTag(viewHolder);
            return view;
        }

        viewHolder = (ViewHolder)view.getTag();
        getItemView(view,viewHolder,getItem(i));

        return view;
    }

    public void finishLoading(ExpandListView listview){
        notifyDataSetChanged();
        int state=listview.getState();
        switch (state){
            case 2:
                listview.hideHeaderView();
                return;
            default:
                listview.hideFooterView();
        }
    }
}
