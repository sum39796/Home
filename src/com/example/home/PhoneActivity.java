package com.example.home;

import java.util.ArrayList;
import java.util.HashMap;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PhoneActivity extends Activity {
	
	ListView phone;
	HashMap<String, String> phoneHashname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_phone);
		phone = (ListView)findViewById(R.id.phone);
		setPhoneList();
		phone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                ListView listView = (ListView) parent;
                // �N���b�N���ꂽ�A�C�e�����擾���܂�
                String item =(String) listView.getItemAtPosition(position);
                Uri uri=Uri.parse("tel:" + phoneHashname.get(item));
                Intent i = new Intent(Intent.ACTION_DIAL,uri);
                startActivity(i);
            }
        });
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.phone, menu);
		return true;
	}
	
	public void setPhoneList(){
		// DATA�\����d�b�ԍ���S�Ď擾
		ContentResolver cr = getContentResolver();
		Cursor dataAddressTable = cr.query(
		  Data.CONTENT_URI,
		  null,
		  Data.MIMETYPE + " = ?",
		  new String[]{Phone.CONTENT_ITEM_TYPE},
		  null);

		// �n�b�V���ϐ��ɃA�h���X���i�[(id, address)
		HashMap<String, String> phoneHash = new HashMap<String, String>();
		while(dataAddressTable.moveToNext()) {
		  phoneHash.put(
		    dataAddressTable.getString(
		      dataAddressTable.getColumnIndex(Data.CONTACT_ID)), // ID
		    dataAddressTable.getString(
		      dataAddressTable.getColumnIndex(Data.DATA1))); // �d�b�ԍ�
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
		phoneHashname = new HashMap<String, String>();

		ArrayList<String> listItems = new ArrayList<String>();
		while(dataNameTable.moveToNext()) {
		  String id = dataNameTable.getString(
		    dataNameTable.getColumnIndex(Data.CONTACT_ID));
		  if(phoneHash.containsKey(id)) {
		    listItems.add(dataNameTable.getString(dataNameTable.getColumnIndex(Data.DISPLAY_NAME)));
		    phoneHashname.put(dataNameTable.getString(dataNameTable.getColumnIndex(Data.DISPLAY_NAME)),
		    		(String)phoneHash.get(id)); 
		  }
		}

		// �J�[�\�������
		dataNameTable.close();

		// �A����ꗗ�����X�g�r���[�ŕ\��
		CustomAdapter adapter = new CustomAdapter(this, 0, listItems);
		phone.setAdapter(adapter);

	}

}
