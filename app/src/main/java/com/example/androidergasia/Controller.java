package com.example.androidergasia;

import androidx.recyclerview.widget.RecyclerView;

/*
Κλάση που δίνει, στις άλλες κλάσεις,  πρόσβαση στην βάση δεδομένων και στον adapter
 */
public class Controller
{
    private static DBhandler DBhandler ;

    public static RecyclerAdapter getAdapter() {
        return recyclerAdapter;
    }

    //    public static RecyclerView.Adapter<RecyclerAdapter.ViewHolder> getAdapter()
//    {
//        return adapter;
//    }
//
    private static RecyclerAdapter recyclerAdapter;

//    private static RecyclerView.Adapter<RecyclerAdapter.ViewHolder> adapter;

    private Controller() throws Exception {
        throw  new Exception("Utilities class!");
    }

    public static DBhandler getDBhandler()
    {
        return DBhandler;
    }

//    public static void setRecyclerAdapter(RecyclerView.Adapter<RecyclerAdapter.ViewHolder> recyclerAdapterForSet)
//    {
//       adapter = recyclerAdapterForSet;
//    }


    public static void setRecyclerAdapter(RecyclerAdapter recyclerAdapterForSet) {
       recyclerAdapter = recyclerAdapterForSet;
    }

    public static void init(DBhandler handler)
    {
        DBhandler = handler;
    }

}
