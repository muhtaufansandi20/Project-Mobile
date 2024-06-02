# Project-Mobile
# Deskripsi Aplikasi
Nama Aplikasi : MarxBook

Aplikasi MarxBook bisa dibilang sebagai aplikasi perpustakan digital yang sederhana, karena didalamnya terdapat berbagai daftar buku beserta genrenya. Aplikasi ini menampilkan informasi lengkap mengenai buku, termasuk judul, penulis, tahun penerbitan, genre, dan deskripsi singkat dari setiap buku. Serta ada fitur pencarian/searching yang dapat memudahkan pengguna dalam mencari buku yang mereka inginkan. Saya membuat aplikasi ini karena saya memiliki idola yaitu Karl Marx seorang pecetus sosialisme dan komunisme yang terkenal dengan kritikannya terhadap sistem kapitalisme, oleh karena itu saya memilih untuk membuat aplikasi terkait dengan buku.
# Fitur Aplikasi
1. Login dan register : Memungkinkan pengguna membuat akun dan login ke aplikasi MarxBook
2. Home : Menyajikan rekomendasi buku dan menampilkan seluruh judul buku dari API
3. Searching :  Memungkinkan pengguna untuk mencari buku berdasarkan judul dan penulis, fungsinya untuk mengefisienkan waktu.
4. Baca Nanti : Pengguna dapat menyimpan buku yang mereka temukan untuk dibaca nanti.
5. Profile User : Menampilkan informasi akun pengguna seperti username dan email.
6. Logout : Memungkinkan pengguna untuk keluar dari akun mereka.

# Cara Penggunaan Aplikasi
1. Register : Jika belum memiliki akun maka pilih button register dan mengisi formulir pendaftarannya, setelah selesai klik button register dan akun sudah siap.
2. Login : dihalaman login isi form pendaftaran sesuai engan yang anda isi ketika melakukan register. Setelah itu klik tombol login dan nanti anda akan dibawa kehalaman Home.
3. Home : Dihalaman home anda akan melihat tampilan eluruh daftar buku beserta judul dan authornya. Anda bisa menggulir kebawah untuk melihat lebih banyak buku atau cara mudahnya bisa mencari judul buku lewat fitur searching di bagian atas pada halaman home. Selanjutnya terdapat icon save di di pojok atas untuk setiap buku, anda dapat klik icon tersebut untuk menambahkan buku ke "Baca nanti".
4. Baca nanti : Di halaman ini terdapat daftar buku yang di masukkan ke dalam "Baca Nanti", ketika item di klik maka akan di arahkan ke halaman detailActivity. Di halaman ini anda dapat melihat informasi detail tentang buku yang kamu baca.
5. Profile user : Jika ingin mengetahui informasi tentang akun anda, anda bisa memilih profile. Di dalam halaman ini nantinya anda bisa melihat username dan email anda lalu ada tombol logout jika anda ingin keluar dari aplikasi.
6. Logout : Ketika anda mengklik tombol logout di halaman profile maka anda akan keluar dari aplikasi an akan di bawa kehalaman login.

# Implementasi Teknis
1. Login dan register disini saya membuat dengan SQLite untuk membuatnya menyimpan orang yang regis dan login disini saya juga menggunakan sharepreferencesutils untuk mengsave orang yang login di database ada semua diatur.
2. di halaman fragment home atau mainactivity saya menggunakan search disertai dengan progress bar dan disini juga diatur tampilan recycler viewnya terdapat juga button untuk menyimpan buku yang ingin di baca nanti
3. di halaman fragment baca terdapat tampilan cardview Ketika kita tadi menyimpan buku pas di fragment home kemudian ada button juga untuk menghapus buku dari daftar baca nanti
4. di halaman profile terdapat nama username dan email yang login disini telah diatur di database

# Spesifikasi
1. Activity
2. Intent
3. RecyclerView
4. Fragment dan Navigation
5. Background Thread (Handler)
6. Networking
7. Local Data Persistent (SQLite)
