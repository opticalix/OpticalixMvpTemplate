package com.felix.opticalixmvptemplate.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by opticalix@gmail.com on 16/1/5.
 */
public class DemoUtils {

    @NonNull
    public static ListView createSimpleListView(Context context, int count, int which) {
        ListView listView = new ListView(context);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            data.add(which + "-" + i);
        }
        listView.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, android.R.id.text1, data));
        return listView;
    }

//    @NonNull
//    public static ListView createSimpleListViewByOpAdapter(Context context, int count, int which) {
//        ListView listView = new ListView(context);
//        List<SimpleText> data = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            data.add(new SimpleText(which + "-" + i));
//        }
//
//        DemoSimpleTextListAdapter simpleTextListAdapter = new DemoSimpleTextListAdapter();
//        simpleTextListAdapter.addToModelList(data);
//        listView.setAdapter(simpleTextListAdapter);
//        return listView;
//    }

}
