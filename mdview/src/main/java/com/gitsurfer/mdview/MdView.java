package com.gitsurfer.mdview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ViewCompat;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MdView extends WebView implements NestedScrollingChild {
  public static final String TAG = MdView.class.getSimpleName();
  private static final String IMAGE_PATTERN = "!\\[(.*)\\]\\((.*)\\)";

  private final Context context;
  private String previewText;
  private boolean isOpenUrlInBrowser;
  private int lastY;
  private final int[] scrollOffset = new int[2];
  private final int[] mScrollConsumed = new int[2];
  private int nestedOffsetY;
  private NestedScrollingChildHelper childHelper;
  private boolean firstScroll = true;
  private boolean interceptTouch;
  private boolean enableNestedScrolling;
  private OnContentChangedListener onContentChangedListener;

  public interface OnContentChangedListener {
    void onContentChanged(int progress);

    void onScrollChanged(boolean reachedTop, int scroll);
  }

  public MdView(Context context) {
    this(context, null);
  }

  public MdView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public MdView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.context = context;
    childHelper = new NestedScrollingChildHelper(this);
    setNestedScrollingEnabled(true);
    init();
  }

  @SuppressLint("SetJavaScriptEnabled")
  private void init() {
    WebSettings settings = getSettings();
    settings.setJavaScriptEnabled(true);
    settings.setAppCachePath(getContext().getCacheDir().getPath());
    settings.setAppCacheEnabled(true);
    settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    settings.setDefaultTextEncodingName("utf-8");
    settings.setLoadsImagesAutomatically(true);
    settings.setBlockNetworkImage(false);
    settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
    setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
    settings.setSupportZoom(true);
    settings.setBuiltInZoomControls(true);
    settings.setDisplayZoomControls(false);

    setWebChromeClient(new ChromeClient());
    setWebViewClient(new MdWebViewClient());

    loadUrl("file:///android_asset/html/preview.html");
    getSettings().setJavaScriptEnabled(true);
    getSettings().setAllowUniversalAccessFromFileURLs(true);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
    }
  }

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public boolean onTouchEvent(MotionEvent ev) {
    if (getParent() != null) {
      getParent().requestDisallowInterceptTouchEvent(interceptTouch);
    }

    boolean returnValue;
    MotionEvent event = MotionEvent.obtain(ev);
    final int action = MotionEventCompat.getActionMasked(event);
    if (action == MotionEvent.ACTION_DOWN) {
      nestedOffsetY = 0;
    }
    int eventY = (int) event.getY();
    event.offsetLocation(0, nestedOffsetY);
    switch (action) {
      case MotionEvent.ACTION_MOVE:
        int deltaY = lastY - eventY;
        // NestedPreScroll
        if (dispatchNestedPreScroll(0, deltaY, mScrollConsumed, scrollOffset)) {
          deltaY -= mScrollConsumed[1];
          lastY = eventY - scrollOffset[1];
          event.offsetLocation(0, -scrollOffset[1]);
          nestedOffsetY += scrollOffset[1];
        }
        returnValue = super.onTouchEvent(event);

        // NestedScroll
        if (dispatchNestedScroll(0, scrollOffset[1], 0, deltaY, scrollOffset)) {
          event.offsetLocation(0, scrollOffset[1]);
          nestedOffsetY += scrollOffset[1];
          lastY -= scrollOffset[1];
        }
        break;
      case MotionEvent.ACTION_DOWN:
        returnValue = super.onTouchEvent(event);
        if (firstScroll) {
          lastY = eventY - 5;
          firstScroll = false;
        } else {
          lastY = eventY;
        }
        startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
        break;
      default:
        returnValue = super.onTouchEvent(event);
        // end NestedScroll
        stopNestedScroll();
        break;
    }
    return returnValue;
  }

  public void setMarkDownText(String markdownText) {
    String bs64MdText = imgToBase64(markdownText);
    String escMdText = escapeForText(bs64MdText);
    previewText = String.format("preview('%s')", escMdText);
    init();
  }

  private String escapeForText(String mdText) {
    String escText = mdText.replace("\n", "\\\\n");
    escText = escText.replace("'", "\\\'");
    escText = escText.replace("\r", "");
    return escText;
  }

  private String imgToBase64(String mdText) {
    Pattern ptn = Pattern.compile(IMAGE_PATTERN);
    Matcher matcher = ptn.matcher(mdText);
    if (!matcher.find()) {
      return mdText;
    }
    String imgPath = matcher.group(2);
    if (isUrlPrefix(imgPath) || !isPathExCheck(imgPath)) {
      return mdText;
    }
    String baseType = imgEx2BaseType(imgPath);
    if ("".equals(baseType)) {
      return mdText;
    }
    File file = new File(imgPath);
    byte[] bytes = new byte[(int) file.length()];
    try {
      BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
      buf.read(bytes, 0, bytes.length);
      buf.close();
    } catch (FileNotFoundException e) {
      Log.e(TAG, "FileNotFoundException:" + e);
    } catch (IOException e) {
      Log.e(TAG, "IOException:" + e);
    }
    String base64Img = baseType + Base64.encodeToString(bytes, Base64.NO_WRAP);
    return mdText.replace(imgPath, base64Img);
  }

  private boolean isUrlPrefix(String text) {
    return text.startsWith("http://") || text.startsWith("https://");
  }

  private boolean isPathExCheck(String text) {
    return text.endsWith(".png")
        || text.endsWith(".jpg")
        || text.endsWith(".jpeg")
        || text.endsWith(".gif");
  }

  private String imgEx2BaseType(String text) {
    if (text.endsWith(".png")) {
      return "data:image/png;base64,";
    } else if (text.endsWith(".jpg") || text.endsWith(".jpeg")) {
      return "data:image/jpg;base64,";
    } else if (text.endsWith(".gif")) {
      return "data:image/gif;base64,";
    } else {
      return "";
    }
  }

  public boolean isOpenUrlInBrowser() {
    return isOpenUrlInBrowser;
  }

  public void setOpenUrlInBrowser(boolean openUrlInBrowser) {
    isOpenUrlInBrowser = openUrlInBrowser;
  }

  @Override public void setNestedScrollingEnabled(boolean enabled) {
    childHelper.setNestedScrollingEnabled(enabled);
  }

  @Override public boolean isNestedScrollingEnabled() {
    return childHelper.isNestedScrollingEnabled();
  }

  @Override public boolean startNestedScroll(int axes) {
    return childHelper.startNestedScroll(axes);
  }

  @Override public void stopNestedScroll() {
    childHelper.stopNestedScroll();
  }

  @Override public boolean hasNestedScrollingParent() {
    return childHelper.hasNestedScrollingParent();
  }

  @Override public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
      int dyUnconsumed,
      int[] offsetInWindow) {
    return childHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
        offsetInWindow);
  }

  @Override
  public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
    return childHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
  }

  @Override public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
    return childHelper.dispatchNestedFling(velocityX, velocityY, consumed);
  }

  @Override public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
    return childHelper.dispatchNestedPreFling(velocityX, velocityY);
  }

  @Override protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    super.onScrollChanged(l, t, oldl, oldt);
    if (onContentChangedListener != null) {
      onContentChangedListener.onScrollChanged(t == 0, t);
    }
  }

  @Override protected void onDetachedFromWindow() {
    onContentChangedListener = null;
    super.onDetachedFromWindow();
  }

  public void setOnContentChangedListener(
      @NonNull OnContentChangedListener onContentChangedListener) {
    this.onContentChangedListener = onContentChangedListener;
  }

  public void setEnableNestedScrolling(boolean enableNestedScrolling) {
    if (this.enableNestedScrolling != enableNestedScrolling) {
      setNestedScrollingEnabled(enableNestedScrolling);
      this.enableNestedScrolling = enableNestedScrolling;
    }
  }

  public void setInterceptTouch(boolean interceptTouch) {
    this.interceptTouch = interceptTouch;
  }

  class MdWebViewClient extends WebViewClient {
    public void onPageFinished(WebView view, String url) {
      evaluateJavascript(previewText, null);
    }

    @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
      if (isOpenUrlInBrowser()) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
        return true;
      }
      return false;
    }
  }

  private class ChromeClient extends WebChromeClient {
    @Override public void onProgressChanged(WebView view, int progress) {
      super.onProgressChanged(view, progress);
      if (onContentChangedListener != null) {
        onContentChangedListener.onContentChanged(progress);
      }
    }
  }
}