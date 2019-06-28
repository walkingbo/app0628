package com.ssb.app0628;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView resultView;
    ImageView resultImageView;

    Button contactbtn, camerabtn, phonebtn, mapbtn, browserbtn;

    Bitmap bitmap;
    //이미지의 크기를 저장할 변수
    int requestWidth, requestHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultView = (TextView)findViewById(R.id.resultview);
        resultImageView= (ImageView)findViewById(R.id.resultimageview);

        contactbtn =(Button)findViewById(R.id.contactbtn);
        camerabtn =(Button)findViewById(R.id.camerabtn);
        phonebtn =(Button)findViewById(R.id.phonebtn);
        browserbtn =(Button)findViewById(R.id.browserbtn);
        mapbtn =(Button)findViewById(R.id.mapbtn);

        //크기 가져오기
        requestWidth = getResources().getDimensionPixelSize(R.dimen.request_image_width);
        requestHeight = getResources().getDimensionPixelSize(R.dimen.request_image_height);

        //연락처 앱 호출
        contactbtn.setOnClickListener((view)->{
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            //다음 액티비티를 화면에 출력하고 데이터를 돌려받을 수는 없습니다.
            //startActivity(intent);

            //데이터를 넘겨받는 형태로 다음 액티비티를 출력
            startActivityForResult(intent,100);
        });


        //카메라 앱을 호출
        camerabtn.setOnClickListener((view)->{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,200);
        });

        //지도 앱을 호출
        mapbtn.setOnClickListener((view)->{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.5662,126.9779"));
            startActivity(intent);
        });

        //크롬 호출
        browserbtn.setOnClickListener((view)->{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.naver.com"));
            startActivity(intent);
        });

        //전화 앱을 호출 - 실시간으로 권한을 확인
        phonebtn.setOnClickListener((view)->{
            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)
            == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:010-9497-8523"));
                startActivity(intent);
            }else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},100);
            }
        });
    }

    //startActivityForResult로 다음 액티비티를 출력했을 때
    //하위 Activity가 사라지면 호출되는 메소드
    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent intent){
        if(requestCode==100){
            //선택한 전화번호의 문자열을 출력
            //마지막 부분이 id 입니다.
            //이 id를 이용해서 ContentProvider를 이용하면 자세한 정보를
            //찾아올수 있습니다.

            String result = intent.getDataString();
            resultView.setText(result);
        }else if(requestCode==200){
            bitmap = (Bitmap)intent.getExtras().get("data");
            resultImageView.setImageBitmap(bitmap);
        }
    }

    //액티비티가 제거되기 직전에 호출되는 메소드
    //저장할 데이터가 있으면 이 메소드의 bundle에 저장
    @Override
    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        //데이터를 저장
        bundle.putString("data",resultView.getText().toString());

        //안드로이드에서는 직력화하는 인터페이스 2개
        //자바에서 사용하는 Serializable
        //안드로이드에서 추가된 Parcelable
        if(bitmap != null){
            bundle.putParcelable("image",bitmap);
        }
    }

    //화면을 복원하기 위한 메소드
    @Override
    public void onRestoreInstanceState(Bundle bundle){
        super.onRestoreInstanceState(bundle);
        //데이터 가져오기
        String data = bundle.getString("data");
        //데이터를 다시 출력
        resultView.setText(data);

        Object obj = bundle.getParcelable("image");
        if(obj!=null){
            bitmap =(Bitmap)bundle.getParcelable("image");
            resultImageView.setImageBitmap(bitmap);
        }

    }



}
