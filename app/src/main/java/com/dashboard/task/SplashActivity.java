package com.dashboard.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.amazonaws.mobile.auth.userpools.CognitoUserPoolsSignInProvider;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.StartupAuthResultHandler;
import com.amazonaws.mobile.auth.core.StartupAuthResult;
import com.amazonaws.mobile.auth.core.DefaultSignInResultHandler;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.IdentityProvider;
import com.amazonaws.mobile.auth.core.StartupAuthErrorDetails;
import com.amazonaws.mobile.auth.core.StartupAuthResult;
import com.amazonaws.mobile.auth.core.StartupAuthResultHandler;
import com.amazonaws.mobile.auth.core.signin.AuthException;
import com.amazonaws.mobile.auth.ui.AuthUIConfiguration;
import com.amazonaws.mobile.auth.ui.SignInActivity;

/**
 * Activity that handle initial sign in / sign up logic. This activity verifies if a user is logged in. Otherwise, it
 * handles to sign up activity provided by AWS.
 */
public class SplashActivity extends AppCompatActivity
{


    /**
     * onCreate function handles the sign in / sign up logic.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AWSConfiguration awsConfig = new AWSConfiguration(getApplicationContext());
        final IdentityManager identityManager = new IdentityManager(getApplicationContext(), awsConfig);
        IdentityManager.setDefaultIdentityManager(identityManager);

        IdentityManager.getDefaultIdentityManager().addSignInProvider(
                CognitoUserPoolsSignInProvider.class);

        IdentityManager.getDefaultIdentityManager().doStartupAuth(this,
                new StartupAuthResultHandler() {
                    @Override
                    public void onComplete(final StartupAuthResult authResults) {
                        if (authResults.isUserSignedIn()) {
                            final IdentityProvider provider =
                                    identityManager.getCurrentIdentityProvider();

                            Toast.makeText(
                                    SplashActivity.this, String.format("Signed in with %s",
                                            provider.getDisplayName()), Toast.LENGTH_LONG).show();
                            goMain(SplashActivity.this);
                            return;

                        }
                        else
                        {

                            final StartupAuthErrorDetails errors =
                                    authResults.getErrorDetails();

                            if (errors.didErrorOccurRefreshingProvider())
                            {
                                final AuthException providerAuthException =
                                        errors.getProviderRefreshException();
                            }

                            doSignIn(IdentityManager.getDefaultIdentityManager());
                            return;
                        }


                    }
                }, 2000);
    }

    /**
     * This function makes the sign in for an authenticated user.
     * @param identityManager
     */
    public void doSignIn(final IdentityManager identityManager)
    {

        identityManager.setUpToAuthenticate(
                SplashActivity.this, new DefaultSignInResultHandler()
                {

                    @Override
                    public void onSuccess(Activity activity, IdentityProvider identityProvider)
                    {
                        activity.startActivity(new Intent(activity, MainActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }

                    @Override
                    public boolean onCancel(Activity activity)
                    {
                        return false;
                    }
                });

        AuthUIConfiguration config = new AuthUIConfiguration.Builder()
                                        .userPools(true)
                                        .build();

        Context context = SplashActivity.this;
        SignInActivity.startSignInActivity(context, config);
        SplashActivity.this.finish();
    }


    /**
     * Goes to main activity that handles tasks.
     * @param callingActivity
     */
    public void goMain(final Activity callingActivity)
    {
        callingActivity.startActivity(new Intent(callingActivity, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        callingActivity.finish();
    }

}
