package org.quan.log;
public class Log {
    public Log(LogType type, String message) {
        System.err.println("[quan]["+type.toString()+"]: "+message);
        if (type == LogType.error)
            System.exit(1);
    }
    public Log(LogType type, String message, boolean strictExit) {
        System.err.println("[quan]["+type.toString()+"]: "+message);
        if (type == LogType.error || strictExit)
            System.exit(1);
    }
}
