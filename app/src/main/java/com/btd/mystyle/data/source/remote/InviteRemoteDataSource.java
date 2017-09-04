package com.btd.mystyle.data.source.remote;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.btd.mystyle.R;
import com.facebook.FacebookSdk;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;
import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import bolts.AppLinks;

/**
 * Created by FRAMGIA\bui.tien.dat on 29/03/2017.
 */

public class InviteRemoteDataSource implements GoogleApiClient.OnConnectionFailedListener {

    public static InviteRemoteDataSource inviteRemoteDataSource;
    public static final String INVITE_CODE = "InviteCode";
    public static final String REFFERRAL_KEY = "referral";
    private static final int REQUEST_INVITE = 1203;
    private Context mContext;

    public InviteRemoteDataSource(Context context) {
        mContext = context;
    }

    public static InviteRemoteDataSource getInstance(Context context) {
        if (inviteRemoteDataSource == null) {
            inviteRemoteDataSource = new InviteRemoteDataSource(context);
        }
        return inviteRemoteDataSource;
    }

    public void getInvitedCodeFacebook(Intent intent) {
        Uri targetUrl =
                AppLinks.getTargetUrlFromInboundIntent(mContext, intent);
        if (targetUrl != null) {
            String invitedCode = targetUrl.getQueryParameter(REFFERRAL_KEY);
            if (invitedCode != null) {
                // invited ............................................
            }

        }
    }

    public void getInvitedCodeFirebase() {
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(AppInvite.API)
                .enableAutoManage((AppCompatActivity) mContext, this)
                .build();

        AppInvite.AppInviteApi.getInvitation(mGoogleApiClient, (AppCompatActivity) mContext, false)
                .setResultCallback(
                        result -> {
                            if (result.getStatus().isSuccess()) {
                                Intent intent = result.getInvitationIntent();
                                String deepLink = AppInviteReferral.getDeepLink(intent);
                                try {
                                    deepLink = URLDecoder.decode(deepLink, "UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                String inviteCode = Uri.parse(deepLink)
                                        .getQueryParameter(INVITE_CODE);
                                if (!TextUtils.isEmpty(inviteCode)) {
                                    // invited ............................................
                                }
                            } else {
                                Log.d("DAT", "getInvitation: no deep link found.");
                            }
                        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("DAT", "getInvitation: onConnectionFailed");
    }

    //11111111111111111111111111111111
    public void inviteFriendsByFacebook(String inviteCode) {
        String appLink = buildAppLink(inviteCode);
        showInviteFriendsByFacebook(appLink);
    }

    //2222222222222222222222222222222222
    public void inviteFriendsViaSMSOrEmail(String inviteCode) {
        Uri deepLink = buildDeepLink(inviteCode, true);
        showInviteFriendsViaSMSOrEmail(deepLink);
    }

    //333333333333333333333333333333333333
    public void inviteFriendsByOther(String inviteCode) {
        Uri deepLink = buildDeepLink(inviteCode, false);
        showInviteFriendsByOther(deepLink.toString());
    }


    private void showInviteFriendsByFacebook(String appLinkUrl) {

        FacebookSdk.sdkInitialize(mContext);
        if (AppInviteDialog.canShow()) {
            AppInviteContent content = new AppInviteContent.Builder()
                    .setApplinkUrl(appLinkUrl)
                    .build();
            AppInviteDialog.show((AppCompatActivity) mContext, content);
        }
    }

    private void showInviteFriendsViaSMSOrEmail(Uri deepLink) {
        Intent intent = new AppInviteInvitation.IntentBuilder("Invite Title")
                .setMessage("Invite Message")
                .setDeepLink(deepLink)
                .build();
        ((AppCompatActivity) mContext).startActivityForResult(intent, REQUEST_INVITE);
    }

    private void showInviteFriendsByOther(String deepLink) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Invite Title");
        intent.putExtra(Intent.EXTRA_TEXT, deepLink);
        mContext.startActivity(intent);
    }


    private Uri buildDeepLink(String inviteCode, boolean isSMSOrEmail) {
        String appCode = mContext.getString(R.string.firebase_app_code);
        String packageName = mContext.getPackageName();
        String deepLink = mContext.getString(R.string.invitation_deep_link);
        if (!TextUtils.isEmpty(inviteCode) && !isSMSOrEmail) {
            String inviteCodeParam;
            try {
                inviteCodeParam = URLEncoder.encode(String.format("?%1s=%2s",
                        INVITE_CODE, inviteCode), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                inviteCodeParam = null;
            }
            if (!TextUtils.isEmpty(inviteCodeParam)) {
                deepLink += inviteCodeParam;
            }
        }
        Uri.Builder builder = new Uri.Builder()
                .scheme("https")
                .authority(appCode + ".app.goo.gl")
                .path("/")
                .appendQueryParameter("link", deepLink)
                .appendQueryParameter("apn", packageName);

        if (!TextUtils.isEmpty(inviteCode) && isSMSOrEmail) {
            builder.appendQueryParameter(INVITE_CODE, inviteCode);
        }

        return builder.build();
    }

    private String buildAppLink(String inviteCode) {
        StringBuilder appLinkUrl = new StringBuilder(mContext.getString(R.string.app_link_url));
        appLinkUrl.append("?")
                .append(REFFERRAL_KEY)
                .append("=")
                .append(inviteCode);

        return appLinkUrl.toString();
    }

}
