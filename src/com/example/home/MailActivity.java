package com.example.home;

import java.util.ArrayList;
import java.util.HashMap;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MailActivity extends Activity {
	
	ListView address;
	HashMap<String, String> mailHashname;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mail);
		
		TextView sample = (TextView)findViewById(R.id.denwa);
	    sample.setTypeface(Typeface.createFromAsset(getAssets(), "mplus-1c-thin.ttf"));
	    
		address = (ListView)findViewById(R.id.address);
		setAddressList();
		address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                ListView listView = (ListView) parent;
                // クリックされたアイテムを取得します
                String item =(String) listView.getItemAtPosition(position);
                Uri uri=Uri.parse("mailto:" + mailHashname.get(item));
        		Intent intent=new Intent(Intent.ACTION_SENDTO,uri);
        		intent.putExtra(Intent.EXTRA_SUBJECT,"");
        		intent.putExtra(Intent.EXTRA_TEXT,"");
        		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        		startActivity(intent);
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mail, menu);
		return true;
	}
	
	public void setAddressList(){
		// DATA表からメールアドレスを全て取得
		ContentResolver cr = getContentResolver();
		Cursor dataAddressTable = cr.query(
		  Data.CONTENT_URI,
		  null,
		  Data.MIMETYPE + " = ?",
		  new String[]{Email.CONTENT_ITEM_TYPE},
		  null);

		// ハッシュ変数にアドレスを格納(id, address)
		HashMap<String, String> mailHash = new HashMap<String, String>();
		while(dataAddressTable.moveToNext()) {
		  mailHash.put(
		    dataAddressTable.getString(
		      dataAddressTable.getColumnIndex(Data.CONTACT_ID)), // ID
		    dataAddressTable.getString(
		      dataAddressTable.getColumnIndex(Data.DATA1))); // メールアドレス
		}

		// カーソルを閉じる
		dataAddressTable.close();

		// ソート文字を格納（連絡先一覧を "ふりがな" 順でソート）
		String order_str =
		  " CASE" +
		  " WHEN IFNULL(" + Data.DATA9 + ", '') = ''" + // Data.DATA9がNULLの場合は空文字を代入
		  " THEN 1 ELSE 0" + // Data.DATA9が空文字のレコードを最後にする
		  " END, " + Data.DATA9 + " ," +
		  " CASE" +
		  " WHEN IFNULL(" + Data.DATA7 + ", '') = ''" +
		  " THEN 1 ELSE 0" +
		  " END, " + Data.DATA7;

		// DATA表から連絡先名を全て取得
		Cursor dataNameTable = cr.query(
		  Data.CONTENT_URI,
		  null,
		  Data.MIMETYPE + " = ?",
		  new String[]{StructuredName.CONTENT_ITEM_TYPE},
		  order_str);
		mailHashname = new HashMap<String, String>();
		// メールアドレスが存在する連絡先だけを名前格納用リストに格納
		ArrayList<String> listItems = new ArrayList<String>(); // 名前格納用リスト
		while(dataNameTable.moveToNext()) {
		  String id = dataNameTable.getString(
		    dataNameTable.getColumnIndex(Data.CONTACT_ID));
		  if(mailHash.containsKey(id)) {
		    listItems.add(dataNameTable.getString(dataNameTable.getColumnIndex(Data.DISPLAY_NAME)));
		    mailHashname.put(dataNameTable.getString(dataNameTable.getColumnIndex(Data.DISPLAY_NAME)),
		    		(String)mailHash.get(id)); 
		  }
		}

		// カーソルを閉じる
		dataNameTable.close();

		// 連絡先一覧をリストビューで表示
		CustomAdapter adapter = new CustomAdapter(this, 0, listItems);
		address.setAdapter(adapter);

	}
	 
}

