package com.vita.licl.expandedlistview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vita.licl.expandedlistview.Adapter.ExpandAdapter;
import com.vita.licl.expandedlistview.Adapter.ExpandListView;
import com.vita.licl.expandedlistview.Adapter.OnListRefreshListener;
import com.vita.licl.expandedlistview.Adapter.ViewHolder;
import com.vita.licl.expandedlistview.Data.ListItemA;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnListRefreshListener{

    ExpandListView testlist;
    ExpandAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testlist=(ExpandListView)findViewById(R.id.test_listview);
        listAdapter=new MyAdapter(this,init());
        testlist.setAdapter(listAdapter);
        testlist.setOnRefreshListener(this);
    }

    private List<ListItemA> init() {
        List list=new ArrayList();
        for(int i=0;i<10;i++){
            ListItemA item=new ListItemA();
            item.setTitle("title "+(i+1));
            item.setSubtitle("subtitlesubtitle");
            item.setContent("Content Content Content Content Content");
            list.add(item);
        }
        return list;
    }

    @Override
    public void onDownPullRefresh() {
        Toast.makeText(this,"下拉刷新",Toast.LENGTH_SHORT).show();
        listAdapter.finishLoading(testlist);
    }

    @Override
    public void onLoadingMore() {
        Toast.makeText(this,"上拉加载更多",Toast.LENGTH_SHORT).show();
        listAdapter.finishLoading(testlist);
    }

    public class ViewHolderItemA extends ViewHolder{

        TextView title;
        TextView subtitle;
        TextView content;

        @Override
        public void setViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.title);
            subtitle = (TextView) view.findViewById(R.id.subtitle);
            content = (TextView) view.findViewById(R.id.content);
        }
    }

    public class MyAdapter extends ExpandAdapter<ListItemA> {

        ViewHolderItemA holder;

        public MyAdapter(Context ctxt, List<ListItemA> lst) {
            super(ctxt, lst);
        }

        @Override
        protected void getItemView(View view, ViewHolder viewHolder,ListItemA item) {
            holder.title.setText(item.getTitle());
            holder.subtitle.setText(item.getSubtitle());
            holder.content.setText(item.getContent());
        }

        @Override
        protected ViewHolder getViewHolder() {
            holder=new ViewHolderItemA();
            return holder;
        }
    }
}
