package org.quan.log;
public class Log {
    public Log(LogTypes type, String message) {
        System.err.println("[quan]["+type.toString()+"]: "+message);
        if (type == LogTypes.error)
            System.exit(1);
    }
    public Log(LogTypes type, String message, boolean strictExit) {
        System.err.println("[quan]["+type.toString()+"]: "+message);
        if (type == LogTypes.error || strictExit)
            System.exit(1);
    }
}
