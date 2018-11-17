package spencerstudios.com.quixilib;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Quixi {

    public static void share(Context ctx,  String shareBody, String subject) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        ctx.startActivity(Intent.createChooser(sharingIntent, "Share via..."));
    }

    public static void copy(Context ctx, String textToCopy) {
        ClipboardManager clipboard = (ClipboardManager) ctx.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("copied text", textToCopy);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            Toast.makeText(ctx, "Copied to clipboard", Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    public static String paste(Context ctx){
        ClipboardManager clipboard = (ClipboardManager) ctx.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null && clipboard.hasPrimaryClip()) {
            ClipDescription description = clipboard.getPrimaryClipDescription();
            ClipData data = clipboard.getPrimaryClip();
            if (data != null && description != null && description.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN))
                return String.valueOf(data.getItemAt(0).getText());
        }
        return null;
    }

    public static String nowDatetime(){
        return DateFormat.getDateTimeInstance().format(System.currentTimeMillis());
    }

    public static String nowDate(){
        return DateFormat.getDateInstance().format(System.currentTimeMillis());
    }

    @NonNull
    public static String removeExtraSpaces(String str){
        return str.replaceAll(" [\\s]+", " ").trim();
    }

    public static Spannable spanBackgroundColor(String src, String target, String hexColor){
        Spannable spannable = new SpannableString(src);
        Pattern pattern = Pattern.compile(target);
        Matcher matcher = pattern.matcher(src);

        while (matcher.find()) {
            spannable.setSpan(new BackgroundColorSpan(Color.parseColor(hexColor)), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }

    public static Spannable spanForegroundColor(String src, String target, String hexColor){
        Spannable spannable = new SpannableString(src);
        Pattern pattern = Pattern.compile(target);
        Matcher matcher = pattern.matcher(src);

        while (matcher.find()) {
            spannable.setSpan(new ForegroundColorSpan(Color.parseColor(hexColor)), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }

    public static Spannable spanBold(String src, String target){
        Spannable spannable = new SpannableString(src);
        Pattern pattern = Pattern.compile(target);
        Matcher matcher = pattern.matcher(src);

        while (matcher.find()) {
            spannable.setSpan(new StyleSpan(Typeface.BOLD), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }

    public static Spannable spanItalic(String src, String target){
        Spannable spannable = new SpannableString(src);
        Pattern pattern = Pattern.compile(target);
        Matcher matcher = pattern.matcher(src);

        while (matcher.find()) {
            spannable.setSpan(new StyleSpan(Typeface.ITALIC), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }

    public static Spannable spanItalicBold(String src, String target){
        Spannable spannable = new SpannableString(src);
        Pattern pattern = Pattern.compile(target);
        Matcher matcher = pattern.matcher(src);

        while (matcher.find()) {
            spannable.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }

    public static Spannable spanUnderline(String src, String target){
        Spannable spannable = new SpannableString(src);
        Pattern pattern = Pattern.compile(target);
        Matcher matcher = pattern.matcher(src);

        while (matcher.find()) {
            spannable.setSpan(new UnderlineSpan(), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }

    public static void putString(Context ctx, String key, String value){
        SharedPreferences preferences  = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value).apply();
    }

    public static String getString(Context ctx, String key, String defaultValue){
        SharedPreferences preferences  = PreferenceManager.getDefaultSharedPreferences(ctx);
        return preferences.getString(key, defaultValue);
    }
}
