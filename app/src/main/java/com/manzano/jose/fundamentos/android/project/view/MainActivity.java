package com.manzano.jose.fundamentos.android.project.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.google.gson.Gson;
import com.manzano.jose.fundamentos.android.project.model.Face;
import com.manzano.jose.fundamentos.android.project.model.FaceRectangle;
import com.manzano.jose.fundamentos.android.project.R;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private final int PICK_IMAGE = 1;
    private ProgressDialog detectionProgressDialog;
    private Context context;
    private String jsonResponse;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(
                        intent, "Select Picture"), PICK_IMAGE);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CardViewActivity.class);
                intent.putExtra("jsonResponse", jsonResponse);
                startActivity(intent);
            }
        });
        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) fab.getLayoutParams();
        //p.setAnchorId(View.NO_ID);
        fab.setLayoutParams(p);
        fab.setVisibility(View.GONE);
        detectionProgressDialog = new ProgressDialog(this);
        detectionProgressDialog.setMessage("Procesando Imagen");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), uri);
                Bitmap bitmap2 = null;
                if (bitmap.getByteCount() > 1000000) {
                    bitmap2 = getResizedBitmap(bitmap, 1280);
                }
                ImageView imageView = findViewById(R.id.imageView1);
                imageView.setImageBitmap(bitmap2 == null ? bitmap : bitmap2);
                detectAndFrame(bitmap2 == null ? bitmap : bitmap2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void detectAndFrame(Bitmap bitmap) throws IOException {
        detectionProgressDialog.show();
        File file = saveBitmap(bitmap, this.getCacheDir().getPath() + Calendar.getInstance().getTimeInMillis());
        doRequest(file, bitmap);
    }

    private File saveBitmap(Bitmap bitmap, String path) {
        File file = null;
        if (bitmap != null) {
            file = new File(path);
            try {
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(path);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream); //
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public void doRequest(File file, final Bitmap bitmap) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"),
                file);

        Request request = new Request.Builder()
                .url("https://westcentralus.api.cognitive.microsoft.com/face/v1.0/detect?returnFaceId=true&returnFaceLandmarks=false&returnFaceAttributes=age,gender,headPose,smile,facialHair,glasses,emotion,hair,makeup,occlusion,accessories,blur,exposure,noise")
                .post(body)
                .addHeader("Ocp-Apim-Subscription-Key", "1d88f949af3443ea8cc16b7146bd7501")
                .addHeader("Content-Type", "application/json")
                .addHeader("cache-control", "no-cache")
                .build();

        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                detectionProgressDialog.dismiss();
                Log.e(MainActivity.class.getName(), "Falló Request");
            }

            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    detectionProgressDialog.dismiss();
                    Log.e(MainActivity.class.getName(), "Falló Request");
                } else {
                    jsonResponse = response.body().string();
                    if (jsonResponse.equals("[]")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                                alertDialog.setTitle("Advertencia");
                                alertDialog.setMessage("No se ha detectado rostro en la imagen, por favor intente con otra imagen");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                detectionProgressDialog.dismiss();
                                alertDialog.show();
                            }
                        });

                    } else {
                        final Face[] tempFaces = (new Gson()).fromJson(jsonResponse, Face[].class);
                        Log.d("print", jsonResponse);
                        final ImageView imageView = findViewById(R.id.imageView1);
                        runOnUiThread(new Runnable() {
                            @SuppressLint("RestrictedApi")
                            @Override
                            public void run() {
                                FloatingActionButton fab = findViewById(R.id.fab);
                                RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) fab.getLayoutParams();
                                fab.setLayoutParams(p);
                                fab.setVisibility(View.VISIBLE);
                                imageView.setImageBitmap(drawFaceRectanglesOnBitmap(bitmap, tempFaces));
                                bitmap.recycle();
                                detectionProgressDialog.dismiss();
                            }
                        });
                    }


                }
            }
        });

    }

    private static Bitmap drawFaceRectanglesOnBitmap(Bitmap originalBitmap, Face[] faces) {
        Bitmap bitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        if (faces != null) {
            for (Face face : faces) {
                FaceRectangle faceRectangle = face.getFaceRectangle();
                canvas.drawRect(
                        faceRectangle.getLeft(),
                        faceRectangle.getTop(),
                        faceRectangle.getLeft() + faceRectangle.getWidth(),
                        faceRectangle.getTop() + faceRectangle.getHeight(),
                        paint);
            }
        }
        return bitmap;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image,
                width,
                height,
                true);
    }
}
