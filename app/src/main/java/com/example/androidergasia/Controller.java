package com.example.androidergasia;

import androidx.recyclerview.widget.RecyclerView;

public class Controller
{
    private static DBhandler DBhandler ;

    private static RecyclerView.Adapter<RecyclerAdapter.ViewHolder> adapter;

    private Controller() throws Exception {
        throw  new Exception("Utilities class!");
    }

    public static DBhandler getDBhandler()
    {
        return DBhandler;
    }

    public static void setRecyclerAdapter(RecyclerView.Adapter<RecyclerAdapter.ViewHolder> recyclerAdapterForSet)
    {
       adapter = recyclerAdapterForSet;
    }

    public static void init(DBhandler handler)
    {
        DBhandler = handler;
    }

    public static void notifyRecyclerAdapter()
    {
        adapter.notifyDataSetChanged();
    }

}
