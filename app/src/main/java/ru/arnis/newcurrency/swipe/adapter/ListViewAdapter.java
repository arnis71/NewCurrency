package ru.arnis.newcurrency.swipe.adapter;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.woxthebox.draglistview.DragListView;

import ru.arnis.newcurrency.swipe.adapter.ViewAdapter;

public class ListViewAdapter implements ViewAdapter {

    private final DragListView mListView;

    public ListViewAdapter(DragListView listView) {
        mListView = listView;
    }

    @Override
    public Context getContext() {
        return mListView.getContext();
    }

    @Override
    public int getWidth() {
        return mListView.getWidth();
    }

    @Override
    public int getChildCount() {
        return mListView.getChildCount();
    }

    @Override
    public void getLocationOnScreen(int[] locations) {
        mListView.getLocationOnScreen(locations);
    }

    @Override
    public View getChildAt(int index) {
        return mListView.getChildAt(index);
    }

    @Override
    public int getChildPosition(View child) {
        return 0;
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        mListView.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    @Override
    public void onTouchEvent(MotionEvent e) {
        mListView.onTouchEvent(e);
    }

    @Override
    public AbsListView.OnScrollListener makeScrollListener(AbsListView.OnScrollListener listener) {
        return listener;
    }
}
