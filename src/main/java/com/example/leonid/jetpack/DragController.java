package com.example.leonid.jetpack;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.leonid.jetpack.adapters.recycleAdapterConst;
import com.example.leonid.jetpack.adapters.recycleAdapterDeliveries;
import com.example.leonid.jetpack.adapters.recycleAdapterRoutes;


public class DragController implements RecyclerView.OnItemTouchListener {
    public static final int ANIMATION_DURATION = 100;
    private RecyclerView recyclerView;
    private ImageView overlay;
    recycleAdapterConst.AdapterList kind;
    private final GestureDetectorCompat gestureDetector;
    public static final String TAG = "DragController";

    private boolean isDragging = false;
    private View draggingView;
    private boolean isFirst = true;
    private long draggingId = -1;
    private float startY = 0f;
    View first;
    private Rect startBounds = null;

    public DragController(final RecyclerView recyclerView, ImageView overlay, recycleAdapterConst.AdapterList kind) {
        this.recyclerView = recyclerView;
        this.overlay = overlay;
        this.kind = kind;
//        GestureDetector.SimpleOnGestureListener longClickGestureListener = new GestureDetector.SimpleOnGestureListener() {
//            @Override
//            public void onLongPress(MotionEvent e) {
//                Toast.makeText(recyclerView.getContext(),"fffffffffff", Toast.LENGTH_SHORT).show();
//                super.onLongPress(e);
//                isDragging = true;
//                dragStart(e.getX(), e.getY());
//            }
//        };
        GestureDetector.SimpleOnGestureListener longClickGestureListener = new MyGestureListener();
        this.gestureDetector = new GestureDetectorCompat(recyclerView.getContext(), longClickGestureListener);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (isDragging) {
            return true;
        }
     //   Log.d(TAG,"onInterceptTouchEvent");
        gestureDetector.onTouchEvent(e);

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
     //   Log.d(TAG,"onTouch");
        int y = (int) e.getY();
        if (e.getAction() == MotionEvent.ACTION_UP) {
            dragEnd();
            isDragging = false;
        } else {
            drag(y);
        }
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    private void dragStart(float x, float y) {
        draggingView = recyclerView.findChildViewUnder(x, y);
         first = recyclerView.getChildAt(0);
        isFirst = draggingView == first;
        startY = y - draggingView.getTop();
        paintViewToOverlay(draggingView);
        overlay.setTranslationY(y - startY);
        draggingView.setVisibility(View.INVISIBLE);
        draggingId = recyclerView.getChildItemId(draggingView);
        startBounds = new Rect(draggingView.getLeft(), draggingView.getTop(), draggingView.getRight(), draggingView.getBottom());
    }

    private void drag(int y) {
        overlay.setTranslationY(y - startY);
        if (!isInPreviousBounds()) {
            if (y<0)
            {
                y=0;
            }
            View view = recyclerView.findChildViewUnder(0, y);
//            Log.d(TAG,"first: " + first.getY() + "curr y:" + y + "view: "  +view +"  view second:" + (recyclerView.findChildViewUnder(0, 1) == first));
//            Log.d(TAG,"childpos: " + recyclerView.getChildPosition(view)  + " sec: " + recyclerView.getChildAdapterPosition(view));
            if (/**recyclerView.getChildPosition(view) != 0 && **/ view != null) {
                if (kind.equals(recycleAdapterConst.AdapterList.DELIVERIES))
                {
                 //   Log.d(TAG,"1290");
                    swapViewsDeliveries(view);
                }
                else if (kind.equals(recycleAdapterConst.AdapterList.ROUTES))
                {
                 //   Log.d(TAG,"1295");
                    swapViewsRoutes(view);
                }
                else
                {
                    Log.d(TAG,"error no routes or deliveries");
                }

            }

        }

    }
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent event) {
           // Log.d(TAG,"onDown: ");

            // don't return false here or else none of the other
            // gestures will work
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
          //  Log.i(TAG, "onSingleTapConfirmed: ");
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
          //  Log.i(TAG, "onLongPress: ");
            return ;
//            Toast.makeText(recyclerView.getContext(),"fffffffffff", Toast.LENGTH_SHORT).show();
//            super.onLongPress(e);
//            isDragging = true;
//            dragStart(e.getX(), e.getY());
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
           // Log.i(TAG, "onDoubleTap: ");
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
          //  Log.i(TAG, "onScroll: ");
            super.onLongPress(e1);
            isDragging = true;
            dragStart(e1.getX(), e1.getY());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
           // Log.d(TAG, "onFling: ");
            return true;
        }
    }

    private void swapViewsDeliveries(View current) {
        long replacementId = recyclerView.getChildItemId(current);
        recycleAdapterDeliveries adapter = (recycleAdapterDeliveries) recyclerView.getAdapter();
        int start = adapter.getPositionForId(replacementId);
        int end = adapter.getPositionForId(draggingId);
        adapter.moveItem(start, end);
        if (isFirst) {
            recyclerView.scrollToPosition(end);
            isFirst = false;
        }
        startBounds.top = current.getTop();
        startBounds.bottom = current.getBottom();
    }
    private void swapViewsRoutes(View current) {
        long replacementId = recyclerView.getChildItemId(current);
        recycleAdapterRoutes adapter = (recycleAdapterRoutes) recyclerView.getAdapter();
        int start = adapter.getPositionForId(replacementId);
        int end = adapter.getPositionForId(draggingId);
        adapter.moveItem(start, end);
        if (isFirst) {
            recyclerView.scrollToPosition(end);
            isFirst = false;
        }
        startBounds.top = current.getTop();
        startBounds.bottom = current.getBottom();
    }

    private void dragEnd() {
        overlay.setImageBitmap(null);
        draggingView.setVisibility(View.VISIBLE);
        float translationY = overlay.getTranslationY();
        draggingView.setTranslationY(translationY - startBounds.top);
        draggingView.animate().translationY(0f).setDuration(ANIMATION_DURATION).start();
    }

    private void paintViewToOverlay(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        overlay.setImageBitmap(bitmap);
        overlay.setTop(0);
    }

    public boolean isInPreviousBounds() {
        float overlayTop = overlay.getTop() + overlay.getTranslationY();
        float overlayBottom = overlay.getBottom() + overlay.getTranslationY();
        Boolean out = overlayTop < startBounds.bottom && overlayBottom > startBounds.top;
        return out;
    }
}