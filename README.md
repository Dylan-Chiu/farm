# farm

### 状态常量定义

#### 操作：
    public static final int PASSWORD_ERROR = -2;
    public static final int USER_NOT_EXIST = -1;
    public static final int SUCCEED = 1;
    public static final int NO_LOGIN = -3;
    public static final int MONEY_NOT_ENOUGH = -4;
    public static final int NUMBER_NOT_ENOUGH = -5;
    public static final int BUYER_ERROR = -6;
    public static final int LAND_OCCUPIED = -7;
    public static final int NO_RESULT = -8;
    public static final int NO_FRIEND_INVITATION = -9;
    public static final int ALREADY_FRIEND = -10;
    public static final int NOT_FRIEND = -11;
    public static final int REPEAT_APPLY = -12;
    public static final int ALREADY_BEST_LAND = -13;

#### 植物：
    public static final Integer STATE_GROW = 0;  
    public static final Integer STATE_DEATH = 1;  
    public static final Integer STATE_WATER = 2;  
    public static final Integer STATE_RIPE = 3;  

#### 土地：
    public static final Integer TYPE_YELLOW = 1;  
    public static final Integer TYPE_RED = 2;  
    public static final Integer TYPE_BLACK = 3;  

#### 一些参数：
    maxLandNumber = 15
    
    unlockedLandNumber = 15
    
    openTestToken = true
    
    dryProbability = 0.001
    
    experienceLength = 200
    
    yellowLandRate = 1
    redLandRate = 2
    blackLandRate = 5
    
    redUpgradeCost = 500
    blackUpgradeCost = 1000
    
    accMoney = 500
    accRate = 10
    accTime = 20
    
    stealRate = 0.2