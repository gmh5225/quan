package org.quan.log;
public class Log {
    public Log(LogType type, String message) {
        if (type == LogType.error) {
            System.err.println("[quan]["+type.toString()+"]: "+message);
            System.exit(1);
        } else
            System.out.println("[quan]["+type.toString()+"]: "+message);
    }
    public Log(LogType type, String message, boolean strictExit) {
        if (type == LogType.error || strictExit) {
            System.err.println("[quan]["+type.toString()+"]: "+message);
            System.exit(1);
        } else
            System.out.println("[quan]["+type.toString()+"]: "+message);
    }
}
