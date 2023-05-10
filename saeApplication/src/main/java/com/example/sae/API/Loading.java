package com.example.sae.API;

import android.app.ProgressDialog;
import android.content.Context;

public class Loading {

    private ProgressDialog progressDialog;

    public Loading(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Chargement en cours...");
        progressDialog.setCancelable(false);
    }

    public void showLoading() {
        progressDialog.show();
    }

    // Enleve la boîte de dialogue de chargement
    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
