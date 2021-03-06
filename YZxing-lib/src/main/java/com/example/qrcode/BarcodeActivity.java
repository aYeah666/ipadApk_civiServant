package com.example.qrcode;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import java.util.EnumMap;
import java.util.Map;

/**
 * Create by rain
 * <p>
 * DATE:18-10-26
 * <p>
 * Describe:
 **/
public class BarcodeActivity extends Activity {
    private static final String TAG = "BarcodeActivity";
    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    private static final int CODE_TYPE_QR_CODE = 0x11;

 //   private int mType = CODE_TYPE_QR_CODE;

    private EditText mInputContentView;
    private Button mEncodeView;
    private ImageView mBarcodeImageView;

    private Bitmap bitmap;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private int smallerDimension;
    private int barcodeImageWidth;
    private int barcodeImageHeight;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_barcode);
        mInputContentView = findViewById(R.id.et_input_content);
        mEncodeView = findViewById(R.id.bt_encode);
        mEncodeView.setOnClickListener(mEncodeListener);
        mBarcodeImageView = findViewById(R.id.barcode_image);


        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);
        int width = displaySize.x;
        int height = displaySize.y;
        smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 7 / 8;
        barcodeImageWidth = smallerDimension;
        barcodeImageHeight = smallerDimension;
        Log.e(TAG, "onCreate: smallerDimension = " + smallerDimension);
    }

    private View.OnClickListener mEncodeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
         //   mType = CODE_TYPE_QR_CODE;
            barcodeImageHeight = smallerDimension;
            String text = mInputContentView.getText().toString();
            if (TextUtils.isEmpty(text)) {
                Toast.makeText(BarcodeActivity.this, "??????????????????!!", Toast.LENGTH_SHORT).show();
                return;
            }
            encode(text);
        }
    };

    private void encode(String content) {
        Log.e(TAG, "encode: content = " + content);
        if (content == null) {
            return;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(content);
        if (encoding != null) {
            hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE
                    , barcodeImageWidth, barcodeImageHeight, hints);
            int width = result.getWidth();
            int height = result.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
                }
            }

            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            mHandler.post(mUpdateImageRunnable);
        } catch (Exception iae) {
            // Unsupported format
            Log.e(TAG, "encode: " + iae.getMessage());
        }
    }


    private Runnable mUpdateImageRunnable = new Runnable() {
        @Override
        public void run() {
            mBarcodeImageView.setImageBitmap(bitmap);
        }
    };


    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }
}
