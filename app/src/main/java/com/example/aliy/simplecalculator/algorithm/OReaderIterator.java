package com.example.aliy.simplecalculator.algorithm;

public interface OReaderIterator {
	
	char get();
	/*
	 * skip space auto
	 */
	char next();
	void previous();
	boolean hasNext();
	boolean isNumber();
}
