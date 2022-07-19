package com.origin.zapgrupos.until.ads.googleBilling;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.origin.zapgrupos.until.ads.Analytics;

import java.util.List;

public class PurchasesUpdatedListenerCuston implements PurchasesUpdatedListener {
    PurchasesUpdatedListenerCuston(){

    }
    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> purchases) {
        Log.i("BILLING (A):", billingResult.getDebugMessage());

        if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
            for(Purchase purchase : purchases){
                handlePurchase( purchase );
            }
        }

        if(purchases != null){
            Log.i("BILLING (A): getDeveloperPayload ", purchases.get( 0 ).getDeveloperPayload());
            Log.i("BILLING (A): getOrderId ", purchases.get( 0 ).getOrderId());
            Log.i("BILLING (A): getPurchaseToken ", purchases.get( 0 ).getPurchaseToken());
            Log.i("BILLING (A): getOriginalJson", purchases.get( 0 ).getOriginalJson());
            Log.i("BILLING (A): getSignature", purchases.get( 0 ).getSignature());
            Log.i("BILLING (A): getProducts", purchases.get( 0 ).getProducts().get( 0 ));
        }



        //Log.i("BILLING (A):", String.valueOf( list.size() ) );
    }

    public static void handlePurchase(Purchase purchase) {
        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
            Billing.setHiddenAds(false);
            Billing.setPurchase(purchase);
            if (!purchase.isAcknowledged()) {
                AcknowledgePurchaseParams acknowledgePurchaseParams =
                        AcknowledgePurchaseParams.newBuilder()
                                .setPurchaseToken(purchase.getPurchaseToken())
                                .build();
                Billing.billingClient.acknowledgePurchase(acknowledgePurchaseParams, new AcknowledgePurchaseResponseListenerCuston());
            }
        }
    }




}
