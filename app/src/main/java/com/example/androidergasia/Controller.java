package com.example.androidergasia;

public class Controller
{
    private static DBhandler DBhandler ;

    public Controller(DBhandler  handler)
    {
        DBhandler = handler;
    }

    public static DBhandler getDBhandler()
    {
        return DBhandler;
    }

}
