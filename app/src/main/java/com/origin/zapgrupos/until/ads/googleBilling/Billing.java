package com.origin.zapgrupos.until.ads.googleBilling;

import android.app.Activity;
import android.util.Log;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.ProductDetailsResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryPurchasesParams;
import com.google.common.collect.ImmutableList;

import java.util.List;

public final class Billing {
    private static Activity activity;
    private static PurchasesUpdatedListener purchasesUpdatedListener;
    public static BillingClient billingClient;
    private static List<ProductDetails> products;
    private static Boolean hiddenAds = true;
    private static Purchase purchase;
    private static Boolean billingWorking = true;

    public Billing() {
    }

    public static void setActivity(Activity activity){
        Billing.activity = activity;
    }

    public static void run(){
        startClient();
        startConnection();
    }

    private static void startClient() {

        purchasesUpdatedListener = new PurchasesUpdatedListenerCuston();

        billingClient = BillingClient.newBuilder( activity.getApplicationContext() )
                .setListener( purchasesUpdatedListener )
                .enablePendingPurchases()
                .build();
    }

    private static void startConnection(){
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                Log.i("BILLING (B):", String.valueOf( billingResult.getResponseCode() ) );
                if (billingResult.getResponseCode() ==  BillingClient.BillingResponseCode.OK) {
                    Log.i("BILLING (B):", "SUSSESS" );
                    confirmPurchaseAssignature();
                }else{
                    Log.i("BILLING (B):", "billingWorking false");
                    billingWorking = false;
                }
            }
            @Override
            public void onBillingServiceDisconnected() {
                Log.i("BILLING (C):", "mensagem");
            }
        });
    }

    public static Boolean DoesBillingisWorking(){
        return billingWorking;
    }

    public static void loadingProducts(String productId){

        billingClient.queryProductDetailsAsync(
                constructQueryProdutuctDetails(productId),
                new ProductDetailsResponseListener() {
                    public void onProductDetailsResponse(BillingResult billingResult, List<ProductDetails> productDetailsList) {
                        products = productDetailsList;
                        showProducts();
                    }
                }
        );
    }

    private static QueryProductDetailsParams constructQueryProdutuctDetails(String productId){
        QueryProductDetailsParams queryProductDetailsParams = QueryProductDetailsParams.newBuilder()
                .setProductList(
                        ImmutableList.of(
                                QueryProductDetailsParams.Product.newBuilder()
                                        .setProductId( productId )
                                        .setProductType( BillingClient.ProductType.SUBS )
                                        .build() ) )
                .build();
        return queryProductDetailsParams;
    }

    public static void showProducts(){
        ImmutableList productDetailsParamsList =
                ImmutableList.of(
                        BillingFlowParams.ProductDetailsParams.newBuilder()
                                // retrieve a value for "productDetails" by calling queryProductDetailsAsync()
                                .setProductDetails(products.get( 0 ))
                                // to get an offer token, call ProductDetails.getSubscriptionOfferDetails()
                                // for a list of offers that are available to the user
                                .setOfferToken(  products.get( 0 ).getSubscriptionOfferDetails().get( 0 ).getOfferToken()  )
                                .build()
                );

        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(productDetailsParamsList)
                .build();
        // Launch the billing flow
        BillingResult billingResult = billingClient.launchBillingFlow(activity, billingFlowParams);
    }

    public static void confirmPurchaseAssignature(){
        billingClient.queryPurchasesAsync(
                QueryPurchasesParams.newBuilder()
                        .setProductType( BillingClient.ProductType.SUBS)
                        .build(),
                new PurchasesResponseListener() {
                    public void onQueryPurchasesResponse(BillingResult billingResult, List<Purchase> purchases) {
                        if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                            for(Object purchase :  purchases){
                                PurchasesUpdatedListenerCuston.handlePurchase( (Purchase) purchase );
                            }
                        }
                    }
                }
        );
    }

    public static Boolean hasAds(){
        return hiddenAds;
    }
    static void setHiddenAds(Boolean value){
        Log.i("PURCHASE_STATE_OK_REMOVE_ADS",String.valueOf( value ));
        hiddenAds = value;
    }

    public static void verifyAssignature(){
        billingClient.queryPurchasesAsync(
                QueryPurchasesParams.newBuilder()
                        .setProductType( BillingClient.ProductType.SUBS)
                        .build(),
                new PurchasesResponseListener() {
                    public void onQueryPurchasesResponse(BillingResult billingResult, List<Purchase> purchases) {
                        if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                            for(Purchase purchase :  purchases){

                            }
                        }
                    }
                }
        );
    }

    static void setPurchase(Purchase purchase){
        Billing.purchase = purchase;
    }

    public static Purchase getPurchase(){
        return  purchase;
    }
}
