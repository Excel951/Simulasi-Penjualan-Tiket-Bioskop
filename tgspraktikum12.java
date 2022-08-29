import java.io.*;
import java.util.*;

public class tgspraktikum12 {

    private static BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));

    // data sementara Customer
    private static int menu, temp_city, temp_theather_nm, temp_type_theater, temp_mv, temp_countseat;
    // data sementara: pilihan menu, kota pelanggan, nama bioskop pelanggan,
    // tipe bioskop pelanggan, pilihan film, jml kursi

    private static int code_cust = 100, total = 0;

    // data nama customer, pilihan tempat duduk
    private static String temp_custname, temp_custseat;

    // storage data pelanggan
    private static Vector code_cust_store = new Vector<Integer>(); // kode customer/transaksi
    private static Vector cust_name = new Vector<String>(); // nama customer
    private static Vector cust_seat = new Vector<String>(); // kode pilihan tempat duduk
    private static Vector cust_city = new Vector<Integer>(); // kode kota customer
    private static Vector cust_theater_nm = new Vector<Integer>(); // kode nama bioskop pelanggan
    private static Vector cust_type_theater = new Vector<Integer>(); // kode tipe bioskop yang dipilih
    private static Vector cust_movie = new Vector<Integer>(); // kode movie/film
    private static Vector cust_countseat = new Vector<Integer>(); // jml kursi customer
    private static Vector cust_cost = new Vector<Integer>();

    // Movies Data
    private static Vector<String> mv_title; // nama film
    private static Vector<String> year, genre; // tahun produksi film, genre film
    private static Vector<Boolean> available_movie; // ketersediaan film

    // Input Movies Data
    private static String add_titlemv, add_year, add_genre;
    // input nama film, input tahun film, input genre

    // Theater Data
    private static Vector<String> city, theater_nm, theater_type;// kota, nm bioskop, jenis bioskop
    private static Vector<Integer> price;
    // static Vector<String> surabaya, malang; // nama bioskop berdasarkan kota
    // static Vector<String> xxi, cgv; // tipe bioskop berdasarkan nama bioskop

    // Seat Data (kode kota_nm bioskop_tipe bioskop_seat)
    private static Vector<String> seat_code = new Vector<>();
    private static Vector<Integer> sby_xxi_r_seat = new Vector<>();
    private static Vector<Integer> sby_cgv_r_seat = new Vector<>();
    private static Vector<Integer> sby_xxi_p_seat = new Vector<>();
    private static Vector<Integer> sby_cgv_p_seat = new Vector<>();
    private static Vector<Integer> mlg_xxi_r_seat = new Vector<>();
    private static Vector<Integer> mlg_cgv_r_seat = new Vector<>();
    private static Vector<Integer> mlg_xxi_p_seat = new Vector<>();
    private static Vector<Integer> mlg_cgv_p_seat = new Vector<>();

    // static int temp1 = cust_cost.get(index), temp2 = 0, temp3 = 0;

    static void menuTransaction1st() throws IOException { // menu awal transaksi
        garisBatas();
        System.out.println("\tPilih Kota");
        for (int i = 0; i < city.size(); i++)
            System.out.print("\n" + (i + 1) + ". " + city.get(i)); // menampilkan list kota
        System.out.print("\nMasukkan index menu (1/2) : ");
        temp_city = Integer.parseInt(rd.readLine()); // inputan pilih kota
        garisBatas();
        System.out.println("\tPilih Film");
        switch (temp_city) { // seleksi pada inputan yang dimasukkan customer
            case 1: // Surabaya
                menuTransaction2nd();
                break;

            case 2: // Malang
                menuTransaction2nd();
                break;

            default:
                System.out.println("Maaf, menu yang Anda pilih tidak tersedia");
                mainmenu();
                break;
        }
    }

    static void menuTransaction2nd() throws IOException { // menu transaksi pilih film
        for (int index = 0; index < mv_title.size(); index++) {
            System.out.print("\n" + (index + 1) + ". " + mv_title.get(index) + " (" + year.get(index) + ")\n"); // menampilkan
                                                                                                                // list
                                                                                                                // film
            if (available_movie.elementAt(index) != true)
                System.out.print(" [FILM TIDAK TERSEDIA]\n");
            System.out.println("\tGenre : " + genre.get(index)); // menampilkan genre film
        }
        System.out.print("\nMasukkan index menu (1/2/3/dst.) : ");
        temp_mv = Integer.parseInt(rd.readLine()); // memasukkan inputan customer
        garisBatas();
        menuTransaction3rd(0);
    }

    static void menuTransaction3rd(int index) throws IOException { // menu transaksi seleksi dan pilih bioskop
        if (index != temp_mv) { // seleksi pencocokan film
            menuTransaction3rd(index + 1); // looping
            // jika index > ukuran vektor film maka film yang dipilih tidak tersedia
        } else if (index > mv_title.size() || available_movie.elementAt(temp_mv - 1) != true) {
            System.out.println("Maaf, film yang Anda maksud tidak ada");
            System.out.print("Apakah Anda ingin memilih film kembali? (YA : 1/TIDAK : 0) : ");
            menu = Integer.parseInt(rd.readLine()); // inputan
            garisBatas();
            if (menu == 1) {
                menuTransaction2nd(); // jika customer memasukkan inputan 1, maka akan kembali ke menu pilih film
            } else {
                mainmenu();
            }
        } else { // jika inputan customer tersedia maka akan lanjut ke transaksi berikutnya
            System.out.print("\tPilih Bioskop");
            for (int i = 0; i < theater_nm.size(); i++) {
                System.out.print("\n" + (i + 1) + ". " + theater_nm.get(i)); // list bioskop yang tersedia
            }
            System.out.print("\nMasukkan index menu dari bioskop yang ingin dipilih (1/2) : ");
            temp_theather_nm = Integer.parseInt(rd.readLine()); // inputan
            garisBatas();
            menuTransaction4th(0);
        }
    }

    static void menuTransaction4th(int index) throws IOException {
        if (index != temp_theather_nm) { // seleksi pencocokan nama bioskop yang dipilih
            menuTransaction4th(index + 1); // looping
        } else if (index > theater_nm.size()) { // jika index > ukuran vektor nama bioskop, bioskop yang dipilih tdk
                                                // tersedia
            System.out.println("Maaf, bioskop yang Anda pilih tidak tersedia");
            System.out.print("Apakah Anda ingin memilih bioskop kembali? (YA : 1/TIDAK : 0) : ");
            menu = Integer.parseInt(rd.readLine()); // inputan
            garisBatas();
            if (menu == 1) {
                menuTransaction3rd(temp_theather_nm); // jika cust memasukkan 1 maka akan kembali memilih bioskop
            } else {
                mainmenu();
            }
        } else { // jika inputan customer tersedia maka akan lanjut ke transaksi berikutnya
            System.out.print("\tPilih Tipe Bioskop");
            for (int i = 0; i < theater_type.size(); i++) {
                System.out.print("\n" + (i + 1) + ". " + theater_type.get(i)); // list tipe bioskop
            }
            System.out.print("\nMasukkan index menu dari tipe bioskop yang ingin dipilih (1/2) : ");
            temp_type_theater = Integer.parseInt(rd.readLine()); // inputan
            garisBatas();
            menuTransaction5th(0);
        }
    }

    static void menuTransaction5th(int index) throws IOException {
        if (index != temp_type_theater) { // seleksi pencocokan tipe bioskop
            menuTransaction5th(index + 1); // looping
        } else if (index > theater_type.size()) { // jika index melebihi, maka tipe bioskop yang dipilih tdk tersedia
            System.out.println("Maaf, tipe bioskop yang Anda maksud tidak ada");
            System.out.print("Apakah Anda ingin memilih tipe bioskop kembali? (YA : 1/TIDAK : 0) : ");
            menu = Integer.parseInt(rd.readLine()); // inputan
            garisBatas();
            if (menu == 1) {
                menuTransaction4th(temp_theather_nm); // akan kembali ke menu input tipe bioskop
            } else {
                mainmenu();
            }
        } else {
            System.out.println("\tPilih Jumlah Kursi");
            System.out.print("Masukkan jumlah kursi yang ingin dipesan : ");
            temp_countseat = Integer.parseInt(rd.readLine()); // inputan jumlah kursi
            garisBatas();
            if (temp_countseat > 30) {
                System.out.println("Maaf, jumlah kursi tidak mencukupi");
                System.out.print("Apakah Anda ingin memilih kembali jumlah kursi? (YA : 1/TIDAK : 0) : ");
                menu = Integer.parseInt(rd.readLine());
                garisBatas();
                if (menu == 1) {
                    menuTransaction5th(temp_type_theater);
                } else {
                    mainmenu();
                }
            }
            System.out.println("\tKursi yang tersedia");
            seat_selection(0); // menampilkan kursi yang tersedia berdasarkan kota, bioskop, tipe bioskop
        }
    }

    static void inputCustData() throws IOException {
        System.out.print("\nMasukkan nama Anda : ");
        temp_custname = rd.readLine(); // inputan nama
        cust_name.add(temp_custname); // inputkan nama ke storage nama cust

        System.out.print("Masukkan pilihan kursi : ");
        temp_custseat = rd.readLine(); // inputan kode tempat duduk
        cust_seat.add(temp_custseat); // inputkan kode tempat duduk ke storage cust seat

        garisBatas();

        code_cust_store.add(code_cust); // menyimpan data yang telah diinputkan
        cust_city.add(temp_city - 1); // menyimpan data yang telah diinputkan
        cust_theater_nm.add(temp_theather_nm - 1); // menyimpan data yang telah diinputkan
        cust_type_theater.add(temp_type_theater - 1); // menyimpan data yang telah diinputkan
        cust_movie.add(temp_mv - 1); // menyimpan data yang telah diinputkan
        cust_countseat.add(temp_countseat);
    }

    static void seat_selection(int countseat) throws IOException {
        while (countseat < temp_countseat) {
            switch (temp_city) { // Seleksi kota
                case 1: // Surabaya
                    switch (temp_theather_nm) { // Seleksi nama bioskop
                        case 1: // XXI
                            switch (temp_type_theater) { // Seleksi tipe bioskop
                                case 1: // Regular
                                    for (int i = 0; i < 10; i++) {
                                        System.out.print(seat_code.get(i) + " = " + sby_xxi_r_seat.get(i));
                                        System.out.print(
                                                "\t" + seat_code.get(i + 10) + " = " + sby_xxi_r_seat.get(i + 10));
                                        System.out.print(
                                                "\t" + seat_code.get(i + 20) + " = " + sby_xxi_r_seat.get(i + 20));
                                        System.out.println();
                                    }
                                    inputCustData();
                                    sby_xxi_r_seat.set(seat_code.indexOf(temp_custseat), 0); // mengubah nilai tempat
                                                                                             // duduk yang dipilih
                                                                                             // menjadi 0
                                    cust_cost.add(price.get(0));
                                    break;

                                case 2: // Premiere
                                    for (int i = 0; i < 10; i++) {
                                        System.out.print(seat_code.get(i) + " = " + sby_xxi_p_seat.get(i));
                                        System.out.print(
                                                "\t" + seat_code.get(i + 10) + " = " + sby_xxi_p_seat.get(i + 10));
                                        System.out.print(
                                                "\t" + seat_code.get(i + 20) + " = " + sby_xxi_p_seat.get(i + 20));
                                        System.out.println();
                                    }
                                    inputCustData();
                                    sby_xxi_p_seat.set(seat_code.indexOf(temp_custseat), 0); // mengubah nilai tempat
                                                                                             // duduk yang dipilih
                                                                                             // menjadi 0
                                    cust_cost.add(price.get(1));
                                    break;
                            }
                            break;

                        case 2: // CGV
                            switch (temp_type_theater) { // Seleksi tipe bioskop
                                case 1: // Regular
                                    for (int i = 0; i < 10; i++) {
                                        System.out.print(seat_code.get(i) + " = " + sby_cgv_r_seat.get(i));
                                        System.out.print(
                                                "\t" + seat_code.get(i + 10) + " = " + sby_cgv_r_seat.get(i + 10));
                                        System.out.print(
                                                "\t" + seat_code.get(i + 20) + " = " + sby_cgv_r_seat.get(i + 20));
                                        System.out.println();
                                    }
                                    inputCustData();
                                    sby_cgv_r_seat.set(seat_code.indexOf(temp_custseat), 0); // mengubah nilai tempat
                                                                                             // duduk yang dipilih
                                                                                             // menjadi 0
                                    cust_cost.add(price.get(2));
                                    break;

                                case 2: // Premiere
                                    for (int i = 0; i < 10; i++) {
                                        System.out.print(seat_code.get(i) + " = " + sby_cgv_p_seat.get(i));
                                        System.out.print(
                                                "\t" + seat_code.get(i + 10) + " = " + sby_cgv_p_seat.get(i + 10));
                                        System.out.print(
                                                "\t" + seat_code.get(i + 20) + " = " + sby_cgv_p_seat.get(i + 20));
                                        System.out.println();
                                    }
                                    inputCustData();
                                    sby_cgv_p_seat.set(seat_code.indexOf(temp_custseat), 0); // mengubah nilai tempat
                                                                                             // duduk yang dipilih
                                                                                             // menjadi 0
                                    cust_cost.add(price.get(3));
                                    break;
                            }
                            break;
                    }
                    break;

                case 2: // Malang
                    switch (temp_theather_nm) { // Seleksi nama bioskop
                        case 1: // XXI
                            switch (temp_type_theater) { // Seleksi tipe bioskop
                                case 1: // Regular
                                    for (int i = 0; i < 10; i++) {
                                        System.out.print(seat_code.get(i) + " = " + mlg_xxi_r_seat.get(i));
                                        System.out.print(
                                                "\t" + seat_code.get(i + 10) + " = " + mlg_xxi_r_seat.get(i + 10));
                                        System.out.print(
                                                "\t" + seat_code.get(i + 20) + " = " + mlg_xxi_r_seat.get(i + 20));
                                        System.out.println();
                                    }
                                    inputCustData();
                                    mlg_xxi_r_seat.set(seat_code.indexOf(temp_custseat), 0); // mengubah nilai tempat
                                                                                             // duduk yang dipilih
                                                                                             // menjadi 0
                                    cust_cost.add(price.get(0));
                                    break;

                                case 2: // Premiere
                                    for (int i = 0; i < 10; i++) {
                                        System.out.print(seat_code.get(i) + " = " + mlg_xxi_p_seat.get(i));
                                        System.out.print(
                                                "\t" + seat_code.get(i + 10) + " = " + mlg_xxi_p_seat.get(i + 10));
                                        System.out.print(
                                                "\t" + seat_code.get(i + 20) + " = " + mlg_xxi_p_seat.get(i + 20));
                                        System.out.println();
                                    }
                                    inputCustData();
                                    mlg_xxi_p_seat.set(seat_code.indexOf(temp_custseat), 0); // mengubah nilai tempat
                                                                                             // duduk yang dipilih
                                                                                             // menjadi 0
                                    cust_cost.add(price.get(1));
                                    break;
                            }
                            break;

                        case 2: // CGV
                            switch (temp_type_theater) { // Seleksi tipe bioskop
                                case 1: // Regular
                                    for (int i = 0; i < 10; i++) {
                                        System.out.print(seat_code.get(i) + " = " + mlg_cgv_r_seat.get(i));
                                        System.out.print(
                                                "\t" + seat_code.get(i + 10) + " = " + mlg_cgv_r_seat.get(i + 10));
                                        System.out.print(
                                                "\t" + seat_code.get(i + 20) + " = " + mlg_cgv_r_seat.get(i + 20));
                                        System.out.println();
                                    }
                                    inputCustData();
                                    mlg_cgv_r_seat.set(seat_code.indexOf(temp_custseat), 0); // mengubah nilai tempat
                                                                                             // duduk yang dipilih
                                                                                             // menjadi 0
                                    cust_cost.add(price.get(2));
                                    break;

                                case 2: // Premiere
                                    for (int i = 0; i < 10; i++) {
                                        System.out.print(seat_code.get(i) + " = " + mlg_cgv_p_seat.get(i));
                                        System.out.print(
                                                "\t" + seat_code.get(i + 10) + " = " + mlg_cgv_p_seat.get(i + 10));
                                        System.out.print(
                                                "\t" + seat_code.get(i + 20) + " = " + mlg_cgv_p_seat.get(i + 20));
                                        System.out.println();
                                    }
                                    inputCustData();
                                    mlg_cgv_p_seat.set(seat_code.indexOf(temp_custseat), 0); // mengubah nilai tempat
                                                                                             // duduk yang dipilih
                                                                                             // menjadi 0
                                    cust_cost.add(price.get(3));
                                    break;
                            }
                            break;
                    }
                    break;
            }
            countseat++;
        }
    }

    public static void garisBatas() {
        for (int i = 0; i < 20; i++) {
            System.out.print("=");
        }
        System.out.println();
    }

    static void inputData() {
        String[] ct = { "Surabaya", "Malang" }; // list kota
        String[] t_nm = { "XXI", "CGV" }; // list nama bioskop
        String[] t_type = { "Regular", "Premiere" }; // list nama bioskop
        String[] mov_title = { "Jurassic World Dominion", "Top Gun: Maverick",
                "Fantastic Beast: The Secrets of Dumbledore" }; // list nama film
        String[] mv_year = { "2022", "2022", "2022" }; // list tahun produksi film
        // list genre film
        String[] mv_genre = { "Action, Adventure, Science Fiction", "Action, Drama", "Adventure, Fantasy" };
        // list ketersediaan film
        Boolean[] avl_mov = { true, true, true };

        int[] seat_price = { 85000, 125000, 87000, 130000 }; // xx1_r, xxi_p, cgv_r, cgv_p
        city = new Vector<String>(Arrays.asList(ct));
        theater_nm = new Vector<String>(Arrays.asList(t_nm));
        theater_type = new Vector<String>(Arrays.asList(t_type));
        mv_title = new Vector<String>(Arrays.asList(mov_title));
        year = new Vector<String>(Arrays.asList(mv_year));
        genre = new Vector<String>(Arrays.asList(mv_genre));
        price = new Vector<Integer>();
        available_movie = new Vector<Boolean>(Arrays.asList(avl_mov));
        for (int i = 0; i < seat_price.length; i++) {
            price.add(seat_price[i]);
        }
        inputDataSeat(0);
        inputCodeSeat(0, 0);
    }

    static void inputDataSeat(int a) {
        if (a != 30) {
            sby_xxi_r_seat.add(1);
            sby_xxi_p_seat.add(1);
            sby_cgv_r_seat.add(1);
            sby_cgv_p_seat.add(1);
            mlg_xxi_r_seat.add(1);
            mlg_xxi_p_seat.add(1);
            mlg_cgv_r_seat.add(1);
            mlg_cgv_p_seat.add(1);
            inputDataSeat(a + 1);
        }
    }

    static void inputCodeSeat(int row, int col) {
        if (row == 0) {
            if (col != 10) {
                seat_code.add("A" + col);
                inputCodeSeat(row, col + 1);
            } else {
                inputCodeSeat(row + 1, 0);
            }
        } else if (row == 1) {
            if (col != 10) {
                seat_code.add("B" + col);
                inputCodeSeat(row, col + 1);
            } else {
                inputCodeSeat(row + 1, 0);
            }
        } else if (row == 2) {
            if (col != 10) {
                seat_code.add("C" + col);
                inputCodeSeat(row, col + 1);
            } else {
                inputCodeSeat(row + 1, 0);
            }
        }
    }

    // int index = 0
    static void printStruk(int index, int i, int code_cust) {
        // System.out.println(index + " " + i);

        if ((int) code_cust_store.elementAt(index) == code_cust) {
            int ctn = (int) cust_theater_nm.get(index); // simpan index nama bioskop pilihan customer
            int ctt = (int) cust_type_theater.get(index); // simpan index tipe bioskop pilihan customer
            int cm = (int) cust_movie.get(index); // simpan index nama film pilihan customer
            total += (int) cust_cost.get(index);

            System.out.println((i + 1) + ". Nama Bioskop : " + theater_nm.get(ctn));
            System.out.println("\tTipe Bioskop : " + theater_type.get(ctt));
            System.out.println(
                    "\tNama Film : " + mv_title.get(cm) + " (" + year.get(cm) + ")");
            System.out.println("\tNama : " + cust_name.get(index));
            System.out.println("\tTempat duduk : " + cust_seat.get(index));
            System.out.println("\tHarga : " + cust_cost.get(index));

            if (index < code_cust_store.size() - 1) {// (index < ukuran penyimpanan data kode transaksi - 1) supaya
                                                     // tidak terjadi outofbound
                printStruk(index + 1, i + 1, code_cust); // ulangi method ini dengan index + 1 dan i + 1
            } else {
                System.out.println("\nTotal : " + total);
                total = 0;
            }
        } else if (index == code_cust_store.size() - 1) {
            System.out.println("\nTotal : " + total);
            total = 0;
        } else {
            printStruk(index + 1, i, code_cust);
        }
    }

    static void printAll(int index, int i, int code_trx) {
        // System.out.println(index + " " + i + " " + code_trx);
        if (code_trx < 100) { // jika kode transaksi (diambil dari kode transaksi terbaru [code_cust]) < 100
            // maka method akan berhenti
        } else if ((int) code_cust_store.elementAt(index) == code_trx) { // jika kode transaksi (code_trx) ada di
                                                                         // penyimpanan data kode transaksi maka
            printStruk(0, i, code_trx); // print struk dgn parameter (index=0, i=0, kode dari transaksi saat ini)
            garisBatas(); // print garis pembatas
            printAll(0, i, code_trx - 1); // ulangi method ini untuk melakukan pencetakan transaksi lainnya
        } else if ((int) code_cust_store.elementAt(index) != code_trx) { // jika kode transaksi saat ini tidak ada dalam
                                                                         // data maka
            printAll(index + 1, i, code_trx); // ulangi method ini dgn index + 1, i=0, dan kode transaksi tetap
        }
    }

    static void printListFilm() {
        for (int index = 0; index < mv_title.size(); index++) {
            System.out.print("\n" + (index + 1) + ". " + mv_title.get(index) + " (" + year.get(index) + ")"); // menampilkan
                                                                                                              // list
                                                                                                              // film
            if (available_movie.elementAt(index) != true)
                System.out.print(" [FILM TIDAK TERSEDIA]\n");
            System.out.println("\n\tGenre : " + genre.get(index)); // menampilkan genre film
        }
    }

    static void editFilm(int seleksi) throws IOException {
        if (seleksi == 1) { // tambah film
            System.out.println("\tFILM YANG ADA");
            printListFilm();
            garisBatas();
            do {
                System.out.print("\nMasukkan Judul Film : ");
                add_titlemv = rd.readLine();
                System.out.print("\nMasukkan Tahun Produksi : ");
                add_year = rd.readLine();
                System.out.print("\nMasukkan List Genre (seperti Komedi, Acara Keluarga, dll.) : ");
                add_genre = rd.readLine();
                System.out.print("\nApakah ingin tambah film lagi? (YA : 1/TIDAK : 0) : ");
                menu = Integer.parseInt(rd.readLine()); // inputan

                mv_title.add(add_titlemv);
                year.add(add_year);
                genre.add(add_genre);
                available_movie.add(true);
            } while (menu == 1);
            garisBatas();
        } else if (seleksi == 2) { // hapus film
            printListFilm();
            garisBatas();
            System.out.print("Pilih nomor menu dari film yang akan dihapus [1-akhir] : ");
            menu = Integer.parseInt(rd.readLine());
            available_movie.set(menu - 1, false);
            garisBatas();
        }
    }

    public static void mainmenu() throws IOException {
        do {
            for (int i = 0; i < 3; i++) {
                if (i != 1) {
                    garisBatas();
                } else {
                    System.out.println("\tMenu");
                }
            }
            System.out.println("1. Transaksi\n2. Admin\n3. Exit");
            garisBatas();
            System.out.print("Masukkan nomor menu [1-3] : ");
            menu = Integer.parseInt(rd.readLine());
            garisBatas();
            switch (menu) {
                case 1: // proses transaksi
                    menuTransaction1st();
                    garisBatas();
                    System.out.println("\tStruk");
                    printStruk(0, 0, code_cust);
                    code_cust++;
                    break;

                case 2: // proses administrasi
                    // edit tayang film
                    // print semua transaksi
                    // System.out.println(code_cust_store);
                    // System.out.println(cust_name);
                    // System.out.println(cust_seat);
                    // System.out.println(cust_city);
                    // System.out.println(cust_theater_nm);
                    // System.out.println(cust_type_theater);
                    // System.out.println(cust_movie);
                    System.out.println("======= ADMIN =======");
                    System.out.println("1. Edit Film yang Tayang\n2. Print Semua Transaksi");
                    garisBatas();
                    System.out.print("Masukkan nomor menu [1-2] : ");
                    menu = Integer.parseInt(rd.readLine());
                    garisBatas();
                    switch (menu) {
                        case 1:
                            System.out.println("\tEDIT FILM");
                            System.out.println("1. Tambah Film\n2. Hapus Film yang Tayang");
                            garisBatas();
                            System.out.print("Masukkan nomor menu [1-2] : ");
                            menu = Integer.parseInt(rd.readLine());
                            garisBatas();
                            switch (menu) {
                                case 1: // tambah film
                                    editFilm(1);
                                    break;
                                case 2: // hapus film yang tayang
                                    editFilm(2);
                                    break;
                            }
                            break;
                        case 2:
                            printAll(0, 0, code_cust - 1);
                            break;
                    }
                    break;

                case 3:
                    System.exit(0);
                    break;
            }
        } while (true);
    }

    public static void main(String[] args) throws IOException {
        inputData();
        mainmenu();
    }
}
