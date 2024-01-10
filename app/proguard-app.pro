# Bỏ qua cảnh báo
#-ignorewarning

# Làm xáo trộn và bảo vệ một phần mã của dự án của riêng bạn và các gói jar của bên thứ ba được tham chiếu
#-libraryjars libs/xxxxxxxxx.jar

-keep class vn.techres.seniorsociable.http.api.** {
    <fields>;
}
-keep class vn.techres.seniorsociable.http.response.** {
    <fields>;
}
-keep class vn.techres.seniorsociable.http.model.** {
    <fields>;
}


-keepclassmembernames class ** {
    @vn.techres.seniorsociable.aop.Log <methods>;
}

-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

-keep class com.google.firebase.appcheck.** { *; }

-keep public class cn.jzvd.JZMediaSystem {*; }
#-keep public class cn.jzvd.demo.CustomMedia.CustomMedia {*; }
#-keep public class cn.jzvd.demo.CustomMedia.JZMediaIjk {*; }
#-keep public class cn.jzvd.demo.CustomMedia.JZMediaSystemAssertFolder {*; }

-keep class tv.danmaku.ijk.media.player.** {*; }
-dontwarn tv.danmaku.ijk.media.player.*
-keep interface tv.danmaku.ijk.media.player.** { *; }
-keep class io.agora.**{*;}
