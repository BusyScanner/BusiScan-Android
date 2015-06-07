package com.busyscanner.busyscanner;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.SocketTimeoutException;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImageUploadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageUploadFragment extends Fragment implements Callback<BizCardResponse>, View.OnClickListener {

    public static final String TAG = ImageUploadFragment.class.getSimpleName();
    private static final String ARG_IMG_URI = "img_uri";
    private MsgFragment msgFragment;
    private File imagePath;
    private CardView cardView;
    private Button editButton;
    private Button addToContacts;
    private View resultsLayout;
    private BizCardResponse cardResponse;
    private static final int REQUEST_CODE_ADD_CONTACT = 100;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param imageUri Full image path in the local file system
     * @return A new instance of fragment ImageUploadFragment.
     */
    public static ImageUploadFragment newInstance(String imageUri) {
        ImageUploadFragment fragment = new ImageUploadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_IMG_URI, imageUri);
        fragment.setArguments(args);
        return fragment;
    }

    public ImageUploadFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePath = new File(getArguments().getString(ARG_IMG_URI));

        msgFragment = new MsgFragment();
        getChildFragmentManager().beginTransaction()
                .replace(R.id.msgfragment_container, msgFragment)
                .commit();
        uploadImage();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_image_upload, container, false);

        cardView = (CardView) rootView.findViewById(R.id.card_item);
        editButton = (Button) rootView.findViewById(R.id.edit_button);
        addToContacts = (Button) rootView.findViewById(R.id.add_to_contacts);
        resultsLayout = rootView.findViewById(R.id.result_layout);

        if (cardResponse != null) {
            resultsLayout.setVisibility(View.GONE);
        }

        editButton.setOnClickListener(this);
        addToContacts.setOnClickListener(this);

        return rootView;
    }

    private void uploadImage() {
        ImageProcessingApi imageProcessingApi = Access.getInstance().getImageProcessingApi();

        msgFragment.pushBusy();

        Bitmap bm = BitmapFactory.decodeFile(imagePath.getPath());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] byteArrayImage = baos.toByteArray();
        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        BizCardRequest request = new BizCardRequest("Testing encoded image string thing", encodedImage);
//        imageProcessingApi.uploadImageBody(request, this);

        TypedFile typedFile = new TypedFile("image/jpg", imagePath);
        String desc = "TEST!";
        imageProcessingApi.uploadCardImage(typedFile, desc, this);
    }

    @Override
    public void failure(RetrofitError error) {
        msgFragment.popBusy();
        error.printStackTrace();
        if (getActivity() != null) {
            if (error.getCause() instanceof SocketTimeoutException) {
                msgFragment.setMsg("Timed out!");
            } else {
                msgFragment.setMsg(error.toString());
            }
        }
    }

    /**
     * Successful HTTP response.
     *
     * @param bizCardResponse
     * @param response
     */
    @Override
    public void success(BizCardResponse bizCardResponse, Response response) {
        msgFragment.popBusy();
        cardResponse = bizCardResponse;
        if (cardResponse.hasNullFields()) {
            Toast.makeText(getActivity(), "Failed to read card!", Toast.LENGTH_LONG).show();
            HomeFragment fragment = HomeFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return;
        }
        if (getView() != null) {
            addDataToCard(bizCardResponse);
            resultsLayout.setVisibility(View.VISIBLE);
        }
    }

    private void addDataToCard(BizCardResponse card) {
        TextView nameView = (TextView) cardView.findViewById(R.id.list_name);
        TextView companyView = (TextView) cardView.findViewById(R.id.list_company);
        TextView emailView = (TextView) cardView.findViewById(R.id.list_email);
        TextView phoneView = (TextView) cardView.findViewById(R.id.list_phone);

        if (TextUtils.isEmpty(card.getFullname())) {
            nameView.setText("");
        } else {
            nameView.setText("Name: " + card.getFullname());
        }
        if (TextUtils.isEmpty(card.getCompany())) {
            companyView.setText("");
        } else {
            companyView.setText("Company: " + card.getCompany());
        }
        if (TextUtils.isEmpty(card.getEmail())) {
            emailView.setText("");
        } else {
            emailView.setText("Email: " + card.getEmail());
        }
        if (TextUtils.isEmpty(card.getPhone())) {
            phoneView.setText("");
        } else {
            phoneView.setText("Phone: " + card.getPhone());
        }
    }

    //
    public void newContact(BizCardResponse card){
        card.save();
        // Creates a new Intent to insert a contact
        Intent intent = new Intent(android.provider.ContactsContract.Intents.Insert.ACTION);
        // Sets the MIME type to match the Contacts Provider
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

        intent.putExtra(ContactsContract.Intents.Insert.NAME, card.getFullname());
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, card.getPhone());

        if(card.getEmail() != null){intent.putExtra(ContactsContract.Intents.Insert.EMAIL, card.getEmail());}
        if(card.getCompany() != null){intent.putExtra(ContactsContract.Intents.Insert.COMPANY, card.getCompany());}

        startActivityForResult(intent, REQUEST_CODE_ADD_CONTACT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ADD_CONTACT) {
            if (resultCode == Activity.RESULT_OK) {
                Snackbar.make(getView(), "Contact added successfully", Snackbar.LENGTH_LONG).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Snackbar.make(getView(), "Contact not added", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_button:
                EditContactFragment fragment = EditContactFragment.newInstance(cardResponse.getFullname(),
                        cardResponse.getEmail(), cardResponse.getCompany(), cardResponse.getPhone());
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(EditContactFragment.TAG)
                        .commit();
                break;
            case R.id.add_to_contacts:
                newContact(cardResponse);
                break;
        }
    }
}
