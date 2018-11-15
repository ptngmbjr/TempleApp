package com.example.android.bluetoothlegatt.Enumerators;

import com.example.android.bluetoothlegatt.R;

public enum Temples {
    TEMPLE1("http://temple-1.com", "Periyalwar Thirumangai Alwar", "Temple 01", R.drawable.periyalvar, "01\nTemple", "",
            "Periyalwar was one of the twelve alvar saints of South India. He is known as Vishnucitta and later because of Vaishnava tradition his name is popular as Periyalwar, Periyazhwar, or Periyazhvar. As he blessed lord Vishnu assuming him as his elder he is called Pariyalvar. Periyalwar was born into a Brahmin family near Madhurai. \n" +
                    "Once there was a competition between pandyan kind Vallabhadeva and scholars to find out the path to paramapada. By explaining that the path to moksha is by service to Vishnu, this competition was won by Vishnuchittar.\n" +
                    "Thirumangai Alwar is the last of 12 saints in south India. He initially worked as a military commander and later converted to Vaishnavism"
            , R.raw.periyalvar_t1_en),

    TEMPLE2("http://temple-2.com", "Sri Bhagawad Ramanujacharya Swami", "Temple 02", R.drawable.sri_bhagawad_ramanujacharya, "02\nTemple", "",
            "Sri Bhagawad Ramanujacharya Swami has dedicated his entire life for upliftment of equality in society. He made a perfect bridge between different sections of society by giving perfect commentaries to Brahma Sutras and Upanishads. World's largest 216 fee sitting statue of Ramanujachary Swami is inagurated on 50 acres of land. Ramanujacharya is accepted by Lord Venkateshwara at tirumala as his guru and accepted shanka chakra from him.\n" +
                    "Sevaral Bhakthi Movements were held by him with Madhya, Nimbarka, Gauda, Swami Narayana, Vallabhacharya, ISKCON, Eknath, Ramanand, Chaithanya, Kabir, Surdas, Meerabai, Tulasidas, Annamacharya and many others"
            , R.raw.sri_bhagawad_ramanujacharya_t2_en),

    TEMPLE3("http://temple-3.com", "Sri Manavala Mamuni", "Temple 03", R.drawable.sri_manavala_mamuni, "03\nTemple", "",
            "Sri Manavala Mamuni is the Hindu Sri Vaishnava Religious leader during 15th century in Tamilnadu. Mamunigal's disciples has established some placed of learning to teach Sri Vaishnavite Vishishtadvaita Philosophy. \n" +
                    "Manavala Mamunigal is well known and significant acharyas of Swami Ramanuja. It is believed that Manavala Mamuni was accepted by the Lord of Srirangam, Sri Ranganatha, as his own acharya at Srirangam."
            , R.raw.sri_manavala_mamuni_t3_en),

    TEMPLE4("http://temple-4.com", "Sri Nammalwar - Nammalwar", "Temple 04", R.drawable.sri_nammalvar, "04\nTemple", "",
            "Sri Nammalwar - Nammalwar was born on 43rd day of Kali Yuga. Since his childhood he is very different from general human nature. For several days he sat with his eyes closed without food yet with a good health. Parents of Nammalwar were worried seeing this and surrendered him at the Kurugoor Diety for rest of his upbringing. \n" +
                    "For about sixteen year he sat motionless in padmasana. Since then he is believed as the avatara of Vishwak Sena the chief of hosts of Sriman Narayana."
            , R.raw.sri_nammalvar_t4_en),

    TEMPLE5("http://temple-5.com", "Sri Sri Sri Tridandi Srimannarayana Ramanuja Jeeyar Swamiji", "Temple 05", R.drawable.srimannarayana_ramanuja_jeeyar, "05\nTemple", "",
            "Sri Sri Sri Tridandi Srimannarayana Ramanuja Jeeyar Swamiji has founded the Jeeyar educational trust in 1981 with motto \"Serve all beings as Service to God\". His spiritual knowledge has tought him many things in life such as selfless and rekindles love in hearts. He is known to many of us as Pedda Jeeyar Swami who was born to revive Ramanuja's vision. Ramanuja's vision was to spread universal brotherhood and universal well being.\n" +
                    "Swamiji conducted many social movements to eliminate the superstitions which are dominant in society those days."
            , R.raw.srimannarayana_ramanuja_jeeyar_t5_en),

    TEMPLE6("http://temple-6.com", "Sri Vishwak Sena", "", R.drawable.sri_vishwak_sena, "Temple 06", "06\nTemple",
            "Sri Vishwak Sena is the Senadhipati who is the commander in chief of army of Hindu god Vishnu. Vishvaksena cannot be seen in vedas but his worship is mentioned in pancharatra and agama texts. Any ritual of function begins with the worship of Vishvaksena. It is believed that his role is equal to Ganesha who is the first worshiped god in Hinduism. \n" +
                    "Vishwaksena is worshipped before offering rice to vishnu. The mantra recited for vishvaksena is mentioned below.\n" +
                    "Om rhyram hrdayaya namaha\n" +
                    "Om rhyrim sirasa svaha \n" +
                    "Om rhrum sikhayai vausat\n" +
                    "Om rhraim havekcaya hum\n" +
                    "Om rhraum netraya vausat\n" +
                    "Om hrah astray phat.\n"
            , R.raw.sri_vishwak_sena_t6_en);

    private final int song;
    private final String details;
    private final int src;
    private final String statuNo;
    private final String godName;
    private final String place;
    private final String description;
    private final String url;

    private Temples(String url, String godName, String details, int src, String statuNo, String place, String description, int song) {
        this.url = url.trim();
        this.godName = godName.trim();
        this.details = details.trim();
        this.src = src;
        this.statuNo = statuNo.trim();
        this.place = place.trim();
        this.description = description.trim();
        this.song = song;
    }

    public String getUrl() {
        return this.url;
    }

    public String getDetails() {
        return this.details;
    }

    public int getSrc() {
        return this.src;
    }

    public String getStatuNo() {
        return this.statuNo;
    }

    public String getGodName() {
        return this.godName;
    }

    public String getPlace() {
        return this.place;
    }

    public String getDescription() {
        return this.description;
    }

    public int getSong() {
        return this.song;
    }

}