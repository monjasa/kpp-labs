package org.monjasa.lab03.model;

public interface Block<T extends CharSequence> {

    int length();

    CharSequence getSequence();
}