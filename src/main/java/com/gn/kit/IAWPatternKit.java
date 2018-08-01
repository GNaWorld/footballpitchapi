package com.gn.kit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IAWPatternKit {
	//找单�?
	public static String patternFind(String regex,CharSequence input) {
		Pattern pImg = Pattern.compile(regex);
		Matcher mImg = pImg.matcher(input);
		if (mImg.find()) {
			return mImg.group(1);
		}
		return "";
		
	}
}
