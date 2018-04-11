# Scrollable Zoom 
[![](https://www.jitpack.io/v/DigitalBitHub/ScrollableZoom.svg)](https://www.jitpack.io/#DigitalBitHub/ScrollableZoom)

# Usage
Step 1. Add the JitPack repository to your build file
```groovy
allprojects {
        repositories {
            ...
            maven { url "https://jitpack.io" }
        }
    }
```
Step 2. Add the dependency
```groovy
dependencies {
  compile 'com.github.DigitalBitHub:ScrollableZoom:1.0'
 }
 ```
  
# Implementation
### XML Implementation:
```xml
<com.digitalbithub.scrollablezoom.ZoomableImage
  android:id="@+id/image"
  android:layout_width="match_parent"
  android:layout_height="match_parent"
  app:imageScr="@drawable/image" />
```
### Java Implementation:
```java
ZoomableImage zoomableImage = findViewById(R.id.image);
diagonalView.setBitmap(bitmap);
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
