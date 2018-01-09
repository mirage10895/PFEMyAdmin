package fr.eseo.dis.amiaudluc.pfeproject.splashscreen;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.amiaudluc.pfeproject.Content.Content;
import fr.eseo.dis.amiaudluc.pfeproject.LoginActivity;
import fr.eseo.dis.amiaudluc.pfeproject.MainActivity;
import fr.eseo.dis.amiaudluc.pfeproject.R;
import fr.eseo.dis.amiaudluc.pfeproject.decoder.CacheFileGenerator;
import fr.eseo.dis.amiaudluc.pfeproject.decoder.WebServerExtractor;

/**
 * Created by lucasamiaud on 08/01/2018.
 */

public class SplashScreenActivity extends Activity{

    private Context ctx;

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        ctx = this;

        if (Build.VERSION.SDK_INT >= 23) {
            List<String> listPermissionsNeeded = new ArrayList<>();
            if (checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
            }
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
            } else {
                StartAnimations();
            }
        } else {
            StartAnimations();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permission, @NonNull int[] grantResult) {
        super.onRequestPermissionsResult(requestCode, permission, grantResult);
        if (grantResult[0] == PackageManager.PERMISSION_GRANTED) {
            StartAnimations();
        } else {
            StartAnimations();
        }
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();

        Animation animReverse = AnimationUtils.loadAnimation(this, R.anim.alpha_inverse);
        animReverse.reset();

        ImageView logoSono = findViewById(R.id.splash_logo);
        logoSono.clearAnimation();
        logoSono.startAnimation(anim);
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 1500) {
                        sleep(100);
                        waited += 100;
                    }

                    if (wasConnected()) {
                        String userResult = CacheFileGenerator.getInstance().read(ctx, CacheFileGenerator.CORE_USER);
                        Content.currentUser = WebServerExtractor.extractUser(userResult);
                        if (Content.currentUser != null) {
                            Intent intent = new Intent(ctx, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(ctx, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Intent intent = new Intent(ctx, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        finish();
                    }
                } catch (InterruptedException e) {
                    Intent intent = new Intent(ctx, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
                } finally {
                }
            }
        };
        splashTread.start();
    }

    private boolean wasConnected() {
        String userResult = CacheFileGenerator.getInstance().read(ctx, CacheFileGenerator.CORE_USER);
        if (userResult.equals("")) {
            return false;
        } else {
            Content.currentUser = WebServerExtractor.extractUser(userResult);
            if (Content.currentUser == null) {
                CacheFileGenerator.getInstance().removeAll(ctx);
                return false;
            }
            return true;
        }
    }

}
