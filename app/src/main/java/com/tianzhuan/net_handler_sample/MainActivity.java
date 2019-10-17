package com.tianzhuan.net_handler_sample;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    private Message message;
    private Handler handler1=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            startActivity(new Intent(MainActivity.this,PersonalActivity.class));
            return false;
        }
    });
    private Handler handler2=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mTextView.setText(msg.obj.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView=findViewById(R.id.tv);
         message=new Message();
        test();
    }

    private void test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //常规写法

//                message.obj="Net163";
//                message.what=163;
//                handler2.sendMessage(message);
                //1.Handler内存泄漏测试
                SystemClock.sleep(1000);//销毁Activity
//                message.what=3;
//               if(handler1!=null) handler1.sendMessage(message);//跳转到第二个页面
//                handler1.sendMessageDelayed(message,3000);

                //2.为什么不能再子线程创建Handler
//                new Handler();
                //3.textView.setText()只能在主线程执行，这句话是错误的！
                mTextView.setText("789");
                Toast.makeText(MainActivity.this, "896", Toast.LENGTH_SHORT).show();

            }
        }).start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("net>>>>","onDestory");
//        handler1.removeMessages(3);
//        handler1=null;
        message.recycle();
    }
}
