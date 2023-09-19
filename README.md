# Rick And Morty

<img src="https://user-images.githubusercontent.com/73544434/233783800-d74a5a5c-5816-43e5-ba7a-9c0e593ad2a2.png" width="192" height="192"/>

The application is written in the Kotlin language. When the application is opened, it greets the user with a splash screen. If it's the first time the application is opened, it displays a "Welcome!" message to the user in subsequent openings, it shows a "Hello!" message with a Toast. On the main page, a horizontal list is displayed using the Paging 3 library, showing locations from the Rick and Morty series. Characters from the selected location are displayed in a vertical list. Clicking on one of the characters in the vertical list opens a character detail page.

The application works seamlessly with both light and dark themes. It is designed to be user-friendly in horizontal usage, with additional design adjustments made to make the main screen suitable for horizontal orientation. For the character detail page, only vertical scrolling has been added to stay in line with the provided design.

## Tech Stack 📚

* [Navigation](https://developer.android.com/jetpack/compose/navigation)

* [ViewModel](https://developer.android.com/jetpack/compose/libraries#viewmodel)

* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)

* [Accompanist](https://google.github.io/accompanist/systemuicontroller/)

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
| Home Screen              | <img src="https://user-images.githubusercontent.com/73544434/233788686-9bf06be3-4f63-4a91-9ef0-299506f466e3.png" width="240" height="480"/>      |  <img src="https://user-images.githubusercontent.com/73544434/233788690-0ece480b-b3bc-4a8c-81de-5007e244b484.png" width="240" height="480"/>    |
| Character Detail Screen | <img src="https://user-images.githubusercontent.com/73544434/233845663-1af03be4-368f-46a2-a098-801b2dd4cdeb.png" width="240" height="480"/>      | <img src="https://user-images.githubusercontent.com/73544434/233845654-094e6166-489e-4e49-aa5f-b4719ab12e8d.png" width="240" height="480"/>     |

### Error Handling ⚠
<div>

<img src="https://user-images.githubusercontent.com/73544434/233799949-14f5239b-532a-4c7c-b773-84d4e9ec71d3.gif" width="240" height="480"/>

<img src="https://user-images.githubusercontent.com/73544434/233800068-413af18e-bf02-4ce7-a0c9-ec477138e8f8.gif" width="240" height="480"/>

</div>


### Portrait and Landing Outputs

### Portrait

<div>
  
  <video src='https://user-images.githubusercontent.com/73544434/233845803-8640056d-b2ef-4d90-a160-d4e399b10975.mp4' />
  
</div>

### LandScape

<div>

<video src='https://user-images.githubusercontent.com/73544434/233845947-6c3744b7-bc2e-4de7-b732-f8fb67976aa8.mp4' />

</div>

<!--
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

Ana sayfa bir başlık, kayan liste ve dikey liste barındırmaktadır. İlgili [Video](#portrait-and-landing-outputs).

<img src="https://user-images.githubusercontent.com/73544434/233788686-9bf06be3-4f63-4a91-9ef0-299506f466e3.png" width="240" height="480"/>

### Task 4 - Kayan liste içeriğinin doldurulması
* Yatay listede Rick and Morty lokasyonları olmalıdır.
* İçerik buradan çekilmelidir: https://rickandmortyapi.com/documentation/#locationschema
* İlk sayfanın listelenmesi yeterlidir (20 lokasyon).
* Seçili lokasyon ile seçili olmayan lokasyonların tasarımları farklı olmalıdır.

Yatay liste verilen API'dan gelen lokasyon verileri ile doldurulmuştur. Seçili olmayan lokasyonların lokasyon resimlerine ve arka plan renklerine gri filtre uygulanmıştır. Seçili lokasyon orjinal bir şekilde gözükmektedir. Böylelikle seçili ve seçili olmayan lokasyonlar ayırt edilebilmektedir. İlgili 
[Video](#portrait-and-landing-outputs).

### Task 5 - Dikey listenin doldurulması
* Yatay listeden çekilen lokasyona ait karakterler listelenmelidir.
* Lokasyon cevabında karakterlere (residents) ait url’ler gelmektedir. Bu url’lerdeki id’ler
ayıklanarak bu kaynaktan karakter detayları çekilecektir. Kaynak: https://rickandmortyapi.com/documentation/#get-multiple-characters

Dikey liste, seçili lokasyondan gelen residents url'lerinden ayıklanan karakter id'leri vasıtasıyla doldurulur. İlgili API'dan karakter id'leri kullanılarak karakterler alınır. İlgili [Video](#portrait-and-landing-outputs).


### Task 6 - Dikey listedeki item’ların farklılaştırılması
* Item tasarımları listenen karakterlerin cinsiyetlerine göre farklılık göstermelidir. Male, female, genderless veya unknown (mockup’ta görebilirsiniz.).

API dokümanlarında 4 cinsiyet olduğu belirtilmiş. Uygulamada 4 cinsiyetide temsil eden 4 resim bulunmaktadır. Bu resimler karakterin cinsiyetine göre atanmaktadır.

<img src="https://user-images.githubusercontent.com/73544434/233788686-9bf06be3-4f63-4a91-9ef0-299506f466e3.png" width="240" height="480"/>


### Task 7 - Detay sayfasının eklenmesi
* Tasarımı iletilen sayfanın geliştirilmesi gerekmektedir.
* Dikey listeden item’a tıklandığında bu sayfa açılmalıdır.

Detay sayfası, ana ekranda bulunan karakter listesinden bir karaktere tıklandığı zaman açılmaktadır. Karakter detay sayfası iletilen tasarıma uygun bir şekilde geliştirilmiştir.

<img src="https://user-images.githubusercontent.com/73544434/233845663-1af03be4-368f-46a2-a098-801b2dd4cdeb.png" width="240" height="480"/>


### Task 8 - Yatay kayan listeye (Lokasyon listesi) lazy load eklenmesi
* Ana sayfadaki yatay liste scroll edilerek sona (sağa) dayandığında bir sonraki sayfa çekilmelidir.
* Yüklenme esnasında liste sonuna loading item eklenmelidir.

Yatay liste sona doğru dayandığında bir sonraki sayfayı çeker. Liste çekilebilecek başka sayfa kalmayıncaya dek her sona doğru dayandığında sayfa çeker. Yeni sayfanın yüklenmesi esnasında loading item gözükür. (İnternet hızının iyi olması halinde veri çok hızlı çekilebileceğinden kullanıcı lazy load'ı göremeyebilir.)


<img src="https://user-images.githubusercontent.com/73544434/233797509-81bddc63-8474-4e59-9a52-58158e076f00.gif" width="240" height="480"/> -->

## Architecture 🏗
The app uses MVVM [Model-View-ViewModel] architecture to have a unidirectional flow of data, separation of concern, testability, and a lot more.

![mvvm](https://user-images.githubusercontent.com/73544434/197416569-d42a6bbe-126e-4776-9c8f-2791925f738c.png)



















