package com.busyscanner.busyscanner;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditContactFragment extends Fragment {
    public static final String TAG = EditContactFragment.class.getSimpleName();
    private static final String ARG_NAME = "name";
    private static final String ARG_EMAIL = "email";
    private static final String ARG_COMPANY = "company";
    private static final String ARG_PHONE = "phone";

    private EditText nameEdit;
    private EditText emailEdit;
    private EditText companyEdit;
    private EditText phoneEdit;
    private Button setButton;

    private String name;
    private String email;
    private String company;
    private String phone;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param name Parameter 1.
     * @param email Parameter 2.
     * @return A new instance of fragment EditContactFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditContactFragment newInstance(String name, String email, String company, String phone) {
        EditContactFragment fragment = new EditContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_EMAIL, email);
        args.putString(ARG_COMPANY, company);
        args.putString(ARG_PHONE, phone);
        fragment.setArguments(args);
        return fragment;
    }

    public EditContactFragment() {
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

        View rootView = inflater.inflate(R.layout.fragment_edit_contact, container, false);
        nameEdit = (EditText) rootView.findViewById(R.id.edit_name);
        emailEdit = (EditText) rootView.findViewById(R.id.edit_email);
        companyEdit = (EditText) rootView.findViewById(R.id.edit_company);
        phoneEdit = (EditText) rootView.findViewById(R.id.edit_phone);
        setButton = (Button) rootView.findViewById(R.id.set_button);

        nameEdit.setText(name);
        emailEdit.setText(email);
        companyEdit.setText(company);
        phoneEdit.setText(phone);

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThingFragment fragment = ThingFragment.newInstance(nameEdit.getText().toString(),
                        emailEdit.getText().toString(),
                        companyEdit.getText().toString(), phoneEdit.getText().toString());
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment, ThingFragment.TAG)
                        .addToBackStack(ThingFragment.TAG)
                        .commit();
            }
        });

        return rootView;
    }


}
