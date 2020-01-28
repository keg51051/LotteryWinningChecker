package com.example.lottery.ui.lotto649

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.lottery.R
import com.example.lottery.ui.LottoDTO
import com.google.android.gms.ads.*
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.text.DecimalFormat

class Lotto649Fragment : Fragment() {

    private lateinit var lotto649ViewModel: Lotto649ViewModel
    lateinit var mAdView : AdView
    lateinit var mAdView2 : AdView
    private lateinit var mInterstitialAd: InterstitialAd
    private var clickcount: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lotto649ViewModel =
            ViewModelProviders.of(this).get(Lotto649ViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_lotto649, container, false)

        // Banner AdMob
        val adView = AdView(activity)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = "ca-app-pub-3013351411920370/2563367164"
        MobileAds.initialize(activity) {}
        mAdView = root.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        mAdView2 = root.findViewById(R.id.adView2)
        mAdView2.loadAd(adRequest)

        // Interstitial AdMob
        mInterstitialAd = InterstitialAd(activity)
        mInterstitialAd.adUnitId = "ca-app-pub-3013351411920370/9816484304"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        // Set an AdListener
        mInterstitialAd.adListener = object: AdListener() {
            override fun onAdClosed() {
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }

        val textView: TextView = root.findViewById(R.id.text_lotto649)
        val ids = arrayListOf<EditText>(
            root.findViewById(R.id.sixNumber1),
            root.findViewById(R.id.sixNumber2),
            root.findViewById(R.id.sixNumber3),
            root.findViewById(R.id.sixNumber4),
            root.findViewById(R.id.sixNumber5),
            root.findViewById(R.id.sixNumber6)
        )
        val check: Button = root.findViewById(R.id.btn649Check)
        val result: TextView = root.findViewById(R.id.text649Numbers)
        val nextWinningNum: TextView = root.findViewById(R.id.textNext649WinningNumbers)
        val recentWinningNum: TextView = root.findViewById(R.id.text649RecentWinningNumbers)
        val client = OkHttpClient()
        val numbers = arrayListOf<Int>()
        val web: WebView = root.findViewById(R.id.sixWeb)

        web.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }

        // GET Request for lottery API
        val request = Request.Builder()
            .url("https://www.lotterytracker.ca/api/winningNumbers?recent=true&provinceCode=ON&filterByGameIds=1")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                var str = response.body()!!.string()
                var lottoDTO: List<LottoDTO> = Gson().fromJson(str, Array<LottoDTO>::class.java).toList()

                // JSON value to string in the activity
                activity?.runOnUiThread {
                    recentWinningNum.text = "WINNING NUMBERS\n" +
                            lottoDTO[lottoDTO.size-1].Regular + "\nBONUS: " + lottoDTO[lottoDTO.size-1].Bonus

                    val dec = DecimalFormat("##,###,###")
                    nextWinningNum.text = "THE NEXT JACKPOT IS\n" +
                            lottoDTO[lottoDTO.size-1].NextDrawDate!!.substring(0, 10) +
                            "\n $" + dec.format(lottoDTO[lottoDTO.size-1].Jackpot!!.toBigDecimal())
                }
            }
        })

        lotto649ViewModel.text.observe(this, Observer {
            textView.text = it

            check.setOnClickListener {
                // The interstitial ad is showed when the user press the check button every 10 times
                clickcount += 1
                if (mInterstitialAd.isLoaded && clickcount % 10 == 0) {
                    Log.d("TAG", "The interstitial is loaded.")
                    mInterstitialAd.show()
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.")
                }
                // Clear the list in order to prevent adding numbers constantly
                numbers.clear()
                for (i in 0..5) {
                    val num = ids[i]
                    if (num.text.toString().isNullOrEmpty()) {
                        result.text = "The number of your numbers is less than 6."
                        break
                    } else if (num.text.toString().toInt() > 50) {
                        result.text = "Please enter the number (1~49)."
                    } else {
                        numbers.add(num.text.toString().toInt())
                        numbers.sort()
                        // Checking duplicated numbers or the number more than 50
                        if (numbers.distinct().size < 6) {
                            result.text =
                                "You put duplicated numbers \nor the number(s) more than 50."
                        } else {
                            recentWinningNum.visibility = View.GONE
                            nextWinningNum.visibility = View.GONE
                            result.setTextColor(Color.BLACK)
                            result.text = "Your numbers: " + numbers.distinct().joinToString()
                            // User Numbers for URL String
                            var numURL: String =
                                "num1=" + numbers[0] +
                                        "&num2=" + numbers[1] +
                                        "&num3=" + numbers[2] +
                                        "&num4=" + numbers[3] +
                                        "&num5=" + numbers[4] +
                                        "&num6=" + numbers[5]

                            // Load the web page of the OLG Number Checker
                            web.loadUrl("https://lottery.olg.ca/en-ca/winning-numbers/lotto-649/have-your-numbers-won?$numURL")
                            // Close the keyboard
                            val imm =
                                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(view!!.windowToken, 0)
                        }
                    }
                }
            }
        })
        return root
    }
}