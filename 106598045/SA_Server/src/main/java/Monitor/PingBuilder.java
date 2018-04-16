package Monitor;

import PingMethod.PingByConsole;
import PingMethod.PingByReachable;

public class PingBuilder {
    public static GetHostStatusCommand Build(int getMethod){
        switch(getMethod) {
            case 1:
                return new PingByReachable();
            case 2:
                return new PingByConsole();
            default:
                return null;
        }
    }
}
