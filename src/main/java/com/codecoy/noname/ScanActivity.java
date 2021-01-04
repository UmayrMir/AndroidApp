package com.codecoy.noname;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.codecoy.noname.databinding.ActivityScanBinding;
import com.google.android.material.button.MaterialButton;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import java.util.List;

public class ScanActivity extends AppCompatActivity {
    CompoundBarcodeView barcodeView;
    private static final String TAG = "ScanActivity";
    private Dialog dialog;
    private ActivityScanBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_scan);
        barcodeView = (CompoundBarcodeView) findViewById(R.id.barcode_scanner);
        barcodeView.decodeContinuous(callback);
        barcodeView.setStatusText("");
        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setTitle("Scan Qr Code");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }


        dialog = new Dialog(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        barcodeView.resume();

    }

    @Override
    protected void onPause()
    {
        super.onPause();

        barcodeView.pause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    private BarcodeCallback callback = new BarcodeCallback()
    {
        @Override
        public void barcodeResult(BarcodeResult result)
        {
            if (result.getText() != null)
            {
                barcodeView.setStatusText(result.getText());

                showAlertDialog(result.getText());
                Log.i("Scan", "barcodeResult: "+result.getText());
                //tvscanResult.setText("Data found: " + result.getText());
            }else {
                Log.i("TAG", "barcodeResult: "+result.getText());
            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints)
        {
            Log.i("TAG", "possibleResultPoints: "+resultPoints.size());
            System.out.println("Possible Result points = " + resultPoints);
        }
    };

    private void showAlertDialog(String text) {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.result_layout);
        dialog.setCancelable(false);
        dialog.show();

        TextView result_tv=dialog.findViewById(R.id.result_tv);
        result_tv.setText(text);

        MaterialButton ok_btn=dialog.findViewById(R.id.ok_btn);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}