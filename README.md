# LoadingEditText

The library is an advanced edit text with a rigth drawable that allows you to show the progress bar when you click on the right icon. For example, you can use component for geolocation request.

Component extended **EditText** and using **animated vector** drawable for progress animation. Used right icon and animation are fully customizable.

# Usage

[ ![Download](https://api.bintray.com/packages/superjob/android/loadingedittext/images/download.svg) ](https://bintray.com/superjob/android/loadingedittext/_latestVersion)
## Add to dependencies
```java
dependencies {
    implementation 'ru.superjob:loadingedittext:1.0.1'
}
```
#### Enable support vectorsEnable support vectors
```
defaultConfig {
		...
		vectorDrawables.useSupportLibrary = true
		...
	}
```

## Layout XML
```xml
<ru.superjob.loadingedittext.LoadingEditText
		android:id="@+id/locationEditText"
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		android:background="@null"
		android:text="Moscow, Tverskaya street, 24"
		app:lockingField="true" />
```

##Control state:
You can control display the progress bar.
If you want hide progress call method
```java
 loadingEditText.setLoading(false)
```

## Customize

#### Right drawable
You can use own right drawable:

##### In XML
```xml
<ru.superjob.loadingedittext.LoadingEditText
...
android:drawableRight="@drawable/ic_circle"
android:drawableEnd="@drawable/ic_circle"
/>
```

##### In code
```java
loadingEditText.setRightDrawable(R.drawable.ic_near_me_gray)
```
#### Progress animation
##### In XML
```xml
<ru.superjob.loadingedittext.LoadingEditText
...
app:customLoadAnimation="@drawable/avd_anim"
/>
```

##### In code
```java
loadingEditText.setCustomLoadingDrawable(R.drawable.avd_anim)
```

## Adding a listener
You can handle click events on right drawable:

##### In Kotlin
```kotlin
  locationEditText.setActionClickListener {
            //handle action
        }
```

##### In Java
```java
loadingEditText.setActionClickListener(new LoadingEditText.ActionClickListener() {
			@Override
			public void onClicked() {
				 //handle action
			}
		});
```
# License

    Copyright 2018.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
