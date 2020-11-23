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

import static com.paz.prefy_lib.PrefyMsg.key_already_exist;
import static com.paz.prefy_lib.PrefyMsg.not_saved;
import static com.paz.prefy_lib.PrefyMsg.saved_successfully;

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
            } catch (GeneralSecurityException | IOException e) {
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
     * save String to shared preferences if the key is not already exist
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return true if the value added, false if the key already exist
     */
    public boolean putStringIfKeyNotExist(String key, String value) {
        if (isKeyExist(key)) {
            putString(key, value);
            return true;
        }
        return false;
    }

    /**
     * save String to shared preferences if the key is not already exist
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return If the key already exist return key_already_exist else Returns saved_successfully if the new values were successfully written to persistent storage and not_saved if not.
     */
    public PrefyMsg putStringSyncIfKeyNotExist(String key, String value) {
        if (isKeyExist(key)) {
            return putStringSync(key, value) ? saved_successfully : not_saved;
        }
        return key_already_exist;
    }

    /**
     * get the saved String and remove it from shared preferences
     *
     * @param key      - key for the requested value
     * @param defValue - default in case the key not exist
     * @return if the key exist his value will returned else devValue
     */
    public String getStringAndRemoveKey(String key, String defValue) {
        String res = getString(key, defValue);
        remove(key);
        return res;
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
     * save Boolean to shared preferences if the key is not already exist
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return true if the value added, false if the key already exist
     */
    public boolean putBooleanIfKeyNotExist(String key, boolean value) {
        if (isKeyExist(key)) {
            putBoolean(key, value);
            return true;
        }
        return false;
    }

    /**
     * save Boolean to shared preferences if the key is not already exist
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return If the key already exist return key_already_exist else Returns saved_successfully if the new values were successfully written to persistent storage and not_saved if not.
     */
    public PrefyMsg putBooleanSyncIfKeyNotExist(String key, boolean value) {
        if (isKeyExist(key)) {
            return putBooleanSync(key, value) ? saved_successfully : not_saved;
        }
        return key_already_exist;
    }

    /**
     * get the saved Boolean and remove it from shared preferences
     *
     * @param key      - key for the requested value
     * @param defValue - default in case the key not exist
     * @return if the key exist his value will returned else devValue
     */
    public boolean getBooleanAndRemoveKey(String key, boolean defValue) {
        boolean res = getBoolean(key, defValue);
        remove(key);
        return res;
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
     * save Int to shared preferences if the key is not already exist
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return true if the value added, false if the key already exist
     */
    public boolean putIntIfKeyNotExist(String key, int value) {
        if (isKeyExist(key)) {
            putInt(key, value);
            return true;
        }
        return false;
    }

    /**
     * save ___ to shared preferences if the key is not already exist
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return If the key already exist return key_already_exist else Returns saved_successfully if the new values were successfully written to persistent storage and not_saved if not.
     */
    public PrefyMsg putIntSyncIfKeyNotExist(String key, int value) {
        if (isKeyExist(key)) {
            return putIntSync(key, value) ? saved_successfully : not_saved;
        }
        return key_already_exist;
    }

    /**
     * get the saved Int and remove it from shared preferences
     *
     * @param key      - key for the requested value
     * @param defValue - default in case the key not exist
     * @return if the key exist his value will returned else devValue
     */
    public int getIntAndRemoveKey(String key, int defValue) {
        int res = getInt(key, defValue);
        remove(key);
        return res;
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
     * save Float to shared preferences if the key is not already exist
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return true if the value added, false if the key already exist
     */
    public boolean putFloatIfKeyNotExist(String key, float value) {
        if (isKeyExist(key)) {
            putFloat(key, value);
            return true;
        }
        return false;
    }

    /**
     * save Float to shared preferences if the key is not already exist
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return If the key already exist return key_already_exist else Returns saved_successfully if the new values were successfully written to persistent storage and not_saved if not.
     */
    public PrefyMsg putFloatSyncIfKeyNotExist(String key, float value) {
        if (isKeyExist(key)) {
            return putFloatSync(key, value) ? saved_successfully : not_saved;
        }
        return key_already_exist;
    }

    /**
     * get the saved Float and remove it from shared preferences
     *
     * @param key      - key for the requested value
     * @param defValue - default in case the key not exist
     * @return if the key exist his value will returned else devValue
     */
    public float getFloatAndRemoveKey(String key, float defValue) {
        float res = getFloat(key, defValue);
        remove(key);
        return res;
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
     * save Long to shared preferences if the key is not already exist
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return true if the value added, false if the key already exist
     */
    public boolean putLongIfKeyNotExist(String key, long value) {
        if (isKeyExist(key)) {
            putLong(key, value);
            return true;
        }
        return false;
    }

    /**
     * save Long to shared preferences if the key is not already exist
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return If the key already exist return key_already_exist else Returns saved_successfully if the new values were successfully written to persistent storage and not_saved if not.
     */
    public PrefyMsg putLongSyncIfKeyNotExist(String key, long value) {
        if (isKeyExist(key)) {
            return putLongSync(key, value) ? saved_successfully : not_saved;
        }
        return key_already_exist;
    }

    /**
     * get the saved Long and remove it from shared preferences
     *
     * @param key      - key for the requested value
     * @param defValue - default in case the key not exist
     * @return if the key exist his value will returned else devValue
     */
    public long getLongAndRemoveKey(String key, long defValue) {
        long res = getLong(key, defValue);
        remove(key);
        return res;
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
     * save Double to shared preferences if the key is not already exist
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return true if the value added, false if the key already exist
     */
    public boolean putDoubleIfKeyNotExist(String key, double value) {
        if (isKeyExist(key)) {
            putDouble(key, value);
            return true;
        }
        return false;
    }

    /**
     * save Double to shared preferences if the key is not already exist
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return If the key already exist return key_already_exist else Returns saved_successfully if the new values were successfully written to persistent storage and not_saved if not.
     */
    public PrefyMsg putDoubleSyncIfKeyNotExist(String key, double value) {
        if (isKeyExist(key)) {
            return putDoubleSync(key, value) ? saved_successfully : not_saved;
        }
        return key_already_exist;
    }

    /**
     * get the saved Double and remove it from shared preferences
     *
     * @param key      - key for the requested value
     * @param defValue - default in case the key not exist
     * @return if the key exist his value will returned else devValue
     */
    public double getDoubleAndRemoveKey(String key, double defValue) {
        double res = getDouble(key, defValue);
        remove(key);
        return res;
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
     * save StringSet to shared preferences if the key is not already exist
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return true if the value added, false if the key already exist
     */
    public boolean putStringSetIfKeyNotExist(String key, Set<String> value) {
        if (isKeyExist(key)) {
            putStringSet(key, value);
            return true;
        }
        return false;
    }

    /**
     * save StringSet to shared preferences if the key is not already exist
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return If the key already exist return key_already_exist else Returns saved_successfully if the new values were successfully written to persistent storage and not_saved if not.
     */
    public PrefyMsg putStringSetSyncIfKeyNotExist(String key, Set<String> value) {
        if (isKeyExist(key)) {
            return putStringSetSync(key, value) ? saved_successfully : not_saved;
        }
        return key_already_exist;
    }

    /**
     * get the saved StringSet and remove it from shared preferences
     *
     * @param key      - key for the requested value
     * @param defValue - default in case the key not exist
     * @return if the key exist his value will returned else devValue
     */
    public Set<String> getStringSetAndRemoveKey(String key, Set<String> defValue) {
        Set<String> res = getStringSet(key, defValue);
        remove(key);
        return res;
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
     * save Object to shared preferences if the key is not already exist
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return true if the value added, false if the key already exist
     */
    public <T> boolean putObjectIfKeyNotExist(String key, T value) {
        if (isKeyExist(key)) {
            putObject(key, value);
            return true;
        }
        return false;
    }

    /**
     * save Object to shared preferences if the key is not already exist
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return If the key already exist return key_already_exist else Returns saved_successfully if the new values were successfully written to persistent storage and not_saved if not.
     */
    public <T> PrefyMsg putObjectSyncIfKeyNotExist(String key, T value) {
        if (isKeyExist(key)) {
            return putObjectSync(key, value) ? saved_successfully : not_saved;
        }
        return key_already_exist;
    }

    /**
     * get the saved Object and remove it from shared preferences
     *
     * @param key      - key for the requested value
     * @param defValue - default in case the key not exist
     * @return if the key exist his value will returned else devValue
     */
    public <T> T getObjectAndRemoveKey(String key, T defValue, Class<T> type) {
        T res = getObject(key, defValue, type);
        remove(key);
        return res;
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
     * save Array to shared preferences if the key is not already exist
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return true if the value added, false if the key already exist
     */
    public <T> boolean putArrayIfKeyNotExist(String key, T[] value) {
        if (isKeyExist(key)) {
            putArray(key, value);
            return true;
        }
        return false;
    }

    /**
     * save ___ to shared preferences if the key is not already exist
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return If the key already exist return key_already_exist else Returns saved_successfully if the new values were successfully written to persistent storage and not_saved if not.
     */
    public <T> PrefyMsg putArraySyncIfKeyNotExist(String key, T[] value) {
        if (isKeyExist(key)) {
            return putArraySync(key, value) ? saved_successfully : not_saved;
        }
        return key_already_exist;
    }

    /**
     * get the saved Array and remove it from shared preferences
     *
     * @param key      - key for the requested value
     * @param defValue - default in case the key not exist
     * @return if the key exist his value will returned else devValue
     */
    public <T> T[] getArrayAndRemoveKey(String key, T[] defValue, Type type) {
        T[] res = getArray(key, defValue, type);
        remove(key);
        return res;
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
     * save ArrayList to shared preferences if the key is not already exist
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return true if the value added, false if the key already exist
     */
    public <T> boolean putArrayListIfKeyNotExist(String key, ArrayList<T> value) {
        if (isKeyExist(key)) {
            putArrayList(key, value);
            return true;
        }
        return false;
    }

    /**
     * save ArrayList to shared preferences if the key is not already exist
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return If the key already exist return key_already_exist else Returns saved_successfully if the new values were successfully written to persistent storage and not_saved if not.
     */
    public <T> PrefyMsg putArrayListSyncIfKeyNotExist(String key, ArrayList<T> value) {
        if (isKeyExist(key)) {
            return putArrayListSync(key, value) ? saved_successfully : not_saved;
        }
        return key_already_exist;
    }

    /**
     * get the saved ArrayList and remove it from shared preferences
     *
     * @param key      - key for the requested value
     * @param defValue - default in case the key not exist
     * @return if the key exist his value will returned else devValue
     */
    public <T> ArrayList<T> getArrayListAndRemoveKey(String key, ArrayList<T> defValue) {
        ArrayList<T> res = getArrayList(key, defValue);
        remove(key);
        return res;
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


    /**
     * save HashMap to shared preferences if the key is not already exist
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return true if the value added, false if the key already exist
     */
    public <E, T> boolean putHashMapIfKeyNotExist(String key, HashMap<E, T> value) {
        if (isKeyExist(key)) {
            putHashMap(key, value);
            return true;
        }
        return false;
    }

    /**
     * save HashMap to shared preferences if the key is not already exist
     *
     * @param key   - key for the value
     * @param value - the value to save
     * @return If the key already exist return key_already_exist else Returns saved_successfully if the new values were successfully written to persistent storage and not_saved if not.
     */
    public <E, T> PrefyMsg put___SyncIfKeyNotExist(String key, HashMap<E, T> value) {
        if (isKeyExist(key)) {
            return putHashMapSync(key, value) ? saved_successfully : not_saved;
        }
        return key_already_exist;
    }

    /**
     * get the saved HashMap and remove it from shared preferences
     *
     * @param key      - key for the requested value
     * @param defValue - default in case the key not exist
     * @return if the key exist his value will returned else devValue
     */
    public <E, T> HashMap<E, T> getHashMapAndRemoveKey(String key, HashMap<E, T> defValue) {
        HashMap<E, T> res = getHashMap(key, defValue);
        remove(key);
        return res;
    }

    /**
     * remove key from shared preferences
     *
     * @param key - the key that should remove
     */
    public void remove(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * check if key exist in sharedPreferences
     *
     * @param key - key to check
     * @return - true if exist false if not
     */
    private boolean isKeyExist(String key) {
        return sharedPreferences.contains(key);
    }


}
