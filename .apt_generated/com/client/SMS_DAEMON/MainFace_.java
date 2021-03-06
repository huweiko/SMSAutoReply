//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.2.
//


package com.client.SMS_DAEMON;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import com.client.SMS_DAEMON.R.id;
import com.client.SMS_DAEMON.R.layout;
import org.androidannotations.api.SdkVersionHelper;
import org.androidannotations.api.builder.ActivityIntentBuilder;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class MainFace_
    extends MainFace
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.main1);
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static MainFace_.IntentBuilder_ intent(Context context) {
        return new MainFace_.IntentBuilder_(context);
    }

    public static MainFace_.IntentBuilder_ intent(Fragment supportFragment) {
        return new MainFace_.IntentBuilder_(supportFragment);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (((SdkVersionHelper.getSdkInt()< 5)&&(keyCode == KeyEvent.KEYCODE_BACK))&&(event.getRepeatCount() == 0)) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        EditTextContainAutoReply = ((EditText) hasViews.findViewById(id.EditTextContainAutoReply));
        EditTextSendNum = ((EditText) hasViews.findViewById(id.EditTextSendNum));
        EditTextContainAutoNotReply = ((EditText) hasViews.findViewById(id.EditTextContainAutoNotReply));
        EditTextAutoReplyNum = ((EditText) hasViews.findViewById(id.EditTextAutoReplyNum));
        EditTextAutoReplyText = ((EditText) hasViews.findViewById(id.EditTextAutoReplyText));
        {
            View view = hasViews.findViewById(id.ButtonSet);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        MainFace_.this.OnClickButtonSet(view);
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ButtonClose);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        MainFace_.this.OnClickButtonClose(view);
                    }

                }
                );
            }
        }
        Init();
    }

    public static class IntentBuilder_
        extends ActivityIntentBuilder<MainFace_.IntentBuilder_>
    {

        private Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            super(context, MainFace_.class);
        }

        public IntentBuilder_(Fragment fragment) {
            super(fragment.getActivity(), MainFace_.class);
            fragmentSupport_ = fragment;
        }

        @Override
        public void startForResult(int requestCode) {
            if (fragmentSupport_!= null) {
                fragmentSupport_.startActivityForResult(intent, requestCode);
            } else {
                super.startForResult(requestCode);
            }
        }

    }

}
