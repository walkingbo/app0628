package com.ssb.app0628;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ThreadActivity extends AppCompatActivity {
    TextView disp;
    Button btn;
    int idx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        disp =(TextView)findViewById(R.id.display);
        btn = (Button)findViewById(R.id.btn);

        btn.setOnClickListener((view)->{
            /*
            for(int i =0;i<10;i=i+1){
                try{
                    Thread.sleep(1000);
                    disp.setText("idx:"+idx);
                    idx =idx+1;
                }catch (Exception e){

                }
            }
            */
            /*
            //Thread 클래스를 상속 받아서 스레드를 생성
            Thread th = new Thread(){
                @Override
                public void run(){
                    for(int i =0;i<10;i=i+1){
                        try{
                            Thread.sleep(1000);
                            disp.setText("idx:"+idx);
                            idx =idx+1;
                        }catch (Exception e){

                        }
                    }

                }
            };
            th.start();
            */

            Runnable r = new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; i = i + 1) {
                        try {
                            Thread.sleep(1000);
                            disp.setText("idx:" + idx);
                            idx = idx + 1;
                        } catch (Exception e) {

                        }
                    }
                }
            };
            Thread th = new Thread(r);
            th.start();

        });


    }
}
