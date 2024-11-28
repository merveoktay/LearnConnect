# LearnConnect App
LearnConnect, modern bir video tabanlı eğitim platformudur. Kullanıcıların kurslara kaydolabildiği hem de ders videolarını izleyebildiği bir uygulamadır. Kullanıcılar isterlerse beğendikleri kursları favorilerine ekleyebilmektedirler.
---

## Özellikler
- **Profil Yönetimi:** Kullanıcılar profil sayfasını görüntüleyebilir.
- **Favori Kurslar:** Kullanıcılar favori kurslarını kolayca görebilir.
- **Tema Seçimi:** Karanlık veya aydınlık mod arasında geçiş yapabilirsiniz.

---

## Kuallanıcı Giriş Ve Kayıt Ekran Görüntüleri
<p float="left">
  <img src=https://github.com/merveoktay/LearnConnect/blob/master/SplashScreen.png width="28%" />
  <img src=https://github.com/merveoktay/LearnConnect/blob/master/LoginScreen.png width="28%" />
  <img src=https://github.com/merveoktay/LearnConnect/blob/master/LoginScreen%20Entry.png width="28%" />
  <img src=https://github.com/merveoktay/LearnConnect/blob/master/RegisterScreen.png width="28%" />
</p>

## HomeScreen ve MyCoursesScreen Ekran Görüntüleri
<p float="left">
  <img src=https://github.com/merveoktay/LearnConnect/blob/master/HomeScreen.png width="28%" />
  <img src=https://github.com/merveoktay/LearnConnect/blob/master/MyCoursesScreen.png width="28%" />
</p>
---

## Kurulum

Bu projeyi kendi cihazınızda çalıştırmak için aşağıdaki adımları izleyin:

### 1. Gereksinimler
- Android Studio Arctic Fox veya daha yeni bir sürüm
- JDK 11 veya daha yeni
- Minimum API Seviyesi: 21 (Android 5.0)
- İnternet bağlantısı

### 2. Adımlar
1. Bu projeyi yerel makinenize klonlayın:
    ```bash
    git clone https://github.com/kullanici_adiniz/learnconnect.git
    ```

2. Android Studio'da projeyi açın:
   - `File` > `Open` > Klonladığınız proje klasörünü seçin.

3. Gerekli bağımlılıkları indirin:
   - Android Studio, Gradle bağımlılıklarını otomatik olarak indirecektir. Eğer bir sorun yaşarsanız, aşağıdaki komutu çalıştırabilirsiniz:
     ```bash
     ./gradlew build
     ```

4. Uygulamayı çalıştırın:
   - Bir emulator veya fiziksel cihaz seçin.
   - `Run` butonuna tıklayın.

---

## Kullanılan Teknolojiler
- **Kotlin:** Modern Android uygulama geliştirme için tercih edildi.
- **Jetpack Compose:** Dinamik ve etkili bir UI oluşturma.
- **Room:** Yerel veri tabanı yönetimi.
- **Hilt:** Bağımlılık enjeksiyonu yönetimi.
- **Material Design 3:** Daha iyi kullanıcı deneyimi için modern tasarım standartları.
- **MVVM Mimari Deseni:** Uygulama daha modüler ve kolay yönetilebilir bir hale getirildi.

---

## Gelecek Güncellemeler
- Kurs detayları sayfası.
- Kullanıcı bildirimleri.



