package com.bzu.softwareconstruction.utils;

import java.util.Collection;

public class CollectionUtils {
	/**
	 * Add element to the collection if its not null
	 * @param collection
	 * @param element
	 * @return
	 */
	public static <E> boolean addNotNull(Collection<E> collection, E element) {
		if (element == null) {
			return false;
		}
		return collection.add(element);
	}
}
