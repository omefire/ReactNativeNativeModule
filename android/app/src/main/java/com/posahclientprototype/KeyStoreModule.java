package com.posahclientprototype;

import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.security.Signature;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;

import android.security.keystore.KeyGenParameterSpec;
import androidx.security.crypto.MasterKeys;
import java.security.KeyStore;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyPairGenerator;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import java.security.KeyPair;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

// Note: https://stackoverflow.com/questions/42110123/save-and-retrieve-keypair-in-androidkeystore
// https://stackoverflow.com/questions/38007084/debug-native-java-code-in-react-native
public class KeyStoreModule extends ReactContextBaseJavaModule {
    private static ReactApplicationContext reactContext;

    KeyStoreModule(ReactApplicationContext context) {
        super(context);
        reactContext = context;
    }

    @Override
    public String getName() {
        return "KeyStore";
    }

    @ReactMethod
    public void digitallySign(String textToSign, Promise promise) {
        try {
            
            //KeyGenParameterSpec keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;
            //String masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec);

            // Create the keys if necessary.
            // ToDO: What's the purpose of MasterKeys.getOrCreate(...) then?
            String keyAlias = "posah-key2";
            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);
            //Enumeration<String> aliases = ks.aliases();
            if(!ks.containsAlias(keyAlias)) {
                KeyPairGenerator kpg = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore");
                kpg.initialize(new KeyGenParameterSpec.Builder(
                    keyAlias,
                    KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY)
                    .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                    .build());
                KeyPair keyPair = kpg.generateKeyPair();
            }

            
            KeyStore.Entry entry = ks.getEntry(keyAlias, null);

            if (!(entry instanceof PrivateKeyEntry)) {
                throw new Exception("Entry is not an instance of PrivateKeyEntry");
            }

            Signature s = Signature.getInstance("SHA256withECDSA");
            PrivateKey privKey = ((PrivateKeyEntry) entry).getPrivateKey();
            s.initSign(privKey);
            s.update(textToSign.getBytes(StandardCharsets.UTF_8));
            byte[] signature = s.sign();
            
            String str = android.util.Base64.encodeToString(signature, Base64.DEFAULT);

            promise.resolve(str);

        } catch(Exception e) {
            // TODO: Properly handle exceptions.
            // e.g: "java.security.InvalidKeyException: Unsupported key algorithmL: RSA. Only EC supported"
            promise.reject("ERROR_WHILE_DIGITALLY_SIGNING", "Sorry, an error occured while digitally signing the text: " + textToSign + ". " + e.getMessage());
        }
    }
}