package comp1110.ass2.board;

public enum Resources {
    COCONUTS (6,'C'),
    BAMBOO (6,'B'),
    WATER (6,'W'),
    PRECIOUS_STONE (6,'P'),
    STATUETTS (8,'S');
    

    final int maximun;
    final char shortDescription;



    Resources (int maximum, char shortDescription) {
        this.maximun = maximum;
        this.shortDescription = shortDescription;
    }



}
