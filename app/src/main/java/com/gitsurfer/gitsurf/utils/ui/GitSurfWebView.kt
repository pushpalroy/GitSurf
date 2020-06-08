package com.gitsurfer.gitsurf.utils.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

class GitSurfWebView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : WebView(context, attrs, defStyleAttr) {

  init {
    setUpWebView(attrs)
  }

  @SuppressLint("SetJavaScriptEnabled")
  private fun setUpWebView(attrs: AttributeSet?) {
    webChromeClient = GitSurfChromeClient()
    webViewClient = GitSurfWebViewClient()

    val webSettings: WebSettings? = settings
    webSettings?.let {
      with(webSettings) {
        javaScriptEnabled = true
        setAppCacheEnabled(true)
        setAppCachePath(context.cacheDir.path)
        cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        defaultTextEncodingName = "utf-8"
        loadsImagesAutomatically = true
        blockNetworkImage = false
      }
    }
  }

  class GitSurfChromeClient : WebChromeClient() {
    override fun onProgressChanged(
      view: WebView?,
      newProgress: Int
    ) {
      super.onProgressChanged(view, newProgress)
    }
  }

  class GitSurfWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(
      view: WebView?,
      request: WebResourceRequest?
    ): Boolean {
      return super.shouldOverrideUrlLoading(view, request)
    }
  }

}