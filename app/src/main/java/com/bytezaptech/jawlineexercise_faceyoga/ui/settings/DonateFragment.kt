package com.bytezaptech.jawlineexercise_faceyoga.ui.settings

import android.content.Context
import android.media.tv.AdRequest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bytezaptech.jawlineexercise_faceyoga.BuildConfig
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.DonateFragmentBinding
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.bytezaptech.jawlineexercise_faceyoga.utils.findNavControllerSafety
import com.bytezaptech.jawlineexercise_faceyoga.utils.showAlertDialog
import com.bytezaptech.jawlineexercise_faceyoga.utils.somethingWentWrong
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import javax.inject.Inject


class DonateFragment : Fragment() {
    private lateinit var binding: DonateFragmentBinding
    private lateinit var viewModel: SettingsViewModel
    private var donationAmount: Long = 0L
    private var mInterstitialAd: InterstitialAd? = null
    private final val TAG = "MainActivity"

    @Inject
    lateinit var mainRepo: MainRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.donate_fragment, container, false)
        viewModel = ViewModelProvider(
            this,
            SettingsViewModelFactory(mainRepo)
        )[SettingsViewModel::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemPaddings = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                systemPaddings.left,
                systemPaddings.top,
                systemPaddings.right,
                systemPaddings.bottom
            )
            insets
        }

        intializeAds()
        setListeners()
        return binding.root
    }

    private fun intializeAds() {
        val adRequest = com.google.android.gms.ads.AdRequest.Builder().build()

        var apiKey = BuildConfig.ADS_API_KEY
        if(BuildConfig.DEBUG)
            apiKey = getString(R.string.ads_test_app_id)

        InterstitialAd.load(requireActivity(), apiKey, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, "onAdFailedToLoad: ")
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "onAdLoaded: ")
                mInterstitialAd = interstitialAd
            }
        })
    }

    private fun setListeners() {
        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                Log.d(TAG, "Ad dismissed fullscreen content.")
                mInterstitialAd = null
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(TAG, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad showed fullscreen content.")
            }
        }

        binding.fiftyCard.setOnClickListener {
            donationAmount = 50

            binding.fiftyLy.background = requireActivity().getDrawable(R.drawable.stroke_shape)
            binding.hdLy.background = null
            binding.twoFiftyHdLy.background = null
            binding.fiveHdLy.background = null
            binding.sevenHdLy.background = null
            binding.thousandLy.background = null
        }
        binding.hdCard.setOnClickListener {
            donationAmount = 100

            binding.fiftyLy.background = null
            binding.hdLy.background = requireActivity().getDrawable(R.drawable.stroke_shape)
            binding.twoFiftyHdLy.background = null
            binding.fiveHdLy.background = null
            binding.sevenHdLy.background = null
            binding.thousandLy.background = null
        }
        binding.twoFiftyHdCard.setOnClickListener {
            donationAmount = 250

            binding.fiftyLy.background = null
            binding.hdLy.background = null
            binding.twoFiftyHdLy.background = requireActivity().getDrawable(R.drawable.stroke_shape)
            binding.fiveHdLy.background = null
            binding.sevenHdLy.background = null
            binding.thousandLy.background = null
        }
        binding.fiveHdCard.setOnClickListener {
            donationAmount = 500

            binding.fiftyLy.background = null
            binding.hdLy.background = null
            binding.twoFiftyHdLy.background = null
            binding.fiveHdLy.background = requireActivity().getDrawable(R.drawable.stroke_shape)
            binding.sevenHdLy.background = null
            binding.thousandLy.background = null
        }
        binding.sevenHdCard.setOnClickListener {
            donationAmount = 750

            binding.fiftyLy.background = null
            binding.hdLy.background = null
            binding.twoFiftyHdLy.background = null
            binding.fiveHdLy.background = null
            binding.sevenHdLy.background = requireActivity().getDrawable(R.drawable.stroke_shape)
            binding.thousandLy.background = null
        }
        binding.thousandCard.setOnClickListener {
            donationAmount = 1000

            binding.fiftyLy.background = null
            binding.hdLy.background = null
            binding.twoFiftyHdLy.background = null
            binding.fiveHdLy.background = null
            binding.sevenHdLy.background = null
            binding.thousandLy.background = requireActivity().getDrawable(R.drawable.stroke_shape)
        }

        binding.etAmount.addTextChangedListener {
            if (it.toString().isNotEmpty()) {
                donationAmount = it.toString().toLong()

                binding.fiftyLy.background = null
                binding.hdLy.background = null
                binding.twoFiftyHdLy.background = null
                binding.fiveHdLy.background = null
                binding.sevenHdLy.background = null
                binding.thousandLy.background = null
            }
        }

        binding.donateBtn.setOnClickListener {
            if (donationAmount > 0) {
                //Launch payment gateway.
//                val activity = this;
//                Checkout checkout = new Checkout();
//                checkout.setKeyID(getResources().getString(R.string.razorpay_key_id));
//                checkout.setImage(R.drawable.logo);
//
//                try {
//
//                    JSONObject options = new JSONObject();
//
//                    options.put("name", "Kaisebhi");
//                    options.put("description", "Reference No. " + orderId);
//                    options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//                    options.put("currency", "INR");
//                    options.put("amount", (am * 100));
//                    options.put("payment_capture", "1");
//                    options.put("theme", new JSONObject("{color: '#1278dd'}"));
//                    JSONObject preFill = new JSONObject();
//                    options.put("prefill", preFill);
//
//                    checkout.open(activity, options);
//
//                } catch (Exception e) {
//                    Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                    Log.d(TAG, "startPayment: " + e);
//                }
//            }

        } else {
                somethingWentWrong(requireActivity(), findNavController(), "Donation", "Please enter donation amount.", "okay")
            }
        }

        binding.adsBtn.setOnClickListener {
            intializeAds()
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(requireActivity())
            } else {
                somethingWentWrong(requireActivity(), findNavController(), "Ads", "The ad wasn't ready yet.", "okay")
            }
        }

        binding.ivUp.setOnClickListener {
            findNavControllerSafety(R.id.donate_fragment)?.popBackStack()
        }
    }
}