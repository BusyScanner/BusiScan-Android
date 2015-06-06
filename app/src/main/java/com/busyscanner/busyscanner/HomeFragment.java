package com.busyscanner.busyscanner;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * Use for permission of getting image from gallary
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = HomeFragment.class.getSimpleName();
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private Button takePicButton;
    private Button uploadButton;
    private Uri fileUri;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        takePicButton = (Button) rootView.findViewById(R.id.pictureButton);
        uploadButton = (Button) rootView.findViewById(R.id.uploadButton);

        takePicButton.setOnClickListener(this);


        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.uploadButton:
                //TODO
                //choose from gallary or another app that has Images.Media
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivity(intent);

                break;
            case R.id.pictureButton:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

                // start the image capture Intent
                startActivityForResult(cameraIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                break;
        }
    }

    //Suggested for optimizing the image size
    /**private void setFullImageFromFilePath(String imagePath) {
        // Get the dimensions of the View
        int targetW = mSelectedImage.getWidth();
        int targetH = mSelectedImage.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        android.graphics.Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
        mSelectedImage.setImageBitmap(bitmap);
    }**/


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                if (getView() != null) {
                    Snackbar.make(getView(), "Image saved to \n" + fileUri,
                            Snackbar.LENGTH_LONG).show();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // User cancelled the image capture
                if (getView() != null) {
                    Snackbar.make(getView(), getString(R.string.image_capture_cancelled),
                            Snackbar.LENGTH_LONG).show();
                }
            } else {
                // Image capture failed, advise user
                Snackbar.make(getView(), getString(R.string.image_capture_failed),
                        Snackbar.LENGTH_LONG).show();
            }
        }
    }

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        Log.d(TAG, Environment.getExternalStorageState());

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Busiscan");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.e(TAG, "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
}
