
  

# Welcome To Prefy!

[![](https://jitpack.io/v/paz-lavi/Prefy.svg)](https://jitpack.io/#paz-lavi/Prefy) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/paz-lavi/Prefy/blob/master/LICENSE) [![GitHub forks](https://img.shields.io/github/forks/paz-lavi/Prefy)](https://github.com/paz-lavi/Prefy/network) [![GitHub stars](https://img.shields.io/github/stars/paz-lavi/Prefy)](https://github.com/paz-lavi/Prefy/stargazers) [![GitHub issues](https://img.shields.io/github/issues/paz-lavi/Prefy)](https://github.com/paz-lavi/Prefy/issues)

## Table of Contents

*  [Usage](https://github.com/paz-lavi/Prefy/blob/master/README.md#usage)
 *  [What's New](https://github.com/paz-lavi/Prefy/blob/master/README.md#what's-new)
    *  [V1.0.5:](https://github.com/paz-lavi/Prefy/blob/master/README.md#v1.0.5:)
 *  [Integration](https://github.com/paz-lavi/Prefy/blob/master/README.md#integration)

*  [How To Use](https://github.com/paz-lavi/Prefy/blob/master/README.md#how-to-use)

*  [API](https://github.com/paz-lavi/Prefy/blob/master/README.md#api)
	*  [String](https://github.com/paz-lavi/Prefy/tree/master#string)

	*  [Boolean](https://github.com/paz-lavi/Prefy/tree/master#boolean)

	*  [Integer](https://github.com/paz-lavi/Prefy/tree/master#integer)

	*  [Float](https://github.com/paz-lavi/Prefy/tree/master#float)

	*  [Long](https://github.com/paz-lavi/Prefy/tree/master#long)

	*  [Double](https://github.com/paz-lavi/Prefy/tree/master#double)

	*  [String Set](https://github.com/paz-lavi/Prefy/tree/master#string-set)

	*  [Generic Object](https://github.com/paz-lavi/Prefy/tree/master#generic-object)

	*  [Generic Array](https://github.com/paz-lavi/Prefy/tree/master#generic-array)

	*  [ArrayList](https://github.com/paz-lavi/Prefy/tree/master#arraylist)

	*  [HashMap](https://github.com/paz-lavi/Prefy/tree/master#hashmap)

	*  [Remove](https://github.com/paz-lavi/Prefy/tree/master#remove)

	*  [Type](https://github.com/paz-lavi/Prefy/tree/master#type)

*  [PrefyMsg](https://github.com/paz-lavi/Prefy/blob/master/README.md#prefymsg)
*  [License](https://github.com/paz-lavi/Prefy/blob/master/README.md#license)

## Usage

**Prefy** is an android library that will make your life with sharedPreferences easy than ever! Just choose your preferred one, the regular sharedPreferences, or the encrypted sharedPreferences with AES256.

  
## What's New
### V1.0.5: 
* New API methos to put value only if the key is not exist.
* New API methos to get the value and remove the key.
* New return type. more info [here](https://github.com/paz-lavi/Prefy/blob/master/README.md#prefymsg). 

## Integration

Add it in your root build.gradle at the end of repositories:

```css

allprojects {

repositories {

...

maven { url 'https://jitpack.io' }

}

}

```

Add the dependency

```css

dependencies {

implementation 'com.github.paz-lavi:Prefy:1.0.4'

}

```

## How To Use

**1.** Initialize **Prefy** in your `Application` class

```Java

init(Context context, boolean encrypted)

```

For example:

```Java

public  class  MyApp  extends  Application {

@Override

public  void  onCreate() {

super.onCreate();

Prefy.init(this,true);

}

}

```

**2.** Get a **Prefy** instanse

```Java

Prefy  prefy = Prefy.getInstance();

```

or whenever you need just use `Prefy.getInstance()` for example:

```Java

Prefy.getInstance().getInt("myKey" , 0);

```

  
  

## API

  

### String

```Java

public  void  putString(String key, String value);

```

```Java

public  boolean  putStringSync(String key, String value);

```

```Java

public  String  getString(String key, String defValue);

```

```Java

public  boolean  putStringIfKeyNotExist(String key, String value);

```

```Java

public  PrefyMsg  putStringSyncIfKeyNotExist(String key, String value);

```

```Java

public  String  getStringAndRemoveKey(String key, String defValue);

```

example:

```Java

prefy.putString("key1", "prefy is ");

prefy.putStringSync("key2", "awesome");

prefy.getString("key1", "");

prefy.getString("key2", "");

```

  

### Boolean

```Java

public  void  putBoolean(String key, boolean value);

```

```Java

public  boolean  putBooleanSync(String key, boolean value);

```

```Java

public  boolean  getBoolean(String key, boolean defValue);

```

```Java

public  boolean  putBooleanIfKeyNotExist(String key, boolean value);

```

```Java

public  PrefyMsg  putBooleanSyncIfKeyNotExist(String key, boolean value);

```

```Java

public  boolean  getBooleanAndRemoveKey(String key, boolean defValue);

```

example:

```Java

prefy.putBoolean("key1", true);

prefy.putBooleanSync("key2", false);

prefy.getBoolean("key1", false);

prefy.getBoolean("key2", true);

```

### Integer

```Java

public  void  putInt(String key, int value)

```

```Java

public  boolean  putIntSync(String key, int value)

```

```Java

public  int  getInt(String key, int defValue)

```

```Java

public  boolean  putIntIfKeyNotExist(String key, int value)

```

```Java

public  PrefyMsg  putIntSyncIfKeyNotExist(String key, int value)

```

```Java

public  int  getIntAndRemoveKey(String key, int defValue)

```

example:

```Java

prefy.putInt("key1", 1);

prefy.putIntSync("key2", 96);

prefy.getInt("key1", 29);

prefy.getInt("key2", 18);

```

### Float

```Java

public  void  putFloat(String key, float value);

```

```Java

public  boolean  putFloatSync(String key, float value);

```

```Java

public  float  getFloat(String key, float defValue);

```

```Java

public  boolean  putFloatIfKeyNotExist(String key, float value);

```

```Java

public  PrefyMsg  putFloatSyncIfKeyNotExist(String key, float value);

```

```Java

public  float  getFloatAndRemoveKey(String key, float defValue);

```

example:

```Java

prefy.putFloat("key1", 1.03f);

prefy.putFloatSync("key2", 96.65f);

prefy.getFloat("key1", 0f);

prefy.getFloat("key2", 0f);

```

### Long

```Java

public  void  putLong(String key, long value)

```

```Java

public  boolean  putLongSync(String key, long value)

```

```Java

public  long  getLong(String key, long defValue)

```

```Java

public  boolean  putLongIfKeyNotExist(String key, long value)

```

```Java

public  PrefyMsg  putLongSyncIfKeyNotExist(String key, long value)

```

```Java

public  long  getLongAndRemoveKey(String key, long defValue)

```

example:

```Java

prefy.putLong("key1", 66656466565541454L);

prefy.putLongSync("key2", 12121L);

prefy.getLong("key1", 0L);

prefy.getLong("key2", 0L);

```

### Double

```Java

public  void  putDouble(String key, double value);

```

```Java

public  boolean  putDoubleSync(String key, double value);

```

```Java

public  double  getDouble(String key, double defValue);

```

```Java

public  boolean  putDoubleIfKeyNotExist(String key, double value);

```

```Java

public  PrefyMsg  putDoubleSyncIfKeyNotExist(String key, double value);

```

```Java

public  double  getDoubleAndRemoveKey(String key, double defValue);

```

example:

```Java

prefy.putDouble("key1", 64.40);

prefy.putDoubleSync("key2", 6.23);

prefy.getDouble("key1", 29.01);

prefy.getDouble("key2", 0.0);

```

### String Set

```Java

public  void  putStringSet(String key, Set<String> value)

```

```Java

public  boolean  putStringSetSync(String key, Set<String> value)

```

```Java

public  Set<String> getStringSet(String key, Set<String> defValue)

```

```Java

public  boolean  putStringSetIfKeyNotExist(String key, Set<String> value)

```

```Java

public  PrefyMsg  putStringSetSyncIfKeyNotExist(String key, Set<String> value)

```

```Java

public  Set<String> getStringSetAndRemoveKey(String key, Set<String> defValue)

```

example:

```Java

Set<String> s1 = new  HashSet<String>();

Set<String> s2 = new  HashSet<String>();

s1.add("prefy");

s1.add("is");

s2.add("awesome");

s2.add("!");

prefy.getStringSet("key1", s1);

prefy.getStringSet("key2", s2);

prefy.getStringSet("key1", new  HashSet<String>());

prefy.getStringSet("key2", new  HashSet<String>());

```

### Generic Object

```Java

public <T> void  putObject(String key, T value)

```

```Java

public <T> boolean  putObjectSync(String key, T value)

```

```Java

public <T> T  getObject(String key, T defValue, Class<T> type)

```
```Java
public <T> boolean putObjectIfKeyNotExist(String key, T value)

```

```Java

public <T> PrefyMsg putObjectSyncIfKeyNotExist(String key, T value)

```

```Java

public <T> T  getObjectAndRemoveKey(String key, T defValue, Class<T> type)

```

example:

```Java

Person  p1 = new  Person("prefy", "1234567890");

Person  p2 = new  Person("is awesome", "9876543210");

  

prefy.putObject("key1", p1);

prefy.putObjectSync("key2", p2);

  

Person  p3 = prefy.getObject("key1", new  Person("", ""), Person.class);

Person  p4 = prefy.getObject("key2", new  Person("", ""), Person.class);

```

### Generic Array

```Java

public <T> void  putArray(String key, T[] value) ;

```

```Java

public <T> boolean  putArraySync(String key, T[] value) ;

```

```Java

public <T> T[] getArray(String key, T[] defValue, Type type);

```
```Java
public <T> boolean putArrayIfKeyNotExist(String key, T[] value) ;

```

```Java

public <T> PrefyMsg putArraySyncIfKeyNotExist(String key, T[] value) ;

```

```Java

public <T> T[] getArrayAndRemoveKey(String key, T[] defValue, Type type);

```

example:

```Java

String[] array1 = new  String[]{"prefy", "is", "awesome", "!"};

int[] array2 = new  int[]{1, 2, 3, 4, 5};

Person[] persons = new  Person[]{new  Person("prefy", "1234567890"),

new  Person("is awesome", "9876543210")};

prefy.putArray("key1", array1);

prefy.putArraySync("key2", Arrays.stream(array2).boxed().toArray(Integer[]::new));

prefy.putArray("key3", persons);

  

String[] strings;

Integer[] integers;

Person[] peoples;

  

Type  type1 = prefy.getTypeToken(String[].class);

Type  type2 = prefy.getTypeToken(Integer[].class);

Type  type3 = prefy.getTypeToken(Person[].class);

  

Type  type = prefy.getTypeToken(String[].class);

strings = prefy.getArray("key1", new  String[]{"prefy"}, type1);

integers = prefy.getArray("key2", new  Integer[]{0}, type2);

peoples = prefy.getArray("key3", new  Person[20], type3);

```

### ArrayList

```Java

public <T> void  putArrayList(String key, ArrayList<T> value);

```

```Java

public <T> ArrayList<T> getArrayList(String key, ArrayList<T> defValue);

```

```Java

public <T> ArrayList<T> getArrayList(String key, ArrayList<T> defValue);

```

```Java

public <T> void  putArrayListAndRemoveKey(String key, ArrayList<T> value);

```

```Java

public <T> ArrayList<T> getArrayListAndRemoveKey(String key, ArrayList<T> defValue);

```

```Java

public <T> ArrayList<T> getArrayListIfKeyNotExist(String key, ArrayList<T> defValue);

```

example:

```Java

ArrayList<String> l1 = new  ArrayList<>();

ArrayList<String> l2 = new  ArrayList<>();

l1.add("prefy");

l1.add("is");

l2.add("awesome");

l2.add("!");

prefy.putArrayList("key1", arrayList);

prefy.putArrayListSync("key2", arrayList);

  

ArrayList<String> l3 = prefy.getArrayList("Key1", new  ArrayList<String>());

ArrayList<String> l4 = prefy.getArrayList("Key2", new  ArrayList<String>());

```

### HashMap

```Java

public <E, T> void  putHashMap(String key, HashMap<E, T> value)

```

```Java

public <E, T> boolean  putHashMapSync(String key, HashMap<E, T> value)

```

```Java

public <E, T> HashMap<E, T> getHashMap(String key, HashMap<E, T> defValue)

```

```Java

public <E, T> boolean  putHashMapIfKeyNotExist(String key, HashMap<E, T> value)

```

```Java

public <E, T> PrefyMsg  putHashMapSyncIfKeyNotExist(String key, HashMap<E, T> value)

```

```Java

public <E, T> HashMap<E, T> getHashMapAndRemoveKey(String key, HashMap<E, T> defValue)

```

example:

```Java

HashMap<String, String> map1 = new  HashMap<>();

HashMap<String, Integer> map2 = new  HashMap<>();

map1.put("k1", "prefy");

map2.put("k2", 112);

prefy.putHashMap("key1", map1);

prefy.putHashMapSync("key2", map2);

HashMap<String, String> map3;

HashMap<String, Integer> map4;

map3 = prefy.getHashMap("key1", new  HashMap<String, String>());

map4 = prefy.getHashMap("key2", new  HashMap<String, Integer>());

```

### Remove

```Java

public  void  remove(String key);

```

example:

```Java

prefy.putString("key1", "prefy is ");

prefy.putStringSync("key2", "awesome");

prefy.remove("key1");

prefy.remove("key2");

```

  
  

### Type

```Java

public  Type  getTypeToken(Type _class);

```

example:

```Java

Type  type = prefy.getTypeToken(String[].class);

```

  
## PrefyMsg
An enum that representing the return on `put<TYPE>SyncIfKeyNotExist(String key,<TYPE> defValue);` methods. the options are: 
* `key_already_exist`  - the key is already saved to sharedPreferences
* `saved_successfully` - the value saved successfully to sharedPreferences
* `not_saved` - the value not saved to sharedPreferences

## License

```

Copyright 2020 Paz Lavi

Licensed under the Apache License, Version 2.0 (the "License");

you may not use this file except in compliance with the License.

You may obtain a copy of the License at

https://github.com/paz-lavi/Prefy/blob/master/LICENSE

Unless required by applicable law or agreed to in writing, software

distributed under the License is distributed on an "AS IS" BASIS,

WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.

See the License for the specific language governing permissions and

limitations under the License.

```