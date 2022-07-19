package com.origin.zapgrupos.ui.assinar;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.billingclient.api.Purchase;

public class AssinarViewModel extends ViewModel {
    public MutableLiveData<Purchase> purchase;
    public static MutableLiveData<Boolean> assignet;

}
