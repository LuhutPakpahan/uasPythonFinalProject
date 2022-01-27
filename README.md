<!-- TENTANG PROYEK -->
## Pemindaian Jerawat

Proyek saya adalah aplikasi untuk mendeteksi jenis jerawat pada kulit. Banyaknya jenis jerawat membuat kita sulit untuk menentukan jenis jerawat yang tepat. Hal ini akan mengakibatkan penanganan jerawat yang tidak tepat, sehingga tidak mereda atau malah bertambah parah. Dengan begitu kami ingin membantu masyarakat mengidentifikasi jenis jerawat pada kulitnya, sehingga dapat memberikan langkah pengobatan yang tepat. Untuk mengidentifikasi jerawat, pengguna hanya perlu memotret bagian kulit yang terdapat jerawat. Kemudian aplikasi akan memberikan hasil akhir berupa jenis jerawat dan langkah perawatan yang tepat.


### Kelompok 14

### NIM dan Nama Anggota Aktif

* 18.83.0341 - Robertus Luhut Pandapotan Pakpahan

## Deskripsi Pemakaian

Pertama, menyiapkan model tflite untuk dimasukkan langsung ke dalam proyek android. Berikut langkah-langkahnya:
* Unduh dataset [dermnet](www.dermnet.com0); [skin90](https://www.kaggle.com/dinartas/skin90); [skin50](https://www.kaggle.com/dinartas/skin50), prosesnya ada di notebook.
* Buat batch pelatihan dan validasi menggunakan generator kereta.
* Buat label dengan menggunakan fungsi generator kereta.
* Melatih model.
* Memvalidasi model.
* Gunakan konverter untuk mengubah model keras menjadi model tflite.
* Pindahkan file tflite dan labelnya ke dalam assets label di folder android.
* Buka FloatMobilenetClassifier dan ubah jalur menjadi model dan label kami sebelumnya.

Kedua, Anda perlu membuat proyek terlebih dahulu untuk terhubung ke proyek studio android di perangkat Anda. Berikut langkah-langkahnya:
* Buka [situs web firebase] (www.firebase.google.com), dan klik mulai
* Buat proyek baru di konsol firebase
* Saat proyek firebase dibuat, tambahkan aplikasi android Anda ke firebase dengan mengklik ikon android untuk menambahkan proyek
* Daftarkan aplikasi dengan mengisi nama nama paket android (wajib), nama aplikasi (opsional) dan penandatanganan debug SHA-1 (opsional)
* Setelah melakukannya, Anda akan membuat file yaitu "google-services.json". Anda akan mengganti file layanan google dengan yang Anda unduh
* Pada langkah selanjutnya Anda akan meminta add firebase sdk, Anda dapat melewatinya
* Setelah Anda selesai menyiapkan proyek firebase, sekarang Anda perlu menyiapkan beberapa layanan juga seperti firebase firestore, penyimpanan cloud, dan otentikasi

### Prasyarat

Ada beberapa kondisi sebelum Anda dapat mengkloning proyek ini dan menghubungkannya dengan proyek firebase Anda sendiri dan menggunakan model yang Anda buat sendiri
* [Versi terbaru studio android](https://developer.android.com/?hl=id)
* [Proyek Firebase](https://console.firebase.google.com/u/0/)
* [Model Tflite](https://www.tensorflow.org/lite/guide)

### Instalasi

1. Kloning repo https://github.com/LuhutPakpahan/Capstone.git
2. Ganti file google-services.json dengan milik Anda. File terletak di folder aplikasi ('folder root'/app/google-services.json)
3. Jangan lupa untuk memastikan bahwa proyek Anda sekarang terhubung ke proyek firebase
4. Untuk mengganti model saat ini dengan model Anda sendiri, klik kanan pada folder aplikasi dan klik New > Other > Tensorflow Lite Model
5. Masukkan model tflite kamu dengan cara mengarahkan lokasi model ke file tflite yang sudah kamu siapkan, lalu klik next dan finish
6. Buka "UploadDataActivity" dan ganti referensi kelas model yang ada dengan mengubah kode "Acnescan6" menjadi nama kelas model Anda.
7. Dan Anda siap melakukannya.
* Tips : Bisa menggunakan ctrl + r, lalu tulis "Acnescan6" dan isikan nama kelas model yang sudah anda masukkan


<!-- KONTRIBUSI -->
## Berkontribusi

Kontribusilah yang membuat komunitas open source menjadi tempat yang luar biasa untuk belajar, menginspirasi, dan berkreasi. Setiap kontribusi yang Anda berikan **sangat dihargai**.

1. Fork Proyek
2. Buat Cabang Fitur Anda (`git checkout -b fitur/AmazingFeature`)
3. Komit Perubahan Anda (`git commit -m 'Tambahkan beberapa Fitur Luar Biasa'`)
4. Dorong ke Cabang (`git push origin feature/AmazingFeature`)
5. Buka Permintaan Tarik