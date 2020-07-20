package com.example.login;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmergencyContacts extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    String list="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);

        firebaseAuth = FirebaseAuth.getInstance();

        final Intent intent = new Intent(EmergencyContacts.this, ViewContacts.class);
        // Get listview checkbox.
        final ListView listViewWithCheckbox = (ListView)findViewById(R.id.list_view_with_checkbox);

        // Initiate listview data.
        final List<ListViewItemDTO> initItemList = this.getInitViewItemDtoList();

        final String fl[]=new String[50];
        for(int i=0;i<fl.length;i++) {
            fl[i] = null;
        }
        final String numlist[]=new String[50];
        for(int i=0;i<numlist.length;i++) {
            numlist[i] = null;
        }
        // Create a custom list view adapter with checkbox control.
        final ListViewItemCheckboxBaseAdapter listViewDataAdapter = new ListViewItemCheckboxBaseAdapter(getApplicationContext(), initItemList);

        listViewDataAdapter.notifyDataSetChanged();

        // Set data adapter to list view.
        listViewWithCheckbox.setAdapter(listViewDataAdapter);

        // When list view item is clicked.
        listViewWithCheckbox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long l) {
                // Get user selected item.
                Object itemObject = adapterView.getAdapter().getItem(itemIndex);

                // Translate the selected item to DTO object.
                ListViewItemDTO itemDto = (ListViewItemDTO)itemObject;

                // Get the checkbox.
                CheckBox itemCheckbox = (CheckBox) view.findViewById(R.id.list_view_item_checkbox);

                // Reverse the checkbox and clicked item check state.
                if(itemDto.isChecked())
                {
                    itemCheckbox.setChecked(false);
                    itemDto.setChecked(false);

                }else
                {
                    itemCheckbox.setChecked(true);
                    itemDto.setChecked(true);
                }

                //Toast.makeText(getApplicationContext(), "select item text : " + itemDto.getItemText(), Toast.LENGTH_SHORT).show();
            }
        });


        //lv = (ListView) findViewById(R.id.lv);
       Button  btnselect = (Button) findViewById(R.id.select);
       Button  btndeselect = (Button) findViewById(R.id.deselect);
       Button  btnnext = (Button) findViewById(R.id.next);
       Button  btview = (Button) findViewById(R.id.view);
       final TextView tv=(TextView)findViewById(R.id.textView);

        btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int size = initItemList.size();
                String display = "";
                for (int i = 0; i < size; i++) {
                    ListViewItemDTO dto = initItemList.get(i);

                    if (dto.isChecked()) {
                        dto.setChecked(true);
                        String check=dto.getItemText();
                        String check1=dto.getNum();
                        for(int j=0;j<fl.length;j++) {
                            if(fl[j]!=check) {
                                fl[i] = check;
                                numlist[i] = check1;
                            }
                        }
                    }


                }
                for(int i=0;i<numlist.length;i++) {
                    if(numlist[i]!=null)
                    {
                        display = display+fl[i]+","+numlist[i]+"  ";
                    }
                }
                tv.setText(display);
            }


        });

       btndeselect.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                 int size = initItemList.size();

                for(int i=0;i<size;i++)
                {
                    ListViewItemDTO dto = initItemList.get(i);
                    dto.setChecked(false);
                }
               tv.setText("");

               for(int i=0;i<fl.length;i++) {
                   fl[i] = null;
               }

               for(int i=0;i<numlist.length;i++) {
                   numlist[i] = null;
               }
                listViewDataAdapter.notifyDataSetChanged();

           }

       });

       btnnext.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
               //FirebaseUser user = firebaseAuth.getCurrentUser();

               final DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());

                   String st="";
                   final HashMap<String, String> contacts = new HashMap <String, String>();
                   for (int i = 0; i < fl.length; i++) {
                       if (numlist[i] != null && fl[i] != null) {
                           contacts.put("Number:" + numlist[i], "Name :" + fl[i]);
                           //myRef.setValue(contacts.toString());
                           //myRef.setValue(c+ontacts);
                       }
                   }


                           try {
                               myRef.child("contacts").setValue(contacts);
                           } catch (Exception e) {
                               e.printStackTrace();
                           finish();
                       }


                   String stb=(contacts.keySet().toString());
                   stb = stb.replace("[", "");
                   stb = stb.replace("]", "");
                   String[] numbers = stb.split(",");

                   for(int i    =0;i<numbers.length;i++){
                       String[] num = numbers[i].split(":");
                       if(i < numbers.length-1){
                           list = list+num[1]+';';
                       }else{
                           list = list+num[1];
                       }

                   }
                   tv.setText(list);


                   //startActivity(i);


           }
       });

        btview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


               String loc=getIntent().getExtras().getString("Location");
               /* Intent intent1 = new Intent(android.content.Intent.ACTION_VIEW);
                intent1.putExtra("address", list);
                // here you can multiple numbers
                intent1.putExtra("sms_body", "Help!!!! PLEASE SEND MY COORDINATES TO POLICE:: "+loc);
                intent1.setType("vnd.android-dir/mms-sms");
*/
               /*
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+list));
                smsIntent.putExtra("sms_body", "Help!!!! PLEASE SEND MY COORDINATES TO POLICE::"+loc);
                startActivity(smsIntent);
               0      */
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    String[] nums = list.split(";");
                    for(int i=0; i<nums.length;i++) {
                        smsManager.sendTextMessage(nums[i], null, "Help!!!! PLEASE SEND MY COORDINATES TO POLICE::" + loc, null, null);
                        Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                            Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }


            }
        });

    }


    // Return an initialize list of ListViewItemDTO.
    private List<ListViewItemDTO> getInitViewItemDtoList()
    {    //ListViewItemDTO name = new ListViewItemDTO();
         String name[]=new String[50];
        String phonenumber[]=new String[50];
        int length ;
         int a=0;
        try {
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

            while (phones.moveToNext()) {
                name[a] = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
               phonenumber[a] = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

               a++;

            }
            phones.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        List<ListViewItemDTO> ret = new ArrayList<ListViewItemDTO>();

        length=a;

        for(int i=0;i<length;i++)
        {
            String itemText = name[i];
            String number=phonenumber[i];
            ListViewItemDTO dto = new ListViewItemDTO();
            dto.setChecked(false);
            dto.setItemText(itemText);
            dto.setNum(number);

            ret.add(dto);
        }

        return ret;
    }

}



