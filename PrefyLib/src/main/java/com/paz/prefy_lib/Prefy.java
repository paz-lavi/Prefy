package com.paz.prefy_lib;


import android.content.Context;
import android.content.SharedPreferences;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

/**
 * Class for using SharedPreferences easily, even with encryption
 */
public class Prefy {
    // Constructor init + set instance //
    private static Prefy instance; // me
    private SharedPreferences sharedPreferences;
    private final Gson gson;

    public static Prefy getInstance() {
        return instance;
    }

    private Prefy(Context appContext, boolean encrypted) {
        gson = new Gson();
        String spName = appContext.getPackageName() + "-Prefy";
        if (encrypted) {
            try {

                KeyGenParameterSpec spec = new KeyGenParameterSpec.Builder(
                        MasterKey.DEFAULT_MASTER_KEY_ALIAS,
                        KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                        .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
                        .build();

                MasterKey masterKey = new MasterKey.Builder(appContext)
                        .setKeyGenParameterSpec(spec)
                        .build();
                sharedPreferences = EncryptedSharedPreferences.create(
                        appContext,
                        spName,
                        masterKey, // masterKey created above
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            sharedPreferences = appContext.getApplicationContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        }
    }

    /**
     * init SharedPreferences
     *
     * @param context   - Application Context
     * @param encrypted - Boolean, true if need encryption
     * @return instance of prefy
     */
    public static Prefy init(Context context, boolean encrypted) {
        if (instance == null)
            instance = new Prefy(context.getApplicationContext(), encrypted);
        return instance;
    }

    /**
     * return the type token of calls
     *
     * @param _class - the class you need the token for example String.class or String[].class
     * @return the type pf the class
     */
    public Type getTypeToken(Type _class) {
        return TypeToken.get(_class).getType();

    }

    /**
     * save String to shared preferences
     *
     * @param key   - key for the value
     * @param value - the value to save
     */
    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * save String to shared preferences
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return Returns true if the new values were successfully written to persistent storage.
     */
    public boolean putStringSync(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * get the saved String from shared preferences
     *
     * @param key      - key for the requested value
     * @param defValue - default in case the key not exist
     * @return if the key exist his value will returned else devValue
     */
    public String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }


    /**
     * save boolean to shared preferences
     *
     * @param key   - key for the value
     * @param value - the value to save
     */
    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * save boolean to shared preferences
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return Returns true if the new values were successfully written to persistent storage.
     */
    public boolean putBooleanSync(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * get the saved boolean from shared preferences
     *
     * @param key      - key for the requested value
     * @param defValue - default in case the key not exist
     * @return if the key exist his value will returned else devValue
     */
    public boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    /**
     * save int to shared preferences
     *
     * @param key   - key for the value
     * @param value - the value to save
     */
    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * save int to shared preferences
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return Returns true if the new values were successfully written to persistent storage.
     */
    public boolean putIntSync(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * get the saved int from shared preferences
     *
     * @param key      - key for the requested value
     * @param defValue - default in case the key not exist
     * @return if the key exist his value will returned else devValue
     */
    public int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    /**
     * save float to shared preferences
     *
     * @param key   - key for the value
     * @param value - the value to save
     */
    public void putFloat(String key, float value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * save float to shared preferences
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return Returns true if the new values were successfully written to persistent storage.
     */
    public boolean putFloatSync(String key, float value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * get the saved float from shared preferences
     *
     * @param key      - key for the requested value
     * @param defValue - default in case the key not exist
     * @return if the key exist his value will returned else devValue
     */
    public float getFloat(String key, float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    /**
     * save long to shared preferences
     *
     * @param key   - key for the value
     * @param value - the value to save
     */
    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * save long to shared preferences
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return Returns true if the new values were successfully written to persistent storage.
     */
    public boolean putLongSync(String key, long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * get the saved long from shared preferences
     *
     * @param key      - key for the requested value
     * @param defValue - default in case the key not exist
     * @return if the key exist his value will returned else devValue
     */
    public long getLong(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    /**
     * save Double to shared preferences
     *
     * @param key   - key for the value
     * @param value - the value to save
     */
    public void putDouble(String key, double value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, Double.doubleToRawLongBits(value));
        editor.apply();
    }

    /**
     * save Double to shared preferences
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return Returns true if the new values were successfully written to persistent storage.
     */
    public boolean putDoubleSync(String key, double value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, Double.doubleToRawLongBits(value));
        return editor.commit();
    }

    /**
     * get the saved Double from shared preferences
     *
     * @param key      - key for the requested value
     * @param defValue - default in case the key not exist
     * @return if the key exist his value will returned else devValue
     */
    public double getDouble(String key, double defValue) {
        return Double.longBitsToDouble(sharedPreferences.getLong(key, Double.doubleToRawLongBits(defValue)));
    }


    /**
     * save String set to shared preferences
     *
     * @param key   - key for the value
     * @param value - the value to save
     */
    public void putStringSet(String key, Set<String> value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, value);
        editor.apply();
    }

    /**
     * save String set to shared preferences
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return Returns true if the new values were successfully written to persistent storage.
     */
    public boolean putStringSetSync(String key, Set<String> value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, value);
        return editor.commit();
    }

    /**
     * get the saved String set from shared preferences
     *
     * @param key      - key for the requested value
     * @param defValue - default in case the key not exist
     * @return if the key exist his value will returned else devValue
     */
    public Set<String> getStringSet(String key, Set<String> defValue) {
        return sharedPreferences.getStringSet(key, defValue);
    }

    /**
     * save any object to shared preferences
     *
     * @param key   - key for the value
     * @param value - the value to save
     */
    public <T> void putObject(String key, T value) {
        String json = gson.toJson(value);
        putString(key, json);
    }

    /**
     * save any object to shared preferences
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return Returns true if the new values were successfully written to persistent storage.
     */
    public <T> boolean putObjectSync(String key, T value) {
        String json = gson.toJson(value);
        return putStringSync(key, json);
    }

    /**
     * get the saved object from shared preferences
     *
     * @param key      - key for the requested value
     * @param defValue - default in case the key not exist
     * @param type     the type of the excepted object - for example MyObject.class
     * @return if the key exist his value will returned else devValue
     */
    public <T> T getObject(String key, T defValue, Class<T> type) {
        String json = getString(key, "");
        if (json.isEmpty())
            return defValue;
        return gson.fromJson(json, type);
    }

    /**
     * save any type of array to shared preferences
     *
     * @param key   - key for the value
     * @param value - the value to save
     */
    public <T> void putArray(String key, T[] value) {
        String json = gson.toJson((T[]) value);
        putString(key, json);
    }

    /**
     * save any type of array to shared preferences
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return Returns true if the new values were successfully written to persistent storage.
     */
    public <T> boolean putArraySync(String key, T[] value) {
        String json = gson.toJson((T[]) value);
        return putStringSync(key, json);
    }

    /**
     * get the saved object from shared preferences
     *
     * @param key      - key for the requested value
     * @param defValue - default in case the key not exist
     * @param type     the type of the excepted array - you can use getTypeToken in order to get this type
     * @return if the key exist his value will returned else devValue
     */
    public <T> T[] getArray(String key, T[] defValue, Type type) {
        String json = getString(key, "");
        if (json.isEmpty())
            return defValue;

        return gson.fromJson(json, type);
    }

    /**
     * save any type of ArrayList to shared preferences
     *
     * @param key   - key for the value
     * @param value - the value to save
     */
    public <T> void putArrayList(String key, ArrayList<T> value) {
        String json = gson.toJson((T[]) value.toArray());
        Log.d("pttt", "putObjectArrayList: " + json);
        putString(key, json);
    }

    /**
     * save any type of ArrayList to shared preferences
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return Returns true if the new values were successfully written to persistent storage.
     */
    public <T> boolean putArrayListSync(String key, ArrayList<T> value) {
        String json = gson.toJson((T[]) value.toArray());
        return putStringSync(key, json);
    }

    /**
     * get the saved object from shared preferences
     *
     * @param key      - key for the requested value
     * @param defValue - default in case the key not exist - same generic type as excepted ArrayList
     * @return if the key exist his value will returned else devValue
     */
    public <T> ArrayList<T> getArrayList(String key, ArrayList<T> defValue) {
        String json = getString(key, "");
        if (json.isEmpty())
            return defValue;

        Type type = new TypeToken<T[]>() {
        }.getType();

        return new ArrayList<T>(Arrays.asList(gson.fromJson(json, type)));
    }

    /**
     * save any type of HasMap to shared preferences
     *
     * @param key   - key for the value
     * @param value - the value to save
     */
    public <E, T> void putHashMap(String key, HashMap<E, T> value) {
        String json = gson.toJson(value);
        putString(key, json);
    }

    /**
     * save HasMap to shared preferences
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return Returns true if the new values were successfully written to persistent storage.
     */
    public <E, T> boolean putHashMapSync(String key, HashMap<E, T> value) {
        String json = gson.toJson(value);
        return putStringSync(key, json);
    }

    /**
     * get the saved object from shared preferences
     *
     * @param key      - key for the requested value
     * @param defValue - default in case the key not exist - same generic type as excepted HashMap
     * @return if the key exist his value will returned else devValue
     */
    public <E, T> HashMap<E, T> getHashMap(String key, HashMap<E, T> defValue) {
        String json = getString(key, "");
        if (json.isEmpty())
            return defValue;

        Type typeOfHashMap = new TypeToken<HashMap<E, T>>() {
        }.getType();
        return gson.fromJson(json, typeOfHashMap);
    }


}
