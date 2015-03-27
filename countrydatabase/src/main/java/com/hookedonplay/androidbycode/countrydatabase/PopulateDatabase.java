package com.hookedonplay.androidbycode.countrydatabase;

import android.content.Context;

/**
 * @author BRM20150310
 *         <p/>
 *         Populate the country data. The data is poulated with two sets of data. The DbCountry
 *         constructor has all compulsary items that need to be added for every country. The
 *         addExtendedData then adds some extra metrics for some countries
 */
public class PopulateDatabase {
    static public void execute(Context context, PopulateCommand command) {
        DbTableCountry tableCountry = new DbTableCountry(context);
        tableCountry.open();
        if (command == PopulateCommand.POPULATE_IF_EMPTY)
            if (0 != tableCountry.getEntryCount(null, null)) {
                tableCountry.close();
                return;
            }

        tableCountry.deleteAll();

        // -- EUROPE -- (EASY)
        DbCountry country = new DbCountry("Denmark", "Copenhagen", 55.716667, 12.566667, 5659715, 113, 347.196, 61884, "+45", ".dk", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 42915, 133, 5316, 1, "Krone", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_denmark, R.drawable.map_denmark, CountryDifficulty.DIFFICULTY_EASY);
        country.addExtendedData(79.5, 4.14, 18.2, 93.0, 179, 1, 72135);
        tableCountry.addDbCountry(country);

        country = new DbCountry("Ireland", "Dublin", 53.344167, -6.2675, 4609600, 121, 252.640, 52256, "+353", ".ie", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 70273, 120, 6437, 1, "Euro", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_ireland, R.drawable.map_ireland, CountryDifficulty.DIFFICULTY_EASY);
        country.addExtendedData(81.4, 3.78, 25.2, 79.0, 28, 0, 13980);
        tableCountry.addDbCountry(country);

        country = new DbCountry("Sweden", "Stockholm", 59.35, 18.066667, 9753627, 90, 572.689, 58472, "+46", ".se", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 450295, 56, 26384, 2, "Krona", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_sweden, R.drawable.map_sweden, CountryDifficulty.DIFFICULTY_EASY);
        country.addExtendedData(83.0, 2.73, 18.6, 94.0, 483, 144, 221163);
        tableCountry.addDbCountry(country);

        country = new DbCountry("Greece", "Athens", 37.966667, 23.716667, 10992589, 80, 249.449, 22594, "+30", ".gr", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 131957, 97, 15147, 4, "Euro", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_greece, R.drawable.map_greece, CountryDifficulty.DIFFICULTY_EASY);
        country.addExtendedData(81.0, 4.85, 20.1, 56.0, 110, 0, 364000);
        tableCountry.addDbCountry(country);

        country = new DbCountry("Italy", "Rome", 41.9, 12.483333, 60782668, 23, 2130.0, 35512, "+39", ".it", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 301338, 73, 9226, 6, "Euro", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_italy, R.drawable.map_italy, CountryDifficulty.DIFFICULTY_EASY);
        country.addExtendedData(83.1, 3.33, 19.8, 58.0, 549, 114, 407029);
        tableCountry.addDbCountry(country);

        country = new DbCountry("United Kingdom", "London", 51.5, -0.116667, 64100000, 22, 2490, 40879, "+44", ".uk", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 243610, 80, 19717, 1, "Pound", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_united_kingdom, R.drawable.map_united_kingdom, CountryDifficulty.DIFFICULTY_EASY);
        country.addExtendedData(81.0, 4.5, 26.9, 87.0, 780, 26, 236520);
        tableCountry.addDbCountry(country);

        country = new DbCountry("France", "Paris", 48.856667, 2.350833, 66616416, 20, 2902.0, 45384, "+33", ".fr", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 640679, 42, 7330, 8, "Euro", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_france, R.drawable.map_france, CountryDifficulty.DIFFICULTY_EASY);
        country.addExtendedData(81.5, 3.34, 18.2, 83.0, 671, 109, 340854);
        tableCountry.addDbCountry(country);

        country = new DbCountry("Spain", "Madrid", 40.433333, -3.7, 46464053, 30, 1400.0, 30113, "+34", ".es", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 504645, 52, 7268, 5, "Euro", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_spain, R.drawable.map_spain, CountryDifficulty.DIFFICULTY_EASY);
        country.addExtendedData(82.5, 3.35, 26.60, 72.0, 131, 2, 219510);
        tableCountry.addDbCountry(country);

        country = new DbCountry("Germany", "Berlin", 52.516667, 13.383333, 80716000, 16, 3820.0, 47201, "+49", ".de", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 357168, 63, 3624, 9, "Euro", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_germany, R.drawable.map_germany, CountryDifficulty.DIFFICULTY_EASY);
        country.addExtendedData(81.0, 3.48, 25.10, 84.0, 573, 209, 226770);
        tableCountry.addDbCountry(country);

        country = new DbCountry("Switzerland", "Bern", 46.95, 7.45, 8211700, 98, 679.028, 84344, "+41", ".ch", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 41285, 135, 0, 5, "Euro", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_switzerland, R.drawable.map_switzerland, CountryDifficulty.DIFFICULTY_EASY);
        country.addExtendedData(82.8, 3.8, 17.50, 85.2, 185, 138, 257900);
        tableCountry.addDbCountry(country);

        // -- EUROPE -- (HARD)
        country = new DbCountry("Netherlands", "Amsterdam", 52.366667, 4.883333, 16892500, 64, 880.394, 52249, "+31", ".nl", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 41543, 134, 1914, 2, "Euro", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_netherlands, R.drawable.map_netherlands, CountryDifficulty.DIFFICULTY_HARD);
        country.addExtendedData(81.5, 3.69, 18.80, 93.0, 266, 110, 46500);
        tableCountry.addDbCountry(country);

        country = new DbCountry("Austria", "Vienna", 48.2, 16.35, 8579747, 95, 444.867, 52216, "+43", ".at", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 83879, 115, 0, 8, "Euro", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_austria, R.drawable.map_austria, CountryDifficulty.DIFFICULTY_HARD);
        country.addExtendedData(81.5, 4.2, 20.9, 81.0, 86, 218, 194200);
        tableCountry.addDbCountry(country);

        country = new DbCountry("Cyprus", "Nicosia", 35.166667, 33.366667, 858000, 162, 23.006, 26389, "+357", ".cy", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 9251, 168, 671, -1, "Euro", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_cyprus, R.drawable.map_cyprus, CountryDifficulty.DIFFICULTY_HARDER);
        country.addExtendedData(81.2, 8.82, 25.50, 61.0, 1, 0, 62750);
        tableCountry.addDbCountry(country);

        country = new DbCountry("Finland", "Helsinki", 60.166667, 24.933333, 5475526, 114, 276.275, 50450, "+358", ".fi", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 338424, 66, 31119, 3, "Euro", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_finland, R.drawable.map_finland, CountryDifficulty.DIFFICULTY_HARD);
        country.addExtendedData(81.0, 3.38, 23.0, 91.0, 302, 161, 365000);
        tableCountry.addDbCountry(country);

        country = new DbCountry("Portugal", "Lisbon", 38.766667, -9.15, 10477800, 85, 231.970, 22089, "+351", ".pt", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 92212, 111, 2830, 1, "Euro", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_portugal, R.drawable.map_portugal, CountryDifficulty.DIFFICULTY_HARD);
        country.addExtendedData(80.0, 4.54, 24.0, 64.0, 23, 0, 302291);
        tableCountry.addDbCountry(country);

        country = new DbCountry("Czech Republic", "Prague", 50.083333, 14.466667, 10528477, 84, 208.872, 19796, "+420", ".cz", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 78866, 116, 0, 4, "Koruna", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_czech_republic, R.drawable.map_czech_republic, CountryDifficulty.DIFFICULTY_HARD);
        country.addExtendedData(78.0, 3.67, 32.7, 75.0, 44, 24, 26750);
        tableCountry.addDbCountry(country);

        country = new DbCountry("Belgium", "Brussels", 50.85, 4.35, 11237160, 76, 534.775, 47787, "+32", ".be", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 30528, 140, 76, 4, "Euro", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_belgium, R.drawable.map_belgium, CountryDifficulty.DIFFICULTY_HARD);
        country.addExtendedData(81.0, 4.23, 22.10, 82.0, 142, 5, 35736);
        tableCountry.addDbCountry(country);

        country = new DbCountry("Croatia", "Zagreb", 45.8, 16, 4267558, 127, 61.280, 13920, "+99", ".xx", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 56594, 126, 5664, 5, "Euro", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_croatia, R.drawable.map_croatia, CountryDifficulty.DIFFICULTY_HARD);
        country.addExtendedData(77.5, 5.96, 24.2, 63.0, 23, 11, 40550);
        tableCountry.addDbCountry(country);

        country = new DbCountry("Turkey", "Ankara", 39.916667, 32.833333, 77695904, 18, 1512.0, 19556, "+90", ".tr", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 783562, 37, 8140, 8, "Lira", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_turkey, R.drawable.map_turkey, CountryDifficulty.DIFFICULTY_HARD);
        country.addExtendedData(74.4, 22.23, 27.80, 45.1, 88, 0, 991500);
        country.setContinent2(Continent.CONTINENT_EUROPE);
        tableCountry.addDbCountry(country);

        country = new DbCountry("Russia", "Moscow", 55.75, 37.616667, 143975923, 9, 2057.0, 14317, "+7", ".ru", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 17098242, 1, 110310, 14, "Ruble", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_russia, R.drawable.map_russia, CountryDifficulty.DIFFICULTY_HARD);
        country.addExtendedData(70.5, 7.19, 26.5, 59.27, 397, 124, 3354000);
        country.setContinent2(Continent.CONTINENT_ASIA);
        tableCountry.addDbCountry(country);

        country = new DbCountry("Ukraine", "Kiev", 50.45, 30.5, 42928900, 32, 134.8, 2979, "+380", ".ua", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 603500, 46, 4953, 7, "Hryvnia", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_ukraine, R.drawable.map_ukraine, CountryDifficulty.DIFFICULTY_HARD);
        country.addExtendedData(68.0, 8.24, 21.3, 33.7, 115, 7, 1016900);
        tableCountry.addDbCountry(country);

        // -- EUROPE -- (HARDER)
        tableCountry.addDbCountry(new DbCountry("Slovenia", "Ljubljana", 46.05, 14.5, 2065857, 146, 62.515, 30266, "+386", ".si", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 20273, 154, 41, 4, "Euro", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_slovenia, R.drawable.map_slovenia, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Slovakia", "Bratislava", 48.143889, 17.109722, 5421034, 116, 103.210, 19029, "+421", ".sk", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 49035, 129, 0, 5, "Euro", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_slovakia, R.drawable.map_slovakia, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Poland", "Warsaw", 52.216667, 21.033333, 38483957, 34, 593.758, 15406, "+48", ".pl", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 312679, 71, 1032, 7, "Złoty", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_poland, R.drawable.map_poland, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Romania", "Bucharest", 44.416667, 26.1, 19942642, 58, 205.302, 10859, "+40", ".ro", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 238391, 83, 696, 5, "Leu", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_romania, R.drawable.map_romania, CountryDifficulty.DIFFICULTY_HARDER));

        country = new DbCountry("Kazakhstan", "Astana", 51.166667, 71.416667, 17417500, 63, 225.619, 12950, "+7-6xx, +7-7xx", ".kz", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 2724900, 9, 4528, 5, "Tenge", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_kazakhstan, R.drawable.map_kazakhstan, CountryDifficulty.DIFFICULTY_HARDER);
        country.setContinent2(Continent.CONTINENT_EUROPE);
        tableCountry.addDbCountry(country);

        tableCountry.addDbCountry(new DbCountry("Hungary", "Budapest", 47.433333, 19.25, 9849000, 88, 145.153, 14703, "+36", ".hu", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 93030, 110, 0, 7, "Forint", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_hungary, R.drawable.map_hungary, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Albania", "Tirana", 41.33, 19.8, 2893005, 140, 14.520, 5261, "+355", ".al", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 28748, 144, 649, 4, "Lek", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_albania, R.drawable.map_albania, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Iceland", "Reykjavík", 64.133333, -21.933333, 329040, 182, 17.216, 52967, "+354", ".is", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 103001, 108, 8506, 0, "Krona", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_iceland, R.drawable.map_iceland, CountryDifficulty.DIFFICULTY_HARDER));

        // -- EUROPE -- (INSANE)
        tableCountry.addDbCountry(new DbCountry("Andorra", "Andorra la Vella", 42.5, 1.516, 76949, 207, 4.510, 53383, "+376", ".ad", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 467, 195, 0, 2, "Euro", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_andorra, R.drawable.map_andorra, CountryDifficulty.DIFFICULTY_INSANE));
        tableCountry.addDbCountry(new DbCountry("Liechtenstein", "Vaduz", 47.141667, 9.523333, 37132, 215, 5.155, 143151, "+423", ".li", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 160, 219, 0, 2, "Franc", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_liechtenstein, R.drawable.map_liechtenstein, CountryDifficulty.DIFFICULTY_INSANE));
        country = new DbCountry("Azerbaijan", "Baku", 40.416667, 49.833333, 9593000, 91, 73.537, 7900, "+994", ".az", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_EUROPE, 86600, 114, 871, 5, "Manat", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_azerbaijan, R.drawable.map_azerbaijan, CountryDifficulty.DIFFICULTY_INSANE);
        country.setContinent2(Continent.CONTINENT_ASIA);
        tableCountry.addDbCountry(country);
        // -- ASIA -- EASY
        country = new DbCountry("South Korea", "Seoul", 37.55, 126.966667, 51302044, 26, 1449, 28738, "+82", ".kr", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 100210, 109, 12478, 1, "Won", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_south_korea, R.drawable.map_south_korea, CountryDifficulty.DIFFICULTY_EASY);
        country.addExtendedData(81.0, 4.1, 0.0, 0.0, 243, 53, 3730000);
        tableCountry.addDbCountry(country);

        country = new DbCountry("Japan", "Tokyo", 35.683333, 139.766667, 126434964, 10, 4770.0, 37540, "+81", ".jp", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 377930, 62, 29020, 0, "Yen", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_japan, R.drawable.map_japan, CountryDifficulty.DIFFICULTY_EASY);
        country.addExtendedData(84.6, 2.17, 0.0, 0.0, 398, 45, 316392);
        tableCountry.addDbCountry(country);

        country = new DbCountry("China", "Beijing", 39.916667, 116.383333, 1357380000, 1, 9469.0, 6959, "+86", ".cn", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 9596961, 3, 30017, 16, "Yuan", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_china, R.drawable.map_china, CountryDifficulty.DIFFICULTY_EASY);
        country.addExtendedData(76.0, 7.2, 0.0, 0.0, 473, 53, 1964000);
        tableCountry.addDbCountry(country);

        country = new DbCountry("Vietnam", "Hanoi", 21.033333, 105.85, 90730000, 13, 187.848, 2072, "+84", ".vn", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 331212, 67, 11409, 3, "Dong", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_vietnam, R.drawable.map_vietnam, CountryDifficulty.DIFFICULTY_EASY);
        country.addExtendedData(75.0, 19.61, 0.0, 0.0, 2, 0, -1);
        tableCountry.addDbCountry(country);

        country = new DbCountry("India", "New Delhi", 28.613333, 77.208333, 1210193422, 2, 2048, 1626, "+91", ".in", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 3287590, 7, 17181, 7, "Rupee", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_india, R.drawable.map_india, CountryDifficulty.DIFFICULTY_EASY);
        country.addExtendedData(65.0, 42.0, 0.0, 0.0, 26, 0, -1);
        tableCountry.addDbCountry(country);

        country = new DbCountry("Sri Lanka", "Colombo", 6.933333, 79.833333, 20277597, 57, 70.966, 3385, "+94", ".lk", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 65610, 122, 2825, -1, "Rupee", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_sri_lanka, R.drawable.map_sri_lanka, CountryDifficulty.DIFFICULTY_EASY);
        country.addExtendedData(74.7, 9.24, 0.0, 0.0, 2, 0, -1);
        tableCountry.addDbCountry(country);

        country = new DbCountry("Bangladesh", "Dhaka", 23.7, 90.35, 156594962, 8, 161.76, 1033, "+880", ".bd", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 147570, 94, 3306, 2, "Taka", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_bangladesh, R.drawable.map_bangladesh, CountryDifficulty.DIFFICULTY_EASY);
        country.addExtendedData(70.0, 47.30, 0.0, 0.0, 0, 0, -1);
        tableCountry.addDbCountry(country);

        tableCountry.addDbCountry(new DbCountry("Israel", "Jerusalem", 31.783333, 35.216667, 8238300, 96, 305.707, 38004, "+972", ".il", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 22072, 152, 205, 5, "New Shekel", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_israel, R.drawable.map_israel, CountryDifficulty.DIFFICULTY_EASY));

        // -- ASIA -- HARD
        tableCountry.addDbCountry(new DbCountry("Pakistan", "Islamabad", 33.666667, 73.166667, 196174380, 6, 243.818, 1307, "+99", ".xx", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 803940, 36, 2599, 4, "Rupee", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_pakistan, R.drawable.map_pakistan, CountryDifficulty.DIFFICULTY_HARD));
        tableCountry.addDbCountry(new DbCountry("Thailand", "Bangkok", 13.75, 100.483333, 64871000, 21, 387.253, 5675, "+66", ".th", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 513120, 51, 7066, 4, "Baht", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_thailand, R.drawable.map_thailand, CountryDifficulty.DIFFICULTY_HARD));
        tableCountry.addDbCountry(new DbCountry("Cambodia", "Phnom Penh", 11.55, 104.916667, 15405157, 70, 17.25, 1108, "+855", ".kh", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 181035, 88, 1127, 3, "Riel", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_cambodia, R.drawable.map_cambodia, CountryDifficulty.DIFFICULTY_HARD));
        tableCountry.addDbCountry(new DbCountry("Malaysia", "Kuala Lumpur", 3.133, 101.68, 30511900, 43, 800.169, 25833, "+60", ".my", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 330803, 68, 9323, 3, "Ringgit", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_malaysia, R.drawable.map_malaysia, CountryDifficulty.DIFFICULTY_HARD));
        tableCountry.addDbCountry(new DbCountry("Philippines", "Manila", 14.583333, 120.966667, 100617630, 12, 289.686, 2913, "+63", ".ph", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 342353, 64, 33900, 0, "Peso", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_philippines, R.drawable.map_philippines, CountryDifficulty.DIFFICULTY_HARD));
        tableCountry.addDbCountry(new DbCountry("Nepal", "Kathmandu", 27.7, 85.316667, 26494504, 45, 19.921, 743, "+977", ".np", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 147181, 95, 0, 2, "Rupee", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_nepal, R.drawable.map_nepal, CountryDifficulty.DIFFICULTY_HARD));
        tableCountry.addDbCountry(new DbCountry("United Arab Emirates", "Abu Dhabi", 24.466667, 54.366667, 9346129, 93, 440.181, 44770, "+971", ".ae", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 83600, 116, 2871, 3, "Dirham", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_united_arab_emirates, R.drawable.map_united_arab_emirates, CountryDifficulty.DIFFICULTY_HARD));

        // -- ASIA -- HARDER
        tableCountry.addDbCountry(new DbCountry("Laos", "Vientiane", 17.966667, 102.6, 6802000, 105, 11.14, 1646, "+856", ".la", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 236800, 84, 0, 5, "Kip", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_laos, R.drawable.map_laos, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Iran", "Tehran", 35.683333, 51.416667, 78165200, 17, 402.700, 5165, "+98", ".ir", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 1648195, 18, 5891, 7, "Rial", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_iran, R.drawable.map_iran, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Iraq", "Baghdad", 33.333333, 44.433333, 36004552, 36, 240.006, 6491, "+964", ".iq", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 437072, 59, 105, 6, "Dinar", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_iraq, R.drawable.map_iraq, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Mongolia", "Ulan Bator", 47.916667, 106.883333, 3000000, 138, 11.516, 3996, "+976", ".mn", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 1564115, 19, 0, 2, "Tögrög", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_mongolia, R.drawable.map_mongolia, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Singapore", "Singapore", 1.283333, 103.833333, 5469700, 115, 297.941, 55182, "+65", ".sg", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 718, 190, 268, 0, "Dollar", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_singapore, R.drawable.map_singapore, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Indonesia", "Jakarta", -6.175, 106.828333, 252164800, 4, 856.066, 3510, "+62", ".id", Hemisphere.HEMISPHERE_BOTH, Continent.CONTINENT_ASIA, 1904569, 15, 95181, 3, "Rupiah", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_indonesia, R.drawable.map_indonesia, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("North Korea", "Pyongyang", 39.033333, 125.75, 24895000, 48, 15.4, 621, "+850", ".kp", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 120540, 98, 4009, 3, "Won", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_north_korea, R.drawable.map_north_korea, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Saudi Arabia", "Riyadh", 24.65, 46.766667, 31521418, 40, 777.870, 25401, "+966", ".sa", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 2149690, 13, 7572, 7, "Riyal", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_saudi_arabia, R.drawable.map_saudi_arabia, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Afghanistan", "Kabul", 34.533333, 69.133333, 31822848, 40, 21.747, 695, "+93", ".af", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 652864, 41, 0, 6, "Afghani", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_afghanistan, R.drawable.map_afghanistan, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Yemen", "Sana'a", 15.348333, 44.206389, 23833000, 48, 36.700, 1418, "+967", ".ye", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 527829, 50, 3149, 2, "Rial", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_yemen, R.drawable.map_yemen, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Syria", "Damascus", 33.5, 36.3, 17951639, 54, 59.957, 2802, "+963", "sy", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 185180, 89, 212, 5, "Pound", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_syria, R.drawable.map_syria, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Lebanon", "Beirut", 33.9, 35.533333, 4467000, 119, 47.497, 10530, "+961", ".lb", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 10452, 166, 294, 2, "Pound", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_lebanon, R.drawable.map_lebanon, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Jordan", "Amman", 31.95, 35.933333, 6703627, 107, 37.897, 5534, "+962", ".jo", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 89342, 112, 27, 5, "Dinar", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_jordan, R.drawable.map_jordan, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Georgia", "Tbilisi", 41.716667, 44.783333, 4935880, 119, 15.984, 3596, "+995", ".ge", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 69700, 120, 376, 4, "Lari", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_georgia, R.drawable.map_georgia, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Kuwait", "Kuwait City", 29.366667, 47.966667, 4044500, 140, 173.438, 44585, "+965", ".kw", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 17820, 157, 756, 2, "Dinar", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_kuwait, R.drawable.map_kuwait, CountryDifficulty.DIFFICULTY_HARDER));

        // -- ASIA -- INSANE

        tableCountry.addDbCountry(new DbCountry("Uzbekistan", "Tashkent", 41.266667, 69.216667, 30492800, 44, 61.720, 2017, "+998", ".uz", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 447400, 57, 1707, 5, "Som", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_uzbekistan, R.drawable.map_uzbekistan, CountryDifficulty.DIFFICULTY_INSANE));
        tableCountry.addDbCountry(new DbCountry("Oman", "Muscat", 23.6, 58.55, 3219775, 129, 80.539, 21687, "+968", ".om", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_ASIA, 309501, 70, 2810, 3, "Rial", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_oman, R.drawable.map_oman, CountryDifficulty.DIFFICULTY_INSANE));

        // -- AFRICA --
        tableCountry.addDbCountry(new DbCountry("South Africa", "", 0, 0, 54002000, 24, 341.216, 6354, "+27", ".za", Hemisphere.HEMISPHERE_SOUTH, Continent.CONTINENT_AFRICA, 1221037, 25, 3751, 6, "Rand", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_south_africa, R.drawable.map_south_africa, CountryDifficulty.DIFFICULTY_EASY));

        tableCountry.addDbCountry(new DbCountry("Namibia", "Windhoek", -22.57, 17.086117, 2113077, 144, 13.064, 5961, "+264", ".na", Hemisphere.HEMISPHERE_SOUTH, Continent.CONTINENT_AFRICA, 825615, 35, 1754, 4, "Dollar", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_namibia, R.drawable.map_namibia, CountryDifficulty.DIFFICULTY_HARD));
        tableCountry.addDbCountry(new DbCountry("Morocco", "Rabat", 34.033333, -6.85, 33543100, 39, 114.7, 3458, "+212", ".ma", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_AFRICA, 446550, 58, 2009, 3, "Dirham", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_morocco, R.drawable.map_morocco, CountryDifficulty.DIFFICULTY_HARD));
        tableCountry.addDbCountry(new DbCountry("Kenya", "Nairobi", -1.266667, 36.8, 46749000, 29, 62.722, 1461, "+254", ".ke", Hemisphere.HEMISPHERE_BOTH, Continent.CONTINENT_AFRICA, 580367, 49, 1586, 5, "Shilling", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_kenya, R.drawable.map_kenya, CountryDifficulty.DIFFICULTY_HARD));

        tableCountry.addDbCountry(new DbCountry("Ethiopia", "Addis Ababa", 9.0300, 38.7400, 90076012, 14, 51.0, 570, "+251", ".et", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_AFRICA, 1104300, 27, 0, 6, "Birr", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_ethiopia, R.drawable.map_ethiopia, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Cameroon", "Yaoundé", 3.866667, 11.516667, 21143237, 57, 32.163, 1426, "+237", ".cm", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_AFRICA, 475442, 54, 1799, 6, "Franc", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_cameroon, R.drawable.map_cameroon, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Central African Republic", "Bangui", 4.366667, 18.583333, 4803000, 118, 1.688, 358, "+236", ".cf", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_AFRICA, 622984, 44, 0, 6, "Franc", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_car, R.drawable.map_car, CountryDifficulty.DIFFICULTY_HARDER));
        country = new DbCountry("Egypt", "Cairo", 30.033333, 31.216667, 88123300, 15, 324.267, 3723, "+20", ".eg", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_AFRICA, 1002450, 30, 5898, 4, "Pound", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_egypt, R.drawable.map_egypt, CountryDifficulty.DIFFICULTY_HARDER);
        country.setContinent2(Continent.CONTINENT_ASIA);
        tableCountry.addDbCountry(country);

        tableCountry.addDbCountry(new DbCountry("Libya", "", 0.0, 0.0, 6317000, 110, 67.622, 11046, "+218", ".ly", Hemisphere.HEMISPHERE_SOUTH, Continent.CONTINENT_AFRICA, 1759540, 17, 2025, 6, "Dinar", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_libya, R.drawable.map_libya, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Madagascar", "Antananarivo", -18.916667, 47.516667, 21842167, 56, 11.188, 475, "+261", ".mg", Hemisphere.HEMISPHERE_SOUTH, Continent.CONTINENT_AFRICA, 587041, 47, 9935, 0, "Ariary", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_madagascar, R.drawable.map_madagascar, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Zambia", "Lusaka", -15.416667, 28.283333, 15473905, 69, 26.611, 1810, "+260", ".zm", Hemisphere.HEMISPHERE_SOUTH, Continent.CONTINENT_AFRICA, 752618, 39, 0, 8, "Kwacha", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_zambia, R.drawable.map_zambia, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Botswana", "Gaborone", -24.658333, 25.908333, 2024904, 147, 15.112, 7704, "+267", ".bw", Hemisphere.HEMISPHERE_SOUTH, Continent.CONTINENT_AFRICA, 582000, 48, 0, 4, "Pula", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_botswana, R.drawable.map_botswana, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Malawi", "Lilongwe", -13.95, 33.7, 16310431, 65, 4.212, 253, "+265", ".mw", Hemisphere.HEMISPHERE_SOUTH, Continent.CONTINENT_AFRICA, 118484, 100, 0, 3, "Kwacha", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_malawi, R.drawable.map_malawi, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Mozambique", "Maputo", -25.95, 32.583333, 25727911, 49, 14.600, 650, "+258", ".mz", Hemisphere.HEMISPHERE_SOUTH, Continent.CONTINENT_AFRICA, 801590, 36, 6942, 6, "Metical", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_mozambique, R.drawable.map_mozambique, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Zimbabwe", "Harare", -17.833333, 31.05, 13061239, 73, 10.978, 837, "+263", ".zw", Hemisphere.HEMISPHERE_SOUTH, Continent.CONTINENT_AFRICA, 390580, 61, 0, 4, "", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_zimbabwe, R.drawable.map_zimbabwe, CountryDifficulty.DIFFICULTY_HARDER));

        tableCountry.addDbCountry(new DbCountry("Burkina Faso", "Ouagadougou", 12.333333, -1.666667, 18450494, 61, 13.0, 790, "+226", ".bf", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_AFRICA, 272967, 75, 0, 6, "Franc", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_burkina_faso, R.drawable.map_burkina_faso, CountryDifficulty.DIFFICULTY_INSANE));
        tableCountry.addDbCountry(new DbCountry("Ivory Coast", "Yamoussoukro", 6.85, -5.3, 22671331, 55, 32.0, 1302, "+225", ".ci", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_AFRICA, 322463, 70, 797, 5, "Franc", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_ivory_coast, R.drawable.map_ivory_coast, CountryDifficulty.DIFFICULTY_INSANE));
        tableCountry.addDbCountry(new DbCountry("Ghana", "Accra", 5.55, -0.2, 27043093, 46, 70.0, 2600, "+233", ".gh", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_AFRICA, 238533, 82, 758, 3, "Cedi", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_ghana, R.drawable.map_ghana, CountryDifficulty.DIFFICULTY_INSANE));

        // -- NORTH AMERICA --
        tableCountry.addDbCountry(new DbCountry("Canada", "Ottawa", 45.4, -75.666667, 35675834, 37, 1825, 51871, "+1", ".ca", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_NORTH_AMERICA, 9984670, 2, 265523, 1, "Dollar", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_canada, R.drawable.map_canada, CountryDifficulty.DIFFICULTY_EASY));
        tableCountry.addDbCountry(new DbCountry("USA", "Washington, D.C.", 38.883333, -77.016667, 320529000, 3, 16768, 53042, "+1", "", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_NORTH_AMERICA, 9526468, 4, 133312, 2, "Dollar", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_usa, R.drawable.map_usa, CountryDifficulty.DIFFICULTY_EASY));
        tableCountry.addDbCountry(new DbCountry("Mexico", "Mexico City", 19.433333, -99.133333, 121005815, 11, 1295, 11320, "+52", ".mx", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_NORTH_AMERICA, 1964375, 14, 23761, 3, "Peso", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_mexico, R.drawable.map_mexico, CountryDifficulty.DIFFICULTY_EASY));

        tableCountry.addDbCountry(new DbCountry("Cuba", "Havana", 23.133333, -82.383333, 11210064, 77, 78.694, 6985, "+53", ".cu", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_NORTH_AMERICA, 109884, 106, 14519, 0, "Peso", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_cuba, R.drawable.map_cuba, CountryDifficulty.DIFFICULTY_HARD));
        tableCountry.addDbCountry(new DbCountry("Panama", "Panama City", 8.966667, -79.533333, 3764166, 131, 49.142, 12744, "+507", ".pa", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_NORTH_AMERICA, 75417, 118, 5637, 2, "Balboa", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_panama, R.drawable.map_panama, CountryDifficulty.DIFFICULTY_HARDER));

        tableCountry.addDbCountry(new DbCountry("Jamaica", "Kingston", 17.983333, -76.8, 2717991, 141, 15.569, 5657, "+1-876", ".jm", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_NORTH_AMERICA, 10991, 166, 895, 0, "Dollar", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_jamaica, R.drawable.map_jamaica, CountryDifficulty.DIFFICULTY_HARD));
        tableCountry.addDbCountry(new DbCountry("Trinidad and Tobago", "Port of Spain", 10.666667, -61.516667, 1328019, 154, 29.629, 21933, "+1 -868", ".tt", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_NORTH_AMERICA, 5131, 174, 704, 0, "Dollar", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_trinidad_and_tobago, R.drawable.map_trinidad_and_tobago, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Barbados", "Bridgetown", 13.1, -59.616667, 285000, 184, 4.490, 16151, "+1 -246", ".bb", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_NORTH_AMERICA, 430, 201, 97, 0, "Dollar", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_barbados, R.drawable.map_barbados, CountryDifficulty.DIFFICULTY_HARD));

        tableCountry.addDbCountry(new DbCountry("Guatemala", "Guatemala City", 14.633333, -90.5, 15806675, 68, 49.880, 3302, "+502", ".gt", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_NORTH_AMERICA, 108889, 197, 445, 4, "Quetzal", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_guatemala, R.drawable.map_guatemala, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Haiti", "Port-au-Prince", 18.533333, -72.333333, 10911819, 82, 8.919, 852, "+509", ".ht", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_NORTH_AMERICA, 27750, 140, 1977, 1, "Gourde", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_haiti, R.drawable.map_haiti, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Dominican Republic", "Santo Domingo", 19, -70.666667, 10378267, 86, 62.484, 5894, "+1-809, +1-829, +1-849", ".do", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_NORTH_AMERICA, 48442, 131, 1612, 1, "Peso", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_dominican_republic, R.drawable.map_dominican_republic, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Nicaragua", "Managua", 12.15, -86.266667, 6134270, 111, 11.848, 1921, "+505", ".ni", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_NORTH_AMERICA, 130375, 97, 1915, 2, "Córdoba", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_nicaragua, R.drawable.map_nicaragua, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Honduras", "Tegucigalpa", 14.1, -87.216667, 8725111, 94, 19.567, 2368, "+504", ".hn", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_NORTH_AMERICA, 112492, 102, 1878, 3, "Lempira", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_honduras, R.drawable.map_honduras, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("El Salvador", "San Salvador", 13.666667, -89.166667, 6401240, 108, 28.986, 4776, "+503", ".sv", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_NORTH_AMERICA, 21044, 153, 756, 2, "Dollar", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_el_salvador, R.drawable.map_el_salvador, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Costa Rica", "San José", 9.933333, -84.083333, 4773130, 119, 52.968, 10893, "+506", ".cr", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_NORTH_AMERICA, 51100, 128, 2069, 2, "Colón", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_costa_rica, R.drawable.map_costa_rica, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Belize", "Belmopan", 17.25, -88.766667, 358899, 180, 1.554, 4535, "+501", ".bz", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_NORTH_AMERICA, 22966, 151, 1996, 2, "Dollar", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_belize, R.drawable.map_belize, CountryDifficulty.DIFFICULTY_HARDER));

        // -- SOUTH AMERICA --
        tableCountry.addDbCountry(new DbCountry("Brazil", "Brasília", -15.783333, -47.866667, 202768562, 5, 2244, 11067, "+55", ".br", Hemisphere.HEMISPHERE_BOTH, Continent.CONTINENT_SOUTH_AMERICA, 8515767, 5, 33379, 10, "Real", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_brazil, R.drawable.map_brazil, CountryDifficulty.DIFFICULTY_EASY));
        tableCountry.addDbCountry(new DbCountry("Argentina", "Buenos Aires", -34.6, -58.383333, 43131966, 31, 536.155, 12778, "+54", ".ar", Hemisphere.HEMISPHERE_SOUTH, Continent.CONTINENT_SOUTH_AMERICA, 2780400, 8, 8397, 5, "Peso", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_argentina, R.drawable.map_argentina, CountryDifficulty.DIFFICULTY_HARD));
        tableCountry.addDbCountry(new DbCountry("Paraguay", "Asunción", -25.266667, -57.666667, 7003406, 104, 45.901, 6758, "+595", ".py", Hemisphere.HEMISPHERE_SOUTH, Continent.CONTINENT_SOUTH_AMERICA, 406752, 60, 0, 3, "Guaraní", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_paraguay, R.drawable.map_paraguay, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Uruguay", "Montevideo", -34.883333, -56.166667, 3404189, 135, 58.057, 16996, "+598", ".uy", Hemisphere.HEMISPHERE_SOUTH, Continent.CONTINENT_SOUTH_AMERICA, 176215, 91, 1096, 2, "Peso", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_uruguay, R.drawable.map_uruguay, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Peru", "Lima", -12.043333, -77.028333, 31151643, 41, 217.607, 6819, "+51", ".pe", Hemisphere.HEMISPHERE_SOUTH, Continent.CONTINENT_SOUTH_AMERICA, 1285216, 20, 3362, 5, "Nuevo sol", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_peru, R.drawable.map_peru, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Chile", "Santiago", -33.433333, -70.666667, 18006407, 62, 264.095, 14911, "+56", ".cl", Hemisphere.HEMISPHERE_SOUTH, Continent.CONTINENT_SOUTH_AMERICA, 756096, 38, 78563, 3, "Peso", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_chile, R.drawable.map_chile, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Bolivia", "Sucre", -19.05, -65.25, 11410651, 75, 29.802, 2700, "+591", ".bo", Hemisphere.HEMISPHERE_SOUTH, Continent.CONTINENT_SOUTH_AMERICA, 1098581, 28, 0, 5, "Boliviano", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_bolivia, R.drawable.map_bolivia, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Colombia", "Bogotá", 4.583333, -74.066667, 48014026, 27, 427.139, 8858, "+57", ".co", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_SOUTH_AMERICA, 1141748, 26, 5875, 5, "Peso", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_colombia, R.drawable.map_colombia, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Ecuador", "Quito", -0.15, -78.35, 15945200, 67, 84.53, 5310, "+593", ".ec", Hemisphere.HEMISPHERE_BOTH, Continent.CONTINENT_SOUTH_AMERICA, 276841, 74, 4597, 2, "Dollar", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_ecuador, R.drawable.map_ecuador, CountryDifficulty.DIFFICULTY_HARDER));

        tableCountry.addDbCountry(new DbCountry("Venezuela", "Caracas", 10.5, -66.966667, 30620404, 42, 209.226, 6869, "+58", ".ve", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_SOUTH_AMERICA, 916445, 33, 6762, 3, "Bolívar fuerte", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_venezuela, R.drawable.map_venezuela, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Suriname", "Paramaribo", 5.833333, -55.166667, 534189, 172, 5.273, 9539, "+597", ".sr", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_SOUTH_AMERICA, 163821, 92, 620, 2, "Dollar", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_suriname, R.drawable.map_suriname, CountryDifficulty.DIFFICULTY_INSANE));
        tableCountry.addDbCountry(new DbCountry("Guyana", "Georgetown", 6.766667, -58.166667, 746900, 166, 2.788, 3596, "+592", ".gy", Hemisphere.HEMISPHERE_NORTH, Continent.CONTINENT_SOUTH_AMERICA, 214970, 85, 1154, 3, "Dollar", DriveRoad.DRIVES_ON_RIGHT, R.raw.flag_guyana, R.drawable.map_guyana, CountryDifficulty.DIFFICULTY_INSANE));

        // -- AUSTRALIA --
        tableCountry.addDbCountry(new DbCountry("Australia", "Canberra", -35.308, 149.1245, 23747700, 51, 1483, 62822, "+99", ".xx", Hemisphere.HEMISPHERE_SOUTH, Continent.CONTINENT_AUSTRALIA, 7692024, 6, 66530, 0, "Dollar", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_australia, R.drawable.map_australia, CountryDifficulty.DIFFICULTY_EASY));

        tableCountry.addDbCountry(new DbCountry("New Zealand", "Wellington", -41.283333, 174.45, 4566220, 123, 230.004, 47784, "+64", ".nz", Hemisphere.HEMISPHERE_SOUTH, Continent.CONTINENT_AUSTRALIA, 270467, 76, 17209, 0, "Dollar", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_new_zealand, R.drawable.map_new_zealand, CountryDifficulty.DIFFICULTY_HARD));
        tableCountry.addDbCountry(new DbCountry("Papua New Guinea", "Port Moresby", -9.5, 147.116667, 7398500, 99, 15.973, 2283, "+675", ".pg", Hemisphere.HEMISPHERE_SOUTH, Continent.CONTINENT_AUSTRALIA, 462840, 55, 20197, 1, "Kina", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_papua_new_guinea, R.drawable.map_papua_new_guinea, CountryDifficulty.DIFFICULTY_HARDER));

        tableCountry.addDbCountry(new DbCountry("Fiji", "Suva", -18.166667, 178.45, 858038, 161, 3.671, 4083, "+679", ".fj", Hemisphere.HEMISPHERE_SOUTH, Continent.CONTINENT_AUSTRALIA, 18274, 156, 4638, 0, "Dollar", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_fiji, R.drawable.map_fiji, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Vanuatu", "Port Vila", -17.75, 168.3, 264652, 187, 0.743, 3036, "+678", ".vu", Hemisphere.HEMISPHERE_SOUTH, Continent.CONTINENT_AUSTRALIA, 12190, 161, 3132, 0, "Vatu", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_vanuatu, R.drawable.map_vanuatu, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Samoa", "Apia", -13.833333, -171.75, 187820, 191, 0.630, 3451, "+685", ".ws", Hemisphere.HEMISPHERE_SOUTH, Continent.CONTINENT_AUSTRALIA, 2842, 174, 463, 0, "Tala", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_samoa, R.drawable.map_samoa, CountryDifficulty.DIFFICULTY_HARDER));
        tableCountry.addDbCountry(new DbCountry("Tonga", "Nukuʻalofa", -21.133333, -175.2, 103252, 201, 0.439, 4220, "+676", ".to", Hemisphere.HEMISPHERE_SOUTH, Continent.CONTINENT_AUSTRALIA, 748, 186, 909, 0, "Paʻanga", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_tonga, R.drawable.map_tonga, CountryDifficulty.DIFFICULTY_INSANE));
        tableCountry.addDbCountry(new DbCountry("Solomon Islands", "Honiara", -9.466667, 159.816667, 523000, 170, 840.00, 1553, "+677", ".sb", Hemisphere.HEMISPHERE_SOUTH, Continent.CONTINENT_AUSTRALIA, 28400, 142, 9880, 0, "Dollar", DriveRoad.DRIVES_ON_LEFT, R.raw.flag_solomon_islands, R.drawable.map_solomon_islands, CountryDifficulty.DIFFICULTY_INSANE));

        tableCountry.close();
    }

    public enum PopulateCommand {POPULATE_IF_EMPTY, POPULATE_CLEAN_BUILD}
}
