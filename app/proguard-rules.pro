# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/liliu/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
###umeng update module
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}
-keep public class com.jiuyescm.helper.R$*{
    public static final int *;
}
-keep public class com.umeng.fb.ui.ThreadView { }
# 以下类过滤不混淆
#-keep public class * extends com.umeng.**
# 以下包不进行过滤
#-keep class com.umeng.** { *; }
###umeng push
-keep class com.umeng.message.* {
        public <fields>;
        public <methods>;
}
-keep class com.umeng.message.protobuffer.* {
        public <fields>;
        public <methods>;
}
-keep class com.squareup.wire.* {
        public <fields>;
        public <methods>;
}
-keep class org.android.agoo.impl.*{
        public <fields>;
        public <methods>;
}
-keep class org.android.agoo.service.* {*;}
-keep class org.android.spdy.**{*;}
