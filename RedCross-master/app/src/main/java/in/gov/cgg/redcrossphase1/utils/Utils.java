package in.gov.cgg.redcrossphase1.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ImageSpan;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicBoolean;


public class Utils {
    /**
     * Check whether the internet connection is present or not. <uses-permission
     * android:name="android.permission.ACCESS_NETWORK_STATE" />
     */
    // To check whether network connection is available on device or not
    public static boolean checkInternetConnection(Activity _activity) {
        ConnectivityManager conMgr = (ConnectivityManager) _activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected();
    }

    public static String nullToTrim(String fieldValue) {
        if (fieldValue == null || fieldValue.equalsIgnoreCase("null") || fieldValue.equalsIgnoreCase("")) {
            return "--";
        } else {
            return fieldValue;
        }
    }

    /**
     * Show an alert dialog and navigate back to previous screen if goBack is
     * true
     */
    public static void showAlert(final Activity _activity, String title,
                                 String alertMsg, final boolean goBack) {
        AlertDialog.Builder alert = new AlertDialog.Builder(_activity);
        alert.setTitle(title);
        alert.setCancelable(false);
        alert.setMessage(alertMsg);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (goBack)
                    _activity.finish();
            }
        });
        alert.show();
    }


    /**
     * Show an alert dialog and navigate back to previous screen if goBack is
     * true
     */
    public static void showAlertOkCancel(final Activity _activity,
                                         String title, String alertMsg, final boolean goBack) {
        AlertDialog.Builder alert = new AlertDialog.Builder(_activity);
        alert.setTitle(title);
        alert.setCancelable(false);
        alert.setMessage(alertMsg);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (goBack)
                    _activity.finish();
            }
        });
        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alert.show();
    }

    public static void showAlert(final Context myContext, String alertMsg, Exception e,
                                 final boolean goBack) {
        AlertDialog.Builder alert = new AlertDialog.Builder(myContext);
        alert.setTitle(alertMsg);
        alert.setCancelable(false);
        alert.setMessage(alertMsg);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (goBack)
                    ((Activity) myContext).finish();
            }
        });
        alert.show();
    }

    public static void justify(final TextView textView) {

        final AtomicBoolean isJustify = new AtomicBoolean(false);

        final String textString = textView.getText().toString();

        final TextPaint textPaint = textView.getPaint();

        final SpannableStringBuilder builder = new SpannableStringBuilder();

        textView.post(new Runnable() {
            @Override
            public void run() {

                if (!isJustify.get()) {

                    final int lineCount = textView.getLineCount();
                    final int textViewWidth = textView.getWidth();

                    for (int i = 0; i < lineCount; i++) {

                        int lineStart = textView.getLayout().getLineStart(i);
                        int lineEnd = textView.getLayout().getLineEnd(i);

                        String lineString = textString.substring(lineStart, lineEnd);

                        if (i == lineCount - 1) {
                            builder.append(new SpannableString(lineString));
                            break;
                        }

                        String trimSpaceText = lineString.trim();
                        String removeSpaceText = lineString.replaceAll(" ", "");

                        float removeSpaceWidth = textPaint.measureText(removeSpaceText);
                        float spaceCount = trimSpaceText.length() - removeSpaceText.length();

                        float eachSpaceWidth = (textViewWidth - removeSpaceWidth) / spaceCount;

                        SpannableString spannableString = new SpannableString(lineString);
                        for (int j = 0; j < trimSpaceText.length(); j++) {
                            char c = trimSpaceText.charAt(j);
                            if (c == ' ') {
                                Drawable drawable = new ColorDrawable(0x00ffffff);
                                drawable.setBounds(0, 0, (int) eachSpaceWidth, 0);
                                ImageSpan span = new ImageSpan(drawable);
                                spannableString.setSpan(span, j, j + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        }

                        builder.append(spannableString);
                    }

                    textView.setText(builder);
                    isJustify.set(true);
                }
            }
        });
    }


	/*public static void openBrowser(final Context myContext, String alertMsg, Exception e) {
		AlertDialog.Builder alert = new AlertDialog.Builder(myContext);
		//alert.setTitle(alertMsg);
		alert.setCancelable(false);
		alert.setMessage(myContext.getResources().getString(R.string.alert_browser)+" and this URL "+CommonData.archives_url+" will open in default browser.");
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Uri url = Uri.parse(CommonData.archives_url);
				Intent intent = new Intent(Intent.ACTION_VIEW, url);
				myContext.startActivity(intent);
			}
		});
		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		alert.showPdf();
	}

	public static void openBrowserURL(final Context myContext, String alertMsg, final String url, Exception e) {
		AlertDialog.Builder alert = new AlertDialog.Builder(myContext);
		//alert.setTitle(alertMsg);
		final String link_url = url;
		alert.setCancelable(false);
		alert.setMessage(myContext.getResources().getString(R.string.alert_browser)+" and this URL "+link_url+" will open in default browser.");
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Uri url = Uri.parse(link_url.toString());
				Intent intent = new Intent(Intent.ACTION_VIEW, url);
				myContext.startActivity(intent);
			}
		});
		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		alert.showPdf();
	}
*/




    /*
     * public static void showAlert(Context _activity, String title, String
     * alertMsg, boolean goBack) { AlertDialog.Builder alert = new
     * AlertDialog.Builder(_activity); alert.setTitle(title);
     * alert.setCancelable(false); alert.setMessage(alertMsg);
     * alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
     *
     * @Override public void onClick(DialogInterface dialog, int which) { } });
     *
     * alert.showPdf(); }
     *
     * public static boolean checkInternetConnection(Context _activity) {
     * ConnectivityManager conMgr = (ConnectivityManager)
     * _activity.getSystemService(Context.CONNECTIVITY_SERVICE); if
     * (conMgr.getActiveNetworkInfo() != null &&
     * conMgr.getActiveNetworkInfo().isAvailable() &&
     * conMgr.getActiveNetworkInfo().isConnected()) return true; else return
     * false; }
     */
}
