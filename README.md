# Magnifize
[![](https://www.jitpack.io/v/DigitalBitHub/Magnifize.svg)](https://www.jitpack.io/#DigitalBitHub/Magnifize)

A Library to add Magnify effect on image.

#### Checkout the video demo http://www.youtube.com/watch?v=-7zJ0ILCi4o
[![Magnifize Demo](http://img.youtube.com/vi/-7zJ0ILCi4o/0.jpg)](http://www.youtube.com/watch?v=-7zJ0ILCi4o)

# Usage
#### Step 1. Add the JitPack repository to your build file
```groovy
allprojects {
        repositories {
            ...
            maven { url "https://jitpack.io" }
        }
    }
```
#### Step 2. Add the dependency
```groovy
dependencies {
  compile 'com.github.DigitalBitHub:Magnifize:1.3'
 }
 ```
  
# Implementation
### XML Implementation:
```xml
<com.digitalbithub.magnifize.MagnifizeView
  android:id="@+id/image"
  android:layout_width="match_parent"
  android:layout_height="match_parent"
  app:imageScr="@drawable/image" />
```
### Java Implementation:
```java
MagnifizeView magnifizeView = findViewById(R.id.image);
magnifizeView.setBitmap(bitmap);
```
# Licence
```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
