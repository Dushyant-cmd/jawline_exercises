package com.bytezaptech.jawlineexercise_faceyoga

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.navArgs
import com.bytezaptech.jawlineexercise_faceyoga.databinding.ActivityWebViewBinding

class WebViewFragment : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding
    private val args : WebViewFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemPaddings = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemPaddings.left, systemPaddings.top, systemPaddings.right, systemPaddings.bottom)
            insets
        }

        setupViews()
        setListeners()
    }

    private fun setupViews() {
        binding.webView.loadUrl(args.url)
        binding.webView.settings.javaScriptEnabled = true

        binding.webView.webViewClient = object : android.webkit.WebViewClient() {
            override fun onPageFinished(view: android.webkit.WebView, url: String) {
                binding.pB.visibility = View.GONE
            }
        }
    }

    private fun setListeners() {
        binding.ivUp.setOnClickListener {
            onBackPressed()
        }
    }
}