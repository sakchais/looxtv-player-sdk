# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

 #Keep all public classes, methods, and fields in the library
-keep public class net.thaicom.sdk.looxtv.** { *; }

# Keep all methods and fields in classes annotated with certain annotations
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Keep annotations that are necessary for runtime
-keepattributes *Annotation*

# Keep view binding or data binding generated classes
-keep class **.databinding.* { *; }

# Prevent obfuscation of any R classes
-keep class **.R$* { *; }

# Prevent warnings for missing resources or generated code
-dontwarn **.R

 #Keep the LooxPlayer class and all its public methods and fields
#-keep public class net.thaicom.sdk.looxtv.LooxPlayer {
#    public *;
#}
#
## Allow inner classes or nested classes of LooxPlayer to be kept if needed
#-keepclassmembers class net.thaicom.sdk.looxtv.LooxPlayer$* {
#    public *;
#}
#
## Obfuscate everything else in your library
#-keep class net.thaicom.sdk.looxtv.** { *; } # Optional: Keep internal references if needed
#-dontwarn net.thaicom.sdk.looxtv.**