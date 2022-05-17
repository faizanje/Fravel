package com.example.fravell;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fravell.Models.CartItem;
import com.example.fravell.Models.Review;
import com.example.fravell.Utils.Constants;
import com.example.fravell.Utils.DBUtils;
import com.example.fravell.Utils.DialogUtil;
import com.example.fravell.Utils.FileUtils;
import com.example.fravell.Utils.FirebaseUtils;
import com.example.fravell.databinding.ActivityWriteReviewScreenBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class WriteReviewScreen extends AppCompatActivity {

    Button submitrev;
    //    Order order;
    CartItem cartItem;
    ActivityResultLauncher<Intent> someActivityResultLauncher;
    File selectedFile;
    ActivityWriteReviewScreenBinding binding;
    Review review = new Review();
    String orderId;
    int cartItemIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityWriteReviewScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cartItem = (CartItem) getIntent().getSerializableExtra(Constants.KEY_CART_ITEM);
        orderId = getIntent().getStringExtra(Constants.KEY_ORDER_ID);
        cartItemIndex = getIntent().getIntExtra(Constants.KEY_CART_ITEM_INDEX, 0);
        submitrev = findViewById(R.id.submitreviewbtn);
        submitrev.setOnClickListener(view -> {

            float rating = binding.ratingBar2.getRating();
            String reviewStr = binding.etReview.getText().toString();
            if (reviewStr.isEmpty()) {
                Toast.makeText(this, "Review cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            review
                    .setTimeInMillis(System.currentTimeMillis())
                    .setUserId(FirebaseAuth.getInstance().getUid())
                    .setUserName(DBUtils.readLoggedInUser().name)
                    .setOrderId(cartItem.getProduct().getId())
                    .setRating(rating)
                    .setReview(reviewStr);
            submitFeedback();
//            Intent intent = new Intent(WriteReviewScreen.this, ProductDetailPage.class);
//            Toast.makeText(getApplicationContext(), "Review Submitted", Toast.LENGTH_LONG).show();
//            startActivity(intent);
        });

        binding.btnPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WriteReviewScreenPermissionsDispatcher.pickFileWithPermissionCheck(WriteReviewScreen.this);
            }
        });
    }

    private void submitFeedback() {
        if (selectedFile != null) {
            uploadFile();
        } else {
            submitFeedbackNow();
        }
    }


    private void uploadFile() {


        DialogUtil.showSimpleProgressWithTextDialog(this, "Uploading file: 0%");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            StorageReference storageRef = FirebaseStorage
                    .getInstance()
                    .getReference()
                    .child("reviews-images")
                    .child(user.getUid())
                    .child(selectedFile.getName());

            UploadTask uploadTask = storageRef.putFile(Uri.fromFile(selectedFile));

            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    DialogUtil.setTextForTextDialog("Uploading file: " + ((int) progress) + "%");
                    Log.d(Constants.TAG, "Upload is " + progress + "% done");
                }
            });

            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return storageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        DialogUtil.setTextForTextDialog("Saving review");
                        review.setImageUrl(downloadUri.toString());
                        submitFeedbackNow();
                        Log.d(Constants.TAG, "onComplete: " + downloadUri);
                    } else {
                        Toast.makeText(WriteReviewScreen.this, "Not successful", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void submitFeedbackNow() {
        cartItem.setFeedbackLeft(true);
        DatabaseReference reference = FirebaseUtils.getOrdersReference()
                .child(orderId)
                .child("cartItemArrayList")
                .child(String.valueOf(cartItemIndex));

        Log.d(Constants.TAG, "submitFeedbackNow: " + reference);
        Log.d(Constants.TAG, "submitFeedbackNow cartItem: " + cartItem);


        reference.setValue(cartItem);

        DatabaseReference databaseReference = FirebaseUtils.getReviewsReference()
                .child(cartItem.getProduct().getId())
                .child(FirebaseAuth.getInstance().getUid());
        review.setReviewId(databaseReference.getKey());
        databaseReference.setValue(review)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        DialogUtil.closeProgressDialog();
                        startActivity(new Intent(WriteReviewScreen.this, HomeScreen.class));
                        finish();
                        finish();
                    }
                });
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void pickFile() {

        ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(128)            //Final image size will be less than 1 MB(Optional)
                .start();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        WriteReviewScreenPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK

            Uri correctUri = null;
            try {
                correctUri = FileUtils.getFilePathFromUri(WriteReviewScreen.this, data.getData());
                selectedFile = new File(correctUri.getPath());

                binding.ivImage.setImageURI(correctUri);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(Constants.TAG, "onActivityResult: " + e.getMessage());
            }


            // Use Uri object instead of File to avoid storage permissions

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(WriteReviewScreen.this)
                .setMessage("Phone and camera permissions are required to send SMS and scan QR code respectively.")
                .setPositiveButton("Allow", (dialog, button) -> request.proceed())
                .setNegativeButton("Deny", (dialog, button) -> request.cancel())
                .show();
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showDeniedForCamera() {
        Toast.makeText(WriteReviewScreen.this, "Permissions denied", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showNeverAskForCamera() {
        Toast.makeText(WriteReviewScreen.this, "Permissions required for camera and phone are denied. Please allow them from your phone settings", Toast.LENGTH_SHORT).show();
    }
}