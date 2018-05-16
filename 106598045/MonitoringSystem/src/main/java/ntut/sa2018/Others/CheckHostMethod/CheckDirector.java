package ntut.sa2018.Others.CheckHostMethod;

import ntut.sa2018.Others.Interface.PingInterface;

public class CheckDirector {
    public static PingInterface PingBuild(String getMethod){
        switch(getMethod) {
            case "Reachable":
                return new CheckByReachable();
            case "Console":
                return new CheckByConsole();
            default:
                return null;
        }
    }
}
