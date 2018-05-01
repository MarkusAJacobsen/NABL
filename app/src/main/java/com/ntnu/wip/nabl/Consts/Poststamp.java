package com.ntnu.wip.nabl.Consts;

/**
 * Poststamp is a representation interface for classes
 * So say you want to send an object via Intent, then this interface
 * holds identifiers for that object, and is to be used as Intent field names
 */
public interface Poststamp {
    String CLIENT = "CLIENT";
    String PROJECT = "PROJECT";
}
