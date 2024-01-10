# sdk/tools/proguard/proguard-android-optimize.txt
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-optimizationpasses 5
-allowaccessmodification
-dontpreverify
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose

# Đừng xóa mã vô dụng
-dontshrink

# Không làm xáo trộn các lớp chú thích
-keepattributes *Annotation*

# Đừng làm xáo trộn các phương thức gốc
-keepclasseswithmembernames class * {
    native <methods>;
}

# Không làm xáo trộn giá trị của thuộc tính onClick được đặt bởi Activity trong bố cục XML
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# Đừng nhầm lẫn các lớp enum
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Đừng nhầm lẫn giữa các lớp con có thể phân loại
-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

# Đừng nhầm lẫn giữa các lớp con Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}


# Không làm xáo trộn các trường trong tệp R
-keepclassmembers class **.R$* {
    public static <fields>;
}

# Đừng nhầm lẫn với tên phương thức của giao diện JS do WebView đặt
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

