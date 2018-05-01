package com.ntnu.wip.nabl.Network;

/**
 * Subscription represents different output which an client can produce.
 * Used for specifying which events to subscribe to
 */
public enum Subscriptions {
    PROJECTS,
    PROJECT_SINGULAR,
    CLIENTS,
    CLIENT_SINGULAR,
    LOG_ENTRIES,
    COMPANIES
}
