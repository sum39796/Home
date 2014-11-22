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
                // �N���b�N���ꂽ�A�C�e�����擾���܂�
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
		// DATA�\���烁�[���A�h���X��S�Ď擾
		ContentResolver cr = getContentResolver();
		Cursor dataAddressTable = cr.query(
		  Data.CONTENT_URI,
		  null,
		  Data.MIMETYPE + " = ?",
		  new String[]{Email.CONTENT_ITEM_TYPE},
		  null);

		// �n�b�V���ϐ��ɃA�h���X���i�[(id, address)
		HashMap<String, String> mailHash = new HashMap<String, String>();
		while(dataAddressTable.moveToNext()) {
		  mailHash.put(
		    dataAddressTable.getString(
		      dataAddressTable.getColumnIndex(Data.CONTACT_ID)), // ID
		    dataAddressTable.getString(
		      dataAddressTable.getColumnIndex(Data.DATA1))); // ���[���A�h���X
		}

		// �J�[�\�������
		dataAddressTable.close();

		// �\�[�g�������i�[�i�A����ꗗ�� "�ӂ肪��" ���Ń\�[�g�j
		String order_str =
		  " CASE" +
		  " WHEN IFNULL(" + Data.DATA9 + ", '') = ''" + // Data.DATA9��NULL�̏ꍇ�͋󕶎�����
		  " THEN 1 ELSE 0" + // Data.DATA9���󕶎��̃��R�[�h���Ō�ɂ���
		  " END, " + Data.DATA9 + " ," +
		  " CASE" +
		  " WHEN IFNULL(" + Data.DATA7 + ", '') = ''" +
		  " THEN 1 ELSE 0" +
		  " END, " + Data.DATA7;

		// DATA�\����A���於��S�Ď擾
		Cursor dataNameTable = cr.query(
		  Data.CONTENT_URI,
		  null,
		  Data.MIMETYPE + " = ?",
		  new String[]{StructuredName.CONTENT_ITEM_TYPE},
		  order_str);
		mailHashname = new HashMap<String, String>();
		// ���[���A�h���X�����݂���A���悾���𖼑O�i�[�p���X�g�Ɋi�[
		ArrayList<String> listItems = new ArrayList<String>(); // ���O�i�[�p���X�g
		while(dataNameTable.moveToNext()) {
		  String id = dataNameTable.getString(
		    dataNameTable.getColumnIndex(Data.CONTACT_ID));
		  if(mailHash.containsKey(id)) {
		    listItems.add(dataNameTable.getString(dataNameTable.getColumnIndex(Data.DISPLAY_NAME)));
		    mailHashname.put(dataNameTable.getString(dataNameTable.getColumnIndex(Data.DISPLAY_NAME)),
		    		(String)mailHash.get(id)); 
		  }
		}

		// �J�[�\�������
		dataNameTable.close();

		// �A����ꗗ�����X�g�r���[�ŕ\��
		CustomAdapter adapter = new CustomAdapter(this, 0, listItems);
		address.setAdapter(adapter);

	}
	 
}

