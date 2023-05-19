package com.example.androidergasia;

import androidx.recyclerview.widget.RecyclerView;

public class Controller
{
    private static DBhandler DBhandler ;

    private static RecyclerView.Adapter<RecyclerAdapter.ViewHolder> adapter;

    public Controller(DBhandler  handler)
    {
        DBhandler = handler;
    }

    public static DBhandler getDBhandler()
    {
        return DBhandler;
    }

    public static void setRecyclerAdapter(RecyclerView.Adapter<RecyclerAdapter.ViewHolder> recyclerAdapterForSet)
    {
       adapter = recyclerAdapterForSet;
    }

    public static void notifyRecyclerAdapter()
    {
        adapter.notifyDataSetChanged();
    }

}
