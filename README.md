# 2023 Çekirge Programı - USG Stajyer Challenge

<img src="https://user-images.githubusercontent.com/73544434/233783800-d74a5a5c-5816-43e5-ba7a-9c0e593ad2a2.png" width="192" height="192"/>

## Tech Stack 📚

* [Navigation](https://developer.android.com/jetpack/compose/navigation)

* [ViewModel](https://developer.android.com/jetpack/compose/libraries#viewmodel)

* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)

* [Accompanist](https://google.github.io/accompanist/insets/)

* [Animations](https://developer.android.com/jetpack/compose/animation)

* [Retrofit](https://square.github.io/retrofit)

* [Coil](https://coil-kt.github.io/coil)

* [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)

* [Splash Screen](https://developer.android.com/develop/ui/views/launch/splash-screen)

* [Mockito](https://site.mockito.org/)

* [Turbine](https://github.com/cashapp/turbine)

* [Mockk](https://mockk.io/)

* [Coroutine Test](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-test/)

## Outputs 🖼

|                        | Light | Dark |
|------------------------|-------|------|
| Splash                 |  <img src="https://user-images.githubusercontent.com/73544434/233789741-23d3b9da-5f80-4cec-b744-d74b79c4b354.gif" width="240" height="480"/>     | <img src="https://user-images.githubusercontent.com/73544434/233789694-32a7599c-13f1-4df0-84fb-19b634f55f9d.gif" width="240" height="480"/>     |
| Ana Sayfa              | <img src="https://user-images.githubusercontent.com/73544434/233788686-9bf06be3-4f63-4a91-9ef0-299506f466e3.png" width="240" height="480"/>      |  <img src="https://user-images.githubusercontent.com/73544434/233788690-0ece480b-b3bc-4a8c-81de-5007e244b484.png" width="240" height="480"/>    |
| Karakter Detay Sayfası | <img src="https://user-images.githubusercontent.com/73544434/233788684-e6bf4200-27af-499c-913c-da4e2e5f2423.png" width="240" height="480"/>      | <img src="https://user-images.githubusercontent.com/73544434/233788682-ebc79034-740c-4514-9cfe-e010e9232070.png" width="240" height="480"/>     |


### Portrait and Landing Outputs 📱

### Portrait

<div>
  
  <video src='https://user-images.githubusercontent.com/73544434/233793555-539e13e9-a5dc-4b92-bafa-9716bf6d2f10.mp4' />
  
</div>

### LandScape

<div>

<video src='https://user-images.githubusercontent.com/73544434/233794385-dc0c2aa1-c90c-44d2-804d-db8b42355ccb.mp4' />

</div>

## Tasks 📝

### Task 1 - Temel uygulamanın oluşturulması
* Çalışabilir temel bir uygulama hazırlanacaktır.
 
Uygulama sorunuz bir şekilde çalışmaktadır.

### Task 2 - Splash / Açılış sayfasının geliştirilmesi
* Bu sayfa sadece açılışta gösterilmelidir.
* Animasyon veya uygulamayı tanıtan bir resim içermelidir.
* Sayfada bir karşılama metni bulunmalıdır. Kullanıcı uygulamayı ilk kez açtığında
“Welcome!” yazmalıdır. Sonraki açılışlarda “Hello!” yazmalıdır.

|                | İlk Açılış | Sonraki Açılış |
|----------------|------------|----------------|
| Splash Sayfası | <img src="https://user-images.githubusercontent.com/73544434/233795748-cce08788-a1dc-43bc-b077-997c61ec64a1.gif" width="280" height="520"/>            | <img src="https://user-images.githubusercontent.com/73544434/233795715-46807762-ce0c-44ae-a1e7-f32ad6372e8b.gif" width="240" height="480"/>               |


### Task 3 - Ana sayfanın oluşturulması
* Sayfa; başlık/logo, kayan yatay liste ve dikey listeden oluşmalıdır.

Ana sayfa bir başlık, kayan liste ve dikey liste barındırmaktadır. İlgili video yukarıda <b>Portrait and Landing Outputs 📱</b> başlığı altındadır.

<img src="https://user-images.githubusercontent.com/73544434/233788686-9bf06be3-4f63-4a91-9ef0-299506f466e3.png" width="240" height="480"/>

### Task 4 - Kayan liste içeriğinin doldurulması
* Yatay listede Rick and Morty lokasyonları olmalıdır.
* İçerik buradan çekilmelidir: https://rickandmortyapi.com/documentation/#locationschema
* İlk sayfanın listelenmesi yeterlidir (20 lokasyon).
* Seçili lokasyon ile seçili olmayan lokasyonların tasarımları farklı olmalıdır.

Yatay liste verilen API'dan gelen lokasyon verileri ile doldurulmuştur. Seçili olmayan lokasyonların lokasyon resimlerine ve arka plan renklerine gri filtre uygulanmıştır. Seçili lokasyon orjinal bir şekilde gözükmektedir. Böylelikle seçili ve seçili olmayan lokasyonlar ayırt edilebilmektedir. İlgili video <b>Portrait and Landing Outputs 📱</b> başlığı altındadır.

### Task 5 - Dikey listenin doldurulması
* Yatay listeden çekilen lokasyona ait karakterler listelenmelidir.
* Lokasyon cevabında karakterlere (residents) ait url’ler gelmektedir. Bu url’lerdeki id’ler
ayıklanarak bu kaynaktan karakter detayları çekilecektir. Kaynak: https://rickandmortyapi.com/documentation/#get-multiple-characters

Dikey liste, seçili lokasyondan gelen residents url'lerinden ayıklanan karakter id'leri vasıtasıyla doldurulur. İlgili API'dan karakter id'leri kullanılarak karakterler alınır. İlgili video <b>Portrait and Landing Outputs 📱</b> başlığı altındadır.


### Task 6 - Dikey listedeki item’ların farklılaştırılması
* Item tasarımları listenen karakterlerin cinsiyetlerine göre farklılık göstermelidir. Male, female, genderless veya unknown (mockup’ta görebilirsiniz.).

API dokümanlarında 4 cinsiyet olduğu belirtilmiş. Uygulamada 4 cinsiyetide temsil eden 4 resim bulunmaktadır. Bu resimler karakterin cinsiyetine göre atanmaktadır.

<img src="https://user-images.githubusercontent.com/73544434/233788686-9bf06be3-4f63-4a91-9ef0-299506f466e3.png" width="240" height="480"/>


### Task 7 - Detay sayfasının eklenmesi
* Tasarımı iletilen sayfanın geliştirilmesi gerekmektedir.
* Dikey listeden item’a tıklandığında bu sayfa açılmalıdır.

Detay sayfası, ana ekranda bulunan karakter listesinden bir karaktere tıklandığı zaman açılmaktadır. Karakter detay sayfası iletilen tasarıma uygun bir şekilde geliştirilmiştir.

<img src="https://user-images.githubusercontent.com/73544434/233788684-e6bf4200-27af-499c-913c-da4e2e5f2423.png" width="240" height="480"/>


### Task 8 - Yatay kayan listeye (Lokasyon listesi) lazy load eklenmesi
* Ana sayfadaki yatay liste scroll edilerek sona (sağa) dayandığında bir sonraki sayfa çekilmelidir.
* Yüklenme esnasında liste sonuna loading item eklenmelidir.

Yatay liste sona doğru dayandığında bir sonraki sayfayı çeker. Liste çekilebilecek başka sayfa kalmayıncaya dek her sona doğru dayandığında sayfa çeker. Yeni sayfanın yüklenmesi esnasında loading item gözükür.


<img src="https://user-images.githubusercontent.com/73544434/233797509-81bddc63-8474-4e59-9a52-58158e076f00.gif" width="240" height="480"/>

