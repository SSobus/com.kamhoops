package com.kamhoops.services;


import com.kamhoops.configuration.ApplicationConfig;
import com.kamhoops.configuration.PersistenceJpaConfig;
import com.kamhoops.data.domain.*;
import com.kamhoops.data.domain.enums.UserRole;
import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.exceptions.EntityValidationException;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataGenerationService {
    public final int GENERATE_PLAYER_COUNT = 30;

    private static Logger logger = LoggerFactory.getLogger(DataGenerationService.class);

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private PersistenceJpaConfig persistenceJpaConfig;

    @Autowired
    private SeasonService seasonService;

    @Autowired
    private GameTimeService gameTimeService;

    @Autowired
    private CourtService courtService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private NewsService newsService;

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
            "Hamilton", "Bearden"
    };

    List<Season> seasonsTestData = new ArrayList<>();
    List<GameTime> gameTimeTestData = new ArrayList<>();
    List<News> newsTestData = new ArrayList<>();
    List<Court> courtTestData = new ArrayList<>();
    List<Player> playerTestData = new ArrayList<>();
    List<Team> teamTestData = new ArrayList<>();

    private String getRandomFirstName() {
        return firstNames[RandomUtils.nextInt(firstNames.length)];
    }

    private String getRandomLastName() {
        return lastNames[RandomUtils.nextInt(lastNames.length)];
    }

    public String getRandomEmail() {
        return RandomStringUtils.randomAlphabetic(5).toLowerCase() + "@kamhoops.com";
    }

    public void deleteAll() {
        userAccountService.getRepository().deleteAll();

        gameTimeService.getRepository().deleteAll();
        seasonService.getRepository().deleteAll();
        courtService.getRepository().deleteAll();

        newsService.getRepository().deleteAll();
    }

    //SEASON FUNCTIONS
    public void generateSeasons() throws EntityValidationException {
        List<Season> seasons = new ArrayList<>();

        Season season = new Season();
        season.setStartDate(new LocalDate(2011, 9, 1).toDate());
        season.setEndDate(new LocalDate(2012, 3, 31).toDate());
        seasons.add(season);

        season = new Season();
        season.setStartDate(new LocalDate(2012, 9, 1).toDate());
        season.setEndDate(new LocalDate(2013, 3, 31).toDate());
        season.setCurrentSeason(true);
        seasons.add(season);

        seasonsTestData = seasonService.getRepository().save(seasons);
    }

    public Season getRandomSeason() throws EntityValidationException {
        if (seasonService.repository.count() == 0) {
            generateSeasons();
        }

        return seasonsTestData.get(RandomUtils.nextInt(seasonsTestData.size()));
    }

    public Season getTestSeason() throws EntityValidationException {
        Season season = new Season();
        season.setStartDate(new LocalDate(2012, 1, 1).toDate());
        season.setEndDate(new LocalDate(2012, 12, 31).toDate());

        return season;
    }

    public Season createTestSeason() throws EntityValidationException, EntityNotFoundException {
        return seasonService.create(getTestSeason());
    }

    //GAME TIME FUNCTIONS
    public void generateGameTimes() {
        List<GameTime> gameTimes = new ArrayList<>();

        GameTime gameTime = new GameTime();
        gameTime.setTime("7:00");
        gameTimes.add(gameTime);

        gameTime = new GameTime();
        gameTime.setTime("8:15");
        gameTimes.add(gameTime);

        gameTimeTestData = gameTimeService.getRepository().save(gameTimes);
    }

    public GameTime getRandomGameTime() {
        if (gameTimeService.getRepository().count() == 0)
            generateGameTimes();

        return gameTimeTestData.get(RandomUtils.nextInt(gameTimeTestData.size()));
    }

    public GameTime getTestGameTime() {
        GameTime gameTime = new GameTime();
        gameTime.setTime("7:00");

        return gameTime;
    }

    public GameTime createTestGameTime() throws EntityValidationException, EntityNotFoundException {
        return gameTimeService.create(getTestGameTime());
    }

    public void generateCourts() {
        List<Court> courts = new ArrayList<>();

        Court court = new Court();
        court.setName("Champion");
        courts.add(court);

        court = new Court();
        court.setName("Middle");
        courts.add(court);

        courtTestData = courtService.getRepository().save(courts);
    }

    public Court getRandomCourt() {
        if (courtService.getRepository().count() == 0)
            generateCourts();

        return courtTestData.get(RandomUtils.nextInt(courtTestData.size()));
    }

    public Court getTestCourt() {
        Court court = new Court();
        court.setName(RandomStringUtils.randomAlphabetic(5));

        return court;
    }

    public Court createTestCourt() throws EntityValidationException, EntityNotFoundException {
        return courtService.create(getTestCourt());
    }

    //USER ACCOUNT FUNCTIONS
    public void setAdminUserDetails(UserAccount user) {
        user.setUsername(getRandomFirstName().toLowerCase() + getRandomLastName().toLowerCase());
        user.setEmail(getRandomEmail());
        user.setPassword("1234");
        user.setUserRole(UserRole.ROLE_ADMIN);
    }

    public void setCaptainUserDetails(UserAccount user) {
        user.setUsername(getRandomFirstName().toLowerCase() + getRandomLastName().toLowerCase());
        user.setEmail(getRandomEmail());
        user.setPassword("1234");
        user.setUserRole(UserRole.ROLE_CAPTAIN);
    }

    public UserAccount getRandomAdminUser() {
        UserAccount user = new UserAccount();

        setAdminUserDetails(user);

        return user;
    }

    public UserAccount getRandomCaptainUser() {
        UserAccount user = new UserAccount();

        setCaptainUserDetails(user);

        return user;
    }

    public void generateRandomNews(int count) {
        logger.info("Test News Data Generation Requested");
        News news;
        List<News> newsList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            news = new News();

            news.setTitle(RandomStringUtils.randomAlphabetic(15));
            news.setContent(RandomStringUtils.randomAlphanumeric(200));

            newsList.add(news);
        }

        newsTestData = newsService.getRepository().save(newsList);
    }

    public News getRandomNews() {
        if (newsService.count() == 0) {
            generateRandomNews(10);
        }

        return newsTestData.get(RandomUtils.nextInt(newsTestData.size()));
    }

    public News getTestNews() {
        News news = new News();

        news.setTitle(RandomStringUtils.randomAlphabetic(15));
        news.setContent(RandomStringUtils.randomAlphanumeric(200));

        return news;
    }

    public News createTestNews() throws EntityValidationException, EntityNotFoundException {
        return newsService.create(getTestNews());
    }

    public List<Player> generatePlayers(int count) {
        for (int i = 0; i < count; i++) {
            playerTestData.add(getTestPlayer());
        }

        playerTestData = playerService.getRepository().save(playerTestData);

        return playerTestData;
    }

    public Player getRandomPlayer() {
        if (playerTestData.size() == 0) {
            generatePlayers(GENERATE_PLAYER_COUNT);
        }

        return playerTestData.get(RandomUtils.nextInt(playerTestData.size()));
    }

    public Player getTestPlayer() {
        Player player = new Player();
        player.setFirstName(firstNames[RandomUtils.nextInt(firstNames.length)]);
        player.setLastName(lastNames[RandomUtils.nextInt(lastNames.length)]);

        return player;
    }

    public Player createTestPlayer() throws EntityNotFoundException, EntityValidationException {
        return playerService.create(getTestPlayer());
    }

    public List<Team> generateTeams(int count) {
        for (int i = 0; i < count; i++) {
            teamTestData.add(getTestTeam());
        }

        teamTestData = teamService.getRepository().save(teamTestData);

        return teamTestData;
    }

    public Team getRandomTeam() {
        if (teamTestData.size() == 0) {
            generateTeams(GENERATE_PLAYER_COUNT);
        }

        return teamTestData.get(RandomUtils.nextInt(teamTestData.size()));
    }

    public Team getTestTeam() {
        Team team = new Team();
        team.setName(RandomStringUtils.randomAlphabetic(10));

        return team;
    }

    public Team createTestTeam() throws EntityNotFoundException, EntityValidationException {
        return teamService.create(getTestTeam());
    }


    @PostConstruct
    public void postConstruct() throws EntityValidationException {

        if (!(applicationConfig.isTestDataGenerationRequired() && persistenceJpaConfig.getDatabaseType() == PersistenceJpaConfig.DatabaseType.H2)) {
            return;
        }

        logger.info("Test Data Generation Requested");

        generateSeasons();

        logger.info("Test Data Generation done");
    }
}
