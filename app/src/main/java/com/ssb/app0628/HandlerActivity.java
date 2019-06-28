package com.ssb.app0628;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class HandlerActivity extends AppCompatActivity {
    TextView handlerDisplay;
    Button handlerBtn;
    int idx;

    //넘겨받은 obj를 텍스트 뷰에 출력하는 핸들러
    Handler handler = new Handler(){
      @Override
      public void handleMessage(Message msg){
        Integer data = (Integer)msg.obj;
        handlerDisplay.setText("data:"+data);
      }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        handlerDisplay = (TextView)findViewById(R.id.hanlerdisplay);
        handlerBtn =(Button)findViewById(R.id.handlerbtn);

        /*
        handlerBtn.setOnClickListener((view)->{
          Thread th = new Thread(){
              @Override
              public void run(){
                  for(int i = 0;i<10;i=i+1){
                      try{
                          Thread.sleep(1000);
                          //msg 생성
                          Message msg = new Message();
                          //데이터 대입
                          msg.obj = idx;
                          idx = idx +1;
                          //핸들러에게 메시지를 전송
                          handler.sendMessage(msg);
                      }catch (Exception e){

                      }
                  }
              }
          };
          th.start();

        });
        */
        handlerBtn.setOnClickListener((view)->{
            //Handler를 post를 이용해서 호출
            Thread th = new Thread(){
                public void run() {
                    for (int i = 0; i < 10; i = i + 1) {
                        try {
                            Calendar cal = new GregorianCalendar(2019,5,29,
                                    9,30,00);

                            long nextDay = cal.getTimeInMillis();

                            Thread.sleep(1000);
                            handler.postAtTime(new Runnable(){
                                @Override
                                public void run() {
                                    handlerDisplay.setText(idx+"");
                                }
                            },nextDay);
                            idx=idx+1;
                        } catch (Exception e) {

                        }
                    }
                }
            };
            th.start();
        });




    }
}
