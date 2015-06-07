package com.busyscanner.busyscanner;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThingFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = ThingFragment.class.getSimpleName();
    private static final String ARG_NAME = "name";
    private static final String ARG_EMAIL = "email";
    private static final String ARG_COMPANY = "company";
    private static final String ARG_PHONE = "phone";
    private static final int REQUEST_CODE_ADD_CONTACT = 100;

    private String name;
    private String email;
    private String company;
    private String phone;

    private CardView cardView;
    private Button editButton;
    private Button addToContacts;
    private View resultsLayout;

    public static ThingFragment newInstance(String name, String email, String company, String phone) {
        ThingFragment fragment = new ThingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_EMAIL, email);
        args.putString(ARG_COMPANY, company);
        args.putString(ARG_PHONE, phone);
        fragment.setArguments(args);
        return fragment;
    }

    public ThingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        name = args.getString(ARG_NAME);
        email = args.getString(ARG_EMAIL);
        company = args.getString(ARG_COMPANY);
        phone = args.getString(ARG_PHONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_thing, container, false);

        cardView = (CardView) rootView.findViewById(R.id.card_item);
        editButton = (Button) rootView.findViewById(R.id.edit_button);
        addToContacts = (Button) rootView.findViewById(R.id.add_to_contacts);
        resultsLayout = rootView.findViewById(R.id.result_layout);

        editButton.setOnClickListener(this);
        addToContacts.setOnClickListener(this);

        addDataToCard();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_button:
                EditContactFragment fragment = EditContactFragment.newInstance(name,
                        email, company, phone);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment, EditContactFragment.TAG)
                        .addToBackStack(EditContactFragment.TAG)
                        .commit();
                break;
            case R.id.add_to_contacts:
                newContact();
                break;
        }
    }

    public void newContact(){
        new BizCardResponse(company, email, name, phone).save();
        // Creates a new Intent to insert a contact
        Intent intent = new Intent(android.provider.ContactsContract.Intents.Insert.ACTION);
        // Sets the MIME type to match the Contacts Provider
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);

        if(email != null){intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);}
        if(company != null){intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);}

        startActivityForResult(intent, REQUEST_CODE_ADD_CONTACT);
    }

    private void addDataToCard() {
        TextView nameView = (TextView) cardView.findViewById(R.id.list_name);
        TextView companyView = (TextView) cardView.findViewById(R.id.list_company);
        TextView emailView = (TextView) cardView.findViewById(R.id.list_email);
        TextView phoneView = (TextView) cardView.findViewById(R.id.list_phone);

        if (TextUtils.isEmpty(name)) {
            nameView.setText("");
        } else {
            nameView.setText(name);
        }
        if (TextUtils.isEmpty(company)) {
            companyView.setText("");
        } else {
            companyView.setText(company);
        }
        if (TextUtils.isEmpty(email)) {
            emailView.setText("");
        } else {
            emailView.setText(email);
        }
        if (TextUtils.isEmpty(phone)) {
            phoneView.setText("");
        } else {
            phoneView.setText(phone);
        }
    }
}
