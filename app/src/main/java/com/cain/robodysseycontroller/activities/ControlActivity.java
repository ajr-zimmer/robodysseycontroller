package com.cain.robodysseycontroller.activities;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.cain.robodysseycontroller.R;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ControlActivity extends AppCompatActivity implements
        GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private GestureDetectorCompat mDetector;
    private boolean tourMode;
    private Socket clientSocket;
    private DataOutputStream dataOut;

    private static final int SERVERPORT = 8888;
    private static final String SERVER_IP = "10.0.2.2";
    private static final String VIDEOFEED = "http://192.168.1.65:8080/video";
            //"http://www.ebookfrenzy.com/android_book/movie.mp4";
            //


    // Creates activity and turns it into immersive/manual mode
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        tourMode = true;
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        mDetector = new GestureDetectorCompat(this, this);
        // Set the gesture detector as the double tap
        // listener.
        mDetector.setOnDoubleTapListener(this);

        new Thread(new ClientThread()).start();

        // code for video feed here
        new Thread(new MjpegMaker()).start();

    }

    class MjpegMaker implements Runnable {
        @Override
        public void run() {
            MjpegView vidView = (MjpegView) findViewById(R.id.vidView);
            vidView.setSource(MjpegInputStream.read(VIDEOFEED));
        }
    }

    class ClientThread implements Runnable {

        @Override
        public void run() {
            // Open a clientSocket to communicate with server
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                clientSocket = new Socket(serverAddr, SERVERPORT);
                dataOut = new DataOutputStream(clientSocket.getOutputStream());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void SendTransmission(String command) {
        try {
            dataOut.writeBytes(command + '\n');
            //dataOut.writeUTF(command + '\n'); // right and up does not work with 'r' and 'u' for whatever reason
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    // handles swipe gestures
    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        if (tourMode) {
            Log.d("Sound: ", "in Tour Mode, double tap to engage controls");
        } else {
            float minDistance = 40;
            if (event1.getX() - event2.getX() > minDistance && Math.abs(velocityX) > Math.abs(velocityY)) {
                Log.d("Direction: ", "Right");
                SendTransmission("right");
            } else if (event1.getX() - event2.getX() < minDistance && Math.abs(velocityX) > Math.abs(velocityY)) {
                Log.d("Direction: ", "Left");
                SendTransmission("left");
            } else if (event1.getY() - event2.getY() > minDistance && Math.abs(velocityX) <= Math.abs(velocityY)) {
                Log.d("Direction: ", "Up");
                SendTransmission("up");
            } else if (event1.getY() - event2.getY() < minDistance && Math.abs(velocityX) <= Math.abs(velocityY)) {
                Log.d("Direction: ", "Down");
                SendTransmission("down");
            }
        }

        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return true;
    }


    // see name
    @Override
    public boolean onDoubleTap(MotionEvent event) {
        if (tourMode) {
            Log.d("Mode Switched: ", "Manual Mode");
            SendTransmission("manual");
        } else {
            Log.d("Mode Switched: ", "Tour Mode");
            SendTransmission("tour");
        }
        tourMode = !tourMode;
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        return true;
    }

}
