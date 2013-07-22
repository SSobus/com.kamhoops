package com.kamhoops.services;


import com.kamhoops.configuration.ApplicationConfig;
import com.kamhoops.configuration.PersistenceJpaConfig;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DataGenerationService {
    private static Logger logger = LoggerFactory.getLogger(DataGenerationService.class);

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private PersistenceJpaConfig persistenceJpaConfig;

    private String[] firstNames = new String[]{
            "Jaymes", "Derek", "Matt", "Mark", "Tom", "Harry", "Sally", "Sandra", "Paul", "Anastasia", "David", "Alex", "Michael", "Tina", "Zachary", "Bob", "Elise", "Michael",
            "Quincy", "Rob", "Odell", "Winford", "Mauro", "Brooks", "Ricardo", "Theo", "Dorian", "Oscar", "Mark", "Randy", "Al", "Nathan", "Homer", "Sebastian", "Preston", "Harlan",
            "Burton", "Don", "Mitchel", "Wilford", "Rayford", "Francis", "Tommie", "Derek", "Gregory", "Waldo", "Whitney", "Bruno", "Porter", "Derick", "Mitchell", "Rudolf", "Jude",
            "Quentin", "Patrick", "Kevin", "Luigi", "Marco", "Marlin", "Gregg", "Buck", "Pete", "Dewitt", "Fletcher", "Percy", "Ron", "Jeremy", "Angelo", "Deon", "Thad", "Wes",
            "Eric", "Aaron", "Myles", "Tyler", "Clay", "Peter", "Blair", "Steve", "Bryant", "Tony", "Riley", "Frank", "Sam", "John", "Shane", "Ken", "Thomas", "Shaun", "Bruno", "Josh",
            "Norman", "Dwayne", "Wilber", "Warner", "Lon", "Beau", "Jeremy", "Jesse", "Edward", "Diego", "Toby", "Ralph", "Scot", "Tad", "Marlon", "Buddy", "Garrett",
            "Marcel", "Keith", "William", "Gus", "Dominique", "Jewell", "Reed", "Kermit", "Perry", "Roy", "Freddie", "Terry", "Glen", "Omar", "Wyatt", "Alden", "Brent",
            "Sandy", "Willow", "Shavonda", "Brynn", "Vivian", "Karri", "Harriet", "Cayla", "Donna", "Aline", "Elsa", "Kortney", "Denisse", "Merideth", "Kim", "Rosalie",
            "Yvette", "Lorelei", "Carolyn", "Katy", "Jennie", "Paola", "Alayna", "Wendy", "Emelia", "Catherine", "Sophia", "Melody", "Melanie", "Regina", "Brooke",
            "Amber", "Tara", "Sasha", "Tamara", "Trish", "Laura", "Sarah", "Linda", "Maria", "Eriz", "Pamella"
    };

    private String[] lastNames = new String[]{
            "Reagan", "Owens", "Smith", "Lewis", "Bowser", "Reyes", "Cipriano", "Porter", "Taylor", "Davis", "Nelson", "Lyle",
            "Wilcox", "Wilmot", "Butters", "Large", "Moreno", "Dyer", "Washington", "Peacock", "Hughes", "Dietz", "Arredondo",
            "Lamm", "Walker", "Lopez", "Lacroix", "Parrish", "Munoz", "Palomino", "Hightower", "Gomez", "Badger", "Gay",
            "Bentz", "Garza", "Larson", "Murphy", "Harris", "Nealon", "Allen", "Maheu", "Dangelo", "Marshall", "Trombley",
            "Decker", "Torres", "Maxwell", "McKee", "Furtado", "Pino", "Joyce", "Chamberland", "Jefferson", "Robinson", "Luke",
            "Hopper", "Lohman", "Broussard", "Isakson", "Petrin", "Clark", "Kohr", "Powell", "Durham", "Potter", "McGowan",
            "Fisher", "Duffin", "Mancini", "Lamb", "Holmes", "Green", "Hardy", "Foltz", "Jones", "Roundtree", "Diaz", "Shaw",
            "Lawson", "Anaya", "Stafford", "Lim", "Reece", "Oliver", "Johnson", "Leavens", "Santiago", "Hahn", "Buster",
            "Hamilton", "Bearden", "Waters", "Bolton", "Hodge", "Matson", "Portugal", "Bennett", "Parker", "Coates", "Naylor",
            "Crisler", "Phillips", "Gaudet", "Willis", "Evans", "Bergman", "Buch", "Young", "Dingman", "White", "Barr",
            "Downing", "Telford", "Sin", "Pace", "Ayala", "Gossett", "Audette", "Shane", "Pentecost", "Hagedorn", "Risner",
            "Toscano", "Marsh", "McCormick", "Osborn", "Jacinto", "Jackson", "Huffman", "Snyder", "Hargrove", "Mask", "Butler",
            "Hall", "Overman", "Prager", "Schulz", "McIntosh", "Gaston", "May", "Dickenson", "Clayton", "Delacruz", "Lerman",
            "Harding", "Brien", "Stokely", "Edward", "Jack", "Baker", "Lovell", "Morris", "Holland", "Roe", "Claassen",
            "Sullivan", "Akers", "Distefano", "Tucker", "Long", "Korhonen", "Williams", "Storer", "Brooks", "Gutierrez",
            "Knickerbocker", "Warden", "Conlan", "Hobbs", "Pierce", "Lane", "Chavez", "Moseley", "Gaines", "Yang", "Krawczyk",
            "Hatton", "Slaton", "Alexander", "Weaver", "Hart", "Laughlin", "Dutton", "Varela", "Francisco", "Rembert",
            "Tooley", "Rollins", "Merrill", "Fredericks", "Russell", "Gilbreath", "Baldwin", "Pruneda", "Brown", "Vasquez",
            "Perez", "Sigler", "Navarette", "Grandy", "Aldrich", "McGill", "Otis", "Damiani", "Reyna", "Paulsen", "Veal",
            "Tefft", "Rodgers", "Allred", "Wong", "Brennan", "Pollard", "Huizenga", "Godwin", "Martin", "Hernandez", "Hanchett",
            "Sessoms", "Sandridge", "Henry", "Magill", "Deluca", "Donald", "Viveiros", "Schneider", "Elliott", "McLean",
            "Munguia", "Ormiston", "Peterson", "Word", "Rivera", "Moyer", "Delvalle", "James", "Pizarro", "Hess", "Little",
            "Gordon", "Brannan", "Marquardt", "Jimenes", "Burke", "Thibodeaux", "Matherne", "Bernstein", "Duckworth",
            "Kirkland", "Isaac", "Levy", "Howard", "Vargas", "Childress", "Ruiz", "Bowen", "Matthews", "Pullen", "Milam",
            "Contreras", "Dye", "Jenkins", "Griggs", "Perry", "Henshaw", "Root", "Hearn", "Roberts", "Brookshire", "Breeding",
            "Burton", "Frazier", "Pickett", "Wilcoxen", "Leyba", "Damon", "Ehlert", "Cisneros", "Merritt", "Jarnigan",
            "Coleman", "Langton", "Lang", "Abner", "Wyatt", "Walsh", "Boozer", "Stall", "Cox", "Whetstone", "Dehaan", "Welsh",
            "Copeland", "Cordes", "Garcia", "Kline", "Schrock", "Walls", "Wallace", "Bryant", "Atkins", "Duran", "Barhorst",
            "Brase", "Weeks", "Barrientos", "Moore", "Campbell", "Mitts", "Joyner", "Nichols", "Montoya", "Beason", "Ziemer",
            "Bryan", "Flores", "Beach", "Stone", "Parsons", "Rich", "Mackey", "Schmitt", "Hopkins", "Jaques", "Barnes",
            "Barbour", "Hadley", "McKinney", "Wiegand", "Piner", "Farmer", "Sampley", "Horne", "Yu", "Spivey", "Weston",
            "Howell", "Price", "Calcote", "Zahn", "Jacob", "Kitchen", "Gilmore", "Vining", "Wright", "Harper", "Rhodes",
            "Ramos", "Booker", "Miranda", "Benedetto", "Lo", "Hoeft", "Singer", "Mitchum", "Yancy", "Wilkinson", "Hooten",
            "Hively", "Conyers", "Hill", "Beck", "Hackworth", "Lemire", "Hyman", "Romano", "Larkin", "Rester", "Reed", "Bosch",
            "Witherspoon", "Brick", "Winters", "Radtke", "Marquez", "Landreth", "Campos", "Salazar", "Forshee", "Florence",
            "Dingle", "Simpson", "Samuels", "Flanders", "Fenwick", "Talley", "Tolle", "Morehead", "Moreau", "Norman",
            "Grammer", "Lawrence", "Khoury", "Mallory", "Batista", "Click", "Guard", "Bowman", "Field", "Hurst", "Maxey",
            "Stokes", "Boissonneault", "Rusch", "Bills", "Casady", "Steelman", "Zendejas", "Hays", "Swatzell", "Adams",
            "Stephens", "Wetzel", "Brock", "Mejia", "Valdez", "Cohen", "Rolling", "Kim", "Dearborn", "Thomas", "Fields",
            "Nguyen", "Spencer", "Doss", "Cano", "Wilson", "Dunning", "Silvey", "Thompson", "Scott", "Rogers", "Edwards", "Funk",
            "Anderson", "Kelly", "Woods", "Stevens", "Powers", "Carroll", "King", "Martinez", "Cook", "Sanders", "Simmons",
            "Cole", "Llewellyn", "Richardson", "Harrison", "Benoit", "Cantara", "Hayes", "Gibson", "Cummins", "Starns", "Ricci",
            "Carter", "Bell", "Gonzales", "Jordan", "Hunter", "Guthrie", "Morgan", "Foster", "West", "Deason", "Albers",
            "Stewart", "Gray", "Wells", "Westmoreland", "Collins", "", "Webb", "Larry", "Burgess", "Metz", "Wood", "Prowell",
            "Tinkle", "Watson", "Pinette", "Noble", "Miller", "Bailey", "Reynolds", "Crawford", "Enright", "Rodriguez",
            "Mitchell", "Hicks", "Colbert", "Lee", "Ross", "Gardner", "Turner", "Cooper", "Henderson", "Griffin", "Ellis",
            "Serrano", "Beals", "Myers", "Mcdonald", "Desmarais", "Hensley", "Ward", "Ford", "Wellman", "Murray", "Hines",
            "Tom", "Patterson", "Graham", "Freeman", "Lakes"
    };

    private String getRandomFirstName() {
        return firstNames[RandomUtils.nextInt(firstNames.length)];
    }

    private String getRandomLastName() {
        return lastNames[RandomUtils.nextInt(lastNames.length)];
    }

    public void deleteAll() {
/*        networkService.getRepository().deleteAll();

        userAccountService.getRepository().deleteAll();*/
    }


    @PostConstruct
    public void postConstruct() {

        if (!(applicationConfig.isTestDataGenerationRequired() && persistenceJpaConfig.getDatabaseType() == PersistenceJpaConfig.DatabaseType.H2)) {
            return;
        }

        logger.info("Test Data Generation Requested");

/*        generateTestNetworks();
        generateTestCrmUsers();
        generateTestAccounts();*/

        logger.info("Test Data Generation done");
    }


}
