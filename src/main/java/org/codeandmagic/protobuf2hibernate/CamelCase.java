package org.codeandmagic.protobuf2hibernate;

import java.util.concurrent.ConcurrentHashMap;

/**
 */
public final class CamelCase {
	private CamelCase() { /* nothing */ }

	private static final ConcurrentHashMap<String, String> UNDERSCORED_TO_CAMELCASE = new ConcurrentHashMap<String, String>(100);
	private static final ConcurrentHashMap<String, String> CAMELCASE_TO_UNDERSCORED = new ConcurrentHashMap<String, String>(100);

	public static String toUnderscored(final String _name) {
		if (_name == null || _name.length() == 0) {
			return _name;
		}

		final String exists = CAMELCASE_TO_UNDERSCORED.get(_name);
		if (exists != null) {
			return exists;
		}

		boolean ccChanged = false;
		boolean alreadyUS = false;
		final StringBuilder sb = new StringBuilder(_name.length() + 2);
		for (int i = 0; i < _name.length(); i++) {
			final char c = _name.charAt(i);
			if (Character.isUpperCase(c)) {
				ccChanged = true;
				sb.append('_');
				sb.append(Character.toLowerCase(c));
			} else {
				if (c == '_') {
					// ouch, we got a (partially?) underscored word...
					// things get really fuzzy here
					alreadyUS = true;
				}
				sb.append(c);
			}
		}

		final String result = ccChanged ? sb.toString() : _name;
		CAMELCASE_TO_UNDERSCORED.put(_name, result);

		if (!alreadyUS) {
			// at the very least, don't put it in the reverse table
			UNDERSCORED_TO_CAMELCASE.put(result, _name);
		}
		return result;
	}

	//
	//
	//

	public static String fromUnderscored(final String _name) {
		if (_name == null || _name.length() == 0) {
			return _name;
		}

		final String exists = UNDERSCORED_TO_CAMELCASE.get(_name);
		if (exists != null) {
			return exists;
		}

		boolean ccChanged = false;
		boolean alreadyCC = false;
		final StringBuilder sb = new StringBuilder(_name.length());
		for (int i = 0; i < _name.length(); i++) {
			final char c = _name.charAt(i);
			if (c == '_') {
				ccChanged = true;
				i++;
				if (i < _name.length()) {
					sb.append(Character.toUpperCase(_name.charAt(i)));
				} else {
					break;
				}
			} else {
				if (Character.isUpperCase(c) && i > 0 && Character.isLowerCase(_name.charAt(i - 1))) {
					// ouch, already camel-cased?
					alreadyCC = true;
				}
				sb.append(c);
			}
		}

		final String result = ccChanged ? sb.toString() : _name;
		UNDERSCORED_TO_CAMELCASE.put(_name, result);
		if (!alreadyCC) {
			// something fishy here, let's not put the reverse in
			CAMELCASE_TO_UNDERSCORED.put(result, _name);
		}
		return result;
	}
}
