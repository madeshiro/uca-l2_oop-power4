package project.uca.power4.ai;

import project.uca.power4.entity.BotPlayer;

public class AIModule {
    private BotPlayer parent;
    private GridAnalyzer analyzer;

    public AIModule(BotPlayer bot) {
        this.parent = bot;
    }

}
