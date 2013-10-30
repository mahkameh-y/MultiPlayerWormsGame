package client.GUI;


import client.GUI.URLs.*;
import shared.weapons.*;
import client.GUI.config.ConfigurationManager;
import client.networkManager.TCPManagerClient;
import shared.*;
import shared.Weapon;
import shared.events.*;
import shared.ClientActions.*;
import shared.entity.*;
import shared.entity.holes.BazookaHole;
import shared.entity.holes.BombHole;
import shared.entity.holes.HoleType;
import com.golden.gamedev.Game;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.funbox.KeyCapture;
import com.golden.gamedev.funbox.ErrorNotificationDialog;
import com.golden.gamedev.object.AnimatedSprite;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.sprite.VolatileSprite;
import com.golden.gamedev.object.background.TileBackground;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;


/**
 * Created by IntelliJ IDEA.
 * User: c.ghoroghi
 * Date: Jan 25, 2006
 * Time: 3:22:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class GUI extends Game implements Enviroments, ObjectNames {
    KeyCapture keyMaxFPS;

    static int numOfPlayer = 1;

    ///////////////////////////////////////////////////////////////////////////////
    ////////////////////////////    Fields           //////////////////////////////
    //////////////////////////////////////////////////////////////////////////////

    //groups
    private SpriteGroup islandGroup;
    private SpriteGroup wormGroup;
    private SpriteGroup dynamicObjectsGroup;
    private SpriteGroup staticObjectsGroup;// islandEntities which are static and don't move : hole
    private SpriteGroup infoGroup;
    private static SpriteGroup seaGroup;
    private SpriteGroup backGroundGroup;

    //other fields
    private boolean started = false;
    private int islandinitialy = 300;
    private int seaLenght = 300;
    private static final int WINDOWWIDTH = 940;
    private static final int WINDOWHEIGHT = 680;
    private boolean gameOver = false;
    private URL URLs;
    private int weaponIndex = 1;
    private Weapon[] weaponList;
    private final int numOfWeapons = 5;
    //private HashMap explodableObjects = new HashMap();

    private WormManager wormManager;
    private IslandManager islandManager;
    private InfoManager infoManager;
    private ImageContainer imageContainer;
    //game states
    private static GameStatePacket currentGameState;

    //configuraion
    private Configuration configuration;

    //backgound
    private Background background;
    // private int i = 0;
    private boolean newPacket = false;

    ///////////////////////////////////////////////////////////////////////////////
    ////////////////////////////    Constructors    //////////////////////////////
    //////////////////////////////////////////////////////////////////////////////
    {
        distribute = true;
    }

    public GUI() {
        weaponList = new Weapon[numOfWeapons];
        weaponList[0] = new Shotgun();
        weaponList[1] = new Sheep();
        weaponList[2] = new Bazoka();
        weaponList[3] = new Wall();
        weaponList[4] = new FlyingBomb();
    }

    ///////////////////////////////////////////////////////////////////////////////
    ////////////////////////////    Overridden Methods    /////////////////////////
    //////////////////////////////////////////////////////////////////////////////

    public void initResources() {
        keyMaxFPS = new KeyCapture(bsInput, "HYPERSPEED", 1000) { // 1000ms = 1 second

            public void keyCaptured() {
                setFPS((getFPS() != 3000) ? 3000 : 50);
            }
        };
        distribute = true;
        //setFPS(100);
        if (!started) {
            //********************* titles ***************************
            this.bsGraphics.setWindowIcon(getImage("../../resources/Ladybug.gif"));
            this.bsGraphics.setWindowTitle("Worms MACT ");
            background = new TileBackground(getImages(URLs.getURL(BACKGROUND), 1, 1), 5, 1);

            //*************************** initiation *********************
            initGroups();
            wormManager = new WormManager();
            islandManager = new IslandManager();
            infoManager = new InfoManager();
            imageContainer = new ImageContainer();

            init();
        }

    }

    protected void notifyError(Throwable e) {
        new ErrorNotificationDialog(e, bsGraphics,
                "Mact Worms", "MACT@yahoo.com");
    }

    public void update(long elapsedTime) {
        keyMaxFPS.update(elapsedTime);
        //if (newPacket)
        {
            try {
                //    dynamicObjectsGroup.update(elapsedTime);
                staticObjectsGroup.update(elapsedTime);
                // wormGroup.update(elapsedTime);
                // infoGroup.update(elapsedTime);
                // seaGroup.update(elapsedTime);
                /**************************** keylisteners *************************/
                keyListener();
                /*******************************************************************/
                islandManager.updateBird();
                newPacket = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void render(Graphics2D g) {
        // render all
        background.render(g);
        backGroundGroup.render(g);
        islandGroup.render(g);
        staticObjectsGroup.render(g);
        seaGroup.render(g);

        dynamicObjectsGroup.render(g);
        infoGroup.render(g);

        wormGroup.render(g);

        g.setFont(new Font("", Font.BOLD, 20));
        g.setColor(Color.RED);
        g.drawString("Score :\t ", WINDOWWIDTH - 120, 30);
        g.setFont(new Font("", Font.BOLD, 20));
        g.setColor(Color.YELLOW);
        g.drawString(String.valueOf(infoManager.score), WINDOWWIDTH - 40, 30);

        g.drawString(String.valueOf(infoManager.numOfSheeps), 50, WINDOWHEIGHT - 30);
        g.drawString(String.valueOf(infoManager.numOfBombs), 120, WINDOWHEIGHT - 30);
        g.drawString(String.valueOf(infoManager.numOfBullets), 190, WINDOWHEIGHT - 30);

        //camellia
        //g.drawString(String.valueOf(infoManager.numOfWalls), 260, WINDOWHEIGHT - 30);
        g.drawString(String.valueOf(infoManager.numOfBazooka), 260, WINDOWHEIGHT - 30);
        //camellia
    }

    protected void notifyExit() {

        System.out.println("close");
        TCPManagerClient.close();
    }

    ///////////////////////////////////////////////////////////////////////////////
    ////////////////////////////    Our Methods     //////////////////////////////
    //////////////////////////////////////////////////////////////////////////////
    private void update() {
        /*if (!started) {
            init();
            started = true;
            //return;
        }*/
        newPacket = true;
        if (wormManager == null) {
            return;
        }

        /* ************************* update all ****************************/
        if (gameOver)
            return;
        /******************************* worm ******************************/
        wormManager.updateWorms();

        /******************************* Other URLs *********************/
        deletedObjectsUpdate();
        dynamicObjectsGroup.clear();
        dynamicObjectsGroupUpdate();
        infoManager.infoGroupUpdate();
        staticObjectsGroupUpdate();


        applyEvent();
        wormManager.setTocenter();
        /* i++;
        if (i == 20) {
            Vector temp = new Vector();
            temp.add(new DangerEntity(300, 370));
            testDeletedObjectsUpdate(temp);
        }*/

    }

    private void applyEvent() {
        shared.events.Event event = currentGameState.getEvent();
        if (event != null) {
            if (event instanceof SinkEvent)
                seaUpdate();
        }
    }

    private void keyListener() throws IOException {
        /***********/
        if (keyDown(KeyEvent.VK_LEFT)) {
            TCPManagerClient.sendObject(new MoveLeftAction());
            playSound(SoundURLs.MOVE_URL);

        }
        /***********/
        else if (keyDown(KeyEvent.VK_RIGHT)) {
            TCPManagerClient.sendObject(new MoveRightAction());
            playSound(SoundURLs.MOVE_URL);
        }
        /***********/
        else if (keyPressed(KeyEvent.VK_CONTROL)) {
            TCPManagerClient.sendObject(new ShootAction());

        }
        /***********/
        else if (keyPressed(KeyEvent.VK_ESCAPE)) {
            finish();
        }
        /***********/
        else if (keyPressed(KeyEvent.VK_SPACE)) {
            TCPManagerClient.sendObject(new JumpAction());
            playSound(SoundURLs.JUMP_URL);
        }
        /***********/
        else if (keyPressed(KeyEvent.VK_ALT)) {

            Weapon weapon = weaponList[weaponIndex];
            TCPManagerClient.sendObject(new ChangeWeaponAction(weapon));
            weaponIndex = (++weaponIndex) % numOfWeapons;
            playSound(SoundURLs.CHANGE_WEAPON_URL);

        }
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        if (configuration.getEnvirment().equals(SUNNY))
            URLs = new SunnyDayURLs();
        //mahkameh
        else if (configuration.getEnvirment().equals(CLOUDY))
            URLs = new CloudyDayURLs();
        //mahkameh
    }

    private void initGroups() {
        //********************** groups ***************************
        islandGroup = new SpriteGroup("Island");
        wormGroup = new SpriteGroup("WormURL");
        dynamicObjectsGroup = new SpriteGroup("DObjects");
        staticObjectsGroup = new SpriteGroup("SObjects");
        infoGroup = new SpriteGroup("infoGroup");
        seaGroup = new SpriteGroup("sea");
        backGroundGroup = new SpriteGroup("bgGroup");

        //********************* the game background ****************
        islandGroup.setBackground(background);
        wormGroup.setBackground(background);
        dynamicObjectsGroup.setBackground(background);
        staticObjectsGroup.setBackground(background);
        seaGroup.setBackground(background);
    }

    private void init() {
        islandManager.initIsland();
        imageContainer.loadImage();
        wormManager.initWorms();
        infoManager.initInfoGroup();

        try {
            TCPManagerClient.sendObject(new StartEvent());
            System.out.println("started");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void staticObjectsGroupUpdate() {
        Vector holeEntities = currentGameState.getIslandStaticEntities();
        if (holeEntities == null)
            return;
        for (int j = 0; j < holeEntities.size(); j++) {
            if (holeEntities.elementAt(j) instanceof HoleEntity) {
                HoleEntity holeEntity = (HoleEntity) holeEntities.elementAt(j);
                staticObjectsGroup.add(new Sprite(imageContainer.getHoleImage(holeEntity.getType()), holeEntity.getX(), holeEntity.getY()));
                playSound(SoundURLs.FIRE_URL);
            } else if (holeEntities.elementAt(j) instanceof WallEntity) {
                WallEntity wallEntity = (WallEntity) holeEntities.elementAt(j);

                staticObjectsGroup.add(new Sprite(imageContainer.wallImage, wallEntity.getX(), wallEntity.getY()));
                playSound(SoundURLs.FIRE_URL);
            }
        }
    }

    public void seaUpdate() {
        if (seaGroup == null)
            return;
        Sprite[] sprites = seaGroup.getSprites();
        for (int j = 0; j < seaGroup.getSize(); j++) {
            Sprite sprite = sprites[j];
            sprite.setY(sprite.getY() - GUIScales.eachSinkingScale);
        }
    }

    private void deletedObjectsUpdate() {
        Vector deletedObjects = currentGameState.getDeletedObjects();
        if (deletedObjects != null)
            for (int j = 0; j < deletedObjects.size(); j++) {
                IslandEntity islandEntity = (IslandEntity) deletedObjects.elementAt(j);
                deleteObject(islandEntity);
            }
    }

    /* private void deleteObject(IslandEntity islandEntity) {
        HashMap yHashMap = (HashMap) explodableObjects.get(new Integer(islandEntity.getX()));
        Sprite sprite = (Sprite) yHashMap.get(new Integer(islandEntity.getY()));
        sprite.setActive(false);
        staticObjectsGroup.remove(sprite);//todo : be che goroohi add shode ?
        BufferedImage[] images = getImages(URLs.getURL(EXPLOSION), 7, 1);
        VolatileSprite explosion = new VolatileSprite(images, islandEntity.getX(), islandEntity.getY());
        wormGroup.add(explosion);//
    }*/
    private void deleteObject(IslandEntity islandEntity) {

        BufferedImage[] images = getImages(URLs.getURL(EXPLOSION), 7, 1);
        VolatileSprite explosion = new VolatileSprite(images, islandEntity.getX(), islandEntity.getY());
        //wormGroup.add(explosion);
    }


    private void dynamicObjectsGroupUpdate() {
        Vector entities = currentGameState.getMovedObjects();
        String URL;
        if (entities != null)
            for (int i = 0; i < entities.size(); i++) {
                IslandEntity islandEntity = (IslandEntity) entities.elementAt(i);


                dynamicObjectsGroup.add(new Sprite(getEntityImages(islandEntity), islandEntity.getX(), islandEntity.getY()));
            }
    }

    synchronized public void updateState(GameStatePacket packet) {
        currentGameState = packet;
        update();

    }

    private BufferedImage getEntityImages(Object obj) {

        if (obj instanceof GiftEntity) {
            return imageContainer.giftImage;
        } else if (obj instanceof DeathEntity) {
            return imageContainer.deathImage;
        } else if (obj instanceof WallEntity) {
            return imageContainer.wallImage;
        } else if (obj instanceof FlyingBombEntity) {
            return imageContainer.flyingBombImage;
        } else if (obj instanceof BulletEntity) {
            return imageContainer.getBulletImage(((BulletEntity) obj).getDirection());
        } else if (obj instanceof SheepEntity) {
            return imageContainer.sheepImage;
        } else if (obj instanceof BazookaEntity) {
            return imageContainer.getBazookaBulletImage(((BazookaEntity) obj).getDirection());
        }
        return null;
    }

    public void setNumOfPlayer(int num) {
        numOfPlayer = num;
    }

    ///////////////////////////////////////////////////////////////////////////////
    ////////////////////////////    Inner classes    //////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////

    final private class WormManager {

        //sprites
        private Sprite worm1Sprite;
        private Sprite worm2Sprite;
        private Sprite weaponWorm1;
        private Sprite weaponWorm2;

        /*//images
      private BufferedImage wormLeftImage1;
      private BufferedImage wormRightImage1;
      private BufferedImage wormLeftJumpImage1;
      private BufferedImage wormRightJumpImage1;
      private BufferedImage wormDrawnImage1;
      //  private BufferedImage wormSimpleImage1;

      private BufferedImage wormLeftImage2;
      private BufferedImage wormRightImage2;
      private BufferedImage wormLeftJumpImage2;
      private BufferedImage wormRightJumpImage2;
      private BufferedImage wormDrawnImage2;*/
        // private BufferedImage wormSimpleImage2;

        public WormManager() {
        }

        ///////////////////////////////////////////////////////////////////
        //////////////////////// Inits   //////////////////////////////////
        ///////////////////////////////////////////////////////////////////


        private void initWorm1() {
            if (currentGameState != null) {
                WormEntity wormObj = currentGameState.getWorm1();
                worm1Sprite = new Sprite(imageContainer.getWormImage(wormObj.getState(), wormObj.getDirection(), 1), wormObj.getX(), wormObj.getY());

            } else
                worm1Sprite = new Sprite();
            wormGroup.add(worm1Sprite);
        }

        private void initWorm2() {
            if (currentGameState != null) {
                WormEntity wormObj = currentGameState.getWorm2();
                worm2Sprite = new Sprite(imageContainer.getWormImage(wormObj.getState(), wormObj.getDirection(), 2), wormObj.getX(), wormObj.getY());
            } else {
                worm2Sprite = new Sprite();
            }
            wormGroup.add(worm2Sprite);


        }

        private void initWorms() {
            initWorm1();
            initWeapon1();
            initWorm2();
            initWeapon2();
        }

        private void initWeapon1() {
            weaponWorm1 = new Sprite(imageContainer.shotgunWeaponRightImage, 100, 100);
            wormGroup.add(weaponWorm1);
            weaponWorm1.setActive(false);

        }

        private void initWeapon2() {
            weaponWorm2 = new Sprite(imageContainer.shotgunWeaponRightImage, 200, 100);
            wormGroup.add(weaponWorm2);
            weaponWorm2.setActive(false);
        }

        ///////////////////////////////////////////////////////////////////
        //////////////////////// Updates //////////////////////////////////
        ///////////////////////////////////////////////////////////////////

        private void updateWorm1() {

            WormEntity wormObj = currentGameState.getWorm1();
            if (wormObj != null && !wormObj.isGameOver()) {
                int x = wormObj.getX();
                int y = wormObj.getY();
                int direction = wormObj.getDirection();

                worm1Sprite.setX(x);
                worm1Sprite.setY(y);
                worm1Sprite.setImage(imageContainer.getWormImage(wormObj.getState(), direction, 1));
                if (wormObj.getWeapon() != null) {
                    Dimension newPlace = wormObj.getWeapon().getSuitablePlace(direction, x, y, wormObj.getState());
                    weaponWorm1.setActive(true);
                    weaponWorm1.setLocation(newPlace.getWidth(), newPlace.getHeight());
                    updateWeapon1(direction);
                }
                /* updateWeapon1(direction);
                                if (direction == 1) {
                                    if (wormObj.getState().equals(WormState.JUMP))
                                        x += 15;
                                    else
                                        x += 35;
                                }
                                weaponWorm1.setLocation(x, y + 42);
                */

                if (wormObj.getState().equals(WormState.DRAWN))
                    playSound(SoundURLs.DRAWN_URL);
            } else {
                gameOver = true;
                if (numOfPlayer == 1)
                    gameOver();
                else
                    winnig();
            }

        }

        private void winnig() {

            Sprite gameOverSprite = new Sprite(getImage(URLs.getURL(WIN)), 200, 250);
            infoGroup.add(gameOverSprite);
        }

        private void updateWeapon1(int direction) {

            if (currentGameState == null)
                return;
            if (currentGameState.getWorm1().getWeapon() instanceof Shotgun)
                weaponWorm1.setImage(imageContainer.getShotgunImage(direction));
            else if (currentGameState.getWorm1().getWeapon() instanceof FlyingBomb)
                weaponWorm1.setImage(imageContainer.flyingBombWeaponImage);
            else if (currentGameState.getWorm1().getWeapon() instanceof Bazoka)
                weaponWorm1.setImage(imageContainer.getBazookaImage(direction));
            else if (currentGameState.getWorm1().getWeapon() instanceof Wall)
                weaponWorm1.setImage(imageContainer.wallWeaponImage);
            else if (currentGameState.getWorm1().getWeapon() instanceof Sheep)
                weaponWorm1.setImage(imageContainer.sheepWeaponImage);
        }

        private void updateWorm2() {

            WormEntity wormObj = currentGameState.getWorm2();
            if (wormObj != null && !wormObj.isGameOver()) {
                int x = wormObj.getX();
                int y = wormObj.getY();
                int direction = wormObj.getDirection();

                worm2Sprite.setX(x);
                worm2Sprite.setY(y);

                worm2Sprite.setImage(imageContainer.getWormImage(wormObj.getState(), direction, 2));

                if (wormObj.getWeapon() != null) {
                    Dimension newPlace = wormObj.getWeapon().getSuitablePlace(direction, x, y, wormObj.getState());
                    weaponWorm2.setActive(true);
                    weaponWorm2.setLocation(newPlace.getWidth(), newPlace.getHeight());
                    updateWeapon2(direction);
                }
                /*updateWeapon2(direction);
                if (direction == 1) {
                    if (wormObj.getState().equals(WormState.JUMP))
                        x += 15;
                    else
                        x += 35;
                }
                weaponWorm2.setLocation(x, y + 42);*/



                if (wormObj.getState().equals(WormState.DRAWN))
                    playSound(SoundURLs.DRAWN_URL);

            } else {
                gameOver = true;
                if (numOfPlayer == 2)
                    gameOver();
                else
                    winnig();
            }

        }

        private void updateWeapon2(int direction) {

            if (currentGameState == null)
                return;

            if (currentGameState.getWorm2().getWeapon() instanceof Shotgun)
                weaponWorm2.setImage(imageContainer.getShotgunImage(direction));
            else if (currentGameState.getWorm2().getWeapon() instanceof FlyingBomb)
                weaponWorm2.setImage(imageContainer.flyingBombWeaponImage);
            else if (currentGameState.getWorm2().getWeapon() instanceof Bazoka)
                weaponWorm2.setImage(imageContainer.getBazookaImage(direction));
            else if (currentGameState.getWorm2().getWeapon() instanceof Wall)
                weaponWorm2.setImage(imageContainer.wallWeaponImage);
            else if (currentGameState.getWorm2().getWeapon() instanceof Sheep)
                weaponWorm2.setImage(imageContainer.sheepWeaponImage);

        }

        private void updateWorms() {
            updateWorm1();
            updateWorm2();
        }

        private void gameOver() {
            Sprite gameOverSprite = new Sprite(getImage(URLs.getURL(GAMEOVER)), 300, 250);
            infoGroup.add(gameOverSprite);
        }


        private void setTocenter() {
            if (numOfPlayer == 1)
                background.setToCenter(worm1Sprite);
            else
                background.setToCenter(worm2Sprite);
        }

    }

    final private class IslandManager {
        AnimatedSprite birdSprite;

        private void initIsland() {
            islandGroup.add(new Sprite(getImage(URLs.getURL(GROUND)), 0, islandinitialy - 45));

            /****** SEA ***/
            initSea();

            for (int i = 0; i < configuration.getEntities().size(); i++) {
                IslandEntity entity = (IslandEntity) configuration.getEntities().elementAt(i);
                if (entity instanceof BottleEntity) {
                    initBottle(entity);
                } else if (entity instanceof Aqua1Entity) {
                    initAqua1(entity);
                } else if (entity instanceof Aqua2Entity) {
                    initAqua2(entity);
                } else if (entity instanceof FrogEntity) {
                    initFrog(entity);
                } else if (entity instanceof DiverEntity) {
                    initDiver(entity);
                } else if (entity instanceof Cloud1Entity) {
                    initCloud1(entity);
                } else if (entity instanceof Cloud2Entity) {
                    initCloud2(entity);
                } else if (entity instanceof BirdEntity) {
                    initBird(entity);
                } else if (entity instanceof SkeltonEntity) {
                    initSkelton(entity);
                } else if (entity instanceof DangerEntity) {
                    initDanger(entity);
                } else if (entity instanceof SunEntity) {
                    initSun(entity);
                } else if (entity instanceof StarEntity) {
                    initStar(entity);
                } else if (entity instanceof MoonEntity) {
                    initMoon(entity);
                }

            }
        }

        private void initMoon(IslandEntity entity) {
            backGroundGroup.add(new Sprite(getImage(URLs.getURL(MOON)), entity.getX(), entity.getY()));
        }

        private void initStar(IslandEntity entity) {
            backGroundGroup.add(new Sprite(getImage(URLs.getURL(STAR)), entity.getX(), entity.getY()));
        }

        private void initSun(IslandEntity entity) {
            backGroundGroup.add(new Sprite(getImage(URLs.getURL(SUN)), entity.getX(), entity.getY()));
        }

        private void initDanger(IslandEntity entity) {
            Sprite sprite = new Sprite(getImage(URLs.getURL(DANGER)), entity.getX(), entity.getY());
            staticObjectsGroup.add(sprite);
        }

        private void initSkelton(IslandEntity entity) {
            staticObjectsGroup.add(new Sprite(getImage(URLs.getURL(SKELTON)), entity.getX(), entity.getY()));
        }

        private void initSea() {
            String url = URLs.getURL(SEA);
            BufferedImage[] seaImage = getImages(url, 1, 1);

            //seaGroup.add(new Sprite(seaImage, 1995, islandinitialy + 250));
          
            int temp = 295;

            AnimatedSprite animatedSprite = new AnimatedSprite(seaImage, 0, islandinitialy + temp);
            seaGroup.add(animatedSprite);
            //animatedSprite.setAnimate(true);
            //animatedSprite.setLoopAnim(true);

            AnimatedSprite animatedSprite1 = new AnimatedSprite(seaImage, seaLenght, islandinitialy + temp);
            seaGroup.add(animatedSprite1);
            //animatedSprite1.setAnimate(true);
            //animatedSprite1.setLoopAnim(true);

            AnimatedSprite animatedSprite2 = new AnimatedSprite(seaImage, 2 * seaLenght, islandinitialy + temp);
            seaGroup.add(animatedSprite2);
            //animatedSprite2.setAnimate(true);
            //animatedSprite2.setLoopAnim(true);

            seaGroup.add(new AnimatedSprite(seaImage, 3 * seaLenght, islandinitialy + temp));
            seaGroup.add(new AnimatedSprite(seaImage, 4 * seaLenght, islandinitialy + temp));
            seaGroup.add(new AnimatedSprite(seaImage, 5 * seaLenght, islandinitialy + temp));
            seaGroup.add(new AnimatedSprite(seaImage, 6 * seaLenght, islandinitialy + temp));
            seaGroup.add(new AnimatedSprite(seaImage, 7 * seaLenght, islandinitialy + temp));
            seaGroup.add(new AnimatedSprite(seaImage, 8 * seaLenght, islandinitialy + temp));
        }


        private void initBird(IslandEntity entity) {
            String birdURL = URLs.getURL(BIRD);
            BufferedImage[] images = new BufferedImage[7];
            for (int i = 1; i < 8; i++) {
                images[i - 1] = getImage(birdURL + i + ".gif");
            }
            birdSprite = new AnimatedSprite(images, entity.getX(), entity.getY());
            birdSprite.setLoopAnim(true);
            birdSprite.setAnimate(true);
            birdSprite.setHorizontalSpeed(0.1);
            staticObjectsGroup.add(birdSprite);
        }

        private void updateBird() {
            if (birdSprite.getX() > 2500)
                birdSprite.setX(20);
            birdSprite.setHorizontalSpeed(0.1);
        }

        private void initCloud2(IslandEntity entity) {
            backGroundGroup.add(new Sprite(getImage(URLs.getURL(CLOUD2)), entity.getX(), entity.getY()));
        }

        private void initCloud1(IslandEntity entity) {
            backGroundGroup.add(new Sprite(getImage(URLs.getURL(CLOUD1)), entity.getX(), entity.getY()));
        }

        private void initDiver(IslandEntity entity) {
            islandGroup.add(new Sprite(getImage(URLs.getURL(DIVER)), entity.getX(), islandinitialy + entity.getY()));
        }

        private void initFrog(IslandEntity entity) {
            islandGroup.add(new Sprite(getImage(URLs.getURL(FROG)), entity.getX(), islandinitialy + entity.getY()));
        }

        private void initAqua2(IslandEntity entity) {
            islandGroup.add(new Sprite(getImage(URLs.getURL(AQUA2)), entity.getX(), islandinitialy + entity.getY()));
        }

        private void initAqua1(IslandEntity entity) {
            islandGroup.add(new Sprite(getImage(URLs.getURL(AQUA1)), entity.getX(), islandinitialy + entity.getY()));
        }

        private void initBottle(IslandEntity entity) {
            seaGroup.add(new Sprite(getImage(URLs.getURL(BOTTLE)), entity.getX(), islandinitialy + entity.getY()));
        }


    }

    final private class InfoManager {

        private int score = 0;
        // private int numOfWalls = 0; //camellia
        private int numOfBullets = 0;
        private int numOfSheeps = 0;
        private int numOfBazooka = 0;
        private int numOfBombs = 0;
        private Sprite lifeSprite;

        private void infoGroupUpdate() {
            WormEntity wormObj = null;
            if (numOfPlayer == 1)
                wormObj = currentGameState.getWorm1();
            else
                wormObj = currentGameState.getWorm2();

            if (wormObj == null)
                return;
            lifeSprite.setImage(getImage(URLs.getLifeNoURL(wormObj.getLife())));
            numOfBombs = wormObj.getNumOfBombs();
            numOfBullets = wormObj.getNumOfBullets();
            numOfBazooka = wormObj.getNumOfBazookaBullet();
            numOfSheeps = wormObj.getNumOfSheeps();
            // numOfWalls = wormObj.getNumOfWalls();    //camellia

            if (numOfPlayer == 1)
                this.score = currentGameState.getWorm1().getScore();
            else
                this.score = currentGameState.getWorm2().getScore();
        }

        private void initInfoGroup() {

            lifeSprite = new Sprite(getImage(InfoURL.getLifeImage(5)), WINDOWWIDTH - 70, WINDOWHEIGHT - 70);
            Sprite bombSprite = new Sprite(getImage(URLs.getBombNoURL()), 100, WINDOWHEIGHT - 60);
            Sprite bulletSprite = new Sprite(getImage(URLs.getBulletNoURL()), 170, WINDOWHEIGHT - 60);
            Sprite sheepSprite = new Sprite(getImage(URLs.getSheepNoURL()), 30, WINDOWHEIGHT - 60);
            //camellia
//            Sprite wallSprite = new Sprite(getImage(URLs.getWallNoURL()), 240, WINDOWHEIGHT - 60);
            Sprite bazookaSprite = new Sprite(getImage(URLs.getBazookaBulletNoURL()), 240, WINDOWHEIGHT - 60);
            //camellia

            infoGroup.add(lifeSprite);
            infoGroup.add(bombSprite);
            infoGroup.add(bulletSprite);
            // infoGroup.add(wallSprite);
            infoGroup.add(bazookaSprite);
            infoGroup.add(sheepSprite);


        }

    }

    private class ImageContainer {

        public BufferedImage bulletImageLeft;
        public BufferedImage bulletImageRight;
        public BufferedImage flyingBombImage;


        private BufferedImage wallImage;
        private BufferedImage giftImage;
        private BufferedImage sheepImage;
        private BufferedImage deathImage;

        private BufferedImage wormLeftImage1;
        private BufferedImage wormRightImage1;
        private BufferedImage wormLeftJumpImage1;
        private BufferedImage wormRightJumpImage1;
        private BufferedImage wormDrawnImage1;
        private BufferedImage wormDeadRightImage1;
        private BufferedImage wormDeadLeftImage1;

        private BufferedImage wormLeftImage2;
        private BufferedImage wormRightImage2;
        private BufferedImage wormLeftJumpImage2;
        private BufferedImage wormRightJumpImage2;
        private BufferedImage wormDrawnImage2;
        private BufferedImage wormDeadRightImage2;
        private BufferedImage wormDeadLeftImage2;

        private BufferedImage shotgunWeaponLeftImage;
        private BufferedImage shotgunWeaponRightImage;
        private BufferedImage wallWeaponImage;
        private BufferedImage flyingBombWeaponImage;
        private BufferedImage sheepWeaponImage;
        private BufferedImage bazookaWeaponRightImage;
        private BufferedImage bazookaWeaponLeftImage;

        private BufferedImage holeBombImage;
        private BufferedImage holeTunnelImage;
        private BufferedImage holeWellImage;

        public BufferedImage bazookaBulletImageLeft;
        public BufferedImage bazookaBulletImageRight;

        public void loadImage() {
            System.out.println("start loading images");
            initObjectsImages();
            initWormImages();
            initWeaponsImages();
            initHoleImages();
            System.out.println("stop loading images");
        }

        private void initWormImages() {
            wormLeftImage1 = getImage(URLs.getWormURL(WormState.NORMAL, -1, 1));
            wormRightImage1 = getImage(URLs.getWormURL(WormState.NORMAL, 1, 1));
            wormLeftJumpImage1 = getImage(URLs.getWormURL(WormState.JUMP, -1, 1));
            wormRightJumpImage1 = getImage(URLs.getWormURL(WormState.JUMP, 1, 1));
            wormDeadLeftImage1 = getImage(URLs.getWormURL(WormState.DEAD, -1, 1));
            wormDeadRightImage1 = getImage(URLs.getWormURL(WormState.DEAD, 1, 1));
            wormDrawnImage1 = getImage(URLs.getWormURL(WormState.DRAWN, 1, 1));

            wormLeftImage2 = getImage(URLs.getWormURL(WormState.NORMAL, -1, 2));
            wormRightImage2 = getImage(URLs.getWormURL(WormState.NORMAL, 1, 2));
            wormLeftJumpImage2 = getImage(URLs.getWormURL(WormState.JUMP, -1, 2));
            wormRightJumpImage2 = getImage(URLs.getWormURL(WormState.JUMP, 1, 2));
            wormDeadLeftImage2 = getImage(URLs.getWormURL(WormState.DEAD, -1, 1));
            wormDeadRightImage2 = getImage(URLs.getWormURL(WormState.DEAD, 1, 1));
            wormDrawnImage2 = getImage(URLs.getWormURL(WormState.DRAWN, 1, 2));
        }

        private void initWeaponsImages() {

            shotgunWeaponLeftImage = getImage(URLs.getGunURL(-1));
            shotgunWeaponRightImage = getImage(URLs.getGunURL(1));

            wallWeaponImage = getImage(URLs.getURL(SWALL));

            flyingBombWeaponImage = getImage(URLs.getURL(LFBOMB));

            sheepWeaponImage = getImage(URLs.getURL(SSHEEP));

            bazookaWeaponLeftImage = getImage(URLs.getBazokaURL(-1));
            bazookaWeaponRightImage = getImage(URLs.getBazokaURL(1));
        }

        private void initObjectsImages() {
            bulletImageLeft = getImage(URLs.getBulletURL(-1));
            bulletImageRight = getImage(URLs.getBulletURL(1));
            flyingBombImage = getImage(URLs.getURL(FLYINGBOMB));
            wallImage = getImage(URLs.getURL(WALL));
            sheepImage = getImage(URLs.getURL(SHEEP));
            deathImage = getImage(URLs.getURL(DEATH));
            giftImage = getImage(URLs.getURL(GIFT));
            bazookaBulletImageLeft = getImage(URLs.getBazookaBulletURL(-1));
            bazookaBulletImageRight = getImage(URLs.getBazookaBulletURL(1));
        }

        private void initHoleImages() {
            /*holeBombImage = getImage(URLs.getHoleURL(HoleType.bombHole));
            holeTunnelImage = getImage(URLs.getHoleURL(HoleType.tunnel));
            holeWellImage = getImage(URLs.getHoleURL(HoleType.well));*/

            holeBombImage = getImage(URLs.getHoleURL(new BombHole()));
            holeTunnelImage = getImage(URLs.getHoleURL(new BazookaHole()));
            holeWellImage = getImage(URLs.getHoleURL(new BazookaHole()));
        }

        public BufferedImage getBulletImage(int direction) {
            if (direction == -1)
                return bulletImageLeft;
            else
                return bulletImageRight;
        }

        public BufferedImage getBazookaBulletImage(int direction) {
            if (direction == -1)
                return bazookaBulletImageLeft;
            else
                return bazookaBulletImageRight;
        }

        private BufferedImage getShotgunImage(int direction) {
            if (direction == 1)
                return shotgunWeaponRightImage;
            return shotgunWeaponLeftImage;
        }

        private BufferedImage getBazookaImage(int direction) {
            if (direction == 1)
                return bazookaWeaponRightImage;
            return bazookaWeaponLeftImage;
        }

        public BufferedImage getHoleImage(HoleType holeType) {
            /*if (holeType.equals(HoleType.bombHole))
                return holeBombImage;
            else if (holeType.equals(HoleType.tunnel))
                return holeTunnelImage;
            else if (holeType.equals(HoleType.well))
                return holeWellImage;
            return holeBombImage;*/
            if (holeType instanceof BombHole)
                return holeBombImage;
            else if (holeType instanceof BazookaHole)
                return holeTunnelImage;


            return holeBombImage;
        }

        private BufferedImage getWormImage(String state, int direction, int wormNum) {
            if (wormNum == 1) {
                if (state.equals(WormState.NORMAL) && direction == -1)
                    return wormLeftImage1;
                else if (state.equals(WormState.NORMAL) && direction == 1)
                    return wormRightImage1;
                else if (state.equals(WormState.JUMP) && direction == -1)
                    return wormLeftJumpImage1;
                else if (state.equals(WormState.JUMP) && direction == 1)
                    return wormRightJumpImage1;
                else if (state.equals(WormState.DRAWN))
                    return wormDrawnImage1;
                else if (state.equals(WormState.DEAD) && direction == 1)
                    return wormDeadRightImage1;
                else if (state.equals(WormState.DEAD) && direction == -1)
                    return wormDeadLeftImage1;
            } else {
                if (state.equals(WormState.NORMAL) && direction == -1)
                    return wormLeftImage2;
                else if (state.equals(WormState.NORMAL) && direction == 1)
                    return wormRightImage2;
                else if (state.equals(WormState.JUMP) && direction == -1)
                    return wormLeftJumpImage2;
                else if (state.equals(WormState.JUMP) && direction == 1)
                    return wormRightJumpImage2;
                else if (state.equals(WormState.DRAWN))
                    return wormDrawnImage2;
                else if (state.equals(WormState.DEAD) && direction == 1)
                    return wormDeadRightImage2;
                else if (state.equals(WormState.DEAD) && direction == -1)
                    return wormDeadLeftImage2;

            }
            return wormRightImage1;
        }

    }

    ///////////////////////////////////////////////////////////////////////////////
    ////////////////////////////      Main           //////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////

    public void main(GUI gui, GameInfo gameInfo) {

        //mahkameh
        ConfigurationManager.loadMap(gameInfo.getConfig());
        //mahkameh
        Configuration configuration = ConfigurationManager.getConfiguration();

        TCPManagerClient.setGameServerIP(gameInfo);

        gui.setConfiguration(configuration);

        GameLoader game = new GameLoader();

        game.setup(gui, new Dimension(GUI.WINDOWWIDTH, GUI.WINDOWHEIGHT), false);
        game.start();


    }

}