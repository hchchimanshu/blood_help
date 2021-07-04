package com.example.testing3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView bloodHelp;
    private CharSequence charSequence;
    private int index;
    private int delay =200;
    private int delayInSpash =3500;
    private Handler handler= new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        fullScreen();

        animateText(getResources().getString(R.string.blood_help));

        handlerToDelayScreen();

    }

    private void handlerToDelayScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                startActivity(new Intent(MainActivity.this,BaseActivity.class));
                finish();
            }
        }, 3500);

    }

    private void fullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initView() {
        bloodHelp= findViewById(R.id.blood_help_TV);
    }

    Runnable runnable= new Runnable() {
        @Override
        public void run() {
            bloodHelp.setText(charSequence.subSequence(0,index++));
            if (index<=charSequence.length())
            {
                handler.postDelayed(runnable,delay);
            }

        }
    };

    public void animateText(CharSequence cs)
    {
        charSequence=cs;
        index=0;
        bloodHelp.setText("");
        handler.removeCallbacks(runnable);

        handler.postDelayed(runnable,delay);
    }
}
