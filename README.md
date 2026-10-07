# ملاحظات سريعة · QuickNotes

تطبيق أندرويد مكتوب بلغة **Kotlin** باستخدام **Jetpack Compose** و **Material 3**.
تطبيق ملاحظات كامل: إضافة وتعديل وحذف، بحث فوري، وتثبيت الملاحظات المهمة في الأعلى،
مع تخزين محلي دائم وواجهة عربية تدعم RTL.

An Android notes app written in **Kotlin** with **Jetpack Compose** and **Material 3**.

## التنزيل

أحدث ملف APK موقّع متاح من صفحة الإصدارات:

<https://github.com/alhibi/QuickNotes/releases/latest>

## المزايا

- كتابة / تعديل / حذف الملاحظات مع تأكيد الحذف.
- بحث فوري في العنوان والنص.
- تثبيت الملاحظات المهمة لتظهر أولًا.
- تخزين محلي دائم (SharedPreferences + JSON) بلا اتصال بالإنترنت وبلا صلاحيات.
- ثيم فاتح/داكن مع ألوان ديناميكية (Material You) على أندرويد 12+.
- اختبارات وحدة (JVM) لمنطق التصفية والترتيب.

## البنية

```
app/src/main/java/com/alhibi/quicknotes/
├── MainActivity.kt          # واجهة Compose
├── NotesViewModel.kt        # حالة الشاشة
├── data/NotesRepository.kt  # التخزين المحلي
├── model/Note.kt            # نموذج البيانات
├── model/NoteFilter.kt      # التصفية والترتيب (قابل للاختبار)
└── ui/theme/Theme.kt        # الثيم
```

## البناء محليًا

يتطلب JDK 17+ و Android SDK (platform 36 و build-tools 36).

```bash
echo "sdk.dir=$HOME/Library/Android/sdk" > local.properties
./gradlew test assembleDebug
```

الناتج:

```
app/build/outputs/apk/debug/app-debug.apk
```

التثبيت على جهاز موصول:

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## نسخة release موقّعة

أنشئ مفتاحًا ثم ملف `keystore.properties` في جذر المشروع (مُستثنى من Git):

```bash
keytool -genkeypair -v -keystore release.jks -alias quicknotes \
  -keyalg RSA -keysize 2048 -validity 10000
```

```properties
storeFile=release.jks
storePassword=******
keyAlias=quicknotes
keyPassword=******
```

ثم:

```bash
./gradlew assembleRelease
```

بدون ملف المفاتيح يُبنى الـ release غير موقّع، وتبقى نسخة الـ debug قابلة للتثبيت مباشرة.

## CI · GitHub Actions

سير العمل `.github/workflows/android.yml` يبني التطبيق ويشغّل اختبارات الوحدة على كل
دفعة (push) وطلب دمج، ويرفع ملف APK كأثر (artifact) قابل للتنزيل من تبويب Actions.

## الترخيص

MIT
