package com.codepath.packagetwitter;

/**
 * Created by pratyusha98 on 7/18/17.
 */

import android.app.Activity;

public class ImageUpload extends Activity {
  //  private static final int PICK_IMAGE = 1;
//    private ImageView imgView;
//    private Button upload;
//    private EditText caption;
//    private Bitmap bitmap;
//    private ProgressDialog dialog;
//    public String str;
//
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.image_upload);
//
//        imgView = (ImageView) findViewById(R.id.ImageView);
//        upload = (Button) findViewById(R.id.Upload);
//        caption = (EditText) findViewById(R.id.Caption);
//        upload.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                 str = caption.getText().toString();
//                if (bitmap == null) {
//                    Toast.makeText(getApplicationContext(),
//                            "Please select image", Toast.LENGTH_SHORT).show();
//                } else {
//                    dialog = ProgressDialog.show(ImageUpload.this, "Uploading",
//                            "Please wait...", true);
//                    new ImageUploadTask().execute();
//                }
//            }
//        });
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.imageupload_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.ic_menu_gallery:
//                try {
//                    Intent intent = new Intent();
//                    intent.setType("image/*");
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(
//                            Intent.createChooser(intent, "Select Picture"),
//                            PICK_IMAGE);
//                } catch (Exception e) {
//                    Toast.makeText(getApplicationContext(),
////                            getString(R.string.exception_message),
//                            "didn't work",
//                            Toast.LENGTH_LONG).show();
//                    Log.e(e.getClass().getName(), e.getMessage(), e);
//                }
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case PICK_IMAGE:
//                if (resultCode == Activity.RESULT_OK) {
//                    Uri selectedImageUri = data.getData();
//                    String filePath = null;
//
//                    try {
//                        // OI FILE Manager
//                        String filemanagerstring = selectedImageUri.getPath();
//
//                        // MEDIA GALLERY
//                        String selectedImagePath = getPath(selectedImageUri);
//
//                        if (selectedImagePath != null) {
//                            filePath = selectedImagePath;
//                        } else if (filemanagerstring != null) {
//                            filePath = filemanagerstring;
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Unknown path",
//                                    Toast.LENGTH_LONG).show();
//                            Log.e("Bitmap", "Unknown path");
//                        }
//
//                        if (filePath != null) {
//                            decodeFile(filePath);
//                        } else {
//                            bitmap = null;
//                        }
//                    } catch (Exception e) {
//                        Toast.makeText(getApplicationContext(), "Internal error",
//                                Toast.LENGTH_LONG).show();
//                        Log.e(e.getClass().getName(), e.getMessage(), e);
//                    }
//                }
//                break;
//            default:
//        }
//    }
//
//    class ImageUploadTask extends AsyncTask<Void, Void, String> {
//        @Override
//        protected String doInBackground(Void... unsued) {
//            try {
//                HttpClient httpClient = new DefaultHttpClient();
//                HttpContext localContext = new BasicHttpContext();
//                HttpPost httpPost = new HttpPost(
//                        "https://packagetrackerr.herokuapp.com/parse/");
//
//                MultipartEntity entity = new MultipartEntity(
//                        HttpMultipartMode.BROWSER_COMPATIBLE);
//
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                bitmap.compress(CompressFormat.JPEG, 100, bos);
//                byte[] data = bos.toByteArray();
//                entity.addPart("photoId", new StringBody(getIntent()
//                        .getStringExtra("photoId")));
//                entity.addPart("returnformat", new StringBody("json"));
//                entity.addPart("uploaded", new ByteArrayBody(data,
//                        "myImage.jpg"));
////                entity.addPart("photoCaption", new
////                (str));
//                httpPost.setEntity(entity);
//                HttpResponse response = httpClient.execute(httpPost,
//                        localContext);
//                BufferedReader reader = new BufferedReader(
//                        new InputStreamReader(
//                                response.getEntity().getContent(), "UTF-8"));
//
//                String sResponse = reader.readLine();
//                return sResponse;
//            } catch (Exception e) {
//                if (dialog.isShowing())
//                    dialog.dismiss();
//                Toast.makeText(getApplicationContext(),
//                        "didnt work",
////                        getString(R.string.exception_message),
//                        Toast.LENGTH_LONG).show();
//                Log.e(e.getClass().getName(), e.getMessage(), e);
//                return null;
//            }
//
//            // (null);
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... unsued) {
//
//        }
//
//        @Override
//        protected void onPostExecute(String sResponse) {
//            try {
//                if (dialog.isShowing())
//                    dialog.dismiss();
//
//                if (sResponse != null) {
//                    JSONObject JResponse = new JSONObject(sResponse);
//                    int success = JResponse.getInt("SUCCESS");
//                    String message = JResponse.getString("MESSAGE");
//                    if (success == 0) {
//                        Toast.makeText(getApplicationContext(), message,
//                                Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(getApplicationContext(),
//                                "Photo uploaded successfully",
//                                Toast.LENGTH_SHORT).show();
//                        caption.setText("");
//                    }
//                }
//            } catch (Exception e) {
//                Toast.makeText(getApplicationContext(),
////                        getString(R.string.exception_message),
//                        "NADA",
//                        Toast.LENGTH_LONG).show();
//                Log.e(e.getClass().getName(), e.getMessage(), e);
//            }
//        }
//    }
//
//    public String getPath(Uri uri) {
//        String[] projection = { MediaStore.Images.Media.DATA };
//        Cursor cursor = managedQuery(uri, projection, null, null, null);
//        if (cursor != null) {
//            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
//            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
//            int column_index = cursor
//                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            return cursor.getString(column_index);
//        } else
//            return null;
//    }
//
//    public void decodeFile(String filePath) {
//        // Decode image size
//        BitmapFactory.Options o = new BitmapFactory.Options();
//        o.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(filePath, o);
//
//        // The new size we want to scale to
//        final int REQUIRED_SIZE = 1024;
//
//        // Find the correct scale value. It should be the power of 2.
//        int width_tmp = o.outWidth, height_tmp = o.outHeight;
//        int scale = 1;
////        while (true) {
////            if ( width_tmp &lt; REQUIRED_SIZE &amp;&amp; height_tmp &lt; REQUIRED_SIZE)
////            break;
////            width_tmp /= 2;
////            height_tmp /= 2;
////            scale *= 2;
////        }
//        scale = calculateInSampleSize(o,32,32 );
//        o.inJustDecodeBounds = false;
//        // Decode with inSampleSize
//        BitmapFactory.Options o2 = new BitmapFactory.Options();
//        o2.inSampleSize = scale;
//        bitmap = BitmapFactory.decodeFile(filePath, o2);
//
//        imgView.setImageBitmap(bitmap);
//
//    }
//
//
//    public static int calculateInSampleSize(
//            BitmapFactory.Options options, int reqWidth, int reqHeight) {
//        // Raw height and width of image
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        int inSampleSize = 1;
//
//        if (height > reqHeight || width > reqWidth) {
//
//            final int halfHeight = height / 2;
//            final int halfWidth = width / 2;
//
//            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
//            // height and width larger than the requested height and width.
//            while ((halfHeight / inSampleSize) >= reqHeight
//                    && (halfWidth / inSampleSize) >= reqWidth) {
//                inSampleSize *= 2;
//            }
//        }
//
//        return inSampleSize;
//    }
}