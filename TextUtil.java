package teste.main;

import java.util.ArrayList;
import java.util.List;

public class TextUtil {
	public static final int FONT_SMALL = 0;
	public static final int FONT_HIGH = 1;
	public static final int FONT_MEDIUM = 2;

	public static StringBuffer getStringBuffer(int fontSize, String text) {
		return getStringBuffer(fontSize, text, null);
	}

	public static StringBuffer getStringBuffer(int fontSize, String text, String[] aditionalTags) {
		StringBuffer stringBuffer = new StringBuffer();
		int lineSize = getLineSizePerFontSize(fontSize);
		String sizeTag = getTagPerFontSize(fontSize);
		String[] words = text.split(" ");
		String currentLine = "";

		int i = 0;

		while (i < words.length) {
			boolean currentLineIsFull = (currentLine + " " + words[i].trim()).length() > lineSize;

			if (currentLineIsFull) {
				if (aditionalTags != null)
					stringBuffer.append("{reset}{center}" + concatAditicalTags(aditionalTags) + sizeTag
							+ justify(currentLine.trim(), lineSize) + "{br}");
				else
					stringBuffer.append("{reset}{center}" + sizeTag
							+ justify(currentLine.trim(), lineSize) + "{br}");

				currentLine = "";
			} else if (i == words.length - 1) {
				if (aditionalTags != null)
					stringBuffer.append("{reset}" + concatAditicalTags(aditionalTags) + sizeTag
							+ currentLine.trim() + " " + words[i].trim() + "{br}");
				else
					stringBuffer.append("{reset}" + sizeTag
						+ currentLine.trim() + " " + words[i].trim() + "{br}");					
					
				currentLine = "";
				i++;
			} else {
				currentLine += " " + words[i].trim();
				i++;
			}
		}

		return stringBuffer;
	}

	private static String concatAditicalTags(String[] aditionalTags) {
		String result = "";

		for (int i = 0; i < aditionalTags.length; i++) {
			result += aditionalTags[i].trim();
		}

		return result.trim();
	}

	private static String justify(String text, int maxLength) {
		int leftCharacters = maxLength - text.length();

		if (leftCharacters == 0)
			return text;

		String[] words = text.split(" ");
		int currentLeftCharacters = leftCharacters;
		String result = "";
		int countLeftWords = 0;

		for (int i = 0; i < words.length - 1; i++) {
			words[i] = words[i] + " ";
		}

		result = addSpaceBetweenWords(words, currentLeftCharacters, result);

		List<String> leftWords = new ArrayList<String>();

		for (int i = words.length - countLeftWords; i < words.length; i++) {
			leftWords.add(words[i]);
		}

		for (int i = 0; i < leftWords.size(); i++) {
			result += leftWords.get(i);
		}

		return result;
	}

	private static String addSpaceBetweenWords(String[] words, int leftCharacters, String result) {
		words[words.length - 1] = words[words.length - 1].trim();
		boolean restartLoop = true;
		String[] tempWords = words;
		List<String> tempList = new ArrayList<String>();

		while (restartLoop) {
			for (int i = 0; i < words.length - 1; i++) {
				tempList.add(words[i] + " ");

				leftCharacters--;

				if (leftCharacters == 0) {
					restartLoop = false;

					break;
				}
			}

			tempWords = new String[tempList.size()];
			tempWords = tempList.toArray(tempWords);
			tempList = new ArrayList<String>();

			for (int i = 0; i < tempWords.length; i++) {
				words[i] = tempWords[i];
			}
		}

		for (int i = 0; i < words.length; i++) {
			result += words[i];
		}

		return result;
	}

	private static String getTagPerFontSize(int fontSize) {
		switch (fontSize) {
		case 0:
			return "{s}";
		case 1:
			return "{h}";
		default:
			return "";
		}
	}

	public static int getLineSizePerFontSize(int fontSize) {
		switch (fontSize) {
		case 0:
			return 64;
		case 1:
			return 48;
		case 2:
			return 48;
		default:
			return 0;
		}
	}
}