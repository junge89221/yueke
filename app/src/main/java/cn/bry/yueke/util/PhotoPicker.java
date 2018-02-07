package cn.bry.yueke.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.widget.Toast;


import cn.bry.yueke.SellerApplication;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class PhotoPicker {

    private static final int REQUEST_TAKE_PHOTO_PERMISSIONS = 1005;
    private static final int REQUEST_FILE_PERMISSIONS = 1006;

    private static final int REQUEST_PHOTO_FROM_CAMERA = 1000;
    private static final int REQUEST_PHOTO_FROM_GALLERY = 1001;
    private static final int REQUEST_PHOTO_FROM_CROP = 1002;

    private static String photoFilePath;
    private static PhotoPickerCallBack pickerCallBack;

    public interface PhotoPickerCallBack {
        void callBack(String photoPath);

        void onCancel();
    }

    public static void takePhoto(Activity activity, PhotoPickerCallBack callBack) {
        pickerCallBack = callBack;
        if (requestTakePhotoPermissions(activity)) {
            try {
                File photoFile = createPhotoFile(activity);
                photoFilePath = photoFile.getAbsolutePath();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri photoUri = FileProvider.getUriForFile(activity, activity.getApplication().getPackageName() + ".fileprovider", photoFile);
                intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) { // 暂时设定为5.0以下可能出现 uri permission denied（如果以上出现，则放开判断）
                    List<ResolveInfo> resolveInfoList = activity.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : resolveInfoList) {
                        String packageName = resolveInfo.activityInfo.packageName;
                        activity.grantUriPermission(packageName, photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                }
                activity.startActivityForResult(intent, REQUEST_PHOTO_FROM_CAMERA);
            } catch (IOException e) {
                ToastUtils.showToast(activity, "图片创建失败！", Toast.LENGTH_SHORT).show();
            }
        } else {
            pickerCallBack.onCancel();
        }
    }

    public static void pickPhoto(Activity activity, PhotoPickerCallBack callBack) {
        pickerCallBack = callBack;
        if (requestPickPhotoPermission(activity)) {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            activity.startActivityForResult(intent, REQUEST_PHOTO_FROM_GALLERY);
        } else {
            pickerCallBack.onCancel();
        }
    }

    public static void cropPhoto(Activity activity, Uri uriRes, int aspectX, int aspectY, PhotoPickerCallBack callBack) {
        pickerCallBack = callBack;
        try {
            File photoFile = createPhotoFile(activity);
            photoFilePath = photoFile.getAbsolutePath();
            Uri photoUri = Uri.fromFile(photoFile);
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uriRes, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", aspectX);
            intent.putExtra("aspectY", aspectY);
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) { // 暂时设定为5.0以下可能出现 uri permission denied（如果以上出现，则放开判断）
                List<ResolveInfo> resolveInfoList = activity.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resolveInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    activity.grantUriPermission(packageName, photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
            }
            activity.startActivityForResult(intent, REQUEST_PHOTO_FROM_CROP);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void onRequestPermissionsResult(Activity activity, int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO_PERMISSIONS:
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        ToastUtils.showToast(activity, "没有拍照权限或sdcard写入权限！", Toast.LENGTH_SHORT).show();
                        pickerCallBack.onCancel();
                        return;
                    }
                }
                takePhoto(activity, pickerCallBack);
                break;
            case REQUEST_FILE_PERMISSIONS:
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        ToastUtils.showToast(activity, "没有sdcard读取权限！", Toast.LENGTH_SHORT).show();
                        pickerCallBack.onCancel();
                        return;
                    }
                }
                pickPhoto(activity, pickerCallBack);
                break;
        }
    }

    public static void onActivityResult(Context context, int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PHOTO_FROM_CAMERA:
                case REQUEST_PHOTO_FROM_CROP:
                    compressPicture(context, photoFilePath);
                    break;
                case REQUEST_PHOTO_FROM_GALLERY:
                    Uri uri = data.getData();
                    try {
                        Cursor cursor = SellerApplication.getApplication().getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                        assert cursor != null;
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                        photoFilePath = cursor.getString(columnIndex);
                        cursor.close();
                    } catch (Exception e) {
                        photoFilePath = uri.getPath();
                    };
                    compressPicture(context, photoFilePath);
                    break;
            }

        } else {
            if (requestCode == REQUEST_PHOTO_FROM_CAMERA || requestCode == REQUEST_PHOTO_FROM_GALLERY || requestCode == REQUEST_PHOTO_FROM_CROP) {
                pickerCallBack.onCancel();
            }
        }
    }

    private static String compressPicturePath;

    public static void compressPicture(final Context context, final String path) {
        Luban.with(context)
                .load(path)                                   // 传人要压缩的图片列表
                .ignoreBy(100)                                  // 忽略不压缩图片的大小
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        compressPicturePath = file.getAbsolutePath();
                        if (compressPicturePath == null) {
                            pickerCallBack.callBack(photoFilePath);
                        } else {
                            pickerCallBack.callBack(compressPicturePath);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }).launch();
    }

    private static boolean requestTakePhotoPermissions(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            activity.getWindow().setWindowAnimations(android.R.style.Animation);
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA},
                    REQUEST_TAKE_PHOTO_PERMISSIONS);
            return false;
        } else {
            return true;
        }
    }

    private static boolean requestPickPhotoPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            activity.getWindow().setWindowAnimations(android.R.style.Animation);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_FILE_PERMISSIONS);
            return false;
        } else return true;
    }

    private static File createPhotoFile(Context context) throws IOException {
        @SuppressLint("SimpleDateFormat")
        String fileName = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
        File directory = getPhotosDirectory(context);
        return File.createTempFile(fileName, ".png", directory);
    }

    private static File getPhotosDirectory(Context context) {
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File sellerDirectory = new File(directory, "yishengyue");
        if (sellerDirectory.exists() || sellerDirectory.mkdirs()) {
            return sellerDirectory;
        } else return context.getCacheDir();
    }

    public static void clear() {
        photoFilePath = null;
        pickerCallBack = null;
    }
}
