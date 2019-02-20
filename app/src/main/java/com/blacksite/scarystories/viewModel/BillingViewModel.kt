package com.blacksite.scarystories.viewModel

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.util.Log
import com.android.billingclient.api.*
import com.blacksite.scarystories.application.Settings

class BillingViewModel(application: Application, activity: Activity, viewModel: MainViewModel) : AndroidViewModel(application), PurchasesUpdatedListener {
    override fun onPurchasesUpdated(responseCode: Int, purchases: MutableList<Purchase>?) {
        Log.e("logger", "Purchase updated.")
        Log.e("logger", "Purchase Response Code: " + responseCode.toString())
        if (responseCode == BillingClient.BillingResponse.OK && purchases != null) {
            for (purchase in purchases) {
                handlePurchase(purchase)
            }
        } else if (responseCode == BillingClient.BillingResponse.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
        } else if (responseCode == BillingClient.BillingResponse.ITEM_ALREADY_OWNED){
            handleAlreadyOwned()
        } else {
            // Handle any other error codes.
        }
    }

    fun cancelPurchases() {
        billingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.INAPP) { responseCode, purchasesList ->
            if (responseCode == BillingClient.BillingResponse.OK && purchasesList != null) {
                for (purchase in purchasesList) {
                    // Process the result.
                    billingClient.consumeAsync(purchase.purchaseToken) { responseCode, _ ->
                        if (responseCode == BillingClient.BillingResponse.OK) {
                            mainViewModel.prefManager.adStatus = true
                            mainViewModel.adStatus.value = true
                        }
                    }
                }
            }
        }
    }
    private fun handleAlreadyOwned() {
        mainViewModel.prefManager.adStatus = false
        mainViewModel.adStatus.value = false
    }
    private fun handlePurchase(purchase: Purchase) {
        if(purchase.sku == Settings.NO_AD_SKU){
            mainViewModel.prefManager.adStatus = false
            mainViewModel.adStatus.value = false
        }
    }

    lateinit private var billingClient: BillingClient
    var mainViewModel = viewModel
    var mActivity = activity
    init {
        prepareBilling()
    }

    private fun prepareBilling() {
        billingClient = BillingClient.newBuilder(getApplication()).setListener(this).build()
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(@BillingClient.BillingResponse billingResponseCode: Int) {
                Log.e("logger", "Billing Setup finished.")
                Log.e("logger", "Billing Setup Response Code: " + billingResponseCode.toString())
                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                    // The billing client is ready. You can query purchases here.
                    Log.e("logger", "Billing Response OK.")
                    val skuList = ArrayList<String>()
                    skuList.add(Settings.NO_AD_SKU)
                    val params = SkuDetailsParams.newBuilder()
                    params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP)
                    billingClient.querySkuDetailsAsync(params.build()) { responseCode, skuDetailsList ->
                        // Process the result.
                        Log.e("logger", "Detail Response OK.")

                    }
                }
            }
            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                Log.e("logger", "Billing Service Disconnected.")

            }
        })
    }

    fun purchase(activity:Activity, skuID:String){
        val flowParams = BillingFlowParams.newBuilder()
                .setSku(skuID)
                .setType(BillingClient.SkuType.INAPP) // SkuType.SUB for subscription
                .build()
        val responseCode = billingClient.launchBillingFlow(activity, flowParams)
    }
}