package com.vita.licl.expandedlistview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vita.licl.expandedlistview.Adapter.ExpandAdapter;
import com.vita.licl.expandedlistview.Adapter.ExpandListView;
import com.vita.licl.expandedlistview.Adapter.OnListRefreshListener;
import com.vita.licl.expandedlistview.Adapter.ViewHolder;
import com.vita.licl.expandedlistview.Data.ListItemA;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnListRefreshListener{

    ExpandListView listView;
    ExpandAdapter listAdapter;
    List<ListItemA> list;
    String testURL="http://y3.ifengimg.com/dee5ac7c19652025/2015/0825/rdn_55dc0f3d3a337.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView =(ExpandListView)findViewById(R.id.test_listview);
        listAdapter=new MyAdapter(this,init());
        listView.setAdapter(listAdapter);
        listView.setOnRefreshListener(this);
    }

    private List<ListItemA> init() {
        list=new ArrayList();
        for(int i=0;i<10;i++){
            ListItemA item=new ListItemA();
            item.setTitle("title " + (i + 1));
            item.setSubtitle("subtitlesubtitle");
            item.setContent("Content Content Content Content Content");
            //item.setMainbg(BitmapFactory.decodeResource(getResources(),R.drawable.mainbg));
            list.add(item);
        }
        return list;
    }

    @Override
    public void onDownPullRefresh() {//这里按照通常做法，刷新时只更新文本数据+url，需要展示时才在getview里去下载图片
        Toast.makeText(this,"下拉刷新",Toast.LENGTH_SHORT).show();
        list.clear();
        for (int i=0;i<10;i++){
            final ListItemA item=new ListItemA();
            item.setTitle("refreshed title "+(i+1));
            item.setSubtitle("subtitlesubtitle");
            item.setContent("Content refreshed Content refreshed Content refreshed");
            item.setMainbgUrl(testURL);
            //item.setMainbg(BitmapFactory.decodeResource(getResources(), R.drawable.mainbg));
            list.add(item);
        }

    listAdapter.finishLoading(listView);
    }

    private Bitmap downLoadImg(String string) {
        Bitmap bm=null;
        try{
            URL url=new URL(string);
            final HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            bm= BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (bm==null)
            throw new NullPointerException("------------下载图片失败!");
        return bm;
    }

    @Override
    public void onLoadingMore() {
        Toast.makeText(this,"上拉加载更多",Toast.LENGTH_SHORT).show();
        for (int i=0;i<5;i++){
            final ListItemA item=new ListItemA();
            item.setTitle("added title "+(i+1));
            item.setSubtitle("subtitlesubtitle");
            item.setContent("Content refreshed Content refreshed Content refreshed");
            item.setMainbgUrl(testURL);
            list.add(item);
        }

        listAdapter.finishLoading(listView);
    }

    public class ViewHolderItemA implements ViewHolder{

        TextView title;
        TextView subtitle;
        TextView content;
        ImageView mainbg;

        @Override
        public void setViewHolder(View view) {
            mainbg=(ImageView)view.findViewById(R.id.mainbg);
            title = (TextView) view.findViewById(R.id.title);
            subtitle = (TextView) view.findViewById(R.id.subtitle);
            content = (TextView) view.findViewById(R.id.content);
        }
    }

    public class MyAdapter extends ExpandAdapter<ListItemA> {

        public MyAdapter(Context ctxt, List<ListItemA> lst) {
            super(ctxt, lst);
        }

        @Override
        protected void getItemView(View view, ViewHolder viewHolder,final ListItemA item) {
            final ViewHolderItemA holder=(ViewHolderItemA)viewHolder;
            holder.title.setText(item.getTitle());
            holder.subtitle.setText(item.getSubtitle());
            holder.content.setText(item.getContent());
            if (item.getMainbgUrl()==null||(listView.isOnScroll())){
                if (listView.isOnScroll()){
                holder.title.setText("fakedata");
                holder.subtitle.setText("fakedata");
                holder.content.setText("fakedata");}
            }else {
            new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                return downLoadImg(params[0]);
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                item.setMainbg(result);
                holder.mainbg.setImageDrawable(new BitmapDrawable(holder.mainbg.getResources(), item.getMainbg()));
            }
        }.execute(item.getMainbgUrl());

            }
        }

        @Override
        protected ViewHolder getViewHolder() {
            return new ViewHolderItemA();
        }
    }
}
