package com.gitsurfer.mdview

import android.R.attr
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
import android.webkit.WebView
import android.webkit.WebViewClient
import com.gitsurfer.mdview.MdUtils.escapeForText
import com.gitsurfer.mdview.MdUtils.imgToBase64

class MdView : NestedScrollWebView {

  private var previewText: String? = null
  var isOpenUrlInBrowser = false
  private var enableNestedScrolling = false
  private var contentChangeListener: ContentChangeListener? = null

  companion object {
    val TAG = MdView::class.java.simpleName
  }

  constructor(
    context: Context
  ) : this(context, null)

  constructor(
    context: Context,
    attrs: AttributeSet?
  ) : this(context, attrs, attr.webViewStyle)

  constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int
  ) : super(context, attrs, defStyleAttr) {
    init()
  }

  fun setMarkDownText(markdownText: String?) {
    markdownText?.let {
      val bs64MdText = imgToBase64(it)
      val escMdText = escapeForText(bs64MdText)
      previewText = String.format("preview('%s')", escMdText)
      init()
    }
  }

  @SuppressLint("SetJavaScriptEnabled")
  private fun init() {
    with(settings) {
      setAppCachePath(context.cacheDir.path)
      setAppCacheEnabled(true)
      setSupportZoom(true)
      javaScriptEnabled = true
      cacheMode = WebSettings.LOAD_NO_CACHE
      defaultTextEncodingName = "utf-8"
      loadsImagesAutomatically = true
      blockNetworkImage = false
      layoutAlgorithm = TEXT_AUTOSIZING
      scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
      builtInZoomControls = true
      displayZoomControls = false
      allowUniversalAccessFromFileURLs = true
      mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
    }
    webChromeClient = ChromeClient()
    webViewClient = MdWebViewClient()
    loadUrl("file:///android_asset/html/preview.html")
  }

  override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
    super.onScrollChanged(l, t, oldl, oldt)
    contentChangeListener?.onScrollChanged(t == 0, t)
  }

  override fun onDetachedFromWindow() {
    contentChangeListener = null
    super.onDetachedFromWindow()
  }

  fun setOnContentChangedListener(contentChangeListener: ContentChangeListener) {
    this.contentChangeListener = contentChangeListener
  }

  fun setEnableNestedScrolling(enableNestedScrolling: Boolean) {
    if (this.enableNestedScrolling != enableNestedScrolling) {
      isNestedScrollingEnabled = enableNestedScrolling
      this.enableNestedScrolling = enableNestedScrolling
    }
  }

  internal inner class MdWebViewClient : WebViewClient() {
    override fun onPageFinished(view: WebView, url: String) {
      evaluateJavascript(previewText, null)
    }

    override fun shouldOverrideUrlLoading(
      view: WebView,
      url: String
    ): Boolean {
      if (isOpenUrlInBrowser) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
        return true
      }
      return false
    }
  }

  private inner class ChromeClient : WebChromeClient() {
    override fun onProgressChanged(view: WebView, progress: Int) {
      super.onProgressChanged(view, progress)
      contentChangeListener?.onContentChanged(progress)
    }
  }
}