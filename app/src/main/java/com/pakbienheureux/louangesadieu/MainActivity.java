package com.pakbienheureux.louangesadieu;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.webkit.WebViewAssetLoader;

/**
 * Louanges à Dieu — application de cantiques.
 * Auteur : Pak le Bienheureux, à partir du livre de cantiques « Louanges à Dieu ».
 * Gratuit pour le peuple de Dieu.
 *
 * L'application affiche la page assets/index.html dans une WebView,
 * sans connexion internet.
 */
public class MainActivity extends Activity {

    private static final String HOTE = "appassets.androidplatform.net";
    private static final String ACCUEIL = "https://" + HOTE + "/assets/index.html";

    private WebView web;

    @Override
    protected void onCreate(Bundle etat) {
        super.onCreate(etat);

        web = new WebView(this);
        web.setBackgroundColor(0xFFF6F2EE);
        setContentView(web);

        final WebViewAssetLoader chargeur = new WebViewAssetLoader.Builder()
                .addPathHandler("/assets/", new WebViewAssetLoader.AssetsPathHandler(this))
                .build();

        WebSettings reglages = web.getSettings();
        reglages.setJavaScriptEnabled(true);
        reglages.setDomStorageEnabled(true);   // favoris, récents, taille du texte
        reglages.setAllowFileAccess(false);
        reglages.setAllowContentAccess(false);
        reglages.setTextZoom(100);

        web.addJavascriptInterface(new Pont(), "AndroidApp");

        web.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView vue, WebResourceRequest requete) {
                return chargeur.shouldInterceptRequest(requete.getUrl());
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView vue, WebResourceRequest requete) {
                Uri adresse = requete.getUrl();
                if (HOTE.equals(adresse.getHost())) {
                    return false;
                }
                // Lien externe : on l'ouvre dans le navigateur du téléphone
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, adresse));
                } catch (Exception ignore) {
                    // aucun navigateur disponible
                }
                return true;
            }
        });

        if (etat != null) {
            web.restoreState(etat);
        } else {
            web.loadUrl(ACCUEIL);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle etat) {
        super.onSaveInstanceState(etat);
        if (web != null) {
            web.saveState(etat);
        }
    }

    /** Touche « retour » : la page décide d'abord (fermer un cantique, effacer la recherche…). */
    @Override
    @SuppressWarnings("deprecation")
    public void onBackPressed() {
        if (web == null) {
            finish();
            return;
        }
        web.evaluateJavascript(
                "(window.androidBack && window.androidBack()) ? '1' : '0'",
                valeur -> {
                    if (!"\"1\"".equals(valeur)) {
                        finish();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        if (web != null) {
            web.destroy();
            web = null;
        }
        super.onDestroy();
    }

    /** Fonctions natives appelées depuis la page. */
    private class Pont {
        @JavascriptInterface
        public void copy(final String texte) {
            runOnUiThread(() -> {
                ClipboardManager pressePapier =
                        (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                if (pressePapier != null) {
                    pressePapier.setPrimaryClip(ClipData.newPlainText("Cantique", texte));
                }
            });
        }
    }
}
