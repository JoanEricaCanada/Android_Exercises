package com.example.joanericacanada.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class CrimeFragment extends Fragment {
    private static final String TAG = "CrimeFragment";

    private Crime crime;
    private EditText edtTxtTitle;
    private Button btnDate;
    private CheckBox chkSolved;
    private ImageButton imgBtnPhoto;
    private Button btnDeletePhoto;
    private ImageView imgVwPhoto;
    private Button btnReport;
    private Button btnSuspect;
    private Button btnDial;

    private Uri contactUri;

    public static final String EXTRA_CRIME_ID = "com.example.joanericacanada.criminalintent.crime_id";
    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_IMAGE = "image";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 1;
    private static final int REQUEST_CONTACT = 2;

    public static CrimeFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
        crime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, parent, false);

        //DELETE PHOTO BUTTON
        btnDeletePhoto = (Button) v.findViewById(R.id.delete_photo_button);
        btnDeletePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearPhoto();
            }
        });
        if (crime.getPhoto() != null){
            btnDeletePhoto.setVisibility(View.VISIBLE);
        }

        //DIAL BUTTON
        btnDial = (Button) v.findViewById(R.id.crime_dial_button);
        btnDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_DIAL);
                    i.setData(Uri.parse("tel:" + crime.getContact()));
                    startActivity(i);
            }
        });

        if(crime.getSuspect() != null){
            btnDial.setVisibility(View.VISIBLE);
            btnDial.setText("Contact "+ crime.getSuspect().toString());
        }

        //REPORT BUTTON
        btnReport = (Button) v.findViewById(R.id.crime_report_button);
        btnReport.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject));
                i = Intent.createChooser(i, getString(R.string.send_report));
                startActivity(i);
            }
        });

        //SUSPECT BUTTON
        btnSuspect = (Button) v.findViewById(R.id.crime_suspect_button);
        btnSuspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(i, REQUEST_CONTACT);
            }
        });

        if (crime.getSuspect() != null){
            btnSuspect.setText(crime.getSuspect());
        }
        //TITLE EDIT TEXT
        edtTxtTitle = (EditText)v.findViewById(R.id.crime_title);
        edtTxtTitle.setText(crime.getTitle());
        edtTxtTitle.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(
                    CharSequence c, int start, int before, int count) {
                crime.setTitle(c.toString());
            }

            public void beforeTextChanged(
                    CharSequence c, int start, int count, int after) {
                // This space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // This one too
            }
        });

        imgBtnPhoto = (ImageButton)v.findViewById(R.id.crime_imageButton);
        imgBtnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CrimeCameraActivity.class);
                startActivityForResult(i, REQUEST_PHOTO);
            }
        });

        imgVwPhoto = (ImageView)v.findViewById(R.id.crime_ImageView);
        imgVwPhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Photo p = crime.getPhoto();
                if (p == null)
                    return;
                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                String path = getActivity()
                        .getFileStreamPath(p.getFilename()).getAbsolutePath();
                int orientation = p.getOrientation();
                ImageFragment.newInstance(path, orientation).show(fm, DIALOG_IMAGE);
            }
        });

        PackageManager pm = getActivity().getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) &&
                !pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
            imgBtnPhoto.setEnabled(false);
        }



        //DATE BUTTON
        btnDate = (Button) v.findViewById(R.id.crime_date);
        updateDate();
        btnDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(crime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        //SOLVED? CHECKBOX
        chkSolved = (CheckBox) v.findViewById(R.id.crime_solved);
        chkSolved.setChecked(crime.isSolved());
        chkSolved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                crime.setSolved(isChecked);
            }
        });

        return v;
    }

    public void updateDate(){
        btnDate.setText(new SimpleDateFormat("EEEE, MMM dd,yyyy").format(crime.getDate()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK)
            return;
        if(requestCode == REQUEST_DATE){
            Date dateResult = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            crime.setDate(dateResult);
            updateDate();
        }else if (requestCode == REQUEST_PHOTO) {
            ClearPhoto();
            String filename = data.getStringExtra(CrimeCameraFragment.EXTRA_PHOTO_FILENAME);
            int i = data.getIntExtra(CrimeCameraFragment.EXTRA_PHOTO_ORIENTATION, 0);
            if (filename != null) {
                Photo p = new Photo(filename, i);
                crime.setPhoto(p);
                showPhoto();
                btnDeletePhoto.setVisibility(View.VISIBLE);
            }
        }else if(requestCode == REQUEST_CONTACT){
            contactUri = data.getData();

            /*
            crime.setSuspect(suspect);
            btnSuspect.setText(suspect);
            btnDial.setText("Contact " + suspect);
            btnDial.setVisibility(View.VISIBLE);
            c.close();*/

            String[] queryFields = new String[]{ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts._ID};
            Cursor cursor = getActivity().getContentResolver().query(contactUri, queryFields, null, null, null);

            if(cursor.getCount() == 0){
                cursor.close();
                return;
            }

            cursor.moveToFirst();
            int indexId = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            int indexName = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            String contractId = cursor.getString(indexId);
            String suspect = cursor.getString(indexName);
            crime.setSuspect(suspect);

            Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contractId, null, null);

            while(phones.moveToNext()){
                String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                int type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                switch (type){
                    case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                        break;
                    case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                        crime.setContact(number);
                        break;
                    case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                        break;
                }
            }
            phones.close();
            cursor.close();

            btnSuspect.setText(crime.getSuspect());
            if(crime.getContact() != null){
                btnDial.setVisibility(View.VISIBLE);
            }

        }

    }

    private String getCrimeReport(){
        String solvedString = null;

        if(crime.isSolved())
            solvedString = getString(R.string.crime_report_solved);
        else
            solvedString = getString(R.string.crime_report_unsolved);

        String dateString = new SimpleDateFormat("EEE, MMM dd").format(crime.getDate());
        String suspect = crime.getSuspect();

        if(suspect == null)
            suspect = getString(R.string.crime_report_no_suspect);
        else
            suspect = getString(R.string.crime_report_suspect, suspect);

        String report = getString(R.string.crime_report, crime.getTitle(), dateString, solvedString, suspect);

        return report;

    }

    private void ClearPhoto(){
        if (crime.getPhoto() != null){
            String filePath = getActivity().getFileStreamPath(crime.getPhoto().getFilename()).getAbsolutePath();
            File file = new File(filePath);
            file.delete();
            crime.setPhoto(null);
            PictureUtils.cleanImageView(imgVwPhoto);
        }
    }

    private void showPhoto(){
        Photo p = crime.getPhoto();
        BitmapDrawable bitmapDrawable = null;
        if(p != null){
            String path = getActivity().getFileStreamPath(p.getFilename()).getAbsolutePath();
            bitmapDrawable = PictureUtils.getScaledDrawable(getActivity(), path);

            int orient = p.getOrientation();
            if(orient == CrimeCameraActivity.ORIENTATION_PORTRAIT_INVERTED ||
                    orient == CrimeCameraActivity.ORIENTATION_PORTRAIT_NORMAL){
                bitmapDrawable = PictureUtils.getPortraitDrawable(imgVwPhoto, bitmapDrawable);
            }
            Log.i(TAG, "JPEG saved at " + path);
        }
        imgVwPhoto.setImageDrawable(bitmapDrawable);
    }

    @Override
    public void onStart() {
        super.onStart();
        showPhoto();
    }

    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.get(getActivity()).saveCrimes();
    }

    @Override
    public void onStop() {
        super.onStop();
        PictureUtils.cleanImageView(imgVwPhoto);
    }

}
